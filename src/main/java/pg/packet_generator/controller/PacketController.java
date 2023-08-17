package pg.packet_generator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pg.packet_generator.domain.GenerateSpecification;
import pg.packet_generator.domain.packet.PacketColumn;
import pg.packet_generator.domain.packet.PacketData;
import pg.packet_generator.domain.sendinfo.SendInformation;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/packet/api")
public class PacketController {

    @PostMapping
    public String generate(@RequestBody GenerateSpecification generateSpecification) {
        SendInformation sendInformation = generateSpecification.getSendInformation();
        log.info("[PacketController:generate] SendInfo: {}", sendInformation.toString());
        PacketData packetData = generateSpecification.getPacketData();

        List<PacketColumn> header = packetData.getHeader();
        List<PacketColumn> body = packetData.getBody();
        List<PacketColumn> tail = packetData.getTail();
        for (PacketColumn packetColumn : header) {
            log.info("[PacketController:generate] header {}={}", packetColumn.getClass().getName(), packetColumn.getData());
        }
        for (PacketColumn packetColumn : body) {
            log.info("[PacketController:generate] body {}={}", packetColumn.getClass().getName(), packetColumn.getData());
        }
        for (PacketColumn packetColumn : tail) {
            log.info("[PacketController:generate] tail {}={}", packetColumn.getClass().getName(), packetColumn.getData());
        }
        return "ok";
    }
}
