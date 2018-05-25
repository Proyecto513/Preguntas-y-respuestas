/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.ObjectOutputStream;

/**
 *
 * @author manuel
 */
public class Usuario implements Serializable{
    
    private final String nombre, archivo;
    private int correctas, incorrectas;
    private Hora duracion;
    private boolean aprobado;
    
    public static String getNombreArchivo(String nombre){
        return nombre.replaceAll("[^a-zA-Z0-9\\.\\-]", "_")+".quiz";
    }
    
    public Usuario(String nombre) {
        this.nombre = nombre;
        this.archivo = Usuario.getNombreArchivo(nombre);
    }

    public String getNombre() {
        return nombre;
    }

    public int getCorrectas() {
        return correctas;
    }

    public int getIncorrectas() {
        return incorrectas;
    }

    public Hora getDuracion() {
        return duracion;
    }
    
    public String getArchivo()
    {
        return archivo;
    }
    
    public void setCorrectas(int correctas) {
        this.correctas = correctas;
    }

    public void setIncorrectas(int incorrectas) {
        this.incorrectas = incorrectas;
    }

    public void setDuracion(Hora duracion) {
        this.duracion = duracion;
    }
    
    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }
    
    public void guardar () throws java.io.IOException{
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(this.archivo)))) {
            oos.writeObject(this);
            oos.close();
        }
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", archivo=" + archivo + ", correctas=" + correctas + ", incorrectas=" + incorrectas + ", duracion=" + duracion + ", aprobado=" + aprobado + '}';
    }
     
}
