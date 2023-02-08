import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Usuario user = new Usuario();
        int eleccion;


        do {
            System.out.println("ACCÉS A LA GESTIÓ DE JOCS\n  1. Registre\n 2. Accedir\n  0. Sortir\n ");
            Scanner sc = new Scanner(System.in);
            eleccion = sc.nextInt();
            {
                switch (eleccion) {
                    case 1:
                        registrar(user);
                        break;
                    case 2:
                        cargarUsuarios();
                        break;
                }

            }

        }while (eleccion != 3);

    }

    private static void registrar(Usuario user) {
        boolean contin = true;
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
            contin = comprovacion(user);
        }while(contin);
        guardarTxt(user);

    }

    private static boolean comprovacion(Usuario user) {
        boolean cont = false;
        if(user.usuario == "mmg"){
            cont = true;
        }
        System.out.println("mmg");
    return cont;
    }
    private static void guardarTxt(Usuario user) {
        try {
            FileWriter writer = new FileWriter("ADMIN/users.txt", true);
            writer.write(user.usuario + ":" + user.contra + ":" + user.nombre + ":" + user.apellido + ":" + "ES" + user.cuenta + ":" + user.email + ":0" + "\n");
            writer.close();
            System.out.println("Usuario guardado");
        } catch (Exception e) {
            System.out.println("Error al guardar el usuario");
        }
    }

    private static void cargarUsuarios() {

        BufferedReader reader = null;

        ArrayList<Usuario> mmg = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader("ADMIN/users.txt"));
            String line;
            File f = new File("ADMIN/users.txt");
            String absolute = f.getAbsolutePath();
            FileReader fr = new FileReader(absolute);
            BufferedReader br = new BufferedReader(fr);
            int mmgg = (int) br.lines().count();


            for (int i = 0; i < mmgg; i++) {
                while ((line = reader.readLine()) != null) {
                    Usuario user = new Usuario();
                    String[] fields = line.split(":");
                    user.usuario = fields[0];
                    user.contra = fields[1];
                    user.nombre = fields[2];
                    user.apellido = fields[3];
                    user.cuenta = fields[4];
                    user.email = fields[5];
                    mmg.add(user);

                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}