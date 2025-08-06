/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "personas")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_persona;
    @Lob // Indica que el atributo puede almacenar datos grandes (como imágenes)
    @Size(max = 16_777_215)
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagen_perfil;
    @Size(min = 8, max = 500, message = "La descripcion debe tener entre 8 y 500 caracteres")
    @Column(length = 500, nullable = false)
    private String descripcion_mi;
    @Size(min = 8, max = 100, message = "La url debe tener entre 8 y 100 caracteres")
    @Column(length = 100)
    private String url_linkedin;
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(length = 50, nullable = false)
    private String nombre;
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @Column(length = 50, nullable = false)
    private String apellido;
    @Column(nullable = false)
    private LocalDate fecha_nacimiento;
    @Pattern(regexp = "^[+]?[(]?\\d{3}[)]?[-\\s.]?\\d{3}[-\\s.]?\\d{4,6}$",
            message = "Número de celular inválido")
    private String num_celular;

    @OneToOne
    @JoinColumn(name = "usuario_id") // Clave foránea en la tabla usuario
    private Usuario usuario;
    
    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    @JsonIgnore
    private Residencia residencia;
    
    
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    private List<Educacion> estudios;
    
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL )
    private List<Proyecto> proyectos;

}
