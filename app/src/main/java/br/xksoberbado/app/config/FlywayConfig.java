package br.xksoberbado.app.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

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
//                        .baselineOnMigrate(Boolean.TRUE)
                        .dataSource((DataSource) dataSourcesMap.get(tenant))
                        .schemas(tenant)
                        .load();

                    fw.migrate();
                });
        };
    }
}
