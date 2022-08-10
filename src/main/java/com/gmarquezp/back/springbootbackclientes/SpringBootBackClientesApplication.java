package com.gmarquezp.back.springbootbackclientes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringBootBackClientesApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBackClientesApplication.class, args);
    }



    @Override
    public void run(String... args) throws Exception {
        System.out.println("*".repeat(100));
        System.out.println("Iniciando bien =)");
    }
}
