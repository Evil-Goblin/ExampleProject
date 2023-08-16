package pg.packet_generator.domain.sendinfo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter // getter, setter 등의 정보가 없이는 deserialize 가 불가능하다.
@RequiredArgsConstructor
public class SendUdpInfo implements SendInformation {
    private String address;
    private int port;
    private boolean repeated;

    private int repeatCount;
    private long interval;
}
