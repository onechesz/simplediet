package com.github.onechesz.simplediet.config;

import com.github.onechesz.simplediet.services.UserDetailsService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    @Contract(pure = true)
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(authorizeHttpRequestsCustomizer -> {
            authorizeHttpRequestsCustomizer.requestMatchers("/login", "/register").anonymous();
            authorizeHttpRequestsCustomizer.requestMatchers("/diets/add", "/diets/*/edit").hasRole("ADMIN");
            authorizeHttpRequestsCustomizer.requestMatchers("/profile", "diet").authenticated();
            authorizeHttpRequestsCustomizer.anyRequest().permitAll();
        }).formLogin(formLoginCustomizer -> {
            formLoginCustomizer.loginPage("/login");
            formLoginCustomizer.loginProcessingUrl("/login");
            formLoginCustomizer.failureUrl("/login");
            formLoginCustomizer.defaultSuccessUrl("/", true);
        }).logout(logoutCustomizer -> {
            logoutCustomizer.logoutUrl("/logout");
            logoutCustomizer.logoutSuccessUrl("/");
        }).userDetailsService(userDetailsService).httpBasic(Customizer.withDefaults()).build();
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
