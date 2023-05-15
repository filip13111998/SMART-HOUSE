package sbnz.ftn.uns.ac.rs.HOME.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import sbnz.ftn.uns.ac.rs.HOME.model.DeviceMessage;

public interface DeviceMessageRepository extends MongoRepository<DeviceMessage,String>{
}
