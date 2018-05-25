package proyectofinal;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

/**
 * Clase que representa graficamente un objeto de tipo {@link proyectofinal.Pregunta Pregunta} en forma de un panel, heredando de {@link javax.swing.JPanel JPanel}
 * @author manuel
 */
public class PreguntaUI extends JPanel{
    private final Pregunta pregunta;
    private final ButtonGroup opciones;
    private final List<JOpcion> botones;
    private final int numeroPregunta;
    private boolean correcto;
    
    /**
     * Clase interna, solo relevante para la clase PreguntaUI, que hereda de JRadioButton y nos permite incluir un ID en cada objeto para seleccionar la opcion correcta
     */
    private class JOpcion extends JRadioButton {
        int id;
        
        /**
         * Constructor de JOpcion que inicializa el JRadioButton con el texto correspondiente
         * @param text texto de la opcion
         */
        public JOpcion(String text) {
            super(text);
        }
        
        /**
         * Regresa el id de la opcion
         * @return id
         */
        public int getId() {
            return id;
        }

        /**
         * Establece el parametro id como el id del objeto
         * @param id 
         */
        public void setId(int id) {
            this.id = id;
        }
    }
    
    public PreguntaUI(Pregunta pregunta, int numeroPregunta) {
        super();
        this.pregunta = pregunta;
        this.numeroPregunta = numeroPregunta;
        this.correcto = false;
        opciones = new ButtonGroup();
        botones = new ArrayList<>();
        Iterator it = pregunta.getRespuestas().entrySet().iterator();
        Map.Entry par;
        JOpcion opcion;
        while(it.hasNext()) {
            par = (Map.Entry) it.next();
            opcion = new JOpcion((String)par.getValue());
            opcion.setId((Integer)par.getKey());
            opcion.addActionListener((ActionEvent e) -> {
                JOpcion raiz = (JOpcion)e.getSource();
                if (raiz.getId() == pregunta.getCorrecto()) {
                    this.correcto = true;
                } else {
                    this.correcto = false;
                }
            });
            opciones.add(opcion);
            botones.add(opcion);
        }
        setUI();
    }
    
    private void setUI(){
        setBackground(Color.white);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(0, 0, 10, 0));
        JLabel lpregunta = new JLabel(this.numeroPregunta+".- ¿"+this.pregunta.getPregunta()+"?");
        lpregunta.setFont(new Font("Arial", Font.BOLD, 16));
        add(lpregunta);
        
        botones.stream().forEach((boton) -> {
            add(boton);
        });
    }
    
    public Pregunta getPregunta() {
        return pregunta;
    }
    
    public boolean isCorrecto() {
        return correcto;
    }
    
}
