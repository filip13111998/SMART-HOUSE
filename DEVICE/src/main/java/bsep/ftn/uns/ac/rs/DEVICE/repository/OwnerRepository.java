package bsep.ftn.uns.ac.rs.DEVICE.repository;

import bsep.ftn.uns.ac.rs.DEVICE.model.Owner;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface OwnerRepository extends MongoRepository<Owner,String> {
    Owner findByUsername(String username);
}
