package repositorio;

import entidad.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Métodos básicos de JpaRepository
    List<Cliente> findAll();
    
    // Métodos personalizados
    boolean existsByRucDni(String rucDni);
    
    Cliente findByRucDni(String rucDni);
    
    @Query("SELECT c FROM Cliente c WHERE c.estado = true")
    List<Cliente> findClientesActivos();
    
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.apellido) LIKE LOWER(concat('%', :termino, '%')) OR LOWER(c.nombre) LIKE LOWER(concat('%', :termino, '%'))")
    List<Cliente> buscarPorApellidoONombre(String termino);
    
    @Query("SELECT c FROM Cliente c WHERE c.sexo = :sexo AND c.estado = true")
    List<Cliente> findBySexoActivos(char sexo);
    
    @Query("SELECT c FROM Cliente c WHERE c.rucDni LIKE %:termino%")
    List<Cliente> buscarPorRucDniParcial(String termino);
}