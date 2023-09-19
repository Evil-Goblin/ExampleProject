package pg.packet_generator.scheduler;

import java.util.concurrent.ScheduledFuture;

public class InfiniteScheduler implements PacketScheduler {

    private final Runnable target;

    public InfiniteScheduler(Runnable target) {
        this.target = target;
    }

    @Override
    public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {

    }

    @Override
    public void run() {
        target.run();
    }
}
