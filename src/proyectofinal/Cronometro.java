/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author manuel
 */
public class Cronometro extends JLabel implements Runnable{
    private final Hora hora;
    private Thread conteo;
    private boolean running;
    private int segundero;
    
    {
        hora = new Hora();
        segundero = 0;
        setText("00:00:00");
        setHorizontalAlignment(SwingConstants.RIGHT);
        setBorder(new EmptyBorder(0, 0, 0, 10));
    }
    
    public Hora getHora() {
        return hora;
    }
    
    public void arranque(){
        conteo = new Thread(this);
        running = true;
        conteo.start();
    }
    
    public void paro(){
        try {
            running = false;
            conteo.join();
            hora.setSegundos(segundero);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cronometro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrar(){
        setText(Hora.formatearSegundos(segundero));
    }

    @Override
    public void run() {
        while(running) {
            try {
                Thread.sleep(990);
                segundero++;
                mostrar();
            } catch (InterruptedException ex) {
                Logger.getLogger(Cronometro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
