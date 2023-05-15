package sbnz.ftn.uns.ac.rs.ADMIN.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

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
