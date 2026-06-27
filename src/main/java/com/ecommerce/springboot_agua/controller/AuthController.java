package com.ecommerce.springboot_agua.controller;

import com.ecommerce.springboot_agua.model.Usuario;
import com.ecommerce.springboot_agua.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Ruta para mostrar la vista de Login personalizada 
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // Buscara src/main/resources/templates/login.html
    }

    // Ruta para mostrar el formulario de Registro
    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register"; // Buscara src/main/resources/templates/register.html
    }

    // Ruta para procesar los datos de registro enviados desde el formulario HTML
    @PostMapping("/register")
    public String procesarRegistro(@ModelAttribute("usuario") Usuario usuario, Model model) {
        try {
            usuarioService.registrarUsuario(usuario);
            // Si todo sale bien, redirige al login con un mensaje de exito
            return "redirect:/login?success";
        } catch (Exception e) {
            // Si ocurre un error (como email duplicado), recarga el formulario mostrando la alerta
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
