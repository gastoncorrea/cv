/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cv_personal.backend.security;

import com.cv_personal.backend.filter.CustomAuthenticationFilter;
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

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig {
    
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder PasswordEncoder;
    
    
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
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login/**","/usuario/save").permitAll()
                .requestMatchers(HttpMethod.GET,"/**").hasAnyAuthority("ROL_USER","ROL_ADMIN")
                .requestMatchers("/**").hasAuthority("ROL_ADMIN")
                .anyRequest().authenticated()
                )
                .authenticationManager(authManager)
                .addFilter(customAuthFilter);
        
        return http.build();
    }
    
}
