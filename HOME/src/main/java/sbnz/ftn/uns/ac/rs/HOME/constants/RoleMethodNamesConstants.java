package sbnz.ftn.uns.ac.rs.HOME.constants;

import java.util.Arrays;
import java.util.List;

public class RoleMethodNamesConstants {

    public static final List<String> ADMIN_METHODS = Arrays.asList(
            "getAllDevices" ,
            "saveDeviceType" ,
            ""

    );

    public static final List<String> OWNER_METHODS = Arrays.asList(
            "getAllDevices" ,
            "saveHouse" ,
            "saveDevice",
            "getAllDeviceTypes",
            "getAllHouses"
    );


    public static final List<String> TENANT_METHODS = Arrays.asList(
            "getAllDevices" ,
            "" ,
            ""
    );

}
