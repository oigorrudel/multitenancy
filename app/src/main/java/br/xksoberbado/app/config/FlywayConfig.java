package br.xksoberbado.app.config;

import br.xksoberbado.multitenancy.config.DataSourceConfigs;
import br.xksoberbado.multitenancy.config.Tenants;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    private static final String DEFAULT_SCHEMA = "public";

    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            flyway.migrate();

            final var dataSourcesMap = DataSourceConfigs.get();

            dataSourcesMap.keySet()
                .stream()
                .map(Object::toString)
                .filter(tenant -> !tenant.equals(Tenants.DEFAULT))
                .forEach(tenant -> {
                    final var fw = Flyway.configure()
                        .locations(String.format("tenants/%s", tenant))
                        .dataSource((DataSource) dataSourcesMap.get(tenant))
                        .defaultSchema(DEFAULT_SCHEMA)
                        .load();

                    fw.migrate();
                });
        };
    }
}
