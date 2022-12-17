/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaproyecto.grupo5;

import java.awt.TextArea;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;

/**
 *
 * @author joel
 */
public class clsUsuario {

    private String nombre;
    private String cuenta;
    private long saldo;
    private String cedula;
    private String clientesPath;
    private char monedaCuenta;

    public clsUsuario(String clientesPath) {
        this.clientesPath = clientesPath;
    }

    public String getmonedaCuenta() {
        if (this.monedaCuenta == 'c') {
            return "colones";
        } else if (this.monedaCuenta == 'd') {
            return "dolares";
        }
        return "";
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getCuenta() {
        return this.cuenta;
    }

    public long getSaldo() {
        return this.saldo;
    }

    public long setSaldo(int dinero, char tipo) {
        clsHandler clsH = new clsHandler();

        if (tipo == 'd') {
            return this.saldo += dinero;
        }

        if (this.saldo - dinero < 0) {
            clsH.showMessage("Fondos insuficientes");
            return -1;
        }
        return this.saldo -= dinero;
    }

    public void ingresoCajero() {
        clsHandler clsH = new clsHandler();
        String usuario = "", contraseña = "";
        char continuar = ' ', metodoInicioSesion = ' ';

        boolean bloqueado = false;
        boolean encontrado = false;
        boolean verificado = false;
        String[] user = null;
        int posicion = -1;

        String[] datosInicio;
        String[] data = clsH.getData(this.clientesPath);
        boolean matcherUsuario = false;

        do {
            metodoInicioSesion = clsH.inputChar("Digite la manera en la que quiere iniciar sesión"
                    + "\nc) nombre de usuario y contraseña"
                    + "\nn) Número de cuenta y ping"
                    + "\nt) Número de targeta y ping"
                    + "\ns) salir");

            switch (metodoInicioSesion) {
                case 'c':
                    datosInicio = clsH.inicioSesion("Escriba su nombre de usuario: ", data, "Nombre", matcherUsuario, "El usuario no existe", "");
                    if (Integer.parseInt(datosInicio[1]) == -1) {
                        continue;
                    }

                    user = datosInicio[0].split("\n");
                    posicion = Integer.parseInt(datosInicio[1]);

                    verificado = clsH.verificarInicioSesion("Digite su contraseña", posicion, user, "Contraseña incorrecta", this.clientesPath, data, "Contraseña", 3, "");
                    if (verificado) {
                        String nombre = user[2].split("\\:")[1].trim();
                        clsH.showMessage("Bienvenido(a) " + nombre);
                        this.nombre = nombre;

                        this.cedula = user[1].split("\\:")[1].trim();
                        String[] cuentas = clsH.getCuentas(user);
                        String[] cuenta = clsH.selection(cuentas).split("\n");
                        this.saldo = Long.parseLong(cuenta[1]);
                        this.cuenta = cuenta[0];
                        System.out.println(this.cuenta);
                        System.out.println(this.saldo);
                    }
                    break;
                case 'n':
                    datosInicio = clsH.inicioSesion("Digite su número de cuenta: ", data, "Número de cuenta", matcherUsuario, "El número de cuenta no existe", "bancarios");
                    if (Integer.parseInt(datosInicio[1]) == -1) {
                        continue;
                    }
                    user = datosInicio[0].split("\n");
                    posicion = Integer.parseInt(datosInicio[1]);

                    verificado = clsH.verificarInicioSesion("Digite su número de ping", posicion, user, "Ping incorrecto", this.clientesPath, data, "Ping", 11, "bancarios");
                    if (verificado) {
                        String nombre = user[2].split("\\:")[1].trim();
                        clsH.showMessage("Bienvenido(a) " + nombre);
                        this.nombre = nombre;
                        this.cedula = user[1].split("\\:")[1].trim();
                        this.cuenta = datosInicio[2].split("\\:")[1].trim();
                        this.saldo = Long.parseLong(datosInicio[3].split("\\:")[1].trim());
                    }
                    break;
                case 't':
                    datosInicio = clsH.inicioSesion("Digite su número de targeta: ", data, "Número de targeta", matcherUsuario, "El número de targeta no existe", "bancarios");
                    if (Integer.parseInt(datosInicio[1]) == -1) {
                        continue;
                    }
                    user = datosInicio[0].split("\n");
                    posicion = Integer.parseInt(datosInicio[1]);

                    verificado = clsH.verificarInicioSesion("Digite su número de ping", posicion, user, "Ping incorrecto", this.clientesPath, data, "Ping", 11, "bancarios");
                    if (verificado) {
                        String nombre = user[2].split("\\:")[1].trim();
                        clsH.showMessage("Bienvenido(a) " + nombre);
                        this.nombre = nombre;
                        this.cedula = user[1].split("\\:")[1].trim();
                        this.cuenta = datosInicio[2].split("\\:")[1].trim();
                        this.saldo = Long.parseLong(datosInicio[3].split("\\:")[1].trim());
                    }
                    break;
                case 's':
                    break;
                default:
                    clsH.showMessage("Opción invalida");
            }

            break;
        } while (!bloqueado && continuar != 'n');
    }

    public void ingresoDinero(clsCajero clsC) {
        clsHandler clsH = new clsHandler();
        int billetesVeinte = 0, billetesDiez = 0, billetesCinco = 0, billetesDos = 0,
                billetesMil = 0, opcion = 0, exito = 0, posicion = -1;
        int menuDenominacion = 0;
        boolean bloqueado = false, encontrado = false;
        long salCuent = 0, deposito = 0;
        char continuar = ' ', siguiente = ' ';

        String clientes[];
        long montoCuenta = 1000000;
        boolean exitCuenta = false;

        String saldoCuenta[] = clsH.getData("clientes.txt");

        do {
            if (this.monedaCuenta == 'd'){
                clsH.showMessage("Este cajero solo cuenta con colones intente de nuevo");
                break;
            }
            menuDenominacion = clsH.inputInt("Ingrese las denominaciones que desea agregar a su cuenta: \n"
                    + "1. Billetes de 20,000\n"
                    + "2. Billetes de 10,000\n"
                    + "3. Billetes de 5,000\n"
                    + "4. Billetes de 2,000\n"
                    + "5. Billetes de 1,000\n"
                    + "6. Regresar");

            switch (menuDenominacion) {
                case 1:
                    billetesVeinte = clsH.inputInt("El saldo en su cuenta es de : ₡" + this.saldo + " colones"
                            + "\nIngrese la cantidad de billetes de ₡20 000: ");
                    exito = clsC.setVeinteMil(billetesVeinte, 'd');
                    if (exito != -1) {
                        this.saldo += (billetesVeinte * 20000);
                        clsH.showMessage("El ingreso se a realizado con exito "
                                + "\nEl nuevo saldo en su cuenta es de: ₡" + this.saldo+ " colones");

                    }
                    continuar = clsH.inputChar("Desea continuar: "
                            + "\ns) Si"
                            + "\nn) No");
                    if (continuar == 'n') {
                        break;
                    }
                    break;

                case 2:

                    billetesDiez = clsH.inputInt("El saldo en su cuenta es de : ₡" + this.saldo + " colones"
                            + "\nIngrese la cantidad de billetes de ₡10 000: ");

                    exito = clsC.setDiezMil(billetesDiez, 'd');
                    if (exito != -1) {
                        clsH.showMessage("El ingreso se a realizado con exito "
                                + "\nEl nuevo saldo en su cuenta es de: ₡" + this.saldo+ " colones");
                        this.saldo += (billetesDiez * 10000);
                    }
                    continuar = clsH.inputChar("Desea continuar: "
                            + "\ns) Si"
                            + "\nn) No");
                    if (continuar == 'n') {
                        break;
                    }
                    break;

                case 3:

                    billetesCinco = clsH.inputInt("El saldo en su cuenta es de : ₡" + this.saldo + " colones"
                            + "\nIngrese la cantidad de billetes de ₡5 000: ");
                    exito = clsC.setCincoMil(billetesCinco, 'd');
                    if (exito != -1) {
                        this.saldo += (billetesCinco * 5000);
                        clsH.showMessage("El ingreso se a realizado con exito "
                                + "\nEl nuevo saldo en su cuenta es de: ₡" + this.saldo+ " colones");

                    }
                    continuar = clsH.inputChar("Desea continuar: "
                            + "\ns) Si"
                            + "\nn) No");
                    if (continuar == 'n') {
                        break;
                    }
                    break;

                case 4:

                    billetesDos = clsH.inputInt("El saldo en su cuenta es de : ₡" + this.saldo + " colones"
                            + "\nIngrese la cantidad de billetes de ₡2 000: ");
                    exito = clsC.setDosMil(billetesDos, 'd');
                    if (exito != -1) {
                        clsH.showMessage("El ingreso se a realizado con exito "
                                + "\nEl nuevo saldo en su cuenta es de: ₡" + this.saldo+ " colones");
                        deposito += (billetesDos * 2000);
                    }
                    continuar = clsH.inputChar("Desea continuar: "
                            + "\ns) Si"
                            + "\nn) No");
                    if (continuar == 'n') {
                        break;
                    }
                    break;

                case 5:

                    billetesMil = clsH.inputInt("El saldo en su cuenta es de : ₡" + this.saldo + " colones"
                            + "\nCantidad de billetes mil " + this.saldo
                            + "\nIngrese la cantidad de billetes de ₡1 000: ");
                    exito = clsC.setMil(billetesMil, 'd');
                    if (exito != -1) {
                        clsH.showMessage("El ingreso se a realizado con exito "
                                + "\nEl nuevo saldo en su cuenta es de: ₡" + this.saldo+ " colones");
                        deposito += (billetesMil * 1000);
                    }
                    continuar = clsH.inputChar("Desea continuar: "
                            + "\ns) Si"
                            + "\nn) No");
                    if (continuar == 'n') {
                        break;
                    }
                    break;
                case 6:
                    break;
                default:
                    clsH.showMessage("Opcion invalida intente de nuevo");

            }

            continuar = clsH.inputChar("¿Desea volver a hacer un ingreso?"
                    + "\ns) Si"
                    + "\nn) No");
        } while (continuar != 'n');
    }

    public void extraccionDinero(clsCajero clsC) {
        clsHandler clsH = new clsHandler();

        char continuar = ' ';
        int retiroUsuario = 0, contador = 0, contVeinteMil = 0, contDiesMil = 0, contCincoMil = 0, contDosMil = 0, contMil = 0;
        int veinteMil = 0, diesMil = 0, cincoMil = 0, dosMil = 0, mil = 0;
        String impresion = "";

        do {
            retiroUsuario = clsH.inputInt("Ingrese el monto que desea retirar: ");

            if (retiroUsuario < 1000) {
                clsH.showMessage("El monto debe ser mayor a ₡1,000 colones");
                continue;
            }
            if (this.monedaCuenta == 'd'){
                clsH.showMessage("Este cajero solo cuenta con colones intente de nuevo");
                break;
            }
            if (clsC.getDinero() < retiroUsuario) {
                clsH.showMessage("El cajero no cuenta con los suficientes fondos para realizar la transacción");
                break;
            }
            if (retiroUsuario > this.saldo) {
                clsH.showMessage("Fondos Insuficientes");
                break;
            }
            this.saldo -= retiroUsuario;
            System.out.print(this.saldo);
            impresion = "La traccion de ₡" + retiroUsuario + " se a relizado con exito"
                    + "\n**************************************************"
                    + "\nTome su Dinero en las siguintes denomonaciones: "
                    + "\n*******************************************************\n";

            int montoRestante = retiroUsuario;
            veinteMil = montoRestante / 20000;
            if (veinteMil > 0) {
                int monto = veinteMil * 20000;
                if (clsC.getVeinteMil() - veinteMil < 0) {
                    montoRestante = montoRestante - monto + Math.abs(clsC.getVeinteMil() - veinteMil) * 20000;
                    veinteMil = clsC.getVeinteMil();
                    impresion += veinteMil + " Billetes de ₵20000\n";
                    contVeinteMil += veinteMil;
                    clsC.setVeinteMil(clsC.getVeinteMil(), 'Q');
                } else {
                    montoRestante = montoRestante - monto;
                    impresion += veinteMil + " Billetes de ₵20000\n";
                    contVeinteMil += veinteMil;
                    clsC.setVeinteMil(veinteMil, 'Q');
                }
            }

            diesMil = montoRestante / 10000;
            if (diesMil > 0) {
                int monto = diesMil * 10000;
                if (clsC.getDiezMil() - diesMil < 0) {
                    montoRestante = montoRestante - monto + Math.abs(clsC.getDiezMil() - diesMil) * 10000;
                    diesMil = clsC.getDiezMil();
                    impresion += diesMil + " Billetes de ₵10000\n";
                    clsC.setDiezMil(clsC.getDiezMil(), 'W');

                } else {
                    montoRestante = montoRestante - monto;
                    impresion += diesMil + " Billetes de ₵10000\n";
                    contDiesMil += diesMil;
                    clsC.setDiezMil(diesMil, 'W');
                }

            }

            cincoMil = montoRestante / 5000;
            if (cincoMil > 0) {
                int monto = cincoMil * 5000;
                if (clsC.getCincoMil() - cincoMil < 0) {
                    montoRestante = montoRestante - monto + Math.abs(clsC.getCincoMil() - cincoMil) * 5000;
                    cincoMil = clsC.getCincoMil();
                    impresion += cincoMil + " Billetes de ₵5000\n";
                    clsC.setCincoMil(clsC.getCincoMil(), 'C');
                } else {
                    montoRestante = montoRestante - monto;
                    impresion += cincoMil + " Billetes de ₵5000\n";
                    contCincoMil += cincoMil;
                    clsC.setCincoMil(cincoMil, 'C');
                }

            }

            dosMil = montoRestante / 2000;
            if (dosMil > 0) {
                int monto = dosMil * 2000;
                if (clsC.getDosMil() - dosMil < 0) {
                    montoRestante = montoRestante - monto + Math.abs(clsC.getDosMil() - dosMil) * 2000;
                    dosMil = clsC.getDosMil();
                    impresion += dosMil + " Billetes de ₵2000\n";
                    clsC.setDosMil(clsC.getDosMil(), 'S');

                } else {
                    montoRestante = montoRestante - monto;
                    impresion += dosMil + " Billetes de ₵2000\n";
                    contDosMil += dosMil;
                    clsC.setDosMil(dosMil, 'S');
                }

            }
            mil = montoRestante / 1000;
            if (mil > 0) {
                int monto = mil * 1000;
                if (clsC.getMil() - mil < 0) {
                    montoRestante -= monto + Math.abs(clsC.getMil() - mil) * 1000;
                    mil = clsC.getMil();
                    impresion += mil + " Billetes de ₵1000\n";
                    clsC.setMil(clsC.getMil(), 'M');

                } else {
                    montoRestante = montoRestante - monto;
                    impresion += mil + " Billetes de ₵1000\n";
                    contMil += mil;
                    clsC.setMil(mil, 'M');
                }

            }
            if (montoRestante > 0){
                clsH.showMessage("El cajero solo cuenta con billetes de"
                        + "\n1000"
                        + "\n2000"
                        + "\n5000"
                        + "\n10000"
                        + "\n20000");
                break;
            }
            impresion += "************************************************\n";
            clsH.showMessage(new TextArea(impresion));
            continuar = clsH.inputChar("Desea continuar: "
                    + "\ns) Si"
                    + "\nn) No");

        } while (continuar != 'n');
    }

    public void transferenciasDinero() {
        clsHandler clsH = new clsHandler();
        String[] data = clsH.getData(this.clientesPath);

        long montoTransferencia = 0L;
        String cuentaDestino;
        long saldocuentadestino = 0L;
        boolean cuentaDestinoMatch;
        String[] cuentas;

        cuentaDestino = clsH.inputString("Ingrese el número de cuenta a la que se le va a envíar la transferencia");
        boolean existeCuentaDestino = false;

        for (int i = 0; i < data.length; i++) {
            String[] usuario = data[i].split("\n");
            cuentas = clsH.getCuentas(usuario);

            for (int j = 0; j < cuentas.length; j++) {
                cuentaDestinoMatch = clsH.match(cuentaDestino, "Número de cuenta", cuentas[j]).find();
                if (cuentaDestinoMatch) {
                    String[] cuenta = cuentas[j].split("\n");

                    this.saldo = 1000000;
                    existeCuentaDestino = true;
                    montoTransferencia = clsH.inputLong("Ingrese el monto que desea transferir: ");
                    if (this.saldo - montoTransferencia < 0) {
                        clsH.showMessage("Fondos insuficientes");
                        return;
                    }

                    this.saldo -= montoTransferencia;
                    cuenta[1] = "Monto en la cuenta: " + String.valueOf(Long.parseLong(cuenta[1].split("\\:")[1].trim()) + montoTransferencia);
                    cuentas[j] = String.join("\n", cuenta) + "\n";
                    cuentas = String.join("\\", cuentas).split("\n");

                    for (int k = 14; k < usuario.length; k++) {
                        usuario[k] = cuentas[k - 13];
                    }

                    try {
                        FileWriter writer = new FileWriter(this.clientesPath);
                        for (int k = 0; k < data.length; k++) {
                            if (i == k) {
                                clsH.changeData(usuario, writer);
                            } else if (k != data.length - 1) {
                                writer.write(data[k] + "|");
                            }

                        }
                        writer.close();
                    } catch (IOException e) {
                        clsH.showMessage("Error: " + e);
                    }
                }
            }
        }

        if (!existeCuentaDestino) {
            clsH.showMessage("La cuenta '" + cuentaDestino + "' no existe");
            return;
        }

        this.boucher(cuentaDestino, this.cuenta, montoTransferencia);
    }

    public void boucher(String cuentaDestino, String cuenta, long montoTransferencia) {
        return;
    }
}
