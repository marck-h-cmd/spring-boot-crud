package repositorio;

import entidad.Empleado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

@Repository  // Marca esta interfaz como un componente de repositorio de Spring
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    
    // Busca un empleado por su DNI (exact match)
    Empleado findByDni(String dni);
    
    // Verifica si existe un empleado con el DNI especificado
    boolean existsByDni(String dni);
    
    // Verifica si existe un empleado con el email especificado
    boolean existsByEmail(String email);
    
    // Búsqueda flexible por nombres o apellidos (contiene el criterio)
    @Query("SELECT e FROM Empleado e WHERE " +
           "e.nombres LIKE %?1% OR " +      // Busca en nombres
           "e.apellidosPaterno LIKE %?1% OR " +  // Busca en apellido paterno
           "e.apellidosMaterno LIKE %?1%")   // Busca en apellido materno
    List<Empleado> findByNombresContainingOrApellidosPaternoContainingOrApellidosMaternoContaining(String criterio);
    
    // Cuenta empleados contratados después de una fecha específica
    long countByFechaContratacionAfter(Date fecha);
    
    // Obtiene los 5 empleados más recientes por fecha de contratación
    List<Empleado> findTop5ByOrderByFechaContratacionDesc();
}
