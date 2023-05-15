package sbnz.ftn.uns.ac.rs.HOME.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceMessageSaveDTO {
    private String text;
    private String status;
}
