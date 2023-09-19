package pg.packet_generator.domain.packet.column;

import lombok.RequiredArgsConstructor;
import pg.packet_generator.domain.Buffer;

@RequiredArgsConstructor
public class ColumnTime implements PacketColumn {

    private long data;
    private boolean fixed = false;

    @Override
    public Object getData() {
        return fixed ? data : System.currentTimeMillis();
    }

    @Override
    public void increaseData() {

    }

    @Override
    public int getSize() {
        return 8;
    }

    @Override
    public void write(Buffer buffer) {
        if (refreshDateTime()) {
            data = System.currentTimeMillis();
        }
        buffer.writeLong(data);
    }

    private boolean refreshDateTime() {
        return !fixed;
    }
}
