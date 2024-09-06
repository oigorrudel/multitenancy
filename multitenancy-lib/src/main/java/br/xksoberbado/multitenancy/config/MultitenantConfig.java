package br.xksoberbado.multitenancy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
public class MultitenantConfig {

    @Bean
    DataSource dataSource() {
        final var multiDataSource = new MultitenantDataSource();
        multiDataSource.setDefaultTargetDataSource(DataSourceConfigs.def());
        multiDataSource.setTargetDataSources(DataSourceConfigs.get());
        multiDataSource.afterPropertiesSet();

        return multiDataSource;
    }


    @Slf4j
    private static class MultitenantDataSource extends AbstractRoutingDataSource {

        @Override
        protected String determineCurrentLookupKey() {
            final var tenant = Optional.ofNullable(TenantHolder.get())
                .orElse(Tenants.DEFAULT);

            log.info("Tenant: {}", tenant);

            return tenant;
        }
    }
}
