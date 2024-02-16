/*
 * ReleaseCatalogue.java Created on 01/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * @author jens
 */
@Entity
@Table(name = "RELCTG")
@NoArgsConstructor
@Getter
@Setter
public class ReleaseCatalogue {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "artifactId", column = @Column(name = "ARTIDR")),
            @AttributeOverride(name = "artifactVersion", column = @Column(name = "ARTVER")),
    })
    private PK releaseCataloguePk;

    @Column(name = "RELBRH")
    private String branch;

    @Column(name = "RELDSC")
    private String description;

    @Column(name = "CMTEMLADD")
    private String committerEmail;

    @Column(name = "RELTIM")
    private ZonedDateTime releaseTime;

    @Column(name = "RELSTA")
    private String releaseStatus;
    
    
    @Column(name = "ACTVTYREF")
    private String actvtyRef;

    @Getter
    @Setter
    @NoArgsConstructor
    @Embeddable
    public static class PK implements Serializable {
        private String artifactId;
        private String artifactVersion;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PK pk = (PK) o;
            return artifactId.equals(pk.artifactId) &&
                    artifactVersion.equals(pk.artifactVersion);
        }

        @Override
        public int hashCode() {
            return Objects.hash(artifactId, artifactVersion);
        }
    }

}
