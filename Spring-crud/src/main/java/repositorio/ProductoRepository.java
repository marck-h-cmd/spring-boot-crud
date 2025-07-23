package repositorio;

import entidad.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.math.BigDecimal;

/**
 * Repositorio para gestionar operaciones de base de datos con la entidad Producto.
 * Extiende JpaRepository para heredar operaciones CRUD básicas.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    /**
     * Busca un producto por su código exacto
     */
    Producto findByCodigo(String codigo);
    
    /**
     * Verifica si existe un producto con el código especificado
     */
    boolean existsByCodigo(String codigo);
    
    /**
     * Busca productos por nombre (contiene el criterio)
     */
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    /**
     * Busca productos por categoría
     */
    List<Producto> findByCategoria(String categoria);
    
    /**
     * Busca productos activos
     */
    List<Producto> findByActivoTrue();
    
    /**
     * Busca productos con stock bajo (stock <= stockMinimo)
     */
    @Query("SELECT p FROM Producto p WHERE p.stock <= p.stockMinimo AND p.activo = true")
    List<Producto> findProductosConStockBajo();
    
    /**
     * Busca productos por rango de precio
     */
    List<Producto> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);
    
    /**
     * Obtiene todas las categorías distintas
     */
    @Query("SELECT DISTINCT p.categoria FROM Producto p ORDER BY p.categoria")
    List<String> findDistinctCategorias();
    
    /**
     * Cuenta productos por categoría
     */
    long countByCategoria(String categoria);
    
    /**
     * Busca productos por nombre o descripción
     */
    @Query("SELECT p FROM Producto p WHERE " +
           "LOWER(p.nombre) LIKE LOWER(CONCAT('%', ?1, '%')) OR " +
           "LOWER(p.descripcion) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Producto> buscarPorNombreODescripcion(String criterio);
}