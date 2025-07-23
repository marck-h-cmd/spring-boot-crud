package entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Entidad que representa una categoría de productos
 * Contiene una descripción que identifica la categoría
 */
@Entity
@Table(name = "categorias")
@Data
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false, unique = true)
    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;
}

