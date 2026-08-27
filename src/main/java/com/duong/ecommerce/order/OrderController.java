package com.duong.ecommerce.order;


import com.duong.ecommerce.order.dto.OrderDTORequest;
import com.duong.ecommerce.order.dto.OrderDTOResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderDTORequest request, @RequestParam Long customerId){
         Long id = orderService.createOrder(request,customerId);

         URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTOResponse>  getOrder(@PathVariable Long id){
        OrderDTOResponse dtoResponse = orderService.getOrder(id);
        return ResponseEntity.ok(dtoResponse);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void>  cancelOrder(@PathVariable Long id
    ){
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }


}
