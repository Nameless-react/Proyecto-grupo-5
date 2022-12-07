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
    private String saldo;
    private String cedula;
    private String clientesPath;
    
    public clsUsuario(String clientesPath) {
        this.clientesPath = clientesPath;
    }
    
    public void ingresoCajero() {
        //Aún no funciona faltan cosas
        clsHandler clsH = new clsHandler();
        String usuario = "", contraseña = "";
        char continuar = ' ', metodoInicioSesion = ' ';
        
        boolean bloqueado = false;
        boolean encontrado = false;
        String[] user = null;
        int posicion = -1;
        
        String[] data = clsH.getData(this.clientesPath); 
        boolean matcherIdentificacion;
        Matcher matcherContraseña;
   
    
        do {
            metodoInicioSesion = clsH.inputChar("Digite la manera en la que quiere iniciar sesión"
                    + "c) Contraseña y nombre de usuario"
                    + "n) Número de cuenta y ping"
                    + "t) Número de targeta y ping");
            
            switch (metodoInicioSesion) {
                case 'c':
                    usuario = clsH.inputString("Escriba su nombre de usuario: ");
                    
                    break;
                case 'n':
                    
                    break;
                case 't':
                    
                    break;
                default:
                    clsH.showMessage("Opción invalida");
            }
            
               
            
            for (int i = 0; i < data.length; i++) {                
                matcherIdentificacion = clsH.match(usuario, "Identificacion", data[i]).find();
                if (!matcherIdentificacion) continue;
                
                user = data[i].split("\n");
                if (Boolean.parseBoolean(user[6].substring(15)))  {
                    clsH.showMessage("El usuario está bloqueado");
                    return;
                } else {
                    posicion = i;
                    encontrado = true;
                    break;
                }
            }
            
            if (!encontrado) {
                clsH.showMessage("El usuario " + usuario + " no existe");
                continuar = clsH.inputChar("¿Desea continuar?"
                        + "\ns) si"
                        + "\nn) no");
                continue;
            }
            
            for (int i = 0; i < 3; i++) {
                contraseña = clsH.inputString("Escriba su contraseña: ");
                matcherContraseña = clsH.match(contraseña, "Contraseña", user[3] + "\n");
                
                if (!matcherContraseña.find()) {
                    clsH.showMessage("Contraseña incorrecta");
                    if (i == 2) {
                        
                        user[18] = "Bloqueado: true";
                        try {
                            FileWriter writer = new FileWriter(this.clientesPath);
                            for (int j = 0; j < data.length; j++) {
                                if (posicion == j) clsH.changeData(user, writer); 
                                else if (j != data.length - 1) writer.write(data[i] + "|");
                            }
                        
                            writer.close();

                        } catch (IOException e) {
                            clsH.showMessage("Error: " + e);
                        }
                        bloqueado = true;
                    }
                } else {
                    String nombre = user[2].split("\\:")[1].trim();
                    clsH.showMessage("Bienvenido(a) " + nombre);
                    this.nombre = nombre;
                    
                    break;
                }   
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
