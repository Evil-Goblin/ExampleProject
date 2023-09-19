package pg.packet_generator.domain.packet.column;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.RequiredArgsConstructor;
import pg.packet_generator.domain.Buffer;

@RequiredArgsConstructor
public class ColumnByteArray implements PacketColumn {

    @JsonDeserialize(using = ByteDeserializer.class)
    private byte[] data;

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void increaseData() {

    }

    @Override
    public int getSize() {
        return data.length;
    }

    @Override
    public void write(Buffer buffer) {
        buffer.writeBytes(data);
    }
}
