package bsep.ftn.uns.ac.rs.DEVICE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping
    public String getMessage(){
        return "ZIKA!";
    }

    @GetMapping(value = "/path")
    public String getPath(){

        String contextPath = servletContext.getContextPath();
        String fullPath = servletContext.getRealPath("/");
        return "Context path: " + contextPath + "<br> Full path: " + fullPath;
    }

    @GetMapping(value = "/path2")
    public String getPath2() throws IOException {
//        Path projectPath = Paths.get(resourceLoader.getResource("").getURL().getPath()).resolve("../../..").normalize();
        return "Project path: " + Paths.get("").normalize().toAbsolutePath();
//        String fullPath = resourceLoader.getResource("").getURL().getPath();
//        return "Project path: " + fullPath;
    }

    @GetMapping(value = "/write")
    @ResponseBody
    public String writeData() throws IOException {
        String path = Paths.get("STORE/store.txt").normalize().toAbsolutePath().toString();
//        System.out.println(path.split("DEVICE")[0]);
//        System.out.println(path.split("DEVICE")[1].substring(1));
        String new_path = path.split("DEVICE")[0] + path.split("DEVICE")[1].substring(1);
//        System.out.println(path.split("DEVICE")[0]+path.split("DEVICE")[1]);
        System.out.println(new_path);
        try (FileWriter writer = new FileWriter(new_path, true)) {
            writer.write("Data to be written\n");
        }
        return "Data written successfully";
    }



}
