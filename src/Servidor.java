import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 1234;

        try {
            // Crear el socket del servidor
            ServerSocket serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor escuchando en el puerto " + puerto);

            // Esperar a que un cliente se conecte
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());

            // Crear los streams de entrada y salida
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            // Leer datos del cliente y enviar respuesta
            String mensajeCliente = input.readLine();
            System.out.println("Cliente dice: " + mensajeCliente);

            String respuesta = "Hola cliente, ¡conexión establecida!";
            output.println(respuesta);

            // Cerrar conexiones
            input.close();
            output.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}