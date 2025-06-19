import java.util.concurrent.*;
import java.util.concurrent.ThreadLocalRandom;

public class AeropuertoControl {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    // Simula latencia de 2-3 segundos
    private void simularLatencia() {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(2, 4));
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupción en la simulación");
        }
    }

    public CompletableFuture<Boolean> verificarPista() {
        return CompletableFuture.supplyAsync(() -> {
            simularLatencia();
            boolean disponible = ThreadLocalRandom.current().nextDouble() < 0.8;
            System.out.println("🛣️ Pista disponible: " + disponible);
            return disponible;
        }, executor);
    }

    public CompletableFuture<Boolean> verificarClima() {
        return CompletableFuture.supplyAsync(() -> {
            simularLatencia();
            boolean favorable = ThreadLocalRandom.current().nextDouble() < 0.85;
            System.out.println("🌦️ Clima favorable: " + favorable);
            return favorable;
        }, executor);
    }

    public CompletableFuture<Boolean> verificarTraficoAereo() {
        return CompletableFuture.supplyAsync(() -> {
            simularLatencia();
            boolean despejado = ThreadLocalRandom.current().nextDouble() < 0.9;
            System.out.println("🚦 Tráfico aéreo despejado: " + despejado);
            return despejado;
        }, executor);
    }

    public CompletableFuture<Boolean> verificarPersonalTierra() {
        return CompletableFuture.supplyAsync(() -> {
            simularLatencia();
            boolean disponible = ThreadLocalRandom.current().nextDouble() < 0.95;
            System.out.println("👷‍♂️ Personal disponible: " + disponible);
            return disponible;
        }, executor);
    }

    // Combinar todas las verificaciones
    public void gestionarAterrizaje() {
        System.out.println("🛫 Verificando condiciones para aterrizaje...\n");

        CompletableFuture<Boolean> pista = verificarPista();
        CompletableFuture<Boolean> clima = verificarClima();
        CompletableFuture<Boolean> trafico = verificarTraficoAereo();
        CompletableFuture<Boolean> personal = verificarPersonalTierra();

        CompletableFuture<Void> todas = CompletableFuture.allOf(pista, clima, trafico, personal);

        todas.thenApply(v -> {
            try {
                boolean autorizado = pista.get() && clima.get() && trafico.get() && personal.get();
                return autorizado;
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        })
        .thenAccept(autorizado -> {
            if (autorizado) {
                System.out.println("\n🛬 Aterrizaje autorizado: todas las condiciones óptimas.");
            } else {
                System.out.println("\n🚫 Aterrizaje denegado: condiciones no óptimas.");
            }
        })
        .exceptionally(e -> {
            System.out.println("\n❌ Error en el proceso de verificación: " + e.getMessage());
            return null;
        })
        .thenRun(executor::shutdown);
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AeropuertoControl control = new AeropuertoControl();
        control.gestionarAterrizaje();

        // Esperar hasta que finalicen las tareas
        Thread.sleep(6000);
    }
}
