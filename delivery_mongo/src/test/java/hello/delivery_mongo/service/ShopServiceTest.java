package hello.delivery_mongo.service;

import hello.delivery_mongo.domain.CategoryCode;
import hello.delivery_mongo.domain.Shop;
import hello.delivery_mongo.repository.ShopRepository;
import hello.delivery_mongo.testcontainer.AbstractTestContainerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ShopServiceTest extends AbstractTestContainerTest {

    @Autowired
    ShopService shopService;

    @Autowired
    ShopRepository shopRepository;

    @BeforeEach
    void setUp() {
        shopRepository.deleteAll();
    }

    @DisplayName("Shop 을 단건 저장한다.")
    @Test
    void saveOneOfShop() {
        // given
        String name = "치킨집";
        List<CategoryCode> categoryCode = List.of(CategoryCode.CHICKEN);
        List<GeoJsonPolygon> deliveryAreas = List.of(
                new GeoJsonPolygon(new GeoJsonPoint(0, 0), new GeoJsonPoint(0, 10), new GeoJsonPoint(10, 10), new GeoJsonPoint(10, 0), new GeoJsonPoint(0, 0))
        );

        // when
        Shop shop = shopService.insertOne(name, categoryCode, deliveryAreas);
        List<Shop> shops = shopRepository.findAll();

        // then
        assertThat(shops).hasSize(1);

        Shop findShop = shops.get(0);

        assertThat(findShop.getId()).isEqualTo(shop.getId());
        assertThat(findShop.getName()).isEqualTo(shop.getName());
        assertThat(findShop.getCategoryCodes()).hasSize(1);
        assertThat(findShop.getCategoryCodes().get(0)).isEqualTo(CategoryCode.CHICKEN);
        assertThat(findShop.getDeliveryAreas().getCoordinates()).hasSize(1);
        assertThat(findShop.getDeliveryAreas().getCoordinates().get(0)).isEqualTo(new GeoJsonPolygon(new GeoJsonPoint(0, 0), new GeoJsonPoint(0, 10), new GeoJsonPoint(10, 10), new GeoJsonPoint(10, 0), new GeoJsonPoint(0, 0)));
    }

    @DisplayName("Point 에 해당하는 Shop 을 조회한다.")
    @Test
    void shopPointIntersectsTest() {
        // given
        shopService.insertOne("치킨집", List.of(CategoryCode.CHICKEN), List.of(
                new GeoJsonPolygon(new GeoJsonPoint(0, 0), new GeoJsonPoint(0, 10), new GeoJsonPoint(10, 10), new GeoJsonPoint(10, 0), new GeoJsonPoint(0, 0))
        ));
        shopService.insertOne("피자집", List.of(CategoryCode.PIZZA), List.of(
                new GeoJsonPolygon(new GeoJsonPoint(0, 0), new GeoJsonPoint(0, 2), new GeoJsonPoint(2, 2), new GeoJsonPoint(2, 0), new GeoJsonPoint(0, 0))
        ));

        // when
        List<Shop> shops = shopService.pointIntersects(new GeoJsonPoint(5, 5));

        // then
        assertThat(shops).hasSize(1);
        assertThat(shops.get(0).getName()).isEqualTo("치킨집");
    }

    @DisplayName("여러 영역을 가진 Shop 도 조회된다.")
    @Test
    void shopPointIntersectsMultipleDeliveryAreasTest() {
        // given
        shopService.insertOne("치킨집", List.of(CategoryCode.CHICKEN), List.of(
                new GeoJsonPolygon(new GeoJsonPoint(0, 0), new GeoJsonPoint(0, 10), new GeoJsonPoint(10, 10), new GeoJsonPoint(10, 0), new GeoJsonPoint(0, 0)),
                new GeoJsonPolygon(new GeoJsonPoint(0, 0), new GeoJsonPoint(0, 1), new GeoJsonPoint(1, 1), new GeoJsonPoint(1, 0), new GeoJsonPoint(0, 0))
        ));
        shopService.insertOne("피자집", List.of(CategoryCode.PIZZA), List.of(
                new GeoJsonPolygon(new GeoJsonPoint(0, 0), new GeoJsonPoint(0, 2), new GeoJsonPoint(2, 2), new GeoJsonPoint(2, 0), new GeoJsonPoint(0, 0))
        ));

        // when
        List<Shop> shops = shopService.pointIntersects(new GeoJsonPoint(5, 5));

        // then
        assertThat(shops).hasSize(1);
        assertThat(shops.get(0).getName()).isEqualTo("치킨집");
    }

    @DisplayName("Point 에 해당하는 Shop 의 CategoryCode 를 조회한다.")
    @Test
    void distinctCategoryCodesByIntersectsPointTest() {
        // given
        shopService.insertOne("치킨집1", List.of(CategoryCode.CHICKEN), List.of(
                new GeoJsonPolygon(new GeoJsonPoint(0, 0), new GeoJsonPoint(0, 10), new GeoJsonPoint(10, 10), new GeoJsonPoint(10, 0), new GeoJsonPoint(0, 0)),
                new GeoJsonPolygon(new GeoJsonPoint(0, 0), new GeoJsonPoint(0, 1), new GeoJsonPoint(1, 1), new GeoJsonPoint(1, 0), new GeoJsonPoint(0, 0))
        ));

        shopService.insertOne("치킨집2", List.of(CategoryCode.CHICKEN), List.of(
                new GeoJsonPolygon(new GeoJsonPoint(0, 0), new GeoJsonPoint(0, 9), new GeoJsonPoint(9, 9), new GeoJsonPoint(9, 0), new GeoJsonPoint(0, 0)),
                new GeoJsonPolygon(new GeoJsonPoint(0, 0), new GeoJsonPoint(0, 1), new GeoJsonPoint(1, 1), new GeoJsonPoint(1, 0), new GeoJsonPoint(0, 0))
        ));

        shopService.insertOne("피자집", List.of(CategoryCode.PIZZA), List.of(
                new GeoJsonPolygon(new GeoJsonPoint(0, 0), new GeoJsonPoint(0, 2), new GeoJsonPoint(2, 2), new GeoJsonPoint(2, 0), new GeoJsonPoint(0, 0))
        ));

        shopService.insertOne("버거집", List.of(CategoryCode.BURGER), List.of(
                new GeoJsonPolygon(new GeoJsonPoint(0, 0), new GeoJsonPoint(0, 9), new GeoJsonPoint(9, 9), new GeoJsonPoint(9, 0), new GeoJsonPoint(0, 0))
        ));

        // when
        List<CategoryCode> categoryCodes = shopService.distinctCategoryCodesByIntersectsPoint(new GeoJsonPoint(5, 5));

        // then
        assertThat(categoryCodes).containsOnly(CategoryCode.CHICKEN, CategoryCode.BURGER);
    }
}
