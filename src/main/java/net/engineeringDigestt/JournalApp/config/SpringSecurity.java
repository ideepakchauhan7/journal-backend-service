package net.engineeringDigestt.JournalApp.config;

import net.engineeringDigestt.JournalApp.Services.UserDetailServiceimpl;
import net.engineeringDigestt.JournalApp.filter.JWTfilter;
import net.engineeringDigestt.JournalApp.utils.JWTutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailServiceimpl userDetailService;

    @Autowired
    private JWTutils jwtutils;
    @Autowired
    private JWTfilter jwtfilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/create-user", "/Health-check", "/error","/public/**").permitAll()
                                .requestMatchers("/journal/**","/user/**").authenticated()
                                .requestMatchers("/Admin/**").hasRole("ADMIN")
                                .anyRequest().permitAll()// Choice: Public APIs
                               // Choice: Admin only
                                                 // Choice: Everything else locked
                )
//                .httpBasic(Customizer.withDefaults())
//                .http.addFilterBefore()// Choice: Use Basic Auth (ideal for Postman)
                .csrf(csrf -> csrf.disable())         // Choice: Disable CSRF for your Journal App

                .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class)
        .build();

    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
