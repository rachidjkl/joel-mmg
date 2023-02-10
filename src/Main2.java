import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
//        int eleccion;
//        do {
//            System.out.println("ACCÉS A LA GESTIÓ DE JOCS\n  1. Registre\n  2. Accedir\n  0. Sortir\n ");
//            Scanner sc = new Scanner(System.in);
//            eleccion = sc.nextInt();
//            {
//                switch (eleccion) {
//                    case 1:
//                        registrar();
//                        break;
//                    case 2:
//                        //menuAcc(listUser);
//                        break;
//                    case 0:
//                        System.out.println("---------------Adios-----------------");
//                        break;
//                }
//            }
//        } while (eleccion != 0);
//    }
//    private static void registrar() {
//
//        boolean contin;
//        Usuario user = new Usuario();
//        Scanner sc = new Scanner(System.in);
//        do {
//            System.out.println("Nombre de usuario:");
//            String usuario = sc.nextLine();
//            user.usuario = comprobarUsuario(usuario);
//            System.out.println("Contraseña:");
//            user.contra = sc.nextLine();
//            System.out.println("Repite la contraseña:");
//            user.contraRep = sc.nextLine();
//            System.out.println("Nombre:");
//            user.nombre = sc.nextLine();
//            System.out.println("Apellidos:");
//            user.apellido = sc.nextLine();
//            System.out.println("Cuenta corriente:");
//            user.cuenta = sc.nextLine();
//            System.out.println("Email:");
//            user.email = sc.nextLine();
//            contin = comprovacion(user, listUser);
//        } while (contin);
//        addUserTxt(user);
//    }
//
//    private static String comprobarUsuario(String usuario) {
//       // user.usuario = fields[0];
//        return "ff";
//    }
//
//    private static void addUserTxt(Usuario user) {
//        try {
//            FileWriter writer = new FileWriter("ADMIN/users.txt", true);
//            FileOutputStream writerr = new FileOutputStream("ADMIN/users.txt");
//            writerr.write((user.usuario + ":" + user.contra + ":" + user.nombre + ":" + user.apellido + ":" +user.cuenta + ":" + user.email + ":"+ + user.saldo+"\n").getBytes());
//            writer.close();
//            System.out.println("Usuario guardado");
//
//        } catch (Exception e) {
//            System.out.println("Error al guardar el usuario");
//        }
  }
}
