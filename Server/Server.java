package Server;

import Window.AppWindow;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public void startServer(int port) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Server started. Waiting for clients...");

                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client connected: " + socket.getInetAddress());

                    SwingUtilities.invokeLater(() -> {
                        AppWindow window = new AppWindow("Server Chat", socket);
                        window.setVisible(true);
                        window.appendText("Client connected: " + socket.getInetAddress());
                    });
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
