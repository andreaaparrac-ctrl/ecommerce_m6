package com.ecommerce.springboot_agua.repository;

import com.ecommerce.springboot_agua.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Busca un usuario por su correo electronico
    Optional<Usuario> findByEmail(String email);
    
    // Verifica si ya existe un usuario con ese correo
    boolean existsByEmail(String email);
}
