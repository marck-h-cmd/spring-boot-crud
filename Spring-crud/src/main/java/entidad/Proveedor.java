package entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * Entidad que representa a un proveedor en el sistema.
 * Mapea a la tabla "proveedores" en la base de datos.
 */
@Entity
@Data // Genera getters, setters, toString, etc. automáticamente (Lombok)
@Table(name = "proveedores") // Nombre de la tabla en la BD
public class Proveedor {
    
    // Identificador único generado automáticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // RUC (11 dígitos) - Único y obligatorio
    @NotBlank(message = "El RUC no puede estar vacío")
    @Size(min = 11, max = 11, message = "El RUC debe tener 11 dígitos")
    @Column(unique = true) // Restricción de unicidad en BD
    private String ruc;
    
    // Nombre legal del proveedor (obligatorio)
    @NotBlank(message = "La razón social no puede estar vacía")
    private String razonSocial;
    
    // Nombre público del proveedor (obligatorio)
    @NotBlank(message = "El nombre comercial no puede estar vacío")
    private String nombreComercial;
    
    // Teléfono de contacto (obligatorio)
    @NotBlank(message = "El teléfono no puede estar vacío")
    private String telefono;
    
    // Email con validación de formato (obligatorio)
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un email válido")
    private String email;
    
    // Dirección física (obligatoria)
    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;
    
    // Estado del proveedor (true por defecto)
    private boolean activo = true; 
}