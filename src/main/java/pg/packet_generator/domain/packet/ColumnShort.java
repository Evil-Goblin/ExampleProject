package pg.packet_generator.domain.packet;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ColumnShort implements PacketColumn {

    private short data;
    private int increase;

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public int getSize() {
        return 2;
    }
}
