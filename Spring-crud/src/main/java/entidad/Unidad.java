package entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Entidad que representa una unidad de medida
 * Contiene una descripción que identifica la unidad (kg, litros, unidades, etc.)
 */
@Entity
@Table(name = "unidades")
@Data
public class Unidad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 50, nullable = false, unique = true)
    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;
}


