/*
 * ArtifactMaster.java Created on 01/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr.entity;

import com.ibsplc.neoicargo.relmgr.model.ServiceTechnology;
import com.ibsplc.neoicargo.relmgr.model.ServiceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author jens
 *
 * The entity represents a artifact that is being managed.
 *
 */

@Entity
@Table(name = "ARTMST")
@NoArgsConstructor
@Getter
@Setter
public class ArtifactMaster {

    @Id
    @Column(name = "ARTIDR")
    private String artifactId;

    @Column(name = "PORT")
    private int port;

    @Column(name = "DOMNAM")
    private String domain;

    @Column(name = "SRVTYP")
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @Column(name = "SRVTCH")
    @Enumerated(EnumType.STRING)
    private ServiceTechnology serviceTechnology;

    @Column(name = "CTXPTH")
    private String contextPath;

    @Column(name = "HLTENP")
    private String healthEndpoint;

    @Column(name = "DSC")
    private String description;

    @Column(name = "PRVAPI")
    private boolean hostsPrivateApi;

    @Column(name = "PUBAPI")
    private boolean hostsPublicApi;

    @Column(name = "ENTAPI")
    private boolean hostsEnterpriseApi;

    @Column(name = "PRJNAM")
    private String projectName;

    @Column(name = "PRJREPNAM")
    private String projectRepositoryName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtifactMaster that = (ArtifactMaster) o;
        return artifactId.equals(that.artifactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artifactId);
    }
}
