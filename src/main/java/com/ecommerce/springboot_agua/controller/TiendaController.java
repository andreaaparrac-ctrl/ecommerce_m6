package com.ecommerce.springboot_agua.controller;

import com.ecommerce.springboot_agua.model.Producto;
import com.ecommerce.springboot_agua.repository.ProductoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TiendaController {

    private final ProductoRepository productoRepository;

    public TiendaController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping("/")
    public String mostrarIndex(Model model) {
        model.addAttribute("listaProductos", productoRepository.findAll());
        return "index";
    }

    @GetMapping("/catalogo")
    public String mostrarCatalogo(Model model) {
        model.addAttribute("listaProductos", productoRepository.findAll());
        return "catalogo";
    }

    // Procesa la adición al carrito usando la sesión HTTP del servidor
    @PostMapping("/carrito/agregar")
    public String agregarAlCarrito(@RequestParam("id") Long id, 
                                   @RequestParam("cantidad") Integer cantidadSolicitada, 
                                   HttpSession session) {
        
        // Buscar el producto en la base de datos
        Producto producto = productoRepository.findById(id).orElse(null);
        
        if (producto != null) {
            // Normalizar el stock (si es nulo por registros viejos, se asume 0)
            Integer stockActual = (producto.getStock() != null) ? producto.getStock() : 0;
            
            // Validación de existencias del MVP
            if (cantidadSolicitada > stockActual) {
                return "redirect:/catalogo?error=stock_insuficiente&disponible=" + stockActual;
            }
            
            // Logica del carrito en sesion (Almacena ID del producto y la cantidad pedida)
            @SuppressWarnings("unchecked")
            Map<Long, Integer> carrito = (Map<Long, Integer>) session.getAttribute("carrito");
            if (carrito == null) {
                carrito = new HashMap<>();
            }
            
            // Si el producto ya estaba en el carrito, sumamos las cantidades y volvemos a validar
            Integer cantidadExistente = carrito.getOrDefault(id, 0);
            if ((cantidadExistente + cantidadSolicitada) > stockActual) {
                return "redirect:/catalogo?error=stock_insuficiente&disponible=" + stockActual;
            }
            
            carrito.put(id, cantidadExistente + cantidadSolicitada);
            session.setAttribute("carrito", carrito);
        }
        
        return "redirect:/catalogo?success=agregado";
    }
}
