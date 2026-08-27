package com.duong.ecommerce.order;


import com.duong.ecommerce.order.dto.OrderDTORequest;
import com.duong.ecommerce.order.dto.OrderItemDTORequest;
import com.duong.ecommerce.exception.ResourceNotFoundException;
import com.duong.ecommerce.product.Product;
import com.duong.ecommerce.product.ProductService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class OrderServiceImpTest {

    @Mock
    private OrderRepository orderRepo;
    @Mock
    private OrderItemRepository orderItemRepo;
    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderServiceImp orderServiceImp;

    @Test
    void createOrderShouldSuccess(){
        OrderDTORequest orderDTORequest = new OrderDTORequest(
                List.of(new OrderItemDTORequest(
                        "ABC-1234",2
                ))
        );
        Order order =  Order.builder().id(2L).build();
        Product product = new Product();
        product.setId(1L);
        product.setSku("ABC-1234");
        product.setPrice(new BigDecimal("50.00"));
        product.setQuantity(10);

        when(productService.findBySkuForUpdate("ABC-1234")).thenReturn(Optional.of(product));
        when(orderRepo.createOrder(10L, OrderStatus.COMPLETED.name())).thenReturn(order);

        Long orderId = orderServiceImp.createOrder(orderDTORequest,10L);

        assertEquals(2L, orderId);

        verify(orderItemRepo,times(1)).save(any());
        verify(orderRepo,times(1)).updateTotalAmount(new BigDecimal("100.00"),order.getId());

    }

    @Test
    void createOrderShouldThrowProductNotFound(){
        //given
        OrderDTORequest orderDTORequest = new OrderDTORequest(
               List.of(new OrderItemDTORequest(
                       "ABC-1234",2
               ))
        );
        Order order = Order.builder().id(1L).build();
        Product product = new Product();
        product.setId(2L);
        product.setSku("ABC-1034");
        product.setPrice(new BigDecimal("50.00"));
        product.setQuantity(10);

        when(productService.findBySkuForUpdate("ABC-1234")).thenReturn(Optional.empty());
        when(orderRepo.createOrder(10L,OrderStatus.COMPLETED.name())).thenReturn(order);

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> orderServiceImp.createOrder(orderDTORequest,10L)
                );
    }
}
