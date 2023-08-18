package pg.packet_generator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class WorkerConfig {

    public static final int N_THREADS = 50; // TODO: 옵션값 사용하도록 개선

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(N_THREADS);
    }
}
