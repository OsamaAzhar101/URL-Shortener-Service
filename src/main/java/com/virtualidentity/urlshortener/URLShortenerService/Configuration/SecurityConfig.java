package com.virtualidentity.urlshortener.URLShortenerService.Configuration;

import com.virtualidentity.urlshortener.URLShortenerService.ExceptionHandling.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Enable @PreAuthorize annotation
public class SecurityConfig extends WebSecurityConfigurerAdapter {


  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("admin").password("{noop}password").roles("ADMIN")
        .and()
        .withUser("user").password("{noop}password").roles("USER");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
        .antMatchers("/api/statistics/**").authenticated()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/shorten").permitAll()
//        .antMatchers("/stats").hasAnyRole("ADMIN", "USER")
        .and()
        .formLogin();
  }

}
