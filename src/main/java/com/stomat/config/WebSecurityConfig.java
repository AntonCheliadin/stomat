package com.stomat.config;

/**
 * @author Anton Chelyadin.
 * @since 09.09.18.
 */

import com.stomat.config.security.DynamicAuthenticationEntryPoint;
import com.stomat.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private DynamicAuthenticationEntryPoint dynamicAuthenticationEntryPoint;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/manage/bookings/**").hasAnyRole("MANAGER", "DOCTOR")
                .antMatchers("/api/manage/schedule/**").hasAnyRole("MANAGER", "DOCTOR")
                .antMatchers("/account/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/manage/**").hasRole("MANAGER")
                .antMatchers("/", "/home", "/registration", "/activate/*", "/static/**").permitAll()
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().permitAll()
                .and().exceptionHandling()
                .authenticationEntryPoint(dynamicAuthenticationEntryPoint)
                .accessDeniedPage("/403")
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setDynamicAuthenticationEntryPoint(DynamicAuthenticationEntryPoint dynamicAuthenticationEntryPoint) {
        this.dynamicAuthenticationEntryPoint = dynamicAuthenticationEntryPoint;
    }
}