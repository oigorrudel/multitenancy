package br.xksoberbado.multitenancy.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TenantResolverTest {

    @Mock
    private TenantCache tenantCache;

    @InjectMocks
    private TenantResolver tenantResolver;

    @Nested
    @DisplayName("When resolve current tenant identifier")
    class WhenResolveCurrentTenantIdentifier {

        @Test
        @DisplayName("Should return default tenant")
        void shouldReturnDefaultTenant() {
            when(tenantCache.getTenant()).thenReturn(Optional.empty());

            assertThat(tenantResolver.resolveCurrentTenantIdentifier()).isEqualTo(Tenants.TENANT_DEFAULT);
        }

        @Test
        @DisplayName("Should return any tenant")
        void shouldReturnAnyTenant() {
            String otherTenant = "BLABLA";
            when(tenantCache.getTenant()).thenReturn(Optional.of(otherTenant));

            assertThat(tenantResolver.resolveCurrentTenantIdentifier()).isEqualTo(otherTenant);
        }

    }

    @Nested
    @DisplayName("When validate existing current sessions")
    class WhenValidateExistingCurrentSessions {

        @Test
        @DisplayName("Should return true always")
        void shouldReturnTrueAlways() {

            assertThat(tenantResolver.validateExistingCurrentSessions()).isTrue();
        }

    }

}
