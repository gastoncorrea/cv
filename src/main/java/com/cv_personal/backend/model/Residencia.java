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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "residencias")
@Getter @Setter
public class Residencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_residencia;
    @Size(min = 2, max = 45, message = "La localidad debe tener entre 2 y 45 caracteres")
    @Column(length = 45)
    private String localidad;
    @Size(min = 2, max = 45, message = "La provincia debe tener entre 2 y 45 caracteres")
    @Column(length = 45)
    private String provincia;
    @Size(min = 2, max = 45, message = "El pais debe tener entre 2 y 45 caracteres")
    @Column(length = 45)
    private String pais;
    @Size(min = 2, max = 45, message = "La nacionalidad debe tener entre 2 y 45 caracteres")
    @Column(length = 45)
    private String nacionalidad;
    
    @OneToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;
    
}
