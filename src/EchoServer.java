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
//        InputStreamReader isr = new InputStreamReader(input, "UTF-8");
        Scanner scanner = new Scanner(System.in, "UTF-8");


        try (PrintWriter writer = new PrintWriter(socket.getOutputStream());
             InputStreamReader isr = new InputStreamReader(socket.getInputStream()))
        {
            while (true) {
                     Scanner sc = new Scanner(isr);
                     String message1 = scanner.nextLine().strip();
                writer.write(message1);
                writer.write(System.lineSeparator());
                writer.flush();
                String message = sc.nextLine().strip();
                System.out.println(reverse(message));
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

