package com.example.PokemonApi.excepciones;

import com.example.PokemonApi.dtos.ErrorDTO;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarValidaciones(
            MethodArgumentNotValidException ex){
        List<String> detalles = new ArrayList<>();

        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            detalles.add(error.getField() + ":" + error.getDefaultMessage());
        }

        ErrorDTO errorDTO = new ErrorDTO(
            "Error de validacion en los datos enviados",
                detalles,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    //Manejo de Excepciones personalizadas - 404 Nor Found
    //Se ejecuta cuando lanzamos un ResourceNotFoundException manualmente en nuestro codigo
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> manejarRecursoNoEncontrado(ResourceNotFoundException ex){
        List<String> detalles = new ArrayList<>();
        detalles.add(ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                "Recurso no encontrado",
                detalles,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    //Manejo global para cualquier otra excepcion no capturada (Fallback) - 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> manejarExcepcionGlobal(Exception ex){
        List<String> detalles = new ArrayList<>();
        detalles.add(ex.getLocalizedMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                "Ocurrio un error interno en el servidor",
                detalles,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
