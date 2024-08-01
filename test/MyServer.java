
package test;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {
    private final int port;
    private final ClientHandler clientHandler;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private boolean stop;

    public MyServer(int port, ClientHandler clientHandler) {
        this.port = port;
        this.clientHandler = clientHandler;
        this.clientSocket = null;
        stop = false;
    }

    public void start() {
        new Thread(() -> { // create a new thread by lambda expression for the server in the background
            try {
                runServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void close() {
        try {
            stop = true; // break the busy loop for the client
        } finally { // make sure everything is closed
            try {
                if (clientSocket != null) {
                    clientSocket.getInputStream().close();
                    clientSocket.getOutputStream().close();
                    clientSocket.close();
                }
                if (serverSocket != null) {
                    serverSocket.close();
                }
            }
            catch (IOException e) {}

        }

    }


    private void runServer() throws Exception {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(1000);
        while (!stop) {
            try {
                clientSocket = serverSocket.accept(); // wait until a client connects to the server, then it creates a new Socket to handle the communication.
                try {
                    clientHandler.handleClient(clientSocket.getInputStream(), clientSocket.getOutputStream());

                    clientSocket.getInputStream().close();
                    clientSocket.getOutputStream().close();
                    clientSocket.close();
                } catch (IOException e) {}

            } catch (SocketTimeoutException e) {}
        }
        serverSocket.close();
    }

}