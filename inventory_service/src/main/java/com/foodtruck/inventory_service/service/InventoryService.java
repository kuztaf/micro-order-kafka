package com.foodtruck.inventory_service.service;

import com.foodtruck.inventory_service.dto.InventoryRequest;
import com.foodtruck.inventory_service.dto.InventoryResponse;
import com.foodtruck.inventory_service.dto.StockCheckResponse;
import com.foodtruck.inventory_service.entity.Product;
import com.foodtruck.inventory_service.entity.StockMovement;
import com.foodtruck.inventory_service.exception.InsufficientStockException;
import com.foodtruck.inventory_service.exception.ProductNotFoundException;
import com.foodtruck.inventory_service.repository.ProductRepository;
import com.foodtruck.inventory_service.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;
    private final KafkaProducerService kafkaProducerService;

    @Transactional
    public InventoryResponse createProduct(InventoryRequest request) {
        if (productRepository.findBySku(request.getSku()).isPresent()) {
            throw new IllegalArgumentException("Product with SKU " + request.getSku() + " already exists");
        }

        Product product = Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .category(request.getCategory())
                .available(request.getQuantity() > 0)
                .build();

        Product savedProduct = productRepository.save(product);

        // Record initial stock movement
        recordStockMovement(savedProduct.getId(), "IN", request.getQuantity(),
                          0, request.getQuantity(), "Initial stock");

        log.info("Created product: {}", savedProduct.getSku());
        return mapToResponse(savedProduct);
    }

    @Transactional
    public InventoryResponse updateProduct(String sku, InventoryRequest request) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException(sku, true));

        int previousQuantity = product.getQuantity();
        product.setName(request.getName());
        product.setQuantity(request.getQuantity());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setAvailable(request.getQuantity() > 0);

        Product updatedProduct = productRepository.save(product);

        // Record stock movement if quantity changed
        if (previousQuantity != request.getQuantity()) {
            String movementType = request.getQuantity() > previousQuantity ? "IN" : "OUT";
            int difference = Math.abs(request.getQuantity() - previousQuantity);
            recordStockMovement(product.getId(), movementType, difference,
                              previousQuantity, request.getQuantity(), "Manual update");
        }

        log.info("Updated product: {}", sku);
        return mapToResponse(updatedProduct);
    }

    @Transactional
    public void reserveStock(Long orderId, String sku, Integer quantity) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException(sku, true));

        if (product.getQuantity() < quantity) {
            throw new InsufficientStockException(sku, quantity, product.getQuantity());
        }

        int previousQuantity = product.getQuantity();
        product.setQuantity(previousQuantity - quantity);
        product.setAvailable(product.getQuantity() > 0);

        productRepository.save(product);
        recordStockMovement(product.getId(), "RESERVED", quantity,
                          previousQuantity, product.getQuantity(), "Order " + orderId);

        // Publish inventory reserved event
        kafkaProducerService.sendInventoryReservedEvent(orderId, sku, quantity);

        log.info("Reserved {} units of product {} for order {}", quantity, sku, orderId);
    }

    @Transactional
    public void releaseStock(Long orderId, String sku, Integer quantity) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException(sku, true));

        int previousQuantity = product.getQuantity();
        product.setQuantity(previousQuantity + quantity);
        product.setAvailable(true);

        productRepository.save(product);
        recordStockMovement(product.getId(), "RELEASED", quantity,
                          previousQuantity, product.getQuantity(), "Order " + orderId + " cancelled");

        // Publish inventory released event
        kafkaProducerService.sendInventoryReleasedEvent(orderId, sku, quantity);

        log.info("Released {} units of product {} for cancelled order {}", quantity, sku, orderId);
    }

    public StockCheckResponse checkStock(String sku, Integer requestedQuantity) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException(sku, true));

        boolean sufficient = product.getQuantity() >= requestedQuantity;
        String message = sufficient ?
            "Stock available" :
            String.format("Only %d units available, requested %d", product.getQuantity(), requestedQuantity);

        return StockCheckResponse.builder()
                .sku(sku)
                .name(product.getName())
                .requestedQuantity(requestedQuantity)
                .availableQuantity(product.getQuantity())
                .sufficientStock(sufficient)
                .message(message)
                .build();
    }

    public List<InventoryResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public InventoryResponse getProductBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException(sku, true));
        return mapToResponse(product);
    }

    public List<InventoryResponse> getLowStockProducts(Integer threshold) {
        return productRepository.findLowStockProducts(threshold).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private void recordStockMovement(Long productId, String movementType, Integer quantity,
                                   Integer previousQuantity, Integer newQuantity, String reason) {
        StockMovement movement = StockMovement.builder()
                .productId(productId)
                .movementType(movementType)
                .quantity(quantity)
                .previousQuantity(previousQuantity)
                .newQuantity(newQuantity)
                .reason(reason)
                .build();

        stockMovementRepository.save(movement);
    }

    private InventoryResponse mapToResponse(Product product) {
        return InventoryResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .category(product.getCategory())
                .available(product.getAvailable())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}