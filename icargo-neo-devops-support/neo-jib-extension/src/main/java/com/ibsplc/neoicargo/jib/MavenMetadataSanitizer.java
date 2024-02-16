/*
 * MavenMetadataSanitizer.java Created on 03/08/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.jib;

import com.google.cloud.tools.jib.api.buildplan.FileEntry;
import org.apache.maven.plugin.logging.SystemStreamLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author jens
 */
class MavenMetadataSanitizer {

    static final SystemStreamLog logger = new SystemStreamLog();
    static final Map<String, String> FILTERED_MANIFEST_ENTRIES;

    static {
        Map<String, String> map = new HashMap<>(8);
        map.put("Specification-Title", "IBS iCargo");
        map.put("Specification-Version", "1.0");
        map.put("Implementation-Title", "IBS iCargo");
        //map.put("Implementation-Version", "1.0");
        map.put("Implementation-Vendor", "IBS");
        FILTERED_MANIFEST_ENTRIES = map;
    }

    static FileEntry sanitizerMavenJar(FileEntry source, Path targetDir) throws IOException {
        Map<String, String> zipProperties = Collections.singletonMap("create", "false");
        Path targetPath = Paths.get(targetDir.toString(), source.getSourceFile().getFileName().toString());
        Files.copy(source.getSourceFile(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        URI jarUri = URI.create("jar:" + targetPath.toUri());
        try (FileSystem jarFs = FileSystems.newFileSystem(jarUri, zipProperties)) {
            deleteMavenDirectory(jarFs);
            removeManifestEntries(jarFs);
            deleteSignatures(jarFs);
        }
        // return the modified entry
        return new FileEntry(targetPath, source.getExtractionPath(), source.getPermissions(), source.getModificationTime());
    }

    static void deleteMavenDirectory(FileSystem jarFs) throws IOException {
        Path mavenDir = jarFs.getPath("META-INF", "maven");
        if (Files.exists(mavenDir, LinkOption.NOFOLLOW_LINKS)) {
            try (Stream<Path> paths = Files.walk(mavenDir, FileVisitOption.FOLLOW_LINKS)) {
                List<Path> mavenSubRes = paths.collect(Collectors.toList());
                Collections.reverse(mavenSubRes);
                mavenSubRes.forEach(p -> {
                    //logger.debug("Deleting [ " + jarFs + " ] - " + p);
                    try {
                        Files.delete(p);
                    } catch (IOException e) {
                        logger.error(e);
                    }
                });
            }
        }
    }

    static void removeManifestEntries(FileSystem jarFs) throws IOException {
        Path manifestPath = jarFs.getPath("META-INF", "MANIFEST.MF");
        if (Files.exists(manifestPath, LinkOption.NOFOLLOW_LINKS)) {
            Manifest manifest;
            try (InputStream is = Files.newInputStream(manifestPath)) {
                manifest = new Manifest(is);
            }
            Attributes mainAttributes = manifest.getMainAttributes();
            FILTERED_MANIFEST_ENTRIES.forEach(mainAttributes::putValue);
            manifest.getEntries().entrySet().removeIf(MavenMetadataSanitizer::isSignatureEntry);
            try (OutputStream os = Files.newOutputStream(manifestPath, StandardOpenOption.TRUNCATE_EXISTING)) {
                manifest.write(os);
            }
        }
    }

    static void deleteSignatures(FileSystem jarFs) throws IOException {
        Path mavenDir = jarFs.getPath("META-INF");
        if (Files.exists(mavenDir, LinkOption.NOFOLLOW_LINKS)) {
            try (Stream<Path> paths = Files.walk(mavenDir, FileVisitOption.FOLLOW_LINKS)) {
                List<Path> metaInfPaths = paths.collect(Collectors.toList());
                Collections.reverse(metaInfPaths);
                metaInfPaths.forEach(p -> {
                    String fileExtension = p.getFileName().toString();
                    if (fileExtension.endsWith(".DSA") || fileExtension.endsWith(".SF") || fileExtension.endsWith(".RSA") || fileExtension.endsWith(".EC")) {
                        try {
                            Files.delete(p);
                        } catch (IOException e) {
                            logger.error(e);
                        }
                    }
                });
            }
        }
    }

    static boolean isSignatureEntry(Map.Entry<String, Attributes> entry) {
        Attributes attrs = entry.getValue();
        return attrs.entrySet().stream().anyMatch(e -> e.getKey().toString().endsWith("-Digest"));
    }

}
