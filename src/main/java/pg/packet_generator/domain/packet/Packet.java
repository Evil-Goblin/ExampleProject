package pg.packet_generator.domain.packet;

import pg.packet_generator.domain.Buffer;

public interface Packet {

    int getSize();

    void write(Buffer buffer);
}
