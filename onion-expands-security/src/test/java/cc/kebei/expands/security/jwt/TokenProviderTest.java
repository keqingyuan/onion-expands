package cc.kebei.expands.security.jwt;

import cc.kebei.expands.security.SimpleAuthenticationManager;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.Assert.*;

/**
 * Created by Qingyuan on 2019-06-21.
 */
public class TokenProviderTest {

    private static AuthenticationManager authenticationManager = new SimpleAuthenticationManager();

    @Test
    public void validateToken() {
        TokenProvider tokenProvider = new TokenProvider("36971949b9de9828c401c30acf5b309003be2019", 86400, 2592000);
        TokenProvider tokenProvider2 = new TokenProvider("36971949b9de9828c401c30acf5b309003be2019", 86400, 2592000);
        Authentication authentication = new UsernamePasswordAuthenticationToken("keqingyuan", "keqingyuan");
        authentication = authenticationManager.authenticate(authentication);
        String token = tokenProvider.createToken(authentication, false);
        System.out.println(token);
        System.out.println(tokenProvider2.validateToken(token));
        System.out.println(tokenProvider2.getAuthentication(token));
    }
}