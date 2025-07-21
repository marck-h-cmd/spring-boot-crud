package servicio;

import entidad.Empleado;
import repositorio.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDate;

/**
 * Implementación concreta del servicio para gestión de empleados
 * que interactúa con el repositorio JPA para operaciones de persistencia.
 */
@Service // Marca esta clase como un servicio de Spring para inyección de dependencias
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired // Inyección automática del repositorio
    private EmpleadoRepository empleadoRepository;

    //------------------------ OPERACIONES CRUD ------------------------//

    @Override
    public Empleado agregar(Empleado empleado) {
        // Guarda un nuevo empleado o actualiza uno existente
        return empleadoRepository.save(empleado);
    }

    @Override
    public List<Empleado> listarTodos() {
        // Obtiene todos los empleados registrados
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado buscar(Long id) {
        // Busca un empleado por ID, retorna null si no existe
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public Empleado actualizar(Empleado empleado) {
        // Actualiza los datos del empleado (usa el mismo método save que agregar)
        return empleadoRepository.save(empleado);
    }

    @Override
    public void eliminar(Long id) {
        // Elimina un empleado por su ID
        empleadoRepository.deleteById(id);
    }

    //------------------------ VALIDACIONES ------------------------//

    @Override
    public boolean existePorDni(String dni) {
        // Verifica si existe un empleado con el DNI especificado
        return empleadoRepository.existsByDni(dni);
    }

    @Override
    public boolean existePorEmail(String email) {
        // Verifica si existe un empleado con el email especificado
        return empleadoRepository.existsByEmail(email);
    }

    //------------------------ BUSQUEDAS ESPECIALES ------------------------//

    @Override
    public List<Empleado> buscarPorNombreOApellido(String criterio) {
        // Busca empleados cuyo nombre o apellidos contengan el criterio (búsqueda parcial)
        return empleadoRepository.findByNombresContainingOrApellidosPaternoContainingOrApellidosMaternoContaining(criterio);
    }

    @Override
    public Empleado buscarPorDni(String dni) {
        // Busca un empleado por su DNI exacto
        return empleadoRepository.findByDni(dni);
    }

    //------------------------ MÉTRICAS Y REPORTES ------------------------//

    @Override
    public long contarEmpleados() {
        // Cuenta el total de empleados registrados
        return empleadoRepository.count();
    }

    @Override
    public long contarNuevosEsteMes() {
        // Cuenta empleados contratados desde el inicio del mes actual
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        return empleadoRepository.countByFechaContratacionAfter(java.sql.Date.valueOf(inicioMes));
    }

    @Override
    public List<Empleado> listarUltimos5() {
        // Obtiene los 5 empleados más recientes por fecha de contratación
        return empleadoRepository.findTop5ByOrderByFechaContratacionDesc();
    }
}