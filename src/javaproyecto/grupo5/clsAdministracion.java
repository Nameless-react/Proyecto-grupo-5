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
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Matcher;
import java.util.Calendar;


/**
 *
 * @author joel
 */
public class clsAdministracion {

    private String usuariosPath;
    private String clientesPath;
    
    public String administrador;
    public String id;
    
    private boolean sesion;
    
    public clsAdministracion (String usuariosPath, String clientesPath) {
        this.usuariosPath = usuariosPath;
        this.clientesPath = clientesPath;
    } 
    
    
    public boolean getSesion() {
        return this.sesion;
    }
    
    public void validacionContraseñaUsuario() {
        clsHandler clsH = new clsHandler();
        String identificacion = "", contraseña = "";
        char continuar = ' ';
        
        boolean bloqueado = false;
        boolean encontrado = false;
        String[] user = null;
        int posicion = -1;
        
        String[] data = clsH.getData(this.usuariosPath); 
        boolean matcherIdentificacion;
        Matcher matcherContraseña;
   
    
        do {
            identificacion = clsH.inputString("Escriba el número de identificación del usuario: ");
               
            
            for (int i = 0; i < data.length; i++) {                
                matcherIdentificacion = clsH.match(identificacion, "Identificacion", data[i]).find();
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
                clsH.showMessage("El usuario con número de identificación " + identificacion + " no existe");
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
                        
                        user[6] = "Deshabilitado: true";
                        try {
                            FileWriter writer = new FileWriter(this.usuariosPath);
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
                    this.administrador = nombre;
                    this.id = identificacion;
                    break;
                }   
            }
            
            
            
            break;
        } while (!bloqueado && continuar != 'n');
    }
    
    
    public void ingresoClientes(clsReportes clsR) {
        clsHandler clsH = new clsHandler();
        char option = ' ', cambio = ' ';
     
        String id = "";
        File exitsDataBase = new File(this.usuariosPath);
        
        String[] data;
        String[] client;
        
        
        Matcher matcher;
        do {
            if (!exitsDataBase.exists()) {
                clsH.showMessage("El archivo usuarios no existe, no podemos procesar las peticiones");
                return;
            }
            
            option = clsH.inputChar("Digite la opción que desea:"
                    + "\n c) ingresar cliente"
                    + "\n u) modificar cliente"
                    + "\n d) eliminar cliente"
                    + "\n r) Mostrar lista de clientes"
                    + "\n s) salir");
            
            
            switch (option) {
                case 'c':
                    char sexo = ' ', tipoCuenta = ' ', monedaCuenta = ' ';
                    String[] verificados = clsH.validar(this.clientesPath);
                    String identificacion = verificados[0], nombre = verificados[1], contraseña = verificados[2], telefono = "";
                    
                    do {
                        sexo = clsH.inputChar("Ingrese su sexo:"
                            + "\n m) masculino"
                            + "\n f) femenino");
                    } while (sexo != 'm' && sexo != 'f');

                    String nacimiento;
                    do {
                        
                        nacimiento = clsH.inputString("Ingrese su fecha de nacimiento formato dd/mm/aa:");
                        if (nacimiento.length() < 10) continue;
                        String[] format = nacimiento.split("/");
                        
                        LocalDate cumpleaños = LocalDate.of(Integer.parseInt(format[2]), Integer.parseInt(format[1]), Integer.parseInt(format[0]));
                        Period mayorEdad = Period.between(cumpleaños, LocalDate.now());
                        
                        if (mayorEdad.getYears() < 18) {
                            clsH.showMessage("No podemos brindarle nuestros servicios a personas menores de edad");
                            continue;
                        }
                    
                        break;
                    } while (true);
                    
                    
                    String ingresos = clsH.inputString("Ingrese la cantidad de dinero que recibe:");
                    String residencia = clsH.inputString("Ingrese su lugar de residencia:");
                    String correo = clsH.inputString("Digite su correo electronico:");
                    
                    
                    do { 
                       telefono = clsH.inputString("Digite su número de teléfono:");
                    } while (telefono.length() < 8);
                    
                    do {
                        tipoCuenta = clsH.inputChar("¿Que tipo de cuenta desea?:"
                            + "\n c) corriente"
                            + "\n a) ahorros");
                    } while (tipoCuenta != 'c' && tipoCuenta != 'a');
                    
                    
                    do {
                        monedaCuenta = clsH.inputChar("¿Que tipo de moneda desea que tenga la cuenta?:"
                            + "\n d) dolares"
                            + "\n c) colones");

                    } while (monedaCuenta != 'c' && monedaCuenta != 'd');

                    
                    try {
                        FileWriter dataBase = new FileWriter(this.clientesPath, true);
                        data = clsH.getData(this.clientesPath);
                        
                        String numeroTargeta = (int) (Math.floor(Math.random() * (9000 - 1000) + 1000)) +
                                " " + (int) (Math.floor(Math.random() * (9000 - 1000) + 1000)) +
                                " " + (int) (Math.floor(Math.random() * (9000 - 1000) + 1000)) + 
                                " " + (int) (Math.floor(Math.random() * (9000 - 1000)) + 1000);
                        
                        String numeroCuenta = String.valueOf((int) (Math.floor(Math.random() * (100000000 - 10000000) + 10000000)));
                        
                        
                        
                        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.YEAR, +5);
                        
                        //Hacer únicos los números de targetas y los números de cuentas bsncarias
                        for (int i = 0; i < data.length; i++) {
                            
                            if (data[i].length() < 2) continue;
                            
                            client = data[i].split("\n");
                            if(client[13].substring(19).equals(numeroTargeta)) {
                                numeroTargeta = (int) (Math.floor(Math.random() * (9000 - 1000) + 1000)) + 
                                        " " + (int) (Math.floor(Math.random() * (9000 - 1000) + 1000)) + 
                                        " " + (int) (Math.floor(Math.random() * (9000 - 1000) + 1000)) + 
                                        " " + (int) (Math.floor(Math.random() * (9000 - 1000)) + 1000);
                                i = 0;
                            }
                            
                            if(client[12].substring(18).equals(numeroCuenta)) {
                                numeroCuenta = String.valueOf((int) (Math.floor(Math.random() * (100000000 - 10000000) + 10000000)));
                                i = 0;
                            }
                        }

                        
                        String usuario = "\nIdentificacion: " + identificacion
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
                                       + "\nNúmero de cuenta: " + numeroCuenta
                                       + "\nNúmero de targeta: " + numeroTargeta
                                       + "\nMonto en la cuenta: 0"
                                       + "\nCVV: " + (int) (Math.floor(Math.random() * (900 - 100) + 100))
                                       + "\nPing: " + (int) (Math.floor(Math.random() * (900 - 100) + 100))
                                       + "\nFecha de vencimiento: " + date.format(calendar.getTime())
                                       + "\nBloqueado: false\n|";
                        
                        
                        dataBase.write(usuario);
                        clsR.setusuariosCreados(usuario);
                        dataBase.close();
                    } catch (IOException e) {
                        clsH.showMessage("Error: " + e);
                    }
                    break;
                case 'u':
                    id = clsH.inputString("Digite la identificacón del usuario que desea modificar");
                    
                    try {
                        data = clsH.getData(this.clientesPath);
                        FileWriter writer = new FileWriter(this.clientesPath);


                        for (int i = 0; i < data.length; i++) {
                            matcher = clsH.match(id, "Identificacion", data[i]);  

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
                                                                + "\n a) Fecha de nacimiento"
                                                                + "\n d) Monto en la cuenta");
                                String actualizado = clsH.inputString("Digite el dato actualizado:");

                                switch (cambio) {
                                    case 'n':
                                        while(actualizado.length() <= 2) {
                                            actualizado = clsH.inputString("Digite el dato actualizado");
                                        }
                                        client[2] = "Nombre: " + actualizado;
                                        break;

                                    case 'c':
                                        while(actualizado.length() < 8) {
                                            actualizado = clsH.inputString("Digite el dato actualizado");
                                        }
                                        client[3] = "Contraseña: " + actualizado;
                                        break;

                                    case 's':
                                        while(actualizado.charAt(0) != 'm' && actualizado.charAt(0) != 'f') {
                                            actualizado = clsH.inputString("Ingrese su sexo:"
                                                                    + "\n m) masculino"
                                                                    + "\n f) femenino");
                                        }
                                        client[4] = "Sexo: " +  actualizado;
                                        break;

                                    case 'a':
                                        int primero = 0;
                                        while (true) {
                                            if (actualizado.length() >= 10 && primero == 0) break;
                                            actualizado = clsH.inputString("Ingrese su fecha de nacimiento formato dd/mm/aa:");
                                            
                                            if (actualizado.length() >= 10) continue; 
                                            String[] format = actualizado.split("/");
                                            LocalDate cumpleaños = LocalDate.of(Integer.parseInt(format[2]), Integer.parseInt(format[1]), Integer.parseInt(format[0]));
                                            Period mayorEdad = Period.between(cumpleaños, LocalDate.now());

                                            if (mayorEdad.getYears() < 18) clsH.showMessage("No podemos brindarle nuestros servicios a personas menores de edad");
                                            primero++;
                                        }
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
                                        while(actualizado.length() < 8) {
                                            actualizado = clsH.inputString("Digite el dato actualizado");
                                        }
                                        client[9] = "Teléfono: " +  actualizado;
                                        break;

                                    case 'p':
                                        client[10] = "Tipo de cuenta: " +  actualizado;
                                        break;
                                        
                                    case 'm':
                                        client[11] = "Moneda de la cuenta: " +  actualizado;
                                        break;
                                        
                                    case 'd':
                                        client[14] = "Monto en la cuenta: " +  actualizado;
                                        break; 
                                }

                                clsH.changeData(client, writer); 
                                
                            } else {
                                if (i != data.length - 1) writer.write(data[i] + "|");
                            }

                        }
                        writer.close();
                    } catch (IOException e) {
                        clsH.showMessage("Error: " + e);
                    }
                    break;
                    
                case 'd':
                    
                    id = clsH.inputString("Digite la identificacón del cliente que desea eliminar");
                    
                    try {
                        data = clsH.getData(this.clientesPath);
                       

                        FileWriter delete = new FileWriter(this.clientesPath);
                        for (int i = 0; i < data.length; i++) {
                            matcher = clsH.match(id, "Identificacion", data[i]);
                            if (!matcher.find() && i != data.length - 1 ) delete.write(data[i] + "|");
                            
                        }

                        delete.close();
                    } catch (IOException e) {
                        clsH.showMessage("Error: " + e);
                    }
                    break;
                    
                case 'r':
                    data = clsH.getData(this.clientesPath);
                    clsH.showMessage(new TextArea(String.join("\n\n", data)));                        
                   
                    break;
                }
        } while (option != 's');
    }
    
    public void seguridad() {
        clsHandler clsH = new clsHandler();
        File exitsDataBase = new File(this.usuariosPath);
        
        char option = ' ', cambio = ' ';
        String id = "";
        
        String[] data;
        String[] user;
        
        Matcher matcher;
        
        do {
            if (!exitsDataBase.exists()) {
                clsH.showMessage("El archivo usuarios.txt no existe, no podemos procesar las peticiones");
                return;
            }

            
            
            try {
                option = clsH.inputChar("¿Que desea hacer?:"
                                + "\n c) ingresar nuevo usuario"
                                + "\n u) modificar usuario"
                                + "\n d) deshabilitar/habilitar usuario"
                                + "\n s) salir");

                switch (option){
                    case 'c':
                        String[] verificados = clsH.validar(this.usuariosPath);
                        String identificacion = verificados[0], nombre = verificados[1], contraseña = verificados[2], añoIngreso = "";
                        
                        
                        String puesto = clsH.inputString("Digite el puesto de trabajo:");
                        
                        do {
                            añoIngreso = clsH.inputString("Digite el año de ingreso:");
                        } while (añoIngreso.length() != 4);
                        
                       
                        FileWriter dataBase = new FileWriter(this.usuariosPath, true);


                        dataBase.write("\nIdentificacion: " + identificacion
                                       + "\nNombre: " + nombre            
                                       + "\nContraseña: " + contraseña            
                                       + "\nPuesto: " + puesto            
                                       + "\nAño de ingreso: " + añoIngreso            
                                       + "\nDeshabilitado: " + false + "\n|");            
                        dataBase.close();
                        break;
                    case 'u':
                        id = clsH.inputString("Digite la identificacón del usuario que desea modificar");
                        data = clsH.getData(this.usuariosPath); 
                        
                        
                        FileWriter writer = new FileWriter(this.usuariosPath);
                        
                        for (int i = 0; i < data.length; i++) {
                            matcher = clsH.match(id, "Identificacion", data[i]);
                            
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
                                        while (actualizado.length() <= 2) {
                                             actualizado = clsH.inputString("Digite un nombre valido:");
                                        }
                                        
                                        user[2] = "Nombre: " + actualizado;
                                        break;
                                    case 'c':
                                        while (actualizado.length() <= 8) {
                                            actualizado = clsH.inputString("Digite una contraseña valida:");
                                        }
                                        
                                        user[3] = "Contraseña: " + actualizado;
                                        break;
                                    case 'p':
                                        user[4] = "Puesto: " +  actualizado;
                                        break;
                                    case 'a':
                                        while (actualizado.length() != 4) {
                                            actualizado = clsH.inputString("Digite una un año valido:");
                                        }
                                        
                                        user[5] = "Año de ingreso: " +  actualizado;
                                        break; 
                                }
                                
                                clsH.changeData(user, writer); 
                            } else {
                                if (i != data.length - 1) writer.write(data[i] + "|");
                            }
                            
                        }
                        writer.close();
                        break;
                    case 'd':
                        id = clsH.inputString("Digite la identificacón del usuario que desea desabilitar"); 
                        data = clsH.getData(this.usuariosPath);
                        
                        
                        FileWriter disable = new FileWriter(this.usuariosPath);
                        for (int i = 0; i < data.length; i++) {
                            matcher = clsH.match(id, "Identificacion", data[i]);
                            if (matcher.find()) {
                                user = data[i].split("\n");
                                Boolean estado = !Boolean.valueOf(user[6].substring(15));
                                
                                
                                user[6] = "Deshabilitado: " + estado;
                                clsH.showMessage("Usuario con identificación: " + id + " ha sido " + (estado ? "deshabilitado" : "habilitado"));
                                
                                clsH.changeData(user, disable);
                                 
                            } else {
                                if (i != data.length - 1) disable.write(data[i] + "|");
                            }
                        }
                        
                        disable.close();
                        break;
                   
                }

            } catch (IOException e) {
                clsH.showMessage("Error: " + e);
            }
                
        } while (option != 's');
    }
    
    
    
    public void balances(clsCajero clsC) {
         clsHandler clsH = new clsHandler();
         
         
        //Variables para el cajero
        int billetesVeinte = 0, billetesDiez = 0, billetesCinco = 0, billetesDos = 0, billetesMil = 0;
        
        
        int opcion = 0;
        int exito = 0;
        long deposito = 0;
        
        char continuar = ' ', siguiente = ' ';
        String impresion = "";

        do {
            opcion = clsH.inputInt("Por favor digite la opcion que desea realizar: "
                    + "\n1) Consulta de dinero en cajero"
                    + "\n2) Depositar dinero al cajero"
                    + "\n3) Salir");

            switch (opcion) {
                case 1:
                    clsH.showMessage("El Saldo en el cajero es de: ₡" + clsC.getDinero() + " colones"
                            + "\nCantidad de billetes de veinte mil: " + clsC.getVeinteMil()
                            + "\nCantidad de billetes de diez mil: " + clsC.getDiezMil()
                            + "\nCantidad de billetes de cinco mil: " + clsC.getCincoMil()
                            + "\nCantidad de billetes de dos mil: " + clsC.getDosMil()
                            + "\nCantidad de billetes de mil: " + clsC.getMil());
                    break;
                case 2:
                    do {
                        billetesVeinte = clsH.inputInt("El saldo del cajero es de : ₡" + clsC.getDinero() + " colones"
                                + "\nCantidad de billetes de veinte mil " + clsC.getVeinteMil()
                                + "\nIngrese la cantidad de billetes de ₡20 000: ");

                        exito = clsC.setVeinteMil(billetesVeinte, 'd');

                        if (exito != -1){
                            clsH.showMessage("El depósito se a realizado con exito "
                                    + "\nLa cantidad de billetes de veinte mil es de " + clsC.getVeinteMil());

                            deposito += (billetesVeinte * 20000);                                
                        }
                        continuar = clsH.inputChar("Desea continuar: "
                                + "\ns) Si"
                                + "\nn) No");
                        if (continuar == 'n') break;
                        
                        
                        

                        billetesDiez = clsH.inputInt("El saldo del cajero es de : ₡" + clsC.getDinero() + " colones"
                                + "\nCantidad de billetes de diez mil " + clsC.getDiezMil()
                                + "\nIngrese la cantidad de billetes de ₡10 000: ");


                        exito = clsC.setDiezMil(billetesDiez, 'd');
                        if (exito != -1) {
                            clsH.showMessage("El depósito se a realizado con exito "
                                    + "\nLa cantidad de billetes de diez mil es de " + clsC.getDiezMil());

                            deposito += (billetesDiez * 10000);

                        }
                        continuar = clsH.inputChar("Desea continuar: "
                                + "\ns) Si"
                                + "\nn) No");
                        if (continuar == 'n') break;
                        
                            
                        
                        
                        
                        billetesCinco = clsH.inputInt("El saldo del cajero es de : ₡" + clsC.getDinero() + " colones"
                                + "\nCantidad de billetes cinco  mil " + clsC.getCincoMil()
                                + "\nIngrese la cantidad de billetes de ₡5 000: ");


                        exito = clsC.setCincoMil(billetesCinco, 'd');
                        if (exito != -1) {
                            clsH.showMessage("El depósito se a realizado con exito "
                                    + "\nLa cantidad de billetes de cinco mil es de " + clsC.getCincoMil());

                            deposito += (billetesCinco * 5000);
                        }
                        continuar = clsH.inputChar("Desea continuar: "
                                + "\ns) Si"
                                + "\nn) No");
                        if (continuar == 'n') break;
                        
                        
                        
                        
                        billetesDos = clsH.inputInt("El saldo del cajero es de : ₡" + clsC.getDinero() + " colones"
                                + "\nCantidad de billetes cinco  mil " + clsC.getDosMil()
                                + "\nIngrese la cantidad de billetes de ₡2 000: ");

                       exito = clsC.setDosMil(billetesDos, 'd');

                       if (exito != -1) {
                            clsH.showMessage("El depósito se a realizado con exito "
                                 + "\nLa cantidad de billetes de dos mil es de " + clsC.getDosMil());

                            deposito += (billetesDos * 2000);
                       }
                        continuar = clsH.inputChar("Desea continuar: "
                                + "\ns) Si"
                                + "\nn) No");
                        if (continuar == 'n') break;
                        
                        
                        
                        
                        billetesMil = clsH.inputInt("El saldo del cajero es de : ₡" + clsC.getDinero() + " colones"
                                + "\nCantidad de billetes cinco  mil " + clsC.getMil()
                                + "\nIngrese la cantidad de billetes de ₡1 000: ");

                        exito = clsC.setMil(billetesMil, 'd');
                        if (exito != -1) {
                            clsH.showMessage("El depósito se a realizado con exito "
                                + "\nLa cantidad de billetes de mil es de " + clsC.getMil());

                            deposito += (billetesMil * 1000);                                
                        }
                        
                        continuar = clsH.inputChar("¿Desea volver a hacer un deposito?"
                                + "\ns) Si"
                                + "\nn) No");
                    } while (continuar != 'n');
                    
                    if(deposito == 0) break;
                    impresion = "************DEPOSITO************\n"
                            + "TOTAL DEPOSITADO: " + clsC.getDinero() + "\n"
                            + "*************************************\n"
                            + "TOTAL BILLETES DE ₡20 000: " + billetesVeinte + "\n"
                            + "TOTAL BILLETES DE ₡10 000: " + billetesDiez + "\n"
                            + "TOTAL BILLETES DE ₡5 000: " + billetesCinco + "\n"
                            + "TOTAL BILLETES DE ₡2 000: " + billetesDos + "\n"
                            + "TOTAL BILLETES DE ₡1 000: " + billetesMil + "\n";
                    clsH.showMessage(new TextArea(impresion));
                    break;
                case 3:
                    break;
                default:
                    clsH.showMessage("La opcion que digito es incorrecta");
                    break;
            }
        } while (opcion != 3);
    }
}