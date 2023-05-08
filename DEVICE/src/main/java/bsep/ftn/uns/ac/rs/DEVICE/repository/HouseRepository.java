package bsep.ftn.uns.ac.rs.DEVICE.repository;

import bsep.ftn.uns.ac.rs.DEVICE.model.House;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HouseRepository  extends MongoRepository<House,String> {
}
