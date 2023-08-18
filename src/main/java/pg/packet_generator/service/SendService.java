package pg.packet_generator.service;

import pg.packet_generator.domain.packet.Packet;
import pg.packet_generator.domain.sendinfo.SendInformation;

public interface SendService {

    void execute(SendInformation sendInformation, Packet packetData);
}
