package Packages;


/**
 * David Gómez Pérez
 */
public class DataPacket implements  Packet{

    public static final  String HEAD = "03";
    private final String num_secuencia;
    private final String data;
    private final byte [] bytes;
    private int length;

    public DataPacket (String num_secuencia, String data){
        this.num_secuencia = num_secuencia;
        this.data = data;
        this.bytes = (HEAD+num_secuencia+data).getBytes();
        this.length = bytes.length;
    }

    public DataPacket (byte [] bytes,int length){
        this.length = length;
        this.num_secuencia = new String(bytes).substring(2,4);
        this.data = new String(bytes).substring(4);
        //construimos el array de bytes
        char [] b = new char[2+2+data.length()];
        //añadimos numero de secuencia
        b[0] = '0'; b[1] = '3'; b[2] = num_secuencia.charAt(0); b[3] = num_secuencia.charAt(1);
        int i = 4;
        for (int j = 0;j<data.length();j++){
            b[i] = data.charAt(j);
            i++;
        }
        this.bytes = new String(b).getBytes();
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
        return this.data;
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
