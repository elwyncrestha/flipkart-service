package com.example.flipkart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.example.flipkart.controller.MerchantController;
import com.example.flipkart.controller.ProductController;
import com.example.flipkart.dto.ResponseDto;
import com.example.flipkart.entity.Merchant;
import com.example.flipkart.entity.Product;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class FlipkartApplicationTests {

    private static final Merchant MERCHANT_1 = Merchant.builder().name("John Doe")
        .mobileNumber("1234567890").emailAddress("john.doe@mail.com").panNumber("9876543210")
        .build();
    private static final Merchant MERCHANT_2 = Merchant.builder().name("Jane Doe")
        .mobileNumber("135792480").emailAddress("jane.doe@mail.com").panNumber("0864297531")
        .build();

    private static final int MERCHANT_NOT_CREATED = 1;
    private static final int MERCHANT_CREATE = 2;
    private static final int MERCHANT_CREATED = 3;

    private static final int PRODUCT_NOT_CREATED = 1;
    private static final int PRODUCT_DELETE = 4;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MerchantController merchantController;

    @Autowired
    private ProductController productController;

    @Test
    @Order(MERCHANT_NOT_CREATED)
    void contextLoads() {
        assertThat(merchantController).isNotNull();
        assertThat(productController).isNotNull();
    }

    @Test
    @Order(MERCHANT_NOT_CREATED)
    void hasNoMerchantsInitially() {
        ResponseDto dto = restTemplate.getForObject(getUrl("/merchant"), ResponseDto.class);
        assertThat(dto.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat((List<Merchant>) dto.getBody()).isEmpty();
    }

    @Test
    @Order(MERCHANT_CREATE)
    void savesMerchant() {
        ResponseDto dto1 = restTemplate.postForObject(getUrl("/merchant"), MERCHANT_1,
            ResponseDto.class);
        assertThat(dto1.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(objectMapper.convertValue(dto1.getBody(), Merchant.class).getId()).isNotNull();

        ResponseDto dto2 = restTemplate.postForObject(getUrl("/merchant"), MERCHANT_2,
            ResponseDto.class);
        assertThat(dto2.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(objectMapper.convertValue(dto2.getBody(), Merchant.class).getId()).isNotNull();
    }

    @Test
    @Order(MERCHANT_CREATED)
    void findsAllMerchants() {
        ResponseDto dto = restTemplate.getForObject(getUrl("/merchant"), ResponseDto.class);
        assertThat(dto.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat((List<Merchant>) dto.getBody()).isNotEmpty();
    }

    @Test
    @Order(MERCHANT_CREATED)
    void findsMerchantById() {
        int id = 1;
        ResponseDto dto = restTemplate.getForObject(getUrl("/merchant/" + id), ResponseDto.class);
        assertThat(dto.getStatus()).isEqualTo(HttpStatus.OK.value());
        Merchant response = objectMapper.convertValue(dto.getBody(), Merchant.class);
        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo(MERCHANT_1.getName());
    }

    @Test
    @Order(PRODUCT_NOT_CREATED)
    void hasNoProductsInitially() {
        ResponseDto dto = restTemplate.getForObject(getUrl("/product"), ResponseDto.class);
        assertThat(dto.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat((List<Product>) dto.getBody()).isEmpty();
    }

    @Test
    @Order(MERCHANT_CREATED)
    void findsProductByMerchantId() {
        Merchant merchant1 = new Merchant();
        BeanUtils.copyProperties(MERCHANT_1, merchant1);
        long merchant1Id = 1L;
        merchant1.setId(merchant1Id);
        Product merchant1Product = Product.builder().name("Electric Heater").price(1500.50f)
            .merchant(merchant1).build();
        restTemplate.postForObject(getUrl("/product"), merchant1Product, ResponseDto.class);

        Merchant merchant2 = new Merchant();
        BeanUtils.copyProperties(MERCHANT_2, merchant2);
        long merchant2Id = 2L;
        merchant2.setId(merchant2Id);
        Product merchant2Product = Product.builder().name("Blanket").price(500f)
            .merchant(merchant2).build();
        restTemplate.postForObject(getUrl("/product"), merchant2Product, ResponseDto.class);

        ResponseDto dto1 = restTemplate.getForObject(getUrl("/product/merchant/" + merchant1Id),
            ResponseDto.class);
        List<?> merchant1Products = objectMapper.convertValue(dto1.getBody(), List.class);
        assertThat(merchant1Products).isNotEmpty();
        assertThat(objectMapper.convertValue(merchant1Products.get(0), Product.class).getName()).isEqualTo("Electric Heater");

        ResponseDto dto2 = restTemplate.getForObject(getUrl("/product/merchant/" + merchant2Id),
            ResponseDto.class);
        List<?> merchant2Products = objectMapper.convertValue(dto2.getBody(), List.class);
        assertThat(merchant2Products).isNotEmpty();
        assertThat(objectMapper.convertValue(merchant2Products.get(0), Product.class).getName()).isEqualTo("Blanket");
    }

    @Test
    @Order(PRODUCT_DELETE)
    void deletesProductById() {
        int id = 1;
        restTemplate.delete(getUrl("/product/" + id));
        ResponseDto dto = restTemplate.getForObject(getUrl("/product/" + id), ResponseDto.class);
        assertThat(dto).isNull();
    }

    private String getUrl(String url) {
        return "http://localhost:" + port + url;
    }

}
