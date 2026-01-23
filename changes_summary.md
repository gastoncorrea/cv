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
- `src/main/java/com/cv_personal/backend/security/SecurityConfig.java`

### Created and integrated `CustomAuthorizationFilter` for JWT handling

**Description:**
Created `CustomAuthorizationFilter.java` to intercept incoming requests, validate JWT tokens from the Authorization header, extract user roles, and set the authentication in the SecurityContextHolder. This filter was then integrated into `SecurityConfig.java`'s `SecurityFilterChain` to ensure proper authorization for protected endpoints. This should resolve 403 Forbidden errors for authenticated requests.

**Files Modified:**
- `src/main/java/com/cv_personal/backend/filter/CustomAuthorizationFilter.java` (new file)
- `src/main/java/com/cv_personal/backend/security/SecurityConfig.java`

### Added missing import for `CustomAuthorizationFilter` in `SecurityConfig.java`

**Description:**
Added the `import com.cv_personal.backend.filter.CustomAuthorizationFilter;` statement to `SecurityConfig.java` to resolve a potential compilation error.

**Files Modified:**
- `src/main/java/com/cv_personal/backend/security/SecurityConfig.java`

### Refactored `CustomAuthorizationFilter` and `SecurityConfig` for Spring management

**Description:**
Modified `CustomAuthorizationFilter.java` by adding the `@Component` annotation to allow Spring to manage its lifecycle. Subsequently, `SecurityConfig.java` was updated to inject this component and use the injected instance in the `securityFilterChain`, addressing potential issues with direct instantiation and improving Spring's handling of the filter.

**Files Modified:**
- `src/main/java/com/cv_personal/backend/filter/CustomAuthorizationFilter.java`
- `src/main/java/com/cv_personal/backend/security/SecurityConfig.java`

### Added missing import for `UsernamePasswordAuthenticationFilter` in `SecurityConfig.java`

**Description:**
Added the `import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;` statement to `SecurityConfig.java` to resolve a "cannot find symbol" compilation error.

**Files Modified:**
- `src/main/java/com/cv_personal/backend/security/SecurityConfig.java`

### Added missing import for `UrlBasedCorsConfigurationSource` in `SecurityConfig.java`

**Description:**
Re-added the `import org.springframework.web.cors.UrlBasedCorsConfigurationSource;` statement to `SecurityConfig.java`, as it was likely accidentally removed during previous modifications, resolving a "cannot find symbol" compilation error.

**Files Modified:**
- `src/main/java/com/cv_personal/backend/security/SecurityConfig.java`

### Added missing import for `java.util.Arrays` in `SecurityConfig.java`

**Description:**
Re-added the `import java.util.Arrays;` statement to `SecurityConfig.java`, as it was likely accidentally removed during previous modifications, resolving a "cannot find symbol" compilation error.

**Files Modified:**
- `src/main/java/com/cv_personal/backend/security/SecurityConfig.java`

### Fixed `NullPointerException` in `PersonaMapper.java` (for `listContacto`)

**Description:**
Added a null check to the `listContactoMapper` method in `PersonaMapper.java`. This prevents a `NullPointerException` when `listContacto` is null by returning an empty `ArrayList` instead of attempting to iterate over a null list.

**Files Modified:**
- `src/main/java/com/cv_personal/backend/mapper/PersonaMapper.java`

### Fixed `NullPointerException` in `PersonaMapper.java` (for `listResidencia`)

**Description:**
Added a null check to the `listResidenciaMapper` method in `PersonaMapper.java`. This prevents a `NullPointerException` when `listResidencia` is null by returning an empty `ArrayList` instead of attempting to iterate over a null list.

**Files Modified:**
- `src/main/java/com/cv_personal/backend/mapper/PersonaMapper.java`
