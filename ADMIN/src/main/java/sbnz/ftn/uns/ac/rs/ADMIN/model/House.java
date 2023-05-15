package sbnz.ftn.uns.ac.rs.ADMIN.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "HOUSE")
public class House {
    @Id
    private String id;
    private String name;

    private List<Device> devices = new ArrayList<>();
    @DBRef
    private List<Owner> ownerList = new ArrayList<>();
    @DBRef
    private List<Tenant> tenantList = new ArrayList<>();
}
