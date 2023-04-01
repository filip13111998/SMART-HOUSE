package sbnz.ftn.uns.ac.rs.ADMIN.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "CERTIFICATE")
public class Certificate {

    @Id
    private String id;
    private Long validityStart;
    private Long validityPeriod;
    private String issuer;
    private String subject;
    private Boolean delete;
}
