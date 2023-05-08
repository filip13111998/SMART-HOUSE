package bsep.ftn.uns.ac.rs.DEVICE.repository;

import bsep.ftn.uns.ac.rs.DEVICE.model.Tenant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TenantRepository extends MongoRepository<Tenant,String> {
    Tenant findByUsername(String username);
}
