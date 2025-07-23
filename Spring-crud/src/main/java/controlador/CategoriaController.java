package controlador;

import entidad.Categoria;
import servicio.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listar")
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodos());
        model.addAttribute("titulo", "Lista de Categorías");
        return "categoria/listar";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("titulo", "Nueva Categoría");
        model.addAttribute("modo", "crear");
        return "categoria/formulario";
    }

    @PostMapping("/guardar")
    public String guardarCategoria(@Valid @ModelAttribute Categoria categoria, 
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirect,
                                 @RequestParam String modo) {
        
        if(modo.equals("crear") && categoriaService.existePorDescripcion(categoria.getDescripcion())) {
            result.rejectValue("descripcion", "error.descripcion", "La categoría ya existe");
        }

        if(result.hasErrors()) {
            model.addAttribute("titulo", modo.equals("editar") ? "Editar Categoría" : "Nueva Categoría");
            model.addAttribute("modo", modo);
            return "categoria/formulario";
        }

        if(modo.equals("crear")) {
            categoriaService.agregar(categoria);
            redirect.addFlashAttribute("success", "Categoría creada correctamente");
        } else {
            categoriaService.actualizar(categoria);
            redirect.addFlashAttribute("success", "Categoría actualizada correctamente");
        }
        
        return "redirect:/categoria/listar";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaService.buscar(id);
        if(categoria == null) {
            return "redirect:/categoria/listar";
        }
        model.addAttribute("categoria", categoria);
        model.addAttribute("titulo", "Editar Categoría");
        model.addAttribute("modo", "editar");
        return "categoria/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id, RedirectAttributes redirect) {
        categoriaService.eliminar(id);
        redirect.addFlashAttribute("success", "Categoría eliminada correctamente");
        return "redirect:/categoria/listar";
    }
}

