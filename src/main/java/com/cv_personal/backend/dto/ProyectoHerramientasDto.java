package com.cv_personal.backend.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProyectoHerramientasDto {
    private Long proyectoId;
    private List<HerramientaRequestDto> herramientas;
}
