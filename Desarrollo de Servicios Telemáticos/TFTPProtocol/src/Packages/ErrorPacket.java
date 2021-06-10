
package Packages;
/**
 * David Gómez Pérez
 */
public class ErrorPacket implements Packet{
    public static final String HEAD = "05";
    private final String message;
    private final byte [] bytes;
    private final int length;

    public  ErrorPacket (String message){
        this.message = message;
        this.bytes = (HEAD+"00"+message).getBytes();
        this.length = bytes.length;
    }

    public ErrorPacket (byte [] bytes, int length){
        this.length = length;
        this.bytes = bytes;
        this.message = new String(bytes).replace(HEAD+"00","");
    }

    @Override
    public byte[] toBytes() {
        return this.bytes;
    }

    @Override
    public String getHEAD() {
        return HEAD;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getAck() {
        return null;
    }

    @Override
    public String getMode() {
        return null;
    }

    @Override
    public String getFileName() {
        return null;
    }

    @Override
    public int size() {
        return  this.length;
    }
}

