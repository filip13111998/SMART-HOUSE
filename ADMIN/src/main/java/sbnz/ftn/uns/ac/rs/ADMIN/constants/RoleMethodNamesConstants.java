package sbnz.ftn.uns.ac.rs.ADMIN.constants;

import java.util.Arrays;
import java.util.List;

public class RoleMethodNamesConstants {

    public static final List<String> ADMIN_METHODS = Arrays.asList(
            "getAllUsers" ,
            "removeUser" ,
            "ownerRoleUser" ,
            "tenantRoleUser",
            "getAllCertificates",
            "deleteCertificate",
            "revokeCertificate",
            "validateCertificate",
//            "createCSR",
//            "verifyCSR",
            "getAllCSRS",
            "acceptCSR",
            "deleteCSR",
            "getConfigs",
            "updateConfig"
    );

    public static final List<String> OWNER_METHODS = Arrays.asList(
            "" ,
            "" ,
            ""
    );


    public static final List<String> TENANT_METHODS = Arrays.asList(
            "" ,
            "" ,
            ""
    );

}
