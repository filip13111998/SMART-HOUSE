package sbnz.ftn.uns.ac.rs.ADMIN.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.model.CSR;
import sbnz.ftn.uns.ac.rs.ADMIN.model.JWT;

public interface JWTRepository extends MongoRepository<JWT,String> {
}
