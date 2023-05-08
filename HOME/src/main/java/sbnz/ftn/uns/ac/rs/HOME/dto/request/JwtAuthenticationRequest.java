package sbnz.ftn.uns.ac.rs.HOME.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthenticationRequest {
    private String username;
    private String password;
    private String pin;
    private String role;
}
