package controlador;

import entidad.*;
import servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listarVentas(Model model) {
        model.addAttribute("ventas", ventaService.listarTodas());
        return "venta/listar";
    }

    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        Venta venta = new Venta();
        
        // Inicializar con un detalle y valores por defecto
        DetalleVenta detalle = new DetalleVenta();
        detalle.setCantidad(1);
        detalle.setPrecio(BigDecimal.ZERO);
        
        venta.setDetalles(List.of(detalle));
        
        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteService.listarActivos());
        model.addAttribute("productos", productoService.listarActivos());
        model.addAttribute("titulo", "Nueva Venta");
        return "venta/formulario";
    }

    @PostMapping("/guardar")
    public String guardarVenta(@ModelAttribute Venta venta, Model model) {
        try {
            ventaService.guardarVenta(venta);
            return "redirect:/ventas";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar la venta: " + e.getMessage());
            model.addAttribute("venta", venta);
            model.addAttribute("clientes", clienteService.listarActivos());
            model.addAttribute("productos", productoService.listarActivos());
            return "venta/formulario";
        }
    }

    @GetMapping("/anular/{id}")
    public String anularVenta(@PathVariable Long id) {
        ventaService.anularVenta(id);
        return "redirect:/ventas";
    }

    @GetMapping("/buscar")
    public String buscarVentas(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            Model model) {
        
        if(clienteId != null) {
            model.addAttribute("ventas", ventaService.listarPorCliente(clienteId));
        } else if(fechaInicio != null && fechaFin != null) {
            LocalDateTime inicio = LocalDateTime.parse(fechaInicio);
            LocalDateTime fin = LocalDateTime.parse(fechaFin);
            model.addAttribute("ventas", ventaService.listarPorFecha(inicio, fin));
        }
        
        return "venta/listar";
    }

    @PostMapping("/agregar-detalle")
    public String agregarDetalle(@ModelAttribute Venta venta, Model model) {
        DetalleVenta nuevoDetalle = new DetalleVenta();
        nuevoDetalle.setCantidad(1);
        nuevoDetalle.setPrecio(BigDecimal.ZERO);
        venta.getDetalles().add(nuevoDetalle);
        
        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteService.listarActivos());
        model.addAttribute("productos", productoService.listarActivos());
        return "venta/formulario";
    }

    @PostMapping("/eliminar-detalle")
    public String eliminarDetalle(
            @ModelAttribute Venta venta,
            @RequestParam("index") int index,
            Model model) {
        if (index >= 0 && index < venta.getDetalles().size()) {
            venta.getDetalles().remove(index);
        }
        
        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteService.listarActivos());
        model.addAttribute("productos", productoService.listarActivos());
        return "venta/formulario";
    }
}