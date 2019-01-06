package com.qwerty.mircoservices.userservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity( debug = true )
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


   /* private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }*/

    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String[] PUBLIC_MATCHERS = {
            "/webjars/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/about/**",
            "/contract/**",
            "/error/**/*",
            "/console/**",
            "/oauth/**"


    };

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder( passwordEncoder );
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .anonymous().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/oauth/token").permitAll()
                .and().httpBasic().and()
                .csrf().disable();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//
//
//    private Environment env;
//
//    //private static final String SALT = "salt"; // Salt should be protected
//    private static final String[] PUBLIC_MATCHERS = {
//            "/webjars/**",
//            "/css/**",
//            "/js/**",
//            "/images/**",
//            "/about/**",
//            "/contract/**",
//            "/error/**/*",
//            "/console/**",
//            "/api/account/register"
//};
//
//
//public SecurityConfig (Environment env, UserDetailsService userDetailsService){
//        this.env = env;
//        this.userDetailsService = userDetailsService;
//    }
//
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
//    }
//
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/api/v1/signup");
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http
//                .authorizeRequests()
//                .antMatchers(PUBLIC_MATCHERS).
//                permitAll().anyRequest().authenticated();
//
//        http
//                .csrf().disable().cors().disable()
//                .formLogin().failureUrl("/index?error").defaultSuccessUrl("/userFront").loginPage("/index").permitAll()
//                .and()
//                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/index?logout").deleteCookies("remember-me").permitAll()
//                .and()
//                .rememberMe();
//    }
//
//  @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        //auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
//        auth.userDetailsService(accountSecurityService).passwordEncoder(passwordEncoder());
//    }
//
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder( passwordEncoder);
//        provider.setUserDetailsService( userDetailsService() );
//        return provider;
//    }
//
//   @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .formLogin().disable() // disable form authentication
//                .anonymous().disable() // disable anonymous user
//                .httpBasic().and()
//                // restricting access to authenticated users
//                .authorizeRequests().anyRequest().authenticated();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder);
//    }
//
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        // provides the default AuthenticationManager as a Bean
//        return super.authenticationManagerBean();
//    }
}