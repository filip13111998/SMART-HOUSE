package sbnz.ftn.uns.ac.rs.HOME.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sbnz.ftn.uns.ac.rs.HOME.model.Owner;

public interface OwnerRepository extends MongoRepository<Owner,String> {
    Owner findByUsername(String username);
}
