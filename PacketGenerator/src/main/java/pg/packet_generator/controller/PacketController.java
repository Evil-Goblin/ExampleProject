package pg.packet_generator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pg.packet_generator.domain.GenerateSpecification;
import pg.packet_generator.domain.packet.Packet;
import pg.packet_generator.domain.sendinfo.SendInformation;
import pg.packet_generator.service.SendService;

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

        Packet packetData = generateSpecification.getPacketData();

        try {
            sendService.execute(generateSpecification);
        } catch (RuntimeException e) {
            // TODO: error handler
            return "error";
        }

        return "ok";
    }
}
