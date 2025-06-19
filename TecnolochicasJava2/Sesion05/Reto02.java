<dependencies>
  <dependency>
    <groupId>io.projectreactor</groupId>
    <artifactId>reactor-core</artifactId>
    <version>3.5.8</version>
  </dependency>
</dependencies>


import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Random;

public class UciMonitor {

    static Random rand = new Random();

    // Datos simulados
    record SignosVitales(int pacienteId, int fc, int sistolica, int diastolica, int spo2) {}

    // Generar flujo de un paciente
    public static Flux<SignosVitales> flujoPaciente(int pacienteId) {
        return Flux.interval(Duration.ofMillis(300))
                .map(i -> new SignosVitales(
                        pacienteId,
                        40 + rand.nextInt(100),           // FC: 40-140
                        80 + rand.nextInt(80),            // PA sistólica: 80–160
                        50 + rand.nextInt(40),            // PA diastólica: 50–90
                        85 + rand.nextInt(15)             // SpO2: 85–100
                ));
    }

    // Evaluar evento crítico y priorizar FC > SpO2 > PA
    public static Flux<String> procesarEventosCriticos(Flux<SignosVitales> flujo) {
        return flujo
            .flatMap(data -> {
                // Evaluaciones individuales
                if (data.fc < 50 || data.fc > 120) {
                    return Flux.just("⚠️ Paciente " + data.pacienteId + " - FC crítica: " + data.fc + " bpm");
                } else if (data.spo2 < 90) {
                    return Flux.just("⚠️ Paciente " + data.pacienteId + " - SpO2 baja: " + data.spo2 + "%");
                } else if (data.sistolica > 140 || data.sistolica < 90 || data.diastolica > 90 || data.diastolica < 60) {
                    return Flux.just("⚠️ Paciente " + data.pacienteId + " - PA crítica: " +
                            data.sistolica + "/" + data.diastolica + " mmHg");
                }
                return Flux.empty();
            })
            .delayElements(Duration.ofSeconds(1)); // ⛔ Contrapresión: simula procesamiento lento
    }

    public static void main(String[] args) throws InterruptedException {
        // Tres pacientes monitoreados en paralelo
        Flux<SignosVitales> paciente1 = flujoPaciente(1);
        Flux<SignosVitales> paciente2 = flujoPaciente(2);
        Flux<SignosVitales> paciente3 = flujoPaciente(3);

        // 🔀 Fusionar y procesar eventos críticos
        Flux<String> todosLosEventosCriticos = procesarEventosCriticos(
                Flux.merge(paciente1, paciente2, paciente3)
        );

        // 🔔 Suscribirse e imprimir alertas críticas
        todosLosEventosCriticos.subscribe(System.out::println);

        // Mantener la app activa por 20 segundos
        Thread.sleep(20000);
    }
}
