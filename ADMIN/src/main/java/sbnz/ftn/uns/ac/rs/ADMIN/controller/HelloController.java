package sbnz.ftn.uns.ac.rs.ADMIN.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "hello")
public class HelloController {

    @GetMapping(value = "/hi")
    public String getHi(){
        return "HI FILIP xz!";
    }
}
