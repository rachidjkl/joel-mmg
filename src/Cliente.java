import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        String servidor = "localhost";
        int puerto = 1234;

        try {
            // Crear el socket del cliente y establecer la conexión
            Socket clientSocket = new Socket(servidor, puerto);
            System.out.println("Conexión establecida con el servidor");

            // Crear los streams de entrada y salida
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            // Enviar mensaje al servidor
            String mensaje = "¡Hola servidor!";
            output.println(mensaje);

            // Leer respuesta del servidor
            String respuestaServidor = input.readLine();
            System.out.println("Servidor responde: " + respuestaServidor);

            // Cerrar conexiones
            input.close();
            output.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}