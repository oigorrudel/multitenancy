package br.xksoberbado.multitenancy.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.logging.Logger;

@Configuration
@EnableJpaRepositories(basePackages = "br.xksoberbado.app")
@RequiredArgsConstructor
public class HibernateAppConfig {

    private static final Logger LOGGER = Logger.getLogger(HibernateAppConfig.class.getName());

    private final JpaProperties jpaProperties;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource,
                                                                       final MultiTenantConnectionProvider provider,
                                                                       final CurrentTenantIdentifierResolver resolver) {

        LOGGER.info("App Entity Manager");
        final var jpaPropertiesMap = new HashMap<String, Object>(jpaProperties.getProperties());
        jpaPropertiesMap.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, provider);
        jpaPropertiesMap.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, resolver);
        jpaPropertiesMap.put(Environment.SHOW_SQL, true);

        final var entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("br.xksoberbado.app");
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManager.setJpaPropertyMap(jpaPropertiesMap);

        return entityManager;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final DataSource dataSource,
                                                         final MultiTenantConnectionProvider multitenancyProvider,
                                                         final CurrentTenantIdentifierResolver tenantResolver) {
        final var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory(dataSource, multitenancyProvider, tenantResolver).getObject());

        return transactionManager;
    }
}
