/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaproyecto.grupo5;


import java.awt.TextArea;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author joel
 */
public class clsAdministracion {

    public String usuariosPath;
    public String clientesPath;
    
    public clsAdministracion (String usuariosPath, String clientesPath) {
        this.usuariosPath = usuariosPath;
        this.clientesPath = clientesPath;
    } 
    
    
    public void validacionContraseñaUsuario() {
        clsHandler clsH = new clsHandler();
        String usuario;
        int intentos = 0;
        boolean bloqueado = false;
        char opcion = ' ';
        String datum = "";
        String[] data;
    
    
        do {
            usuario = clsH.inputString("Escriba su nombre de usuario: ");
            data = clsH.getData(this.usuariosPath);

            for (int i = 0; i < data.length; i++) {

            }
            intentos++;

        } while (intentos < 3);
    }
    
    
    public void ingresoClientes() {
        clsHandler clsH = new clsHandler();
        char option = ' ', cambio = ' ';
     
        String id = "", datum = "";
        
        
        String[] data;
        String[] client;
        
        Pattern pattern;
        Matcher matcher;
        do {
            option = clsH.inputChar("Digite la opción que desea:"
                    + "\n c) ingresar cliente"
                    + "\n u) modificar cliente"
                    + "\n d) eliminar cliente"
                    + "\n r) Mostrar lista de clientes"
                    + "\n s) salir");
            
            
            switch (option) {
                case 'c':
                    String identificacion = clsH.inputString("Ingrese su identificación:");
                    String nombre = clsH.inputString("Ingrese su nombre");
                    String contraseña = clsH.inputString("Ingrese la contraseña:");
                    char sexo = clsH.inputChar("Ingrese su sexo:"
                            + "\n m) masculino"
                            + "\n f) femenino");
                    String nacimiento = clsH.inputString("Ingrese su fecha de nacimiento formato dd/mm/aa:");
                    String ingresos = clsH.inputString("Ingrese la cantidad de dinero que recibe:");
                    String residencia = clsH.inputString("Ingrese su lugar de residencia:");
                    String correo = clsH.inputString("Digite su correo electronico:");
                    String telefono = clsH.inputString("Digite su numero de telefono:");
                    char tipoCuenta = clsH.inputChar("¿Que tipo de cuenta desea?:"
                            + "\n c) corriente"
                            + "\n a) ahorros");
                    char monedaCuenta = clsH.inputChar("¿Que tipo de moneda desea que tenga la cuenta?:"
                            + "\n d) dolares"
                            + "\n c) colones");

                    
                    try {
                        FileWriter dataBase = new FileWriter(this.clientesPath, true);
                        String numeroTargeta = (int) (Math.floor(Math.random() * (9000 - 1000) + 1000)) + " " + (int) (Math.floor(Math.random() * (9000 - 1000) + 1000)) + " " + (int) (Math.floor(Math.random() * (9000 - 1000) + 1000)) + " " + (int) (Math.floor(Math.random() * (9000 - 1000)) + 1000);
      
                        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.YEAR, +5);
                        
                        dataBase.write("\nIdentificacion: " + identificacion
                                       + "\nNombre: " + nombre            
                                       + "\nContraseña: " + contraseña            
                                       + "\nSexo: " + sexo            
                                       + "\nFecha de nacimiento: " + nacimiento            
                                       + "\nIngresos: " + ingresos            
                                       + "\nResidencia: " + residencia            
                                       + "\nCorreo: " + correo            
                                       + "\nTeléfono: " + telefono            
                                       + "\nTipo de cuenta: " + tipoCuenta            
                                       + "\nMoneda de la cuenta: " + monedaCuenta            
                                       + "\nNúmero de cuenta: " + (int) (Math.floor(Math.random() * (10000000 - 100000000) + 10000000))
                                       + "\nNúmero de targeta: " + numeroTargeta
                                       + "\nMonto en la cuenta: 0"
                                       + "\nCVV: " + (int) (Math.floor(Math.random() * (900 - 100) + 100))
                                       + "\nFecha de vencimiento: " + date.format(calendar.getTime())
                                       + "\nDeshabilitado: " + false + "\n-");            
                        dataBase.close();
                    } catch (IOException e) {
                        clsH.showMessage("Error: " + e);
                    }
                    break;
                case 'u':
                    datum = "";
                    cambio = ' ';

                    id = clsH.inputString("Digite la identificacón del usuario que desea modificar");
                    
                    try {
                        Scanner reader = new Scanner(new File(this.clientesPath));

                        String regexp = String.format("\\n?Identificacion\\:\\s%s", id);
                        pattern = Pattern.compile(regexp);


                        while (reader.hasNextLine()) {
                            datum += reader.nextLine() + "\n";
                        }

                        reader.close();
                        data = datum.split("-");
                        FileWriter writer = new FileWriter(this.clientesPath);


                        for (int i = 0; i < data.length; i++) {
                            matcher = pattern.matcher(data[i]);


                            if (matcher.find()) {
                                client = data[i].split("\n");

                                cambio = clsH.inputChar("¿Que desea cambiar?"
                                                                + "\n n) Nombre"
                                                                + "\n c) Contraseña"
                                                                + "\n m) Tipo de moneda"
                                                                + "\n p) Tipo de cuenta"
                                                                + "\n t) Teléfono"
                                                                + "\n e) Correo electronico"
                                                                + "\n s) Sexo"
                                                                + "\n i) Ingresos"
                                                                + "\n r) Residencia"
                                                                + "\n a) Fecha de nacimiento");
                                String actualizado = clsH.inputString("Digite el dato actualizado:");

                                switch (cambio) {
                                    case 'n':
                                        client[2] = "Nombre: " + actualizado;
                                        break;

                                    case 'c':
                                        client[3] = "Contraseña: " + actualizado;
                                        break;

                                    case 's':
                                        client[4] = "Sexo: " +  actualizado;
                                        break;

                                    case 'a':
                                        client[5] = "Fecha de nacimiento: " +  actualizado;
                                        break;

                                    case 'i':
                                        client[6] = "Ingresos: " +  actualizado;
                                        break;

                                    case 'r':
                                        client[7] = "Residencia: " +  actualizado;
                                        break;
                                        
                                    case 'e':
                                        client[8] = "Correo: " +  actualizado;
                                        break;

                                    case 't':
                                        client[9] = "Teléfono: " +  actualizado;
                                        break;

                                    case 'p':
                                        client[10] = "Tipo de cuenta: " +  actualizado;
                                        break; 
                                    case 'm':
                                        client[11] = "Moneda de la cuenta: " +  actualizado;
                                        break; 
                                }


                                for (int j = 0; j < client.length; j++) {  
                                    if (j == client.length - 1) writer.write(client[j] + "\n-");
                                    else writer.write(client[j] + "\n");
                                } 
                            } else {
                                if (i != data.length - 1) writer.write(data[i] + "-");
                            }

                        }
                        writer.close();
                    } catch (IOException e) {
                        clsH.showMessage("Error: " + e);
                    }
                    break;
                    
                case 'd':
                    datum = "";
                    id = clsH.inputString("Digite la identificacón del usuario que desea eliminar");
                    
                    try {
                        Scanner readerDelete = new Scanner(new File(this.clientesPath));
                        Pattern regex = Pattern.compile(String.format("\\n?Identificacion\\:\\s%s", id));

                        while (readerDelete.hasNextLine()) {
                            datum += readerDelete.nextLine() + "\n";
                        }
                        data = datum.split("-");

                        FileWriter delete = new FileWriter(this.clientesPath);
                        
                        for (int i = 0; i < data.length; i++) {
                            matcher = regex.matcher(data[i]);
                            if (!matcher.find() && i != data.length -1 ) delete.write(data[i] + "-");
                            
                        }

                        delete.close();
                    } catch (IOException e) {
                        clsH.showMessage("Error: " + e);
                    }
                    break;
                    
                case 'r':
                    try {
                        datum = "";
                        Scanner reader = new Scanner(new File(this.clientesPath));
                        while (reader.hasNextLine()) {
                                datum += reader.nextLine() + "\n";
                            }
                        clsH.showMessage(new TextArea(datum));                        
                    } catch (IOException e) {
                        clsH.showMessage("Error: " + e);
                    }
                    
                    break;
                }
        } while (option != 's');
    }
    
    public void seguridad() {
        clsHandler clsH = new clsHandler();
        File exitsDataBase = new File(this.usuariosPath);
        
        char option = ' ', cambio = ' ';
        String datum = "", id = "";
        
        String[] data;
        String[] user;
        
        Pattern pattern;
        Matcher matcher;
        
        do {
            if (!exitsDataBase.exists()) {
                clsH.showMessage("El archivo usuarios no existe, no podemos procesar las peticiones");
                return;
            }

            
            
            try {
                option = clsH.inputChar("¿Que desea hacer?:"
                                + "\n i) ingresar nuevo usuario"
                                + "\n m) modificar usuario"
                                + "\n d) deshabilitar/habilitar usuario"
                                + "\n s) salir");

                switch (option){
                    case 'c':
                        String identificacion = clsH.inputString("Digite la identificación:");
                        String nombre = clsH.inputString("Digite el nombre");
                        String contraseña = clsH.inputString("Digite la contraseña:");
                        String puesto = clsH.inputString("Digite el puesto de trabajo:");
                        String añoIngreso = clsH.inputString("Digite el año de ingreso:");
                        
                       
                        FileWriter dataBase = new FileWriter(this.usuariosPath, true);


                        dataBase.write("\nIdentificacion: " + identificacion
                                       + "\nNombre: " + nombre            
                                       + "\nContraseña: " + contraseña            
                                       + "\nPuesto: " + puesto            
                                       + "\nAño de ingreso: " + añoIngreso            
                                       + "\nDeshabilitado: " + false + "\n-");            
                        dataBase.close();
                        break;
                    case 'u':
                        datum = "";
                        cambio = ' ';
                        
                        id = clsH.inputString("Digite la identificacón del usuario que desea modificar");
                        
                        Scanner reader = new Scanner(new File(this.usuariosPath));
                        
                        String regexp = String.format("\\n?Identificacion\\:\\s%s", id);
                        pattern = Pattern.compile(regexp);
                        
                        
                        while (reader.hasNextLine()) {
                            datum += reader.nextLine() + "\n";
                        }
                        
                        reader.close();
                        data = datum.split("-");
                        FileWriter writer = new FileWriter(this.usuariosPath);
                        
                        
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
                        id = clsH.inputString("Digite la identificacón del usuario que desea desabilitar");
                        
                        Scanner readerDelete = new Scanner(new File(this.usuariosPath));
                        Pattern regex = Pattern.compile(String.format("\\n?Identificacion\\:\\s%s", id));
                        
                        while (readerDelete.hasNextLine()) {
                            datum += readerDelete.nextLine() + "\n";
                        }
                        data = datum.split("-");
                        
                        FileWriter delete = new FileWriter(this.usuariosPath);
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
                        
                    case 'r':
                        datum = "";
                        Scanner read = new Scanner(new File(this.usuariosPath));
                        while (read.hasNextLine()) {
                            datum += read.nextLine() + "\n";
                        }
                        clsH.showMessage(new TextArea(datum));
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
            opcion = clsH.inputInt("Por favor digite la opción que desea realizar: "
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
