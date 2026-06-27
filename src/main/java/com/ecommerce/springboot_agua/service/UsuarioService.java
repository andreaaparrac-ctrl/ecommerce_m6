package com.ecommerce.springboot_agua.service;

import com.ecommerce.springboot_agua.model.Usuario;

public interface UsuarioService {
    // Guarda un nuevo usuario aplicando encriptacion y rol por defecto
    Usuario registrarUsuario(Usuario usuario) throws Exception;
    
    // Busca un usuario por correo para validaciones adicionales
    Usuario buscarPorEmail(String email);
}
