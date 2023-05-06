package sbnz.ftn.uns.ac.rs.ADMIN.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import sbnz.ftn.uns.ac.rs.ADMIN.constants.RoleMethodNamesConstants;
import sbnz.ftn.uns.ac.rs.ADMIN.utils.TokenUtils;

import java.io.Serializable;
import java.util.Optional;

@Component("methodLevelPermission")
public class MethodLevelPermission implements PermissionEvaluator {

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        System.out.println("METHOD LEVEL PERMISSION");
        if (authentication == null) {
            return false;
        }
        if (!(permission instanceof String)) {
            return false;
        }
        String permissionString = (String) permission;
        System.out.println(permissionString);
        System.out.println(RoleMethodNamesConstants.ADMIN_METHODS.contains(permissionString));
        System.out.println(authentication.getAuthorities().contains("ROLE_ADMIN"));
        System.out.println(authentication.getAuthorities());
        Optional<? extends GrantedAuthority> authority = authentication.getAuthorities().stream().filter(a->a.getAuthority().equals("ROLE_ADMIN")).findFirst();

        if(RoleMethodNamesConstants.ADMIN_METHODS.contains(permissionString) && authority.isPresent()){
            return true;
        }
        authority = authentication.getAuthorities().stream().filter(a->a.getAuthority().equals("ROLE_OWNER")).findFirst();

        if(RoleMethodNamesConstants.OWNER_METHODS.contains(permissionString) && authority.isPresent()){
            return true;
        }
        authority = authentication.getAuthorities().stream().filter(a->a.getAuthority().equals("ROLE_TENANT")).findFirst();
        if(RoleMethodNamesConstants.TENANT_METHODS.contains(permissionString) && authority.isPresent()){
            return true;
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

}
