/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estudios")
@Getter
@Setter
public class Educacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_educacion;
    @Size(min = 2, max = 45, message = "El nombre de la institucion debe tener entre 2 y 45 caracteres")
    @Column(length = 45, nullable = false)
    private String nombre_institucion;
    @Size(min = 2, max = 100, message = "El link de la imagen debe tener entre 2 y 100 caracteres")
    @Column(length = 100)
    private String logo_imagen;
    @Column(nullable = false)
    private LocalDate fecha_inicio;
    @Column(nullable = false)
    private LocalDate fecha_fin;
    @Size(min = 2, max = 45, message = "El titulo debe tener entre 2 y 45 caracteres")
    @Column(length = 45, nullable = false)
    private String titulo;
    @Size(min = 2, max = 200, message = "El link del titulo debe tener entre 2 y 200 caracteres")
    @Column(length = 200)
    private String url_titulo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", nullable = false)
    @JsonBackReference
    private Persona persona;
    
}
