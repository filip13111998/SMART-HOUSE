package sbnz.ftn.uns.ac.rs.HOME.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sbnz.ftn.uns.ac.rs.HOME.model.Message;

public interface MessageRepository extends MongoRepository<Message,String> {
    Message findByType(String type);
}
