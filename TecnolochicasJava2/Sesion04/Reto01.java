import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MovilidadApp {

    // Simula el cálculo de ruta con latencia
    public CompletableFuture<String> calcularRuta() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("🚦 Calculando ruta...");
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(2, 4)); // 2-3 segundos
                return "Centro -> Norte";
            } catch (InterruptedException e) {
                throw new RuntimeException("Error al calcular la ruta");
            }
        });
    }

    // Simula la estimación de tarifa con latencia
    public CompletableFuture<Double> estimarTarifa() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("💰 Estimando tarifa...");
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1, 3)); // 1-2 segundos
                return 75.50;
            } catch (InterruptedException e) {
                throw new RuntimeException("Error al estimar tarifa");
            }
        });
    }

    // Combina ambas operaciones
    public void procesarSolicitudViaje() {
        calcularRuta()
            .thenCombine(estimarTarifa(), (ruta, tarifa) ->
                "✅ 🚗 Ruta calculada: " + ruta + " | Tarifa estimada: $" + tarifa)
            .exceptionally(error -> "❌ Error durante el proceso: " + error.getMessage())
            .thenAccept(System.out::println);
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MovilidadApp app = new MovilidadApp();
        app.procesarSolicitudViaje();

        // Evita que el main termine antes que los procesos asincrónicos
        Thread.sleep(5000); // Máximo tiempo de espera
    }
}
