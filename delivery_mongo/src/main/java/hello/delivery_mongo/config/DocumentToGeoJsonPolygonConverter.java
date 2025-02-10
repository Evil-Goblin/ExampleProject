package hello.delivery_mongo.config;

import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import java.util.List;

//@ReadingConverter
public class DocumentToGeoJsonPolygonConverter implements Converter<Document, GeoJsonPolygon> {
    @Override
    public GeoJsonPolygon convert(Document source) {
        List<List<Double>> coordinates = (List<List<Double>>) source.get("coordinates");
        List<Point> list = coordinates.stream().map(point -> new Point(point.get(0), point.get(1))).toList();
        return new GeoJsonPolygon(list);
    }
}
