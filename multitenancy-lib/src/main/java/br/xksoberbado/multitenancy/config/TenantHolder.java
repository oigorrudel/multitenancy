package br.xksoberbado.multitenancy.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TenantHolder {

    private static final ThreadLocal<String> THREAD_TENANT = new InheritableThreadLocal<>();

    public static void setTenant(final String tenant) {
        THREAD_TENANT.set(tenant);
    }

    public static Optional<String> getTenant() {
        return Optional.ofNullable(THREAD_TENANT.get());
    }
}
