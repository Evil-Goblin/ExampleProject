package hello.delivery_mongo.repository.explain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(value = "app.debug", havingValue = "false")
public class DisableExplainLoggerImpl implements ExplainLogger{
    @Override
    public void explainFindQuery(Query query, Class<?> documentClass) {
        // Do nothing
        log.info("Explain is disabled");
    }
}
