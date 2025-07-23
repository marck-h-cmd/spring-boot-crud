package entidad;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad que representa una venta en el sistema
 * Relaciona Cliente con Productos a través de DetalleVenta
 */
@Entity
@Table(name = "ventas")
@Data
public class Venta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();
    
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
    
    @Column(length = 20)
    private String tipoComprobante; // Factura, Boleta, etc.
    
    @Column(length = 20)
    private String numeroComprobante;
    
    @Column(nullable = false)
    private Boolean estado = true; // Para anulaciones
    
    // Método para calcular el total
    @PrePersist
    @PreUpdate
    private void calcularTotal() {
        this.total = detalles.stream()
            .map(d -> d.getPrecio().multiply(BigDecimal.valueOf(d.getCantidad())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}