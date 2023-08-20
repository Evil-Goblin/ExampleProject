package pg.packet_generator.scheduler;

import java.util.concurrent.ScheduledFuture;

public class FixedScheduler implements PacketScheduler {

    private int limitCount;
    private int executions = 0;
    private Runnable target;

    private ScheduledFuture<?> scheduledFuture;

    public FixedScheduler(int limitCount, Runnable target) {
        this.limitCount = limitCount;
        this.target = target;
    }

    @Override
    public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }

    @Override
    public void run() {
        target.run();
        executions++;

        if (executions >= limitCount) {
            scheduledFuture.cancel(false);
        }
    }
}
