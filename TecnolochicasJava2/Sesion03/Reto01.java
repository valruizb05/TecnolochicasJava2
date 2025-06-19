import java.util.Optional;
import java.util.*;
import java.util.stream.*;

public class ProcesadorPedidos {

    public static void main(String[] args) {
        List<Pedido> pedidos = Arrays.asList(
            new Pedido("Carlos", "domicilio", "555-1234"),
            new Pedido("Ana", "local", "555-0000"),
            new Pedido("Luis", "domicilio", null),
            new Pedido("María", "domicilio", "555-5678"),
            new Pedido("Jose", "local", null)
        );

        // Procesar pedidos
        pedidos.stream()
            .filter(p -> p.getTipoEntrega().equalsIgnoreCase("domicilio")) // Solo pedidos a domicilio
            .map(Pedido::getTelefono) // Obtener Optional<String>
            .flatMap(Optional::stream) // Solo teléfonos presentes
            .map(telefono -> "📞 Confirmación enviada al número: " + telefono)
            .forEach(System.out::println);
    }
}

public class Pedido {
    private String cliente;
    private String tipoEntrega; // "domicilio" o "local"
    private String telefono;    // Puede ser null

    public Pedido(String cliente, String tipoEntrega, String telefono) {
        this.cliente = cliente;
        this.tipoEntrega = tipoEntrega;
        this.telefono = telefono;
    }

    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public Optional<String> getTelefono() {
        return Optional.ofNullable(telefono);
    }
}
