package hello.delivery_mongo.repository;

import hello.delivery_mongo.domain.CategoryCode;
import hello.delivery_mongo.domain.Shop;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;

public interface CustomShopRepository {

    List<Shop> findShopsByIntersectsPoint(GeoJsonPoint point);

    List<CategoryCode> findDistinctCategoryCodesByIntersectsPoint(GeoJsonPoint point);

    boolean existsByCategoryAndPointIntersects(CategoryCode categoryCode, GeoJsonPoint point);
}
