package repositorio;

import entidad.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    
    List<DetalleVenta> findByVentaId(Long ventaId);
    
    List<DetalleVenta> findByProductoId(Long productoId);
    
    @Query("SELECT dv FROM DetalleVenta dv WHERE dv.venta.cliente.id = :clienteId")
    List<DetalleVenta> findByClienteId(Long clienteId);
}