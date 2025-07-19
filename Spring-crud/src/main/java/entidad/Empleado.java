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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 35, nullable = false)
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(min = 2, max = 35, message = "El nombre debe tener entre 2 y 35 caracteres")
    private String nombres;

    @Column(length = 35, nullable = false)
    @NotBlank(message = "El apellido paterno no puede estar en blanco")
    @Size(min = 2, max = 35, message = "El apellido debe tener entre 2 y 35 caracteres")
    private String apellidosPaterno;
    
    @Column(length = 35, nullable = false)
    @NotBlank(message = "El apellido materno no puede estar en blanco")
    @Size(min = 2, max = 35, message = "El apellido debe tener entre 2 y 35 caracteres")
    private String apellidosMaterno;

    @Column(length = 8, nullable = false, unique = true)
    @NotBlank(message = "El DNI no puede estar en blanco")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 caracteres")
    private String dni;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    @NotNull(message = "La fecha de nacimiento no puede estar vacía")
    private Date fechaNacimiento;

    @Column(length = 9, nullable = true, unique = true)
    @Size(min = 9, max = 9, message = "El celular debe tener 9 caracteres")
    private String celular;

    @Column(length = 80)
    @Email(message = "Debe ingresar un correo válido")
    @NotBlank(message = "El correo no puede estar vacío")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "El cargo no puede estar en blanco")
    private String cargo;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", message = "El salario debe ser mayor o igual a 0")
    private Double salario;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha de contratación no puede estar vacía")
    private Date fechaContratacion;
}