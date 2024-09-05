package br.xksoberbado.multitenancy.error;

public class TenantNotFound extends RuntimeException {

    public TenantNotFound(final String message) {
        super(message);
    }
}
