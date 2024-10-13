package com.example.CRUD.security.securityToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDextailsService;

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/usuarios").hasAnyRole("sys_admin", "gerente", "func")
                        .requestMatchers(HttpMethod.POST, "/produtos").hasAnyRole("sys_admin", "gerente", "func")
                        .requestMatchers(HttpMethod.POST, "/mao-de-obra").hasAnyRole("sys_admin", "gerente", "func", "user")
                        .requestMatchers(HttpMethod.POST, "/funcionario").hasAnyRole("sys_admin", "gerente")
                        .requestMatchers(HttpMethod.POST, "/enderecos").permitAll()
                        .requestMatchers(HttpMethod.POST, "/enderecos/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/produtos/listar-produtos").hasAnyRole("sys_admin", "gerente", "func", "user")

                        .requestMatchers(HttpMethod.PUT, "/usuarios/").hasAnyRole("sys_admin", "gerente", "func")
                        .requestMatchers(HttpMethod.PUT, "/mao-de-obra/").hasAnyRole("sys_admin", "gerente", "func")
                        .requestMatchers(HttpMethod.PUT, "/enderecos/inativar-endereco/").hasAnyRole("sys_admin", "gerente", "func")
                        .requestMatchers(HttpMethod.PUT, "/usuarios/inativar-cliente/").hasAnyRole("sys_admin", "gerente", "func")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
