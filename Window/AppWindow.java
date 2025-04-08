package Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppWindow extends JFrame implements ActionListener {

    private JTextArea textArea;
    private JTextField textField;
    private JButton sendButton;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ImageIcon icon = new ImageIcon("./Res/icon.png");

    public AppWindow(String title, Socket socket) {
        super(title);
        this.socket = socket;
        setupSocket();
        prepareWindow();
    }

    private void setupSocket() {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
        }
        new ReceiverThread().start();
    }

    private void prepareWindow() {
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        bottomPanel.add(textField, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void appendText(String message) {
        textArea.append(message + "\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendButton) {
            String msg = textField.getText().trim() + "\n" + date();
            if (!msg.isEmpty()) {
                appendText("You : " + msg);
                textField.setText("");
                try {
                    dataOutputStream.writeUTF(msg);
                } catch (Exception exp) {

                }
            }
        }
    }

    private String date() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return "[" + formattedDate + "]";
    }

    class ReceiverThread extends Thread {
        public void run() {
            while (!interrupted()) {
                try {
                    String msg = dataInputStream.readUTF();
                    onMessage(msg);
                } catch (Exception e) {

                }
            }
        }

        private void onMessage(String msg) {
            appendText("Other : " + msg);
            textField.setText("");
        }
    }
}
