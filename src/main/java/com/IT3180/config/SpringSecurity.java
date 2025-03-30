package com.IT3180.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.IT3180.util.TbConstants;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity  
public class SpringSecurity {
    private final CustomSuccessHandler customSuccessHandler;

    public SpringSecurity(CustomSuccessHandler customSuccessHandler) {
        super();
        this.customSuccessHandler = customSuccessHandler;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/login","/css/**", "/js/**", "/images/**").permitAll()  // Cho phép file tĩnh
                .requestMatchers("/login/**").permitAll()
                .requestMatchers("/user/**").hasAuthority(TbConstants.Role.USER)
                .requestMatchers("/admin/**").hasAuthority(TbConstants.Role.ADMIN)
                .requestMatchers("/guest/**").hasAuthority(TbConstants.Role.GUEST)
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .loginProcessingUrl("/auth/login")
                .successHandler(customSuccessHandler)
                .permitAll()
            )
            .logout((logout) -> logout.permitAll())
            .exceptionHandling(handling -> handling.accessDeniedPage("/access-denied"));
        
        return http.build();
    }
}
