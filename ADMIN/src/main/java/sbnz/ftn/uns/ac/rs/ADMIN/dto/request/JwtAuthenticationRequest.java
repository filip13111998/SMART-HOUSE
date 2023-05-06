package sbnz.ftn.uns.ac.rs.ADMIN.dto.request;

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

//    public JwtAuthenticationRequest() {
//        super();
//    }
//
//    public JwtAuthenticationRequest(String username, String password , String pin) {
//        this.setUsername(username);
//        this.setPassword(password);
//        this.setPin(pin);
//    }
//
//    public String getUsername() {
//        return this.username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return this.password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getPin() {
//        return pin;
//    }
//
//    public void setPin(String pin) {
//        this.pin = pin;
//    }
}
