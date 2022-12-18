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
    
    private String[] reportesUsuario = new String[1000];
    private int numeroreportesUsuario = 0;
    
    private String datosUsuario = "";
    
    private String[] reportesTipo = new String[1000];
    private int numeroreportesTipo = 0;
    
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
    
    public void setTransaccionCuenta(String cuentaDestino,String cuenta, double montoTransferencia,double saldo,String tipo){
        //1. Detalle de la cuenta incluyendo el saldo
        //2. Tipo de transaccion
        //3. Monto
        //4. Fecha
        this.reportesUsuario[numeroreportesUsuario]="Cuenta origen: "+cuenta+"\n"+"Cuenta destino: "+cuentaDestino+"\n"+"Saldo actual: "+saldo+"\n"
                +"Tipo de transaccion"+tipo;
        this.numeroreportesUsuario++;
    }
    public void getTransaccionCuenta(){
        clsHandler clsH = new clsHandler();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String reporte=date.format(date)+"\n";
        for (int i = 0; i < this.reportesUsuario.length; i++) {
            if (this.reportesUsuario[i]==null) {
                break;
            }
            reporte+="transaccion#"+(i+1)+"\n"+this.reportesUsuario[i]+"\n";
        }
        clsH.showMessage(new TextArea(reporte));
    }
    public void setEstadoCuenta(double saldo,String nombre,String cedula,String cuenta,char monedaCuenta){
        //1. Detalle de la cuenta
        //2. Monto disponible
        //3. Datos personales
        this.datosUsuario="Nombre: "+nombre+"\n"+"Cedula: "+cedula+"\n"+"Monto disponible: "+saldo+"\n"+(monedaCuenta=='d'?"Dolares":"Colones");
    }
    public void getEstadoCuenta(){
        clsHandler clsH = new clsHandler();
        clsH.showMessage(new TextArea(this.datosUsuario));
    }
    public void setTransaccionTipo(String boucher){
        //1. Tipo de cuenta
        //2. Numero de cuenta
        //3. Monto de transaccion
        //4. Fecha de transaccion
        this.reportesTipo[numeroreportesTipo]=boucher;
        this.numeroreportesTipo++;
        
        
    }
    public void getTransaccionTipo(){
        clsHandler clsH = new clsHandler();
        String reporte="";
        for (int i = 0; i < this.reportesTipo.length; i++) {
            if (this.reportesTipo[i]==null) {
                break;
            }
            reporte+=this.reportesTipo[i]+"\n";
        }
        clsH.showMessage(new TextArea(reporte));
    }
}
