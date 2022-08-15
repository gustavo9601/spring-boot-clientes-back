package com.gmarquezp.back.springbootbackclientes.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer // Habilita en el escaneo de inyeccon esta configuracion
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private InfoAdicionalToken infoAdicionalToken;


    /*
    * // Usuara las implementaciones que definidmos en SpringSecurityConfig
    * */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager; // Inyecta el objeto que se encarga de autenticar los usuarios


    /*
    * Metodos sobreescritos de configuracion
    * */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // Permisos de nuestros endpoints
        security.tokenKeyAccess("permitAll()") // permiso a cualquier usuario para que se pueda autenticar en el path /oauth/token
                .checkTokenAccess("isAuthenticated()"); // para acceder al /oauth/check_token debe estar autenticado
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // Por cada cliente se debe realizar la siguiente configuracion

        clients.inMemory()
                .withClient("angular") // id del cliente que se conectara
                .secret(passwordEncoder.encode("12345"))
                .scopes("read", "write") // permiso que tendra el cliente sobre la app
                .authorizedGrantTypes("password", "refresh_token") // tipo de token
                .accessTokenValiditySeconds(3600) // tiempo de validez del token
                .refreshTokenValiditySeconds(3600); // tiempo de validez del refresh token


    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        // Añadiendo informacion adicional a la firma del token
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));

        // Registrando el AuthenticationManager
        endpoints.authenticationManager(this.authenticationManager)
                .accessTokenConverter(this.accessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain); // Registrando los datos adicionales en el token
    }

    // Definira el objeto que se encargara de convertir el token y la firma
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(JwtConfig.LLAVE_SECRETA); // Secreto
        // accessTokenConverter.setSigningKey(JwtConfig.RSA_PRIV); // Firmamos el secreto llave rsa
        // accessTokenConverter.setVerifierKey(JwtConfig.RSA_PUB); // Verifica con la llave publica
        return accessTokenConverter;
    }
}
