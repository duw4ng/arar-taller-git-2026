package py.edu.uc.lp3.arar_taller_git_2026.rest.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.CompaneroNoConfiguradoException;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.CompaneroRechazoException;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.EntidadNoEncontradaException;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.MinecraftException;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.ReglaDeJuegoException;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.ErrorCompaneroResponse;
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

    @ExceptionHandler(CompaneroRechazoException.class)
    public ResponseEntity<ErrorCompaneroResponse> manejarRechazoDeCompanero(
            CompaneroRechazoException excepcion,
            HttpServletRequest solicitud) {
        ErrorCompaneroResponse cuerpo = new ErrorCompaneroResponse(
                "COMPANERO_RECHAZO",
                excepcion.getOrigen(),
                excepcion.getMessage(),
                HttpStatus.BAD_GATEWAY.value(),
                excepcion.getEstadoCompanero(),
                excepcion.getCodigoCompanero(),
                solicitud.getRequestURI(),
                LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(cuerpo);
    }

    @ExceptionHandler(CompaneroNoConfiguradoException.class)
    public ResponseEntity<ErrorResponse> manejarCompaneroNoConfigurado(
            CompaneroNoConfiguradoException excepcion,
            HttpServletRequest solicitud) {
        ErrorResponse cuerpo = new ErrorResponse(
                "COMPANERO_NO_CONFIGURADO",
                excepcion.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                solicitud.getRequestURI(),
                LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(cuerpo);
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
