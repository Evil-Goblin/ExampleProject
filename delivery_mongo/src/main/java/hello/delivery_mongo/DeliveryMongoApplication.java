package hello.delivery_mongo;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Polygon;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeliveryMongoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryMongoApplication.class, args);
    }

}
