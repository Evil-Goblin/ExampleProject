package hello.delivery_mongo.config;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

public class GeoJsonPointCodec implements Codec<GeoJsonPoint> {
    @Override
    public GeoJsonPoint decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        bsonReader.readString("type");  // "Point" 타입 확인
        bsonReader.readStartArray();
        double x = bsonReader.readDouble();
        double y = bsonReader.readDouble();
        bsonReader.readEndArray();
        bsonReader.readEndDocument();
        return new GeoJsonPoint(x, y);
    }

    @Override
    public void encode(BsonWriter bsonWriter, GeoJsonPoint geoJsonPoint, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("type", "Point");
        bsonWriter.writeStartArray("coordinates");
        bsonWriter.writeDouble(geoJsonPoint.getX());
        bsonWriter.writeDouble(geoJsonPoint.getY());
        bsonWriter.writeEndArray();
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<GeoJsonPoint> getEncoderClass() {
        return GeoJsonPoint.class;
    }
}
