package bsep.ftn.uns.ac.rs.DEVICE.repository;

import bsep.ftn.uns.ac.rs.DEVICE.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AdminRepository extends MongoRepository<Admin,String> {
    Admin findByUsername(String username);
}
