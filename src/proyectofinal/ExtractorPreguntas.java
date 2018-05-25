
package proyectofinal;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Clase cuyos objetos extraen las preguntas de los archivos formateados y las almacenan como una {@link java.util.List lista generica} que se inicializa como un {@link java.util.ArrayList ArrayList} de {@link proyectofinal.Pregunta preguntas}
 * @author manuel
 */
public class ExtractorPreguntas {
    List<String> lineas;
    List<Pregunta> preguntas;
    
    /**
     * Constructor default de un extractor de preguntas que usa el archivo 'preguntas.txt' como su default
     */
    public ExtractorPreguntas(){
        recuperarLineas("preguntas.txt");
    }
    
    /**
     * Constructor que recibe un nombre de archivo del cual se deben recuperar las preguntas
     * @param filename nombre de archivo formateado de preguntas
     */
    public ExtractorPreguntas(String filename)
    {
        recuperarLineas(filename);
    }
    
    /**
     * Metodo sin retorno que abre el archivo `filename` y recupera todas sus lineas
     * @param filename nombre del archivo formateado de preguntas
     * @see java.nio.file.Files#readAllLines(java.nio.file.Path) 
     * @see java.util.Collections#shuffle(java.util.List) 
     * 
     */
    private void recuperarLineas(String filename) {
        try {
            lineas = Files.readAllLines(Paths.get(filename));
            Collections.shuffle(lineas);
            if (lineas.size() > 10) {
                lineas = lineas.subList(0, 10);
            }
            constructorPreguntas();
        } catch (IOException e) {
            System.err.println("Imposible abrir "+filename);
        }
    }
    
    /**
     * Metodo sin retorno que interpreta el formato de las preguntas usando expresiones regulares y predicados, las separa en pregunta, opciones y respuesta correcta y los configura en estructuras adecuadas
     * @see java.util.HashMap
     * @see java.util.function.Predicate
     */
    private void constructorPreguntas() {
        preguntas = new ArrayList<>();
        String [] partes;
        String [] opciones;
        HashMap<Integer, String> respuestas;
        Pattern patron = Pattern.compile("(\\w)\\W+(.+)");
        Predicate predicado = patron.asPredicate();
        Matcher matcher;
        for(String linea : lineas){
            respuestas = new HashMap<>();
            partes = linea.split("\\?|\\*");
            opciones = partes[1].split("\\#");
            for(String opcion : opciones) {
                if (predicado.test(opcion)) {
                    matcher = patron.matcher(opcion);
                    if (matcher.lookingAt()) {
                        respuestas.put(Integer.parseInt(matcher.group(1)), matcher.group(2));
                    }
                }
            }
            preguntas.add(new Pregunta(partes[0], respuestas, Integer.parseInt(partes[2])));
        }
    }
    
    /**
     * Metodo que regresa la lista de preguntas recuperada del archivo formateado
     * @return lista generica de tipo {@link proyectofinal.Pregunta Pregunta}
     */
    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
            
}