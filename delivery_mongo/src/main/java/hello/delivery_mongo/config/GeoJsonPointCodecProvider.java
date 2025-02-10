package hello.delivery_mongo.config;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

public class GeoJsonPointCodecProvider implements CodecProvider {
    @Override
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        if (aClass == GeoJsonPoint.class) {
            return (Codec<T>) new GeoJsonPointCodec();
        }

        return null;
    }
}
