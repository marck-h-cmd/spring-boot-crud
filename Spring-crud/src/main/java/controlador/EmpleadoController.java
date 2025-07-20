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

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/listar")
    public String listarEmpleados(Model model) {
        List<Empleado> empleados = empleadoService.listarTodos();
        model.addAttribute("empleados", empleados);
        model.addAttribute("titulo", "Lista de Empleados");
        return "empleado/listar"; 
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("titulo", "Registrar Nuevo Empleado");
        model.addAttribute("modo", "crear");
        return "empleado/form";
    }

    @PostMapping("/guardar")
    public String guardarEmpleado(@Valid @ModelAttribute Empleado empleado, 
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirect,
                                @RequestParam String modo) {
        
        if(empleadoService.existePorDni(empleado.getDni())) {
            result.rejectValue("dni", "error.dni", "El DNI ya está registrado");
        }
        

        if(empleadoService.existePorEmail(empleado.getEmail())) {
            result.rejectValue("email", "error.email", "El email ya está registrado");
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

    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Long id, RedirectAttributes redirect) {
        empleadoService.eliminar(id);
        redirect.addFlashAttribute("success", "Empleado eliminado correctamente");
        return "redirect:/empleado/listar";
    }

    /*
    @GetMapping("/buscar")
    public String buscarEmpleados(@RequestParam String criterio, Model model) {
        List<Empleado> empleados = empleadoService.buscarPorNombreOApellido(criterio);
        model.addAttribute("empleados", empleados);
        model.addAttribute("titulo", "Resultados de búsqueda: " + criterio);
        return "empleado/listar";
    }  */
}