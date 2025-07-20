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

@Controller
@RequestMapping("/proveedor")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/")
    public String listarProveedores(Model model) {
        model.addAttribute("proveedores", proveedorService.listarTodos());
        model.addAttribute("titulo", "Lista de Proveedores");
        return "proveedor/listar";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        model.addAttribute("titulo", "Registrar Nuevo Proveedor");
        model.addAttribute("modo", "crear");
        return "proveedor/formulario";
    }

    @PostMapping("/guardar")
    public String guardarProveedor(@Valid @ModelAttribute Proveedor proveedor, 
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirect,
                                 @RequestParam String modo) {
        
        if(proveedorService.existePorRuc(proveedor.getRuc())) {
            result.rejectValue("ruc", "error.ruc", "El RUC ya está registrado");
        }
        
        if(proveedorService.existePorEmail(proveedor.getEmail())) {
            result.rejectValue("email", "error.email", "El email ya está registrado");
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
        
        return "redirect:/proveedor/";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Proveedor proveedor = proveedorService.buscar(id);
        
        if(proveedor == null) {
            return "redirect:/proveedor/";
        }

        model.addAttribute("proveedor", proveedor);
        model.addAttribute("titulo", "Editar Proveedor");
        model.addAttribute("modo", "editar");
        return "proveedor/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable Long id, RedirectAttributes redirect) {
        proveedorService.eliminar(id);
        redirect.addFlashAttribute("success", "Proveedor eliminado correctamente");
        return "redirect:/proveedor/";
    }
}