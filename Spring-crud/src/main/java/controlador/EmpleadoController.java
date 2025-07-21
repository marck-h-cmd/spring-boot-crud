package controlador;

import entidad.Empleado;
import servicio.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.*;
import java.util.List;

/**
 * Controlador para gestionar las operaciones CRUD de empleados
 */
@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    /**
     * Muestra la lista de todos los empleados
     */
    @GetMapping("/listar")
    public String listarEmpleados(Model model) {
        List<Empleado> empleados = empleadoService.listarTodos();
        model.addAttribute("empleados", empleados);
        model.addAttribute("titulo", "Lista de Empleados");
        return "empleado/listar"; 
    }

    /**
     * Muestra el formulario para crear un nuevo empleado
     */
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("titulo", "Registrar Nuevo Empleado");
        model.addAttribute("modo", "crear");
        return "empleado/form";
    }

    /**
     * Procesa el guardado o actualización de un empleado
     */
    @PostMapping("/guardar")
    public String guardarEmpleado(@Valid @ModelAttribute Empleado empleado, 
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirect,
                                @RequestParam String modo) {
        
        // Solo validar DNI si es nuevo registro o si el DNI ha cambiado
        if(modo.equals("crear")) {
            if(empleadoService.existePorDni(empleado.getDni())) {
                result.rejectValue("dni", "error.dni", "El DNI ya está registrado");
            }
        } else {
            Empleado empleadoExistente = empleadoService.buscarPorDni(empleado.getDni());
            if(empleadoExistente != null && !empleadoExistente.getId().equals(empleado.getId())) {
                result.rejectValue("dni", "error.dni", "El DNI ya está registrado");
            }
        }

        // Solo validar email si es nuevo registro o si el email ha cambiado
        if(modo.equals("crear")) {
            if(empleadoService.existePorEmail(empleado.getEmail())) {
                result.rejectValue("email", "error.email", "El email ya está registrado");
            }
        } else {
            Empleado empleadoExistente = empleadoService.buscar(empleado.getId());
            if(empleadoExistente != null && !empleadoExistente.getEmail().equals(empleado.getEmail())) {
                if(empleadoService.existePorEmail(empleado.getEmail())) {
                    result.rejectValue("email", "error.email", "El email ya está registrado");
                }
            }
        }

        if(result.hasErrors()) {
            model.addAttribute("titulo", modo.equals("editar") ? "Editar Empleado" : "Nuevo Empleado");
            model.addAttribute("modo", modo);
            return "empleado/form";
        }

        empleadoService.agregar(empleado);
        redirect.addFlashAttribute("success", modo.equals("editar") ? "Empleado actualizado correctamente" : "Empleado registrado correctamente");
        return "redirect:/empleado/listar";
    }

    /**
     * Muestra el formulario para editar un empleado existente
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Empleado empleado = empleadoService.buscar(id);
        
        if(empleado == null) {
            return "redirect:/empleado/listar";
        }

        model.addAttribute("empleado", empleado);
        model.addAttribute("titulo", "Editar Empleado");
        model.addAttribute("modo", "editar");
        return "empleado/form";
    }

    /**
     * Elimina un empleado
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Long id, RedirectAttributes redirect) {
        empleadoService.eliminar(id);
        redirect.addFlashAttribute("success", "Empleado eliminado correctamente");
        return "redirect:/empleado/listar";
    }
}