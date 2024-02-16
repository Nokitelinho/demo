/*
 * ReleaseManagerJaxrsService.java Created on 04/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr;

import com.fasterxml.jackson.databind.JsonNode;
import com.ibsplc.neoicargo.relmgr.entity.*;
import com.ibsplc.neoicargo.relmgr.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.relmgr.ReleaseManagerController.DEFAULT_TENANT;
import static javax.ws.rs.core.Response.*;

/**
 * @author jens
 */
@Component
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReleaseManagerJaxrsService {

    static final Logger log = LoggerFactory.getLogger(ReleaseManagerJaxrsService.class);

    private final ReleaseManagerController controller;

    @Autowired
    public ReleaseManagerJaxrsService(ReleaseManagerController controller) {
        this.controller = controller;
    }

    @POST
    @Path("/artifact/{artifactId}")
    public Response upsertArtifactMaster(@PathParam("artifactId") String artifactId, Artifact artifact) {
        artifact.setArtifactId(artifactId);
        ArtifactMaster master = Mapper.toArtifactMaster(artifact);
        this.controller.artifactMasterRepository().save(master);
        return ok().build();
    }

    @DELETE
    @Path("/artifact/{artifactId}")
    public Response deleteArtifactMaster(@PathParam("artifactId") String artifactId) {
        boolean answer = this.controller.artifactMasterRepository().existsById(artifactId);
        if (!answer)
            return notModified("ArtifactMaster entry absent").build();
        this.controller.artifactMasterRepository().deleteById(artifactId);
        return ok().build();
    }

    @GET
    @Path("/artifact/{artifactId}")
    public Response artifact(@PathParam("artifactId") String artifactId) {
        Optional<ArtifactMaster> answer = this.controller.artifactMasterRepository().findById(artifactId);
        return answer.map(m -> ok(Mapper.fromArtifactMaster(m)).build()).orElse(status(Status.NOT_FOUND).build());
    }

    @GET
    @Path("/artifacts")
    public Response allArtifacts() {
        List<Artifact> artifacts = new ArrayList<>();
        this.controller.artifactMasterRepository().findAll().forEach(master -> artifacts.add(Mapper.fromArtifactMaster(master)));
        return ok(artifacts).build();
    }

    @POST
    @Path("/release/{artifactId}/{artifactVersion}")
    public Response upsertArtifactRelease(@PathParam("artifactId") String artifactId,
                                          @PathParam("artifactVersion") String artifactVersion, ArtifactRelease release) {
        release.setArtifactId(artifactId);
        release.setArtifactVersion(artifactVersion);
        this.controller.upsertArtifactRelease(release);
        return ok().build();
    }

    @GET
    @Path("/release/{artifactId}/{artifactVersion}")
    public Response artifactRelease(@PathParam("artifactId") String artifactId,
                                    @PathParam("artifactVersion") String artifactVersion) {
        ReleaseCatalogue.PK pk = new ReleaseCatalogue.PK();
        pk.setArtifactVersion(artifactVersion);
        pk.setArtifactId(artifactId);
        Optional<ReleaseCatalogue> answer = this.controller.releaseCatalogueRepository().findById(pk);
        return answer.map(m -> ok(Mapper.fromReleaseCatalogue(m)).build()).orElse(status(Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/release/{artifactId}/{artifactVersion}")
    public Response deleteArtifactRelease(@PathParam("artifactId") String artifactId,
                                          @PathParam("artifactVersion") String artifactVersion) {
        ReleaseCatalogue.PK pk = new ReleaseCatalogue.PK();
        pk.setArtifactVersion(artifactVersion);
        pk.setArtifactId(artifactId);
        boolean answer = this.controller.releaseCatalogueRepository().existsById(pk);
        if (!answer)
            return notModified("ArtifactRelease entry absent").build();
        this.controller.releaseCatalogueRepository().deleteById(pk);
        return ok().build();
    }


    @GET
    @Path("/releases/latest")
    public Response latestReleases(@QueryParam("artifactId") String artifactId) {
        log.info("Find latest artefact releases for {}", artifactId);
        var releases = controller.findAllLatestReleases(artifactId);
        return CollectionUtils.isEmpty(releases) ? status(Status.NOT_FOUND).build() : ok(releases).build();
    }

    @POST
    @Path("/tenant/{tenantId}/{applicationId}/{artifactId}")
    public Response upsertTenantCatalogue(@PathParam("tenantId") String tenantId,
                                          @PathParam("applicationId") String applicationId,
                                          @PathParam("artifactId") String artifactId) {
        TenantCatalogue catalogue = new TenantCatalogue();
        catalogue.setTenantCataloguePk(new TenantCatalogue.PK());
        catalogue.getTenantCataloguePk().setApplicationId(applicationId);
        catalogue.getTenantCataloguePk().setArtifactId(artifactId);
        catalogue.getTenantCataloguePk().setTenantId(tenantId);
        this.controller.tenantCatalogueRepository().save(catalogue);
        return ok().build();
    }

    @DELETE
    @Path("/tenant/{tenantId}/{applicationId}/{artifactId}")
    public Response deleteTenantCatalogue(@PathParam("tenantId") String tenantId,
                                          @PathParam("applicationId") String applicationId,
                                          @PathParam("artifactId") String artifactId) {
        TenantCatalogue.PK pk = new TenantCatalogue.PK();
        pk.setApplicationId(applicationId);
        pk.setArtifactId(artifactId);
        pk.setTenantId(tenantId);
        boolean answer = this.controller.tenantCatalogueRepository().existsById(pk);
        if (!answer)
            return notModified("TenantCatalogue entry absent").build();
        this.controller.tenantCatalogueRepository().deleteById(pk);
        return ok().build();
    }

    @GET
    @Path("/tenant/{tenantId}")
    public Response allTenantArtifacts(@PathParam("tenantId") String tenantId) {
        List<Artifact> answer = this.controller.sqlDao().findTenantCatalogue(tenantId);
        return ok(answer).build();
    }


    @GET
    @Path("/tenant/{tenantId}/applications")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllApplicationsOfTenant(@PathParam("tenantId") @NotNull String tenantId) {
        return controller.findApplicationsOfTenant(tenantId).map(apps -> Response.ok(apps).build()).orElse(status(Status.NOT_FOUND).build());
    }


    @Path("/tenants")
    @GET
    public Response allKnownTenants() {
        Response result = null;
        var tenants = controller.listActiveTenants();
        if (!CollectionUtils.isEmpty(tenants)) {
            result = Response.ok(tenants).build();
        } else {
            result = status(Status.NOT_FOUND).build();
        }
        return result;
    }

    @Path("/tenants")
    @POST
    public void maintainTenant(@Valid TenantMaster tenantMaster) {
        controller.maintainTenant(tenantMaster, false);
    }

    @Path("/tenants/{tenantId}")
    @DELETE
    public void deleteTenant(@PathParam("tenantId") @NotNull String tenantId) {
        var tenantMaster = new TenantMaster();
        tenantMaster.setTenantId(tenantId);
        controller.maintainTenant(tenantMaster, true);
    }

    @GET
    @Path("/tenant/{tenantId}/applications/{applicationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getApplication(@PathParam("tenantId") @NotNull String tenantId,
                                   @PathParam("applicationId") @NotNull String applicationId) {

        return this.controller.findApplicationById(tenantId, applicationId).map(apps -> Response.ok(apps).build()).orElse(status(Status.NOT_FOUND).build());
    }

    @POST
    @Path("/tenant/{tenantId}/applications/{applicationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response upsertApplicationWithTenantCatalogue(@PathParam("tenantId") @NotBlank String tenantId,
                                                         @PathParam("applicationId") @NotBlank String applicationId,
                                                         @Valid Application application) {
        application.setApplicationId(applicationId.trim().toLowerCase());
        application.setTenantId(tenantId);
        log.debug("Save application {}", application);
        this.controller.upsertApplicationWithTenantCatalogue(application);
        return ok().build();
    }

    @POST
    @Path("/tenant/{tenantId}/applications/{applicationId}/release/{relNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateReleaseNumberForApplication(@PathParam("tenantId") @NotBlank String tenantId,
                                                         @PathParam("applicationId") @NotBlank String applicationId,
                                                         @PathParam("relNumber")  String relNumber) {
        this.controller.updateReleaseNumberForApplication(tenantId,applicationId,relNumber);
        return ok().build();
    }

    @GET
    @Path("/tenant/{tenantId}/applications/{applicationId}/artefacts")
    public Response allTenantApplicationArtifacts(@PathParam("tenantId") String tenantId,
                                                  @PathParam("applicationId") String applicationId) {
        List<Artifact> answer = this.controller.sqlDao().findTenantCatalogueForApplication(tenantId, applicationId);
        return ok(answer).build();
    }

    @GET
    @Path("/deployment/{tenantId}/{applicationId}")
    @Produces("application/yaml")
    public Response createTenantDeploymentBom(@PathParam("tenantId") String tenantId,
                                              @PathParam("applicationId") String applicationId) {
        Optional<DeploymentBom> maybe = this.controller.findTenantDeploymentBom(tenantId, applicationId,
                ReleaseManagerController.MASTER_BRANCH);
        return maybe.map(m -> ok(m).build()).orElse(status(Status.NOT_FOUND).build());
    }
    @GET
    @Path("/deployment/{tenantId}/{applicationId}/activity")
    @Produces("application/json")
    public Response getActivtyReferences(@PathParam("tenantId") String tenantId,
                                              @PathParam("applicationId") String applicationId) {
        Optional<List<ActiveTask>> maybe = this.controller.findActive(tenantId,applicationId);
        return maybe.map(m -> ok(m).build()).orElse(status(Status.NOT_FOUND).build());
    }

    @GET
    @Path("/deployment/{tenantId}/{artifactId}/{artifactVersion}/bom")
    @Produces("application/yaml")
    public Response artifactReleaseBom(@PathParam("tenantId") String tenantId,
                                       @PathParam("artifactId") String artifactId,
                                       @PathParam("artifactVersion") String artifactVersion) {
        Optional<DeploymentBom> maybe = this.controller.findArtefactReleaseBom(tenantId, artifactId, artifactVersion);
        return maybe.map(m -> ok(m).build()).orElse(status(Status.NOT_FOUND).build());
    }

    @POST
    @Path("/builds")
    public String generateABuildCatalogue(@DefaultValue(DEFAULT_TENANT) @QueryParam("tenantId") String tenantId,
                                        @NotBlank @QueryParam("applicationId") String applicationId) {
        return controller.generateBuildCatalogue(tenantId, applicationId);
    }

    @POST
    @Path("/build-reduced")
    public String generateReducedBuildCatalog(@DefaultValue(DEFAULT_TENANT) @QueryParam("tenantId") String tenantId,
                                              @NotBlank @QueryParam("applicationId") String applicationId,
                                              @NotBlank @QueryParam("buildNum") String buildNum) {
       return controller.generateReducedBuildCatalogue(tenantId, applicationId, buildNum);
    }

    //Sander
    @GET
    @Path("/builds/{tenantId}/{applicationId}/activity/list")
    @Produces("application/json")
    public Response getActivtyReferencesJIRA(@PathParam("tenantId") String tenantId,
                                              @PathParam("applicationId") String applicationId,
                                              @QueryParam("currentVersion") String currentVersion,
                                              @NotBlank @QueryParam("newVersion") String newVersion) {
        Optional<JiraIdResponse> maybe = this.controller.findActiveRefernces(tenantId,applicationId,currentVersion, newVersion);
        return maybe.map(m -> ok(m).build()).orElse(status(Status.NOT_FOUND).build());
    }


    @GET
    @Path("/builds/{tenantId}/{applicationId}/{releaseQuality}")
    @Produces("application/yaml")
    public Response getQualitySpecificApplication(@PathParam("tenantId") String tenantId,
                                              @PathParam("applicationId") String applicationId,
                                              @PathParam("releaseQuality") String releaseQuality
    		) {
        Optional<DeploymentBom> maybe = this.controller.getBomForApplication(tenantId,applicationId,releaseQuality);
        return maybe.map(m -> ok(m).build()).orElse(status(Status.NOT_FOUND).build());
    }



    @POST
    @Path("/builds/{buildNum}/clone")
    public String cloneABuildCatalogue(@PathParam("buildNum") String buildnum,
                                     @Valid List<ArtifactRelease> patchArtefactReleases) {
        return controller.cloneABuildCatalogue(buildnum, patchArtefactReleases);
    }


    @POST
    @Path("/builds/{buildNum}/alpha")
    public Response buildToAlpha(@PathParam("buildNum") String buildNum) {
        return progressBuild(buildNum, BuildCatalogue.BuildQuality.Alpha);
    }

    @POST
    @Path("/builds/{buildNum}/beta")
    public Response buildToBeta(@PathParam("buildNum") String buildNum) {
        return progressBuild(buildNum, BuildCatalogue.BuildQuality.Beta);
    }

    @POST
    @Path("/builds/{buildNum}/rc")
    public Response buildToRC(@PathParam("buildNum") String buildNum) {
        return progressBuild(buildNum, BuildCatalogue.BuildQuality.ReleaseCandidate);
    }


    private Response progressBuild(String buildNum, BuildCatalogue.BuildQuality buildQuality) {
        return controller.changeBuildQuality(buildNum, buildQuality) ? ok().build() : status(Status.NOT_FOUND).build();
    }


    @GET
    @Path("/builds")
    public List<Build> lastNDaysBuilds(@DefaultValue("30") @QueryParam("lastNDays") int days) {
        if (days > 180) {
            days = 180;
        }
        return controller.findLastNDaysBuilds(days);
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/builds/{buildnum}/bom")
    @GET
    public Response findDeploymentBOMForABuild(@PathParam("buildnum") String buildNum) {
        return controller.getDeploymentBOMForABuild(buildNum).map(jsonNode -> Response.ok(jsonNode).build()).
                orElse(status(Status.NOT_FOUND).build());

    }

    @Produces("application/yaml")
    @Path("/builds/{buildnum}/bom")
    @GET
    public Response findDeploymentBOMForABuildAsYaml(@PathParam("buildnum") String buildNum) {
        Optional<DeploymentBom> bom = controller.getDeploymentBOMForABuildAsYaml(buildNum);
        return bom.map(b -> Response.ok(b).build()).orElse(status(Status.NOT_FOUND).build());
    }

    @POST
    @Path("/packages")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveReleasePackage(@Valid ReleasePackage rp) {
        controller.saveReleasePackage(rp);
        return Response.ok().build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/packages/{tenantId}")
    @GET
    public Response findReleasePackagesOfTenant(@PathParam("tenantId") String tenantId, @QueryParam("status") ReleasePackage.Status status) {

        Optional<List<ReleasePackage>> packages = Objects.isNull(status) ? controller.findAllReleasePackagesOfTenant(tenantId) :
                controller.findReleasePackagesOfTenant(tenantId, status);
        return packages.map(data -> Response.ok(data).build()).
                orElse(status(Status.NOT_FOUND).build());

    }

    @Produces("application/yaml")
    @Path("/packages/{tenantId}/{packageId}/bom")
    @GET
    public Response generateDeploymentBOMForAPackage(@PathParam("tenantId") String tenantId, @PathParam("packageId") long packageId) {
        return controller.findDeploymentBomForReleasePackage(tenantId, packageId).map(data -> Response.ok(data).build()).
                orElse(status(Status.NOT_FOUND).build());

    }

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/packages/{tenantId}/{packageId}")
    @GET
    public Response findPackage(@PathParam("tenantId") String tenantId, @PathParam("packageId") long packageId) {
        return controller.findReleasePackageById(packageId).map(data -> Response.ok(data).build()).
                orElse(status(Status.NOT_FOUND).build());
    }

    @POST
    @Path("/tests")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addArtifactTest(ArtifactTest artifactTest){
        controller.artifactTestMasterRepository().save(new ArtifactTestMaster(artifactTest));
        return Response.ok().build();
    }

    @GET
    @Path("/tests/{tenantId}/{artifactId}/{testType}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArtifactTest(@PathParam("tenantId") String tenantId, @PathParam("artifactId") String artifactId, @PathParam("testType") String testType){
        Optional<ArtifactTestMaster> master = controller.artifactTestMasterRepository().findById(new ArtifactTestMaster.PK(tenantId, artifactId, testType));
        return master.isEmpty() ? Response.noContent().build() : Response.ok().entity(Collections.singletonList(new ArtifactTest(master.get()))).build();
    }

    @GET
    @Path("/tests/{tenantId}/{artifactId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArtifactTest(@PathParam("tenantId") String tenantId, @PathParam("artifactId") String artifactId){
        List<ArtifactTest> masters = controller.artifactTestMasterRepository().findByPkArtifactIdAndPkTenantId(artifactId, tenantId).stream().map(ArtifactTest::new).collect(Collectors.toList());
        return masters.isEmpty() ? Response.noContent().build() : Response.ok().entity(masters).build();
    }

    @DELETE
    @Path("/tests/{tenantId}/{artifactId}/{testType}")
    public Response deleteArtifactTest(@PathParam("tenantId") String tenantId, @PathParam("artifactId") String artifactId, @PathParam("testType") String testType){
        controller.artifactTestMasterRepository().deleteById(new ArtifactTestMaster.PK(tenantId, artifactId, testType));
        return Response.ok().build();
    }


}
