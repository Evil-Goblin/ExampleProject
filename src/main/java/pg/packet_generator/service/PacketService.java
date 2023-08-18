package pg.packet_generator.service;

import org.springframework.stereotype.Service;
import pg.packet_generator.connection.ConnectionHandler;
import pg.packet_generator.domain.Buffer;
import pg.packet_generator.domain.packet.Packet;
import pg.packet_generator.domain.packet.column.PacketColumn;
import pg.packet_generator.domain.sendinfo.SendInformation;

import java.util.concurrent.ExecutorService;

@Service
public class PacketService implements SendService {

    private final ExecutorService executorService;

    public PacketService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void execute(SendInformation sendInformation, Packet packetData) {
        ConnectionHandler connectionHandler = sendInformation.getConnectionHandler();
        connectionHandler.init();

        executorService.submit(() -> {
            Buffer buffer = new Buffer(packetData.getSize());

            sendInformation.executeIteratedJop(() -> {
                buffer.setIndex(0);
                packetData.write(buffer);
                connectionHandler.send(buffer);
            });
        });
    }
}
