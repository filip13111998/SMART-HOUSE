package bsep.ftn.uns.ac.rs.DEVICE.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "MESSAGE")
public class Message {

    @Id
    private String id;
    private String text;
    private String status;
    private String device;
    private long date;
    private String type;



    public String writeToFile(){
        return status + "|" + type + "|" + text + "|" + date + '\n';
    }

    public String getContent(){
        return status + "|" + type + "|" + text + "|" + date;
    }
}
