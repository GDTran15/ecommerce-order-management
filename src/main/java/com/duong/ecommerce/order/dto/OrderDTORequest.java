package com.duong.ecommerce.order.dto;

import java.util.List;

public record OrderDTORequest(
            List<OrderItemDTORequest> orderLineDTORequests
) {
}
