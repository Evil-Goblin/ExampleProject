package hello.delivery_mongo.config;

import com.mongodb.client.model.geojson.Point;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import java.util.Arrays;
import java.util.List;

//@WritingConverter
public class GeoJsonPolygonToDocumentConverter implements Converter<GeoJsonPolygon, Document> {
    @Override
    public Document convert(GeoJsonPolygon source) {
        return new Document("type", "Polygon")
                .append("coordinates", source.getPoints().stream().map(point -> List.of(point.getX(), point.getY())).toList());
    }
}
