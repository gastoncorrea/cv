package com.cv_personal.backend.security;

import com.cv_personal.backend.filter.CustomAuthenticationFilter;
import com.cv_personal.backend.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder PasswordEncoder;
    private final CustomAuthorizationFilter customAuthorizationFilter;


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)throws Exception{
        AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
        auth.userDetailsService(userDetailsService)
        .passwordEncoder(PasswordEncoder);
        return auth.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager)throws Exception{
        CustomAuthenticationFilter customAuthFilter = new CustomAuthenticationFilter(authManager);
        customAuthFilter.setFilterProcessesUrl("/login");
        http
                .cors(withDefaults()) // Habilitar configuración global de CORS
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Rutas Públicas de Lectura y Autenticación
                        .requestMatchers("/login/**", "/usuario/save", "/v3/api-docs/**", "/swagger-ui/**", "/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/persona/**",
                                "/educacion/**",
                                "/proyecto/**",
                                "/herramienta/**",
                                "/residencia/**",
                                "/contacto/**").permitAll()
                        // Todas las demás rutas requieren rol de ADMIN
                        .anyRequest().hasAuthority("ROLE_ADMIN")
                )
                .authenticationManager(authManager)
                .addFilter(customAuthFilter)
                .addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
