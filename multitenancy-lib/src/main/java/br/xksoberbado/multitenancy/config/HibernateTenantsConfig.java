package br.xksoberbado.multitenancy.config;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;
import java.util.logging.Logger;

@Configuration
@EnableJpaRepositories(
        basePackages = "br.xksoberbado.multitenancy",
        entityManagerFactoryRef = "entityManagerTenants",
        transactionManagerRef = "transactionManagerTenants")
public class HibernateTenantsConfig {

    private static final Logger log = Logger.getLogger(HibernateTenantsConfig.class.getName());

    @Autowired
    private DataSource defaultDS;

    @Autowired
    private JpaProperties jpaProperties;

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerTenants() {
        log.info("Entity Manager das Tenants");
        Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());
        jpaPropertiesMap.put(Environment.SHOW_SQL, true);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(defaultDS);
        em.setPackagesToScan("br.xksoberbado.multitenancy");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaPropertyMap(jpaPropertiesMap);
        return em;
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManagerTenants() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerTenants().getObject());
        return transactionManager;
    }

}
