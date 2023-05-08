package bsep.ftn.uns.ac.rs.DEVICE.repository;


import bsep.ftn.uns.ac.rs.DEVICE.model.Device;
import bsep.ftn.uns.ac.rs.DEVICE.model.DeviceType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TypeRepository extends MongoRepository<DeviceType,String> {
}
