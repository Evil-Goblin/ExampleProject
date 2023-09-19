package pg.packet_generator.domain.packet.column;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pg.packet_generator.domain.packet.Packet;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @Type(value = ColumnByte.class, name = "byte"),
        @Type(value = ColumnShort.class, name = "short"),
        @Type(value = ColumnInt.class, name = "int"),
        @Type(value = ColumnLong.class, name = "long"),
        @Type(value = ColumnByteArray.class, name = "bytearray"),
        @Type(value = ColumnString.class, name = "string"),
})
public interface PacketColumn extends Packet {

    Object getData();

    void increaseData();
}

