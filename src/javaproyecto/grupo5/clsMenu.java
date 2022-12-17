/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaproyecto.grupo5;

/**
 *
 * @author joel
 */
public class clsMenu {
    public void menu() {
        int opcion = 0;
        
        clsHandler clsH = new clsHandler();
        clsCajero clsC = new clsCajero("cajero.txt");
        clsAdministracion clsA = new clsAdministracion("usuarios.txt", "clientes.txt");
        clsReportes clsR = new clsReportes();
        clsUsuario clsU = new clsUsuario("clientes.txt");
        
        clsC.initCajero();
        
        do {
            opcion = clsH.inputInt("Digite la opción que desea:"
                    + "\n 1) inicio sesión usuarios"
                    + "\n 2) CRUD de usuarios"
                    + "\n 3) CRUD de clientes"
                    + "\n 4) balances"
                    + "\n 5) reportes"
                    + "\n 6) Depocitar"
                    + "\n 7) extracción de dinero"
                    + "\n 8) inicio sesión clientes"
                    + "\n 9) transferencias"
                    + "\n 10) salir");
            
            switch(opcion) {
                case 1:
                    clsA.validacionContraseñaUsuario();
                    break;
                case 2:
                    clsA.seguridad();
                    break;
                case 3:
                    clsA.ingresoClientes(clsR);
                    break;
                case 4:
                    clsA.balances(clsC);
                    break;
                case 5:
                    clsR.getusuariosCreados();
                    break;
                case 6:
                    clsU.ingresoDinero(clsC);
                    break;
                case 7:
                    clsU.extraccionDinero(clsC);
                    break;
                case 8:
                    clsU.ingresoCajero();
                    break;
                case 9:
                    clsU.transferenciasDinero();
                    break;
                case 10:
                    break;
                default:
                    break;
            }
        } while (opcion != 10);
        clsC.saveCajero();
    }
}
