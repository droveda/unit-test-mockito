package br.com.empresa.leilao.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
				.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/").permitAll()
                .antMatchers(HttpMethod.GET, "/leiloes").permitAll()
                .antMatchers("/h2-console").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().ignoringAntMatchers("/h2-console/**")
                .and()
                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/leiloes", true).permitAll())
                .logout(logout -> {
                    logout.logoutUrl("/logout").logoutSuccessUrl("/leiloes");
                })
                .csrf().disable()
				.headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**", "/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**, /**.jsp, /**.do");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        BCryptPasswordEncoder bCryptPasswordEncoder = passwordEncoder();
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("pt", "BR"));
        return resolver;
    }

}
