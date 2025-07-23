package servicio;

import entidad.Unidad;
import java.util.List;

/**
 * Servicio para lógica de negocio relacionada con Unidades de medida
 */
public interface UnidadService {
    List<Unidad> listarTodos();
    Unidad buscar(Long id);
    Unidad agregar(Unidad unidad);
    Unidad actualizar(Unidad unidad);
    void eliminar(Long id);
    boolean existePorDescripcion(String descripcion);
}

