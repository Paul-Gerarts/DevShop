package be.syntra.devshop.DevshopBack.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JWTTokenProvider JWTTokenProvider;

    public JWTConfigurer(JWTTokenProvider JWTTokenProvider) {
        this.JWTTokenProvider = JWTTokenProvider;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        JWTFilter customFilter = new JWTFilter(JWTTokenProvider);
        httpSecurity.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
