/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaproyecto.grupo5;

import java.awt.TextArea;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 *
 * @author joel
 */
public class clsUsuario {
    private String nombre;
    private String cuenta;
    private double saldo;
    private String cedula;
    private String clientesPath;
    private char monedaCuenta;
    
    public clsUsuario(String clientesPath) {
        this.clientesPath = clientesPath;
    }
    
    public String getmonedaCuenta() {
        if (this.monedaCuenta == 'c') return "colones";
        return "dolares";
    }
    public String getNombre() {
        return this.nombre;
    }
    public String getCuenta() {
        return this.cuenta;
    }
    public double getSaldo() {
        return this.saldo;
    }
    
    public double setSaldo(int dinero, char tipo) {
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
                    + "\nc) Nombre de usuario y contraseña"
                    + "\nn) Número de cuenta y ping"    
                    + "\nt) Número de targeta y ping"
                    + "\ns) salir");
            
            switch (metodoInicioSesion) {
                case 'c':
                    datosInicio = clsH.inicioSesion("Escriba su nombre de usuario: ", data, "Nombre", matcherUsuario, "El usuario no existe", "");
                    if (Integer.parseInt(datosInicio[1]) == -1) continue;
                    
                    user = datosInicio[0].split("\n");
                    posicion = Integer.parseInt(datosInicio[1]);
                    
                    verificado = clsH.verificarInicioSesion("Digite su contraseña", posicion, user, "Contraseña incorrecta", this.clientesPath, data, "Contraseña", 3, "");
                    if (verificado) {
                        String nombre = user[2].split("\\:")[1].trim();
                        clsH.showMessage("Bienvenido(a) " + nombre);
                        this.nombre = nombre;
                        
                        this.cedula = user[1].split("\\:")[1].trim();
                        String[] cuentas = clsH.getCuentas(user);
                        String[] cuenta = clsH.selection(cuentas);
                        this.saldo = Long.parseLong(cuenta[3]);
                        this.cuenta = cuenta[1];
                        this.monedaCuenta = cuenta[4].charAt(0);
                    }
                    break;
                case 'n':
                    datosInicio = clsH.inicioSesion("Digite su número de cuenta: ", data, "Número de cuenta", matcherUsuario, "El número de cuenta no existe", "bancarios");
                    if (Integer.parseInt(datosInicio[1]) == -1) continue;
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
                    if (Integer.parseInt(datosInicio[1]) == -1) continue;
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
    
    public void ingresoDinero() {
        
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
                    montoRestante -= monto;
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
                    montoRestante -= monto;
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
                    montoRestante -= monto;
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
                    montoRestante -= monto;
                    impresion += dosMil + " Billetes de ₵2000\n";
                    contDosMil += dosMil;
                    clsC.setDosMil(dosMil, 'S');
                }

            }
            mil = montoRestante / 1000;
            if (mil > 0) {
                int monto = mil * 1000;
                if (clsC.getMil() - mil < 0) {
                    montoRestante -=  monto + Math.abs(clsC.getMil() - mil) * 1000;
                    mil = clsC.getMil();
                    impresion += mil + " Billetes de ₵1000\n";
                    clsC.setMil(clsC.getMil(), 'M');

                } else {
                    montoRestante -= monto;
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
    // ---Aqui empieza la seccion para transferir dinero--- //
      
    public void transferenciasDinero() {
       //---- estas son las variantes que se van a utilizar----//

        clsHandler clsH = new clsHandler();
        //--- esta es la ruta del archivo del cliente---//
        String[] data = clsH.getData(this.clientesPath);
        
        long montoTransferencia = 0L;
        String cuentaDestino;
        //---- aqui se realiza con el boolean la operacion si es verdadera o falsa---//
        boolean cuentaDestinoMatch;
        String[] cuentas;
        boolean existeCuentaDestino = false;
        
        
        
        cuentaDestino = clsH.inputString("Ingrese el número de cuenta a la que se le va a envíar la transferencia");
//---- aqui se aplican la el vector de data para buscar cada posicion , cada split separa los datos del usuario en un vector por el salto de linea--//
        for (int i = 0; i < data.length; i++) {
            String[] usuario = data[i].split("\n");
            cuentas = clsH.getCuentas(usuario);
            
            for (int j = 0; j < cuentas.length; j++) {
                if(cuentas[j].length() < 10) continue;
                
                cuentaDestinoMatch = clsH.match(cuentaDestino, "Número de cuenta", cuentas[j]).find();
                if (cuentaDestinoMatch) {
                    
                    String[] cuenta = cuentas[j].trim().split("\n");
                    
                    
                    
                    existeCuentaDestino = true;
                    montoTransferencia = clsH.inputLong("Ingrese el monto que desea transferir en " + this.getmonedaCuenta() + ": ");
                    
                    
                    if(this.saldo - montoTransferencia < 0) {
                        clsH.showMessage("Fondos insuficientes");
                        return;
                    }
                    this.saldo -= montoTransferencia;
                    //--aqui se consigue el tipo de moneda que tiene la cuenta de destino--//
                    char monedaCuentaDestino = cuenta[3].split("\\:")[1].trim().charAt(0);
                    //--aqui se hace la conversion de la moneda que tiene la cuenta de transferencia ala que tiene la de destino--//
                    if (monedaCuentaDestino == 'd' && this.monedaCuenta == 'c') montoTransferencia /= 600;
                    else if (monedaCuentaDestino == 'c' && this.monedaCuenta == 'd') montoTransferencia *= 600;
                    
                    //--aqui guardo el nuevo valor de la cuenta de destino y sus cambios--//
                    cuenta[1] = "Monto en la cuenta: " + String.valueOf(Double.parseDouble(cuenta[1].split("\\:")[1].trim()) + montoTransferencia);
                    cuentas[j] = (j == 0 ? "" : "\n") + String.join("\n", cuenta)+ "\n";
                    cuentas = (String.join("\\", cuentas) + "\\").split("\n");
                  
                    //--aqui las lineas de usuario se actualiza con las lineas de la cuenta--//
                    for (int k = 13; k < usuario.length; k++) {
                        usuario[k] = cuentas[k - 13];
                        
                    }
                    //---aqui escribe la informacion que se cambio del usuario--//
                    try {
                        FileWriter writer = new FileWriter(this.clientesPath);
                        for (int k = 0; k < data.length; k++) {
                           if (i == k) clsH.changeData(usuario, writer); 
                            else if (k != data.length - 1) writer.write(data[k] + "|");
                            
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

        this.boucher(this.nombre ,cuentaDestino, this.cuenta, montoTransferencia);
    }
    
    public void boucher(String nombre, String cuentaDestino, String cuentaOrigen, long montoTransferencia) {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");

                           
        String boucher =  date.format(date) + "\n"
                          + "Nombre " + nombre 
                         + "\n cuentaDestino " + cuentaDestino
                         + "\n cuentaOrigen " + cuentaOrigen 
                         + "\n montoTransferencia " + montoTransferencia
                         + "\n"; 
                         
        clsHandler clsH = new clsHandler();
        
        clsH.showMessage(boucher);
        
        
        
        
        
        
        
        
        
    }
}
