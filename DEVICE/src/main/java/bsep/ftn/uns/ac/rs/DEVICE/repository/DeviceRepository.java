package bsep.ftn.uns.ac.rs.DEVICE.repository;

import bsep.ftn.uns.ac.rs.DEVICE.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeviceRepository extends MongoRepository<Device,String> {
}
