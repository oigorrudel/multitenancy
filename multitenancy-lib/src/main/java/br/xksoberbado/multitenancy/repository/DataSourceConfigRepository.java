package br.xksoberbado.multitenancy.repository;

import br.xksoberbado.multitenancy.model.DataSourceConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataSourceConfigRepository extends JpaRepository<DataSourceConfig, Long> {

    Optional<DataSourceConfig> findByTenant(String tenant);
}
