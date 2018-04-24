package org.sjlchatham.sjlcweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*  TODO:
        - There is a bug where, if the user fails a login, then succeeds, they are redirected to the login-error path
          even though the login still works. Curiously, they are not redirected to login-error UNTIL the login
          succeeds.
     */

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
        ;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(
                            "/css/**",
                            "/img/**",
                            "/js/**",
                            "/doc/**",
                            "/",
                            "/login",
                            "/signup",
                            "/about/**",
                            "/preschool",
                            "/preschool/**",
                            "/calendar",
                            "/news",
                            "/news/viewpost/*",
                            "/links",
                            "/downloads/**").permitAll()
                    .antMatchers("/news/new-post", "/admin", "/admin-service").hasRole("ADMIN")
                        .anyRequest().authenticated()
                        .and()
                .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login-error")
                    .permitAll()
                        .and()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .permitAll()
                        .and()
                .httpBasic()
                        .and()
                // Limits the maximum session for each user so that only 1 "instance" of each user can be logged in at once.
                .sessionManagement()
                    .maximumSessions(1);
    }

}
