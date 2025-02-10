package hello.delivery_mongo.repository.explain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ExplainVerbosity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@ConditionalOnProperty(value = "app.debug", havingValue = "true")
public class ExplainLoggerImpl implements ExplainLogger {

    private final MongoTemplate mongoTemplate;
    private final CodecRegistry codecRegistry;
    private final ObjectMapper objectMapper;

    @Override
    public void explainFindQuery(Query query, Class<?> documentClass) {
        Document explain = mongoTemplate.getCollection(mongoTemplate.getCollectionName(documentClass))
                .withCodecRegistry(codecRegistry)
                .find(query.getQueryObject())
                .explain(ExplainVerbosity.EXECUTION_STATS);

        String result = parseExplainStats(explain);

        log.info("Explain: {}", result);
    }

    private String parseExplainStats(Document explain) {
        try {
            return objectMapper.readValue(explain.toJson(), ExplainResult.class).toString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
//            return explain.toJson();
        }
    }
}
