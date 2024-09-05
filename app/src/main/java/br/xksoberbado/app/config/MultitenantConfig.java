package br.xksoberbado.app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Configuration
public class MultitenantConfig {

    private static final String DEFAULT_TENANT = "DEFAULT";

    private static final Map<Object, Object> MAP = Map.ofEntries(
        Map.entry("MULTI_ONE", multiOne()),
        Map.entry("MULTI_TWO", multiTwo()),
        Map.entry(DEFAULT_TENANT, def())
    );

    @Bean
    DataSource dataSource() {
        final var multiDataSource = new MultitenantDataSource();
        multiDataSource.setDefaultTargetDataSource(def());
        multiDataSource.setTargetDataSources(MAP);
        multiDataSource.afterPropertiesSet();

        return multiDataSource;
    }

    private static DataSource multiOne() {
        return DataSourceBuilder.create()
            .driverClassName("org.postgresql.Driver")
            .url("jdbc:postgresql://localhost:5432/MULTI_ONE")
            .username("postgres")
            .password("12345")
            .build();
    }

    private static DataSource multiTwo() {
        return DataSourceBuilder.create()
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .url("jdbc:mysql://localhost:3306/MULTI_TWO")
            .username("root")
            .password("12345")
            .build();
    }

    private static DataSource def() {
        return DataSourceBuilder.create()
            .driverClassName("org.h2.Driver")
            .url("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1")
            .username("xksoberbado")
            .password("")
            .build();
    }

    @Slf4j
    private static class MultitenantDataSource extends AbstractRoutingDataSource {

        @Override
        protected String determineCurrentLookupKey() {
            final var tenant = Optional.ofNullable(TenantHolder.get())
                .orElse(DEFAULT_TENANT);

            log.info("Tenant: {}", tenant);

            return tenant;
        }
    }
}
