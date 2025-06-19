import java.util.*;
import java.util.function.Predicate;

// Clase abstracta
abstract class MaterialCurso {
    protected String titulo;
    protected String autor;

    public MaterialCurso(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    public abstract void mostrarDetalle();
}

// Subclase Video
class Video extends MaterialCurso {
    private int duracion; // en minutos

    public Video(String titulo, String autor, int duracion) {
        super(titulo, autor);
        this.duracion = duracion;
    }

    public int getDuracion() {
        return duracion;
    }

    @Override
    public void mostrarDetalle() {
        System.out.println("🎥 Video: " + titulo + " - Autor: " + autor + " - Duración: " + duracion + " min");
    }
}

// Subclase Articulo
class Articulo extends MaterialCurso {
    private int palabras;

    public Articulo(String titulo, String autor, int palabras) {
        super(titulo, autor);
        this.palabras = palabras;
    }

    @Override
    public void mostrarDetalle() {
        System.out.println("📄 Artículo: " + titulo + " - Autor: " + autor + " - Palabras: " + palabras);
    }
}

// Subclase Ejercicio
class Ejercicio extends MaterialCurso {
    private boolean revisado;

    public Ejercicio(String titulo, String autor) {
        super(titulo, autor);
        this.revisado = false;
    }

    public void marcarRevisado() {
        this.revisado = true;
        System.out.println("✅ Ejercicio '" + titulo + "' marcado como revisado.");
    }

    public boolean isRevisado() {
        return revisado;
    }

    @Override
    public void mostrarDetalle() {
        System.out.println("📝 Ejercicio: " + titulo + " - Autor: " + autor + " - Revisado: " + revisado);
    }
}

// Clase principal
public class PlataformaEducativa {

    // Mostrar cualquier tipo de material
    public static void mostrarMateriales(List<? extends MaterialCurso> lista) {
        for (MaterialCurso material : lista) {
            material.mostrarDetalle();
        }
    }

    // Sumar duración de los videos
    public static void contarDuracionVideos(List<? extends Video> lista) {
        int total = 0;
        for (Video video : lista) {
            total += video.getDuracion();
        }
        System.out.println("\n🎥 Duración total de videos: " + total + " minutos");
    }

    // Marcar ejercicios como revisados
    public static void marcarEjerciciosRevisados(List<? super Ejercicio> lista) {
        for (Object obj : lista) {
            if (obj instanceof Ejercicio) {
                ((Ejercicio) obj).marcarRevisado();
            }
        }
    }

    // Filtrar por autor usando Predicate
    public static <T extends MaterialCurso> void filtrarPorAutor(List<T> lista, Predicate<T> filtro) {
        System.out.println("\n🔍 Filtrando materiales por autor:");
        for (T material : lista) {
            if (filtro.test(material)) {
                material.mostrarDetalle();
            }
        }
    }

    // Método main
    public static void main(String[] args) {
        List<MaterialCurso> materiales = Arrays.asList(
            new Video("Introducción a Java", "Mario", 15),
            new Video("POO en Java", "Carlos", 20),
            new Articulo("Historia de Java", "Ana", 1200),
            new Articulo("Tipos de datos", "Luis", 800),
            new Ejercicio("Variables y tipos", "Luis"),
            new Ejercicio("Condicionales", "Mario")
        );

        System.out.println("📚 Materiales del curso:");
        mostrarMateriales(materiales);

        // Filtrar solo videos
        List<Video> videos = new ArrayList<>();
        for (MaterialCurso m : materiales) {
            if (m instanceof Video) videos.add((Video) m);
        }
        contarDuracionVideos(videos);

        // Marcar ejercicios como revisados
        marcarEjerciciosRevisados(materiales);

        // Filtro por autor: Mario
        filtrarPorAutor(materiales, m -> m.autor.equals("Mario"));
    }
}
