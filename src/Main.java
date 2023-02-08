import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.awt.Color.red;

public class Main {
    public static void main(String[] args) {

        Usuario user = new Usuario();
        ArrayList<Usuario> mmg = new ArrayList<>();
        cargarUsuarios(mmg);
        int eleccion;

        do {
            System.out.println("ACCÉS A LA GESTIÓ DE JOCS\n  1. Registre\n 2. Accedir\n  0. Sortir\n ");
            Scanner sc = new Scanner(System.in);
            eleccion = sc.nextInt();
            {
                switch (eleccion) {
                    case 1:
                        registrar(user, mmg);
                        break;
                    case 2:
                        cargarUsuarios(mmg);
                        break;
                }
            }
        } while (eleccion != 3);
    }

    private static void registrar(Usuario user, ArrayList<Usuario> mmg) {

        boolean contin;
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
            contin = comprovacion(user, mmg);
        } while (contin);
        guardarTxt(user);
    }

    private static boolean comprovacion(Usuario user, ArrayList<Usuario> mmg ) {

        boolean cont = false;
        for (int i=0;i<mmg.size() && cont == false;i++){
            if (user.usuario.equals(mmg.get(i).usuario)) {
                cont = true;
                System.out.println("Usuario existente");
            }
            if (user.email.equals(mmg.get(i).email)) {
                cont = true;
                System.out.println("Email existente");
            }
        }
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

    private static void cargarUsuarios(ArrayList<Usuario> mmg) {

        BufferedReader reader = null;
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
                    mmg.add(user);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}