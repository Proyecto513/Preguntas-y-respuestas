/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

/**
 *
 * @author manuel
 */
import java.util.HashMap;

public class Pregunta {
    private String pregunta;
    private HashMap<Integer, String> respuestas;
    private int correcta;
    
    /**
     *
     * @param pregunta
     * @param respuestas
     * @param correcto
     */
    public Pregunta(String pregunta, HashMap<Integer, String> respuestas, int correcto){
        this.pregunta = pregunta;
        this.respuestas = respuestas;
        this.correcta = correcto;
    }
    
    public Pregunta(){}

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public HashMap<Integer, String> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(HashMap<Integer, String> respuestas) {
        this.respuestas = respuestas;
    }

    public int getCorrecto() {
        return correcta;
    }

    public void setCorrecto(int correcto) {
        this.correcta = correcto;
    }

    @Override
    public String toString() {
        return this.pregunta + " " + this.respuestas + " " + this.correcta;
    }
    
    
}
