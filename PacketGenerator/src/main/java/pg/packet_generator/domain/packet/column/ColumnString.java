package pg.packet_generator.domain.packet.column;

import lombok.RequiredArgsConstructor;
import pg.packet_generator.domain.Buffer;

@RequiredArgsConstructor
public class ColumnString implements PacketColumn {

    private String data;

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void increaseData() {

    }

    @Override
    public int getSize() {
        return data.length();
    }

    @Override
    public void write(Buffer buffer) {
        buffer.writeString(data);
    }
}
