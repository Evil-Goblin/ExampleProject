package hello.delivery_mongo.repository.explain;

import org.springframework.data.mongodb.core.query.Query;

public interface ExplainLogger {
    void explainFindQuery(Query query, Class<?> documentClass);
}
