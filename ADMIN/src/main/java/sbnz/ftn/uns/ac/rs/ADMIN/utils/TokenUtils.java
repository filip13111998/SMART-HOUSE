package sbnz.ftn.uns.ac.rs.ADMIN.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenUtils {

    // Izdavac tokena
    @Value("spring-security-example")
    private String APP_NAME;

    // Tajna koju samo backend aplikacija treba da zna kako bi mogla da generise i proveri JWT https://jwt.io/
    @Value("somesecret")
    public String SECRET;

    // Period vazenja tokena - 30 minuta
    @Value("1800000")
    private int EXPIRES_IN;

    // Naziv headera kroz koji ce se prosledjivati JWT u komunikaciji server-klijent
    @Value("Authorization")
    private String AUTH_HEADER;

    // Naziv headera kroz koji ce se prosledjivati cookie u komunikaciji server-klijent
    @Value("Cookie")
    private String COOKIE_HEADER;

    // Moguce je generisati JWT za razlicite klijente (npr. web i mobilni klijenti nece imati isto trajanje JWT,
    // JWT za mobilne klijente ce trajati duze jer se mozda aplikacija redje koristi na taj nacin)
    // Radi jednostavnosti primera, necemo voditi racuna o uređaju sa kojeg zahtev stiže.
    //	private static final String AUDIENCE_UNKNOWN = "unknown";
    //	private static final String AUDIENCE_MOBILE = "mobile";
    //	private static final String AUDIENCE_TABLET = "tablet";

    private static final String AUDIENCE_WEB = "web";

    // Algoritam za potpisivanje JWT
    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    private final SecureRandom secureRandom = new SecureRandom();

    // ============= Funkcije za generisanje JWT tokena =============

    /**
     * Funkcija za generisanje JWT tokena.
     *
     * @param username Korisničko ime korisnika kojem se token izdaje
     * @return JWT token
     */
    public String generateToken(String username , String roles , String pin , String fingerprint) {
//        System.out.println(System.currentTimeMillis());
//        System.out.println(new Date(System.currentTimeMillis() + EXPIRES_IN).getTime());
        // Kreiranje tokena sa fingerprint-om
        String fingerprintHash = generateFingerprintHash(fingerprint);

        return Jwts.builder()
//                .setClaims(new HashMap<String,Object>(){{
//                    put("roles", roles);
//                    put("pin" , pin);
//                    put("userFingerprint",fingerprintHash);
////					put("key2",  roles.toString());
//                }})
                .claim("roles", roles)
                .claim("pin" , pin)
                .claim("userFingerprint",fingerprintHash)
                .setIssuer(APP_NAME)
                .setSubject(username)
                .setAudience(generateAudience())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, SECRET)
                .compact();


        // moguce je postavljanje proizvoljnih podataka u telo JWT tokena pozivom funkcije .claim("key", value), npr. .claim("role", user.getRole())
    }

    public String generateFingerprint() {
        // Generisanje random string-a koji ce predstavljati fingerprint za korisnika
        byte[] randomFgp = new byte[50];
        this.secureRandom.nextBytes(randomFgp);
        return DatatypeConverter.printHexBinary(randomFgp);
    }

    private String generateFingerprintHash(String userFingerprint) {
        // Generisanje hash-a za fingerprint koji stavljamo u token (sprecavamo XSS da procita fingerprint i sam postavi ocekivani cookie)
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] userFingerprintDigest = digest.digest(userFingerprint.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(userFingerprintDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Funkcija za utvrđivanje tipa uređaja za koji se JWT kreira.
     * @return Tip uređaja.
     */
    private String generateAudience() {

        //	Moze se iskoristiti org.springframework.mobile.device.Device objekat za odredjivanje tipa uredjaja sa kojeg je zahtev stigao.
        //	https://spring.io/projects/spring-mobile

        //	String audience = AUDIENCE_UNKNOWN;
        //		if (device.isNormal()) {
        //			audience = AUDIENCE_WEB;
        //		} else if (device.isTablet()) {
        //			audience = AUDIENCE_TABLET;
        //		} else if (device.isMobile()) {
        //			audience = AUDIENCE_MOBILE;
        //		}

        return AUDIENCE_WEB;
    }

    /**
     * Funkcija generiše datum do kog je JWT token validan.
     *
     * @return Datum do kojeg je JWT validan.
     */
    private Date generateExpirationDate() {

        //samo 30 sekundi
//        return new Date(System.currentTimeMillis() + 20_000);
        return new Date(System.currentTimeMillis() + EXPIRES_IN);
//        return new Date(new Date().getTime() + EXPIRES_IN);
    }

    // =================================================================

    // ============= Funkcije za citanje informacija iz JWT tokena =============

    /**
     * Funkcija za preuzimanje JWT tokena iz zahteva.
     *
     * @param request HTTP zahtev koji klijent šalje.
     * @return JWT token ili null ukoliko se token ne nalazi u odgovarajućem zaglavlju HTTP zahteva.
     */
    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);

        // JWT se prosledjuje kroz header 'Authorization' u formatu:
        // Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // preuzimamo samo token (vrednost tokena je nakon "Bearer " prefiksa)
        }
        System.out.println("UPAO");
        return null;
    }

    public String getFingerprintFromCookie(HttpServletRequest request) {
        String userFingerprint = null;
        if (request.getCookies() != null && request.getCookies().length > 0) {
            List<Cookie> cookies = Arrays.stream(request.getCookies()).collect(Collectors.toList());
            Optional<Cookie> cookie = cookies.stream().filter(c -> "Fingerprint".equals(c.getName())).findFirst();

            if (cookie.isPresent()) {
                userFingerprint = cookie.get().getValue();
            }
        }
        return userFingerprint;
    }



    /**
     * Funkcija za preuzimanje vlasnika tokena (korisničko ime).
     * @param token JWT token.
     * @return Korisničko ime iz tokena ili null ukoliko ne postoji.
     */
    public String getUsernameFromToken(String token) {
        String username;

        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            username = null;
        }

        return username;
    }

    /**
     * Funkcija za preuzimanje datuma kreiranja tokena.
     * @param token JWT token.
     * @return Datum kada je token kreiran.
     */
    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    /**
     * Funkcija za preuzimanje informacije o uređaju iz tokena.
     *
     * @param token JWT token.
     * @return Tip uredjaja.
     */
    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    /**
     * Funkcija za preuzimanje datuma do kada token važi.
     *
     * @param token JWT token.
     * @return Datum do kojeg token važi.
     */
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            expiration = null;
        }

        return expiration;
    }

    private String getFingerprintFromToken(String token) {
        String fingerprint;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            fingerprint = claims.get("userFingerprint", String.class);
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            fingerprint = null;
        }
        return fingerprint;
    }

    private String getAlgorithmFromToken(String token) {
        String algorithm;
        try {
            algorithm = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getHeader()
                    .getAlgorithm();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            algorithm = null;
        }
        return algorithm;
    }

    /**
     * Funkcija za čitanje svih podataka iz JWT tokena
     *
     * @param token JWT token.
     * @return Podaci iz tokena.
     */
    public Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            claims = null;
        }

        // Preuzimanje proizvoljnih podataka je moguce pozivom funkcije claims.get(key)

        return claims;
    }

    // =================================================================

    // ============= Funkcije za validaciju JWT tokena =============

    /**
     *
     * Funkcija za validaciju JWT tokena.
     *
     * @param token JWT token.
     * @param userDetails Informacije o korisniku koji je vlasnik JWT tokena.
     * @return Informacija da li je token validan ili ne.
     */
    public Boolean validateToken(String token, UserDetails userDetails , String fingerprint) {
//        Iterator<? extends GrantedAuthority> iterator = userDetails.getAuthorities().iterator();
//        if (iterator.hasNext()) {
//            GrantedAuthority authority = iterator.next();
//            if(authority.getAuthority().equals("ROLE_ADMIN")){
//                Admin user = (Admin) userDetails;
//            }
//            else if (authority.getAuthority().equals("ROLE_CITIZEN")){
//                Citizen user = (Citizen) userDetails;
//            }else{
//                Driver user = (Driver) userDetails;
//            }
//            // do something with the authority object
//        }
//        try {
//            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
//        } catch (SignatureException ex) {
//            // Invalid signature
//            System.out.println("WRONG SIGNATURE ALGORITHM");
//        } catch (MalformedJwtException ex) {
//            // Invalid JWT token
//        } catch (ExpiredJwtException ex) {
//            // Expired JWT token
//        } catch (UnsupportedJwtException ex) {
//            // Unsupported JWT token
//        } catch (IllegalArgumentException ex) {
//            // JWT token compact of handler are invalid
//        }

        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);

         // Token je validan kada:
        boolean isUsernameValid = (username != null // korisnicko ime nije null
                && username.equals(userDetails.getUsername()) // korisnicko ime iz tokena se podudara sa korisnickom imenom koje pise u bazi
                && !isCreatedBeforeLastPasswordReset(created, new Date(61000))); // nakon kreiranja tokena korisnik nije menjao svoju lozinku

        boolean isFingerprintValid = false;
        boolean isAlgorithmValid = false;
        if (fingerprint != null) {
            isFingerprintValid = validateTokenFingerprint(fingerprint, token);
            isAlgorithmValid = SIGNATURE_ALGORITHM.getValue().equals(getAlgorithmFromToken(token));
        }

        return isUsernameValid;
    }

    private boolean validateTokenFingerprint(String fingerprint, String token) {
        // Hesiranje fingerprint-a radi poređenja sa hesiranim fingerprint-om u tokenu
        String fingerprintHash = generateFingerprintHash(fingerprint);
        String fingerprintFromToken = getFingerprintFromToken(token);
        return fingerprintFromToken.equals(fingerprintHash);
    }

    /**
     * Funkcija proverava da li je lozinka korisnika izmenjena nakon izdavanja tokena.
     *
     * @param created Datum kreiranja tokena.
     * @param lastPasswordReset Datum poslednje izmene lozinke.
     * @return Informacija da li je token kreiran pre poslednje izmene lozinke ili ne.
     */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    // =================================================================

    /**
     * Funkcija za preuzimanje perioda važenja tokena.
     *
     * @return Period važenja tokena.
     */
    public int getExpiredIn() {
        return EXPIRES_IN;
    }

    /**
     * Funkcija za preuzimanje sadržaja AUTH_HEADER-a iz zahteva.
     *
     * @param request HTTP zahtev.
     *
     * @return Sadrzaj iz AUTH_HEADER-a.
     */
    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }

}
