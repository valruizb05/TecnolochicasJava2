<dependencies>
    <dependency>
        <groupId>io.projectreactor</groupId>
        <artifactId>reactor-core</artifactId>
        <version>3.5.8</version>
    </dependency>
</dependencies>

import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;
import reactor.util.function.Tuple5;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MeridianPrimeSystem {

    static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        // 🔁 Contadores
        AtomicInteger semaforoRojoConsecutivo = new AtomicInteger(0);

        // 🚗 Sensores de tráfico
        Flux<String> trafico = Flux.interval(Duration.ofMillis(500))
                .map(i -> random.nextInt(101))
                .onBackpressureBuffer()
                .filter(congestion -> congestion > 70)
                .map(congestion -> "🚗 Alerta: Congestión del " + congestion + "% en Avenida Solar");

        // 🌫️ Contaminación del aire
        Flux<String> contaminacion = Flux.interval(Duration.ofMillis(600))
                .map(i -> random.nextInt(101))
                .filter(pm -> pm > 50)
                .map(pm -> "🌫️ Alerta: Contaminación alta (PM2.5: " + pm + " ug/m3)");

        // 🚑 Accidentes viales
        Flux<String> accidentes = Flux.interval(Duration.ofMillis(800))
                .map(i -> {
                    int nivel = random.nextInt(3);
                    return switch (nivel) {
                        case 2 -> "Alta";
                        case 1 -> "Media";
                        default -> "Baja";
                    };
                })
                .filter(prioridad -> prioridad.equals("Alta"))
                .map(p -> "🚑 Emergencia vial: Accidente con prioridad Alta");

        // 🚝 Trenes maglev
        Flux<String> trenes = Flux.interval(Duration.ofMillis(700))
                .map(i -> random.nextInt(11))
                .onBackpressureBuffer()
                .filter(min -> min > 5)
                .map(min -> "🚝 Tren maglev con retraso crítico: " + min + " minutos");

        // 🚦 Semáforos inteligentes
        Flux<String> semaforos = Flux.interval(Duration.ofMillis(400))
                .map(i -> {
                    int estado = random.nextInt(3);
                    return switch (estado) {
                        case 0 -> "Verde";
                        case 1 -> "Amarillo";
                        default -> "Rojo";
                    };
                })
                .map(estado -> {
                    if (estado.equals("Rojo")) {
                        if (semaforoRojoConsecutivo.incrementAndGet() >= 3) {
                            semaforoRojoConsecutivo.set(0);
                            return "🚦 Semáforo en Rojo detectado 3 veces seguidas en cruce Norte";
                        }
                    } else {
                        semaforoRojoConsecutivo.set(0);
                    }
                    return null;
                })
                .filter(mensaje -> mensaje != null);

        // 📡 Alerta Global si 3+ eventos críticos ocurren al mismo tiempo
        Flux<String> fusionCritica = Flux.combineLatest(trafico, contaminacion, accidentes, trenes, semaforos,
                (t, c, a, tr, s) -> {
                    int count = 0;
                    if (t != null) count++;
                    if (c != null) count++;
                    if (a != null) count++;
                    if (tr != null) count++;
                    if (s != null) count++;
                    if (count >= 3) {
                        return "🚨 Alerta global: Múltiples eventos críticos detectados en Meridian Prime";
                    }
                    return null;
                })
                .filter(msg -> msg != null)
                .distinctUntilChanged();

        // 🖨️ Suscripciones separadas por sistema
        trafico.subscribe(System.out::println);
        contaminacion.subscribe(System.out::println);
        accidentes.subscribe(System.out::println);
        trenes.subscribe(System.out::println);
        semaforos.subscribe(System.out::println);
        fusionCritica.subscribe(System.out::println);

        // 🕒 Mantener app viva
        Thread.sleep(20000); // Simula 20 segundos de ejecución
    }
}
