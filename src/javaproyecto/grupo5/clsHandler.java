/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaproyecto.grupo5;

import java.awt.TextArea;
import java.io.File;
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
    
    public Matcher match(String matcher, String identifier, String input) {
            String regex = String.format("\\n?%2$s\\:\\s%1$s\\n", matcher, identifier);
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
            
            nombre = clsH.inputString("Ingrese su nombre");
            contraseña = clsH.inputString("Ingrese la contraseña:");
            
            
            contador++;
        } while (identificacion.length() < 8 || nombre.length() < 2 || contraseña.length() < 8);
        datos = identificacion + ":" + nombre + ":" + contraseña;
        return datos.split("\\:");
    }
}
