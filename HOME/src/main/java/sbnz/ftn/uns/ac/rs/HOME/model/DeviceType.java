package sbnz.ftn.uns.ac.rs.HOME.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "DEVICE_TYPE")
public class DeviceType {

    @Id
    private String id;
    private String name; // sijalica,merac temperature itd...

    private List<DeviceMessage> messages = new ArrayList<>();

}
