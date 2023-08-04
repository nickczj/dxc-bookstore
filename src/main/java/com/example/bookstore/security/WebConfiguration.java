package com.example.bookstore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebConfiguration {

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.withDefaultPasswordEncoder()
      .username("nick")
      .password("password")
      .roles("USER")
      .build();
    UserDetails user2 = User.withDefaultPasswordEncoder()
      .username("admin")
      .password("password")
      .roles("ADMIN")
      .build();
    return new InMemoryUserDetailsManager(user, user2);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests((authz) -> authz
        .requestMatchers(toH2Console()).permitAll()
        .requestMatchers(antMatcher("/h2-console/**")).permitAll()
        .requestMatchers(antMatcher("/console/**")).permitAll()
      )
      .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
      .httpBasic(Customizer.withDefaults())
      .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
      .csrf(AbstractHttpConfigurer::disable);
    return http.build();
  }

}

