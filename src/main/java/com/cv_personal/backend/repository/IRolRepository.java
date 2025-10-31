/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cv_personal.backend.repository;

import com.cv_personal.backend.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;



public interface IRolRepository extends JpaRepository<Rol,Long>{
    Rol findByName(String rol);
}
