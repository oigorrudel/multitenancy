package br.xksoberbado.multitenancy.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TenantResolver implements CurrentTenantIdentifierResolver<String> {

    @Override
    public String resolveCurrentTenantIdentifier() {
        final var tenant = TenantHolder.getTenant()
            .orElse(Tenants.TENANT_DEFAULT);

        log.info("** Tenant: " + tenant);

        return tenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
