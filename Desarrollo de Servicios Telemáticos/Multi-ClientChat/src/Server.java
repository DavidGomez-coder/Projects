import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * David Gómez Pérez
 * Práctica Chat Multi-Cliente usando paquete NIO de Java
 * Asig.: Desarrollo de Servicios Telemáticos
 */
public class Server {

    private static final int BUFFER_SIZE = 10000;
    private static final int SERVER_PORT = 6543;

    public static void main(String[] args) throws Exception {
        //CREAMOS LOS CANALES TCP
        ServerSocketChannel server = ServerSocketChannel.open();
        ServerSocket server_socket = server.socket();
        //CREAMOS UN SOCKET EN LA MÁQUINA HOST, USANDO EL PUERTO 6543
        SocketAddress server_address = new InetSocketAddress(SERVER_PORT);
        //ENLAZAMOS LOS DOS SOCKETS
        server_socket.bind(server_address);
        //PONEMOS AL CANAL DE SOCKET COMO NO BLOQUEANTE
        server.configureBlocking(false);
        //CREAMOS SELECTOR PARA ATENDER LAS PETICIONES DE LOS CLIENTES
        Selector selector = Selector.open();
        //REGISTRAMOS EL SELECTOR PARA QUE INICIALMENTE SOLO PUEDA ACEPTAR PETICIONES
        server.register(selector, SelectionKey.OP_ACCEPT);
        //CRECIÓN DEL BUFFER
        ByteBuffer serverBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        serverBuffer.clear();
        //INICIAMOS EL BUFFER Y LA LISTA DE CLAVES A NULL
        Iterator<SelectionKey> keys_iterator;
        System.out.println("Servidor iniciado en el puerto " + SERVER_PORT);
        while (true) {
            selector.select();
            keys_iterator = selector.selectedKeys().iterator();
            //ITERAMOS SOBRE LAS CLAVES REGISTRADAS EN EL SELECTOR
            while (keys_iterator.hasNext()) {
                SelectionKey key = (SelectionKey) keys_iterator.next();
                //ELIMINAMOS ESTA CLAVE PARA NO ATENDER DOS VECES SEGUIDAS UNA PETICIÓN DE ESTE CLIENTE
                keys_iterator.remove();
                try {
                    //PETICIÓN DE REGISTRO EN EL SELECTOR
                    if (key.isAcceptable()) {
                        ByteBuffer channelBuffer2;
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        channelBuffer2 = ByteBuffer.allocate(BUFFER_SIZE);
                        System.out.println("Entra cliente <" + client.socket().getInetAddress() + "," + client.socket().getPort() + ">");
                        client.register(selector, SelectionKey.OP_READ, channelBuffer2);

                        //PETICIÓN DE LECTURA
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        //SE HA LEIDO EOF (EL BUFFER SE HA CERRADO), LUEGO EL CLIENTE SE HA SALIDO
                        if (client.read(serverBuffer) == -1) {
                            System.out.println("Sale cliente <" + client.socket().getInetAddress() + "," + client.socket().getPort() + ">");
                            client.close();

                            //ENVIAMOS MENSAJE AL RESTO DE CLIENTES REGISTRADOS
                        } else {
                            //PREPARAMOS BUFFER Y EL ITERADOR DE LOS USUARIOS A ENVIAR EL MENSAJE
                            serverBuffer.flip();
                            Iterator<SelectionKey> selector_iterator = selector.keys().iterator();
                            System.out.println(" > Enviando mensaje a " + (selector.keys().size() - 1) + " usuarios ...");
                            SelectionKey selector_key = null;
                            while (selector_iterator.hasNext()) {
                                selector_key = selector_iterator.next();
                                ByteBuffer buf = (ByteBuffer) selector_key.attachment();
                                //SI EL BUFFER ESTÁ ABIERTO, ENVIAMOS, EN OTRO CASO, NO HACEMOS NADA
                                if (buf != null) {
                                    buf.put(serverBuffer);
                                    //AL CLIENTE LE INTERESA ESCRIBIR Y LEER
                                    selector_key.interestOps(SelectionKey.OP_WRITE | SelectionKey.OP_READ);
                                    serverBuffer.rewind();
                                }
                            }
                            //LIMPIAMOS BUFFER
                            serverBuffer.clear();

                        }

                        //PETICIÓN DE ESCRITURA
                    } else if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        buffer.flip();
                        buffer.rewind();
                        client.write(buffer);
                        if (buffer.hasRemaining()) {
                            buffer.compact();
                        } else {
                            buffer.clear();
                            key.interestOps(SelectionKey.OP_READ);
                        }
                    }

                    //EN CASO DE UNA EXCEPCIÓN, ELIMINAMOS ESTA CLAVE DEL SELECTOR
                } catch (Exception e) {
                    SocketChannel client = (SocketChannel) key.channel();
                    System.out.println("Sale cliente <" + client.socket().getInetAddress() + "," + client.socket().getPort() + ">");
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (Exception ce) {
                        System.out.println(ce.getLocalizedMessage());
                    }
                }
            }
        }
    }
}