package sbnz.ftn.uns.ac.rs.HOME.auth;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import sbnz.ftn.uns.ac.rs.HOME.dto.request.JwtAuthenticationRequest;
import sbnz.ftn.uns.ac.rs.HOME.utils.TokenUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter{
	
	private TokenUtils tokenUtils;

    private UserDetailsService userDetailsService;

    private ConfigurableApplicationContext context;

    protected final Log LOGGER = LogFactory.getLog(getClass());

    public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService , ConfigurableApplicationContext context) {
        this.tokenUtils = tokenHelper;
        this.userDetailsService = userDetailsService;
        this.context = context;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {


        String username;
        String role;
        String pin;
        // 1. Preuzimanje JWT tokena iz zahteva
        String authToken = tokenUtils.getToken(request);
        String fingerprint = tokenUtils.getFingerprintFromCookie(request);

//        System.out.println(authToken.toString() != null);
//        System.out.println(fingerprint != null);
        try {

            if (authToken != null) {
//                System.out.println("AUTH");
                // 2. Citanje korisnickog imena iz tokena
                username = tokenUtils.getUsernameFromToken(authToken);
                role = tokenUtils.getAllClaimsFromToken(authToken).get("roles" , String.class);
                pin =  tokenUtils.getAllClaimsFromToken(authToken).get("pin" , String.class);
                if (username != null) {
//                	System.out.println("USERNAME" + username);
//                    System.out.println("PRC");
                    // 3. Preuzimanje korisnika na osnovu username-a
                    context.getBeanFactory().registerSingleton(username, new JwtAuthenticationRequest(username,"",pin, role));

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // 4. Provera da li je prosledjeni token validan
                    if (tokenUtils.validateToken(authToken, userDetails , fingerprint)) {
//                    	System.out.println("TOKEN VALIDAN" + username);
//                        throw new ServletException();

                        // 5. Kreiraj autentifikaciju
                        TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                        authentication.setToken(authToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }

        } catch (ExpiredJwtException ex) {
            LOGGER.debug("Token expired!");
        }

        // prosledi request dalje u sledeci filter
        chain.doFilter(request, response);
    }
	
}
