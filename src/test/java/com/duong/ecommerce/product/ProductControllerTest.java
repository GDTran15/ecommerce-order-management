package com.duong.ecommerce.product;



import com.duong.ecommerce.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Import(TestConfig.class)
@ActiveProfiles("test")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Test
    void testCreateProduct() throws Exception {
        String json = """
                {
                   "productName": "Wireless Mouse",
                   "sku": "ABC-1234",
                   "productDescription": "Ergonomic wireless mouse",
                   "productPrice": 49.99,
                   "productQuantity": 10
                 }
                """;

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpectAll(
                        status().isCreated(),
                        header().exists("Location"));

        int count = JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate,
                "products",
                "sku = 'ABC-1234'"
        );

        assertEquals(1, count);
    }


 @Test
    void getProduct() throws Exception {
        Long productId = jdbcTemplate.queryForObject("""
                 INSERT INTO products
                (product_name, sku, product_description, product_price, product_quantity)
            VALUES
                (?, ?, ?, ?, ?)
            RETURNING product_id
            """,
                Long.class,
                "Wireless Mouse",
                "ABC-1234",
                "Ergonomic wireless mouse",
                new BigDecimal("49.99"),
                10);

        mockMvc.perform(get("/products/sku/{sku}","ABC-1234")
                .accept(MediaType.APPLICATION_JSON)).andExpectAll(
                        status().isOk(),
                jsonPath("$.productName").value("Wireless Mouse"),
                jsonPath("$.sku").value("ABC-1234"),
                jsonPath("$.productDescription").value("Ergonomic wireless mouse"),
                jsonPath("$.price").value("49.99"),
                jsonPath("$.quantity").value(10)
        );





 }




}
