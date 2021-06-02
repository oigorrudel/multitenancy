package br.xksoberbado.multitenancy.config;

import java.util.Optional;

public interface TenantCache {

    void setTenant(String tenant);

    Optional<String> getTenant();
}
