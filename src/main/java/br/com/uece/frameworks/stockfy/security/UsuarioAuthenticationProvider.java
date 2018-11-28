package br.com.uece.frameworks.stockfy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Create by Bruno Barbosa - 11/10/2018
 */
@Component
public class UsuarioAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final AutenticacaoUserDetailService userDetailService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioAuthenticationProvider(AutenticacaoUserDetailService userDetailService, PasswordEncoder passwordEncoder) {
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        if (usernamePasswordAuthenticationToken.getCredentials() == null || userDetails.getPassword() == null) {
            throw new BadCredentialsException("Credenciais não podem ser nulas");
        }

        if (!passwordEncoder.matches((String) usernamePasswordAuthenticationToken.getCredentials(), userDetails.getPassword())) {
            throw new BadCredentialsException("Senha Inválida");
        }
    }

    @Override
    protected UserDetails retrieveUser(String login, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        return userDetailService.loadUserByUsername(login);
    }
}
