package pg.packet_generator.domain.packet.column;

import lombok.RequiredArgsConstructor;
import pg.packet_generator.domain.Buffer;

@RequiredArgsConstructor
public class ColumnShort implements PacketColumn {

    private short data;
    private int increase;

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void increaseData() {
        data += (short) increase;
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public void write(Buffer buffer) {
        buffer.writeShort(data);
    }
}
