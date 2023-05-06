package sbnz.ftn.uns.ac.rs.ADMIN.service;

import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.request.CSRRequestDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.dto.response.CSRResponseDTO;
import sbnz.ftn.uns.ac.rs.ADMIN.initializator.KeyStoreInitializer;
import sbnz.ftn.uns.ac.rs.ADMIN.mail.EmailService;
import sbnz.ftn.uns.ac.rs.ADMIN.model.CSR;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Certificate;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Owner;
import sbnz.ftn.uns.ac.rs.ADMIN.model.Tenant;
import sbnz.ftn.uns.ac.rs.ADMIN.passwordReader.PasswordReader;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.CSRRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.CertificateRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.OwnerRepository;
import sbnz.ftn.uns.ac.rs.ADMIN.repository.TenantRepository;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.*;
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
    private CertificateRepository cr;

    @Autowired
    private KeyStoreInitializer ksi;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailService mail;

    @Autowired
    private PasswordReader passwordReader;

    public boolean save(CSRRequestDTO cssrdto) throws IOException, NoSuchAlgorithmException, OperatorCreationException {

        Random random = new Random();
        long randomNumber = (long) (Math.random() * 9_000_000_000_000_000L) + 1_000_000_000_000_000L;

        if(cssrdto.getPassword().length() < 7 || !cssrdto.getPassword().matches(".*\\d+.*") || !cssrdto.getPassword().matches(".*[A-Z]+.*")
            ||  !cssrdto.getPassword().matches(".*[a-z]+.*") || !cssrdto.getPassword().matches(".*[^a-zA-Z0-9]+.*")){
            System.out.println("WRONG USERNAME!");
            return false;
        }

        if(passwordReader.getPasswords().contains(cssrdto.getPassword())){
            System.out.println("WEAK PASSWORD!");
            return false;
        }

        System.out.println("GOOD!");
//        return true;

        if(cssrdto.getRole().equals("OWNER")){
            Owner o = Owner.builder()
                    .id(UUID.randomUUID().toString())
                    .name(cssrdto.getName())
                    .password(encoder.encode(cssrdto.getPassword()))
                    .username(cssrdto.getUsername())
                    .pin(cssrdto.getPin())
                    .counter(0l)
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
                    .template(cssrdto.getTemplate())
                    .extensions(cssrdto.getExtensions())
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
                    .pin(cssrdto.getPin())
                    .counter(0l)
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
                    .template(cssrdto.getTemplate())
                    .extensions(cssrdto.getExtensions())
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

        return true;
    }

    public boolean verify(String code){
        System.out.println("VERIFYYY");
        CSR csr = csrr.findBySerialNumber(code);
        if(csr == null){
            return false;
        }
        csr.setAccept(true);
        csrr.save(csr);
        Owner owner = or.findByUsername(csr.getUser().getUsername());
        if(owner != null){
            owner.setActive(true);
            or.save(owner);
            return true;
        }
        Tenant tenant = tr.findByUsername(csr.getUser().getUsername());
        if(tenant != null){
            tenant.setActive(true);
            tr.save(tenant);
            return true;
        }
        return false;
    }

    //ADMIN VERIFY ALL CSR
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
                        .template(c.getTemplate())
                        .build()
                )
                .collect(Collectors.toList());

        return lcsrrdto;
    }

    //ADMIN ACCEPT CSR
    public Boolean accept(String uuid) throws Exception {
        List<CSRResponseDTO> lcsrrdto= new ArrayList<>();

        CSR csr  = csrr.findAll().stream()
                .filter(c->c.getId().equals(uuid))
                .findFirst()
                .get();

        csr.setDeleted(true);

        csrr.save(csr);

        //MAYBE I NEED TO DELETE CSR FILE IN RESOURE FOLDER
        //WRITE CODE THIS

        //CREATE CERTIFICATE AND PUT THEM IN MONGO DB
        Certificate certificate = Certificate.builder()
                .id(UUID.randomUUID().toString())
                .validityStart(csr.getValidityStart())
                .validityPeriod(csr.getValidityPeriod())
                .issuer("myrootca")
                .subject(csr.getUser().getUsername())
                .template(csr.getTemplate())
                .delete(false)
                .revoke(false)
                .build();

        cr.save(certificate);

        //TODO:
        //generate keypair and put certificate x509 and private key to keystore.p12
        //issuer is myrootca and subject is csr.getUser().getUsername()
        //alias is csr.getUser().getUsername()

        //PUT CERTIFICATE INTO KEYSTORE
        if(csr.getTemplate().equals("SSL Client")){
            ksi.createSSLClientCerificate(csr.getUser().getUsername(), Arrays.asList(csr.getExtensions().split("\\|")));
        }
        else if(csr.getTemplate().equals("CA")){
//            System.out.println("PRE GRESKE");
            ksi.createCACertificate(csr.getUser().getUsername(), Arrays.asList(csr.getExtensions().split("\\|")));
        }

        return true;
    }

    //ADMIN DELETE CSR
    public Boolean delete(String uuid){
        List<CSRResponseDTO> lcsrrdto= new ArrayList<>();

        CSR csr  = csrr.findAll().stream()
                .filter(c->c.getId().equals(uuid))
                .findFirst()
                .get();

        csr.setDeleted(true);

        csrr.save(csr);

        //MAYBE I NEED TO DELETE CSR FILE IN RESOURE FOLDER
        //WRITE CODE THIS


        return true;
    }

}
