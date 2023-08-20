package pg.packet_generator.domain.sendinfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pg.packet_generator.connection.ConnectionHandler;
import pg.packet_generator.connection.UdpHandler;

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
    public boolean repeatValueValidation() {
        return repeatCount >= 0 || interval != 0;
    }

    @Override
    public int getExecuteCount() {
        return repeatCount;
    }

    @Override
    public long getInterval() {
        return interval;
    }

}
