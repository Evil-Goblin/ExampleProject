package pg.packet_generator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pg.packet_generator.domain.GenerateSpecification;
import pg.packet_generator.domain.packet.column.PacketColumn;
import pg.packet_generator.domain.packet.PacketData;
import pg.packet_generator.domain.sendinfo.SendInformation;
import pg.packet_generator.service.SendService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/packet/api")
public class PacketController {

    private final SendService sendService;

    public PacketController(SendService sendService) {
        this.sendService = sendService;
    }

    @PostMapping
    public String generate(@RequestBody GenerateSpecification generateSpecification) {
        SendInformation sendInformation = generateSpecification.getSendInformation();
        log.info("[PacketController:generate] SendInfo: {}", sendInformation.toString());
        if (!sendInformation.repeatValueValidation()) {
            // TODO: error
            return "error";
        }

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

        try {
            sendService.execute(sendInformation, packetData);
        } catch (RuntimeException e) {
            // TODO: error handler
            return "error";
        }

        return "ok";
    }
}
