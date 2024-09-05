package br.xksoberbado.multitenancy.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.Environment;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.logging.Logger;

@Configuration
@EnableJpaRepositories(
        basePackages = "br.xksoberbado.multitenancy",
        entityManagerFactoryRef = "entityManagerTenants",
        transactionManagerRef = "transactionManagerTenants")
@RequiredArgsConstructor
public class HibernateTenantsConfig {

    private static final Logger LOGGER = Logger.getLogger(HibernateTenantsConfig.class.getName());

    private final DataSource defaultDS;
    private final JpaProperties jpaProperties;

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerTenants() {
        LOGGER.info("Tenants Entity Manager");

        final var jpaPropertiesMap = new HashMap<String, Object>(jpaProperties.getProperties());
        jpaPropertiesMap.put(Environment.SHOW_SQL, true);

        final var entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(defaultDS);
        entityManager.setPackagesToScan("br.xksoberbado.multitenancy");
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManager.setJpaPropertyMap(jpaPropertiesMap);

        return entityManager;
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManagerTenants() {
        final var transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerTenants().getObject());

        return transactionManager;
    }
}
