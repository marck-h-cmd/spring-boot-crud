package servicio;

import entidad.Cliente;
import repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public List<Cliente> listarActivos() {
        return clienteRepository.findClientesActivos();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public Cliente buscarPorRucDni(String rucDni) {
        return clienteRepository.findByRucDni(rucDni);
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public boolean existePorRucDni(String rucDni) {
        return clienteRepository.existsByRucDni(rucDni);
    }

    @Override
    public List<Cliente> buscarPorNombreOApellido(String termino) {
        return clienteRepository.buscarPorApellidoONombre(termino);
    }

    @Override
    public List<Cliente> buscarPorRucDniParcial(String termino) {
        return clienteRepository.buscarPorRucDniParcial(termino);
    }
}