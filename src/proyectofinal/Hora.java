/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import java.io.Serializable;

/**
 *
 * @author manuel
 */
public class Hora implements Serializable{
    
    private int horas, minutos, segundos;
    
    public Hora()
    {
        horas = minutos = segundos = 0;
    }
    
    public Hora(int segundos) {
        this.segundos = segundos;
        this.minutos = this.horas = 0;
        normalizarHora();
    }
    
    public Hora(int horas, int minutos, int segundos) {
        this.horas = horas;
        this.minutos = minutos;
        this.segundos = segundos;
        normalizarHora();
    }
    
    private void normalizarHora()
    {
        if(this.segundos > 59) {
            while (this.segundos > 59) {
                this.segundos -= 60;
                this.minutos++;
            }
        }
        
        if(this.minutos > 59) {
            while (this.minutos > 59) {
                this.minutos -= 60;
                this.horas++;
            }
        }
    }
    
    public static String formatearSegundos(int segundos){
        int minutos = 0, horas = 0;
        
        if(segundos > 59){
            while(segundos > 59) {
                minutos++;
                segundos -= 60;
            }
        }
        
        if(minutos > 59){
            while(minutos > 59) {
                horas++;
                minutos -= 60;
            }
        }
        
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }
    
    public static Hora sumar(Hora h1, Hora h2)
    {
        return new Hora(h1.getHoras() + h2.getHoras(), h1.getMinutos() + h2.getMinutos(), h1.getSegundos() + h2.getSegundos());
    }
    
    public Hora sumar(Hora sumando)
    {
       return new Hora(this.horas + sumando.getHoras(), this.minutos + sumando.getMinutos(), this.segundos + sumando.getSegundos());
    }
    
    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
        normalizarHora();
    }

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
        normalizarHora();
    }
    
    public void imprimirHora()
    {
           System.out.println(String.format("%02d:%02d:%02d", horas, minutos, segundos));
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }
    
    
}
