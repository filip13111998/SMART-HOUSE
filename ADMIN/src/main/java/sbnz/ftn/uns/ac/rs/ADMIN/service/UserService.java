package sbnz.ftn.uns.ac.rs.ADMIN.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sbnz.ftn.uns.ac.rs.ADMIN.controller.CSRController;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.response.UserTableDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Owner;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Tenant;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.OwnerRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.TenantRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class UserService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PasswordEncoder encoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public List<UserTableDTO> getAllUsers() {

        logger.info("ADMIN-APP UserService getAllUsers.");

        List<UserTableDTO> owners = ownerRepository.findAll().stream()
                .filter(o->o.isActive())
                .map(o->
                        UserTableDTO.builder()
                                .id(o.getId()).name(o.getName())
                                .username(o.getUsername())
                                .owner(true)
                                .tenant(false)
                                .build()
                )
                .collect(Collectors.toList());

        List<UserTableDTO> tenants = tenantRepository.findAll().stream()
                .filter(t->t.isActive())
                .map(t->
                        UserTableDTO.builder()
                                .id(t.getId()).name(t.getName())
                                .username(t.getUsername())
                                .owner(false)
                                .tenant(true)
                                .build()
                )
                .collect(Collectors.toList());

        List<UserTableDTO> mergedList = Stream.concat(owners.stream(), tenants.stream())
                .collect(Collectors.toMap(UserTableDTO::getUsername, // key mapper function
                        dto -> dto, // value mapper function
                        (existingDto, newDto) -> { // merge function to resolve conflicts
                            return UserTableDTO.builder()
                                    .id(existingDto.getId())
                                    .name(existingDto.getName())
                                    .username(existingDto.getUsername())
                                    .owner(true)
                                    .tenant(true)
                                    .build();
                        }))
                .values().stream()
                .collect(Collectors.toList());

        return mergedList;
    }


    public Boolean removeUser(String username, String role) {

        if(role.equals("ROLE_OWNER")){
            Owner owner = ownerRepository.findByUsername(username);
            if(owner != null){
                owner.setActive(false);
                ownerRepository.save(owner);
                logger.info("ADMIN-APP UserService removeUser.");
                return true;
            }
        }
        if(role.equals("ROLE_TENANT")){
            Tenant tenant = tenantRepository.findByUsername(username);
            if(tenant != null){
                tenant.setActive(false);
                tenantRepository.save(tenant);
                logger.info("ADMIN-APP UserService removeUser.");
                return true;
            }
        }
        logger.warn("ADMIN-APP UserService removeUser.");
        return false;
    }

    public Boolean ownerRoleUser(String username , boolean flag) {
        Owner owner = ownerRepository.findByUsername(username);
        Tenant tenant = tenantRepository.findByUsername(username);

        if(owner != null){
            if(!flag){
                owner.setActive(flag);
                ownerRepository.save(owner);

                logger.info("ADMIN-APP UserService ownerRoleUser.");

                return true;
            }
            if(tenant != null && tenant.isActive()){
                owner.setActive(flag);
                ownerRepository.save(owner);

                logger.info("ADMIN-APP UserService ownerRoleUser.");

                return true;
            }
            logger.warn("ADMIN-APP UserService ownerRoleUser.");
            return false;
        }else{
            if(tenant != null && tenant.isActive()){
                owner = Owner.builder()
                            .id(UUID.randomUUID().toString())
                            .name(tenant.getName())
                            .username(tenant.getUsername())
                            .password(tenant.getPassword())
                            .pin(tenant.getPin())
                            .counter(0l)
                            .active(true)
                            .build();
                    ownerRepository.save(owner);

                    logger.info("ADMIN-APP UserService ownerRoleUser.");

                    return true;

            }
        }


//        if(role.equals("ROLE_OWNER")){
//            if(!flag){
//                if(owner == null  && tenant != null){
//                    owner = Owner.builder()
//                            .name(tenant.getName())
//                            .username(tenant.getUsername())
//                            .password(tenant.getPassword())
//                            .pin(tenant.getPin())
//                            .counter(0l)
//                            .active(true)
//                            .build();
//                    ownerRepository.save(owner);
//                    return true;
//                }
//            }else{
//                if(owner != null){
//                    owner.setActive(false);
//                    ownerRepository.save(owner);
//                    return true;
//                }
//            }
//        }
//        if(role.equals("ROLE_TENANT")){
//            if(!flag){
//                if(tenant == null){
//                    tenant = Tenant.builder()
//                            .name(owner.getName())
//                            .username(owner.getUsername())
//                            .password(owner.getPassword())
//                            .pin(owner.getPin())
//                            .counter(0l)
//                            .active(true)
//                            .build();
//                    tenantRepository.save(tenant);
//                    return true;
//                }
//            }else{
//                if(tenant != null){
//                    tenant.setActive(false);
//                    tenantRepository.save(tenant);
//                    return true;
//                }
//            }
//        }

        logger.warn("ADMIN-APP UserService ownerRoleUser.");

        return false;
    }

    public Boolean tenantRoleUser(String username, boolean flag) {

        Owner owner = ownerRepository.findByUsername(username);
        Tenant tenant = tenantRepository.findByUsername(username);

        if(tenant != null){
            if(!flag){
                tenant.setActive(flag);
                tenantRepository.save(tenant);

                logger.info("ADMIN-APP UserService tenantRoleUser.");

                return true;
            }
            if(owner != null && owner.isActive()){
                tenant.setActive(flag);
                tenantRepository.save(tenant);

                logger.info("ADMIN-APP UserService tenantRoleUser.");

                return true;
            }

            logger.warn("ADMIN-APP UserService tenantRoleUser.");

            return false;
        }else{
            if(owner != null && owner.isActive()){
                tenant = Tenant.builder()
                        .id(UUID.randomUUID().toString())
                        .name(owner.getName())
                        .username(owner.getUsername())
                        .password(owner.getPassword())
                        .pin(owner.getPin())
                        .counter(0l)
                        .active(true)
                        .build();
                tenantRepository.save(tenant);

                logger.info("ADMIN-APP UserService tenantRoleUser.");

                return true;

            }
        }
        logger.warn("ADMIN-APP UserService tenantRoleUser.");

        return false;
    }

}
