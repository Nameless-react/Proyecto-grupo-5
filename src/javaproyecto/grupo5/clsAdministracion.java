/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaproyecto.grupo5;


import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;


/**
 *
 * @author joel
 */
public class clsAdministracion {
    public void validacionContraseñaUsuario(String contraseña, String usuario) {
            
    }
    
    public void seguridad() {
        clsHandler clsH = new clsHandler();
        
        String path = "dataBase.txt";
        File exitsDataBase = new File(path);
        
        char option = ' ';
        String datum = "";
        char cambio = ' ';
        String id = "";
        String[] data;
        String[] user;
        
        Pattern pattern;
        Matcher matcher;
        
        do {
            if (!exitsDataBase.exists()) {
                clsH.showMessage("El archivo dataBase no existe, no podemos procesar las peticiones");
                return;
            }

            
            
            try {
                option = clsH.inputChar("¿Que desea hacer?:"
                                + "\n i) ingresar nuevo usuario"
                                + "\n m) modificar usuario"
                                + "\n d) deshabilitar/habilitar usuario"
                                + "\n s) salir");

                switch (option){
                    case 'i':
                        String identificacion = clsH.inputString("Digite la identificación:");
                        String nombre = clsH.inputString("Digite el nombre");
                        String contraseña = clsH.inputString("Digite la contraseña:");
                        String puesto = clsH.inputString("Digite el puesto de trabajo:");
                        String añoIngreso = clsH.inputString("Digite el año de ingreso:");


                        FileWriter dataBase = new FileWriter(path, true);


                        dataBase.write("\nIdentificacion: " + identificacion
                                       + "\nNombre: " + nombre            
                                       + "\nContraseña: " + contraseña            
                                       + "\nPuesto: " + puesto            
                                       + "\nAño de ingreso: " + añoIngreso            
                                       + "\nDeshabilitado: " + false + "\n-");            
                        dataBase.close();
                        break;
                    case 'm':
                        datum = "";
                        cambio = ' ';
                        
                        id = clsH.inputString("Digite la identificacón del usuario que desea modificar");
                        
                        Scanner reader = new Scanner(new File(path));
                        
                        String regexp = String.format("\\n?Identificacion\\:\\s%s", id);
                        pattern = Pattern.compile(regexp);
                        
                        
                        while (reader.hasNextLine()) {
                            datum += reader.nextLine() + "\n";
                        }
                        
                        reader.close();
                        data = datum.split("-");
                        FileWriter writer = new FileWriter(path);
                        
                        
                        for (int i = 0; i < data.length; i++) {
                            matcher = pattern.matcher(data[i]);
                            
                            
                            if (matcher.find()) {
                                user = data[i].split("\n");
                                
                                cambio = clsH.inputChar("¿Que desea cambiar?"
                                                                + "\n n) Nombre"
                                                                + "\n c) Contraseña"
                                                                + "\n p) Puesto"
                                                                + "\n a) Año de ingreso");
                                String actualizado = clsH.inputString("Digite el dato actualizado:");
                           
                                switch (cambio) {
                                    case 'n':
                                        user[2] = "Nombre: " + actualizado;
                                        break;
                                    case 'c':
                                        user[3] = "Contraseña: " + actualizado;
                                        break;
                                    case 'p':
                                        user[4] = "Puesto: " +  actualizado;
                                        break;
                                    case 'a':
                                        user[5] = "Año de ingreso: " +  actualizado;
                                        break; 
                                }
                                
                                for (int j = 0; j < user.length; j++) {  
                                    if (j == user.length - 1) writer.write(user[j]);
                                    else writer.write(user[j] + "\n");
                                } 
                            } else {
                                writer.write(data[i] + "-");
                            }
                            
                        }
                        writer.close();
                        break;
                    case 'd':
                        datum = "";
                        id = clsH.inputString("Digite la identificacón del usuario que desea modificar");
                        
                        Scanner readerDelete = new Scanner(new File(path));
                        Pattern regex = Pattern.compile(String.format("\\n?Identificacion\\:\\s%s", id));
                        
                        while (readerDelete.hasNextLine()) {
                            datum += readerDelete.nextLine() + "\n";
                        }
                        data = datum.split("-");
                        
                        FileWriter delete = new FileWriter(path);
                        for (int i = 0; i < data.length; i++) {
                            matcher = regex.matcher(data[i]);
                            if (matcher.find()) {
                                user = data[i].split("\n");
                                Boolean estado = !Boolean.valueOf(user[6].substring(15));
                                
                                
                                user[6] = "Deshabilitado: " + estado;
                                clsH.showMessage("Usuario con identificación: " + id + " ha sido " + (estado ? "deshabilitado" : "habilitado"));
                                
                                for (int j = 0; j < user.length; j++) {  
                                    if (j == user.length - 1) delete.write(user[j]);
                                    else delete.write(user[j] + "\n");
                                } 
                            } else {
                                delete.write(data[i] + "-");
                            }
                        }
                        
                        delete.close();
                        break;
                }

            } catch (IOException e) {
                clsH.showMessage("Error: " + e);
            }
                
        } while (option != 's');
    }
    
    
    
    public void balances() {
        
    }
}
