package Window;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Client.Client;
import Server.Server;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartWindow extends JFrame implements ActionListener {
    JButton serverButton;
    JButton clientButton;
    JLabel ipLabel;
    JTextField ipTextField;
    JTextField portTextField;

    public StartWindow() {

        ipLabel = new JLabel();
        ipLabel.setText("IP address/port :");
        ipLabel.setFont(new Font(getName(), Font.PLAIN, 16));
        ipLabel.setBounds(25, 50, 125, 50);

        ipTextField = new JTextField();
        ipTextField.setFont(new Font(getName(), Font.PLAIN, 16));
        ipTextField.setText("localhost");
        ipTextField.setBounds(150, 50, 125, 50);

        portTextField = new JTextField();
        portTextField.setFont(new Font(getName(), Font.PLAIN, 16));
        portTextField.setText("9000");
        portTextField.setBounds(290, 50, 75, 50);

        serverButton = new JButton();
        serverButton.setBounds(200, 125, 100, 50);
        serverButton.addActionListener(this);
        serverButton.setText("Host");
        serverButton.setFocusable(false);

        clientButton = new JButton();
        clientButton.setBounds(200, 200, 100, 50);
        clientButton.addActionListener(this);
        clientButton.setText("Join");
        clientButton.setFocusable(false);

        ImageIcon icon = new ImageIcon("./Res/icon.png");

        this.setTitle("TCP chat");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(icon.getImage());
        this.setSize(500, 500);
        this.setResizable(false);
        this.setLayout(null);

        this.add(ipLabel);
        this.add(ipTextField);
        this.add(portTextField);
        this.add(serverButton);
        this.add(clientButton);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == serverButton) {
            this.dispose();
            int port = Integer.parseInt(portTextField.getText().trim());

            Server server = new Server();
            server.startServer(port);
        }

        if (e.getSource() == clientButton) {
            this.dispose();
            String ip = ipTextField.getText().trim();
            int port = Integer.parseInt(portTextField.getText().trim());

            Client client = new Client();
            client.startClient(ip, port);
        }
    }
}
