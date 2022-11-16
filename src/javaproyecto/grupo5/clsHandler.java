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
    
    public float inputFloat(String message) {
        return Float.parseFloat(JOptionPane.showInputDialog(message));
    }
   
    public String[] getData(String path) {
        String datum = "";
        try {
            
            Scanner file = new Scanner(new File(path)); 

            while(file.hasNextLine()){
                datum += file.nextLine() + "\n";
            }

            
            file.close();
        } catch (IOException e) {
            this.showMessage("Error: " + e);
        }
        return datum.split("-");
    }
    
    public void changeData(String[] data, FileWriter file) {
        try {
            for (int j = 0; j < data.length; j++) {  
                if (j == data.length - 1) file.write(data[j] + "\n-");
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
}
