import java.util.concurrent.*;

// Subsistemas implementando Callable<String>

class SistemaNavegacion implements Callable<String> {
    public String call() throws Exception {
        Thread.sleep(1000);
        return "Navegación: trayectoria corregida con éxito.";
    }
}

class SistemaSoporteVital implements Callable<String> {
    public String call() throws Exception {
        Thread.sleep(800);
        return "Soporte vital: presión y oxígeno dentro de parámetros normales.";
    }
}

class SistemaControlTermico implements Callable<String> {
    public String call() throws Exception {
        Thread.sleep(900);
        return " Control térmico: temperatura estable (22°C).";
    }
}

class SistemaComunicaciones implements Callable<String> {
    public String call() throws Exception {
        Thread.sleep(600);
        return " Comunicaciones: enlace con estación terrestre establecido.";
    }
}

// Clase principal
public class SimulacionMisionEspacial {

    public static void main(String[] args) {
        System.out.println("Simulación de misión espacial iniciada...");

        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Enviar tareas
        Future<String> nav = executor.submit(new SistemaNavegacion());
        Future<String> soporte = executor.submit(new SistemaSoporteVital());
        Future<String> termico = executor.submit(new SistemaControlTermico());
        Future<String> comunicaciones = executor.submit(new SistemaComunicaciones());

        try {
            // Imprimir resultados
            System.out.println(comunicaciones.get());
            System.out.println(soporte.get());
            System.out.println(termico.get());
            System.out.println(nav.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        System.out.println(" Todos los sistemas reportan estado operativo.");
    }
}
