package sbnz.ftn.uns.ac.rs.ADMIN.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Admin;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Owner;

public interface OwnerRepository extends MongoRepository<Owner,String> {
    Owner findByUsername(String username);
}
