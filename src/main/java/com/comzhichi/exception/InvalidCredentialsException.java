package com.comzhichi.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Correo electrónico o contraseña incorrectos");
    }

}