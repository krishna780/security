package com.spring.security.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class ProjectSecurityConfig  {


    @Bean
    SecurityFilterChain  configure(HttpSecurity http) throws Exception {
       return http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
               .cors().configurationSource(request -> {
           CorsConfiguration corsConfiguration = new CorsConfiguration();
           corsConfiguration.setMaxAge(3000L);
           corsConfiguration.setAllowCredentials(true);
           corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
           corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
           corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
           return corsConfiguration;
       })
                .and().csrf().disable()
                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                 .addFilterAfter(new AfterLoggingFilterEx(),BasicAuthenticationFilter.class)
                 .addFilterBefore(new JWTTokenGeneratorFilter(),BasicAuthenticationFilter.class)
               .addFilterAfter(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .mvcMatchers("/myAccount").hasRole("admin")
                .antMatchers("/myBalance").authenticated()
                .antMatchers("/userRest").permitAll().and().formLogin().and().httpBasic(Customizer.withDefaults()).build();
    }

    /*  @Override
      protected void configure(AuthenticationManagerBuilder auth) throws Exception {
          auth.inMemoryAuthentication().withUser("admin").password("12345").authorities("admin").and()
                  .withUser("user").password("12345").authorities("read").and()
                  .passwordEncoder(NoOpPasswordEncoder.getInstance());
      }*/
 /*   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        UserDetails builder = User.withUsername("admin").password("1234").authorities("admin").build();
        UserDetails builder2 = User.withUsername("user").password("1234").authorities("read").build();
        manager.createUser(builder);
        manager.createUser(builder2);
        auth.userDetailsService(manager);
    }*/

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) throws Exception {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
