package com.duong.ecommerce.repoImp;

import com.duong.ecommerce.model.OrderItem;
import com.duong.ecommerce.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderItemRepoImp implements OrderItemRepository {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public Long save(OrderItem orderItem) {
        String sql = """ 
INSERT INTO order_items (order_id,product_id,quantity,unit_price) VALUES (?,?,?,?) RETURNING order_items_id
""";

       return jdbcTemplate.queryForObject(sql, Long.class, orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), orderItem.getUnitPrice());
    }

    @Override
    public List<OrderItem> getByOrderId(Long orderId) {
        String sql = """
                SELECT * FROM order_items WHERE order_id = ?
                """;

        return jdbcTemplate.query(sql,this::rowMapperToOrderItem,orderId);
    }

    private OrderItem rowMapperToOrderItem(ResultSet rs, int rowNum) throws SQLException{
        return OrderItem.builder()
                .id(rs.getLong("_id"))
                .orderId(rs.getLong("order_id"))
                .productId(rs.getLong("product_id"))
                .unitPrice(rs.getBigDecimal("unit_price"))
                .quantity(rs.getInt("quantity"))
                .build();
    }

}
