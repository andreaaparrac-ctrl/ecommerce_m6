package com.ecommerce.springboot_agua.model;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    private String imagenUrl; // Guarda el nombre o ruta de la imagen

    // Atributo requerido para controlar el inventario en el Panel de Administracion
    private Integer stock;

    // Constructor vacio (Obligatorio para JPA)
    public Producto() {}

    // Constructor con campos actualizado
    public Producto(String nombre, String descripcion, Double precio, String imagenUrl, Integer stock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
        this.stock = stock;
    }
    
 // Constructor adicional para mantener compatibilidad con DataInitializer
    public Producto(String nombre, String descripcion, Double precio, String imagenUrl) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
        this.stock = 0; // Se inicializa en cero por defecto si no se especifica
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}

