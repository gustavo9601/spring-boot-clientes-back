package com.gmarquezp.back.springbootbackclientes.auth;

import com.gmarquezp.back.springbootbackclientes.models.entity.Usuario;
import com.gmarquezp.back.springbootbackclientes.models.services.IUsuarioService;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/*
* Clase que permitira añadir datos adicionales en el token
* */
@Component
public class InfoAdicionalToken implements TokenEnhancer {

    @Autowired
    IUsuarioService usuarioService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> infoAdicional = new HashMap<>();

        Usuario usuario = this.usuarioService.findByUsername(oAuth2Authentication.getName());

        infoAdicional.put("nueva_llave", "valor de la nueva llave");
        infoAdicional.put("usuario_por_bd", usuario);

        // Seteamos los atributos adicionales, en el token casteando con la implementacion de la interfaz
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(infoAdicional);

        return oAuth2AccessToken;
    }
}
