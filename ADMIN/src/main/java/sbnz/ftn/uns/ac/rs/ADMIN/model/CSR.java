package sbnz.ftn.uns.ac.rs.ADMIN.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "CSR")
public class CSR {

    @Id
    private String id;
//    private String username;
//    private String password;
//    private String name;
    private Long validityStart;
    private Long validityPeriod;
    private String serialNumber;
    private String commonName;
    private String organizationUnion;
    private String organizationName;
    private String localityName;
    private String stateName;
    private String country;
    private Boolean accept;

    private Boolean deleted;

    @OneToOne
    private UserDetails user;

    public void generateCSR() throws IOException, NoSuchAlgorithmException, OperatorCreationException {
        // Create a new key pair
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom();
        keyPairGenerator.initialize(2048, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Prepare the CSR subject information
        X500Name subject = new X500NameBuilder()
                .addRDN(BCStyle.CN, commonName)
                .addRDN(BCStyle.O, organizationName)
                .addRDN(BCStyle.OU, organizationUnion)
                .addRDN(BCStyle.L, localityName)
                .addRDN(BCStyle.ST, stateName)
                .addRDN(BCStyle.C, country)
                .build();

        // Create the CSR builder
        PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(subject, keyPair.getPublic());

        // Add the extension attributes
//        GeneralNames subjectAltNames = new GeneralNames(new GeneralName(GeneralName.dNSName, "example.com"));
//        p10Builder.addAttribute(PKCSObjectIdentifiers.pkcs_9_at_extensionRequest, new Extensions(new Extension[]{
//                new Extension(Extension.subjectAlternativeName, true, ASN1OctetString.getInstance(subjectAltNames))
//        }));

        // Sign the CSR using the private key
        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA").build(keyPair.getPrivate());
        PKCS10CertificationRequest csr = p10Builder.build(signer);

        // Convert the CSR to PEM format
        StringWriter strW = new StringWriter();
        JcaPEMWriter pemW = new JcaPEMWriter(strW);
        pemW.writeObject(csr);
        pemW.close();

        // Write the CSR to a file
//        String fileName = "usr1.csr"; // Change this to the desired file name
        String fileContent = strW.toString();
        Writer writer = new OutputStreamWriter(new FileOutputStream("src/main/resources/csr/" + this.getUser().getUsername()+".csr"), "UTF-8");
        writer.write(fileContent);
        writer.close();
    }

}
