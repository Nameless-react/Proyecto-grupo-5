/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaproyecto.grupo5;

import javax.swing.JOptionPane;

/**
 *
 * @author joel
 */
public class clsMenu {
    public void menu() {
        int opcion = 0;
        int accion=0;
        char opcionreporte=' ';
        String[] options = {"Deposito","Retiro","Trasferencia","Reportes","Exit"};
        clsHandler clsH = new clsHandler();
        clsCajero clsC = new clsCajero("cajero.txt");
        clsAdministracion clsA = new clsAdministracion("usuarios.txt", "clientes.txt");
        clsReportes clsR = new clsReportes();
        clsUsuario clsU = new clsUsuario("clientes.txt");
        
        clsC.initCajero();
        do {
            accion=JOptionPane.showOptionDialog(null,"¿Que desea realizar?","ATM", 0, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            switch(accion){
                case 1:
                    //Deposito
                    clsU.ingresoDinero(clsC);
                    break;
                case 2:
                    //Retiro
                    clsU.extraccionDinero(clsC);
                    break;
                case 3:
                    //Transferencia
                    clsU.transferenciasDinero(clsR);
                    break;
                case 4:
                    //Reportes
                    do {
                        opcionreporte=clsH.inputChar("¿Que tipo de reporte quiere mostrar?"
                    + "\nA. Detalle de transaccion por cuenta y fecha"
                    + "\nB. Estado actual de la cuenta"
                    + "\nC. Detalle de transaccion por tipo y fecha"
                    + "\nS. Salir");
                    } while (opcionreporte != 'S');
                    break;
                case 5:
                    //Exit
                    break;
                default:
                    break;
            }
        } while (accion !=5);
        clsC.saveCajero();
    }
}
