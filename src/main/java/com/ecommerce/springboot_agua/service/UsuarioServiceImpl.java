package com.ecommerce.springboot_agua.service;

import com.ecommerce.springboot_agua.model.Usuario;
import com.ecommerce.springboot_agua.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Inyeccion por constructor
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) throws Exception {
        // Validar si el email ya existe en la base de datos 
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new Exception("El correo electrónico ya se encuentra registrado.");
        }

        // Encriptar la contraseña usando BCrypt antes de guardar
        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));

        // Asignar el rol por defecto CLIENT 
        usuario.setRol("CLIENT");

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    // Metodo para el proceso de Login automatico
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + email));

        // "ROLE_" para manejar correctamente la autorizacion por roles
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol());

        return new User(
                usuario.getEmail(),
                usuario.getContrasenia(),
                Collections.singletonList(authority)
        );
    }
}
