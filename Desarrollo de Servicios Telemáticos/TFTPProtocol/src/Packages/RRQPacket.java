
package Packages;

/**
 * David Gómez Pérez
 */
public class RRQPacket implements Packet{

    public static final String HEAD = "01";
    private final String fileName;
    private final String mode;
    private final byte [] bytes;
    private int length;

    public RRQPacket(String fileName, String mode){
        this.fileName = fileName;
        this.mode = mode;
        this.bytes = (HEAD+fileName+"0"+mode+"0").getBytes();
        this.length = bytes.length;
    }

    public RRQPacket (byte [] bytes, int length){
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
        return this.HEAD;
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
