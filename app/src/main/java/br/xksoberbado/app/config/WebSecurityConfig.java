package br.xksoberbado.app.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().defaultSuccessUrl("/people");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //CUIDADO: sem o {noop} da erro de falta de encoder
        auth.inMemoryAuthentication()
                .withUser("joao")
                .password("{noop}12345")
                .roles("USER")
                .and()
                .withUser("maria")
                .password("{noop}12345")
                .roles("USER");
    }
}
