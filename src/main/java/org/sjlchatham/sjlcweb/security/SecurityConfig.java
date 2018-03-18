package org.sjlchatham.sjlcweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER").and()
                .withUser("admin").password("password").roles("USER", "ADMIN");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(
                            "/css/**",
                            "/img/**",
                            "/js/**",
                            "/",
                            "/login",
                            "/about/*",
                            "/preschool",
                            "/preschool/*",
                            "/calendar",
                            "/news",
                            "/news/viewpost/*",
                            "/links",
                            "/downloads/*").permitAll()
                    .antMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                        .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .failureUrl("/login-error")
                        .and()
                    .logout()
                        .logoutSuccessUrl("/");
    }

}
