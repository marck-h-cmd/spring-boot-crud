package repositorio;


import entidad.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para gestionar operaciones de base de datos con la entidad Proveedor.
 * Extiende JpaRepository para heredar operaciones CRUD básicas.
 */
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
	
	  // Busca un empleado por su DNI (exact match)
    Proveedor findByRuc(String ruc);
    
    /**
     * Verifica si existe un proveedor con el RUC especificado.
     * @param ruc Número de RUC (11 dígitos) a verificar
     */
    boolean existsByRuc(String ruc);
    
    /**
     * Verifica si existe un proveedor con el email especificado.
     * @param email Dirección de correo electrónico a verificar
     */
    boolean existsByEmail(String email);
}

