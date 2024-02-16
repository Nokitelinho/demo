/*
 * MavenMetadataSanitizerExtension.java Created on 02/08/22
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.jib;

import com.google.cloud.tools.jib.api.buildplan.ContainerBuildPlan;
import com.google.cloud.tools.jib.api.buildplan.FileEntriesLayer;
import com.google.cloud.tools.jib.api.buildplan.FileEntry;
import com.google.cloud.tools.jib.maven.extension.JibMavenPluginExtension;
import com.google.cloud.tools.jib.maven.extension.MavenData;
import com.google.cloud.tools.jib.plugins.extension.ExtensionLogger;
import com.google.cloud.tools.jib.plugins.extension.JibPluginExtensionException;
import org.apache.maven.plugin.logging.SystemStreamLog;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.jib.MavenMetadataSanitizer.sanitizerMavenJar;

/**
 * @author jens
 */
public class MavenMetadataSanitizerExtension implements JibMavenPluginExtension<MetadataConfiguration> {

    static final String LAYER_WITH_MAVEN_MD_REMOVAL = "layerWithMavenMatadataSanitizer";
    static final SystemStreamLog logger = new SystemStreamLog();

    @Override
    public Optional<Class<MetadataConfiguration>> getExtraConfigType() {
        return Optional.of(MetadataConfiguration.class);
    }

    @Override
    public ContainerBuildPlan extendContainerBuildPlan(ContainerBuildPlan containerBuildPlan, Map<String, String> map,
                                                       Optional<MetadataConfiguration> extraConfig, MavenData mavenData,
                                                       ExtensionLogger extensionLogger) throws JibPluginExtensionException {

        return extraConfig.filter(config -> !config.getFilters().isEmpty())
                .map(config -> extendContainerBuildPlan(config, containerBuildPlan, mavenData))
                .orElse(containerBuildPlan);
    }

    private ContainerBuildPlan extendContainerBuildPlan(MetadataConfiguration config, ContainerBuildPlan containerBuildPlan, MavenData mavenData) {
        ContainerBuildPlan.Builder planBuilder = containerBuildPlan.toBuilder().setLayers(new ArrayList<>());
        List<PathMatcher> newLayerEntryMatchers = getPathMatchersForNewLayerEntries(config);
        List<FileEntriesLayer> originalLayers = (List<FileEntriesLayer>) containerBuildPlan.getLayers();
        adjustAndAddLayersToPlan(originalLayers, newLayerEntryMatchers, planBuilder, mavenData);
        return planBuilder.build();
    }

    private List<PathMatcher> getPathMatchersForNewLayerEntries(MetadataConfiguration config) {
        return config.getFilters().stream()
                .map(filter -> FileSystems.getDefault().getPathMatcher("glob:" + filter))
                .collect(Collectors.toList());
    }

    private void adjustAndAddLayersToPlan(List<FileEntriesLayer> originalLayers, List<PathMatcher> newLayerEntryMatchers, ContainerBuildPlan.Builder planBuilder, MavenData maven) {
        List<FileEntry> layerWithModificationEntries = new ArrayList<>();

        originalLayers.forEach(layer -> {
            SeparatedLayerEntries separatedEntries = separateEntriesFrom(layer, newLayerEntryMatchers);
            addLayerToPlan(layer.getName(), separatedEntries.getRetainedLayerEntries(), planBuilder);
            layerWithModificationEntries.addAll(separatedEntries.getModifiedLayerEntries());
        });
        // layerWithModificationEntries.forEach(e -> logger.debug("Modification Entry [ source: " + e.getSourceFile() + " target: " + e.getExtractionPath()));

        Path targetTempRoot = Paths.get(maven.getMavenProject().getBuild().getDirectory(), "jib-maven-sanitizer");
        targetTempRoot.toFile().mkdirs();
        List<FileEntry> entries = layerWithModificationEntries.stream()
                .map(entry -> {
                    try {
                        return sanitizerMavenJar(entry, targetTempRoot);
                    } catch (IOException e) {
                        logger.error(e);
                        return entry;
                    }
                }).collect(Collectors.toList());
        addLayerToPlan(LAYER_WITH_MAVEN_MD_REMOVAL, entries, planBuilder);
    }

    private SeparatedLayerEntries separateEntriesFrom(FileEntriesLayer layer, List<PathMatcher> newLayerEntryMatchers) {
        List<FileEntry> retainedOriginalLayerEntries = new ArrayList<>();
        List<FileEntry> layerWithModificationEntries = new ArrayList<>();

        for (FileEntry fileEntry : layer.getEntries()) {
            boolean shouldMoveToNewLayer = newLayerEntryMatchers.stream().anyMatch(pathMatcher -> pathMatcher.matches(Paths.get(fileEntry.getExtractionPath().toString())));
            if (shouldMoveToNewLayer)
                layerWithModificationEntries.add(fileEntry);
            else
                retainedOriginalLayerEntries.add(fileEntry);
        }
        return new SeparatedLayerEntries(retainedOriginalLayerEntries, layerWithModificationEntries);
    }

    private void addLayerToPlan(String layerName, List<FileEntry> entries, ContainerBuildPlan.Builder planBuilder) {
        Optional.of(entries).filter(list -> !list.isEmpty())
                .map(list -> FileEntriesLayer.builder().setName(layerName).setEntries(list).build())
                .ifPresent(planBuilder::addLayer);
    }

    static class SeparatedLayerEntries {

        private final List<FileEntry> retainedLayerEntries;
        private final List<FileEntry> modifiedLayerEntries;

        SeparatedLayerEntries(List<FileEntry> retainedLayerEntries, List<FileEntry> modifiedLayerEntries) {
            this.retainedLayerEntries = retainedLayerEntries;
            this.modifiedLayerEntries = modifiedLayerEntries;
        }

        List<FileEntry> getRetainedLayerEntries() {
            return retainedLayerEntries;
        }

        List<FileEntry> getModifiedLayerEntries() {
            return modifiedLayerEntries;
        }
    }
}
