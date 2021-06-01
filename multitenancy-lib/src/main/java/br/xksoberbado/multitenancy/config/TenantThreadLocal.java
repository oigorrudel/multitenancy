package br.xksoberbado.multitenancy.config;

import java.util.Optional;

public class TenantThreadLocal {

    private TenantThreadLocal() {
    }

    private static ThreadLocal<String> threadTenant = new InheritableThreadLocal<>();

    public static void setTenant(String tenant) {
        threadTenant.set(tenant);
    }

    public static Optional<String> getTenant() {
        return Optional.ofNullable(threadTenant.get());
    }
}
