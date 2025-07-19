package servicio;

import entidad.Empleado;

import repositorio.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


import java.time.LocalDate;
@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public Empleado agregar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado buscar(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public Empleado actualizar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    public void eliminar(Long id) {
        empleadoRepository.deleteById(id);
    }

    @Override
    public boolean existePorDni(String dni) {
        return empleadoRepository.existsByDni(dni);
    }

    @Override
    public boolean existePorEmail(String email) {
        return empleadoRepository.existsByEmail(email);
    }

    @Override
    public List<Empleado> buscarPorNombreOApellido(String criterio) {
        return empleadoRepository.findByNombresContainingOrApellidosPaternoContainingOrApellidosMaternoContaining(
            criterio);
    }

    @Override
    public long contarEmpleados() {
        return empleadoRepository.count();
    }
    
    @Override
    public Empleado buscarPorDni(String dni) {
        return empleadoRepository.findByDni(dni);
    }

    @Override
    public long contarNuevosEsteMes() {
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        return empleadoRepository.countByFechaContratacionAfter(java.sql.Date.valueOf(inicioMes));
    }

    @Override
    public List<Empleado> listarUltimos5() {
        return empleadoRepository.findTop5ByOrderByFechaContratacionDesc();
    }
}