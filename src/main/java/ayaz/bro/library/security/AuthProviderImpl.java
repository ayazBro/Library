package ayaz.bro.library.security;

import ayaz.bro.library.services.ClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthProviderImpl implements AuthenticationProvider {
    private final ClientDetailsService clientDetailsService;

    @Autowired
    public AuthProviderImpl(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name=authentication.getName();
        String password=authentication.getCredentials().toString();

        UserDetails clientDetails=clientDetailsService.loadUserByUsername(name);
        if(!password.equals(clientDetails.getPassword()))
            throw new BadCredentialsException("Incorrect password");
        return new UsernamePasswordAuthenticationToken(clientDetails,password,
                clientDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
