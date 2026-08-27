package com.duong.ecommerce.order;

import com.duong.ecommerce.order.dto.OrderDTORequest;
import com.duong.ecommerce.order.dto.OrderDTOResponse;

public interface OrderService {
    Long createOrder(OrderDTORequest request, Long customerId);

    OrderDTOResponse getOrder(Long orderId);

    void cancelOrder(Long orderId);
}
