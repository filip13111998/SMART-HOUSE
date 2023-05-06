package sbnz.ftn.uns.ac.rs.ADMIN.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    @GetMapping(value = "/proba")
    public String getText(){
        return "ADMIN";
    }
}
