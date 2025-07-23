package servicio;

import entidad.Venta;
import java.time.LocalDateTime;
import java.util.List;

public interface VentaService {
    Venta guardarVenta(Venta venta);
    Venta buscarPorId(Long id);
    List<Venta> listarTodas();
    List<Venta> listarPorCliente(Long clienteId);
    List<Venta> listarPorFecha(LocalDateTime inicio, LocalDateTime fin);
    void anularVenta(Long id);
    boolean existeComprobante(String numeroComprobante);
}