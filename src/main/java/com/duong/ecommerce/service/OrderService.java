package com.duong.ecommerce.service;

import com.duong.ecommerce.dto.order.request.OrderDTORequest;
import com.duong.ecommerce.dto.order.response.OrderDTOResponse;

public interface OrderService {
    Long createOrder(OrderDTORequest request, Long customerId);

    OrderDTOResponse getOrder(Long orderId);

    void cancelOrder(Long orderId);
}
