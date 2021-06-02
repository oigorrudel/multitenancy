package br.xksoberbado.multitenancy.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class TenantResolver implements CurrentTenantIdentifierResolver {

    private static final Logger log = Logger.getLogger(TenantResolver.class.getName());

    @Autowired
    private TenantCache tenantCache;

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = tenantCache.getTenant()
                .orElse(Tenants.TENANT_DEFAULT);

        log.info("** Tenant: " + tenant);

        return tenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
