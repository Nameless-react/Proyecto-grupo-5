/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaproyecto.grupo5;

import java.awt.TextArea;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author joel
 */
public class clsHandler {
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
    
    public void showMessage(TextArea message) {
        JOptionPane.showMessageDialog(null, message);
    }
    
    public char inputChar(String message){
        return JOptionPane.showInputDialog(message).toLowerCase().charAt(0);
    }
    public String inputString(String message) {
        return JOptionPane.showInputDialog(message);
    }
    
    public Integer inputInt(String message) {
        return Integer.parseInt(JOptionPane.showInputDialog(message));
    }
    
    public double inputDouble(String message) {
        return Double.parseDouble(JOptionPane.showInputDialog(message));
    }
    
    public long inputLong(String mensaje){
        return Long.parseLong(JOptionPane.showInputDialog(mensaje));
    }
    
    public float inputFloat(String message) {
        return Float.parseFloat(JOptionPane.showInputDialog(message));
    }
   
    public String selection(String[] opciones) {
        String[] cuentas = new String[opciones.length];
        String[] cuenta;
        
        for (int i = 0; i < opciones.length; i++) {
            if (opciones[i].length() < 2) continue;
            cuenta = opciones[i].trim().split("\n");
            cuentas[i] = cuenta[0] + "\n" + cuenta[1];
        }
        String[] selection = ((String) JOptionPane.showInputDialog(null, "Cuenta: ", " ¿Con cual cuenta desea iniciar sesión?", JOptionPane.QUESTION_MESSAGE, null,cuentas, cuentas[0])).split("\n");
        
        
        return selection[0].split("\\:")[1].trim() + "\n" + selection[1].split("\\:")[1].trim();
    }
    
    public String[] getData(String path) {
        String datum = "";
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
                FileWriter write = new FileWriter(path);
                if (path.contains("clientes.txt")) {
                    write.write("\nIdentificacion: 9876543256789\n" +
                                "Nombre: Juan\n" +
                                "Contraseña: admin1234\n" +
                                "Sexo: m\n" +
                                "Fecha de nacimiento: 03/02/1999\n" +
                                "Ingresos: 123456789\n" +
                                "Residencia: san cristobal\n" +
                                "Correo: frgthyjui654\n" +
                                "Teléfono: 2345687654\n" +
                                "Bloqueado: false\n" +
                                "Ping: 5835\n" +
                                "Cuenta:\n" +
                                "Número de cuenta: 87217079\n" +
                                "Monto en la cuenta: 0\n" +
                                "Tipo de cuenta: c\n" +
                                "Moneda de la cuenta: c\n" +
                                "Número de targeta: 6205 8741 4648 2102\n" +
                                "Fecha de vencimiento: 16/11/2027\n" +
                                "CVV: 556\n" +
                                "\\\n" +
                                "Número de cuenta: 79149337\n" +
                                "Monto en la cuenta: 0\n" +
                                "Tipo de cuenta: c\n" +
                                "Moneda de la cuenta: c\n" +
                                "Número de targeta: 8210 6682 6662 8288\n" +
                                "Fecha de vencimiento: 16/11/2027\n" +
                                "CVV: 256\n" +
                                "\\\n" +
                                "|");
                    
                    write.close();
                } else if (path.contains("usuarios.txt")) {
                    write.write("Identificacion: 2\n" +
                                "Nombre: gerardo\n" +
                                "Contraseña: admin1234\n" +
                                "Puesto: ertyhju\n" +
                                "Año de ingreso: 2010\n" +
                                "Deshabilitado: false\n" +
                                "|");
                    write.close();
                }
            }

            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){
                datum += scanner.nextLine() + "\n";
            }

            
            scanner.close();
        } catch (IOException e) {
            this.showMessage("Error: " + e);
        }
        return datum.split("\\|");
    }
    
    public void changeData(String[] data, FileWriter file) {
        try {
            for (int j = 0; j < data.length; j++) {  
                if (j == data.length - 1) file.write(data[j] + "\n|");
                else file.write(data[j] + "\n");
            } 
            
        } catch (IOException e) {
            this.showMessage("Error: " + e);
        }  
    }
    
    public Matcher match(String matcher, String identificador, String input) {
            String regex = String.format("\\n?%2$s\\:\\s%1$s\\n", matcher, identificador);
            Pattern pattern = Pattern.compile(regex);
            return pattern.matcher(input);      
    }
    
    public String[] validar(String path) {
        int contador = 0;
        clsHandler clsH = new clsHandler();
        String datos = "";
        String[] data = this.getData(path);
        boolean existIdentificacion = false;
        String identificacion = "", contraseña = "", nombre = "";
                
        do {
            if (contador == 1) {
                if (identificacion.length() < 8) clsH.showMessage("La identificación debe tener 8 digitos o más");
                if (contraseña.length() < 8) clsH.showMessage("La contraseña debe tener 8 digitos o más");
                if (nombre.length() < 2) clsH.showMessage("Nombre invalido");
            }
            
            identificacion = clsH.inputString("Ingrese su identificación:");
            for (int i = 0; i < data.length; i++) {
                existIdentificacion = this.match(identificacion, "Identificacion", data[i]).find();
                if (existIdentificacion) {
                    clsH.showMessage("Identificación ya existente, por favor digite una identificación diferente");
                    break;
                }
            }
            if (existIdentificacion) continue; 
            
            nombre = clsH.inputString("Ingrese su nombre").toLowerCase();
            contraseña = clsH.inputString("Ingrese la contraseña:");
            nombre = nombre.toUpperCase().charAt(0) + nombre.substring(1);
            
            contador++;
        } while (identificacion.length() < 8 || nombre.length() < 2 || contraseña.length() < 8);
        datos = identificacion + ":" + nombre + ":" + contraseña;
        return datos.split("\\:");
    }
    
    public String[] inicioSesion(String pregunta, String[] data, String identificador, boolean matcherUsuario, String error, String datos) {
        int posicion = -1;
        String[] usuario = new String[4];
        String[] user = null;
        boolean encontrado = false;
        char continuar = ' ';
        String[] cuentas;
        
        
        String datosUsuario = this.inputString(pregunta).toLowerCase();
        for (int i = 0; i < data.length; i++) {    
            if (datos == "bancarios") {
                cuentas = this.getCuentas(data[i].split("\n"));
                for (int j = 0; j < cuentas.length; j++) {
                    matcherUsuario = this.match(datosUsuario, identificador, cuentas[j]).find();
                    if(!matcherUsuario) continue;
                    user = data[i].split("\n");
                    usuario[0] = data[i];
                    if (Boolean.parseBoolean(user[10].substring(11)))  {
                        this.showMessage("El usuario está bloqueado");
                        usuario[1] = String.valueOf(posicion);
                        return usuario;
                    } else {
                        posicion = i;
                        usuario[1] = String.valueOf(posicion);
                        encontrado = true;
                        usuario[2] = datosUsuario;
                        usuario[3] = cuentas[j].split("\n")[2].substring(20);
                        break;
                    }
                }
            } else {
                datosUsuario = datosUsuario.toUpperCase().charAt(0) + datosUsuario.substring(1);
                matcherUsuario = this.match(datosUsuario, identificador, data[i]).find();
                if (!matcherUsuario) continue;
                user = data[i].split("\n");
                usuario[0] = data[i];
                if (Boolean.parseBoolean(user[10].substring(11)))  {
                    this.showMessage("El usuario está bloqueado");
                    usuario[1] = String.valueOf(posicion);
                    return usuario;
                } else {
                    posicion = i;
                    usuario[1] = String.valueOf(posicion);
                    encontrado = true;
                    break;
                }
            
            }  
        }
        
        if (!encontrado) {
                this.showMessage(error);
                continuar = this.inputChar("¿Desea continuar?"
                        + "\ns) si"
                        + "\nn) no");
                if (continuar == 's') usuario = this.inicioSesion(pregunta, data, identificador, matcherUsuario, error, datos);
                else usuario[1] = String.valueOf(posicion);
            }
        return usuario;
    }
    
    public boolean verificarInicioSesion(String pregunta, int posicion, String[] user, String error, String clientesPath, String[] data, String identificador, int numeroLinea, String cuenta) {
        String ping = "";
        boolean match;
        
        
        for (int i = 0; i < 3; i++) {  
            
            ping = this.inputString(pregunta);
            match = this.match(ping, identificador, user[numeroLinea] + "\n").find();

            if (!match) {
                this.showMessage(error);
                if (i == 2) {

                    user[10] = "Bloqueado: true";
                    try {
                        FileWriter writer = new FileWriter(clientesPath);
                        for (int j = 0; j < data.length; j++) {
                            if (posicion == j) this.changeData(user, writer); 
                            else if (j != data.length - 1) writer.write(data[j] + "|");
                        }

                        writer.close();
                        this.showMessage("Usuario bloqueado");
                    } catch (IOException e) {
                        this.showMessage("Error: " + e);
                    }
                    return false;
                }
            } else return true;
        }
        return false;
    }
    
    public String[] getCuentas(String[] cliente) {
        String cuentas = "";
        for (int i = 0; i < cliente.length; i++) {
            if (i >= 13) {
                if(cliente[i].length() < 2) continue;
                cuentas += cliente[i] + "\n";
            }
        }
        return cuentas.split("\\\\");
    }
    
    public String[] generarDatosBancariosUnicos(String[] data, String numeroTargeta, String numeroCuenta) {
        String[] cuentas;
        String[] datosBancarios = new String[2];
        for (int i = 0; i < data.length; i++) {
            if (data[i].length() < 2) continue;
            cuentas = this.getCuentas(data[i].split("\n"));
            for (int j = 0; j < cuentas.length; j++) {
                String[] cuenta = cuentas[j].split("\n");
                if(cuentas[j].length() < 4) continue;
                
                
                if(cuenta[5].substring(19).equals(numeroTargeta)) {
                    numeroTargeta = (int) (Math.floor(Math.random() * (9000 - 1000) + 1000)) + 
                            " " + (int) (Math.floor(Math.random() * (9000 - 1000) + 1000)) + 
                            " " + (int) (Math.floor(Math.random() * (9000 - 1000) + 1000)) + 
                            " " + (int) (Math.floor(Math.random() * (9000 - 1000)) + 1000);
                    i = 0;
                    break;
                }

                if(cuenta[4].substring(18).equals(numeroCuenta)) {
                    numeroCuenta = String.valueOf((int) (Math.floor(Math.random() * (100000000 - 10000000) + 10000000)));
                    i = 0;
                    break;
                }
                
            }
      
        }
        datosBancarios[0] = numeroCuenta;
        datosBancarios[1] = numeroTargeta;
        return datosBancarios;
    }
    
    public String especificacionesCuenta() {
        char tipoCuenta = ' ';
        char monedaCuenta = ' ';
         do {
            tipoCuenta = this.inputChar("¿Que tipo de cuenta desea?:"
                + "\n c) corriente"
                + "\n a) ahorros");
        } while (tipoCuenta != 'c' && tipoCuenta != 'a');


        do {
            monedaCuenta = this.inputChar("¿Que tipo de moneda desea que tenga la cuenta?:"
                + "\n d) dolares"
                + "\n c) colones");
        } while (monedaCuenta != 'c' && monedaCuenta != 'd');
        
        return tipoCuenta + "|" + monedaCuenta;
    }
}
