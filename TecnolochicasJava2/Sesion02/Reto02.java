import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

// Recurso médico con sincronización
class RecursoMedico {
    private final String nombre;
    private final ReentrantLock lock = new ReentrantLock();

    public RecursoMedico(String nombre) {
        this.nombre = nombre;
    }

    public void usar(String profesional) {
        System.out.println("🔄 " + profesional + " está intentando acceder a " + nombre + "...");
        lock.lock(); // Adquiere el bloqueo
        try {
            System.out.println("👩‍⚕️ " + profesional + " ha ingresado a " + nombre);
            Thread.sleep(1000); // Simula uso del recurso
            System.out.println("✅ " + profesional + " ha salido de " + nombre);
        } catch (InterruptedException e) {
            System.out.println("⚠️ " + profesional + " fue interrumpido.");
        } finally {
            lock.unlock(); // Libera el bloqueo
        }
    }
}

public class SimulacionHospital {

    public static void main(String[] args) {
        System.out.println("🏥 Iniciando acceso a la Sala de cirugía...");

        RecursoMedico salaCirugia = new RecursoMedico("Sala de cirugía");

        // Lista de profesionales médicos
        String[] profesionales = {
            "Dra. Sánchez",
            "Dr. Gómez",
            "Enf. Ramírez",
            "Dra. Torres",
            "Dr. Pérez"
        };

        // ExecutorService para concurrencia
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (String profesional : profesionales) {
            executor.execute(() -> salaCirugia.usar(profesional));
        }

        executor.shutdown(); // No se aceptan más tareas
    }
}
