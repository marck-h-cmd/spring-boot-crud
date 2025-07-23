package servicio;

import entidad.Categoria;
import java.util.List;

/**
 * Servicio para lógica de negocio relacionada con Categorías
 */
public interface CategoriaService {
    List<Categoria> listarTodos();
    Categoria buscar(Long id);
    Categoria agregar(Categoria categoria);
    Categoria actualizar(Categoria categoria);
    void eliminar(Long id);
    boolean existePorDescripcion(String descripcion);
}