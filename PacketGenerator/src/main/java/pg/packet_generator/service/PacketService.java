package pg.packet_generator.service;

import org.springframework.stereotype.Service;
import pg.packet_generator.domain.GenerateSpecification;
import pg.packet_generator.scheduler.PacketScheduler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class PacketService implements SendService {

    private final ScheduledExecutorService executorService;

    public PacketService(ScheduledExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void execute(GenerateSpecification generateSpecification) {
        PacketScheduler target = generateSpecification.getRunnable();
        long interval = generateSpecification.getSendInformation().getInterval();

        ScheduledFuture<?> scheduledFuture = executorService.scheduleAtFixedRate(target, 0, interval, TimeUnit.MILLISECONDS);
        target.setScheduledFuture(scheduledFuture);
    }
}
