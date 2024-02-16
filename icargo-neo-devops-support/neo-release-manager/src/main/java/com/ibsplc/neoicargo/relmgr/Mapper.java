/*
 * Mapper.java Created on 04/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr;

import com.ibsplc.neoicargo.relmgr.entity.ArtifactMaster;
import com.ibsplc.neoicargo.relmgr.entity.BuildCatalogue;
import com.ibsplc.neoicargo.relmgr.entity.ReleaseCatalogue;
import com.ibsplc.neoicargo.relmgr.model.Artifact;
import com.ibsplc.neoicargo.relmgr.model.ArtifactRelease;
import com.ibsplc.neoicargo.relmgr.model.DeploymentBom;

/**
 * @author jens
 */
public class Mapper {

    private Mapper(){
        // thou shall not procreate
    }

    public static ArtifactMaster toArtifactMaster(Artifact artifact){
        ArtifactMaster entity = new ArtifactMaster();
        entity.setArtifactId(artifact.getArtifactId());
        entity.setPort(artifact.getPort());
        entity.setHealthEndpoint(artifact.getHealthEndpoint());
        entity.setContextPath(artifact.getContextPath());
        entity.setDescription(artifact.getDescription());
        entity.setDomain(artifact.getDomain());
        entity.setServiceTechnology(artifact.getServiceTechnology());
        entity.setServiceType(artifact.getServiceType());
        entity.setHostsEnterpriseApi(artifact.isHostsEnterpriseApi());
        entity.setHostsPrivateApi(artifact.isHostsPrivateApi());
        entity.setHostsPublicApi(artifact.isHostsPublicApi());
        entity.setProjectName(artifact.getProjectName());
        entity.setProjectRepositoryName(artifact.getProjectRepositoryName());
        return entity;
    }

    public static Artifact fromArtifactMaster(ArtifactMaster master){
        Artifact artifact = new Artifact();
        artifact.setArtifactId(master.getArtifactId());
        artifact.setPort(master.getPort());
        artifact.setHealthEndpoint(master.getHealthEndpoint());
        artifact.setContextPath(master.getContextPath());
        artifact.setDescription(master.getDescription());
        artifact.setDomain(master.getDomain());
        artifact.setServiceTechnology(master.getServiceTechnology());
        artifact.setServiceType(master.getServiceType());
        artifact.setHostsEnterpriseApi(master.isHostsEnterpriseApi());
        artifact.setHostsPrivateApi(master.isHostsPrivateApi());
        artifact.setHostsPublicApi(master.isHostsPublicApi());
        artifact.setProjectName(master.getProjectName());
        artifact.setProjectRepositoryName(master.getProjectRepositoryName());
        return artifact;
    }

    public static ReleaseCatalogue toReleaseCatalogue(ArtifactRelease release){
        ReleaseCatalogue catalogue = new ReleaseCatalogue();
        catalogue.setBranch(release.getBranch());
        catalogue.setDescription(release.getDescription());
        catalogue.setReleaseTime(release.getReleaseTime());
        catalogue.setReleaseCataloguePk(new ReleaseCatalogue.PK());
        catalogue.getReleaseCataloguePk().setArtifactId(release.getArtifactId());
        catalogue.getReleaseCataloguePk().setArtifactVersion(release.getArtifactVersion());
        catalogue.setCommitterEmail(release.getCommitterEmail());
        catalogue.setReleaseStatus(release.getReleaseStatus());
        return catalogue;
    }

    public static ArtifactRelease fromReleaseCatalogue(ReleaseCatalogue catalogue){
        ArtifactRelease release = new ArtifactRelease();
        release.setBranch(catalogue.getBranch());
        release.setDescription(catalogue.getDescription());
        release.setReleaseTime(catalogue.getReleaseTime());
        release.setArtifactId(catalogue.getReleaseCataloguePk().getArtifactId());
        release.setArtifactVersion(catalogue.getReleaseCataloguePk().getArtifactVersion());
        release.setCommitterEmail(catalogue.getCommitterEmail());
        release.setReleaseStatus(catalogue.getReleaseStatus());
        return release;
    }

}
