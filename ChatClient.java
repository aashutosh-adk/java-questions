package socket;

import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            System.out.println("Connected to server.");

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // Thread to receive messages from server
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while (!(message = dis.readUTF()).equalsIgnoreCase("exit")) {
                        System.out.println("Server: " + message);
                    }
                    System.out.println("Server has exited.");
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            });

            // Thread to send messages to server
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
            System.out.println("Client socket closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

