import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
                        menuAcc(iniciarSesion());
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

    private static String[] iniciarSesion () {
        Scanner sc = new Scanner(System.in);
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
                        cont = false;
                    }else{
                        System.out.println("Nobmbre de usuario o contraseña incorrectos");
                        parts = iniciarSesion();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parts;
    }
    private static void menuAcc(String [] userLog) {
        int eleccion;
        do {
            System.out.println("JOCS ONLINE\n" + "1. Jugar\n" + "2. Gestionar jocs\n" + "3. Gestionar saldo\n" + "4. Gestionar les dades de l'usuari\n" + "0. Sortida al menú d'entrada\n");
            Scanner sc = new Scanner(System.in);
            eleccion = sc.nextInt();
            {
                switch (eleccion) {
                    case 1:

                        break;
                    case 2:

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
    private static void gestionarSaldo(String[] userLog) {

        File file = new File("ADMIN/users.txt");
        File tempFile = new File("ADMIN/users_temp.txt");

        System.out.println("Tu saldo es: " + userLog[6]);
        System.out.println("Cuanto quieres añadir?");
        Scanner sc = new Scanner(System.in);
        int saldoSum = sc.nextInt();
        saldoSum = saldoSum + Integer.parseInt(userLog[6]);


        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(userLog[0])) {
                    line = userLog[0] + ":" + userLog[1]+ ":" + userLog[2]+ ":" + userLog[3]+ ":" + userLog[4]+ ":" + userLog[5]+ ":" + saldoSum;
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
