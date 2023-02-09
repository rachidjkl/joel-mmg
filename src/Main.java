import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Usuario user = new Usuario();
        ArrayList<Usuario> listUser = new ArrayList<>();
        cargarUsuarios(listUser);
        int eleccion;

        do {
            System.out.println("ACCÉS A LA GESTIÓ DE JOCS\n  1. Registre\n  2. Accedir\n  0. Sortir\n ");
            Scanner sc = new Scanner(System.in);
            eleccion = sc.nextInt();
            {
                switch (eleccion) {
                    case 1:
                        registrar(listUser);
                        break;
                    case 2:
                        menuAcc(listUser);
                        break;
                    case 0:
                        System.out.println("---------------Adios-----------------");
                        break;
                }
            }
        } while (eleccion != 0);
    }

    private static void menuAcc(ArrayList<Usuario> listUser) {
        int eleccion;
        Usuario userLog = iniciarSesion(listUser);

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
                        gestionarSaldo(userLog, listUser);
                        break;
                    case 4:

                        break;
                    case 0:

                        break;
                }
            }
        } while (eleccion != 0);
    }

    private static Usuario iniciarSesion(ArrayList<Usuario> listUser) {
        boolean log = true;
        Usuario userlog = new Usuario();
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("Introduce tu nombre de usuario");
            String user = sc.nextLine();
            System.out.println("Introduce tu contraseña");
            String contra = sc.nextLine();
            for (int i=0;i<listUser.size();i++){
                if (user.equals(listUser.get(i).usuario)) {
                    if (BlowFish.comparePasswords(contra, listUser.get(i).contra)){
                        userlog = listUser.get(i);
                        log = false;
                    }
                    System.out.println("Usuario o contraseña incorrectos");
                }
            }
        }while (log);
        return userlog;
    }

    private static void gestionarSaldo(Usuario userLog, ArrayList<Usuario> listUser) {

        Scanner sc = new Scanner(System.in);
        int i = 0;
        System.out.println("tu saldo es: "+ userLog.saldo);
        System.out.println("cuantos quieres añadir:" );
        int saldoSumar = sc.nextInt();
        while (i<listUser.size()) {
            if (listUser.get(i).usuario.equals(userLog.usuario)) {
                listUser.get(i).saldo = listUser.get(i).saldo + saldoSumar;
            }
            i++;
        }
        guardarTxt(listUser);

    }

    private static void registrar(ArrayList<Usuario> listUser) {

        boolean contin;
        Usuario user = new Usuario();
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Nombre de usuario:");
            user.usuario = sc.nextLine();
            System.out.println("Contraseña:");
            user.contra = sc.nextLine();
            System.out.println("Repite la contraseña:");
            user.contraRep = sc.nextLine();
            System.out.println("Nombre:");
            user.nombre = sc.nextLine();
            System.out.println("Apellidos:");
            user.apellido = sc.nextLine();
            System.out.println("Cuenta corriente:");
            user.cuenta = sc.nextLine();
            System.out.println("Email:");
            user.email = sc.nextLine();
            contin = comprovacion(user, listUser);
        } while (contin);
        guardarTxt(listUser);
    }

    private static boolean comprovacion(Usuario user, ArrayList<Usuario> listUser ) {

        boolean cont = false;
        if(user.contra == user.contraRep){
            cont = true;
            System.out.println("las contraseñas no coinciden");
        }else {
            user.contra = BlowFish.encrypt(user.contra);
        }
        for (int i = 0; i<listUser.size() && !cont; i++){
            if (user.usuario.equals(listUser.get(i).usuario)) {
                cont = true;
                System.out.println("Usuario existente");
            }
            if (user.email.equals(listUser.get(i).email)) {
                cont = true;
                System.out.println("Email existente");
            }
        }
        listUser.add(user);
        return cont;
    }


    private static void guardarTxt(ArrayList<Usuario> listUser) {

        try {
            FileWriter writer = new FileWriter("ADMIN/users.txt", true);
            FileOutputStream writerr = new FileOutputStream("ADMIN/users.txt");
            writerr.write(new byte[0]);
            writer.close();
            for (int i=0; i< listUser.size(); i++){
                writerr.write((listUser.get(i).usuario + ":" + listUser.get(i).contra + ":" + listUser.get(i).nombre + ":" + listUser.get(i).apellido + ":" + listUser.get(i).cuenta + ":" + listUser.get(i).email + ":"+ + listUser.get(i).saldo+"\n").getBytes());
            }
            writer.close();
            System.out.println("Usuario guardado");

        } catch (Exception e) {
            System.out.println("Error al guardar el usuario");
        }
    }

    private static void cargarUsuarios(ArrayList<Usuario> listUser) {

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("ADMIN/users.txt"));
            String line;
            File f = new File("ADMIN/users.txt");
            String absolute = f.getAbsolutePath();
            FileReader fr = new FileReader(absolute);
            BufferedReader br = new BufferedReader(fr);
            for (int i = 0; i < br.lines().count(); i++) {
                while ((line = reader.readLine()) != null) {
                    Usuario user = new Usuario();
                    String[] fields = line.split(":");
                    user.usuario = fields[0];
                    user.contra = fields[1];
                    user.nombre = fields[2];
                    user.apellido = fields[3];
                    user.cuenta = fields[4];
                    user.email = fields[5];
                    user.saldo = Integer.parseInt(fields[6]);
                    listUser.add(user);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}