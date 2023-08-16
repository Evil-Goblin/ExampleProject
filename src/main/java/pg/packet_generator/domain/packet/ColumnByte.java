package pg.packet_generator.domain.packet;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ColumnByte implements PacketColumn {

    private byte data;

    @Override
    public Object getData() {
        return data;
    }
}
