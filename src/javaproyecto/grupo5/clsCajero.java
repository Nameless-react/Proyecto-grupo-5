/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaproyecto.grupo5;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author joel
 */
public class clsCajero {
    private int dinero;
    private String cajeroPath;
    private int veinteMil;
    private int cincoMil;
    private int diezMil;
    private int dosMil;
    private int mil;
    
    public clsCajero(String cajeroPath) {
        this.cajeroPath = cajeroPath;
    }
  
    public void initCajero() {
        clsHandler clsH = new clsHandler();
        String[] data = clsH.getData(this.cajeroPath);
        
        if(data.length == 1) {
            try {
                FileWriter writer = new FileWriter(this.cajeroPath);
                this.veinteMil = 100;
                this.diezMil = 200;
                this.cincoMil = 400;
                this.dosMil = 1000;
                this.mil = 2000;
                this.dinero = 10000000;
                
                writer.write("Dinero total: 10000000"
                        + "\n|\n"
                        + "Billetes veinte mil: 100"
                        + "\n|\n"
                        + "Billetes diez mil: 200"
                        + "\n|\n"
                        + "Billetes cinco mil: 400"
                        + "\n|\n"
                        + "Billetes dos mil: 1000"
                        + "\n|\n"
                        + "Billetes mil: 2000");
                
                writer.close();
            } catch(IOException e) {
                clsH.showMessage("Error: " + e);
            }
        } else {
            this.dinero = Integer.parseInt(data[0].split("\\:")[1].trim());
            this.veinteMil = Integer.parseInt(data[1].split("\\:")[1].trim());
            this.diezMil = Integer.parseInt(data[2].split("\\:")[1].trim());
            this.cincoMil = Integer.parseInt(data[3].split("\\:")[1].trim());
            this.dosMil = Integer.parseInt(data[4].split("\\:")[1].trim());
            this.mil = Integer.parseInt(data[5].split("\\:")[1].trim());     
        }
    }
    
    public void saveCajero() {
        clsHandler clsH = new clsHandler();
        try {
            FileWriter writer = new FileWriter(this.cajeroPath);


            writer.write("Dinero total: " + this.dinero
                    + "\n|\n"
                    + "Billetes veinte mil: " + this.veinteMil
                    + "\n|\n"
                    + "Billetes diez mil: " + this.diezMil
                    + "\n|\n"
                    + "Billetes cinco mil: " + this.cincoMil
                    + "\n|\n"
                    + "Billetes dos mil: " + this.dosMil
                    + "\n|\n"
                    + "Billetes mil: " + this.mil);

            writer.close();
        } catch(IOException e) {
            clsH.showMessage("Error: " + e);
        }
    }
    
    public int setVeinteMil(int nuevosBilletes, char tipo) {
        clsHandler clsH = new clsHandler();
        if (tipo == 'd') {
            if(this.veinteMil + nuevosBilletes > 100) {
                clsH.showMessage("La cantidad de billetes de veinte mil no puede ser mayor a 100");
                return -1;
            }
        
            this.setDinero(nuevosBilletes * 20000, 'd');
            return this.veinteMil += nuevosBilletes;
        } else {
            
            if(this.veinteMil - nuevosBilletes < 0) {
                clsH.showMessage("La cantidad de billetes de veinte mil no puede ser menor a 0");
                return -1;
            }
            
            this.setDinero(nuevosBilletes * 20000, 'e');
            return this.veinteMil -= nuevosBilletes;
        }
    }
    
    public int setDiezMil(int nuevosBilletes, char tipo) {
        clsHandler clsH = new clsHandler();
        if (tipo == 'd') {
            if(this.diezMil + nuevosBilletes > 200) {
                clsH.showMessage("La cantidad de billetes de diez mil no puede ser mayor a 200");
                return -1;
            }
            
            this.setDinero(nuevosBilletes * 5000, 'd');
            return this.diezMil += nuevosBilletes;
        } else {
            if (this.diezMil - nuevosBilletes < 0) {
                clsH.showMessage("La cantidad de billetes de diez mil no puede ser menor a 0");
                return -1;
            }
            this.setDinero(nuevosBilletes * 10000, 'e');
            return this.diezMil -= nuevosBilletes;
        }
    }
    
    public int setCincoMil(int nuevosBilletes, char tipo) {
        clsHandler clsH = new clsHandler();
        if (tipo == 'd') {
            if(this.cincoMil + nuevosBilletes > 400) {
                clsH.showMessage("La cantidad de billetes de cinco mil no puede ser mayor a 400");
                return -1;
            }
            this.setDinero(nuevosBilletes * 5000, 'd');
            return this.cincoMil += nuevosBilletes;
            
        } else {
            if (this.cincoMil - nuevosBilletes < 0) {
                clsH.showMessage("La cantidad de billetes de cinco mil no puede ser menor a 0");
                return -1;
                
            }
            this.setDinero(nuevosBilletes * 5000, 'e');
            return this.cincoMil -= nuevosBilletes; 
        }        
    }
    
    public int setDosMil(int nuevosBilletes, char tipo) {
        clsHandler clsH = new clsHandler();
        if (tipo == 'd') {
            if(this.dosMil + nuevosBilletes > 1000) {
                clsH.showMessage("La cantidad de billetes de dos mil no puede ser mayor a 1000");
                return -1;
            }
            this.setDinero(nuevosBilletes * 2000, 'd');
            return this.dosMil += nuevosBilletes; 
                        
        } else {
            if(this.dosMil - nuevosBilletes < 0) {
                clsH.showMessage("La cantidad de billetes de dos mil no puede ser menor a 0");
                return -1;
            }
            this.setDinero(nuevosBilletes * 2000, 'e');
            return this.dosMil -= nuevosBilletes;   
        }      
    }
    
    public int setMil(int nuevosBilletes, char tipo) {
        clsHandler clsH = new clsHandler();
        if(tipo == 'd') {
            if(this.mil + nuevosBilletes > 2000) {
                clsH.showMessage("La cantidad de billetes de mil no puede ser mayor a 2000");
                return -1;

            }
            this.setDinero(nuevosBilletes * 1000, 'd');
            return this.mil += nuevosBilletes;
            
        } else {
            if(this.mil - nuevosBilletes < 0) {
                clsH.showMessage("La cantidad de billetes de mil no puede ser menor a 0");
                return -1;

            }
            this.setDinero(nuevosBilletes * 1000, 'e');
            return this.mil -= nuevosBilletes;   
        }
    }
    
    public void setDinero(int nuevoDinero, char tipo) {
        clsHandler clsH = new clsHandler();
        if (tipo == 'd') {
            if (this.dinero + nuevoDinero > 10000000) {
                clsH.showMessage("La cantidad de dinero del cajero no puede ser mayor a 10 000 000");
            } 
            this.dinero += nuevoDinero;
        } else {
            if (this.dinero + nuevoDinero < 0) {
                clsH.showMessage("La cantidad de dinero del cajero no puede ser menor a 0");
            } 
            this.dinero -= nuevoDinero; 
        }
    }
    
    public int getDinero() {
        return this.dinero;
    }
    
    public int getVeinteMil() {
        return this.veinteMil;
    }
    
    public int getDiezMil() {
        return this.diezMil;
    }
    
    public int getCincoMil() {
        return this.cincoMil;
    }
    
    public int getDosMil() {
        return this.dosMil;
    }
    
    public int getMil() {
        return this.mil;
    }
    
    
}
