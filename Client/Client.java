package Client;

import Window.AppWindow;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.net.Socket;

public class Client {

    public void startClient(String host, int port) {
        new Thread(() -> {
            try {
                Socket socket = new Socket(host, port);
                System.out.println("Connected to server at " + host + ":" + port);

                SwingUtilities.invokeLater(() -> {
                    AppWindow clientWindow = new AppWindow("Client Chat", socket);
                    clientWindow.setVisible(true);
                    clientWindow.appendText("Connected to server at " + host + ":" + port);
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
