package servicio;

import entidad.Unidad;
import repositorio.UnidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UnidadServiceImpl implements UnidadService {

    @Autowired
    private UnidadRepository unidadRepository;

    @Override
    public List<Unidad> listarTodos() {
        return unidadRepository.findAll();
    }

    @Override
    public Unidad buscar(Long id) {
        return unidadRepository.findById(id).orElse(null);
    }

    @Override
    public Unidad agregar(Unidad unidad) {
        return unidadRepository.save(unidad);
    }

    @Override
    public Unidad actualizar(Unidad unidad) {
        return unidadRepository.save(unidad);
    }

    @Override
    public void eliminar(Long id) {
        unidadRepository.deleteById(id);
    }

    @Override
    public boolean existePorDescripcion(String descripcion) {
        return unidadRepository.existsByDescripcion(descripcion);
    }
}