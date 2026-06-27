package com.ecommerce.springboot_agua.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomSuccessHandler customSuccessHandler;

    public SecurityConfig(CustomSuccessHandler customSuccessHandler) {
        this.customSuccessHandler = customSuccessHandler;
    }

    // Definicion del encriptador de contraseñas oficial para todo el sistema
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/assets/**", "/images/**");
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Reglas de Autorizacion de Rutas
            .authorizeHttpRequests(auth -> auth
                // AGREGADO: "/" permite que la landing page sea de acceso publico e inmediato
                .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/assets/**", "/images/**").permitAll()
                // Restringe las rutas de administracion exclusivamente al rol ADMIN
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Cualquier otra ruta (Catalogo seguro, carrito, perfil) requiere estar autenticado (CLIENT o ADMIN)
                .anyRequest().authenticated()
            )
            // Configuracion del Login del Formulario
            .formLogin(form -> form
                .loginPage("/login") // Define la ruta de nuestra vista personalizada de inicio de sesion
                .successHandler(customSuccessHandler) // Integra el manejador de redirección por rol
                .usernameParameter("email") // Indica que usaremos el correo como credencial de usuario
                .passwordParameter("contrasenia")
                .permitAll()
            )
            // Configuracion del Cierre de Sesion
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout") // Redirige al login con un parametro de confirmacion
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            );

        return http.build();
    }
}

