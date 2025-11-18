package com.kauan.projex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class InitData {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desativa proteção CSRF (necessário se faz login via POST manual)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Libera todas as rotas
            )
            .formLogin(form -> form.disable()) // Desativa login automático do Spring
            .httpBasic(httpBasic -> httpBasic.disable()) // Desativa autenticação básica
            .logout(logout -> logout.disable()); // Desativa logout padrão

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
