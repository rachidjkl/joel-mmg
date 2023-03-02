public class Menus {

    static void menu() {
        int eleccion;
        do {
            System.out.println("ACCÉS A LA GESTIÓ DE JOCS\n  1. Registre\n  2. Accedir\n  0. Sortir\n ");
            eleccion = Keyboard.readInt();
            {
                switch (eleccion) {
                    case 1:
                        Main2.registrar();
                        break;
                    case 2:
                        Usuario userLog = Main2.iniciarSesion();
                        if(userLog.usuario != null){
                            menuAcc(userLog);
                        }
                        break;
                    case 0:
                        System.out.println("---------------Adios-----------------");
                        break;
                }
            }
        } while (eleccion != 0);
    }
    static void menuAcc(Usuario userLog) {

        int eleccion;
        do {
            System.out.println("JOCS ONLINE\n" + "1. Jugar\n" + "2. Gestionar jocs\n" + "3. Gestionar saldo\n" + "4. Gestionar les dades de l'usuari\n" + "0. Sortida al menú d'entrada\n");
            eleccion = Keyboard.readInt();

            switch (eleccion) {
                case 1:
                    System.out.println(userLog.saldo);
                    InfoFichero.jugar(userLog);

                    break;
                case 2:
                    InfoFichero.gestionarJuegos(userLog);
                    break;
                case 3:
                    Main2.gestionarSaldo(userLog);
                    break;
                case 4:
                    Main2.cambiarAtributos(userLog);
                    break;
                case 0:

                    break;
            }

        } while (eleccion != 0);
    }
    static void menuCompra(Game gamel, Usuario user) {
        System.out.println(gamel.nombre+"\n"+"Credito: "+gamel.precio+"\n"+"Tarifa: "+gamel.precioTa);
        System.out.println("Comprar creditos (1) o Tarifa plana (2): ");
        int eleccion = Keyboard.readInt();
        if (eleccion == 1){
            System.out.println("numero de partidas que quieres ?");
            int numCre = Keyboard.readInt();
            float saldoRestar = numCre * gamel.precio;
            if(saldoRestar<= user.saldo){
                user.saldo = user.saldo - saldoRestar;
                InfoFichero.actualizarUser(user, user);
                InfoFichero.guardarCambiosJuegos(gamel, user, numCre);
            }else {
                System.out.println("saldo insuficiente");
            }
        }else if(eleccion == 2){

            if(user.saldo>= gamel.precioTa) {
                user.saldo = user.saldo - gamel.precioTa;
                InfoFichero.actualizarUser(user, user);
                InfoFichero.guardarTarifaJuegos(gamel, user);
            }else {
                System.out.println("saldo insuficiente");
            }

        }
    }
}
