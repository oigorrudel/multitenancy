package br.xksoberbado.multitenancy.config;

import br.xksoberbado.multitenancy.error.TenantNotFound;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MultitenancyProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl<String> {

    private final DataSource defaultDS;

    @Lazy
    private final DataSourceStorage dataSourceStorage;

    @Override
    protected DataSource selectAnyDataSource() {
        return defaultDS;
    }

    @Override
    protected DataSource selectDataSource(final String tenant) {
        return Optional.ofNullable(dataSourceStorage.get(tenant))
            .orElseThrow(() -> new TenantNotFound("Data Source Config não encontrado"));
    }
}
