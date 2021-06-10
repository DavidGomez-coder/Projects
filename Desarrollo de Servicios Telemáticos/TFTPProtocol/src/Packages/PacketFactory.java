
package Packages;
/**
 * David Gómez Pérez
 */
public class PacketFactory {

    /**
     * Devueve un paquete el tipo "packet" con sus argumentos (pueden ser vacíos)
     * El parámetro "data", se utiliza también para crear el mensaje de error
     */
    public Packet  newPacket (String packet, String mode, String fileName, String ack, String data){
        Packet p = null;
        if (packet.equals("RRQ")){
            p = new RRQPacket(fileName, mode);
        }else if (packet.equals("WRQ")){
            p =  new WRQPacket(fileName,mode);
        }else if (packet.equals("DATA")){
            p = new DataPacket(ack,data);
        }else if (packet.equals("ACK")){
            p = new AckPacket(ack);
        }else if (packet.equals("ERROR")){
            p =  new ErrorPacket(data);
        }
        return p;
    }

    /**
     * Devuelve un paquete según el tipo, pasando como parámetro un array de bytes
     */
    public Packet newPacket (byte [] bytes, int length){
        String packet = getPacket(bytes);
        Packet p = null;
        if (packet.equals("RRQ")){
            p = new RRQPacket(bytes,length);
        }else if (packet.equals("WRQ")){
            p =  new WRQPacket(bytes,length);
        }else if (packet.equals("DATA")){
            p =  new DataPacket(bytes, length);
        }else if (packet.equals("ACK")){
            p = new AckPacket(bytes,length);
        }else if (packet.equals("ERROR")){
            p =  new ErrorPacket(bytes,length);
        }
        return  p;
    }

    private String getPacket (byte [] bytes){
        String head = new String(bytes).substring(0,2);
        String p = "";
        if (head.equals("01")){
            p =   "RRQ";
        }else if (head.equals("02")){
            p =  "WRQ";
        }else if (head.equals("03")){
            p =   "DATA";
        }else if (head.equals("04")){
            p =  "ACK";
        }else if (head.equals("05")){
            p = "ERROR";
        }
        return p;
    }
}
