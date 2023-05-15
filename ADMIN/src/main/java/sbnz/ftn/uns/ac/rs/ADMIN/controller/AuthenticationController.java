package sbnz.ftn.uns.ac.rs.ADMIN.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.ConfigurableApplicationContext;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.request.JwtAuthenticationRequest;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.response.UserTokenState;
import sbnz.ftn.uns.ac.rs.ADMIN.exception.JWTBlackListException;
import sbnz.ftn.uns.ac.rs.ADMIN.model.JWT;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.JWTRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.utils.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ConfigurableApplicationContext  context;

    @Autowired
    private JWTRepository jwtRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    //ResponseEntity<UserTokenState>
    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

        logger.info("ADMIN-APP AuthenticationController createAuthenticationToken.");
        // Register the new bean in the application context with a dynamic bean name based on the username.
        context.getBeanFactory().registerSingleton(authenticationRequest.getUsername(), authenticationRequest);

//       if(!context.containsBean(authenticationRequest.getUsername())){
//           System.out.println("USAO u bin");
//           context.getBeanFactory().registerSingleton(authenticationRequest.getUsername(), authenticationRequest);
//       }


//        String beanName = "loginCred-" + System.currentTimeMillis();
//        context.getAutowireCapableBeanFactory().registerSingleton(authenticationRequest.getUsername(),authenticationRequest);
//        context.getAutowireCapableBeanFactory().initializeBean(authenticationRequest , authenticationRequest.getUsername());
//        context.getAutowireCapableBeanFactory().re
//        context.getAutowireCapableBeanFactory().
        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Kreiraj token za tog korisnika
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String fingerprint = tokenUtils.generateFingerprint();
        String role = "";
        if(user.getAuthorities().iterator().hasNext()){
            role = user.getAuthorities().iterator().next().getAuthority();
        }
//		System.out.println("Principal"+((User) authentication.getPrincipal()).getUsername() + " " +((User) authentication.getPrincipal()).getPassword());
//        String jwt = tokenUtils.generateToken(user.getUsername(), user.getRole());
        String jwt = tokenUtils.generateToken(user.getUsername(), role , authenticationRequest.getPin() , fingerprint);

        Optional<JWT> token = jwtRepository.findAll().stream().filter(tkn -> tkn.getToken() == jwt ).findFirst();
        if(token.isPresent()){
            throw new JWTBlackListException("JWT TOKEN IS ON BLACK LIST!");
        }

        int expiresIn = tokenUtils.getExpiredIn();
        // Create cookie with JWT
        String cookie = "Fingerprint=" + fingerprint + "; HttpOnly; Path=/";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie", cookie);
//        Cookie cookie = new Cookie("JWT-TOKEN", jwt);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge(24 * 60 * 60); // Cookie expires in 24 hours
//
//        // Add cookie to response
////        response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
//        response.addCookie(cookie);
//        return ResponseEntity.ok("YESS");
        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok().headers(headers).body(new UserTokenState(jwt, expiresIn));
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<Boolean> logout(HttpServletRequest request){

        String jwt = tokenUtils.getToken(request);
        System.out.println("JWT-----" + jwt);
        jwtRepository.save(JWT.builder()
                .id(UUID.randomUUID().toString())
                .token(jwt)
                .build());

        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }


}
