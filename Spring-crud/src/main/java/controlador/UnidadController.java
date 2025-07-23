package controlador;

import entidad.Unidad;
import servicio.UnidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/unidad")
public class UnidadController {

    @Autowired
    private UnidadService unidadService;

    @GetMapping("/listar")
    public String listarUnidades(Model model) {
        model.addAttribute("unidades", unidadService.listarTodos());
        model.addAttribute("titulo", "Lista de Unidades");
        return "unidad/listar";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("unidad", new Unidad());
        model.addAttribute("titulo", "Nueva Unidad");
        model.addAttribute("modo", "crear");
        return "unidad/formulario";
    }

    @PostMapping("/guardar")
    public String guardarUnidad(@Valid @ModelAttribute Unidad unidad, 
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirect,
                              @RequestParam String modo) {
        
        if(modo.equals("crear") && unidadService.existePorDescripcion(unidad.getDescripcion())) {
            result.rejectValue("descripcion", "error.descripcion", "La unidad ya existe");
        }

        if(result.hasErrors()) {
            model.addAttribute("titulo", modo.equals("editar") ? "Editar Unidad" : "Nueva Unidad");
            model.addAttribute("modo", modo);
            return "unidad/formulario";
        }

        if(modo.equals("crear")) {
            unidadService.agregar(unidad);
            redirect.addFlashAttribute("success", "Unidad creada correctamente");
        } else {
            unidadService.actualizar(unidad);
            redirect.addFlashAttribute("success", "Unidad actualizada correctamente");
        }
        
        return "redirect:/unidad/listar";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Unidad unidad = unidadService.buscar(id);
        if(unidad == null) {
            return "redirect:/unidad/listar";
        }
        model.addAttribute("unidad", unidad);
        model.addAttribute("titulo", "Editar Unidad");
        model.addAttribute("modo", "editar");
        return "unidad/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarUnidad(@PathVariable Long id, RedirectAttributes redirect) {
        unidadService.eliminar(id);
        redirect.addFlashAttribute("success", "Unidad eliminada correctamente");
        return "redirect:/unidad/listar";
    }
}