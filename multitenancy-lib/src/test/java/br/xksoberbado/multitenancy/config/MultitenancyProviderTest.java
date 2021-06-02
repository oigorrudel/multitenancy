package br.xksoberbado.multitenancy.config;

import br.xksoberbado.multitenancy.error.TenantNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MultitenancyProviderTest {

    @Mock
    private DataSource defaultDS;

    @Mock
    private DataSourceStorage dataSourceStorage;

    @InjectMocks
    private MultitenancyProvider provider;

    @Nested
    @DisplayName("When select any datasource")
    class WhenSelectAnyDatasource {

        @Test
        @DisplayName("Should return defaultDS")
        void shouldReturnDefaultDS() {
            assertThat(provider.selectAnyDataSource()).isEqualTo(defaultDS);
        }

    }

    @Nested
    @DisplayName("When select datasource by tenant")
    class WhenSelectDatasourceByTenant {

        @Test
        @DisplayName("Should return datasource selected")
        void shouldReturnDatasourceSelected() {
            String tenant = "TESTE";
            DataSource ds = DataSourceBuilder.create().build();
            when(dataSourceStorage.get(tenant)).thenReturn(ds);

            assertThat(provider.selectDataSource(tenant)).isEqualTo(ds);
        }

        @Test
        @DisplayName("Should throw TenantNotFound")
        void shouldThrowTenantNotFound() {
            when(dataSourceStorage.get(any())).thenReturn(null);

            assertThatThrownBy(() -> provider.selectDataSource("tenant"))
                    .isInstanceOf(TenantNotFound.class);
        }

    }

}
