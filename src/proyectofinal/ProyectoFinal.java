package proyectofinal;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Clase principal que establece el Look and Feel Nimbus para la aplicacíon y genera el objeto ventana de manera asincrona en el hilo de eventos de ventana de AWT
 * @author manuel
 * @version 1.0
 * @see javax.swing.plaf.nimbus.NimbusLookAndFeel
 * @see javax.swing.SwingUtilities#invokeLater(java.lang.Runnable)
 */
public class ProyectoFinal {

    /**
     * @param args the command line arguments
     */
    public static void main(String [] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ProyectoFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        SwingUtilities.invokeLater(() -> {
            Ventana ventana = new Ventana();
            ventana.setVisible(true);
        });
    }
}
