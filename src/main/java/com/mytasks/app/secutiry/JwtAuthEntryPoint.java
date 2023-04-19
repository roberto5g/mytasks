package com.mytasks.app.secutiry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytasks.app.exceptions.ErrorObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ObjectMapper mapper = new ObjectMapper();
        ErrorObject errorObject = ErrorObject.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(authException.getMessage())
                .timestamp(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss", Locale.ENGLISH)
                        .format(LocalDateTime.now()))
                .build();

        if(request.getAttribute("expired") != null){
            errorObject.setMessage("JWT has expired");
        } else if (request.getAttribute("invalid_token") != null) {
            errorObject.setMessage(String.valueOf(request.getAttribute("invalid_token")));
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(errorObject));
    }

}
