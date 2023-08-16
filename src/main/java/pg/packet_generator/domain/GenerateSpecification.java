package pg.packet_generator.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pg.packet_generator.domain.packet.PacketData;
import pg.packet_generator.domain.sendinfo.SendInformation;

@Getter
@RequiredArgsConstructor
public class GenerateSpecification {
    private SendInformation sendInformation;
    private PacketData packetData;

}
