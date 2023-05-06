package sbnz.ftn.uns.ac.rs.ADMIN.initializator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Admin;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Owner;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Tenant;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.AdminRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.OwnerRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.TenantRepository;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class DBInitializator {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AdminRepository ar;

    @Autowired
    private OwnerRepository or;

    @Autowired
    private TenantRepository tr;

    @Autowired
    private PasswordEncoder encoder;

    @PostConstruct
	public void write(){
		System.out.println("ADD USERS TO MONGO DB");

        mongoTemplate.getDb().drop();
//        mongoTemplate.getDb().createCollection("DB");

		Admin a1 = Admin.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Nikola Perkovic")
                        .password(encoder.encode("asd"))
                        .username("a1")
                        .counter(0l)
                        .pin("11")
                        .active(true)
                        .build();

        Admin a2 = Admin.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Zivko Radic")
                        .password(encoder.encode("asd"))
                        .username("a2")
                        .counter(0l)
                        .pin("22")
                        .active(true)
                        .build();

        Admin a3 = Admin.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Pavle Vekic")
                        .password(encoder.encode("asd"))
                        .username("a3")
                        .counter(0l)
                        .pin("33")
                        .active(true)
                        .build();

        Admin a4 = Admin.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Zoran Lakic")
                        .password(encoder.encode("asd"))
                        .username("a4")
                        .counter(0l)
                        .pin("44")
                        .active(true)
                        .build();

        Owner o1 = Owner.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Filip Lubic")
                        .password(encoder.encode("asd"))
                        .username("o1")
                        .counter(0l)
                        .pin("11")
                        .active(true)
                        .build();

        Owner o2 = Owner.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Zarko Lakic")
                        .password(encoder.encode("asd"))
                        .username("o2")
                        .counter(0l)
                        .pin("22")
                        .active(true)
                        .build();

        Owner o3 = Owner.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Petar Lakic")
                        .password(encoder.encode("asd"))
                        .username("o3")
                        .counter(0l)
                        .pin("33")
                        .active(true)
                        .build();

        Tenant t1 = Tenant.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Djordje Lakic")
                        .password(encoder.encode("asd"))
                        .username("t1")
                        .counter(0l)
                        .pin("11")
                        .active(true)
                        .build();

        Tenant t2 = Tenant.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Ognjen Vasic")
                        .password(encoder.encode("asd"))
                        .username("t2")
                        .counter(0l)
                        .pin("22")
                        .active(true)
                        .build();

        Tenant t3 = Tenant.builder()
                .id(UUID.randomUUID().toString())
                .name("Zarko Lakic")
                .password(encoder.encode("asd"))
                .username("o2")
                .counter(0l)
                .pin("22")
                .active(true)
                .build();

//        System.out.println(a.getId());
		ar.save(a1);
        ar.save(a2);
        ar.save(a3);
        ar.save(a4);

        or.save(o1);
        or.save(o2);
        or.save(o3);


        tr.save(t1);
        tr.save(t2);
        tr.save(t3);

	}

}
