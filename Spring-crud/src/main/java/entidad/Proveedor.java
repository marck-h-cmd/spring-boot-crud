package entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name = "proveedores")
public class Proveedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El RUC no puede estar vacío")
    @Size(min = 11, max = 11, message = "El RUC debe tener 11 dígitos")
    @Column(unique = true)
    private String ruc;
    
    @NotBlank(message = "La razón social no puede estar vacía")
    private String razonSocial;
    
    @NotBlank(message = "El nombre comercial no puede estar vacío")
    private String nombreComercial;
    
    @NotBlank(message = "El teléfono no puede estar vacío")
    private String telefono;
    
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un email válido")
    private String email;
    
    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;
    
    private boolean activo = true;
}