package sbnz.ftn.uns.ac.rs.ADMIN.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CSRResponseDTO {
    private String id;
    private String username;
    private Long validityStart;
    private Long validityPeriod;
    private String serialNumber;
    private String commonName;
    private String organizationUnion;
    private String organizationName;
    private String localityName;
    private String stateName;
    private String country;
}
