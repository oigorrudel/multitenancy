package br.xksoberbado.multitenancy.config;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TenantResolverTest {

    @InjectMocks
    private TenantResolver tenantResolver;

    @Nested
    class WhenResolveCurrentTenantIdentifier {

        @Test
        void shouldReturnDefaultTenant() {
            assertThat(tenantResolver.resolveCurrentTenantIdentifier()).isEqualTo(Tenants.TENANT_DEFAULT);
        }

        @Test
        void shouldReturnAnyTenant() {
            final var otherTenant = "BLABLA";

            TenantHolder.setTenant(otherTenant);

            assertThat(tenantResolver.resolveCurrentTenantIdentifier()).isEqualTo(otherTenant);
        }
    }

    @Nested
    class WhenValidateExistingCurrentSessions {

        @Test
        void shouldReturnTrueAlways() {
            assertThat(tenantResolver.validateExistingCurrentSessions()).isTrue();
        }
    }
}
