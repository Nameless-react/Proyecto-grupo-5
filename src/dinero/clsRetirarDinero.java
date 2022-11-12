/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dinero;

import javax.swing.JOptionPane;

/**
 *
 * @author Danny Jimenez
 */
public class clsRetirarDinero {

    public void retirarDinero() {
        clsHelper clsH = new clsHelper();

        String dinero = "";
        int opcion = 0;
        float saldoActual = 0, deposito = 0, retiro = 0.0f;
        char siguiente = ' ', siguiente2 = ' ';

        do {
            opcion = clsH.recibeInt("Por favor digite la opcion que desea realizar: "
                    + "\n1) Consulta de cuents"
                    + "\n2) Deposito"
                    + "\n3) Retiro"
                    + "\n4) Salir");

            switch (opcion) {
                case 1:
                    clsH.imprimeMensaje("El Saldo en su cuenta es de: " + saldoActual + " colones");
                    break;
                case 2:
                    do {
                        deposito = clsH.recibeFloat("Su saldo actual es de: " + saldoActual
                                + " \nIngrese el monto que desea depositar: ");
                        if (deposito >= 1000) {
                            saldoActual = saldoActual + deposito;
                            clsH.imprimeMensaje("El depósito se a realizado con exito \nEl saldo en su cuenta es de: " + saldoActual);
                        } else {
                            clsH.imprimeMensaje("El depósito debe ser mayor a 1000 colones");
                        }
                        siguiente = clsH.recibeChar("Desea continuar: \nSi\nNo");
                    } while (siguiente == 'S' && siguiente != 'N');
                    break;
                case 3:
                    do {
                        retiro = clsH.recibeFloat("Su saldo actual es de: " + saldoActual + " \nIngrese el monto que desea retirar: ");
                        if (retiro > saldoActual) {
                            clsH.imprimeMensaje("No cuenta con suficiente dinero en la cuenta");
                        } else if (retiro >= 1000) {
                            saldoActual -= retiro;
                            clsH.imprimeMensaje("El retiro de su cuenta se ah realizado con exito \nEl saldo en su cuenta es de: " + saldoActual);
                        } else {
                            clsH.imprimeMensaje("El retiro debe ser mayor a 1000 colones");
                        }
                        siguiente2 = clsH.recibeChar("Desea continuar: \nSi\nNo");
                    } while (siguiente2 == 'S' && siguiente2 != 'N');
                    break;
                case 4:
                    break;
                default:
                    clsH.imprimeMensaje("La opcion que dijito es incorrecta");
                    break;
            }
        } while (opcion != 4);

    }
}
