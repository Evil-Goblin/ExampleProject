package hello.delivery_mongo.repository;

import hello.delivery_mongo.domain.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopRepository extends MongoRepository<Shop, String>, CustomShopRepository {
}
