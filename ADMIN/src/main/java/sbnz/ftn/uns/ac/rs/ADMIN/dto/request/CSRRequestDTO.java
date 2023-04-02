package sbnz.ftn.uns.ac.rs.ADMIN.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CSRRequestDTO {

    private String username;
    private String password;
    private String name;
    private Long validityStart;
    private Long validityPeriod;
    private String commonName;
    private String organizationUnion;
    private String organizationName;
    private String localityName;
    private String stateName;
    private String country;
    private String role;
    private String template;
    private String extensions;


}
