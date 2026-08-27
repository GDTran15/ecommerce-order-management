package com.duong.ecommerce.order;

import java.util.List;


public interface OrderItemRepository{

    Long save(OrderItem orderItem);

    List<OrderItem> getByOrderId(Long orderId);
}
