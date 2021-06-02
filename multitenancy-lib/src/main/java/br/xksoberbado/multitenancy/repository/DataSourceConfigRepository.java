package br.xksoberbado.multitenancy.repository;

import br.xksoberbado.multitenancy.model.DataSourceConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceConfigRepository extends JpaRepository<DataSourceConfig, String> {
}
