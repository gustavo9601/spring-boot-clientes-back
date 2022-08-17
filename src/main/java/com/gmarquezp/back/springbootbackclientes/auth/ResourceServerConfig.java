package com.gmarquezp.back.springbootbackclientes.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

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
                .antMatchers("/api/clientes/{id}").permitAll()
                .antMatchers("/api/facturas/**").permitAll()
                // .antMatchers(HttpMethod.GET, "/api/clientes/{id}").hasAnyRole("ADMIN", "USER") // Acceso a usuarios de rol admin y user
                // .antMatchers(HttpMethod.POST, "/api/clientes/upload").hasAnyRole("ADMIN", "USER")
                // .antMatchers(HttpMethod.POST, "/api/clientes").hasRole("ADMIN")
                // .antMatchers( "/api/clientes/**").hasRole("ADMIN") // Solo admin en todos los demas request para el path
                .anyRequest().authenticated() // Cualquier otra peticion requiere autenticacion
                .and()
                .cors().configurationSource(this.corsConfigurationSource()); // Seteando la configuracion de CORS
    }



    /*
    * Configuracion de cors desde Spring Security
    * */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        //  config.setAllowedOriginPatterns(Arrays.asList("*")); // "*" // para todos
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowCredentials(true);
        // config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        config.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /*
    * Registrando el bean de cors
    * */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(this.corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE); // Prioridad mas alta para el filtro
        return bean;
    }

}
