package controlador;

import entidad.Proveedor;
import servicio.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador para gestionar las operaciones CRUD de proveedores
 */
@Controller
@RequestMapping("/proveedor")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    /**
     * Muestra la lista de todos los proveedores
     */
    @GetMapping("/listar")
    public String listarProveedores(Model model) {
        model.addAttribute("proveedores", proveedorService.listarTodos());
        model.addAttribute("titulo", "Lista de Proveedores");
        return "proveedor/listar";
    }

    /**
     * Muestra formulario para crear nuevo proveedor
     */
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        model.addAttribute("titulo", "Registrar Nuevo Proveedor");
        model.addAttribute("modo", "crear");
        return "proveedor/formulario";
    }

    /**
     * Procesa el guardado de proveedores (creación y actualización)
     */
    @PostMapping("/guardar")
    public String guardarProveedor(@Valid @ModelAttribute Proveedor proveedor, 
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirect,
                                 @RequestParam String modo) {
        
        // Validación condicional para RUC (solo si es nuevo o cambió)
        if(modo.equals("crear")) {
            if(proveedorService.existePorRuc(proveedor.getRuc())) {
                result.rejectValue("ruc", "error.ruc", "El RUC ya está registrado");
            }
        } else {
            Proveedor existente = proveedorService.buscarPorRuc(proveedor.getRuc());
            if(existente != null && !existente.getId().equals(proveedor.getId())) {
                result.rejectValue("ruc", "error.ruc", "El RUC ya está registrado");
            }
        }

        // Validación condicional para email (solo si es nuevo o cambió)
        if(modo.equals("crear")) {
            if(proveedorService.existePorEmail(proveedor.getEmail())) {
                result.rejectValue("email", "error.email", "El email ya está registrado");
            }
        } else {
            Proveedor existente = proveedorService.buscar(proveedor.getId());
            if(existente != null && !existente.getEmail().equals(proveedor.getEmail())) {
                if(proveedorService.existePorEmail(proveedor.getEmail())) {
                    result.rejectValue("email", "error.email", "El email ya está registrado");
                }
            }
        }

        if(result.hasErrors()) {
            model.addAttribute("titulo", modo.equals("editar") ? "Editar Proveedor" : "Nuevo Proveedor");
            model.addAttribute("modo", modo);
            return "proveedor/formulario";
        }

        if(modo.equals("crear")) {
            proveedorService.agregar(proveedor);
            redirect.addFlashAttribute("success", "Proveedor registrado correctamente");
        } else {
            proveedorService.actualizar(proveedor);
            redirect.addFlashAttribute("success", "Proveedor actualizado correctamente");
        }
        
        return "redirect:/proveedor/listar";
    }

    /**
     * Muestra formulario para editar proveedor existente
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Proveedor proveedor = proveedorService.buscar(id);
        
        if(proveedor == null) {
            return "redirect:/proveedor/listar";
        }

        model.addAttribute("proveedor", proveedor);
        model.addAttribute("titulo", "Editar Proveedor");
        model.addAttribute("modo", "editar");
        return "proveedor/formulario";
    }

    /**
     * Elimina un proveedor por su ID
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable Long id, RedirectAttributes redirect) {
        proveedorService.eliminar(id);
        redirect.addFlashAttribute("success", "Proveedor eliminado correctamente");
        return "redirect:/proveedor/listar";
    }
}