package hello.delivery_mongo.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
public class MongoConfiguration {

//    @Bean
//    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
//        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
//                MongoClientSettings.getDefaultCodecRegistry(),
//                CodecRegistries.fromProviders(new GeoJsonPointCodecProvider())
//        );
//
//        return new MongoTemplate(
//                MongoClients.create(MongoClientSettings.builder()
//                        .applyToClusterSettings(builder -> builder.hosts(mongoClient.getClusterDescription().getClusterSettings().getHosts()))
//                        .codecRegistry(codecRegistry)
//                        .build()),
//                "mongodb"
//                );
//    }

    @Bean
    public CodecRegistry codecRegistry() {
        return CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(new GeoJsonPointCodecProvider())
        );
    }

//    @Bean
//    public MongoCustomConversions customConversions() {
//        return new MongoCustomConversions(Arrays.asList(
//                new GeoJsonPointToDocumentConverter(),
//                new DocumentToGeoJsonPointConverter()
//        ));
//    }
//    @Bean
//    public MongoCustomConversions customConversions() {
//        return new MongoCustomConversions(Arrays.asList(
//                new GeoJsonPolygonToDocumentConverter(),
//                new DocumentToGeoJsonPolygonConverter()
//        ));
//    }

//    @Bean
//    public MongoCustomConversions customConversions() {
//        return new MongoCustomConversions(List.of(new GeoJsonPointCodec()));
//    }
}
