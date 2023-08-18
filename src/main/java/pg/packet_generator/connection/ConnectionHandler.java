package pg.packet_generator.connection;

import pg.packet_generator.domain.Buffer;

public interface ConnectionHandler {

    void init();
    void send(Buffer buffer);
}
