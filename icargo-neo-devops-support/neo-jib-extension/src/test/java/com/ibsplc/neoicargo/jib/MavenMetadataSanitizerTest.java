package com.ibsplc.neoicargo.jib;


import com.google.cloud.tools.jib.api.buildplan.FileEntry;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class MavenMetadataSanitizerTest {

    //@Test
    void testJarSanitize() throws IOException {
        // basic
        test_sanitizer(Paths.get("/home/jens/.m2/repository/jakarta/activation/jakarta.activation-api/1.2.2/jakarta.activation-api-1.2.2.jar"));
        // with signature
        test_sanitizer(Paths.get("/home/jens/.m2/repository/org/codehaus/janino/janino/3.1.7/janino-3.1.7.jar"));
        test_sanitizer(Paths.get("/home/jens/.m2/repository/org/springframework/boot/spring-boot-starter-web/2.5.4/spring-boot-starter-web-2.5.4.jar"));
    }


    void test_sanitizer(Path path) throws IOException{
        FileEntry entry = new FileEntry(path, null, null, null);
        Path targetRoot = Paths.get("/home/jens/tmp", "jib-customizer");
        targetRoot.toFile().mkdirs();
        MavenMetadataSanitizer.sanitizerMavenJar(entry, targetRoot);
    }
}