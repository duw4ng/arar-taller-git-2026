package py.edu.uc.lp3.arar_taller_git_2026.rest.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.EntidadNoEncontradaException;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.MinecraftException;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.ReglaDeJuegoException;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.ErrorResponse;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntidadNoEncontradaException.class)
    public ResponseEntity<ErrorResponse> manejarEntidadNoEncontrada(
            EntidadNoEncontradaException excepcion,
            HttpServletRequest solicitud) {
        return construir("ENTIDAD_NO_ENCONTRADA", excepcion, HttpStatus.NOT_FOUND, solicitud);
    }

    @ExceptionHandler(ReglaDeJuegoException.class)
    public ResponseEntity<ErrorResponse> manejarReglaDeJuego(
            ReglaDeJuegoException excepcion,
            HttpServletRequest solicitud) {
        return construir("REGLA_DE_JUEGO", excepcion, HttpStatus.BAD_REQUEST, solicitud);
    }

    @ExceptionHandler(MinecraftException.class)
    public ResponseEntity<ErrorResponse> manejarErrorDeDominio(
            MinecraftException excepcion,
            HttpServletRequest solicitud) {
        return construir("ERROR_DE_DOMINIO", excepcion, HttpStatus.BAD_REQUEST, solicitud);
    }

    private ResponseEntity<ErrorResponse> construir(
            String tipo,
            MinecraftException excepcion,
            HttpStatus estado,
            HttpServletRequest solicitud) {
        ErrorResponse cuerpo = new ErrorResponse(
                tipo,
                excepcion.getMessage(),
                estado.value(),
                solicitud.getRequestURI(),
                LocalDateTime.now().toString());
        return ResponseEntity.status(estado).body(cuerpo);
    }
}
