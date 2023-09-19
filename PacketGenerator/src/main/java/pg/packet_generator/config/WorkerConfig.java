package pg.packet_generator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class WorkerConfig {

    public static final int N_THREADS = 50; // TODO: 옵션값 사용하도록 개선

    @Bean
    public ScheduledExecutorService executorService() {
        return Executors.newScheduledThreadPool(N_THREADS);
    }
}
