package pg.packet_generator.domain.packet.column;

import lombok.RequiredArgsConstructor;
import pg.packet_generator.domain.Buffer;

@RequiredArgsConstructor
public class ColumnByte implements PacketColumn {

    private byte data;
    private int increase;

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void increaseData() {
        data += (byte) increase;
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public void write(Buffer buffer) {
        buffer.writeByte(data);
    }


}
