package repositorio;

import entidad.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para operaciones de base de datos con Categorías
 * Hereda operaciones CRUD básicas de JpaRepository
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByDescripcion(String descripcion);
}

