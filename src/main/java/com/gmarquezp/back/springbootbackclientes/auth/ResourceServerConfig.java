package com.gmarquezp.back.springbootbackclientes.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        /*
        * Los permisos sobre las rutas son en cascada, deben ir de lo mas generico a lo general
        * */
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/clientes", "/api/clientes/page/**", "/api/clientes/upload/**").permitAll() // Permitimos el acceso a todos los clientes AL REQUEST GET
                // .antMatchers(HttpMethod.GET, "/api/clientes/{id}").hasAnyRole("ADMIN", "USER") // Acceso a usuarios de rol admin y user
                // .antMatchers(HttpMethod.POST, "/api/clientes/upload").hasAnyRole("ADMIN", "USER")
                // .antMatchers(HttpMethod.POST, "/api/clientes").hasRole("ADMIN")
                // .antMatchers( "/api/clientes/**").hasRole("ADMIN") // Solo admin en todos los demas request para el path
                .anyRequest().authenticated(); // Cualquier otra peticion requiere autenticacion
    }
}
