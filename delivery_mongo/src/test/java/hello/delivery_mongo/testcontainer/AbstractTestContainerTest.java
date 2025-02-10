package hello.delivery_mongo.testcontainer;

import org.junit.jupiter.api.AfterAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

//@Import(AbstractTestContainerTest.MongoTestConfiguration.class)
//@SpringBootTest
@Testcontainers
public abstract class AbstractTestContainerTest {

//    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6")
            .withReuse(true);

    @DynamicPropertySource
    static void containersProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        String replicaSetUrl = mongoDBContainer.getReplicaSetUrl();

        registry.add("spring.data.mongodb.uri", () -> replicaSetUrl);
//        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
//        registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);

        System.out.println("replicaSetUrl = " + replicaSetUrl);
//        System.out.println("mongoDBContainer.getHost() = " + mongoDBContainer.getHost());
//        System.out.println("mongoDBContainer.getFirstMappedPort() = " + mongoDBContainer.getFirstMappedPort());
    }

//    @TestConfiguration
//    static class MongoTestConfiguration {
//
//        @Bean(initMethod = "start", destroyMethod = "stop")
//        public MongoDBContainer mongoDBContainer() {
//            return new MongoDBContainer("mongo:6");
//        }
//
//        @Bean
//        @DependsOn("mongoDBContainer")
//        public MongoDatabaseFactory mongoDatabaseFactory(MongoDBContainer mongoDBContainer) {
//            return new SimpleMongoClientDatabaseFactory(mongoDBContainer.getReplicaSetUrl());
//        }
//    }
}
