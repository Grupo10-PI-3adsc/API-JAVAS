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
    private SecurityFilter securityFilter;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;  // Injetando o AccessDeniedHandler

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Desabilitar CSRF para facilitar a API stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Política de sessão stateless para APIs REST
                .authorizeHttpRequests(authorize -> authorize
                        // Permissões POST
                        .requestMatchers(HttpMethod.POST, "/usuarios").hasAnyRole("SYS_ADM", "GERENTE", "FUNC")
                        .requestMatchers(HttpMethod.POST, "/produtos").hasAnyRole("SYS_ADM", "GERENTE", "FUNC")
                        .requestMatchers(HttpMethod.POST, "/mao-de-obra").hasAnyRole("SYS_ADM", "GERENTE", "FUNC", "USER")
                        .requestMatchers(HttpMethod.POST, "/funcionario").hasAnyRole("SYS_ADM", "GERENTE")
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()

                        // Permissões GET
                        .requestMatchers(HttpMethod.GET, "/usuarios").hasAnyRole("SYS_ADM", "GERENTE", "FUNC")
                        .requestMatchers(HttpMethod.GET, "/produtos/listar-produtos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/produtos/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/produto/buscar-por-nome").permitAll()

                        // Permissões PUT
                        .requestMatchers(HttpMethod.PUT, "/produtos/{id}").hasAnyRole("SYS_ADM", "GERENTE")
                        .requestMatchers(HttpMethod.PUT, "/usuarios").hasAnyRole("SYS_ADM", "GERENTE", "FUNC")
                        .requestMatchers(HttpMethod.PUT, "/mao-de-obra").hasAnyRole("SYS_ADM", "GERENTE", "FUNC")
                        .requestMatchers(HttpMethod.PUT, "/enderecos/inativar-endereco").hasAnyRole("SYS_ADM", "GERENTE", "FUNC")
                        .requestMatchers(HttpMethod.PUT, "/usuarios/inativar-cliente").hasAnyRole("SYS_ADM", "GERENTE", "FUNC")

                        // Permissões DELETE
                        .requestMatchers(HttpMethod.DELETE, "/produtos/{id}").hasAnyRole("SYS_ADM", "GERENTE")

                        // Swagger
                        .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/**").permitAll()
                        // Qualquer outra requisição precisa ser autenticada
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions
                                .sameOrigin() // Permite que a página seja exibida em um frame do mesmo domínio (melhor prática)
                        )
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler)  // Define o manipulador de acesso negado personalizado
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);  // Filtro de autenticação JWT
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

//TODO: SYS_ADM
//{
//        "email": "ana.paula@example.com",
//        "password": "SenhaSegura456"
//}
//TODO: Gerente
