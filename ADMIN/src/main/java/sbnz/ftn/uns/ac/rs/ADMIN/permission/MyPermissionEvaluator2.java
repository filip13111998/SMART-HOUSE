package sbnz.ftn.uns.ac.rs.ADMIN.permission;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("permissionEvaluator2")
public class MyPermissionEvaluator2 implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        System.out.println("USO u 22");
        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        System.out.println("USO u 232142");
        return true;
    }
    // Implementation details
}
