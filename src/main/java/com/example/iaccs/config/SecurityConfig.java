package com.example.iaccs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

            http.cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login/**").permitAll()
                        .requestMatchers("/api/commands/**")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN")

                        .requestMatchers("/api/ntns/**")
                        .hasAnyRole("SUPER_ADMIN", "ADMIN", "COMMAND_HEAD")

                        .requestMatchers("/api/agencies/**")
                        .hasAnyRole(
                                "SUPER_ADMIN",
                                "ADMIN",
                                "COMMAND_HEAD",
                                "NTN_HEAD"
                        )

                        .requestMatchers("/api/endpoints/**")
                        .hasAnyRole(
                                "SUPER_ADMIN",
                                "ADMIN",
                                "COMMAND_HEAD",
                                "NTN_HEAD",
                                "AGENCY_HEAD"
                        )

                        .requestMatchers("/api/me/**")
                        .hasAnyRole(
                                "SUPER_ADMIN",
                                "ADMIN",
                                "COMMAND_HEAD",
                                "NTN_HEAD",
                                "AGENCY_HEAD"
                        )

                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                //.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(
                "http://localhost:5500",
                "http://127.0.0.1:5500"
        ));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

//    @Bean
//    public UserDetailsService users() {
//
//        UserDetails admin =
//                User.withUsername("admin")
//                        .password(passwordEncoder().encode("admin123"))
//                        .roles("ADMIN")
//                        .build();
//        UserDetails user =
//                User.withUsername("sark")
//                        .password(passwordEncoder().encode("s@123"))
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(admin,user);
//    }


}
