package br.xksoberbado.app.config;

import br.xksoberbado.multitenancy.config.TenantCache;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TenantCacheImpl implements TenantCache {

    private static ThreadLocal<String> threadTenant = new InheritableThreadLocal<>();

    @Override
    public void setTenant(String tenant) {
        threadTenant.set(tenant);
    }

    @Override
    public Optional<String> getTenant() {
        return Optional.ofNullable(threadTenant.get());
    }
}
