package com.duong.ecommerce.dto.order.request;

import java.util.List;

public record OrderDTORequest(
            List<OrderItemDTORequest> orderLineDTORequests
) {
}
