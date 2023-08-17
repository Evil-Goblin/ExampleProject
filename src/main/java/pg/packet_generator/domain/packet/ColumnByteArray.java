package pg.packet_generator.domain.packet;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ColumnByteArray implements PacketColumn {

    @JsonDeserialize(using = ByteDeserializer.class)
    private byte[] data;

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public int getSize() {
        return data.length;
    }
}
