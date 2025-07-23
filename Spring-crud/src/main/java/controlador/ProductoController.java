package controlador;

import entidad.Producto;
import servicio.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador para gestionar las operaciones CRUD de productos
 */
@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    /**
     * Método helper para agregar atributos comunes a la vista listar
     */
    private void agregarAtributosComunes(Model model) {
        model.addAttribute("categorias", productoService.listarCategorias());
        model.addAttribute("productosStockBajo", productoService.listarProductosConStockBajo());
    }

    /**
     * Muestra la lista de todos los productos
     */
    @GetMapping("/listar")
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("titulo", "Lista de Productos");
        model.addAttribute("soloStockBajo", false);
        agregarAtributosComunes(model);
        return "producto/listar";
    }

    /**
     * Muestra formulario para crear nuevo producto
     */
    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("titulo", "Registrar Nuevo Producto");
        model.addAttribute("modo", "crear");
        model.addAttribute("categorias", productoService.listarCategorias());
        return "producto/formulario";
    }

    /**
     * Procesa el guardado de productos (creación y actualización)
     */
    @PostMapping("/guardar")
    public String guardarProducto(@Valid @ModelAttribute Producto producto, 
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirect,
                                 @RequestParam String modo) {
        
        // Validación condicional para código (solo si es nuevo o cambió)
        if(modo.equals("crear")) {
            if(productoService.existePorCodigo(producto.getCodigo())) {
                result.rejectValue("codigo", "error.codigo", "El código ya está registrado");
            }
        } else {
            Producto existente = productoService.buscarPorCodigo(producto.getCodigo());
            if(existente != null && !existente.getId().equals(producto.getId())) {
                result.rejectValue("codigo", "error.codigo", "El código ya está registrado");
            }
        }

        if(result.hasErrors()) {
            model.addAttribute("titulo", modo.equals("editar") ? "Editar Producto" : "Nuevo Producto");
            model.addAttribute("modo", modo);
            model.addAttribute("categorias", productoService.listarCategorias());
            return "producto/formulario";
        }

        if(modo.equals("crear")) {
            productoService.agregar(producto);
            redirect.addFlashAttribute("success", "Producto registrado correctamente");
        } else {
            productoService.actualizar(producto);
            redirect.addFlashAttribute("success", "Producto actualizado correctamente");
        }
        
        return "redirect:/producto/listar";
    }

    /**
     * Muestra formulario para editar producto existente
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoService.buscar(id);
        
        if(producto == null) {
            return "redirect:/producto/listar";
        }

        model.addAttribute("producto", producto);
        model.addAttribute("titulo", "Editar Producto");
        model.addAttribute("modo", "editar");
        model.addAttribute("categorias", productoService.listarCategorias());
        return "producto/formulario";
    }

    /**
     * Elimina un producto por su ID
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id, RedirectAttributes redirect) {
        productoService.eliminar(id);
        redirect.addFlashAttribute("success", "Producto eliminado correctamente");
        return "redirect:/producto/listar";
    }

    /**
     * Busca productos por criterio
     */
    @GetMapping("/buscar")
    public String buscarProductos(@RequestParam String criterio, Model model) {
        model.addAttribute("productos", productoService.buscarPorNombreODescripcion(criterio));
        model.addAttribute("titulo", "Resultados de búsqueda: " + criterio);
        model.addAttribute("criterio", criterio);
        model.addAttribute("soloStockBajo", false);
        agregarAtributosComunes(model);
        return "producto/listar";
    }

    /**
     * Filtra productos por categoría
     */
    @GetMapping("/categoria/{categoria}")
    public String filtrarPorCategoria(@PathVariable String categoria, Model model) {
        model.addAttribute("productos", productoService.buscarPorCategoria(categoria));
        model.addAttribute("titulo", "Productos - Categoría: " + categoria);
        model.addAttribute("categoriaSeleccionada", categoria);
        model.addAttribute("soloStockBajo", false);
        agregarAtributosComunes(model);
        return "producto/listar";
    }

    /**
     * Muestra productos con stock bajo
     */
    @GetMapping("/stock-bajo")
    public String mostrarStockBajo(Model model) {
        model.addAttribute("productos", productoService.listarProductosConStockBajo());
        model.addAttribute("titulo", "Productos con Stock Bajo");
        model.addAttribute("soloStockBajo", true);
        agregarAtributosComunes(model);
        return "producto/listar";
    }

    /**
     * Actualiza solo el stock de un producto
     */
    @PostMapping("/actualizar-stock")
    public String actualizarStock(@RequestParam Long id, @RequestParam Integer nuevoStock, 
                                 RedirectAttributes redirect) {
        productoService.actualizarStock(id, nuevoStock);
        redirect.addFlashAttribute("success", "Stock actualizado correctamente");
        return "redirect:/producto/listar";
    }
}