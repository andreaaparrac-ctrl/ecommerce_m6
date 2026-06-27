package com.ecommerce.springboot_agua.controller;

import com.ecommerce.springboot_agua.model.Producto;
import com.ecommerce.springboot_agua.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/products")
public class AdminProductoController {

    private final ProductoService productoService;

    public AdminProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Ruta: GET /admin/products (Muestra el panel)
    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("nuevoProducto", new Producto());
        return "admin/productos";
    }

    // Ruta: POST /admin/products/agregar (Procesa el nuevo producto)
    @PostMapping("/agregar")
    public String agregarProducto(@ModelAttribute("nuevoProducto") Producto producto) {
        productoService.guardar(producto);
        return "redirect:/admin/products?success=agregado";
    }

    // Ruta: GET /admin/products/eliminar/{id} (Elimina el producto)
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long id) {
        productoService.eliminar(id);
        return "redirect:/admin/products?success=eliminado";
    }

    // Ruta: POST /admin/products/actualizar-stock (Actualiza inventario)
    @PostMapping("/actualizar-stock")
    public String actualizarStock(@RequestParam("id") Long id, @RequestParam("stock") Integer nuevoStock) {
        Producto producto = productoService.buscarPorId(id);
        if (producto != null) {
            producto.setStock(nuevoStock);
            productoService.guardar(producto);
        }
        return "redirect:/admin/products?success=stock_actualizado";
    }
}

