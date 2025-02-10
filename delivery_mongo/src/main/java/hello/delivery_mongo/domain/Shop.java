package hello.delivery_mongo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@CompoundIndex(name = "delivery_areas_category_codes_idx", def = "{'deliveryAreas': '2dsphere', 'categoryCodes': 1}")
@Document(collection = "shops")
public class Shop {
    @Id
    private String id;

    private String name;
    private List<CategoryCode> categoryCodes;
//    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonMultiPolygon deliveryAreas;

    @Builder
    public Shop(String name, List<CategoryCode> categoryCodes, GeoJsonMultiPolygon deliveryAreas) {
        this.name = name;
        this.categoryCodes = categoryCodes;
        this.deliveryAreas = deliveryAreas;
    }
}
