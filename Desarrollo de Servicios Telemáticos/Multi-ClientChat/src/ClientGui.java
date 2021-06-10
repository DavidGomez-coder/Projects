import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * David Gómez Pérez
 * Práctica Chat Multi-Cliente usando paquete NIO de Java
 * Asig.: Desarrollo de Servicios Telemáticos
 */
public class ClientGui {
    private static final int BUFFER_SIZE = 1000;

    private JPanel mainPanel;
    private JPanel south_panel;
    private JTextField text_enter;
    private JButton send_button;
    private JPanel east_panel;
    private JLabel ip_label;
    private JTextField ip_field;
    private JLabel port_label;
    private JTextField port_field;
    private JLabel user_lab;
    private JTextField user_field;
    private JButton connect_button;
    private JPanel north_panel;
    private JLabel status_label;
    private JPanel center_panel;
    private JTextArea textPanel;
    private JLabel not_label;
    private JButton exit_button;

    /* SOCKETS */
    private SocketChannel socket;
    private int server_port;
    private String server_IP;
    private String username;
    private ByteBuffer buffer;

    public ClientGui() {
        this.ip_field.setText("LOCALHOST");
        this.port_field.setText("6543");

        this.status_label.setForeground(Color.RED);
        this.status_label.setText(" Status : Sin conexión");
        this.send_button.setEnabled(false);

        this.textPanel.setEditable(false);
        this.textPanel.setLineWrap(true);
        this.textPanel.setWrapStyleWord(true);
        this.textPanel.setForeground(Color.BLUE);

        this.text_enter.setEnabled(false);
        this.exit_button.setEnabled(false);

        connect_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    try {
                        server_port = Integer.parseInt(port_field.getText());
                        server_IP = ip_field.getText();
                        username = user_field.getText();
                        if (username == null || username.equals("")) {
                            not_label.setForeground(Color.RED);
                            not_label.setText(" !!: Introducir un nombre de usuario");
                        } else {
                            socket = SocketChannel.open(new InetSocketAddress(server_IP, server_port));
                            //buffer = ByteBuffer.allocate(BUFFER_SIZE)
                            status_label.setForeground(Color.GREEN);
                            status_label.setText(" Status : Conexión establecida");
                            send_button.setEnabled(true);
                            text_enter.setEnabled(true);
                            connect_button.setEnabled(false);
                            exit_button.setEnabled(true);
                            ip_field.setEnabled(false);
                            port_field.setEnabled(false);
                            user_field.setEnabled(false);
                            not_label.setText("");
                        }
                    } catch (Exception ex) {
                        not_label.setForeground(Color.RED);
                        not_label.setText(" !!: Introducir dirección IP y puerto de conexión válidos");
                    }
                }
            }
        });
        exit_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    //cerramos cliente
                    try {
                        socket.close();
                        buffer = null;
                        send_button.setEnabled(false);
                        text_enter.setEnabled(false);
                        connect_button.setEnabled(true);
                        exit_button.setEnabled(false);
                        ip_field.setEnabled(true);
                        port_field.setEnabled(true);
                        user_field.setEnabled(true);
                        not_label.setText("");
                        status_label.setForeground(Color.RED);
                        status_label.setText("Status : Sin conexión");
                    } catch (Exception ex) {

                    }
                }
            }
        });
        send_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    //enviamos mensaje
                    String msg = username + ": " + text_enter.getText();
                    try {
                        buffer = ByteBuffer.wrap(msg.getBytes());
                        //buffer.clear();
                        socket.write(buffer);
                        text_enter.setText("");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        ClientGui cg = new ClientGui();
        JFrame window = new JFrame("CLIENTE NIO");
        window.setBounds(100, 100, 500, 350);
        window.setContentPane(cg.mainPanel);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        cg.receiveMess();
    }

    public void receiveMess() {
        while (true) {
            try {
                buffer = ByteBuffer.allocate(BUFFER_SIZE);
                buffer.clear();
                socket.read(buffer);
                String mensaje = new String(buffer.array()).trim();
                this.textPanel.append(mensaje + System.lineSeparator());
            } catch (Exception ie) {
                //connected = false;
            }
        }
    }

}
