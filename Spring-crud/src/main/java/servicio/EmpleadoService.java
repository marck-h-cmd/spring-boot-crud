package servicio;

import entidad.Empleado;
import java.util.List;

public interface EmpleadoService {
    public Empleado agregar(Empleado empleado);
    public List<Empleado> listarTodos();
    public Empleado buscar(Long id);
    public Empleado actualizar(Empleado empleado);
    public void eliminar(Long id);
    public boolean existePorDni(String dni);
    public boolean existePorEmail(String email);
    public List<Empleado> buscarPorNombreOApellido(String criterio);
    public long contarEmpleados();
    public long contarNuevosEsteMes();
    public List<Empleado> listarUltimos5();
    Empleado buscarPorDni(String dni);
}