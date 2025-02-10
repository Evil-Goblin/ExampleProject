package hello.delivery_mongo.service;

import hello.delivery_mongo.domain.CategoryCode;
import hello.delivery_mongo.domain.Shop;
import hello.delivery_mongo.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ShopService {

    private final ShopRepository shopRepository;

    public Shop insertOne(String name, List<CategoryCode> categoryCodes, List<GeoJsonPolygon> deliveryAreas) {
        Shop shop = Shop.builder()
                .name(name)
                .categoryCodes(categoryCodes)
                .deliveryAreas(new GeoJsonMultiPolygon(deliveryAreas))
                .build();

        return shopRepository.save(shop);
    }

    public List<Shop> pointIntersects(GeoJsonPoint point) {
        return shopRepository.findShopsByIntersectsPoint(point);
    }

    public List<CategoryCode> distinctCategoryCodesByIntersectsPoint(GeoJsonPoint point) {
        return shopRepository.findDistinctCategoryCodesByIntersectsPoint(point);
    }

    public List<String> getDistinctHomeCategories(BigDecimal longitude, BigDecimal latitude) {
        return distinctCategoryCodesByIntersectsPoint(new GeoJsonPoint(longitude.doubleValue(), latitude.doubleValue())).stream()
                .map(CategoryCode::getValue)
                .toList();
    }

    public List<String> getHomeCategoriesOneByOne(BigDecimal longitude, BigDecimal latitude) {
        GeoJsonPoint point = new GeoJsonPoint(longitude.doubleValue(), latitude.doubleValue());

        return Arrays.stream(CategoryCode.values()).parallel()
                .filter(categoryCode -> shopRepository.existsByCategoryAndPointIntersects(categoryCode, point))
                .map(CategoryCode::getValue)
                .toList();
    }
}
