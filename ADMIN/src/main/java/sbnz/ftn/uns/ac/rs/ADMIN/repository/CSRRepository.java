package sbnz.ftn.uns.ac.rs.ADMIN.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.model.CSR;

public interface CSRRepository extends MongoRepository<CSR,String> {
    CSR findBySerialNumber(String serialNumber);
}
