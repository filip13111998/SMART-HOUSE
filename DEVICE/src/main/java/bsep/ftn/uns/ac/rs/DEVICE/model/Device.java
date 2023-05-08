package bsep.ftn.uns.ac.rs.DEVICE.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "DEVICE")
public class Device {

    @Id
    private String id;
    private String name;
//    @OneToMany
//    private List<DeviceType> type = new ArrayList<>();
//    @OneToOne
    @DBRef
    private DeviceType deviceType;

    @DBRef
    private House house;

}
