package sbnz.ftn.uns.ac.rs.ADMIN.initializator;

import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

@Component
public class KeyStoreInitializer {

    private static final String KEYSTORE_PASSWORD = "cacert";
    private static final String TRUSTSTORE_PASSWORD = "usercert";
    //src/main/resources/csr/
    private static final String KEYSTORE_PATH = "src/main/resources/store/keystore.p12";
    private static final String TRUSTSTORE_PATH = "src/main/resources/store/truststore.p12";

    private static final String ROOT_CA_ALIAS = "myrootca";

    private File keystoreFile;

    private File truststoreFile;

    public KeyStore keystore;

    static {
        Security.addProvider(new BouncyCastleProvider());

    }
//    public KeyStoreInitializer() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
//        KeyStore keystore = KeyStore.getInstance("PKCS12");
//        keystore.load(null, null);
//    }


    @PostConstruct
    public void init() throws Exception {
//        File keystoreFile = new File(KEYSTORE_PATH);
//        File truststoreFile = new File(TRUSTSTORE_PATH);

        // Delete the keystore and truststore files if they exist
        if (keystoreFile != null) {
            if(keystoreFile.exists()){
                keystoreFile.delete();
            }
        }
        if (truststoreFile != null) {
            //truststoreFile.exists()
            truststoreFile.delete();
        }
        keystoreFile = new File(KEYSTORE_PATH);
        keystoreFile.createNewFile();

        truststoreFile = new File(TRUSTSTORE_PATH);
        truststoreFile.createNewFile();

        // Create a new key pair for the root CA certificate
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Create a self-signed root CA certificate

        X509Certificate rootCert = this.createRootCA("CN=myrootca",365, keyPair);

//        // Create a new empty keystore
//        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore = KeyStore.getInstance("PKCS12");
        keystore.load(null, null);

        // Add the key pair and root CA certificate to the keystore
        keystore.setKeyEntry(ROOT_CA_ALIAS, keyPair.getPrivate(), KEYSTORE_PASSWORD.toCharArray(), new X509Certificate[]{rootCert});

        // Save the keystore to a file
        keystore.store(new FileOutputStream(keystoreFile), KEYSTORE_PASSWORD.toCharArray());

        // Create a new empty truststore
        KeyStore truststore = KeyStore.getInstance("PKCS12");
        truststore.load(null, null);

        // Add the root CA certificate to the truststore
        truststore.setCertificateEntry(ROOT_CA_ALIAS, rootCert);

        // Save the truststore to a file
        truststore.store(new FileOutputStream(truststoreFile), TRUSTSTORE_PASSWORD.toCharArray());
    }


    public X509Certificate createRootCA(String dn, int validDays, KeyPair keyPair) throws Exception {
        X500Principal subjectName = new X500Principal(dn);

        // Set the validity period of the certificate
        Date notBefore = new Date();
        Date notAfter = new Date(System.currentTimeMillis() + validDays * 24 * 60 * 60 * 1000L);

        // Generate the certificate serial number
        BigInteger serialNumber = BigInteger.valueOf(new SecureRandom().nextInt(Integer.MAX_VALUE));

        // Initialize the certificate builder
        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                subjectName,
                serialNumber,
                notBefore,
                notAfter,
                subjectName,
                keyPair.getPublic()
        );

        // Add basic constraints to mark the certificate as a CA
        BasicConstraints basicConstraints = new BasicConstraints(true);
        certBuilder.addExtension(Extension.basicConstraints, true, basicConstraints);

        // Add key usage constraints
        KeyUsage keyUsage = new KeyUsage(KeyUsage.keyCertSign | KeyUsage.digitalSignature);
        certBuilder.addExtension(Extension.keyUsage, true, keyUsage);

        // Sign the certificate with the private key of the root CA
        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption").build(keyPair.getPrivate());
        X509CertificateHolder certHolder = certBuilder.build(signer);
        X509Certificate cert = new JcaX509CertificateConverter().getCertificate(certHolder);

        return cert;
    }

    public X509Certificate createEndEntityCertificate(String subject,String issuer, int validDays, KeyPair keyPair) throws Exception {
        X500Principal issuerName = new X500Principal(issuer);
        X500Principal subjectName = new X500Principal(subject);
        // Set the validity period of the certificate
        Date notBefore = new Date();
        Date notAfter = new Date(System.currentTimeMillis() + validDays * 24 * 60 * 60 * 1000L);

        // Generate the certificate serial number
        BigInteger serialNumber = BigInteger.valueOf(new SecureRandom().nextInt(Integer.MAX_VALUE));

        // Initialize the certificate builder
        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                issuerName,
                serialNumber,
                notBefore,
                notAfter,
                subjectName,
                keyPair.getPublic()
        );

        // Add basic constraints to mark the certificate as a CA
//        BasicConstraints basicConstraints = new BasicConstraints(true);
//        certBuilder.addExtension(Extension.basicConstraints, true, basicConstraints);

        // Add key usage constraints
//        KeyUsage keyUsage = new KeyUsage(KeyUsage.keyCertSign | KeyUsage.digitalSignature);
//        certBuilder.addExtension(Extension.keyUsage, true, keyUsage);

        // Sign the certificate with the private key of the root CA
        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSAEncryption").build(keyPair.getPrivate());
        X509CertificateHolder certHolder = certBuilder.build(signer);
        X509Certificate cert = new JcaX509CertificateConverter().getCertificate(certHolder);

        return cert;
    }

    public void createUserCerificate(String username) throws Exception {
        // Create a new key pair for the root CA certificate
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair myKeyPair = keyPairGenerator.generateKeyPair();

        // Create a self-signed root CA certificate

        X509Certificate rootCert = this.createEndEntityCertificate("CN="+username,"CN=myrootca",365, myKeyPair);

//        // Create a new empty keystore
//        KeyStore keystore = KeyStore.getInstance("PKCS12");
//        keystore.load(null, null);

        // Add the key pair and root CA certificate to the keystore
//        keystore.setKeyEntry(ROOT_CA_ALIAS, keyPair.getPrivate(), KEYSTORE_PASSWORD.toCharArray(), new X509Certificate[]{rootCert});
        keystore.setKeyEntry(username, myKeyPair.getPrivate(), KEYSTORE_PASSWORD.toCharArray(), new X509Certificate[]{rootCert});


        // Save the keystore to a file
        keystore.store(new FileOutputStream(keystoreFile), KEYSTORE_PASSWORD.toCharArray());

    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

}
