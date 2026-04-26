package com.pao.proiect.PlatformaLicitatii.exception;

public class OfertaInexistentaException extends RuntimeException {
    public OfertaInexistentaException(String mesaj) {
        super(mesaj);
    }
}