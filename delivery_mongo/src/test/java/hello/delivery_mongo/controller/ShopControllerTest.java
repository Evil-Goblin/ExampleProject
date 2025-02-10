package hello.delivery_mongo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hello.delivery_mongo.domain.CategoryCode;
import hello.delivery_mongo.domain.Shop;
import hello.delivery_mongo.repository.ShopRepository;
import hello.delivery_mongo.testcontainer.AbstractTestContainerTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class ShopControllerTest extends AbstractTestContainerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ShopRepository shopRepository;

    @BeforeEach
    void setUp() {
        shopRepository.deleteAll();
    }

    @Test
    void testApiGetCategoriesDistinct() throws Exception {
        // given
        Shop chickenShop = Shop.builder()
                .name("치킨집")
                .categoryCodes(List.of(CategoryCode.CHICKEN))
                .deliveryAreas(new GeoJsonMultiPolygon(List.of(
                        new GeoJsonPolygon(new GeoJsonPoint(127.005,37.611), new GeoJsonPoint(127.005,38.611), new GeoJsonPoint(128.005,38.611), new GeoJsonPoint(128.005,37.611), new GeoJsonPoint(127.005,37.611))
                ))).build();
        Shop pizzaShop = Shop.builder()
                .name("피자집")
                .categoryCodes(List.of(CategoryCode.PIZZA))
                .deliveryAreas(new GeoJsonMultiPolygon(List.of(
                        new GeoJsonPolygon(new GeoJsonPoint(0,0), new GeoJsonPoint(0, 2), new GeoJsonPoint(2, 2), new GeoJsonPoint(2, 0), new GeoJsonPoint(0, 0))
                ))).build();
        shopRepository.saveAll(List.of(chickenShop, pizzaShop));

        // expect
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/home-categories/distinct")
                .queryParam("longitude", "127.005")
                .queryParam("latitude", "37.611"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.categories").isArray())
                .andExpect(jsonPath("$.categories").isNotEmpty())
                .andExpect(jsonPath("$.categories").value(Matchers.containsInAnyOrder("chicken")));
    }
}
