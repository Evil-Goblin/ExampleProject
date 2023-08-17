package pg.packet_generator.connection;

public class UdpHandler implements ConnectionHandler {

    private final String host;
    private final int port;

    public UdpHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

}
