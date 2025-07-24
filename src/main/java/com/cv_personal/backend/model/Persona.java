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
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@Entity
@Table(name = "personas")
public class Persona {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id_persona;
    @Lob // Indica que el atributo puede almacenar datos grandes (como imágenes)
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imagen_perfil;;
    private String descripcion_mi;
    private String url_linkedin;
    private String nombre;
    private String apellido;
    private String num_celular;
    
    @OneToOne
    @JoinColumn(name = "usuario_id") // Clave foránea en la tabla usuario
    private Usuario usuario;
    
    
}
