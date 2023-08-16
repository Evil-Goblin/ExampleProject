package pg.packet_generator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pg.packet_generator.domain.packet.PacketColumn;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/packet/api")
public class PacketController {

    @PostMapping
    public String generate(@RequestBody List<PacketColumn> packetColumns) {
        for (PacketColumn packetColumn : packetColumns) {
            log.info("[PacketController:generate] {}={}", packetColumn.getClass().getName(), packetColumn.getData());
        }
        return "ok";
    }
}
