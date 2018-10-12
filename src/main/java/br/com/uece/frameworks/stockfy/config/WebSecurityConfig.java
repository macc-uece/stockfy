package br.com.uece.frameworks.stockfy.config;

import br.com.uece.frameworks.stockfy.security.AcessoNegado;
import br.com.uece.frameworks.stockfy.security.AcessoPermitido;
import br.com.uece.frameworks.stockfy.security.UsuarioAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Order(0)
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final UsuarioAuthenticationProvider usuarioAuthenticationProvider;

    private final AcessoNegado acessoNegado;

    private final AcessoPermitido acessoPermitido;

    @Autowired
    public WebSecurityConfig(UsuarioAuthenticationProvider usuarioAuthenticationProvider, AcessoNegado acessoNegado, AcessoPermitido acessoPermitido) {
        this.usuarioAuthenticationProvider = usuarioAuthenticationProvider;
        this.acessoNegado = acessoNegado;
        this.acessoPermitido = acessoPermitido;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usuarioAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .antMatchers(
                        "/**/*.ico",
                        "/**/*.png",
                        "/**/*.jpeg",
                        "/**/*.jpg",
                        "/**/*.gif",
                        "/**/*.css",
                        "/**/*.js",
                        "/fonts/**"
                ).permitAll()
                .antMatchers("/403","/500").permitAll()
                .antMatchers("/recuperarSenha").permitAll()
                .antMatchers("/novaSenha").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("login")
                .passwordParameter("senha")
                .successHandler(acessoPermitido)
                .and()
                .logout().logoutUrl("/logout").permitAll()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedHandler(acessoNegado);
    }

}