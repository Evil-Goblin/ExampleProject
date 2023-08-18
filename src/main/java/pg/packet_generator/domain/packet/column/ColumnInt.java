package pg.packet_generator.domain.packet.column;

import lombok.RequiredArgsConstructor;
import pg.packet_generator.domain.Buffer;

@RequiredArgsConstructor
public class ColumnInt implements PacketColumn {

    private int data;
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
        return 4;
    }

    @Override
    public void write(Buffer buffer) {
        buffer.writeInt(data);
    }
}
