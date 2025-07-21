package entidad;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "empleado")
@Data
public class Empleado {

    // Estrategia IDENTITY para compatibilidad con auto-incremento en MySQL/PostgreSQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Los constraints de tamaño se sincronizan con la definición de columna
    @Column(length = 35, nullable = false)
    @NotBlank
    @Size(min = 2, max = 35)
    private String nombres;

    // Se repite el mismo patrón de validación para apellidos
    @Column(length = 35, nullable = false)
    @NotBlank
    @Size(min = 2, max = 35)
    private String apellidosPaterno;
    
    @Column(length = 35, nullable = false)
    @NotBlank
    @Size(min = 2, max = 35)
    private String apellidosMaterno;

    // El DNI tiene restricción de longitud exacta y debe ser único
    @Column(length = 8, nullable = false, unique = true)
    @NotBlank
    @Size(min = 8, max = 8)
    private String dni;

    // TemporalType.DATE almacena solo fecha sin componente horario
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // Formato estándar ISO
    @Past  // Valida que sea fecha pasada
    @NotNull
    private Date fechaNacimiento;

    // Celular es opcional pero si se provee debe tener 9 dígitos
    @Column(length = 9, nullable = true, unique = true)
    @Size(min = 9, max = 9)
    private String celular;

    // Spring valida automáticamente el formato de email
    @Column(length = 80)
    @Email
    @NotBlank
    private String email;

    // Cargo no tiene restricción de tamaño pero es obligatorio
    @Column(nullable = false)
    @NotBlank
    private String cargo;

    // DecimalMin evita valores negativos para el salario
    @Column(nullable = false)
    @DecimalMin("0.0")
    private Double salario;

    // Fecha de contratación sin validación de rango temporal
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date fechaContratacion;
}