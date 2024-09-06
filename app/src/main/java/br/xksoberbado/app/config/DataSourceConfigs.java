package br.xksoberbado.app.config;

import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.util.Map;

public class DataSourceConfigs {

    public static Map<Object, Object> get() {
        return Map.ofEntries(
            Map.entry("multi_one", multiOne()),
            Map.entry("multi_two", multiTwo()),
            Map.entry(Tenants.DEFAULT, def())
        );
    }

    private static DataSource multiOne() {
        return DataSourceBuilder.create()
            .driverClassName("org.postgresql.Driver")
            .url("jdbc:postgresql://localhost:5432/multi_one")
            .username("postgres")
            .password("12345")
            .build();
    }

    private static DataSource multiTwo() {
        return DataSourceBuilder.create()
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .url("jdbc:mysql://localhost:3306/multi_two")
            .username("mysql")
            .password("12345")
            .build();
    }

    public static DataSource def() {
        return DataSourceBuilder.create()
            .driverClassName("org.h2.Driver")
            .url("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1")
            .username("xksoberbado")
            .password("")
            .build();
    }
}
