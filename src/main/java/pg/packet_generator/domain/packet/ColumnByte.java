package pg.packet_generator.domain.packet;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ColumnByte implements PacketColumn {

    private byte data;
    private int increase;

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public int getSize() {
        return 1;
    }
}
