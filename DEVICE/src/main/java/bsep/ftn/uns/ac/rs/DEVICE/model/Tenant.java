package bsep.ftn.uns.ac.rs.DEVICE.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "TENANTS")
public class Tenant {

    @Id
    private String  id;
    private String name;
    private String username;
    private String password;
    private Long counter;
    private String pin;
    private boolean active;

    @DBRef
    private List<House> houseList = new ArrayList<>();
//    private String role;


}
