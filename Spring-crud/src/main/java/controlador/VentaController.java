package controlador;

import entidad.*;
import servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        venta.setDetalles(List.of(new DetalleVenta()));
        
        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteService.listarActivos());
        model.addAttribute("productos", productoService.listarActivos());
        return "venta/formulario";
    }

    @PostMapping("/guardar")
    public String guardarVenta(@ModelAttribute Venta venta) {
        ventaService.guardarVenta(venta);
        return "redirect:/ventas";
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
}