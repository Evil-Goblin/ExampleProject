package pg.packet_generator.scheduler;

import java.util.concurrent.ScheduledFuture;

public interface PacketScheduler extends Runnable {

    public static PacketScheduler from(int executeCount, Runnable target) {
        if (executeCount < 0) {
            return new InfiniteScheduler(target);
        }
        return new FixedScheduler(executeCount, target);
    }

    void setScheduledFuture(ScheduledFuture<?> scheduledFuture);
}
