package pg.packet_generator.domain.packet;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ColumnTIme implements PacketColumn {

    private long data;
    private boolean fixed = false;

    @Override
    public Object getData() {
        return fixed ? data : System.currentTimeMillis();
    }
}
