package br.xksoberbado.multitenancy;

import br.xksoberbado.multitenancy.config.HibernateConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(HibernateConfig.class)
@Configuration
public @interface EnableMultitenancy {
}
