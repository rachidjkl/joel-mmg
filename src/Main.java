import java.io.*;
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

    private static void ShowGames() {
        BufferedReader reader = null;
        Usuario user = new Usuario();

        try {
            reader = new BufferedReader(new FileReader("admin/user.txt"));
            String line;
            File f = new File("admin/user.txt");
            String absolute = f.getAbsolutePath();
            FileReader fr = new FileReader(absolute);
            BufferedReader br = new BufferedReader(fr);

            while (br.readLine() != null) {
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(":");
                    user.usuario = fields[0];
                    user.contra = fields[1];
                    user.nombre = fields[2];
                    user.apellido = fields[3];
                    user.cuenta = fields[4];
                    user.contra = fields[5];

                    Frase = (Separador + GameName + PriceG + PriceTP + Cash);
                    System.out.println(Frase);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}