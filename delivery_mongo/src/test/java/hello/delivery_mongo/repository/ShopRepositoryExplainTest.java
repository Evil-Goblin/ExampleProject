package hello.delivery_mongo.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.delivery_mongo.domain.CategoryCode;
import hello.delivery_mongo.domain.Shop;
import hello.delivery_mongo.service.ShopService;
import hello.delivery_mongo.testcontainer.AbstractTestContainerTest;
import org.assertj.core.api.Assertions;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(properties = "app.debug=true")
class ShopRepositoryExplainTest extends AbstractTestContainerTest {

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MongoMappingContext mongoMappingContext;

    @Autowired
    ShopService shopService;

    @BeforeAll
    void setUp() {
        Resource explainSetup = new ClassPathResource("explain_setup");

        try (Stream<Path> paths = Files.walk(Paths.get(explainSetup.getURI()))) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> {
                        try {
                            List<Document> documents = objectMapper.readValue(
                                    Files.newBufferedReader(path),
                                    new TypeReference<>() {}
                            );

                            if (documents.isEmpty()) {
                                return;
                            }

                            mongoTemplate.getCollection(mongoTemplate.getCollectionName(Shop.class))
                                    .insertMany(documents);
                        } catch (IOException e) {
                            System.out.println("Exception while reading file: " + path);
                        }
                    });
        } catch (IOException e) {
            System.out.println("Exception while reading directory: " + explainSetup);
        }
    }

    @Test
    void explainFindIntersectsQueryTest() {
        long count = shopRepository.count();
        System.out.println("count = " + count);
//        System.out.println("mongoMappingContext.isAutoIndexCreation = " + mongoMappingContext.isAutoIndexCreation());
//        mongoTemplate.indexOps(Shop.class).getIndexInfo().forEach(System.out::println);
        List<Shop> shopsByIntersectsPoint = shopRepository.findShopsByIntersectsPoint(new GeoJsonPoint(126.9234, 37.611));
        List<CategoryCode> distinctCategoryCodesByIntersectsPoint = shopRepository.findDistinctCategoryCodesByIntersectsPoint(new GeoJsonPoint(126.9234, 37.611));
    }

    @Test
    void shopExistsByCategoryAndPointIntersectsWhenItHasMultipleDeliveryArea() {
        // given
        Shop chickenShop = Shop.builder()
                .name("치킨집")
                .categoryCodes(List.of(CategoryCode.CHICKEN))
                .deliveryAreas(
                        new GeoJsonMultiPolygon(
                                List.of(
                                        new GeoJsonPolygon(new GeoJsonPoint(0, 0), new GeoJsonPoint(0, 10), new GeoJsonPoint(10, 10), new GeoJsonPoint(10, 0), new GeoJsonPoint(0, 0)),
                                        new GeoJsonPolygon(new GeoJsonPoint(0, 0), new GeoJsonPoint(0, 1), new GeoJsonPoint(1, 1), new GeoJsonPoint(1, 0), new GeoJsonPoint(0, 0))
                                )
                        )
                )
                .build();

        Shop pizzaShop = Shop.builder()
                .name("피자집")
                .categoryCodes(List.of(CategoryCode.PIZZA))
                .deliveryAreas(
                        new GeoJsonMultiPolygon(
                                List.of(
                                        new GeoJsonPolygon(new GeoJsonPoint(0, 0), new GeoJsonPoint(0, 2), new GeoJsonPoint(2, 2), new GeoJsonPoint(2, 0), new GeoJsonPoint(0, 0))
                                )
                        )
                )
                .build();

        shopRepository.saveAll(List.of(chickenShop, pizzaShop));

        // when
        boolean found = shopRepository.existsByCategoryAndPointIntersects(CategoryCode.CHICKEN, new GeoJsonPoint(5, 5));
        boolean notFound = shopRepository.existsByCategoryAndPointIntersects(CategoryCode.PIZZA, new GeoJsonPoint(5, 5));

        // then
        Assertions.assertThat(found).isTrue();
        Assertions.assertThat(notFound).isFalse();
    }

    @Test
    void test() {
        Arrays.stream(CategoryCode.values())
                .filter(categoryCode -> shopRepository.existsByCategoryAndPointIntersects(categoryCode, new GeoJsonPoint(127.005926, 37.490060)))
                .toList();


        long startTime = System.currentTimeMillis();
        List<String> homeCategoriesOneByOne = shopService.getHomeCategoriesOneByOne(BigDecimal.valueOf(127.005926), BigDecimal.valueOf(37.490060));
        System.out.println("parallel elapsed time: " + (System.currentTimeMillis() - startTime) + "ms");

        startTime = System.currentTimeMillis();
        List<CategoryCode> list = Arrays.stream(CategoryCode.values())
                .filter(categoryCode -> shopRepository.existsByCategoryAndPointIntersects(categoryCode, new GeoJsonPoint(127.005926, 37.490060)))
                .toList();
        System.out.println("serial elapsed time: " + (System.currentTimeMillis() - startTime) + "ms");

        startTime = System.currentTimeMillis();
        List<CategoryCode> distinctCategoryCodesByIntersectsPoint = shopRepository.findDistinctCategoryCodesByIntersectsPoint(new GeoJsonPoint(127.005926, 37.490060));
        System.out.println("distinct elapsed time: " + (System.currentTimeMillis() - startTime) + "ms");

        System.out.println("homeCategoriesOneByOne = " + homeCategoriesOneByOne);
        System.out.println("list = " + list);
        System.out.println("distinctCategoryCodesByIntersectsPoint = " + distinctCategoryCodesByIntersectsPoint);
    }
}
