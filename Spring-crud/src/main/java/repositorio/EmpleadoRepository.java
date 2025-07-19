package repositorio;

import entidad.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Empleado findByDni(String dni);
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
    
    @Query("SELECT e FROM Empleado e WHERE " +
           "e.nombres LIKE %?1% OR " +
           "e.apellidosPaterno LIKE %?1% OR " +
           "e.apellidosMaterno LIKE %?1%")
    List<Empleado> findByNombresContainingOrApellidosPaternoContainingOrApellidosMaternoContaining(String criterio);
    
    long countByFechaContratacionAfter(Date fecha);
    List<Empleado> findTop5ByOrderByFechaContratacionDesc();
 
}