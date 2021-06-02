package br.xksoberbado.multitenancy;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(EnableMultitenancyApplication.class)
@Configuration
public @interface EnableMultitenancy {
}
