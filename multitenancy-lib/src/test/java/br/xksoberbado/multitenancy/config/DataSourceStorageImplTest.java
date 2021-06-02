package br.xksoberbado.multitenancy.config;

import br.xksoberbado.multitenancy.model.DataSourceConfig;
import br.xksoberbado.multitenancy.repository.DataSourceConfigRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DataSourceStorageImplTest {

    @Mock
    private DataSource defaultDS;

    @Mock
    private DataSourceConfigRepository repository;

    @InjectMocks
    private DataSourceStorageImpl dataSourceStorage;

    @Nested
    @DisplayName("When start")
    class WhenStart {

        @Test
        @DisplayName("Should has one default datasource")
        void shouldHasOneDefaultDatasource() {
            dataSourceStorage.setDefault();

            assertThat(dataSourceStorage.get(Tenants.TENANT_DEFAULT)).isNotNull();
        }
    }

    @Nested
    @DisplayName("When find by tenant")
    class WhenFindByTenant {

        @Test
        @DisplayName("Should return null")
        void shouldReturnNull() {
            assertThat(dataSourceStorage.get("BLABLA")).isNull();
        }

        @Test
        @DisplayName("Should return datasource")
        void shouldReturnDatasource() throws SQLException {
            String tenant = "BLABLA";
            DataSourceConfig dsc = new DataSourceConfig();
            dsc.setUrl("jdbc:h2:mem:db");
            dsc.setDriverClassName("org.h2.Driver");
            dsc.setUsername("username");
            dsc.setPassword("");
            when(repository.findById(tenant)).thenReturn(Optional.of(dsc));

            assertThat(dataSourceStorage.get(tenant).getConnection().getMetaData().getURL()).isEqualTo(dsc.getUrl());
        }

    }

}
