package proyectofinal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Clase Ventana que hereda de JFrame para generar un frame que orqueste la ejecución de la logica de los componentes individuales del programa
 * @author manuel
 * @see javax.swing.JFrame
 */
public class Ventana extends JFrame{
    private JPanel begin, questionario, resultados;
    private JScrollPane scrollpane;
    private Usuario usuario;
    private JButton enviar;
    private final List<PreguntaUI> preguntas;
    private final Cronometro cronometro;
    private JLabel instrucciones;
    private boolean usuarioPasado;
    private static final int VALOR_APROBADO = 7;
    private String inputText;
    
    /**
     *  Constructor unico de la clase @Ventana 
     */
    public Ventana()
    {
        setupInicio();
    }
    
    /**
     * Metodo anonimo secundario al constructor que asegura que el objeto este completamente construido antes de modificar sus propiedades sobreescribibles
     */
    
    {
        usuarioPasado = false;
        preguntas = new ArrayList<>();
        cronometro = new Cronometro();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cuestionario");
        setSize(300, 400);
    }
     /**
      * Metodo sin retorno que inicializa los componentes de la primera pantalla del programa y maneja el acceso a los archivos de resultados del usuario
      * @return None
      * @see javax.swing.JPanel
      * @see java.nio.file.Files;
      */
    private void setupInicio() {
        begin = new JPanel();
        begin.setLayout(new BoxLayout(begin, BoxLayout.PAGE_AXIS));
        JLabel titulo = new JLabel("Cuestionario", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        begin.add(titulo);
        JLabel lnombre = new JLabel("Nombre");
        begin.add(lnombre);
        JTextField inp = new JTextField();
        inp.setMaximumSize(new Dimension(Integer.MAX_VALUE, inp.getPreferredSize().height));
        begin.add(inp);
        JButton iniciar = new JButton("Iniciar");
        iniciar.addActionListener((ActionEvent e) -> {
            if (inp.getDocument().getLength() > 0) {
                inputText = inp.getText();
                Path userpath = Paths.get(Usuario.getNombreArchivo(inputText));
                if (Files.exists(userpath)) {
                    int nuevo = JOptionPane.showConfirmDialog(this, "¿Desea ver su resultado anterior?", "Resultados anteriores", JOptionPane.YES_NO_OPTION);
                    if (nuevo == 0) {
                        try {
                            ObjectInputStream istream = new ObjectInputStream(new FileInputStream(Usuario.getNombreArchivo(inp.getText())));
                            usuario = (Usuario) istream.readObject();
                            usuarioPasado = true;
                            setupResultado();
                        } catch (IOException | ClassNotFoundException ex) {
                            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println(ex.getClass());
                            JOptionPane.showMessageDialog(Ventana.this, "Error al abrir archivo del usuario, iniciando nuevo cuestionario", "Error de archivo", JOptionPane.ERROR_MESSAGE);
                            viaCuestionario();
                        }
                    } else {
                        viaCuestionario();
                    }
                } else {
                    viaCuestionario();
                }
            } else {
                JOptionPane.showMessageDialog(Ventana.this, "Ingrese su nombre completo para continuar", "Atencion", JOptionPane.WARNING_MESSAGE);
            }
        });
        getRootPane().setDefaultButton(iniciar);
        begin.add(iniciar);
        add(begin);
    }
    
    /**
     * Metodo sin retorno auxiliar a {@link proyectofinal.Ventana#setupInicio() setupInicio()}
     * @return None
     */
    private void viaCuestionario() {
        usuario = new Usuario(inputText);
        setupCuestionario();
    }
    
    /**
     * Metodo sin retorno que configura al usuario una vez realizado el test
     * @return None
     */
    private void setupUsuario() {
        int aciertos = 0;
        aciertos = preguntas.stream().filter((pregui) -> (pregui.isCorrecto())).map((_item) -> 1).reduce(aciertos, Integer::sum);
        usuario.setAprobado(aciertos >= VALOR_APROBADO);
        usuario.setDuracion(cronometro.getHora());
        usuario.setCorrectas(aciertos);
        usuario.setIncorrectas(10 - aciertos);
    }
    
    
    /**
     * Metodo sin retorno que establece el panel correspondiente a la muestra de los resultados del cuestionario cuando se ha realizado o el usuario desea ver sus resultados anteriores
     * @return None
     */
    private void setupResultado() {
        if (!usuarioPasado) {
            remove(scrollpane);
            remove(instrucciones);
            remove(cronometro);
        } else {
            remove(begin);
        }
        
        setSize(500, 300);
        resultados = new JPanel();
        resultados.setLayout(new BoxLayout(resultados, BoxLayout.Y_AXIS));
        resultados.setBorder(new EmptyBorder(20, 10, 10, 10));
        
        JLabel revision = new JLabel();
        revision.setText(usuario.isAprobado() ? "Aprobado" : "No aprobado");
        revision.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        revision.setHorizontalAlignment(SwingConstants.CENTER);
        revision.setFont(new Font("Arial", Font.BOLD, 42));
        revision.setForeground(usuario.isAprobado() ? Color.green : Color.red);
        resultados.add(revision);
        
        JLabel nombreUsuario = new JLabel(usuario.getNombre());
        nombreUsuario.setFont(new Font("Arial", Font.BOLD, 28));
        nombreUsuario.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        resultados.add(nombreUsuario);
        
        JLabel aciertos = new JLabel("Aciertos: "+usuario.getCorrectas()+"/10");
        aciertos.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        aciertos.setFont(new Font("Arial", Font.BOLD, 18));
        resultados.add(aciertos);
        
        JLabel tiempo = new JLabel("Tiempo transcurrido: "+usuario.getDuracion().toString());
        tiempo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        tiempo.setFont(new Font("Arial", Font.BOLD, 16));
        resultados.add(tiempo);
        
        if (!usuarioPasado) {
            try {
                usuario.guardar();
            } catch (IOException ex) {
                Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Error al guardar los datos del usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JButton retomar = new JButton("Retomar prueba");
            retomar.setAlignmentX(JButton.CENTER_ALIGNMENT);
            retomar.addActionListener((ActionEvent e) -> {
                setupCuestionario();
            });
            resultados.add(retomar);
        }
        
        add(resultados);
        revalidate();
        repaint();
        

    }
    
    /**
     * Metodo sin retorno que establece el panel correspondiente al cuestionario mismo y sus componentes auxiliares en el frame
     */
    private void setupCuestionario()
    {
        if (!usuarioPasado) {
            remove(begin);
        } else {
            remove(resultados);
            usuarioPasado = false;
        }
        
        setSize(900, 700);
        ExtractorPreguntas extractor = new ExtractorPreguntas();
        
        questionario = new JPanel();
        instrucciones = new JLabel("Conteste todas las preguntas seleccionando el incisio correcto, aquellas no contestadas seran tomadas como incorrectas");
        enviar = new JButton("Enviar");
        
        getRootPane().setDefaultButton(enviar);
        
        questionario.setLayout(new BoxLayout(questionario, BoxLayout.Y_AXIS));
        extractor.getPreguntas().stream().forEach((pregunta) -> {
            PreguntaUI ui = new PreguntaUI(pregunta, preguntas.size() + 1);
            preguntas.add(ui);
            questionario.add(ui);
        });
        
        enviar.addActionListener((ActionEvent e) -> {
            cronometro.paro();
            setupUsuario();
            System.out.println(usuario);
            setupResultado();
        }); 
        
        questionario.add(enviar);
        
        questionario.setBorder(new EmptyBorder(10, 10, 10, 10));
        questionario.setBackground(Color.white);
        
        scrollpane = new JScrollPane(questionario);
        
        add(instrucciones, BorderLayout.NORTH);
        add(scrollpane, BorderLayout.CENTER);
        add(cronometro, BorderLayout.SOUTH);
        cronometro.arranque();
        revalidate();
        repaint();
    }
}
