package com.duong.ecommerce.order;

import com.duong.ecommerce.exception.OrderAlreadyCancelledException;
import com.duong.ecommerce.exception.OutOfStockException;
import com.duong.ecommerce.order.dto.OrderDTORequest;
import com.duong.ecommerce.order.dto.OrderItemDTORequest;

import com.duong.ecommerce.order.dto.OrderDTOResponse;
import com.duong.ecommerce.order.dto.OrderItemDTOResponse;
import com.duong.ecommerce.exception.ResourceNotFoundException;
import com.duong.ecommerce.product.Product;
import com.duong.ecommerce.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepo;
    private final ProductService productService;
    private final OrderItemRepository orderItemRepo;


    @Override
    @Transactional
    public Long createOrder(OrderDTORequest request, Long customerId) {
        Order order = orderRepo.createOrder(customerId, OrderStatus.COMPLETED.name());
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItemDTORequest item: request.orderLineDTORequests()){
            Product product = productService.findBySkuForUpdate(item.sku()).orElseThrow(
                    () -> new ResourceNotFoundException("Product " + item.sku() + " not found")
            );

            int newQuantity = product.getQuantity() -  item.quantity();
            if (newQuantity < 0) {
                throw new OutOfStockException("Product " + product.getName() + " is out of stock");
            }
            OrderItem orderItem = OrderItem.builder()
                    .orderId(order.getId())
                    .productId(product.getId())
                    .unitPrice(product.getPrice())
                    .quantity(item.quantity()).build();
            orderItemRepo.save(orderItem);
            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(item.quantity())));
            orderRepo.updateTotalAmount(totalAmount,order.getId());
        }
        return order.getId();
    }

    @Override
    public OrderDTOResponse getOrder(Long orderId) {
        Order order = orderRepo.getById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Order not found")
        );

        List<OrderItemDTOResponse> orderItemDTOResponses = orderItemRepo.getByOrderId(order.getId()).stream()
                .map(this::orderItemDTOResponse)
                .toList();

        return new OrderDTOResponse(
                order.getTotalAmount(),
                order.getStatus(),
                order.getCustomerId(),
                orderItemDTOResponses
        );
    }

    @Override
    public void cancelOrder(Long orderId) {
        OrderStatus status = orderRepo.getOrderStatusById(orderId);
        if (status == OrderStatus.CANCELLED){
            throw new OrderAlreadyCancelledException("Order already cancelled");
        }
        orderRepo.updateOrderStatus(OrderStatus.CANCELLED,orderId);
        List<OrderItem> orderItems = orderItemRepo.getByOrderId(orderId);
        for (OrderItem item: orderItems){
            Product product = productService.findByIdForUpdate(item.getProductId()).orElseThrow(
                    () -> new ResourceNotFoundException("Product not found"));

            product.setQuantity(product.getQuantity() + item.getQuantity());
            productService.update(product);
        }

    }

    private OrderItemDTOResponse orderItemDTOResponse(OrderItem orderItem){
        Product product = productService.findById(orderItem.getProductId()).orElseThrow(
                () -> new ResourceNotFoundException("Product not found")
        );
        return new OrderItemDTOResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getPrice(),
                orderItem.getQuantity());
    }


}
