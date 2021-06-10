/**
 * David Gómez Pérez
 */
package Packages;

public class AckPacket implements Packet{
    private static final String HEAD = "04";
    private final String num_secuencia;
    private final byte[] bytes;
    private int length;

    public  AckPacket (String num_secuencia){
        this.num_secuencia = num_secuencia;
        this.bytes = (HEAD+num_secuencia).getBytes();
        this.length = bytes.length;
    }

    public AckPacket (byte [] bytes, int length){
        this.length = length;
        this.bytes = bytes;
        this.num_secuencia = new String(bytes).replace(HEAD,"");
    }

    @Override
    public byte[] toBytes() {
        return  this.bytes;
    }

    @Override
    public String getHEAD() {
        return HEAD;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public String getAck() {
        return this.num_secuencia;
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
        return this.length;
    }
}
