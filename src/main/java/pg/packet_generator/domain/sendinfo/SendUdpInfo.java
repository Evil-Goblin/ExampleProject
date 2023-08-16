package pg.packet_generator.domain.sendinfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class SendUdpInfo implements SendInformation {
    private String address;
    private int port;
    private boolean repeated;

    private int repeatCount;
    private long interval;


    @JsonCreator
    public SendUdpInfo(
            @JsonProperty("address")String address,
            @JsonProperty("port")int port,
            @JsonProperty("repeated") boolean repeated,
            @JsonProperty("repeatCount") int repeatCount,
            @JsonProperty("interval") long interval) {
        this.address = address;
        this.port = port;
        this.repeated = repeated;
        this.repeatCount = repeatCount;
        this.interval = interval;
    }
}
