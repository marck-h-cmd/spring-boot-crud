package servicio;

import entidad.Producto;
import java.util.List;
import java.math.BigDecimal;

/**
 * Interfaz que define los servicios disponibles para la gestión de productos.
 * Contiene operaciones CRUD básicas y métodos específicos para el negocio.
 */
public interface ProductoService {
    
    /**
     * Obtiene todos los productos registrados en el sistema.
     * @return Lista completa de productos
     */
    List<Producto> listarTodos();

    /**
     * Busca un producto específico por su ID único.
     * @param id Identificador único del producto
     * @return Producto encontrado o null si no existe
     */
    Producto buscar(Long id);

    /**
     * Registra un nuevo producto en el sistema.
     * @param producto Objeto Producto con los datos a registrar
     * @return Producto registrado con ID generado
     */
    Producto agregar(Producto producto);

    /**
     * Actualiza los datos de un producto existente.
     * @param producto Objeto con los datos actualizados del producto
     * @return Producto actualizado
     */
    Producto actualizar(Producto producto);

    /**
     * Elimina un producto del sistema usando su ID.
     * @param id Identificador único del producto a eliminar
     */
    void eliminar(Long id);

    /**
     * Verifica si existe un producto con el código especificado.
     * @param codigo Código del producto a verificar
     * @return true si existe un producto con ese código, false en caso contrario
     */
    boolean existePorCodigo(String codigo);
    
    /**
     * Busca un producto por su código único.
     * @param codigo Código del producto a buscar
     * @return Producto encontrado o null si no existe
     */
    Producto buscarPorCodigo(String codigo);
    
    /**
     * Busca productos por nombre (búsqueda parcial).
     * @param nombre Criterio de búsqueda para el nombre
     * @return Lista de productos que coinciden con el criterio
     */
    List<Producto> buscarPorNombre(String nombre);
    
    /**
     * Busca productos por categoría.
     * @param categoria Categoría de productos a buscar
     * @return Lista de productos de la categoría especificada
     */
    List<Producto> buscarPorCategoria(String categoria);
    
    /**
     * Obtiene solo los productos activos.
     * @return Lista de productos activos
     */
    List<Producto> listarActivos();
    
    /**
     * Obtiene productos con stock bajo (stock <= stockMinimo).
     * @return Lista de productos con stock bajo
     */
    List<Producto> listarProductosConStockBajo();
    
    /**
     * Busca productos en un rango de precios.
     * @param precioMin Precio mínimo
     * @param precioMax Precio máximo
     * @return Lista de productos en el rango de precios
     */
    List<Producto> buscarPorRangoPrecio(BigDecimal precioMin, BigDecimal precioMax);
    
    /**
     * Obtiene todas las categorías disponibles.
     * @return Lista de categorías distintas
     */
    List<String> listarCategorias();
    
    /**
     * Busca productos por nombre o descripción.
     * @param criterio Texto a buscar en nombre o descripción
     * @return Lista de productos que coinciden con el criterio
     */
    List<Producto> buscarPorNombreODescripcion(String criterio);
    
    /**
     * Cuenta el total de productos registrados.
     * @return Número total de productos
     */
    long contarProductos();
    
    /**
     * Cuenta productos por categoría.
     * @param categoria Categoría a contar
     * @return Número de productos en la categoría
     */
    long contarPorCategoria(String categoria);
    
    /**
     * Actualiza el stock de un producto.
     * @param id ID del producto
     * @param nuevoStock Nuevo stock del producto
     * @return Producto actualizado
     */
    Producto actualizarStock(Long id, Integer nuevoStock);
}