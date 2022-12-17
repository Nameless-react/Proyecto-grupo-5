/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaproyecto.grupo5;

import java.awt.TextArea;
import java.text.SimpleDateFormat;

/**
 *
 * @author joel
 */
public class clsReportes {
    private String[] clientesCreados = new String[1000];
    private int numeroClientesCreado = 0;
    
    private String[] transaccionesUsuarios = new String[1000];
    private int numeroTransaccionesUsuarios = 0;   
    
    private int dineroExtraidoCliente = 0;    
    private int dineroIngresadoCliente = 0;    
    
    private int dineroIngresadoAdmin = 0;
    private int numeroVecesDineroIngresadoAdmin = 0;
    
    public void setusuariosCreados(String usuario) {
        this.clientesCreados[numeroClientesCreado] = usuario;
        this.numeroClientesCreado++;
    }
    
    public void getusuariosCreados() {
        clsHandler clsH = new clsHandler();
        if(this.numeroClientesCreado == 0) {
            clsH.showMessage("No se han creados nuevos clientes recientemente");
            return;
        } 
        clsH.showMessage(new TextArea(String.join("\n", this.clientesCreados).replace("\\|", "\n").replace("null", "")));
        
    }
    
    public void getTransaccionesUsuario() {
        clsHandler clsH = new clsHandler();
        String reportesUsuarios = "";
        
        if (this.transaccionesUsuarios[0] == null) {
            clsH.showMessage("No se han hecho transacciones");
            return;
        }
        
        for (int i = 0; i < this.transaccionesUsuarios.length; i++) {
            if (this.transaccionesUsuarios[i] == null) break;
            reportesUsuarios += this.transaccionesUsuarios[i];
            
        }
        
        
        clsH.showMessage(new TextArea(String.join("\n", reportesUsuarios)));
        
    }
    
    public void setTransaccionesUsuario(String transaccion) {
        this.transaccionesUsuarios[numeroTransaccionesUsuarios] = transaccion;
        this.numeroTransaccionesUsuarios++;
    }
    
    
    public void getDatosCajero() {
        clsHandler clsH = new clsHandler();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String datos = date.format(date)
                       + "\n" + "*".repeat(20)
                       + "\nTotal de dinero ingresado por los administradores: " + this.dineroIngresadoAdmin
                       + "\nTotal de dinero ingresado por los clientes: " + this.dineroIngresadoCliente 
                       + "\nTotal de dinero extraido por los clientes: " + this.dineroIngresadoCliente
                       + "\nCantidad de veces que se ha ingresado dinero por parte de los administradores: " + this.numeroVecesDineroIngresadoAdmin 
                       + "\nCantidad de transacciones hechas por los clientes: " + this.numeroTransaccionesUsuarios;
        
      clsH.showMessage(new TextArea(datos));
        
        
    }
    public void setDineroIngresadoAdmin(long dinero) {
        this.dineroIngresadoAdmin += dinero;
    }
    
    public void setnumeroVecesDineroIngresadoAdmin() {
        this.numeroVecesDineroIngresadoAdmin++;
    }
    
    public void setDineroExtraidoCliente(int dinero) {
        this.dineroExtraidoCliente += dinero;
    }
    
    public void setDineroIngresadoCliente(int dinero) {
        this.dineroIngresadoCliente += dinero;
    }   
}
