package com.cv_personal.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HerramientaRequestDto {
    private Long id; // For existing tools
    private String nombre; // For new tools
    private String version; // For new tools
}
