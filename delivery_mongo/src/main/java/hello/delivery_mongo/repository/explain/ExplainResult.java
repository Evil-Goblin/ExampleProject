package hello.delivery_mongo.repository.explain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class ExplainResult {
    private QueryPlanner queryPlanner;
    private ExecutionStats executionStats;

    @ToString
    @Getter
    public static class QueryPlanner {
        private Map<String, Object> winningPlan;
        private boolean indexFilterSet;
        private Map<String, Object> parsedQuery;
    }

    @ToString
    @Getter
    public static class ExecutionStats {
        private int executionTimeMillis;
        private int nReturned;
        private int totalDocsExamined;
        private int totalKeysExamined;
    }
}
