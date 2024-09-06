package br.xksoberbado.multitenancy.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TenantHolder {

    private static final ThreadLocal<String> TENANT = new ThreadLocal<>();

    public static void set(final String tenant) {
        TENANT.set(tenant);
    }

    public static String get() {
        return TENANT.get();
    }
}
