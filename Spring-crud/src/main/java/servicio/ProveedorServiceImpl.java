package servicio;

import entidad.Empleado;
import entidad.Proveedor;
import repositorio.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Implementación concreta del servicio para gestión de proveedores
 * que interactúa con el repositorio JPA para operaciones de persistencia.
 * 
 * Proporciona operaciones CRUD y validaciones para la entidad Proveedor.
 */
@Service // Indica que esta clase es un componente de servicio de Spring
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired // Inyección automática del repositorio de proveedores
    private ProveedorRepository proveedorRepository;

    //------------------------ OPERACIONES CRUD BÁSICAS ------------------------//

    /**
     * Obtiene todos los proveedores registrados en el sistema
     * @return Lista completa de proveedores
     */
    @Override
    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }

    /**
     * Busca un proveedor por su ID único
     * @param id Identificador del proveedor
     * @return Proveedor encontrado o null si no existe
     */
    @Override
    public Proveedor buscar(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    /**
     * Registra un nuevo proveedor en el sistema
     * @param proveedor Datos del proveedor a registrar
     * @return Proveedor registrado con su ID generado
     */
    @Override
    public Proveedor agregar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    /**
     * Actualiza los datos de un proveedor existente
     * @param proveedor Datos actualizados del proveedor
     * @return Proveedor actualizado
     */
    @Override
    public Proveedor actualizar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    /**
     * Elimina un proveedor del sistema
     * @param id Identificador del proveedor a eliminar
     */
    @Override
    public void eliminar(Long id) {
        proveedorRepository.deleteById(id);
    }

    //------------------------ VALIDACIONES DE NEGOCIO ------------------------//

    /**
     * Verifica si existe un proveedor con el RUC especificado
     * @param ruc Número de RUC a verificar (11 dígitos)
     * @return true si existe, false si no existe
     */
    @Override
    public boolean existePorRuc(String ruc) {
        return proveedorRepository.existsByRuc(ruc);
    }
    
    @Override
    public Proveedor buscarPorRuc(String ruc) {
        // Busca un empleado por su DNI exacto
        return proveedorRepository.findByRuc(ruc);
    }

    /**
     * Verifica si existe un proveedor con el email especificado
     * @param email Dirección de correo a verificar
     * @return true si existe, false si no existe
     */
    @Override
    public boolean existePorEmail(String email) {
        return proveedorRepository.existsByEmail(email);
    }
}