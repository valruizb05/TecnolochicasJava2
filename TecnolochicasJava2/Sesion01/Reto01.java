import java.util.*;

// Clase abstracta
abstract class OrdenProduccion {
    protected String codigo;
    protected int cantidad;

    public OrdenProduccion(String codigo, int cantidad) {
        this.codigo = codigo;
        this.cantidad = cantidad;
    }

    public abstract void mostrarResumen();
}

// Subclase OrdenMasa
class OrdenMasa extends OrdenProduccion {
    public OrdenMasa(String codigo, int cantidad) {
        super(codigo, cantidad);
    }

    @Override
    public void mostrarResumen() {
        System.out.println("🔧 OrdenMasa - Código: " + codigo + " - Cantidad: " + cantidad);
    }
}

// Subclase OrdenPersonalizada
class OrdenPersonalizada extends OrdenProduccion {
    private String cliente;

    public OrdenPersonalizada(String codigo, int cantidad, String cliente) {
        super(codigo, cantidad);
        this.cliente = cliente;
    }

    public void ajustarCosto(int costo) {
        System.out.println("✅ Orden " + codigo + " ajustada con costo adicional de $" + costo);
    }

    @Override
    public void mostrarResumen() {
        System.out.println("🛠️ OrdenPersonalizada - Código: " + codigo + " - Cantidad: " + cantidad + " - Cliente: " + cliente);
    }
}

// Subclase OrdenPrototipo
class OrdenPrototipo extends OrdenProduccion {
    private String faseDesarrollo;

    public OrdenPrototipo(String codigo, int cantidad, String faseDesarrollo) {
        super(codigo, cantidad);
        this.faseDesarrollo = faseDesarrollo;
    }

    @Override
    public void mostrarResumen() {
        System.out.println("🧪 OrdenPrototipo - Código: " + codigo + " - Cantidad: " + cantidad + " - Fase: " + faseDesarrollo);
    }
}

// Clase principal
public class PlantaProduccion {
    
    // Método genérico
    public static void mostrarOrdenes(List<? extends OrdenProduccion> lista) {
        for (OrdenProduccion orden : lista) {
            orden.mostrarResumen();
        }
    }

    // Método con comodín super
    public static void procesarPersonalizadas(List<? super OrdenPersonalizada> lista, int costoAdicional) {
        System.out.println("\n💰 Procesando órdenes personalizadas...");
        for (Object obj : lista) {
            if (obj instanceof OrdenPersonalizada) {
                ((OrdenPersonalizada) obj).ajustarCosto(costoAdicional);
            }
        }
    }

    // Método adicional: contar tipos
    public static void contarTipos(List<OrdenProduccion> todas) {
        int masa = 0, personalizadas = 0, prototipos = 0;

        for (OrdenProduccion orden : todas) {
            if (orden instanceof OrdenMasa) masa++;
            else if (orden instanceof OrdenPersonalizada) personalizadas++;
            else if (orden instanceof OrdenPrototipo) prototipos++;
        }

        System.out.println("\n📊 Resumen total de órdenes:");
        System.out.println("🔧 Producción en masa: " + masa);
        System.out.println("🛠️ Personalizadas: " + personalizadas);
        System.out.println("🧪 Prototipos: " + prototipos);
    }

    public static void main(String[] args) {
        List<OrdenMasa> ordenesMasa = Arrays.asList(
            new OrdenMasa("A123", 500),
            new OrdenMasa("A124", 750)
        );

        List<OrdenPersonalizada> ordenesPersonalizadas = Arrays.asList(
            new OrdenPersonalizada("P456", 100, "ClienteX"),
            new OrdenPersonalizada("P789", 150, "ClienteY")
        );

        List<OrdenPrototipo> ordenesPrototipo = Arrays.asList(
            new OrdenPrototipo("T789", 10, "Diseño"),
            new OrdenPrototipo("T790", 5, "Pruebas")
        );

        // Mostrar órdenes
        System.out.println("📋 Órdenes registradas:");
        mostrarOrdenes(ordenesMasa);

        System.out.println("\n📋 Órdenes registradas:");
        mostrarOrdenes(ordenesPersonalizadas);

        System.out.println("\n📋 Órdenes registradas:");
        mostrarOrdenes(ordenesPrototipo);

        // Procesar personalizadas
        List<OrdenProduccion> todasLasOrdenes = new ArrayList<>();
        todasLasOrdenes.addAll(ordenesMasa);
        todasLasOrdenes.addAll(ordenesPersonalizadas);
        todasLasOrdenes.addAll(ordenesPrototipo);

        procesarPersonalizadas(todasLasOrdenes, 200);

        // Contar tipos
        contarTipos(todasLasOrdenes);
    }
}