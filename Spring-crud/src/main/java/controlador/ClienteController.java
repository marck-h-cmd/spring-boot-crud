package controlador;

import entidad.Cliente;
import servicio.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/listar")
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarActivos());
        model.addAttribute("titulo", "Lista de Clientes");
        return "cliente/listar";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("titulo", "Registrar Nuevo Cliente");
        model.addAttribute("modo", "crear");
        return "cliente/formulario";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@Valid @ModelAttribute Cliente cliente, 
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirect,
                               @RequestParam String modo) {
        
        // Validar RUC/DNI único solo si es nuevo o cambió
        if(modo.equals("crear")) {
            if(clienteService.existePorRucDni(cliente.getRucDni())) {
                result.rejectValue("rucDni", "error.rucDni", "El RUC/DNI ya está registrado");
            }
        } else {
            Cliente existente = clienteService.buscarPorRucDni(cliente.getRucDni());
            if(existente != null && !existente.getId().equals(cliente.getId())) {
                result.rejectValue("rucDni", "error.rucDni", "El RUC/DNI ya está registrado");
            }
        }

        if(result.hasErrors()) {
            model.addAttribute("titulo", modo.equals("editar") ? "Editar Cliente" : "Nuevo Cliente");
            model.addAttribute("modo", modo);
            return "cliente/formulario";
        }

        clienteService.guardar(cliente);
        redirect.addFlashAttribute("success", modo.equals("editar") ? "Cliente actualizado correctamente" : "Cliente registrado correctamente");
        return "redirect:/cliente/listar";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id);
        
        if(cliente == null) {
            return "redirect:/cliente/listar";
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");
        model.addAttribute("modo", "editar");
        return "cliente/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id, RedirectAttributes redirect) {
        clienteService.eliminar(id);
        redirect.addFlashAttribute("success", "Cliente eliminado correctamente");
        return "redirect:/cliente/listar";
    }

    @GetMapping("/buscar")
    public String buscarClientes(@RequestParam String criterio, Model model) {
        if(criterio.matches("\\d+")) { // Si es numérico (DNI/RUC)
            model.addAttribute("clientes", clienteService.buscarPorRucDniParcial(criterio));
        } else { // Si es texto (nombre/apellido)
            model.addAttribute("clientes", clienteService.buscarPorNombreOApellido(criterio));
        }
        return "cliente/listar";
    }
}