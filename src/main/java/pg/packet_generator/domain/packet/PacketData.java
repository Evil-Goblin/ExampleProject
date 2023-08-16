package pg.packet_generator.domain.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PacketData {
    private List<PacketColumn> header;
    private List<PacketColumn> body;
    private List<PacketColumn> tail;
}
