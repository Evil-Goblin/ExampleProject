package pg.packet_generator.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pg.packet_generator.connection.ConnectionHandler;
import pg.packet_generator.domain.packet.PacketData;
import pg.packet_generator.domain.sendinfo.SendInformation;
import pg.packet_generator.scheduler.PacketScheduler;

@Getter
@RequiredArgsConstructor
public class GenerateSpecification {
    private SendInformation sendInformation;
    private PacketData packetData;

    public PacketScheduler getRunnable() {
        ConnectionHandler connectionHandler = sendInformation.getConnectionHandler();
        connectionHandler.init();

        int executeCount = sendInformation.getExecuteCount();
        Buffer buffer = new Buffer(packetData.getSize());

        return PacketScheduler.from(executeCount, () -> {
            buffer.setIndex(0);
            packetData.write(buffer);
            connectionHandler.send(buffer);
        });
    }
}
