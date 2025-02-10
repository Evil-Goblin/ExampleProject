package hello.delivery_mongo.repository;

import hello.delivery_mongo.domain.CategoryCode;
import hello.delivery_mongo.domain.Shop;
import hello.delivery_mongo.repository.explain.ExplainLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CustomShopRepositoryImpl implements CustomShopRepository {

    private final MongoTemplate mongoTemplate;
    private final ExplainLogger explainLogger;

    @Override
    public List<Shop> findShopsByIntersectsPoint(GeoJsonPoint point) {
        Query query = createIntersectsQuery(point, "deliveryAreas");
        return mongoTemplate.find(query, Shop.class);
    }

    @Override
    public List<CategoryCode> findDistinctCategoryCodesByIntersectsPoint(GeoJsonPoint point) {
        Query query = createIntersectsQuery(point, "deliveryAreas");
        return mongoTemplate.findDistinct(query, "categoryCodes", Shop.class, CategoryCode.class);
    }

    @Override
    public boolean existsByCategoryAndPointIntersects(CategoryCode categoryCode, GeoJsonPoint point) {
        Query query = new Query();
        query.addCriteria(Criteria.where("deliveryAreas").intersects(point));
        query.addCriteria(Criteria.where("categoryCodes").is(categoryCode));
        query.limit(1);

        explainLogger.explainFindQuery(query, Shop.class);

        return !mongoTemplate.find(query, Shop.class).isEmpty();
    }

    private Query createIntersectsQuery(GeoJsonPoint point, String key) {
        Criteria intersectsArea = Criteria.where(key).intersects(point);
        Query query = new Query(intersectsArea);
        explainLogger.explainFindQuery(query, Shop.class);

        return query;
    }
}
