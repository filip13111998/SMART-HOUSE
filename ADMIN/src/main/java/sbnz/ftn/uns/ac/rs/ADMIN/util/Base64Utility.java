package sbnz.ftn.uns.ac.rs.ADMIN.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

@Component
public class Base64Utility {
    // Pomocna funkcija za enkodovanje bajtova u string
    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    // Pomocna funkcija za dekodovanje stringa u bajt niz
    public static byte[] decode(String base64Data) throws IOException {
        return Base64.getDecoder().decode(base64Data);
    }
}
