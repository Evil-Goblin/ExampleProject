package pg.packet_generator.domain.sendinfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pg.packet_generator.connection.ConnectionHandler;
import pg.packet_generator.connection.UdpHandler;
import pg.packet_generator.domain.PacketSender;

@ToString
@RequiredArgsConstructor
public class SendUdpInfo implements SendInformation {
    private String address;
    private int port;

    private int repeatCount;
    private long interval;


    @JsonCreator
    public SendUdpInfo(
            @JsonProperty("address")String address,
            @JsonProperty("port")int port,
            @JsonProperty("repeatCount") int repeatCount,
            @JsonProperty("interval") long interval) {
        this.address = address;
        this.port = port;
        this.repeatCount = repeatCount;
        this.interval = interval;
    }

    @Override
    public ConnectionHandler getConnectionHandler() {
        return new UdpHandler(address, port);
    }

    @Override
    public void executeIteratedJop(PacketSender sender) {
        if (repeatCount < 0) {
            executeInfinite(sender);
        }

        for (int i = 0; i < repeatCount; i++) {
            sender.send();
            sleepInterval(interval);
        }
    }

    private void executeInfinite(PacketSender sender) {
        while (true) {
            sender.send();
            sleepInterval(interval);
        }
    }

    private void sleepInterval(long interval) {
        try {
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean repeatValueValidation() {
        return repeatCount >= 0 || interval != 0;
    }

}
