package sbnz.ftn.uns.ac.rs.ADMIN.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import sbnz.ftn.uns.ac.rs.ADMIN.utils.TokenUtils;

import java.io.Serializable;
import java.security.Permission;
import java.util.Set;
import java.util.stream.Collectors;

@Component("permissionEvaluator1")
public class MyPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        System.out.println("KECC");
        if (authentication == null) {
            return false;
        }
        if (!(permission instanceof String)) {
            return false;
        }
        String permissionString = (String) permission;
//        if (!permissionString.startsWith("PERMISSION_")) {
//            return false;
//        }
        System.out.println("USAO : " + permissionString);
        System.out.println(authentication.getAuthorities());
        System.out.println(authentication.getCredentials());
        System.out.println(tokenUtils.getUsernameFromToken(authentication.getCredentials().toString()));
        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        System.out.println("KECC TO");
        return true;
    }
}
