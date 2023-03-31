package sbnz.ftn.uns.ac.rs.ADMIN.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Admin;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Owner;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Tenant;

public interface TenantRepository extends MongoRepository<Tenant,Long> {
    Tenant findByUsername(String username);
}
