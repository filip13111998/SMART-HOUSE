package sbnz.ftn.uns.ac.rs.ADMIN.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigDTO {
    private String name;
    private String minutes;
    private String regex;
}
