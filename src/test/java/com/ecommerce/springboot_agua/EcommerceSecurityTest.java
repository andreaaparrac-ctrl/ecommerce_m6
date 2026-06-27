package com.ecommerce.springboot_agua;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ecommerce.springboot_agua.model.Usuario;
import com.ecommerce.springboot_agua.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class EcommerceSecurityTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        // Inicializa el entorno de simulacion web con el filtro de Spring Security activado
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("1. Verificar que el panel de administración esté protegido contra intrusos")
    public void testRutaAdminProtegida() throws Exception {
        // Al intentar entrar a /admin/products sin loguearse, el sistema debe arrojar una redireccion (Status 3xx) al Login
        mockMvc.perform(get("/admin/products"))
               .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("2. Verificar el funcionamiento del registro y la encriptación BCrypt")
    public void testRegistroYEncriptacion() throws Exception {
        String correoPrueba = "test.unitario" + System.currentTimeMillis() + "@correo.com";
        
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre("Carlos");
        nuevoUsuario.setApellido("Test");
        nuevoUsuario.setEmail(correoPrueba);
        nuevoUsuario.setContrasenia("claveSegura123");

        // Guardamos el usuario a traves del servicio operativo
        Usuario guardado = usuarioService.registrarUsuario(nuevoUsuario);

        // Verificamos que la contraseña guardada empiece por la firma de BCrypt ($2a$) y no sea el texto plano
        org.junit.jupiter.api.Assertions.assertNotNull(guardado.getId());
        org.junit.jupiter.api.Assertions.assertTrue(guardado.getContrasenia().startsWith("$2a$"));
        org.junit.jupiter.api.Assertions.assertTrue(passwordEncoder.matches("claveSegura123", guardado.getContrasenia()));
    }
}
