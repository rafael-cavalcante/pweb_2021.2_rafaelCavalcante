package br.com.rafaelcavalcante.biritashop.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration {

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
            .antMatchers("/").permitAll()
            .antMatchers("/cadastrar").permitAll()
            .anyRequest().authenticated())
                .formLogin((form) -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/", true)
                    .failureUrl("/login-error").permitAll())
                .logout((logout) -> logout
                    .logoutUrl("/logout")
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID"))
                .exceptionHandling((ex) -> ex
                    .accessDeniedPage("/negado"))
                .sessionManagement(session -> session
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
                    .expiredUrl("/expired"));

        return http.build();
    }

    @Bean
    SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/static/**", "/public/**", "/resources/**", "/assets/**", "/js/**",
                "/css/**", "/templates/fragments/**", "/image/**");
    }
}
