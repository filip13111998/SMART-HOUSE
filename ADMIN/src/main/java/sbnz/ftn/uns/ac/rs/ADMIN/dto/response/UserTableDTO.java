package sbnz.ftn.uns.ac.rs.ADMIN.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTableDTO {

    private String id;
    private String name;
    private String username;
    private boolean owner;
    private boolean tenant;

}
