package entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * Entidad que representa un cliente del sistema
 * - Almacena información personal y de contacto
 * - Estado booleano para control lógico (activo/inactivo)
 */
@Entity
@Table(name = "clientes")
@Data
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 50, nullable = false)
    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;
    
    @Column(length = 50, nullable = false)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    
    @Column(length = 100)
    private String direccion;
    
    @Column(length = 1, nullable = false)
    @Pattern(regexp = "[MF]", message = "Sexo debe ser M (Masculino) o F (Femenino)")
    private String sexo;
    
    @Column(name = "ruc_dni", length = 11, nullable = false, unique = true)
    @NotBlank(message = "RUC/DNI no puede estar vacío")
    @Size(min = 8, max = 11, message = "RUC/DNI debe tener entre 8 y 11 caracteres")
    private String rucDni;
    
    @Column(nullable = false)
    private Boolean estado = true;
}