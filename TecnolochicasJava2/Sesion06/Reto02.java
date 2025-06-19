import jakarta.persistence.*;

@Entity
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    public Marca() {}

    public Marca(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() { return id; }

    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return "Marca[id=" + id + ", nombre='" + nombre + "']";
    }
}

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String descripcion;

    @Min(1)
    private double precio;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    public Producto() {}

    public Producto(String nombre, String descripcion, double precio, Marca marca) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.marca = marca;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public Marca getMarca() { return marca; }

    @Override
    public String toString() {
        return "Producto[id=" + id + ", nombre='" + nombre + "', marca='" + marca.getNombre() + "']";
    }
}

import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
}

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MarcaProductoRunner {

    @Bean
    public CommandLineRunner run(MarcaRepository marcaRepo, ProductoRepository productoRepo) {
        return args -> {
            // Limpiar base de datos
            productoRepo.deleteAll();
            marcaRepo.deleteAll();

            // Crear marcas
            Marca apple = marcaRepo.save(new Marca("Apple"));
            Marca samsung = marcaRepo.save(new Marca("Samsung"));

            // Crear productos asociados
            productoRepo.save(new Producto("iPhone 15", "Teléfono inteligente", 25000.00, apple));
            productoRepo.save(new Producto("iPad Pro", "Tableta profesional", 18000.00, apple));
            productoRepo.save(new Producto("Galaxy S23", "Teléfono Android", 22000.00, samsung));
            productoRepo.save(new Producto("Smart TV", "Televisor 4K", 12000.00, samsung));

            // Mostrar productos agrupados por marca
            System.out.println("📚 Productos por marca:");
            marcaRepo.findAll().forEach(marca -> {
                System.out.println("🏷️ " + marca.getNombre() + ":");
                productoRepo.findAll().stream()
                    .filter(p -> p.getMarca().getId().equals(marca.getId()))
                    .forEach(p -> System.out.println("   - " + p.getNombre()));
            });
        };
    }
}

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
