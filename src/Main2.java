import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
import java.util.ArrayList;

public class Main2 {
    public static void main(String[] args) {
        Menus.menu();
    }
    static void registrar() {
        Usuario user = new Usuario();
        user.usuario = comprobarUsuario();
        user.contra = comprobarContra();
        System.out.println("Nombre:");
        user.nombre = Keyboard.readString();
        System.out.println("Apellidos:");
        user.apellido = Keyboard.readString();
        System.out.println("Cuenta corriente:");
        user.cuenta = Keyboard.readString();
        System.out.println("Email:");
        user.email = Keyboard.readString();
        InfoFichero.addUserTxt(user);
    }

    private static String comprobarContra() {
        String contra;
        String contraRep;
        do{
            System.out.println("Contraseña:");
            contra = Keyboard.readString();
            System.out.println("Repite la contraseña:");
            contraRep = Keyboard.readString();
            if(!contra.equals(contraRep)){
                System.out.println("Contraseñas no coinciden");
            }
        }while(!contra.equals(contraRep));
        contra = BCrypt.hashpw(contra, BCrypt.gensalt(12));
        return contra;
    }

    private static String comprobarUsuario() {
        boolean repe = false;
        System.out.println("Nombre de usuario:");
        String usuario = Keyboard.readString();

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

    static Usuario iniciarSesion() {
        Usuario user = new Usuario();
        boolean cont = true;
        String[] parts = null;
        System.out.println("Nombe de usuario:");
        String usuario = Keyboard.readString();
        System.out.println("Contraseña:");
        String contra = Keyboard.readString();
        try (BufferedReader reader = new BufferedReader(new FileReader("ADMIN/users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null && cont==true ) {
                parts = line.split(":");
                if (parts[0].equals(usuario)) {
                    if (BCrypt.checkpw(contra, parts[1])) {
                        user.usuario = parts[0];
                        user.contra = parts[1];
                        user.nombre = parts[2];
                        user.apellido = parts[3];
                        user.cuenta = parts[4];
                        user.email = parts[5];
                        user.saldo = Float.parseFloat(parts[6]);
                        cont = false;
                    }
                }
            }
            if (cont){
                System.out.println("Nobmbre de usuario o contraseña incorrectos");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    static void gestionarSaldo(Usuario userLog) {
            System.out.println("Retirar(1) o Añadir(2)");
            int eleccion = Keyboard.readInt();
            if(eleccion == 1){
                retirar(userLog);
            }else if (eleccion == 2){
                System.out.println("Tu saldo es: " + userLog.saldo);
                System.out.println("Cuanto quieres añadir?");
                int saldoSum = Keyboard.readInt();
                if (saldoSum>0){
                    userLog.saldo = saldoSum + userLog.saldo;
                    InfoFichero.actualizarUser(userLog, userLog);
                }else{
                    System.out.println("Valor no valido");
                }
            }else{
                System.out.println("Valor no valido");
            }
    }

    private static void retirar(Usuario userLog) {
        System.out.println("Tu saldo es "+ userLog.saldo);
        System.out.println("cuanto quieres retirar:");
        int retirar = Keyboard.readInt();
        if (userLog.saldo>=retirar && retirar > 0){
            userLog.saldo = userLog.saldo - retirar;
            InfoFichero.actualizarUser(userLog, userLog);
        }else{
            System.out.println("Valor no valido");
        }
    }

    static void cambiarAtributos(Usuario user){
        boolean next = false;
        Usuario aux = new Usuario();
        aux.usuario = user.usuario;
        aux.contra = user.contra;
        aux.nombre = user.nombre;
        aux.apellido = user.apellido;
        aux.email = user.email;
        aux.cuenta = user.cuenta;
        while(!next){
            System.out.println("Que quieres cambiar?");
            System.out.println("1-Nombre de usuario: "+aux.usuario);
            System.out.println("2-Contraseña");
            System.out.println("3-Nombre: "+aux.nombre);
            System.out.println("4-Apellidos: "+aux.apellido);
            System.out.println("5-email: "+aux.email);
            System.out.println("6-cuenta");
            System.out.println("0-salir");
            int eleccion = Keyboard.readInt();
            switch (eleccion){
                case 1:
                    aux.usuario = comprobarUsuario();
                    break;
                case 2:
                    aux.contra = comprobarContra();
                    break;
                case 3:
                    System.out.println("Nuevo nombre: ");
                    String auxx = Keyboard.readString();
                    aux.nombre = auxx;
                    break;
                case 4:
                    System.out.println("Nuevo apellido: ");
                    auxx = Keyboard.readString();
                    aux.apellido = auxx;
                    break;
                case 5:
                    System.out.println("Nuevo email: ");
                    auxx = Keyboard.readString();
                    aux.email = auxx;
                    break;
                case 6:
                    System.out.println("Nueva cuenta: ");
                    auxx = Keyboard.readString();
                    aux.cuenta = auxx;
                    break;
                case 0:
                    next = true;
                    System.out.println("adios");
            }
            InfoFichero.actualizarUser(user, aux);
        }
    }
}
