package entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

/**
 * Entidad que representa un producto en el sistema.
 * Mapea a la tabla "productos" en la base de datos.
 */
@Entity
@Data // Genera getters, setters, toString, etc. automáticamente (Lombok)
@Table(name = "productos")
public class Producto {
    
    // Identificador único generado automáticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Código del producto - Único y obligatorio
    @NotBlank(message = "El código no puede estar vacío")
    @Size(min = 3, max = 20, message = "El código debe tener entre 3 y 20 caracteres")
    @Column(unique = true, length = 20)
    private String codigo;
    
    // Nombre del producto - Obligatorio
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(length = 100, nullable = false)
    private String nombre;
    
    // Descripción del producto - Opcional
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Column(length = 500)
    private String descripcion;
    
    // Precio unitario - Obligatorio y positivo
    @NotNull(message = "El precio no puede estar vacío")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    // Stock disponible - Obligatorio y no negativo
    @NotNull(message = "El stock no puede estar vacío")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    private Integer stock;
    
    // Stock mínimo para alertas
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    @Column(nullable = false)
    private Integer stockMinimo = 0;
    
    // Categoría del producto - Obligatorio
    @NotBlank(message = "La categoría no puede estar vacía")
    @Column(length = 50, nullable = false)
    private String categoria;
    
    // Unidad de medida - Obligatorio
    @NotBlank(message = "La unidad no puede estar vacía")
    @Column(length = 20, nullable = false)
    private String unidad;
    
    // Estado del producto (activo/inactivo)
    @Column(nullable = false)
    private boolean activo = true;
    
    // Método para verificar si está en stock bajo
    public boolean isStockBajo() {
        return stock <= stockMinimo;
    }
    
    // Método para calcular el valor total del inventario
    public BigDecimal getValorInventario() {
        return precio.multiply(BigDecimal.valueOf(stock));
    }
}