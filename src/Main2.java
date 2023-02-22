import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        int eleccion;
        do {
            System.out.println("ACCÉS A LA GESTIÓ DE JOCS\n  1. Registre\n  2. Accedir\n  0. Sortir\n ");
            Scanner sc = new Scanner(System.in);
            eleccion = sc.nextInt();
            {
                switch (eleccion) {
                    case 1:
                        registrar();
                        break;
                    case 2:
                        menuAcc();
                        break;
                    case 0:
                        System.out.println("---------------Adios-----------------");
                        break;
                }
            }
        } while (eleccion != 0);
    }
    private static void registrar() {
        Usuario user = new Usuario();
        Scanner sc = new Scanner(System.in);
        user.usuario = comprobarUsuario();
        user.contra = comprobarContra();
        System.out.println("Nombre:");
        user.nombre = sc.nextLine();
        System.out.println("Apellidos:");
        user.apellido = sc.nextLine();
        System.out.println("Cuenta corriente:");
        user.cuenta = sc.nextLine();
        System.out.println("Email:");
        user.email = sc.nextLine();
        addUserTxt(user);
    }

    private static String comprobarContra() {
        String contra;
        String contraRep;
        do{
            System.out.println("Contraseña:");
            Scanner sc = new Scanner(System.in);
            contra = sc.nextLine();
            System.out.println("Repite la contraseña:");
            contraRep = sc.nextLine();
            if(!contra.equals(contraRep)){
                System.out.println("Contraseñas no coinciden");
            }
        }while(!contra.equals(contraRep));
        contra = BlowFish.encrypt(contra);
        return contra;
    }

    private static String comprobarUsuario() {
        Scanner sc = new Scanner(System.in);
        boolean repe = false;
        System.out.println("Nombre de usuario:");
        String usuario = sc.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader("ADMIN/users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String [] parts = line.split(":");
                if (parts[0].equals(usuario)){
                    System.out.println("Usuario repetido");
                    repe = true;
                }
            }
            if (repe == true){
                usuario = comprobarUsuario();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    private static void addUserTxt(Usuario user) {

        try {
            FileWriter fw = new FileWriter("ADMIN/users.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write((user.usuario + ":" + user.contra + ":" + user.nombre + ":" + user.apellido + ":" +user.cuenta + ":" + user.email + ":"+ + user.saldo+"\n"));
            bw.close();
            System.out.println("Usuario guardado");

        } catch (Exception e) {
            System.out.println("Error al guardar el usuario");
        }
  }

    private static Usuario iniciarSesion () {
        Scanner sc = new Scanner(System.in);
        Usuario user = new Usuario();
        boolean cont = true;
        String[] parts = null;
        System.out.println("Nombe de usuario:");
        String usuario = sc.nextLine();
        System.out.println("Contraseña:");
        String contra = sc.nextLine();
        try (BufferedReader reader = new BufferedReader(new FileReader("ADMIN/users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null && cont==true ) {
                parts = line.split(":");
                if (parts[0].equals(usuario)) {
                    if (BlowFish.comparePasswords(contra, parts[1])) {
                        user.usuario = parts[0];
                        user.contra = parts[1];
                        user.nombre = parts[2];
                        user.apellido = parts[3];
                        user.cuenta = parts[4];
                        user.email = parts[5];
                        user.saldo = Float.parseFloat(parts[6]);
                        cont = false;
                    }else{
                        System.out.println("Nobmbre de usuario o contraseña incorrectos");
                        user = iniciarSesion();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }
    private static void menuAcc() {
        Usuario userLog = iniciarSesion();
        int eleccion;
        do {
            System.out.println("JOCS ONLINE\n" + "1. Jugar\n" + "2. Gestionar jocs\n" + "3. Gestionar saldo\n" + "4. Gestionar les dades de l'usuari\n" + "0. Sortida al menú d'entrada\n");
            Scanner sc = new Scanner(System.in);
            eleccion = sc.nextInt();
            {
                switch (eleccion) {
                    case 1:
                        System.out.println(userLog.saldo);

                        break;
                    case 2:
                        gestionarJuegos(userLog);
                        break;
                    case 3:
                        gestionarSaldo(userLog);
                        break;
                    case 4:

                        break;
                    case 0:

                        break;
                }
            }
        } while (eleccion != 0);
    }

    private static void gestionarJuegos(Usuario user) {


        try (BufferedReader reader = new BufferedReader(new FileReader("ADMIN/games.txt"))) {
            ArrayList<Game> games = new ArrayList<Game>();
            String line;
            int i = 1;
            while ((line = reader.readLine()) != null) {
                Game game = new Game();
                String[] parts = line.split(":");
                System.out.println(i+" - "+parts[0]);
                game.nombre = parts[0];
                game.precio = Float.parseFloat(parts[1]);
                game.precioTa = Float.parseFloat(parts[2]);
                games.add(game);
                i++;
            }
            System.out.println("introduce numero :");
            Scanner sc = new Scanner(System.in);
            int eleccion = sc.nextInt();
            menuCompra(games.get((eleccion-1)), user);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void menuCompra(Game gamel, Usuario user) {
        System.out.println(gamel.nombre+"\n"+"Credito: "+gamel.precio+"\n"+"Tarifa: "+gamel.precioTa);
        System.out.println("Comprar creditos (1) o Tarifa plana (2): ");
        Scanner sc = new Scanner(System.in);
        int eleccion = sc.nextInt();
        if (eleccion == 1){
            System.out.println("numero de creditos que quieres ?");
            int numCre = sc.nextInt();
            float saldoRestar = numCre * gamel.precio;
            if(saldoRestar<= user.saldo){
                user.saldo = user.saldo - saldoRestar;
                guardarCambiosJuegos(gamel, user, numCre);
            }else {
                System.out.println("saldo insuficiente");
            }
        }else if(eleccion == 2){


        }
    }

    private static void guardarCambiosJuegos(Game gamel, Usuario user, int numCre) {

        File file = new File("ADMIN/userGame.txt");
        File tempFile = new File("ADMIN/userGame_temp.txt");
        boolean nuevo = true;
        try {
            FileWriter fw = new FileWriter(tempFile);
            FileReader fr = new FileReader(file);
            BufferedWriter bw = new BufferedWriter(fw);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(user.usuario) && parts[1].equals(gamel.nombre)) {
                    int aux = Integer.parseInt(parts[2] + numCre);
                    line = user.usuario + ":" + gamel.nombre + ":" + aux;
                    nuevo = false;
                }else if (parts[0].equals(user.usuario)){
                    bw.write(user.usuario + ":" + gamel.nombre + ":" + numCre);
                    bw.newLine();
                    nuevo = false;
                }
                bw.write(line);
                bw.newLine();
            }
            if (nuevo){
                bw.write(user.usuario + ":" + gamel.nombre + ":" + numCre);
            }
            bw.close();
            br.close();
            System.out.println("Juego añadido al usuario");
        } catch (Exception e) {
            System.out.println("Error al guardar el usuario");
        }
        if (file.delete()) {
            tempFile.renameTo(file);
        } else {
            System.out.println("Error al editar el archivo");
        }


    }

    private static void gestionarSaldo(Usuario userLog) {

        File file = new File("ADMIN/users.txt");
        File tempFile = new File("ADMIN/users_temp.txt");

        System.out.println("Tu saldo es: " + userLog.saldo);
        System.out.println("Cuanto quieres añadir?");
        Scanner sc = new Scanner(System.in);
        int saldoSum = sc.nextInt();
        userLog.saldo = saldoSum + userLog.saldo;


        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(userLog.usuario)) {
                    line = userLog.usuario + ":" + userLog.contra+ ":" + userLog.nombre+ ":" + userLog.apellido+ ":" + userLog.cuenta+ ":" + userLog.email+ ":" + userLog.saldo;
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file.delete()) {
            tempFile.renameTo(file);
        } else {
            System.out.println("Error al editar el archivo");
        }
    }
}
