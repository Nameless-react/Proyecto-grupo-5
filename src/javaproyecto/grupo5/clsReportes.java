/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaproyecto.grupo5;

import java.awt.TextArea;

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
        if (this.transaccionesUsuarios[0] == null) {
            clsH.showMessage("No se han hecho transacciones");
            return;
        }
        //ordenar por fechas
        
        clsH.showMessage("");
        
    }
    
    public void setTransaccionesUsuario(String transaccion) {
        this.transaccionesUsuarios[numeroTransaccionesUsuarios] = transaccion;
        this.numeroTransaccionesUsuarios++;
    }
    
    
    public void getDatosCajero() {
        clsHandler clsH = new clsHandler();
        //totalizado por fechas
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
