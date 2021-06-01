package br.xksoberbado.multitenancy.error;

public class TenantNotFound extends RuntimeException {

    public TenantNotFound(String message) {
        super(message);
    }
}
