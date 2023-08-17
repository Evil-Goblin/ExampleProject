package pg.packet_generator.domain.sendinfo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pg.packet_generator.connection.ConnectionHandler;
import pg.packet_generator.domain.PacketSender;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({@Type(value = SendUdpInfo.class, name = "udp")})
public interface SendInformation {

    ConnectionHandler getConnectionHandler();

    void executeIteratedJop(PacketSender sender);
}
