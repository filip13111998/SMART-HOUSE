package sbnz.ftn.uns.ac.rs.HOME.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sbnz.ftn.uns.ac.rs.HOME.model.Device;

public interface DeviceRepository extends MongoRepository<Device,String> {
}
