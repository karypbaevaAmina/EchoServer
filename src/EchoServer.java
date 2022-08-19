import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoServer {

    private final int port;

    private EchoServer(int port) {
        this.port = port;
    }

    public static EchoServer bindToPort(int port) {
        return new EchoServer(port);
    }


    public void run() {
        try (var server = new ServerSocket(port)) {
            // обработка подключения

            try (var clientSocket = server.accept()) {
                handle(clientSocket);
            }
        } catch (IOException e) {
            System.out.printf("Probably port %s is busy.%n", port);
            e.printStackTrace();
        }
    }

    private void handle(Socket socket) throws IOException {
        // логика обработки
        InputStream input = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(input, "UTF-8");
        Scanner sc = new Scanner(isr);
        PrintWriter writer = new PrintWriter(socket.getOutputStream());

        try (sc) {
            while (true) {
                String message = sc.nextLine().strip();
                System.out.println(reverse(message));
                writer.write(message);
                writer.write(System.lineSeparator());
                writer.flush();

                if ("bye".equalsIgnoreCase(message)) {
                    System.out.printf("Bye bye!%n");
                    return;
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Client dropped the connection!");
        }
    }



    public static String reverse (String message){
        StringBuilder rez = new StringBuilder();
        for (int i = 0, j = message.length() - 1; i <= message.length() - 1; ++i, --j) {
            rez.append(message.charAt(j));
        }
        return rez.toString();
    }

}

