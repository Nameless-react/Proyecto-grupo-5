/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaproyecto.grupo5;

/**
 *
 * @author joel
 */
import javax.swing.JOptionPane;
public class clsMenu {
    

//-----Separamos las clases para que se puedan acceder desde los 2 metodos-----//
    private clsCajero clsC = new clsCajero("cajero.txt");
    private clsReportes clsR = new clsReportes();
    
    
    
    
    //-----INICIO DE SESION-------//
    public void inicioSesion(){
        this.clsC.initCajero();
        
        
        clsAdministracion clsA = new clsAdministracion("usuarios.txt", "clientes.txt");
        clsUsuario clsU = new clsUsuario("clientes.txt");
        
        
        int accion = 0;
        int exito = 0;
        
        String[] options = {"Cliente", "Administrador", "Exit"};
        
        do {
            accion=JOptionPane.showOptionDialog(null,"¿Como desea iniciar sesion?","ATM", 0, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            switch(accion){
                case 0:
                    //Clientes
                    exito = clsU.ingresoCajero(this.clsR);
                    if (exito == 1) {
                        this.menuCliente(clsU);
                    }
                    break;
                case 1:
                    //Usuarios
                    exito = clsA.validacionContraseñaUsuario();
                    if (exito == 1) {
                        this.menuAdministrador(clsA);
                    }
                    break;
                case 2:
                    //Exit
                    break;
            }
        } while(accion != 2);
        this.clsC.saveCajero();
    }
    
    
    
    //-----MENU DEL CLIENTE-------//
    public void menuCliente(clsUsuario clsU) {
        clsHandler clsH = new clsHandler();
        
        
        int accion = 0;
        int opcionReporte = 0;
        String[] options = {"Deposito", "Retiro", "Trasferencia", "Reportes", "Exit"};
        
        
        do {
            accion=JOptionPane.showOptionDialog(null,"Nombre: " + clsU.getNombre()
                    + "Cuenta: " + clsU.getCuenta()
                    + "Saldo: " + clsU.getSaldo() + "" + clsU.getmonedaCuenta()
                    + "¿Que desea realizar?","ATM", 0, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            switch(accion){
                case 0:
                    //Deposito
                    clsU.ingresoDinero(this.clsC, this.clsR);
                    break;
                case 1:
                    //Retiro
                    clsU.extraccionDinero(this.clsC, this.clsR);
                    break;
                case 2:
                    //Transferencia
                    clsU.transferenciasDinero(this.clsR);
                    break;
                case 3:
                    //Reportes
                    do {
                        opcionReporte = clsH.inputInt("¿Que tipo de reporte quiere mostrar?"
                    + "\n1. Detalle de transaccion por cuenta y fecha"
                    + "\n2. Estado actual de la cuenta"
                    + "\n3. Detalle de transaccion por tipo y fecha"
                    + "\n4. Salir");
                        
                        switch(opcionReporte){
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
                    } while (opcionReporte != 4);
                    break;
                case 4:
                    //Exit
                    break;
            }
        } while (accion != 4);
        clsU.saveUsuario();
        
    }
    
    
    
    //---MENU DEL ADMINISTRADOR-----//
    public void menuAdministrador(clsAdministracion clsA){
        int accion = 0;
        String[] options = {"CRUD Clientes", "CRUD Usuarios", "Deposito al Cajero", "Exit"};
        
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
            }
        } while (accion != 3);
    }
}