package cc.kebei.expands.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qingyuan on 2019-06-21.
 */
public class SimpleAuthenticationManager implements AuthenticationManager {

    static final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();

    // 构建一个角色列表
    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 这里我们自定义了验证通过条件：username与password相同就可以通过认证
        if (authentication.getName().equals(authentication.getCredentials())) {
            return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), AUTHORITIES);
        }
        // 没有通过认证则抛出密码错误异常
        throw new BadCredentialsException("Bad Credentials");
    }
}
