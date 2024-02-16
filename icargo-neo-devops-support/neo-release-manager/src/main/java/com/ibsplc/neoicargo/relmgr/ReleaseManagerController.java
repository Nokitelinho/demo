/*
 * ReleaseManagerController.java Created on 04/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibsplc.neoicargo.relmgr.dao.*;
import com.ibsplc.neoicargo.relmgr.entity.*;
import com.ibsplc.neoicargo.relmgr.model.*;
import com.vladmihalcea.hibernate.type.json.internal.JacksonUtil;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * @author jens
 */
@Component
public class ReleaseManagerController {

    public static final String MASTER_BRANCH = "master";
    protected static final String DEFAULT_TENANT = "base";
    static final Logger logger = LoggerFactory.getLogger(ReleaseManagerController.class);

    private final ArtifactMasterRepository artifactMasterRepository;
    private final ReleaseCatalogueRepository releaseCatalogueRepository;
    private final TenantCatalogueRepository tenantCatalogueRepository;
    private final ReleaseManagerSqlDao releaseManagerSqlDao;
    private final BitbucketRepository bitbucketRepository;
    private final ApplicationCatalogueRepository applicationCatalogueRepository;
    private final ReleasePackageRepository releasePackageRepository;
    private final SystemParameterRepository systemParameterRepository;
    private final BuildCatalogueRepository buildCatalogueRepository;
    private final TenantMasterRepository tenantMasterRepository;
    private final ArtifactTestMasterRepository artifactTestMasterRepository;
    private final ObjectMapper objectMapper;


    @Autowired
    public ReleaseManagerController(ArtifactMasterRepository artifactMasterRepository, ReleaseCatalogueRepository releaseCatalogueRepository,
                                    TenantCatalogueRepository tenantCatalogueRepository, ReleaseManagerSqlDao releaseManagerSqlDao, BitbucketRepository bitbucketRepository,
                                    ApplicationCatalogueRepository applicationCatalogueRepository, ReleasePackageRepository releasePackageRepository,
                                    SystemParameterRepository systemParameterRepository, BuildCatalogueRepository buildCatalogueRepository, TenantMasterRepository tenantMasterRepository,
                                    ObjectMapper objectMapper, ArtifactTestMasterRepository artifactTestMasterRepository) {
        this.artifactMasterRepository = artifactMasterRepository;
        this.releaseCatalogueRepository = releaseCatalogueRepository;
        this.tenantCatalogueRepository = tenantCatalogueRepository;
        this.releaseManagerSqlDao = releaseManagerSqlDao;
        this.bitbucketRepository = bitbucketRepository;
        this.applicationCatalogueRepository = applicationCatalogueRepository;
        this.releasePackageRepository = releasePackageRepository;
        this.systemParameterRepository = systemParameterRepository;
        this.buildCatalogueRepository = buildCatalogueRepository;
        this.tenantMasterRepository = tenantMasterRepository;
        this.artifactTestMasterRepository = artifactTestMasterRepository;
        this.objectMapper = objectMapper;
    }

    static void updatePortsInDeploymentGroups(List<DeploymentUnit> units) {
        Map<String, List<DeploymentUnit>> groupedUnits = units.stream().collect(Collectors.groupingBy(DeploymentUnit::getDeploymentGroup));
        for (Map.Entry<String, List<DeploymentUnit>> groupUnit : groupedUnits.entrySet()) {
            // dont update port for default group
            if (DeploymentUnit.DEFAULT_DEP_GROUP.equals(groupUnit.getKey()))
                continue;
            AtomicInteger portIncr = new AtomicInteger(8000);
            groupUnit.getValue().stream().forEach(u -> u.setPort(portIncr.getAndIncrement()));
        }
    }

    static Set<String> deploymentGroups(List<DeploymentUnit> units) {
        return units.stream().map(DeploymentUnit::getDeploymentGroup)
                .filter(s -> !s.equals(DeploymentUnit.DEFAULT_DEP_GROUP)).collect(Collectors.toSet());
    }

    public Optional<DeploymentBom> findTenantDeploymentBom(String tenantId, String applicationId, @Nullable String branch) {
        List<DeploymentUnit> units = this.releaseManagerSqlDao.findTenantDeploymentBom(tenantId, applicationId, branch);
        logger.info("Creating deployment bom for tenant {} application {} artifact count {} ...", tenantId, applicationId, units.size());
        //Filter for "master" branch alone
        if (!CollectionUtils.isEmpty(units) && !StringUtils.isEmpty(branch)) {
            units = units.stream().filter(item -> branch.equalsIgnoreCase(item.getBranch())).collect(Collectors.toList());
        }
        if (units.isEmpty())
            return Optional.empty();
        DeploymentBom bom = new DeploymentBom();
        units.forEach(u -> bom.getDeployments().put(u.getArtifactId(), u));
        bom.setApplicationId(applicationId);
        bom.setTenantId(tenantId);
        bom.setVersionTimestamp(ZonedDateTime.now(ZoneId.of("UTC")));
        bom.setDeploymentGroups(deploymentGroups(units));
        updatePortsInDeploymentGroups(units);
        return Optional.of(bom);
    }

    public Optional<List<ActiveTask>> findActive(String tenantId, String applicationId) {
        List<ActiveTask> units = this.releaseManagerSqlDao.findAggregatedJiraId(tenantId, applicationId);
        logger.info("Creating JIRA LIST");
        if (units.isEmpty())
            return Optional.empty();
        return Optional.of(units);
    }

    @Transactional
    public Optional<DeploymentBom> getBomForApplication(String tenantId, String applicationId, String env) throws WebApplicationException {
        logger.info("Creating the Bom For the Application for  tenant {} application {} env {}  : ", tenantId, applicationId, env);
        JiraIdResponse jd = new JiraIdResponse();
        List<String> betList = releaseManagerSqlDao.getBomForDeployment(applicationId, tenantId, env);
        Optional<DeploymentBom> yaml = getDeploymentBOMForABuildAsYaml(betList.get(0));
        return yaml;
    }

    public Optional<JiraIdResponse> findActiveRefernces(String tenantId, String applicationId, String currentVersion, String newVersion) throws WebApplicationException {
        Pair<String, Integer> currentBuildAndRelease = getBuildAndRelease(currentVersion, applicationId);
        Pair<String, Integer> newBuildAndRelease = getBuildAndRelease(newVersion, applicationId);
        List<String> values = this.releaseManagerSqlDao.findAggregatedJiraIdWithVersion(tenantId, applicationId, currentBuildAndRelease, newBuildAndRelease);
        Map<String, Set<String>> listofCommits = new HashMap<String, Set<String>>();
        List<DeploymentUnit> units1 = new ArrayList<DeploymentUnit>();
        values.stream().forEach(value -> {
            try {
                List<DeploymentUnit> units = new ArrayList<DeploymentUnit>();
                JsonNode actualObj = objectMapper.readTree(value);
                units = toDeploymentUnits(actualObj);
                units.stream().forEach(deplymentUnit -> {
                    if (null != listofCommits.get(deplymentUnit.getArtifactId())) {
                        listofCommits.get(deplymentUnit.getArtifactId()).add(deplymentUnit.getArtifactVersion());
                    } else {
                        listofCommits.put(deplymentUnit.getArtifactId(), new HashSet<>(Arrays.asList(deplymentUnit.getArtifactVersion())));
                    }
                });

                for (DeploymentUnit deplymentUnit : units) {
                    if (null != listofCommits.get(deplymentUnit.getArtifactId())) {
                        listofCommits.get(deplymentUnit.getArtifactId()).add(deplymentUnit.getArtifactVersion());
                    } else {
                        listofCommits.put(deplymentUnit.getArtifactId(), new HashSet<>(Arrays.asList(deplymentUnit.getArtifactVersion())));
                    }
                }
            } catch (JsonProcessingException e) {
            }
        });
        JiraIdResponse listOfActiveJira = this.releaseManagerSqlDao.findActiveJiraList(listofCommits);
        logger.info("Creating JIRA LIST");
        return Optional.of(listOfActiveJira);
    }

    private Pair<String, Integer> getBuildAndRelease(String bldNum, String appIdr) {
        int seperator = bldNum.lastIndexOf(".");
        if (seperator > 0) {
            return Pair.of(bldNum.substring(0, seperator), Integer.parseInt(bldNum.substring(seperator + 1)));
        } else {
            return Pair.of(appIdr, Integer.parseInt(bldNum));
        }
    }

    public void upsertArtifactRelease(ArtifactRelease release) {
        release.setReleaseStatus("ALPHA");
        if (release.getReleaseTime() == null)
            release.setReleaseTime(ZonedDateTime.now(ZoneOffset.UTC));
        ReleaseCatalogue catalogue = Mapper.toReleaseCatalogue(release);
        try {
            this.bitbucketRepository.primeReleaseCatalogue(catalogue);
        } catch (Exception e) {
            logger.warn("Error occurred while adding commit metadata", e);
        }
        this.releaseCatalogueRepository.save(catalogue);
    }

    public Optional<Application> findApplicationById(String tenantId, String applicationId) {
        var pk = new ApplicationMaster.PK(tenantId, applicationId);

        return applicationCatalogueRepository.findById(pk).map(master -> {
            var app = new Application();
            app.setApplicationId(applicationId);
            app.setTenantId(tenantId);
            app.setApplicationDesc(master.getApplicationDesc());
            app.setReleaseNumber(master.getReleaseNumber());
            return app;
        });
    }

    @Transactional
    public void upsertApplicationWithTenantCatalogue(Application application) {
        var pk = new ApplicationMaster.PK(application.getTenantId(), application.getApplicationId());
        applicationCatalogueRepository.findById(pk).ifPresent(master -> {
            applicationCatalogueRepository.deleteById(pk);
            releaseManagerSqlDao
                    .findTenantCatalogueForApplication(application.getTenantId(), application.getApplicationId())
                    .stream().map(art -> new TenantCatalogue.PK(application.getTenantId(), art.getArtifactId(), application.getApplicationId()))
                    .forEach(tenantCatalogueRepository::deleteById);

        });
        var master = new ApplicationMaster();
        master.setApplicationMasterPK(pk);
        master.setApplicationDesc(application.getApplicationDesc());
        master.setReleaseNumber(application.getReleaseNumber());
        applicationCatalogueRepository.save(master);

        var artIds = application.getArtifactIds();
        if (Objects.nonNull(artIds)) {
            artIds.stream().forEach(artId -> upsertTenantCatalogue(application.getTenantId(), application.getApplicationId(), artId));
        }
    }

    @Transactional
    public void upsertTenantCatalogue(String tenantId,
                                      String applicationId,
                                      String artifactId) {

        var pk = new TenantCatalogue.PK();
        pk.setApplicationId(applicationId);
        pk.setArtifactId(artifactId);
        pk.setTenantId(tenantId);
        tenantCatalogueRepository.findById(pk).ifPresent((t) -> tenantCatalogueRepository.deleteById(pk));
        TenantCatalogue catalogue = new TenantCatalogue();
        catalogue.setTenantCataloguePk(pk);
        tenantCatalogueRepository.save(catalogue);
    }

    private Optional<DeploymentBom> getDeploymentBom(String tenantId, String applicationId, List<DeploymentUnit> units) {
        if (!CollectionUtils.isEmpty(units)) {
            logger.info("Creating deployment bom for tenant {} application {} artifact count {} ...", tenantId, applicationId, units.size());
            DeploymentBom bom = new DeploymentBom();
            units.forEach(u -> bom.getDeployments().put(u.getArtifactId(), u));
            bom.setApplicationId(applicationId);
            bom.setTenantId(tenantId);
            bom.setVersionTimestamp(ZonedDateTime.now(ZoneId.of("UTC")));
            return Optional.of(bom);
        }
        return Optional.empty();
    }

    public Optional<DeploymentBom> findArtefactReleaseBom(String tenantId, String artfactId, String artver) {
        var units = releaseManagerSqlDao.findArtefactReleaseDeplUnit(artfactId, artver);
        return getDeploymentBom(tenantId, "NA", units);
    }

    @Transactional
    public Optional<DeploymentBom> findDeploymentBomForReleasePackage(String tenantId, long packageId) {
        return releasePackageRepository.findById(packageId).flatMap(releasePackage -> {
            Optional<DeploymentBom> result = Optional.empty();
            var tenantCtg = releaseManagerSqlDao.findTenantCatalogueForApplication(tenantId, releasePackage.getApplicationId());
            if (!CollectionUtils.isEmpty(tenantCtg)) {
                //Find bom for build and filter by artifact ids
                Optional<JsonNode> buildBom = getDeploymentBOMForABuild(releasePackage.getBldnum());
                var artlist = tenantCtg.stream().map(Artifact::getArtifactId).collect(Collectors.toSet());
                var units = buildBom.map(jsonNode -> toDeploymentUnits(jsonNode)).orElse(null);
                if (!CollectionUtils.isEmpty(units)) {
                    var appUnitsForBuild = units.stream().filter(unit -> artlist.contains(unit.getArtifactId())).collect(Collectors.toList());
                    result = getDeploymentBom(tenantId, releasePackage.getApplicationId(), appUnitsForBuild);
                }
            }
            return result;
        });
    }

    private List<DeploymentUnit> toDeploymentUnits(JsonNode jsonNode) {
        DeploymentUnit[] unitArray = null;
        try {
            unitArray = this.objectMapper.treeToValue(jsonNode, DeploymentUnit[].class);
        } catch (JsonProcessingException e) {
            logger.error("Failed processing json of {}", jsonNode);
            throw new WebApplicationException(e);
        }
        return ObjectUtils.isEmpty(unitArray) ? null : Arrays.asList(unitArray);
    }

    //Can have builds specific for an application Hence accepts app & tenant ids & buildNum
    @Transactional
    @SneakyThrows
    public String saveBuildCatalogue(@Nullable String tenantId, @Nullable String applicationId, long buildNum, String relNum, Collection<DeploymentUnit> deploymentUnits) {
        var catalogue = new BuildCatalogue();
        BuildCatalogue.PK pk = new BuildCatalogue.PK(buildNum, relNum);
        catalogue.setBuildCatalogPK(pk);
        catalogue.setApplicationIdr(applicationId);
        catalogue.setTenantId(tenantId);
        catalogue.setBuildStatus(BuildCatalogue.BuildStatus.InProgress);
        catalogue.setBuildQuality(BuildCatalogue.BuildQuality.Alpha);
        catalogue.setBuildStartTime(ZonedDateTime.now(ZoneOffset.UTC));
        //We convert to json and parse it again so as to be able to use our own objectMapper
        catalogue.setDeploymentCatalogue(JacksonUtil.toJsonNode(objectMapper.writeValueAsString(deploymentUnits)));
        buildCatalogueRepository.save(catalogue);
        return catalogue.getBldnum();
    }

    private Optional<BuildCatalogue> findById(String buildNum) {
        int seperator = buildNum.lastIndexOf('.');
        String relNum = buildNum.substring(0, seperator);
        long buildNumber = Long.parseLong(buildNum.substring(seperator + 1));
        Optional<BuildCatalogue> cat = buildCatalogueRepository.findById(new BuildCatalogue.PK(buildNumber, relNum));
        return cat;
    }

    @Transactional
    public boolean changeBuildStatus(String buildNum, BuildCatalogue.BuildStatus buildStatus) {
        return findById(buildNum).map(buildCatalogue -> {
            buildCatalogue.setBuildStatus(buildStatus);
            buildCatalogueRepository.save(buildCatalogue);
            return true;
        }).orElse(Boolean.FALSE);
    }

    @Transactional
    public boolean changeBuildQuality(String buildNum, BuildCatalogue.BuildQuality buildQuality) {
        return findById(buildNum).map(buildCatalogue -> {
            buildCatalogue.setBuildQuality(buildQuality);
            buildCatalogueRepository.save(buildCatalogue);
            return true;
        }).orElse(Boolean.FALSE);
    }

    public List<DeploymentUnit> findAllLatestReleases(@Nullable String artefactId) {
        return releaseManagerSqlDao.findAllLatestReleases(artefactId);
    }

    public List<DeploymentUnit> findAllLatestReleasesForBranch(String branch) {
        var result = releaseManagerSqlDao.findAllLatestReleases(null);
        if (!CollectionUtils.isEmpty(result)) {
            result = result.stream().filter(item -> branch.equalsIgnoreCase(item.getBranch())).collect(Collectors.toList());
        }
        return result;
    }

    private Optional<DeploymentUnit> existsWithId(List<DeploymentUnit> units, DeploymentUnit unit) {
        return units.stream().filter(u -> u.getArtifactId().equalsIgnoreCase(unit.getArtifactId())).findFirst();
    }

    @Transactional
    public String cloneABuildCatalogue(String buildnum, List<ArtifactRelease> artifactReleases) {
        logger.info("cloneABuildCatalogue from {} with artfact releases {}", buildnum, artifactReleases);
        return findById(buildnum).map(bc -> {
            long patchbuildNum = -1L;
            var units = artifactReleases.stream().
                    flatMap(ar -> releaseManagerSqlDao.findArtefactReleaseDeplUnit(ar.getArtifactId(), ar.getArtifactVersion()).stream())
                    .collect(Collectors.toList());
            logger.debug("Found deployment units for artifactReleases as {}", units);
            var bcUnits = toDeploymentUnits(bc.getDeploymentCatalogue());
            logger.debug("Found deployment units for build catalogue {}  as {}", buildnum, bcUnits);
            //Merge deployment units from build catalogue with new deployment units specified
            if (!CollectionUtils.isEmpty(bcUnits)) {
                var mergedUnits = bcUnits.stream().map(unit -> existsWithId(units, unit).orElse(unit)).collect(Collectors.toList());
                patchbuildNum = releaseManagerSqlDao.findNextAvailablePatchBuildNum(bc.getBuildCatalogPK().getBldnum(), bc.getBuildCatalogPK().getRelNum());
                logger.debug("Patch build number generated  as {}", patchbuildNum);
                if (patchbuildNum > 0) {
                    saveBuildCatalogue(bc.getTenantId(), bc.getApplicationIdr(), patchbuildNum, bc.getBuildCatalogPK().getRelNum(), mergedUnits);
                }
            }

            return bc.getBldnum();
        }).orElse("");

    }

    @Transactional
    public String generateBuildCatalogue(@Nullable String tenantId, @Nullable String applicationId) {
        //Find latest releases from master branch
        Collection<DeploymentUnit> units = null;
        String relNum = "icargo-neo";
        if (!(Objects.isNull(applicationId) && Objects.isNull(tenantId))) {
            var app = findApplicationById(tenantId, applicationId).orElseThrow(() -> new BadRequestException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Application " + applicationId + " does not exist").build()));

            relNum = app.getReleaseNumber();
            units = this.findTenantDeploymentBom(tenantId, applicationId, MASTER_BRANCH).map(bom -> bom.getDeployments().values()).orElse(null);
            var artifactsInApp = this.releaseManagerSqlDao.findTenantCatalogueForApplication(tenantId, applicationId);
            var artifactsinAppCount = artifactsInApp != null ? artifactsInApp.size() : 0;
            var unitsAvaialbe = units != null ? units.size() : 0;

            if (unitsAvaialbe < artifactsinAppCount) {
                logger.error("Cannot make a build catalogue as not all artefacts in Application definition have releases from \"master\" branch");
                logger.error("{} Artifacts are configured for the Application {} - {} ", artifactsinAppCount, applicationId, artifactsInApp);
                logger.error("Only {} artefact release are available from master branch for these artefacts - {} ", unitsAvaialbe, units);
                List<String> artifactsList = new ArrayList<>();
                artifactsInApp.forEach(artifact -> artifactsList.add(artifact.getArtifactId()));
                logger.error("Items in list are {}", artifactsList);
                units.forEach(unit -> artifactsList.remove(unit.getArtifactId()));
                logger.error("Orphan artifacts are {} ", artifactsList);
                //Not all artefacts in application has releases from master bracnh available. Cannot make a build catalagoue in this state
                throw new BadRequestException(
                        Response.status(Response.Status.BAD_REQUEST).entity(format
                                ("Cannot make a build catalogue.Not all Artefacts in Application definition of " +
                                        "\"%s\" have Artefact releases from \"master\" branch. %s", applicationId, artifactsList)).build());
            }

        } else {
            findAllLatestReleasesForBranch(MASTER_BRANCH);
        }
        var buildNum = releaseManagerSqlDao.findNextBuildNum(applicationId, relNum);
        saveBuildCatalogue(tenantId, applicationId, buildNum, relNum, units);
        return relNum + "." + buildNum;
    }

    @Transactional
    public String generateReducedBuildCatalogue(String tenantId, String applicationId, String buildNum) {
       DeploymentBom uberBom = getDeploymentBOMForABuildAsYaml(buildNum).orElseThrow(() -> new BadRequestException(format("Invalid build number %s", buildNum)));
       Application app = findApplicationById(tenantId, applicationId).orElseThrow(() -> new BadRequestException(
                Response.status(Response.Status.BAD_REQUEST).entity("Application " + applicationId + " does not exist").build()));

       var artifactsInApp = this.releaseManagerSqlDao.findTenantCatalogueForApplication(tenantId, applicationId);
       app.setArtifactIds(artifactsInApp.stream().map(Artifact::getArtifactId).collect(Collectors.toList()));
       // remove additional artifacts
       Set<String> additionalArtifacts = uberBom.getDeployments().keySet().stream().filter(artifactId -> !app.getArtifactIds().contains(artifactId)).collect(Collectors.toSet());
       additionalArtifacts.forEach(a -> uberBom.getDeployments().remove(a));
       // ensure that all the required artifacts are there in the uber bom
       app.getArtifactIds().forEach(a -> {
           if (!uberBom.getDeployments().containsKey(a))
               throw new BadRequestException(format("artifact %s is not present in build %s", a, buildNum));
       });
       String releaseNum = app.getReleaseNumber();
       long nextBuildNum = releaseManagerSqlDao.findNextBuildNum(applicationId, releaseNum);
       saveBuildCatalogue(tenantId, applicationId, nextBuildNum, releaseNum, uberBom.getDeployments().values());
       return releaseNum + "." + nextBuildNum;
    }

    @Transactional
    public Optional<JsonNode> getDeploymentBOMForABuild(String buildNum) {
        return findById(buildNum).map(BuildCatalogue::getDeploymentCatalogue);
    }

    @Transactional
    public Optional<DeploymentBom> getDeploymentBOMForABuildAsYaml(String buildNum) {
        Optional<DeploymentBom> theBom = findById(buildNum).map(catalogue -> {
            DeploymentBom bom = new DeploymentBom();
            bom.setTenantId(catalogue.getTenantId());
            bom.setApplicationId(catalogue.getApplicationIdr());
            bom.setVersionTimestamp(catalogue.getBuildStartTime());
            bom.setBuildNumber(catalogue.getBldnum());
            DeploymentUnit[] units = null;
            try {
                units = this.objectMapper.treeToValue(catalogue.getDeploymentCatalogue(), DeploymentUnit[].class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            bom.setDeployments(new HashMap<>(units.length));
            List<DeploymentUnit> unitList = Arrays.asList(units);
            unitList.forEach(u -> bom.getDeployments().put(u.getArtifactId(), u));
            bom.setDeploymentGroups(deploymentGroups(unitList));
            updatePortsInDeploymentGroups(unitList);
            return bom;
        });
        return theBom;
    }

    public List<Build> findLastNDaysBuilds(int days) {
        return releaseManagerSqlDao.findLastNDaysBuilds(days).stream().map(build -> {
            if (build.getBuildNum().endsWith("00")) {
                build.setAClone(true);
            }
            return build;
        }).collect(Collectors.toList());
    }

    @Transactional
    public boolean saveReleasePackage(ReleasePackage rp) {
        var pkgId = rp.getPkgId();
        //New
        if (pkgId < 1) {
            var appId = rp.getApplicationId();
            var bldNum = rp.getBldnum();
            var tenantId = rp.getTenantId();
            var envRef = rp.getEnvRef();
            var oldP = releasePackageRepository.
                    findByApplicationIdAndTenantIdAndEnvRefAndStatus(appId, tenantId, envRef, ReleasePackage.Status.Planned.name());
            //                    findByBldnumAndApplicationIdAndTenantIdAndEnvRef(bldNum, appId, tenantId, envRef);
            if (oldP.isPresent()) {
                throw new BadRequestException(
                        Response.status(Response.Status.BAD_REQUEST).
                                entity(format("Package of { applicationId: %s, teantId:%s, envRef:%s, status:%s } already exists. " +
                                                "Only one release package for an Application & Environment can be in Planned state",
                                        appId, tenantId, envRef, ReleasePackage.Status.Planned.name())).build());
            }
        }
        releasePackageRepository.save(rp);
        return true;
    }

    @Transactional
    public Optional<ReleasePackage> findReleasePackageById(long pkgid) {
        return releasePackageRepository.findById(pkgid);
    }

    public Optional<List<ReleasePackage>> findReleasePackagesOfTenant(String tenantId, ReleasePackage.Status status) {
        return Optional.ofNullable(releasePackageRepository.findByTenantIdAndStatusOrderByPlannedDateDesc(tenantId, status.name()));
    }

    public Optional<List<ReleasePackage>> findAllReleasePackagesOfTenant(String tenantId) {
        return Optional.ofNullable(releasePackageRepository.findByTenantIdOrderByPlannedDateDesc(tenantId));
    }

    public Optional<List<Application>> findApplicationsOfTenant(String tenantId) {
        return Optional.ofNullable(releaseManagerSqlDao.findApplicationsOfTenant(tenantId));
    }


    public List<TenantMaster> listActiveTenants() {
        return tenantMasterRepository.findByIsActive(true);
    }

    public void maintainTenant(TenantMaster tenantMaster, boolean isDelete) {
        var foundTenant = tenantMasterRepository.findById(tenantMaster.getTenantId());
        if (foundTenant.isPresent()) {
            var tenant = foundTenant.get();
            if (isDelete) {
                tenantMasterRepository.deleteById(tenantMaster.getTenantId());
            } else {
                tenant.setDescription(tenantMaster.getDescription());
                tenantMasterRepository.save(tenant);
            }

        } else {
            tenantMasterRepository.save(tenantMaster);
        }
    }

    public Optional<List<String>> findConfiguredTenants() {
        return getCSVParameter("TENANTS").map(list -> {
            Collections.sort(list);
            return list;
        });
    }


    private Optional<List<String>> getCSVParameter(String parCode) {
        return systemParameterRepository.findById(parCode).map(SystemParameter::getValueAsCsv)
                .map(value -> value.split(",")).map(Arrays::asList);
    }

    public ArtifactMasterRepository artifactMasterRepository() {
        return this.artifactMasterRepository;
    }

    public ReleaseCatalogueRepository releaseCatalogueRepository() {
        return this.releaseCatalogueRepository;
    }

    public TenantCatalogueRepository tenantCatalogueRepository() {
        return this.tenantCatalogueRepository;
    }

    public ReleaseManagerSqlDao sqlDao() {
        return this.releaseManagerSqlDao;
    }

    public BuildCatalogueRepository buildCatalogueRepository() {
        return this.buildCatalogueRepository;
    }

    public ArtifactTestMasterRepository artifactTestMasterRepository() {
        return this.artifactTestMasterRepository;
    }

    public void updateReleaseNumberForApplication(@NotBlank String tenantId, @NotBlank String applicationId,
                                                  String relNumber) {
        var pk = new ApplicationMaster.PK(tenantId, applicationId);
        applicationCatalogueRepository.findById(pk).ifPresent(master -> {
            master.setReleaseNumber(relNumber);
            applicationCatalogueRepository.save(master);
        });
    }
}
