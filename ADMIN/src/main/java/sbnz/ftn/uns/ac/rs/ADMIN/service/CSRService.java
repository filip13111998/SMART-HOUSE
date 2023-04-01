package sbnz.ftn.uns.ac.rs.ADMIN.service;

import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.request.CSRRequestDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.response.CSRResponseDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.mail.EmailService;
import sbnz.ftn.uns.ac.rs.ADMIN.model.CSR;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Owner;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Tenant;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.CSRRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.OwnerRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.TenantRepository;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CSRService {

    @Autowired
    private CSRRepository csrr;

    @Autowired
    private CustomUserDetailsService cuds;

    @Autowired
    private OwnerRepository or;

    @Autowired
    private TenantRepository tr;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailService mail;

    public boolean save(CSRRequestDTO cssrdto) throws IOException, NoSuchAlgorithmException, OperatorCreationException {

        Random random = new Random();
        long randomNumber = (long) (Math.random() * 9_000_000_000_000_000L) + 1_000_000_000_000_000L;


        if(cssrdto.getRole().equals("OWNER")){
            Owner o = Owner.builder()
                    .id(UUID.randomUUID().toString())
                    .name(cssrdto.getName())
                    .password(encoder.encode(cssrdto.getPassword()))
                    .username(cssrdto.getUsername())
                    .build();

            CSR csr = CSR.builder()
                    .id(UUID.randomUUID().toString())
                    .validityStart(cssrdto.getValidityStart())
                    .validityPeriod(cssrdto.getValidityPeriod())
                    .serialNumber(randomNumber+"")
                    .commonName(cssrdto.getCommonName())
                    .organizationName(cssrdto.getOrganizationName())
                    .organizationUnion(cssrdto.getOrganizationUnion())
                    .localityName(cssrdto.getLocalityName())
                    .stateName(cssrdto.getStateName())
                    .country(cssrdto.getCountry())
                    .accept(false)
                    .deleted(false)
                    .user(o)
                    .build();
            or.save(o);
            csrr.save(csr);

            //CREATE CSR FILE

            csr.generateCSR();
        }
        else{

            Tenant t = Tenant.builder()
                    .id(UUID.randomUUID().toString())
                    .name(cssrdto.getName())
                    .password(encoder.encode(cssrdto.getPassword()))
                    .username(cssrdto.getUsername())
                    .build();

            CSR csr = CSR.builder()
                    .id(UUID.randomUUID().toString())
                    .validityStart(cssrdto.getValidityStart())
                    .validityPeriod(cssrdto.getValidityPeriod())
                    .serialNumber(randomNumber+"")
                    .commonName(cssrdto.getCommonName())
                    .organizationName(cssrdto.getOrganizationName())
                    .organizationUnion(cssrdto.getOrganizationUnion())
                    .localityName(cssrdto.getLocalityName())
                    .stateName(cssrdto.getStateName())
                    .country(cssrdto.getCountry())
                    .accept(false)
                    .deleted(false)
                    .user(t)
                    .build();
            tr.save(t);
            csrr.save(csr);

            //CREATE CSR FILE

            csr.generateCSR();
        }





        //SENT VERIFICATION MAIL:

        mail.sendEmail(
                "gavin.lehner83@ethereal.email",
                "VERIFY CSR REQUEST : " + cssrdto.getUsername() ,
                "YOUR CODE IS: " + randomNumber

        );

        System.out.println("STIGAO");

        return true;
    }

    public boolean verify(String code){
        CSR csr = csrr.findBySerialNumber(code);
        if(csr == null){
            return false;
        }
        csr.setAccept(true);
        csrr.save(csr);
        return true;
    }

    //ADMIN READ ALL ACCEPTED(VERIFIED) CERTIFICATES
    public List<CSRResponseDTO> findAll(){
        List<CSRResponseDTO> lcsrrdto= new ArrayList<>();

        lcsrrdto  = csrr.findAll().stream()
                .filter(c->c.getAccept() && !c.getDeleted())
                .map(c -> CSRResponseDTO.builder()
                        .id(c.getId())
                        .username(c.getUser().getUsername())
                        .validityStart(c.getValidityStart())
                        .validityPeriod(c.getValidityPeriod())
                        .serialNumber(c.getSerialNumber())
                        .commonName(c.getCommonName())
                        .organizationUnion(c.getOrganizationUnion())
                        .organizationName(c.getOrganizationName())
                        .localityName(c.getLocalityName())
                        .stateName(c.getStateName())
                        .country(c.getCountry())
                        .build()
                )
                .collect(Collectors.toList());

        return lcsrrdto;
    }



}
