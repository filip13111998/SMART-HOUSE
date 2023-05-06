package sbnz.ftn.uns.ac.rs.ADMIN.passwordReader;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class PasswordReader {

    private final ResourceLoader resourceLoader;

    private List<String> passwords;

    public PasswordReader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:passwords.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            passwords = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                passwords.add(line);
            }
        }
    }

    public List<String> getPasswords() {
        return passwords;
    }

}
