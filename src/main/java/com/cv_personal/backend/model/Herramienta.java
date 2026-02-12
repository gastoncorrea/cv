/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "herramientas")
@Getter @Setter        
public class Herramienta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_herramienta;
    @Column(length = 45, nullable = false)
    private String nombre;
    @Column(length = 45)
    private String version;
    @Column(length = 255)
    private String descripcion;
    @Column(length = 255)
    private String url;
    @Column(name = "logo")
    private String logo;
    
    @ManyToMany(mappedBy = "herramientas")
    private Set<Proyecto> proyectos = new HashSet<>();
    
    @ManyToMany(mappedBy = "herramientas")
    private Set<Educacion> estudios = new HashSet<>();
}
