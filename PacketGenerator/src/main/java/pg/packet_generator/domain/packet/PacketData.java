package pg.packet_generator.domain.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pg.packet_generator.domain.Buffer;
import pg.packet_generator.domain.packet.column.PacketColumn;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PacketData implements Packet {
    private List<PacketColumn> header;
    private List<PacketColumn> body;
    private List<PacketColumn> tail;

    public int getSize() {
        return header.stream().mapToInt(PacketColumn::getSize).sum()
                + body.stream().mapToInt(PacketColumn::getSize).sum()
                + tail.stream().mapToInt(PacketColumn::getSize).sum();
    }

    @Override
    public void write(Buffer buffer) {
        header.forEach((column) -> {
            column.write(buffer);
            column.increaseData();
        });
        body.forEach((column) -> {
            column.write(buffer);
            column.increaseData();
        });
        tail.forEach((column) -> {
            column.write(buffer);
            column.increaseData();
        });
    }
}
