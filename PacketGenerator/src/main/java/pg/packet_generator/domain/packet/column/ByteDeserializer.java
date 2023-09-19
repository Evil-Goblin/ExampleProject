package pg.packet_generator.domain.packet.column;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class ByteDeserializer extends JsonDeserializer<byte[]> {
    @Override
    public byte[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return stringToBytes(p.getText());
    }

    private byte[] stringToBytes(String source) {
        byte[] bytes = new byte[source.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(source.substring(2*i, 2*i+2), 16);
        }
        return bytes;
    }
}
