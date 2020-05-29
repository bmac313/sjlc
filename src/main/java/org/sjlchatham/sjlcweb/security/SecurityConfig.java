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
                            "/login/resetpass",
                            "/signup",
                            "/about/**",
                            "/preschool",
                            "/preschool/**",
                            "/calendar",
                            "/news",
                            "/news/viewpost/*",
                            "/links",
                            "/downloads/**").permitAll()
                    .antMatchers(
                            "/login/resetpass/resetforuser"
                    ).hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
                    .antMatchers(
                            "/news/new-post",
                            "/news/editpost/**",
                            "/news/deletepost",
                            "/news/deletepost/**",
                            "/events/edit",
                            "/events/edit/**",
                            "/events/schedule",
                            "/events/schedule/**",
                            "/events/deleteevent",
                            "/events/deleteevent/**",
                            // TODO: The below three paths are authenticated for testing purposes. When testing is complete, move /events and /events/register to permitAll.
                            "/events",
                            "/events/register",
                            "/events/register/**",
                            "/changepass",
                            "/changepass/**" ).hasRole("ADMIN")
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
