package socket;

import java.io.*;
import java.net.*;

public class ChatServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started. Waiting for client...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // Thread to receive messages from client
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while (!(message = dis.readUTF()).equalsIgnoreCase("exit")) {
                        System.out.println("Client: " + message);
                    }
                    System.out.println("Client has exited.");
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            });

            // Thread to send messages to client
            Thread sendThread = new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                    String message;
                    while (true) {
                        message = reader.readLine();
                        dos.writeUTF(message);
                        dos.flush();
                        if (message.equalsIgnoreCase("exit")) break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            receiveThread.start();
            sendThread.start();

            receiveThread.join();
            socket.close();
            System.out.println("Server socket closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
