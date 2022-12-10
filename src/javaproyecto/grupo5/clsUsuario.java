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
        if (this.monedaCuenta == 'c') return "colones";
        else if(this.monedaCuenta == 'd') return "dolares";
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
        //Aún no funciona faltan cosas
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
                    datosInicio = clsH.inicioSesion("Escriba su nombre de usuario: ", data, "Nombre", matcherUsuario, "El usuario no existe");
                    if (Integer.parseInt(datosInicio[1]) == -1) continue;
                    
                    user = datosInicio[0].split("\n");
                    posicion = Integer.parseInt(datosInicio[1]);
                    
                    verificado = clsH.verificarInicioSesion("Digite su contraseña", posicion, user, "Contraseña incorrecta", this.clientesPath, data, "Contraseña", 3);
                    if (verificado) {
                        String nombre = user[2].split("\\:")[1].trim();
                        clsH.showMessage("Bienvenido(a) " + nombre);
                        this.nombre = nombre;
                        this.cedula = user[1].split("\\:")[1].trim();
                        this.cuenta = user[12].split("\\:")[1].trim();
                        this.saldo = Long.parseLong(user[14].split("\\:")[1].trim());
                    }
                    break;
                case 'n':
                    datosInicio = clsH.inicioSesion("Digite su número de cuenta: ", data, "Número de cuenta", matcherUsuario, "El número de cuenta no existe");
                    if (Integer.parseInt(datosInicio[1]) == -1) continue;
                    user = datosInicio[0].split("\n");
                    posicion = Integer.parseInt(datosInicio[1]);
                    
                    verificado = clsH.verificarInicioSesion("Digite su número de ping", posicion, user, "Ping incorrecto", this.clientesPath, data, "Ping", 16);
                    if (verificado) {
                        String nombre = user[2].split("\\:")[1].trim();
                        clsH.showMessage("Bienvenido(a) " + nombre);
                        this.nombre = nombre;
                        this.cedula = user[1].split("\\:")[1].trim();
                        this.cuenta = user[12].split("\\:")[1].trim();
                        this.saldo = Long.parseLong(user[14].split("\\:")[1].trim());
                    }
                    break;
                case 't':
                    datosInicio = clsH.inicioSesion("Digite su número de targeta: ", data, "Número de targeta", matcherUsuario, "El número de targeta no existe");
                    if (Integer.parseInt(datosInicio[1]) == -1) continue;
                    user = datosInicio[0].split("\n");
                    posicion = Integer.parseInt(datosInicio[1]);
                    
                    verificado = clsH.verificarInicioSesion("Digite su número de ping", posicion, user, "Ping incorrecto", this.clientesPath, data, "Ping", 16);
                    if (verificado) {
                        String nombre = user[2].split("\\:")[1].trim();
                        clsH.showMessage("Bienvenido(a) " + nombre);
                        this.nombre = nombre;
                        this.cedula = user[1].split("\\:")[1].trim();
                        this.cuenta = user[12].split("\\:")[1].trim();
                        this.saldo = Long.parseLong(user[14].split("\\:")[1].trim());
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
    
    public void ingresoDinero() {
        
    }
    
    public void extraccionDinero(clsCajero clsC) {
        clsHandler clsH = new clsHandler();

        char continuar = ' ';
        int retiroUsuario = 0, contador = 0, contVeinteMil = 0, contDiesMil = 0,
                contCincoMil = 0, contDosMil = 0, contMil = 0;
        int veinteMil = 0, diesMil = 0, cincoMil = 0, dosMil = 0, mil = 0;
        String impresion = "";

        do {
            retiroUsuario = clsH.inputInt("Ingrese el monto que desea retirar: ");

            if (retiroUsuario < 1000) {
                clsH.showMessage("El monto debe ser mayor a ₡1 000 colones");
                continue;
            }
            if (clsC.getDinero() < retiroUsuario) {
                clsH.showMessage("El cajero no cuenta con los suficientes fondos para realizar la transacción");
                break;
            }
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
                    montoRestante = montoRestante - monto + Math.abs(clsC.getMil() - mil) * 1000;
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
            impresion += "************************************************\n";
            clsH.showMessage(new TextArea(impresion));

            continuar = clsH.inputChar("Desea continuar: "
                    + "\ns) Si"
                    + "\nn) No");

        } while (continuar != 'n');
    }
    
    public void transferenciasDinero(long saldocuentaorigen,String cuentaorigen) {
        
        long montodetransferencia;
        String cuentadestino;
        long saldocuentadestino = 0L;
        Matcher cuentaMatch;

        clsHandler clsH = new clsHandler();

        String[] Data = clsH.getData(this.clientesPath);

        cuentadestino = clsH.inputString("Ingrese el numero de cuenta de destino de la transferencia");
        montodetransferencia = clsH.inputLong("Ingrese el monto de la transferencia");
        boolean existecuentaorigen = false;

        
        try {
            
            FileWriter writer = new FileWriter("clientes.txt");
            for (int i = 0; i < Data.length; i++) {
                cuentaMatch = clsH.match(cuentadestino, "Numero de cuenta", Data[i]);
                if (cuentaMatch.find()) {
                    saldocuentaorigen -= montodetransferencia;

                    saldocuentadestino += montodetransferencia;
                    String[] usuarios = Data[i].split("\n");
                    for (int j = 0; j < usuarios.length; j++) {
                         if ((usuarios[j].contains("Numero de cuenta: " + cuentaorigen))) {
                            usuarios[j] = String.valueOf(saldocuentaorigen);
                        }

                    }
                    clsH.changeData(Data, writer);
                }
            }

        } catch (IOException e) {
            clsH.showMessage("error intente de nuevo");
        }
    }
}
