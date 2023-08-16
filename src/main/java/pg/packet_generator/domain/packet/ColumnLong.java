package pg.packet_generator.domain.packet;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ColumnLong implements PacketColumn {

    private long data;

    @Override
    public Object getData() {
        return data;
    }
}
