package bsep.ftn.uns.ac.rs.DEVICE.repository;

import bsep.ftn.uns.ac.rs.DEVICE.model.Device;
import bsep.ftn.uns.ac.rs.DEVICE.model.DeviceMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeviceMessageRepository extends MongoRepository<DeviceMessage,String>{
}
