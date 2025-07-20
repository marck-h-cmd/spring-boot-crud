package servicio;

import entidad.Proveedor;
import java.util.List;

public interface ProveedorService {
    List<Proveedor> listarTodos();
    Proveedor buscar(Long id);
    Proveedor agregar(Proveedor proveedor);
    Proveedor actualizar(Proveedor proveedor);
    void eliminar(Long id);
    boolean existePorRuc(String ruc);
    boolean existePorEmail(String email);
}