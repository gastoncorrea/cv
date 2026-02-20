# Resumen de Cambios

Durante esta sesión, se han implementado y corregido los siguientes aspectos en el proyecto:

## Configuración de CORS
- Se implementó una configuración global de CORS en `src/main/java/com/cv_personal/backend/config/MvcConfig.java` para permitir peticiones desde `http://localhost:4200`, lo cual resuelve los problemas de bloqueo por política de mismo origen.
- Se corrigió un error de compilación añadiendo la importación `import org.springframework.context.annotation.Configuration;` en `MvcConfig.java`.

## Consistencia en la Ruta de Imágenes de Logo
- Se modificó `src/main/java/com/cv_personal/backend/service/HerramientaService.java` para almacenar las rutas de los logos en el formato `/uploads/nombre_archivo`, haciéndolo consistente con `ProyectoService`.
- Se modificó `src/main/java/com/cv_personal/backend/service/EducacionService.java` para almacenar las rutas de los logos en el formato `/uploads/nombre_archivo`, haciéndolo consistente con `ProyectoService`.

## Desasociación de Herramientas de Proyectos y Educaciones
- Se añadió el método `removeHerramientaFromEducacion(Long educacionId, Long herramientaId)` a la interfaz `src/main/java/com/cv_personal/backend/service/IEducacionService.java` y su implementación en `src/main/java/com/cv_personal/backend/service/EducacionService.java`.
- Se añadió el método `removeHerramientaFromProyecto(Long proyectoId, Long herramientaId)` a la interfaz `src/main/java/com/cv_personal/backend/service/IProyectoService.java` y su implementación en `src/main/java/com/cv_personal/backend/service/ProyectoService.java`.
- Se añadió el endpoint `DELETE /educacion/{educacionId}/herramientas/{herramientaId}` al controlador `src/main/java/com/cv_personal/backend/controller/EducacionController.java` para la desasociación.
- Se añadió el endpoint `DELETE /proyecto/{proyectoId}/herramientas/{herramientaId}` al controlador `src/main/java/com/cv_personal/backend/controller/ProyectoController.java` para la desasociación.

## Correcciones de Errores de Compilación
- Se corrigió el orden de los parámetros para el método `updateLogoImage` en `src/main/java/com/cv_personal/backend/service/IProyectoService.java` de `(MultipartFile file, Long id)` a `(Long id, MultipartFile file)` para resolver errores de compilación.
