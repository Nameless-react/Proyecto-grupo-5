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
   
    public String[] getData(String path) {
        String datum = "";
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
                FileWriter write = new FileWriter(path);
                if (path.contains("clientes.txt")) {
                    write.write("\nIdentificacion: 10\n" +
                                "Nombre: juan\n" +
                                "Contraseña: admin1234\n" +
                                "Sexo: m\n" +
                                "Fecha de nacimiento: 3/2/1999\n" +
                                "Ingresos: 123456789\n" +
                                "Residencia: san cristobal\n" +
                                "Correo: frgthyjui654\n" +
                                "Teléfono: 2345687654\n" +
                                "Tipo de cuenta: c\n" +
                                "Moneda de la cuenta: c\n" +
                                "Número de cuenta: 71709346\n" +
                                "Número de targeta: 6205 8741 4648 2102\n" +
                                "Monto en la cuenta: 0\n" +
                                "CVV: 556\n" +
                                "Fecha de vencimiento: 16/11/2027\n" +
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
            
            
            contador++;
        } while (identificacion.length() < 8 || nombre.length() < 2 || contraseña.length() < 8);
        datos = identificacion + ":" + nombre + ":" + contraseña;
        return datos.split("\\:");
    }
    
    public String[] inicioSesion(String pregunta, String[] data, String identificador, boolean matcherUsuario, String error) {
        int posicion = -1;
        String[] usuario = new String[2];
        String cuenta = "";
        String[] user = null;
        boolean encontrado = false;
        char continuar = ' ';
        
        cuenta = this.inputString(pregunta).toLowerCase();
        for (int i = 0; i < data.length; i++) {                
            matcherUsuario = this.match(cuenta, identificador, data[i]).find();
            if (!matcherUsuario) continue;

            user = data[i].split("\n");
            usuario[0] = data[i];
            if (Boolean.parseBoolean(user[18].substring(11)))  {
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
        
        if (!encontrado) {
                this.showMessage(error);
                continuar = this.inputChar("¿Desea continuar?"
                        + "\ns) si"
                        + "\nn) no");
                if (continuar == 's') this.inicioSesion(pregunta, data, identificador, matcherUsuario, error);
                usuario[1] = String.valueOf(posicion);
            }
        return usuario;
    }
    
    public boolean verificarInicioSesion(String pregunta, int posicion, String[] user, String error, String clientesPath, String[] data, String identificador, int numerolinea) {
        String ping = "";
        Matcher match;
        
        for (int i = 0; i < 3; i++) {
            ping = this.inputString(pregunta);
            match = this.match(ping, identificador, user[numerolinea] + "\n");

            if (!match.find()) {
                this.showMessage(error);
                if (i == 2) {

                    user[18] = "Bloqueado: true";
                    try {
                        FileWriter writer = new FileWriter(clientesPath);
                        for (int j = 0; j < data.length; j++) {
                            if (posicion == j) this.changeData(user, writer); 
                            else if (j != data.length - 1) writer.write(data[j] + "|");
                        }

                        writer.close();

                    } catch (IOException e) {
                        this.showMessage("Error: " + e);
                    }
                    return false;
                }
            } else return true;
        }
        return false;
    }
}
