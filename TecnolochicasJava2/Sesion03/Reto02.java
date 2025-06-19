import java.util.Optional;
import java.util.List;
import java.util.*;
import java.util.stream.*;


public class Sucursal {
    private String nombre;
    private List<Encuesta> encuestas;

    public Sucursal(String nombre, List<Encuesta> encuestas) {
        this.nombre = nombre;
        this.encuestas = encuestas;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Encuesta> getEncuestas() {
        return encuestas;
    }
}

public class Encuesta {
    private String paciente;
    private String comentario; // Puede ser null
    private int calificacion;

    public Encuesta(String paciente, String comentario, int calificacion) {
        this.paciente = paciente;
        this.comentario = comentario;
        this.calificacion = calificacion;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public Optional<String> getComentario() {
        return Optional.ofNullable(comentario);
    }
}

public class ProcesadorEncuestas {

    public static void main(String[] args) {
        // Crear encuestas
        List<Encuesta> centro = Arrays.asList(
            new Encuesta("Luis", "El tiempo de espera fue largo", 2),
            new Encuesta("Ana", null, 4),
            new Encuesta("Carlos", "Poca disponibilidad de médicos", 3)
        );

        List<Encuesta> norte = Arrays.asList(
            new Encuesta("María", "La atención fue buena, pero la limpieza puede mejorar", 3),
            new Encuesta("Pedro", null, 5)
        );

        // Crear sucursales
        List<Sucursal> sucursales = Arrays.asList(
            new Sucursal("Centro", centro),
            new Sucursal("Norte", norte)
        );

        // Procesar con Stream API y flatMap
        sucursales.stream()
            .flatMap(sucursal ->
                sucursal.getEncuestas().stream()
                    .filter(e -> e.getCalificacion() <= 3) // Insatisfechos
                    .flatMap(encuesta -> encuesta.getComentario().stream()
                        .map(comentario -> "Sucursal " + sucursal.getNombre()
                                + ": Seguimiento a paciente con comentario: \"" + comentario + "\""))
            )
            .forEach(System.out::println);
    }
}
