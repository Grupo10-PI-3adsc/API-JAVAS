package com.example.CRUD.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class JaCadastradoException extends RuntimeException{
    public JaCadastradoException(String message){super(message);}
}
