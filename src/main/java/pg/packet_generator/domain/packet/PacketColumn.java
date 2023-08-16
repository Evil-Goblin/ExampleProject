package pg.packet_generator.domain.packet;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
public interface PacketColumn {

    Object getData();
}

