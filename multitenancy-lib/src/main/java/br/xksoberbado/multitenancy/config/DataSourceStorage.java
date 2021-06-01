package br.xksoberbado.multitenancy.config;

import br.xksoberbado.multitenancy.model.DataSourceConfig;
import br.xksoberbado.multitenancy.repository.DataSourceConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DataSourceStorage {

    @Autowired
    private DataSource defaultDS;

    private Map<String, DataSource> map = new HashMap<>();

    @Autowired
    private DataSourceConfigRepository repository;

    @PostConstruct
    private void populateMap() {
        map.put(Tenants.TENANT_DEFAULT, defaultDS);
    }

    public DataSource get(String tenant) {
        return Optional.ofNullable(map.get(tenant))
                .orElseGet(() -> findByRepository(tenant));
    }

    private DataSource findByRepository(String tenant) {
        return repository.findByTenant(tenant)
                .map(dsc -> {
                    DataSource ds = buildDataSource(dsc);
                    map.put(tenant, ds);
                    return ds;
                })
                .orElse(null);
    }

    private DataSource buildDataSource(DataSourceConfig dataSourceConfig) {
        return DataSourceBuilder.create()
                .driverClassName(dataSourceConfig.getDriverClassName())
                .url(dataSourceConfig.getUrl())
                .username(dataSourceConfig.getUsername())
                .password(dataSourceConfig.getPassword())
                .build();
    }

}
