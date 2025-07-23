package servicio;

import entidad.Producto;
import repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.math.BigDecimal;

/**
 * Implementación concreta del servicio para gestión de productos
 * que interactúa con el repositorio JPA para operaciones de persistencia.
 * 
 * Proporciona operaciones CRUD y validaciones para la entidad Producto.
 */
@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    //------------------------ OPERACIONES CRUD BÁSICAS ------------------------//

    /**
     * Obtiene todos los productos registrados en el sistema
     */
    @Override
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    /**
     * Busca un producto por su ID único
     */
    @Override
    public Producto buscar(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    /**
     * Registra un nuevo producto en el sistema
     */
    @Override
    public Producto agregar(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Actualiza los datos de un producto existente
     */
    @Override
    public Producto actualizar(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Elimina un producto del sistema
     */
    @Override
    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    //------------------------ VALIDACIONES DE NEGOCIO ------------------------//

    /**
     * Verifica si existe un producto con el código especificado
     */
    @Override
    public boolean existePorCodigo(String codigo) {
        return productoRepository.existsByCodigo(codigo);
    }
    
    /**
     * Busca un producto por su código único
     */
    @Override
    public Producto buscarPorCodigo(String codigo) {
        return productoRepository.findByCodigo(codigo);
    }

    //------------------------ BÚSQUEDAS ESPECIALIZADAS ------------------------//

    /**
     * Busca productos por nombre (búsqueda parcial)
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    /**
     * Busca productos por categoría
     */
    @Override
    public List<Producto> buscarPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    /**
     * Obtiene solo los productos activos
     */
    @Override
    public List<Producto> listarActivos() {
        return productoRepository.findByActivoTrue();
    }

    /**
     * Obtiene productos con stock bajo
     */
    @Override
    public List<Producto> listarProductosConStockBajo() {
        return productoRepository.findProductosConStockBajo();
    }

    /**
     * Busca productos en un rango de precios
     */
    @Override
    public List<Producto> buscarPorRangoPrecio(BigDecimal precioMin, BigDecimal precioMax) {
        return productoRepository.findByPrecioBetween(precioMin, precioMax);
    }

    /**
     * Obtiene todas las categorías disponibles
     */
    @Override
    public List<String> listarCategorias() {
        return productoRepository.findDistinctCategorias();
    }

    /**
     * Busca productos por nombre o descripción
     */
    @Override
    public List<Producto> buscarPorNombreODescripcion(String criterio) {
        return productoRepository.buscarPorNombreODescripcion(criterio);
    }

    //------------------------ MÉTRICAS Y REPORTES ------------------------//

    /**
     * Cuenta el total de productos registrados
     */
    @Override
    public long contarProductos() {
        return productoRepository.count();
    }

    /**
     * Cuenta productos por categoría
     */
    @Override
    public long contarPorCategoria(String categoria) {
        return productoRepository.countByCategoria(categoria);
    }

    //------------------------ OPERACIONES DE STOCK ------------------------//

    /**
     * Actualiza el stock de un producto
     */
    @Override
    public Producto actualizarStock(Long id, Integer nuevoStock) {
        Producto producto = buscar(id);
        if (producto != null) {
            producto.setStock(nuevoStock);
            return actualizar(producto);
        }
        return null;
    }
}