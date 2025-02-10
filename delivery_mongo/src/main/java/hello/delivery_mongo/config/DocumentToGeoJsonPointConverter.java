package hello.delivery_mongo.config;

import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;

//@ReadingConverter
public class DocumentToGeoJsonPointConverter implements Converter<Document, GeoJsonPoint> {
    @Override
    public GeoJsonPoint convert(Document source) {
        List<Double> coordinates = (List<Double>) source.get("coordinates");
        return new GeoJsonPoint(coordinates.get(0), coordinates.get(1));
    }
}
