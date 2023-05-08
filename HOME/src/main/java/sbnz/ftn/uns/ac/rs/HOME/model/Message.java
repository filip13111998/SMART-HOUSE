package sbnz.ftn.uns.ac.rs.HOME.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kie.api.definition.type.Role;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "MESSAGE")
@Role(Role.Type.EVENT)
public class Message {

    @Id
    private String id;
    private String text;
    private String status;
    private String device;
    private long date;
    private String type;

}
