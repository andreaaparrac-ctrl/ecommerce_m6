package com.ecommerce.springboot_agua.config;

import com.ecommerce.springboot_agua.model.Producto;
import com.ecommerce.springboot_agua.model.Usuario;
import com.ecommerce.springboot_agua.repository.ProductoRepository;
import com.ecommerce.springboot_agua.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Inyectamos todos los repositorios necesarios en el constructor
    public DataInitializer(ProductoRepository productoRepository, 
                           UsuarioRepository usuarioRepository, 
                           BCryptPasswordEncoder passwordEncoder) {
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Insercion automatica del catalogo de 9 productos 
        if (productoRepository.count() == 0) {
            productoRepository.save(new Producto("Bidón 20L", "Bidon retornable de 20L recargado con agua purificada de alta calidad, sometida a procesos de osmosis inversa y filtrado micrométrico. Ideal para el hogar.", 3000.0, "bidon.jpg"));
            productoRepository.save(new Producto("Pack Botellas 500ml", "6 botellas desechables de 500ml con agua purificada de alta calidad, sometida a procesos de osmosis inversa y filtrado micrométrico. Ideal para el hogar.", 3000.0, "pack-botellas.jpg"));
            productoRepository.save(new Producto("Dispensador Basico", "Dispensador basico, entrega agua a temperatura ambiente. Compatible con bidones de 10, 12 y 20 Litros.", 6500.0, "Dispensador.jpg"));
            productoRepository.save(new Producto("Dispensador Electrico", "Dispensador recargable vía USB, entrega agua a temperatura ambiente. Compatible con bidones de 10L y 20L.", 5500.0, "dispensador-electrico.jpg"));
            productoRepository.save(new Producto("Pack Botellas 1.5L", "6 botellas desechables de 1.500ml de agua purificada de alta calidad, sometida a procesos de osmosis inversa y filtrado micrométrico. Ideal para el hogar.", 4500.0, "pack-1.jpg"));
            productoRepository.save(new Producto("Bidón 20L + Dispensador", "Bidon retornable de 20L con agua purificada de alta calidad, sometida a procesos de osmosis inversa y filtrado micrométrico + dispensador basico. Ideal para el hogar.", 9000.0, "bidon_dispensador.jpg"));
            productoRepository.save(new Producto("Bidón 5L", "Bidon de 5L desechable con agua purificada de alta calidad, sometida a procesos de osmosis inversa y filtrado micrométrico. Ideal para el hogar.", 1500.0, "bidon-5.jpg"));
            productoRepository.save(new Producto("2 Bidones 20L + Dispensador Basico", "2 Bidones 20L retornables con agua purificada de alta calidad, sometida a procesos de osmosis inversa y filtrado micrométrico + dispensador basico. Ideal para el hogar.", 12000.0, "bidosn2-dispensador.jpg"));
            productoRepository.save(new Producto("4 Bidones 20L + Dispensador Basico", "4 Bidones 20L con agua purificada de alta calidad, sometida a procesos de osmosis inversa y filtrado micrométrico + dispensador basico. Ideal para el hogar.", 18000.0, "bidon4-dispensador.jpg"));
            
            System.out.println("🌱 Base de datos: Los 9 productos de Fuente de Vida han sido insertados exitosamente.");
        }

        // Inyeccion del administrador por defecto
        if (usuarioRepository.findByEmail("admin@fuentedevida.cl").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Don");
            admin.setApellido("Admin");
            admin.setEmail("admin@fuentedevida.cl");
            // Encriptamos la contraseña "admin123" usando la viga del sistema
            admin.setContrasenia(passwordEncoder.encode("admin123"));
            admin.setRol("ADMIN"); // Rol de gestión que lee el CustomSuccessHandler
            
            usuarioRepository.save(admin);
            System.out.println("🔑 Base de datos: Cuenta de Administrador corporativo creada con éxito.");
        }
    }
}
