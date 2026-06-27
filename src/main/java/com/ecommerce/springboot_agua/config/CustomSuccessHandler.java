package com.ecommerce.springboot_agua.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        // Extrae los roles asignados al usuario autenticado
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // Redireccion condicionada segun el rol
        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/products");
        } else {
            response.sendRedirect("/catalogo");
        }
    }
}
