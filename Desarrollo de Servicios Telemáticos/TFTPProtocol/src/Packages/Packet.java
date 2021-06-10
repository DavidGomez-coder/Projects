
package Packages;

/**
 * David Gómez Pérez
 */
public interface Packet {

    String RRQ_HEAD = "01";
    String WRQ_HEAD = "02";
    String DATA_HEAD = "03";
    String ACK_HEAD = "04";
    String ERROR_HEAD = "05";

    String RRQ = "RRQ";
    String WRQ = "WRQ";
    String DATA = "DATA";
    String ACK = "ACK";
    String ERROR = "ERROR";

    int ECHOMAX = 512;

    byte[] toBytes();

    String getHEAD();

    String getMessage();

    String getAck();

    String getMode();

    String getFileName();

    int size();

}