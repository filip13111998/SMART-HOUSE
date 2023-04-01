package sbnz.ftn.uns.ac.rs.ADMIN.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Admin;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Owner;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Tenant;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.AdminRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.OwnerRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.TenantRepository;

import java.util.Optional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository ar;
    @Autowired
    private OwnerRepository or;
    @Autowired
    private TenantRepository tr;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin = ar.findByUsername(username);
        if (admin != null) {
            return admin;
        }

        Owner owner = or.findByUsername(username);
        if (owner != null) {
            return owner;
        }

        Tenant tenant = tr.findByUsername(username);
        if (tenant != null) {
            return tenant;
        }
        else{
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }

    }
}
