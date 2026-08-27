package com.duong.ecommerce.product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImp implements ProductRepository {


    private final JdbcTemplate jdbcTemplate;

    public ProductRepositoryImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Product> rowMapper = ((rs, rowNum) -> Product.builder().id(rs.getLong("id"))
            .name(rs.getString("name"))
            .sku(rs.getString("sku"))
            .description(rs.getString("description"))
            .price(rs.getBigDecimal("price"))
            .quantity(rs.getInt("quantity"))
            .build());

    @Override
    public Long save(Product product) {
        String sql = """
                INSERT INTO products (name, sku , description, price, quantity)
                VALUES (?,?,?,?,?) RETURNING id
                """;
        return jdbcTemplate.queryForObject(sql, Long.class,product.getName(),
                product.getSku(),product.getDescription(),product.getPrice(),product.getQuantity());
    }

    @Override
    public Optional<Product> getBySku(String sku) {
        String sql = """
                SELECT * FROM products WHERE sku = ?
                """;
        List<Product> products = jdbcTemplate.query(sql,rowMapper ,sku);
        return products.stream().findFirst();
    }

    @Override
    public List<Product> getAll(int page, int size) {
        int offset = page * size;
        String sql = """
                SELECT * FROM products ORDER BY id LIMIT ? OFFSET ?
                """;
        return jdbcTemplate.query(sql,rowMapper,size,offset);

    }

    @Override
    public Optional<Product> findById(Long productId) {
        String sql = """
                SELECT * FROM products WHERE id = ?
                """;
        List<Product> products = jdbcTemplate.query(sql,rowMapper ,productId);
        return products.stream().findFirst();
    }

    @Override
    public boolean existedBySku(String sku) {
        String sql = """
                SELECT EXISTS (SELECT * FROM products WHERE sku = ?)
                """;
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class,sku);
        return Boolean.TRUE.equals(result);
    }

    @Override
    public void update(Product product) {
        String sql = """
                UPDATE products SET name = ? , sku = ? , description = ? , price = ?, quantity = ? WHERE id = ?
                """;

        jdbcTemplate.update(sql,product.getName(),
                product.getSku(),product.getDescription(),product.getPrice(),product.getQuantity(),product.getId());
    }

    @Override
    public boolean existedById(Long productId) {
        String sql = """
                SELECT EXISTS (SELECT * FROM products WHERE id = ?)
                """;
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class,productId);
        return Boolean.TRUE.equals(result);
    }

    @Override
    public void deleteById(Long productId) {
        String sql = """
                DELETE FROM products WHERE id = ?
                """;
        jdbcTemplate.update(sql,productId);
    }

    @Override
    public Optional<Product> findBySkuForUpdate(String sku) {
        String sql = """
                SELECT * FROM products WHERE sku = ? FOR UPDATE
                """;
        List<Product> products = jdbcTemplate.query(sql,rowMapper ,sku);
        return products.stream().findFirst();
    }

    @Override
    public Optional<Product> findByIdForUpdate(Long productId) {
        String sql = """
                SELECT * FROM products WHERE id = ? FOR UPDATE
                """;
        List<Product> products = jdbcTemplate.query(sql,rowMapper ,productId);
        return products.stream().findFirst();
    }


}
