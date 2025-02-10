package hello.delivery_mongo.config;

import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.Arrays;

//@WritingConverter
public class GeoJsonPointToDocumentConverter implements Converter<GeoJsonPoint, Document> {
    @Override
    public Document convert(GeoJsonPoint source) {
        return new Document("type", "Point")
                .append("coordinates", Arrays.asList(source.getX(), source.getY()));
    }
}
