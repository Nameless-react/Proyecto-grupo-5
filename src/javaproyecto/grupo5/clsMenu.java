package javaproyecto.grupo5;
import javax.swing.JOptionPane;
public class clsMenu {
    //-----Separamos las clases para que se puedan acceder desde los 2 metodos-----//
    private clsCajero clsC= new clsCajero("cajero.txt");
    private clsReportes clsR = new clsReportes();
    //-----INICIO DE SESION-------//
    public void inicioSesion(){
        clsHandler clsH = new clsHandler();
        clsAdministracion clsA = new clsAdministracion("usuarios.txt", "clientes.txt");
        clsUsuario clsU = new clsUsuario("clientes.txt");
        int accion=0;
        String[] options = {"Cliente","Usuario","Exit"};
        do {
            accion=JOptionPane.showOptionDialog(null,"¿Como desea iniciar sesion?","ATM", 0, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            switch(accion){
                case 0:
                    //Clientes
                    int exit=clsU.ingresoCajero(this.clsR);
                    if (exit==1) {
                        this.menuCliente(clsU);
                    }
                    break;
                case 1:
                    //Usuarios
                    int exito=clsA.validacionContraseñaUsuario();
                    if (exito==1) {
                        this.menuAdministrador(clsA);
                    }
                    break;
                case 2:
                    //Exit
                    break;
                default:
                    break;
            }
        }while(accion != 2);
    }
    //-----MENU DEL CLIENTE-------//
    public void menuCliente(clsUsuario clsU) {
        int accion=0;
        int opcionreporte=0;
        String[] options = {"Deposito","Retiro","Trasferencia","Reportes","Exit"};
        clsHandler clsH = new clsHandler();
        this.clsC.initCajero();
        do {
            accion=JOptionPane.showOptionDialog(null,"¿Que desea realizar?","ATM", 0, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            switch(accion){
                case 0:
                    //Deposito
                    clsU.ingresoDinero(this.clsC);
                    break;
                case 1:
                    //Retiro
                    clsU.extraccionDinero(this.clsC);
                    break;
                case 2:
                    //Transferencia
                    clsU.transferenciasDinero(this.clsR);
                    break;
                case 3:
                    //Reportes
                    do {
                        opcionreporte=clsH.inputInt("¿Que tipo de reporte quiere mostrar?"
                    + "\n1. Detalle de transaccion por cuenta y fecha"
                    + "\n2. Estado actual de la cuenta"
                    + "\n3. Detalle de transaccion por tipo y fecha"
                    + "\n4. Salir");
                        
                        switch(opcionreporte){
                            case 1:
                                this.clsR.getTransaccionesUsuario();
                                break;
                            case 2:
                                this.clsR.getEstadoCuenta();
                                break;
                            case 3:
                                this.clsR.getTransaccionTipo();
                                break;
                            case 4:
                                break;
                        }
                    } while (opcionreporte != 4);
                    break;
                case 4:
                    //Exit
                    break;
                default:
                    break;
            }
        } while (accion !=4);
        this.clsC.saveCajero();
    }
    //---MENU DEL ADMINISTRADOR-----//
    public void menuAdministrador(clsAdministracion clsA){
        
        int accion=0;
        int opcionreporte=0;
        String[] options = {"CRUD Clientes","CRUD Usuarios","Deposito al Cajero","Exit"};
        clsHandler clsH = new clsHandler();
        this.clsC.initCajero();
        do {
            accion=JOptionPane.showOptionDialog(null,"¿Que desea realizar?","ATM", 0, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            switch(accion){
                case 0:
                    //CRUD Clientes
                    clsA.ingresoClientes(this.clsR);
                    break;
                case 1:
                    //CRUD Usuarios
                    clsA.seguridad();
                    break;
                case 2:
                    //Deposito al Cajero
                    clsA.balances(this.clsC, this.clsR);
                    break;
                case 3:
                    //Exit
                    break;
                default:
                    break;
            }
        } while (accion !=3);
    }
}
