package sbnz.ftn.uns.ac.rs.ADMIN.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificateTableDTO {

    private String id;
    private Long validityStart;
    private Long validityPeriod;
    private String issuer;
    private String subject;
    private String template;
    private Boolean delete;

}
