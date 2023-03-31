package sbnz.ftn.uns.ac.rs.ADMIN;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Admin;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.AdminRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}


}
