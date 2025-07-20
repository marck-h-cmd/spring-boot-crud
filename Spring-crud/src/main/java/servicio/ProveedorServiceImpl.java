package servicio;

import entidad.Proveedor;
import repositorio.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor buscar(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    @Override
    public Proveedor agregar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public Proveedor actualizar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public void eliminar(Long id) {
        proveedorRepository.deleteById(id);
    }

    @Override
    public boolean existePorRuc(String ruc) {
        return proveedorRepository.existsByRuc(ruc);
    }

    @Override
    public boolean existePorEmail(String email) {
        return proveedorRepository.existsByEmail(email);
    }
}