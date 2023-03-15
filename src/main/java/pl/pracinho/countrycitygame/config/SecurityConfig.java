package pl.pracinho.countrycitygame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Player1")
                .password(getPasswordEncoder().encode("test"))
                .roles("player_role")
                .and()
                .withUser("Player2")
                .password(getPasswordEncoder().encode("test"))
                .roles("player_role")
                .and()
                .withUser("Player3")
                .password(getPasswordEncoder().encode("test"))
                .roles("player_role")
                .and()
                .withUser("Player4")
                .password(getPasswordEncoder().encode("test"))
                .roles("player_role");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(EndpointsConfig.UI_PREFIX).permitAll()
                .antMatchers(EndpointsConfig.UI_GAMES).permitAll()
                .antMatchers(EndpointsConfig.API_PREFIX + "/**").permitAll()
                .antMatchers("/ws/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/swagger-ui",
                        "/swagger-ui/",
                        "/swagger-ui.html",
                        "/swagger-ui.html/index.html",
                        "/webjars/**"
                );
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}