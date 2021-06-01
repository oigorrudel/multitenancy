package br.xksoberbado.multitenancy.config;

import br.xksoberbado.multitenancy.error.TenantNotFound;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Optional;

@Component
public class MultitenancyProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    @Autowired
    private DataSource defaultDS;

    @Lazy
    @Autowired
    private DataSourceStorage dataSourceStorage;

    @Override
    protected DataSource selectAnyDataSource() {
        return defaultDS;
    }

    @Override
    protected DataSource selectDataSource(String tenant) {
        return Optional.ofNullable(dataSourceStorage.get(tenant))
                .orElseThrow(() -> new TenantNotFound("Data Source Config não encontrado"));
    }
}
