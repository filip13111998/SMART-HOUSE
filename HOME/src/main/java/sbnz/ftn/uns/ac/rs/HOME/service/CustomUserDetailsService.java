package sbnz.ftn.uns.ac.rs.HOME.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sbnz.ftn.uns.ac.rs.HOME.dto.request.JwtAuthenticationRequest;
import sbnz.ftn.uns.ac.rs.HOME.model.Admin;
import sbnz.ftn.uns.ac.rs.HOME.model.Owner;
import sbnz.ftn.uns.ac.rs.HOME.model.Tenant;
import sbnz.ftn.uns.ac.rs.HOME.repository.AdminRepository;
import sbnz.ftn.uns.ac.rs.HOME.repository.OwnerRepository;
import sbnz.ftn.uns.ac.rs.HOME.repository.TenantRepository;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository ar;
    @Autowired
    private OwnerRepository or;
    @Autowired
    private TenantRepository tr;

    @Autowired
    private ConfigurableApplicationContext context;

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("HOME-APP CustomUserDetailsService loadUserByUsername.");


        JwtAuthenticationRequest jar = context.getBean(username , JwtAuthenticationRequest.class);
//        context.getBeanFactory().destroyBean(username , jar);
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
        beanFactory.destroySingleton(username);
//        System.out.println(jar.getPin() + ':' + jar.getUsername() + ':' + jar.getPassword());
        if(jar.getRole().equals("ROLE_ADMIN")){
            Admin admin = ar.findByUsername(username);
            if (admin != null) {
                if(admin.getPin().equals(jar.getPin()) && admin.isActive()){
                    admin.setCounter(0l);
                    ar.save(admin);
                    return admin;
                }
                else {
                    if(admin.getCounter() == 5){
                        admin.setActive(false);
                    }else {
                        admin.setCounter(admin.getCounter() + 1);
                    }
                    ar.save(admin);
                    throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
                }
            }
            else {
                throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
            }
        }


        if(jar.getRole().equals("ROLE_OWNER")){
            Owner owner = or.findByUsername(username);
            if (owner != null) {
                if(owner.getPin().equals(jar.getPin()) && owner.isActive()){
                    owner.setCounter(0l);
                    or.save(owner);
                    return owner;
                }
                else {
                    if(owner.getCounter() == 5){
                        owner.setActive(false);
                    }else {
                        owner.setCounter(owner.getCounter() + 1);
                    }
                    or.save(owner);
                    throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
                }
            }
            else {
                throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
            }
        }


        if(jar.getRole().equals("ROLE_TENANT")){
            Tenant tenant = tr.findByUsername(username);
            if (tenant != null) {

                if(tenant.getPin().equals(jar.getPin()) && tenant.isActive()){
                    tenant.setCounter(0l);
                    tr.save(tenant);
                    return tenant;
                }
                else {
                    if(tenant.getCounter() == 5){
                        tenant.setActive(false);
                    }else {
                        tenant.setCounter(tenant.getCounter() + 1);
                    }
                    tr.save(tenant);
                    throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
                }

            }
        }

        throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));

    }
}
