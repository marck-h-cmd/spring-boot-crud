package repositorio;

import entidad.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    
    List<Venta> findByClienteId(Long clienteId);
    
    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
    
    @Query("SELECT v FROM Venta v WHERE v.estado = true")
    List<Venta> findVentasActivas();
    
    @Query("SELECT v FROM Venta v WHERE v.tipoComprobante = :tipo")
    List<Venta> findByTipoComprobante(String tipo);
    
    boolean existsByNumeroComprobante(String numeroComprobante);
}