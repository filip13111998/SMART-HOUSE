package sbnz.ftn.uns.ac.rs.ADMIN.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Admin;

public interface AdminRepository extends MongoRepository<Admin,Long> {
    Admin findByUsername(String username);
}
