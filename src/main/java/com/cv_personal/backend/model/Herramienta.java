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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_herramienta;
    @Column(length = 45, nullable = false)
    private String nombre;
    @Column(length = 45)
    private String version;
    
    @ManyToMany
    @JoinTable(
        name = "herramienta_proyecto",
        joinColumns = @JoinColumn(name = "herramienta_id"),
        inverseJoinColumns = @JoinColumn(name = "proyecto_id")
    )
    private Set<Proyecto> proyectos = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "herramienta_educacion",
        joinColumns = @JoinColumn(name = "herramienta_id"),
        inverseJoinColumns = @JoinColumn(name = "educacion_id")
    )
    private Set<Educacion> estudios = new HashSet<>();
}
