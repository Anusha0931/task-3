import java.io.*;
import java.net.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            new Thread(new ReceiveMessages(socket)).start();

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String msg;
            while ((msg = reader.readLine()) != null) {
                writer.println(msg);
                if (msg.equalsIgnoreCase("exit")) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ReceiveMessages implements Runnable {
    private Socket socket;

    public ReceiveMessages(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String msg;
            while ((msg = reader.readLine()) != null) {
                System.out.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
