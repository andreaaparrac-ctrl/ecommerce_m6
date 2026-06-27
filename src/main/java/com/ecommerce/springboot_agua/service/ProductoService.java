package com.ecommerce.springboot_agua.service;

import com.ecommerce.springboot_agua.model.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> listarTodos();
    Producto guardar(Producto producto);
    Producto buscarPorId(Long id);
    void eliminar(Long id);
}
