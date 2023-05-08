package bsep.ftn.uns.ac.rs.DEVICE.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "ADMINS")
public class Admin {

    @Id
    private String  id;
    private String name;
    private String username;
    private String password;
    private Long counter;
    private String pin;
    private boolean active;


}
