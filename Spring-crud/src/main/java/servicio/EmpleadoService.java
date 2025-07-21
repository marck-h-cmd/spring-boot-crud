package servicio;

import entidad.Empleado;
import java.util.List;

/**
 * Interfaz que define los servicios disponibles para la gestión de empleados.
 * Contiene operaciones CRUD básicas y métodos específicos para validaciones y consultas.
 */
public interface EmpleadoService {

    /**
     * Registra un nuevo empleado en el sistema.
     * @param empleado Objeto Empleado con los datos a registrar
     * @return Empleado registrado con ID generado
     */
    public Empleado agregar(Empleado empleado);

    /**
     * Obtiene todos los empleados registrados.
     * @return Lista completa de empleados
     */
    public List<Empleado> listarTodos();

    /**
     * Busca un empleado por su ID único.
     * @param id Identificador único del empleado
     * @return Empleado encontrado o null si no existe
     */
    public Empleado buscar(Long id);

    /**
     * Actualiza los datos de un empleado existente.
     * @param empleado Objeto con los nuevos datos del empleado
     * @return Empleado actualizado
     */
    public Empleado actualizar(Empleado empleado);

    /**
     * Elimina un empleado del sistema usando su ID.
     * @param id Identificador único del empleado a eliminar
     */
    public void eliminar(Long id);

    /**
     * Verifica si existe un empleado con el DNI especificado.
     * @param dni Número de documento a verificar
     * @return true si existe, false si no existe
     */
    public boolean existePorDni(String dni);

    /**
     * Verifica si existe un empleado con el email especificado.
     * @param email Dirección de correo a verificar
     * @return true si existe, false si no existe
     */
    public boolean existePorEmail(String email);

    /**
     * Busca empleados cuyo nombre o apellidos contengan el criterio especificado.
     * @param criterio Texto a buscar en nombres o apellidos
     * @return Lista de empleados que coinciden con el criterio
     */
    public List<Empleado> buscarPorNombreOApellido(String criterio);

    /**
     * Obtiene el número total de empleados registrados.
     * @return Cantidad total de empleados
     */
    public long contarEmpleados();

    /**
     * Cuenta los empleados contratados en el mes actual.
     * @return Cantidad de nuevos empleados este mes
     */
    public long contarNuevosEsteMes();

    /**
     * Obtiene los 5 empleados más recientemente contratados.
     * @return Lista de los últimos 5 empleados registrados
     */
    public List<Empleado> listarUltimos5();

    /**
     * Busca un empleado por su número de DNI exacto.
     * @param dni Número de documento a buscar
     * @return Empleado encontrado o null si no existe
     */
    Empleado buscarPorDni(String dni);
}