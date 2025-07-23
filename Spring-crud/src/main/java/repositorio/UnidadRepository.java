package repositorio;

import entidad.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para operaciones de base de datos con Unidades de medida
 * Hereda operaciones CRUD básicas de JpaRepository
 */
public interface UnidadRepository extends JpaRepository<Unidad, Long> {
    boolean existsByDescripcion(String descripcion);
}