package sbnz.ftn.uns.ac.rs.ADMIN.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.model.CSR;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Certificate;

public interface CertificateRepository extends MongoRepository<Certificate,String> {
}
