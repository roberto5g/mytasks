package com.mytasks.app.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AccessForbiddenException extends RuntimeException implements AccessDeniedHandler {

    public AccessForbiddenException(){
    }

    public AccessForbiddenException(String text){
        super("You don't have permission to "+text+" this resource.");
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        ErrorObject errorObject = ErrorObject.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message("You don't have permission to access this resource.")
                .timestamp(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss", Locale.ENGLISH)
                        .format(LocalDateTime.now()))
                .build();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(mapper.writeValueAsString(errorObject));
    }
}
