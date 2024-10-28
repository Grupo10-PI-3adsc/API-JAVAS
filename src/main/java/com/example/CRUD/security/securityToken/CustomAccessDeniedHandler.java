package com.example.CRUD.security.securityToken;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Define o código de status para 403 Forbidden
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // Define o tipo de conteúdo como JSON
        response.setContentType("application/json");

        // Cria a resposta JSON com a mensagem de erro
        response.getWriter().write("{\"error\": \"Acesso negado. Você não tem permissão para acessar este recurso.\"}");
        response.getWriter().flush();
    }
}
