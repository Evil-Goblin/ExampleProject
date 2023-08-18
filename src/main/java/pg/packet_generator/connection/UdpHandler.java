package pg.packet_generator.connection;

import pg.packet_generator.domain.Buffer;

import java.io.IOException;
import java.net.*;

public class UdpHandler implements ConnectionHandler {

    private final String host;
    private final int port;

    private InetAddress dest;
    private DatagramSocket sock;

    public UdpHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void init() {
        try {
            dest = InetAddress.getByName(host);
            sock = new DatagramSocket();
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(Buffer buffer) {
        DatagramPacket datagramPacket = new DatagramPacket(buffer.getBuf(), buffer.getOffset(), dest, port);

        try {
            sock.send(datagramPacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
