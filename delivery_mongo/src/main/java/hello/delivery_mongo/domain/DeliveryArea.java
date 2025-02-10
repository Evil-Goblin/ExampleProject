package hello.delivery_mongo.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "delivery_areas")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryArea {

    @Id
    private String id;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPolygon polygon;

    public DeliveryArea(GeoJsonPolygon polygon) {
        this.polygon = polygon;
    }
}
