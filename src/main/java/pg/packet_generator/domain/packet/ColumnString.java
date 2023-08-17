package pg.packet_generator.domain.packet;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ColumnString implements PacketColumn {

    private String data;

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public int getSize() {
        return data.length();
    }
}
