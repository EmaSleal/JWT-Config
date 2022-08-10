package com.jwt.backend.Impljwt.security;

import com.jwt.backend.Impljwt.filter.CustomAuthenticationFilter;
import com.jwt.backend.Impljwt.filter.CustomAuthorizationFilter;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity



public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

     
    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception{
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        /*http.authorizeRequests().antMatchers("/api/login/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/user/**").hasAnyRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/user/save**").hasAnyRole("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();*/
        http.authorizeRequests()
                .antMatchers("/api/user/**", "/api/role/**")
                .hasRole("ADMIN")
                .antMatchers("/api/users")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/token/refresh/**")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/api/login")
                /*.and()
                .exceptionHandling().accessDeniedPage("/errores/403")*/;
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
}
