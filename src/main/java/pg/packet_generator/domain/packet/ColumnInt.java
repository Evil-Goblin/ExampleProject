package pg.packet_generator.domain.packet;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ColumnInt implements PacketColumn {

    private int data;
    private int increase;

    @Override
    public Object getData() {
        return data;
    }
}
