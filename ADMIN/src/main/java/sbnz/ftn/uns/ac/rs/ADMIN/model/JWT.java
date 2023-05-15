package sbnz.ftn.uns.ac.rs.ADMIN.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "JWT")
public class JWT {

    @Id
    private String id;
    private String token;
}
