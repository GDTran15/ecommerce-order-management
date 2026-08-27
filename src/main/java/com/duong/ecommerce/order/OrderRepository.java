package com.duong.ecommerce.order;

import java.math.BigDecimal;
import java.util.Optional;

public interface OrderRepository {


    Order createOrder(Long customerId, String orderStatus);

    void updateTotalAmount(BigDecimal totalAmount, Long orderId);

    Optional<Order> getById(Long orderId);

    OrderStatus getOrderStatusById(Long orderId);

    void updateOrderStatus(OrderStatus orderStatus, Long orderId);
}
