package pg.packet_generator.domain.packet.column;

import lombok.RequiredArgsConstructor;
import pg.packet_generator.domain.Buffer;

@RequiredArgsConstructor
public class ColumnLong implements PacketColumn {

    private long data;
    private int increase;

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void increaseData() {
        data += increase;
    }

    @Override
    public int getSize() {
        return 8;
    }

    @Override
    public void write(Buffer buffer) {
        buffer.writeLong(data);
    }
}
