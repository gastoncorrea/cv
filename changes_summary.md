# Project Change Summary

This file will track all modifications and additions made to the project by the AI agent.

## Log:
### Added `springdoc-openapi-starter-webmvc-ui` dependency to `pom.xml`

**Description:**
Integrated `springdoc-openapi-starter-webmvc-ui` (version 2.5.0) to enable OpenAPI (Swagger) documentation generation for the Spring Boot application. This allows for better client-server communication by providing a clear API specification.

**Files Modified:**
- `pom.xml`

### Configured `SecurityConfig` to allow Swagger UI access

**Description:**
Modified the `SecurityConfig.java` file to permit access to the `/v3/api-docs/**` and `/swagger-ui/**` endpoints, allowing unauthenticated access to the OpenAPI documentation.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/security/SecurityConfig.java`

### Created and integrated `CustomAuthorizationFilter` for JWT handling

**Description:**
Created `CustomAuthorizationFilter.java` to intercept incoming requests, validate JWT tokens from the Authorization header, extract user roles, and set the authentication in the SecurityContextHolder. This filter was then integrated into `SecurityConfig.java`'s `SecurityFilterChain` to ensure proper authorization for protected endpoints. This should resolve 403 Forbidden errors for authenticated requests.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/filter/CustomAuthorizationFilter.java` (new file)
- `src/main/java/com.cv_personal.backend/security/SecurityConfig.java`

### Added missing import for `CustomAuthorizationFilter` in `SecurityConfig.java`

**Description:**
Added the `import com.cv_personal.backend.filter.CustomAuthorizationFilter;` statement to `SecurityConfig.java` to resolve a potential compilation error.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/security/SecurityConfig.java`

### Refactored `CustomAuthorizationFilter` and `SecurityConfig` for Spring management

**Description:**
Modified `CustomAuthorizationFilter.java` by adding the `@Component` annotation to allow Spring to manage its lifecycle. Subsequently, `SecurityConfig.java` was updated to inject this component and use the injected instance in the `securityFilterChain`, addressing potential issues with direct instantiation and improving Spring's handling of the filter.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/filter/CustomAuthorizationFilter.java`
- `src/main/java/com.cv_personal.backend/security/SecurityConfig.java`

### Added missing import for `UsernamePasswordAuthenticationFilter` in `SecurityConfig.java`

**Description:**
Added the `import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;` statement to `SecurityConfig.java` to resolve a "cannot find symbol" compilation error.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/security/SecurityConfig.java`

### Added missing import for `UrlBasedCorsConfigurationSource` in `SecurityConfig.java`

**Description:**
Re-added the `import org.springframework.web.cors.UrlBasedCorsConfigurationSource;` statement to `SecurityConfig.java`, as it was likely accidentally removed during previous modifications, resolving a "cannot find symbol" compilation error.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/security/SecurityConfig.java`

### Added missing import for `java.util.Arrays` in `SecurityConfig.java`

**Description:**
Re-added the `import java.util.Arrays;` statement to `SecurityConfig.java`, as it was likely accidentally removed during previous modifications, resolving a "cannot find symbol" compilation error.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/security/SecurityConfig.java`

### Fixed `NullPointerException` in `PersonaMapper.java` (for `listContacto`)

**Description:**
Added a null check to the `listContactoMapper` method in `PersonaMapper.java`. This prevents a `NullPointerException` when `listContacto` is null by returning an empty `ArrayList` instead of attempting to iterate over a null list.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/mapper/PersonaMapper.java`

### Fixed `NullPointerException` in `PersonaMapper.java` (for `listResidencia`)

**Description:**
Added a null check to the `listResidenciaMapper` method in `PersonaMapper.java`. This prevents a `NullPointerException` when `listResidencia` is null by returning an empty `ArrayList` instead of attempting to iterate over a null list.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/mapper/PersonaMapper.java`

### Fixed partial update issue in `UsuarioService.java`'s `updateUsuario` method

**Description:**
Implemented null and empty string checks for `email`, `nombre`, and `rol` fields from the `UsuarioDto` within the `updateUsuario` method. This ensures that if these fields are not provided in a PUT request, their existing values in the `Usuario` entity are retained instead of being overwritten with `null`.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/service/UsuarioService.java`

### Fixed partial update issue in `ResidenciaController.java`'s `updateResidencia` method

**Description:**
Implemented null and empty string checks for `localidad`, `provincia`, `pais`, and `nacionalidad` fields from the incoming `Residencia` object within the `updateResidencia` controller method. This prevents `null` values from overwriting existing data when not provided in a PUT request.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/controller/ResidenciaController.java`

### Fixed partial update issue in `ProyectoController.java`'s `updateProyecto` method

**Description:**
Implemented null and empty string checks for `nombre`, `descripcion`, `url`, `inicio`, and `fin` fields from the incoming `Proyecto` object within the `updateProyecto` controller method. This prevents `null` values from overwriting existing data when not provided in a PUT request.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/controller/ProyectoController.java`

### Fixed partial update issue in `PersonaService.java`'s `updatePersona` method

**Description:**
Implemented null and empty string checks for `nombre`, `apellido`, `descripcion_mi`, `fecha_nacimiento`, and `num_celular` fields from the `PersonaDto` within the `updatePersona` method. This ensures that if these fields are not provided in a PUT request, their existing values in the `Persona` entity are retained instead of being overwritten with `null`.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/service/PersonaService.java`

### Fixed partial update issue in `HerramientaController.java`'s `updateHerramienta` method

**Description:**
Implemented null and empty string checks for `nombre` and `version` fields from the incoming `Herramienta` object within the `updateHerramienta` controller method. This prevents `null` values from overwriting existing data when not provided in a PUT request.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/controller/HerramientaController.java`

### Fixed partial update issue in `EducacionController.java`'s `updateEducacion` method

**Description:**
Implemented null and empty string checks for `nombre_institucion`, `logo_imagen`, `fecha_inicio`, `fecha_fin`, `titulo`, and `url_titulo` fields from the incoming `Educacion` object within the `updateEducacion` controller method. This prevents `null` values from overwriting existing data when not provided in a PUT request.

**Files Modified:**
- `src/main/java/com/cv_personal.backend/controller/EducacionController.java`

### Fixed partial update issue in `ContactoController.java`'s `updateContacto` method and added logo upload endpoint

**Description:**
Removed the `logo_img` update logic from `updateContacto` as image uploads are now handled by a separate dedicated endpoint. A new `uploadContactLogo` endpoint (`POST /{id}/logo`) was added to `ContactoController.java` to manage `MultipartFile` uploads for contact logos.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/controller/ContactoController.java`

### Fixed `Persona` deletion issue by adding `@Transactional` to `PersonaService.java`

**Description:**
Added the `@Transactional` annotation to the `deletePersona` method in `PersonaService.java`. This ensures that the deletion operation, including cascading deletions to related entities (Residencia, Educacion, Proyecto, Contacto), is performed within a single database transaction, guaranteeing its atomicity and persistence.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/service/PersonaService.java`

### Added missing import for `@Transactional` in `PersonaService.java`

**Description:**
Added the `import org.springframework.transaction.annotation.Transactional;` statement to `PersonaService.java` to resolve a "cannot find symbol" compilation error for the `@Transactional` annotation.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/service/PersonaService.java`

### Changed `logo_img` from `byte[]` to `String` in `Contacto.java`

**Description:**
Modified the `Contacto` model by changing the `logo_img` field type from `byte[]` to `String` and removing the `@Lob` and `columnDefinition` annotations. This aligns with storing image URLs/paths locally instead of binary data directly in the database.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/model/Contacto.java`

### Added `updateLogoImage` method signature to `IContactoService.java`

**Description:**
Added the `public ContactoDto updateLogoImage(Long id, org.springframework.web.multipart.MultipartFile file);` method signature to the `IContactoService` interface. This defines the contract for handling logo image updates.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/service/IContactoService.java`

### Implemented `updateLogoImage` in `ContactoService.java`

**Description:**
Implemented the `updateLogoImage` method in `ContactoService.java`. This method handles saving a `MultipartFile` to the local file system, generates a unique filename, updates the `Contacto` entity's `logo_img` field with the generated file URL, and saves the updated entity. It also includes an `@PostConstruct` method to ensure the 'uploads' directory exists.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/service/ContactoService.java`

### Persona Deletion Issue: Check for Existence, Error Handling, and Logging

**Description:**
Multiple changes were implemented to address the issue where deleting a `Persona` would return a success message even if the record was not actually removed from the database.

1.  **`PersonaService.java` - Existence Check & Exception:**
    *   Modified the `deletePersona` method to first check if the `Persona` with the given ID exists using `personaRep.existsById(id)`.
    *   If the `Persona` does not exist, a `RuntimeException` with a specific "Persona no encontrada" message is thrown.
    *   Added SLF4J logging (`logger.info`, `logger.warn`) to trace the execution flow, including ID received, existence check result, and deletion completion.

2.  **`PersonaController.java` - Improved Error Handling:**
    *   Updated the `deletePersona` method to catch the `RuntimeException` thrown by `PersonaService` when a `Persona` is not found.
    *   Returns an `HttpStatus.NOT_FOUND` (404) response with the exception message in this scenario, providing more accurate feedback to the client.

3.  **`PersonaController.java` - Corrected `catch` Block Order:**
    *   Reordered the `catch` blocks in `deletePersona` to place the more specific `DataAccessException` handler before the general `RuntimeException` handler. This resolved a compilation error and ensures correct exception handling.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/service/PersonaService.java`
- `src/main/java/com.cv_personal.backend/controller/PersonaController.java`

### Enabled Cascade Deletion for Persona Relationships

**Description:**
To ensure that all related entities (Residencia, Educacion, Proyecto, Contacto) are automatically deleted when a `Persona` is removed, the `orphanRemoval = true` attribute was added to all `@OneToMany` relationships in the `Persona` entity. This resolves potential foreign key constraint issues during deletion.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/model/Persona.java`

### Configured Database Schema Recreation on Startup

**Description:**
Modified the `application.properties` file to set `spring.jpa.hibernate.ddl-auto=create`. This configuration ensures that the database schema is dropped and recreated from scratch based on the JPA entity definitions every time the application starts. This is a common strategy during development to reflect schema changes quickly and to resolve potential data integrity issues.

**Files Modified:**
- `src/main/resources/application.properties`

### Modified Persona Deletion Logic to Retain Associated User

**Description:**
The `deletePersona` method in `PersonaService.java` was refactored to allow the deletion of a `Persona` entity while preserving its associated `Usuario` entity. The previous attempt to cascade delete the `Usuario` or the subsequent `TransientObjectException` indicated an undesired behavior.
The updated logic now explicitly breaks the bidirectional `@OneToOne` association between `Persona` and `Usuario` by setting the `persona` field in `Usuario` to `null` and the `usuario` field in `Persona` to `null` within a transactional context, *before* the `Persona` entity is deleted. This ensures that the `Usuario` record remains in the database and is de-linked from the deleted `Persona`, fulfilling the new requirement. The `IUsuarioRepository` was also injected into `PersonaService` to support this change.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/service/PersonaService.java`

### Success message in ConctactoController fixed

**Description:**
Corrected the success message returned by the `deleteContacto` method in `ContactoController.java` to be "Contacto eliminado con exito" instead of "Educacion eliminada con exito".

**Files Modified:**
- `src/main/java/com.cv_personal.backend/controller/ContactoController.java`

### Updated ID generation strategy to GenerationType.IDENTITY

**Description:**
Modified the `@GeneratedValue` strategy for `@Id` fields in several model entities (Contacto, Educacion, Herramienta, Proyecto, Residencia, Rol) from `GenerationType.SEQUENCE` to `GenerationType.IDENTITY` for consistency and to leverage database auto-increment capabilities.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/model/Contacto.java`
- `src/main/java/com.cv_personal.backend/model/Educacion.java`
- `src/main/java/com/cv_personal.backend/model/Herramienta.java`
- `src/main/java/com/cv_personal.backend/model/Proyecto.java`
- `src/main/java/com/cv_personal.backend/model/Residencia.java`
- `src/main/java/com/cv_personal.backend/model/Rol.java`

### Removed unused imports and variables

**Description:**
Removed unused imports and variables across several Java files to improve code clarity and maintainability. This cleanup was based on the analysis provided by the codebase_investigator agent.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/controller/EducacionController.java`
- `src/main/java/com.cv_personal.backend/controller/PersonaController.java`
- `src/main/java/com.cv_personal.backend/model/Persona.java`
- `src/main/java/com.cv_personal.backend/model/Usuario.java`
- `src/main/java/com/cv_personal.backend/service/PersonaService.java`

### Implement ManyToMany relationship for Educacion/Proyecto and Herramienta

**Description:**
Introduced functionality to manage Herramienta associations with Educacion and Proyecto entities.
This includes:
- Created HerramientaRequestDto, EducacionHerramientasDto, and ProyectoHerramientasDto for flexible request handling (linking existing tools or creating new ones).
- Added `addHerramientasToEducacion` and `addHerramientasToProyecto` methods in respective services to handle relationship establishment and Herramienta creation.
- Exposed these functionalities via new POST endpoints `/educacion/herramientas` and `/proyecto/herramientas` in their respective controllers.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/controller/EducacionController.java`
- `src/main/java/com/cv_personal.backend/controller/ProyectoController.java`
- `src/main/java/com/cv_personal.backend/service/EducacionService.java`
- `src/main/java/com/cv_personal.backend/service/IEducacionService.java`
- `src/main/java/com.cv_personal.backend/service/IProyectoService.java`
- `src/main/java/com.cv_personal.backend/service/ProyectoService.java`
- `src/main/java/com/cv_personal.backend/dto/EducacionHerramientasDto.java` (new file)
- `src/main/java/com/cv_personal.backend/dto/HerramientaRequestDto.java` (new file)
- `src/main/java/com/cv_personal.backend/dto/ProyectoHerramientasDto.java` (new file)

### Feature: Retrieve related entities by Persona ID

**Description:**
Implemented functionality to retrieve lists of associated entities (Educacion, Residencia, Proyecto, Contacto) given a `Persona` ID. This involved:
- Adding `get[Entity]ByPersonaId(Long personaId)` method signatures to `IEducacionService`, `IResidenciaService`, `IProyectoService`, and `IContactoService`.
- Implementing these methods in `EducacionService`, `ResidenciaService`, `ProyectoService`, and `ContactoService`, utilizing `IPersonaRepository` to fetch the `Persona` and access its `@OneToMany` collections directly within a `@Transactional(readOnly = true)` context.
- Adding `GET /entidad/persona/{personaId}` endpoints to `EducacionController`, `ResidenciaController`, `ProyectoController`, and `ContactoController` to expose this functionality, including robust error handling.
- Corrected a missing `List` import in `IEducacionRepository.java`.
- Fixed a duplicated class definition in `ResidenciaService.java`.

**Files Modified:**
- `src/main/java/com.cv_personal.backend/service/IEducacionService.java`
- `src/main/java/com/cv_personal.backend/service/EducacionService.java`
- `src/main/java/com.cv_personal.backend/repository/IEducacionRepository.java`
- `src/main/java/com/cv_personal.backend/controller/EducacionController.java`
- `src/main/java/com/cv_personal.backend/service/IResidenciaService.java`
- `src/main/java/com.cv_personal.backend/service/ResidenciaService.java`
- `src/main/java/com/cv_personal.backend/controller/ResidenciaController.java`
- `src/main/java/com.cv_personal.backend/service/IProyectoService.java`
- `src/main/java/com.cv_personal.backend/service/ProyectoService.java`
- `src/main/java/com.cv_personal.backend/controller/ProyectoController.java`
- `src/main/java/com/cv_personal.backend/service/IContactoService.java`
- `src/main/java/com.cv_personal.backend/service/ContactoService.java`
- `src/main/java/com/cv_personal.backend/controller/ContactoController.java`
