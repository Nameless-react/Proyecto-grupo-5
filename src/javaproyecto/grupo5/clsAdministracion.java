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
    public void validacionContraseñaUsuario() {
        clsHandler clsH = new clsHandler();
        String usuario1 = "", contra1 = "", usuario2 ="", contra2 = "";
        int contador = 0;
        boolean logueado = false;
        char opcion = ' ';
        String data = "";
        String[] espacio;
    
    
        do {
            try{

                usuario2 = clsH.inputString("Escriba su nombre de usuario:");
                File Usuarios = new File("dataBase.txt");
                Scanner hola = new Scanner(Usuarios); 

                while(hola.hasNextLine()){
                    data+=hola.nextLine();
                }
                espacio = data.split("-");
                for (int i = 0; i<espacio.length; i++) {
                    System.out.println(espacio[i]);
                }
                break;
                //contador++;
            }catch(IOException e){
                clsH.showMessage("ERROR ARCHIVO NO ENCONTRADO");
            }

        } while (contador < 3);
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
        clsHandler clsH = new clsHandler();

        String dinero = "";
        int opcion = 0;
        float saldoActual = 0, deposito = 0, retiro = 0.0f;
        char siguiente = ' ', siguiente2 = ' ';

        do {
            opcion = clsH.inputInt("Por favor digite la opcion que desea realizar: "
                    + "\n1) Consulta de cuentas"
                    + "\n2) Deposito"
                    + "\n3) Retiro"
                    + "\n4) Salir");

            switch (opcion) {
                case 1:
                    clsH.showMessage("El Saldo en su cuenta es de: " + saldoActual + " colones");
                    break;
                case 2:
                    do {
                        deposito = clsH.inputFloat("Su saldo actual es de: " + saldoActual
                                + " \nIngrese el monto que desea depositar: ");
                        if (deposito >= 1000) {
                            saldoActual = saldoActual + deposito;
                            clsH.showMessage("El depósito se a realizado con exito \nEl saldo en su cuenta es de: " + saldoActual);
                        } else {
                            clsH.showMessage("El depósito debe ser mayor a 1000 colones");
                        }
                        siguiente = clsH.inputChar("Desea continuar: \nSi\nNo");
                    } while (siguiente == 'S' && siguiente != 'N');
                    break;
                case 3:
                    do {
                        retiro = clsH.inputFloat("Su saldo actual es de: " + saldoActual + " \nIngrese el monto que desea retirar: ");
                        if (retiro > saldoActual) {
                            clsH.showMessage("No cuenta con suficiente dinero en la cuenta");
                        } else if (retiro >= 1000) {
                            saldoActual -= retiro;
                            clsH.showMessage("El retiro de su cuenta se ah realizado con exito \nEl saldo en su cuenta es de: " + saldoActual);
                        } else {
                            clsH.showMessage("El retiro debe ser mayor a 1000 colones");
                        }
                        siguiente2 = clsH.inputChar("Desea continuar: \nSi\nNo");
                    } while (siguiente2 == 'S' && siguiente2 != 'N');
                    break;
                case 4:
                    break;
                default:
                    clsH.showMessage("La opcion que dijito es incorrecta");
                    break;
            }
        } while (opcion != 4);
    }
}
