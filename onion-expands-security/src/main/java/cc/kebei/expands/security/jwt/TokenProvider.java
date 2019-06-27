package cc.kebei.expands.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by Qingyuan on 2019-06-21.
 */
public class TokenProvider {

    private final static Logger LOG = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private String secretKey;
    // Token is valid 24 hours
    private long tokenValidityInMilliseconds = 86400;
    // Token is valid 72 hours
    private long tokenValidityInMillisecondsForRememberMe = 2592000;

    /**
     * 构造函数1
     */
    private TokenProvider() {
    }

    /**
     * 构造函数2
     *
     * @param secretKey
     * @param tokenValidityInMilliseconds
     * @param tokenValidityInMillisecondsForRememberMe
     */
    public TokenProvider(String secretKey, long tokenValidityInMilliseconds, long tokenValidityInMillisecondsForRememberMe) {
        this.secretKey = secretKey;
        this.tokenValidityInMilliseconds = 1000 * tokenValidityInMilliseconds;
        this.tokenValidityInMillisecondsForRememberMe = 1000 * tokenValidityInMillisecondsForRememberMe;
    }

    /**
     * 创建Token
     *
     * @param authentication 认证信息
     * @param rememberMe     是否记住我
     * @return token
     */
    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(validity)
                .compact();
    }

    /**
     * 获取认证信息
     *
     * @param token token
     * @return 认证信息
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**
     * @param authToken
     * @return
     */
    public boolean validateToken(String authToken) {
        boolean isValidated = false;
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            isValidated = true;
        } catch (SignatureException e) {
            LOG.info("Invalid JWT signature.");
            LOG.trace("Invalid JWT signature trace: {}", e);
            throw e;
        } catch (MalformedJwtException e) {
            LOG.info("Invalid JWT token.");
            LOG.trace("Invalid JWT token trace: {}", e);
            throw e;
        } catch (ExpiredJwtException e) {
            LOG.info("Expired JWT token.");
            LOG.trace("Expired JWT token trace: {}", e);
            throw e;
        } catch (UnsupportedJwtException e) {
            LOG.info("Unsupported JWT token.");
            LOG.trace("Unsupported JWT token trace: {}", e);
            throw e;
        } catch (IllegalArgumentException e) {
            LOG.info("JWT token compact of handler are invalid.");
            LOG.trace("JWT token compact of handler are invalid trace: {}", e);
            throw e;
        }
        return isValidated;
    }
}
