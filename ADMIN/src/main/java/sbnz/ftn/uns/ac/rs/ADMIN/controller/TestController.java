package sbnz.ftn.uns.ac.rs.ADMIN.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import sbnz.ftn.uns.ac.rs.ADMIN.permission.MyPermissionEvaluator2;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @GetMapping(value = "/proba")
    public String getText(){
        return "PROSLOO";
    }

//    @PreAuthorize("hasRole('OWNER')")
    @PreAuthorize("@permissionEvaluator1.hasPermission(authentication,'foo', 'getOrder')")
    @GetMapping(value = "/opa")
    public String opa(){
        return "PROSLOO opaa111";
    }

    @PreAuthorize("@permissionEvaluator2.hasPermission(authentication,'bar', 'getOrder')")
    @GetMapping(value = "/opa2")
    public String opa2(){
        return "PROSLOO opaa222";
    }

//    @PreAuthorize("hasPermission('ass', 'getOrder')")
//    @GetMapping(value = "/opa3")
//    public String opa3(){
//        return "PROSLOO opaa333";
//    }
}
