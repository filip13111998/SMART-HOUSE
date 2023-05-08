package sbnz.ftn.uns.ac.rs.HOME.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sbnz.ftn.uns.ac.rs.HOME.model.Admin;

public interface AdminRepository extends MongoRepository<Admin,String> {
    Admin findByUsername(String username);
}
