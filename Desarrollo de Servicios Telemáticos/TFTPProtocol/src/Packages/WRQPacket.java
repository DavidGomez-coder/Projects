
package Packages;
/**
 * David Gómez Pérez
 */
public class WRQPacket implements  Packet{

    private static final String HEAD = "02";
    private final byte [] bytes;
    private final String fileName;
    private final String mode;
    private int length;

    public WRQPacket (String fileName, String mode){
        this.fileName = fileName;
        this.mode = mode;
        this.bytes = (HEAD+fileName+"0"+mode+"0").getBytes();
        this.length = bytes.length;
    }

    public WRQPacket (byte [] bytes, int length){
        this.length = length;
        this.bytes = bytes;
        this.fileName = extractFilename(bytes);
        this.mode = extractMode(bytes);
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
        return null;
    }

    @Override
    public String getAck() {
        return null;
    }

    @Override
    public String getMode() {
        return this.mode;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public int size() {
        return this.length;
    }

    /** MÉTODOS PRIVADOS */
    private String extractFilename (byte [] b){
        String str = new String(b);
        String str_aux = str.replace(HEAD,"");
        String res = "";
        int i = 0;
        while (i<str_aux.length() && str_aux.charAt(i)!='0') {
            res += str_aux.charAt(i);
            i++;
        }
        return res;
    }

    private String extractMode (byte [] b){
        String f = extractFilename(b);
        String bs = new String(b).replace(HEAD,"").replace(f,"");
        return bs.substring(1,bs.length()-1);
    }
}

