package hello.delivery_mongo.repository;

import hello.delivery_mongo.domain.DeliveryArea;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestMongo extends MongoRepository<DeliveryArea, String> {
}
