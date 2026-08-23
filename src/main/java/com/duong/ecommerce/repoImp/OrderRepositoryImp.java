    package com.duong.ecommerce.repoImp;

    import com.duong.ecommerce.model.Order;
    import com.duong.ecommerce.model.OrderStatus;
    import com.duong.ecommerce.repository.OrderRepository;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.jdbc.core.RowMapper;
    import org.springframework.stereotype.Repository;

    import java.math.BigDecimal;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.Optional;

    @Repository
    public class OrderRepositoryImp implements OrderRepository {
        private final JdbcTemplate jdbcTemplate;

        public OrderRepositoryImp(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        public Order createOrder(Long customerId, String orderStatus) {
            String sql = """
                    INSERT INTO orders (status,customer_id) VALUES (?,?) RETURNING id, customer_id, status, total_amount
                    """;

            return jdbcTemplate.queryForObject(sql, this::productRowMapper, orderStatus, customerId);
        }

        @Override
        public void updateTotalAmount(BigDecimal totalAmount, Long orderId) {
            String sql = """
                    UPDATE orders SET total_amount = ? WHERE id = ?
                    """;
            jdbcTemplate.update(sql,totalAmount,orderId);
        }

        @Override
        public Optional<Order> getById(Long orderId) {
            String sql = "SELECT * FROM orders WHERE id = ?";
            return jdbcTemplate.query(sql,this::productRowMapper,orderId).stream().findFirst();
        }

        @Override
        public OrderStatus getOrderStatusById(Long orderId) {
            String sql = """
                    SELECT status FROM orders WHERE id = ?;
                    """;
            String status = jdbcTemplate.queryForObject(sql, String.class, orderId);
            return OrderStatus.valueOf(status);

        }

        @Override
        public void updateOrderStatus(OrderStatus orderStatus, Long orderId) {
            String sql = """
                    UPDATE orders SET status = ? WHERE id = ?
                    """;
            jdbcTemplate.update(sql,orderStatus.name(),orderId);
        }

        private Order productRowMapper (ResultSet rs,int rowNum) throws SQLException {
                return Order.builder()
                        .id(rs.getLong("d"))
                        .customerId(rs.getLong("customer_id"))
                        .status(OrderStatus.valueOf(rs.getString("order_status")))
                        .totalAmount(rs.getBigDecimal("total_amount"))
                        .build();
        }
    }
