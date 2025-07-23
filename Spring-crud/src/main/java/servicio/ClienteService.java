package servicio;

import entidad.Cliente;
import java.util.List;

public interface ClienteService {
    List<Cliente> listarTodos();
    List<Cliente> listarActivos();
    Cliente buscarPorId(Long id);
    Cliente buscarPorRucDni(String rucDni);
    Cliente guardar(Cliente cliente);
    void eliminar(Long id);
    boolean existePorRucDni(String rucDni);
    List<Cliente> buscarPorNombreOApellido(String termino);
    List<Cliente> buscarPorRucDniParcial(String termino);
}