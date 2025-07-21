package servicio;

import entidad.Empleado;
import entidad.Proveedor;
import java.util.List;

/**
 * Interfaz que define los servicios disponibles para la gestión de proveedores.
 * Contiene operaciones CRUD básicas y métodos de validación para RUC y email.
 */
public interface ProveedorService {
    
    /**
     * Obtiene todos los proveedores registrados en el sistema.
     * @return Lista completa de proveedores
     */
    List<Proveedor> listarTodos();

    /**
     * Busca un proveedor específico por su ID único.
     * @param id Identificador único del proveedor
     * @return Proveedor encontrado o null si no existe
     */
    Proveedor buscar(Long id);

    /**
     * Registra un nuevo proveedor en el sistema.
     * @param proveedor Objeto Proveedor con los datos a registrar
     * @return Proveedor registrado con ID generado
     */
    Proveedor agregar(Proveedor proveedor);

    /**
     * Actualiza los datos de un proveedor existente.
     * @param proveedor Objeto con los datos actualizados del proveedor
     * @return Proveedor actualizado
     */
    Proveedor actualizar(Proveedor proveedor);

    /**
     * Elimina un proveedor del sistema usando su ID.
     * @param id Identificador único del proveedor a eliminar
     */
    void eliminar(Long id);

    /**
     * Verifica si existe un proveedor con el RUC especificado.
     * @param ruc Número de RUC (11 dígitos) a verificar
     * @return true si existe un proveedor con ese RUC, false en caso contrario
     */
    boolean existePorRuc(String ruc);

    /**
     * Verifica si existe un proveedor con el email especificado.
     * @param email Dirección de correo electrónico a verificar
     * @return true si existe un proveedor con ese email, false en caso contrario
     */
    boolean existePorEmail(String email);
    
    Proveedor buscarPorRuc(String ruc);
}