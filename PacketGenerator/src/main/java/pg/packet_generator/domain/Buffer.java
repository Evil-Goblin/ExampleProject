package pg.packet_generator.domain;

import lombok.Getter;

@Getter
public class Buffer {
    private final byte[] buf;
    private int offset;

    public Buffer(int bufferSize) {
        this.buf = new byte[bufferSize];
    }

    public Buffer setIndex(int index) {
        offset = index;
        return this;
    }

    public Buffer writeByte(byte value) {
        buf[offset++] = value;
        return this;
    }

    public Buffer writeShort(short value) {
        buf[offset++] = (byte) ((value >> 8) & 0xff);
        buf[offset++] = (byte) (value & 0xff);
        return this;
    }

    public Buffer writeInt(int value) {
        buf[offset++] = (byte) ((value >> 24) & 0xff);
        buf[offset++] = (byte) ((value >> 16) & 0xff);
        buf[offset++] = (byte) ((value >> 8) & 0xff);
        buf[offset++] = (byte) (value & 0xff);
        return this;
    }

    public Buffer writeLong(long value) {
        buf[offset++] = (byte) ((value >> 56) & 0xff);
        buf[offset++] = (byte) ((value >> 48) & 0xff);
        buf[offset++] = (byte) ((value >> 40) & 0xff);
        buf[offset++] = (byte) ((value >> 32) & 0xff);
        buf[offset++] = (byte) ((value >> 24) & 0xff);
        buf[offset++] = (byte) ((value >> 16) & 0xff);
        buf[offset++] = (byte) ((value >> 8) & 0xff);
        buf[offset++] = (byte) (value & 0xff);
        return this;
    }

    public Buffer writeBytes(byte[] value) {
        System.arraycopy(value, 0, buf, offset, value.length);
        offset += value.length;
        return this;
    }

    public Buffer writeString(String value) {
        byte[] bytes = value.getBytes();
        return writeBytes(bytes);
    }
}
