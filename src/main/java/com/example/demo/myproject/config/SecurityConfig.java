package com.example.demo.myproject.config;

import com.example.demo.myproject.models.UserRole;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JWTAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(
                                                            HttpSecurity http
                                                    ) throws Exception {
        return http
                //.requiresChannel(channel -> channel.anyRequest().requiresSecure())
               .cors()
                .and()
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request ->
                                request
                                        .requestMatchers("/api/v1/auth/**", "/error").permitAll()
                                        .requestMatchers("/", "/error").permitAll()
                                        .requestMatchers(HttpMethod.GET,"/api/v1/user/users","/api/v1/user/users/**").authenticated()
                                        .requestMatchers(HttpMethod.PUT,"/api/v1/user/users","/api/v1/user/users/**").authenticated()
                                        .requestMatchers(HttpMethod.POST,"/api/v1/user/users","/api/v1/user/users/**").authenticated()
                                        .requestMatchers("/api/v1/user/users/**").hasAnyRole("ADMIN")                                        .requestMatchers(HttpMethod.GET,"/api/v1/user/users").authenticated()

                                        .requestMatchers(HttpMethod.GET,"/api/v1/city","/api/v1/city/**").authenticated()
                                        .requestMatchers(HttpMethod.PUT,"/api/v1/city","/api/v1/city/**").authenticated()
                                        .requestMatchers(HttpMethod.POST,"/api/v1/city","/api/v1/city/**").authenticated()
                                        .requestMatchers("/api/v1/city/**").hasAnyRole("ADMIN")

                                        .requestMatchers(HttpMethod.GET,"/api/v1/region","/api/v1/region/**").authenticated()
                                        .requestMatchers(HttpMethod.PUT,"/api/v1/region","/api/v1/region/**").authenticated()
                                        .requestMatchers(HttpMethod.POST,"/api/v1/region","/api/v1/region/**").authenticated()
                                        .requestMatchers("/api/v1/region/**").hasAnyRole("ADMIN")

                                        .requestMatchers(HttpMethod.GET,"/api/v1/town","/api/v1/town/**").authenticated()
                                        .requestMatchers(HttpMethod.PUT,"/api/v1/town","/api/v1/town/**").authenticated()
                                        .requestMatchers(HttpMethod.POST,"/api/v1/town","/api/v1/town/**").authenticated()
                                        .requestMatchers("/api/v1/town/**").hasAnyRole("ADMIN")

                                        .requestMatchers(HttpMethod.GET,"/api/v1/company","/api/v1/company/**").authenticated()
                                        .requestMatchers(HttpMethod.PUT,"/api/v1/company","/api/v1/company/**").authenticated()
                                        .requestMatchers(HttpMethod.POST,"/api/v1/company","/api/v1/company/**").authenticated()
                                        .requestMatchers("/api/v1/company/**").hasAnyRole("ADMIN")








                                        .requestMatchers("/auth/**","/swagger-ui/**","/swagger-ui.html","/api-docs/**")
                                        .permitAll()
                                        .anyRequest().permitAll())
                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}
