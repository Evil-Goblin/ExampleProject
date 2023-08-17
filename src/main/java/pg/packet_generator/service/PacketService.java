package pg.packet_generator.service;

import pg.packet_generator.connection.ConnectionHandler;
import pg.packet_generator.domain.packet.PacketColumn;
import pg.packet_generator.domain.sendinfo.SendInformation;

import java.util.concurrent.ExecutorService;

public class PacketService implements SendService {

    private final ExecutorService executorService;

    public PacketService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void execute(SendInformation sendInformation, PacketColumn packetData) { // TODO: 보다 큰범위인 Packet Interface 필요 ( packet Write 메소드 구현필 )
        ConnectionHandler connectionHandler = sendInformation.getConnectionHandler();

        executorService.submit(() -> {
            byte[] packet = new byte[packetData.getSize()]; // TODO: 패킷 write를 위한 버퍼 객체 필요

            sendInformation.executeIteratedJop(() -> {

            });
        });
    }
}
