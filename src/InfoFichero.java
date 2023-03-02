import java.io.*;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class InfoFichero {
    static void gestionarJuegos(Usuario user) {
        ArrayList<Game> games = mostrarJuegos();
        System.out.println("introduce numero :");
        int eleccion = Keyboard.readInt();
        Menus.menuCompra(games.get((eleccion-1)), user);
    }
    static void addUserTxt(Usuario user) {

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
    static void guardarTarifaJuegos(Game gamel, Usuario user) {

        File file = new File("ADMIN/userGame.txt");
        File tempFile = new File("ADMIN/userGame_temp.txt");
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaComoCadena = fechaActual.format(formateador);
        try {
            FileWriter fw = new FileWriter(tempFile);
            FileReader fr = new FileReader(file);
            BufferedWriter bw = new BufferedWriter(fw);
            BufferedReader br = new BufferedReader(fr);
            boolean escrita = false;

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(user.usuario) && parts[1].equals(gamel.nombre) && parts[3].equals("S")) {
                    line = user.usuario + ":" + gamel.nombre + ":" + parts[2] +":"+ "T"+":"+fechaComoCadena;
                    escrita = true;
                }else if (parts[0].equals(user.usuario) && parts[1].equals(gamel.nombre) && parts[3].equals("T")){
                    System.out.println("Tienes targeta plana");
                    user.saldo = user.saldo + gamel.precioTa;
                    escrita = true;
                }
                bw.write(line);
                bw.newLine();
            }
            if (!escrita){
                bw.write(user.usuario + ":" + gamel.nombre + ":" + "0" +":"+ "T"+":"+fechaComoCadena);
            }
            bw.close();
            br.close();
        }catch (Exception e) {
            System.out.println("Error al guardar el usuario");
        }
        if (file.delete()) {
            tempFile.renameTo(file);
        } else {
            System.out.println("Error al editar el archivo");
        }

    }
    static void guardarCambiosJuegos(Game gamel, Usuario user, int numCre) {

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
                    int aux = Integer.parseInt(parts[2])+numCre;
                    line = user.usuario + ":" + gamel.nombre + ":" + aux + ":" + parts[3]+":"+ parts[4];
                    nuevo = false;
                }else if (parts[0].equals(user.usuario)){
                    line = (user.usuario + ":" + parts[1] + ":" + numCre+":"+parts[3]+":"+ parts[4]);
                    nuevo = false;
                }
                bw.write(line);
                bw.newLine();
            }
            if (nuevo){
                bw.write(user.usuario + ":" + gamel.nombre + ":" + numCre+":"+"S"+":0");
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

    static void actualizarUser(Usuario userLog, Usuario aux){
        File file = new File("ADMIN/users.txt");
        File tempFile = new File("ADMIN/users_temp.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(userLog.usuario)) {
                    line = aux.usuario + ":" + aux.contra+ ":" + aux.nombre+ ":" + aux.apellido+ ":" + aux.cuenta+ ":" + aux.email+ ":" + aux.saldo;
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

    public static void jugar(Usuario user) {

        System.out.println("Que quiere jugar ?");
        ArrayList<Game> games = mostrarJuegos();
        System.out.println("introduce numero :");
        int eleccion = Keyboard.readInt();
        Game gamel=games.get((eleccion-1));


        File file = new File("ADMIN/userGame.txt");
        File tempFile = new File("ADMIN/userGame_temp.txt");

        try {
            FileWriter fw = new FileWriter(tempFile);
            FileReader fr = new FileReader(file);
            BufferedWriter bw = new BufferedWriter(fw);
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                int partidas = Integer.parseInt(parts[2]);
                if (parts[0].equals(user.usuario) && parts[1].equals(gamel.nombre) && parts[3].equals("T")){
                    if(comprobarFecha(parts[4])){
                        line = user.usuario + ":" + gamel.nombre + ":" + parts[2] +":"+ "S"+":0";
                    }else{
                        System.out.println("JUGANDO:::::::::::::::");
                    }

                }
                else if (parts[0].equals(user.usuario) && parts[1].equals(gamel.nombre) && partidas > 0){
                    line = (user.usuario + ":" + parts[1] + ":" + (partidas-1) +":"+parts[3]);
                    System.out.println("Te quedan "+(partidas-1)+" partias");
                    System.out.println("JUGANDO:::::::::::::::");
                }
                bw.write(line);
                bw.newLine();
            }
            bw.close();
            br.close();
        } catch (Exception e) {
            System.out.println("Error al guardar el usuario");
        }
        if (file.delete()) {
            tempFile.renameTo(file);
        } else {
            System.out.println("Error al editar el archivo");
        }
    }

    private static boolean comprobarFecha(String fechaCompra) {

        boolean mes = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaEjemplo = LocalDate.parse(fechaCompra, formatter);
        LocalDate fechaActual = LocalDate.now();

        Period periodo = Period.between(fechaEjemplo, fechaActual);
        int mesesTranscurridos = periodo.getMonths();

        if (mesesTranscurridos >= 1) {
            mes = true;
        }
        return mes;
    }

    public static ArrayList<Game> mostrarJuegos(){
        ArrayList<Game> games = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("ADMIN/games.txt"))) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return games;
    }
}
