package sbnz.ftn.uns.ac.rs.HOME.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sbnz.ftn.uns.ac.rs.HOME.model.Tenant;

public interface TenantRepository extends MongoRepository<Tenant,String> {
    Tenant findByUsername(String username);
}
