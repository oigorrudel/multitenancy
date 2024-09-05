package br.xksoberbado.multitenancy.config;

import br.xksoberbado.multitenancy.model.DataSourceConfig;
import br.xksoberbado.multitenancy.repository.DataSourceConfigRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataSourceStorageImpl implements DataSourceStorage {

    private final DataSource defaultDS;
    private final DataSourceConfigRepository repository;

    private Map<String, DataSource> map = new HashMap<>();

    @PostConstruct
    public void setDefault() {
        map.put(Tenants.TENANT_DEFAULT, defaultDS);
    }

    public DataSource get(final String tenant) {
        return Optional.ofNullable(map.get(tenant))
            .orElseGet(() -> findByRepository(tenant));
    }

    private DataSource findByRepository(final String tenant) {
        return repository.findById(tenant)
            .map(dsc -> {
                final var ds = buildDataSource(dsc);
                map.put(tenant, ds);
                return ds;
            })
            .orElse(null);
    }

    private DataSource buildDataSource(final DataSourceConfig dataSourceConfig) {
        return DataSourceBuilder.create()
            .driverClassName(dataSourceConfig.getDriverClassName())
            .url(dataSourceConfig.getUrl())
            .username(dataSourceConfig.getUsername())
            .password(dataSourceConfig.getPassword())
            .build();
    }

}
