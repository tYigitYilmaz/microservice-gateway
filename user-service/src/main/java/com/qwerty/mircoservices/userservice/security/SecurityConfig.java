package com.qwerty.mircoservices.userservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.core.env.Environment;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity( debug = true )
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    private Environment env;

    //private static final String SALT = "salt"; // Salt should be protected
    private static final String[] PUBLIC_MATCHERS = {
            "/webjars/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/",
            "/about/**",
            "/contract/**",
            "/error/**/*",
            "/console/**",
            "/api/account/register"
    };

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }



    public SecurityConfig (Environment env, UserDetailsService userDetailsService){
        this.env = env;
        this.userDetailsService = userDetailsService;
    }

/*
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }
*/

    /*@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/v1/signup");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers(PUBLIC_MATCHERS).
                permitAll().anyRequest().authenticated();

        http
                .csrf().disable().cors().disable()
                .formLogin().failureUrl("/index?error").defaultSuccessUrl("/userFront").loginPage("/index").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/index?logout").deleteCookies("remember-me").permitAll()
                .and()
                .rememberMe();
    }

 /*   @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
        auth.userDetailsService(accountSecurityService).passwordEncoder(passwordEncoder());
    }*/


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder( passwordEncoder);
        provider.setUserDetailsService( userDetailsService() );
        return provider;
    }

 /*   @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable() // disable form authentication
                .anonymous().disable() // disable anonymous user
                .httpBasic().and()
                // restricting access to authenticated users
                .authorizeRequests().anyRequest().authenticated();
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // provides the default AuthenticationManager as a Bean
        return super.authenticationManagerBean();
    }
}