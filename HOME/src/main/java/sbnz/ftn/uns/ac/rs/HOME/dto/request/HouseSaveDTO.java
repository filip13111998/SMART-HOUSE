package sbnz.ftn.uns.ac.rs.HOME.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseSaveDTO {
    private String name;
    private List<String> owners;
    private List<String> tenants;
}
