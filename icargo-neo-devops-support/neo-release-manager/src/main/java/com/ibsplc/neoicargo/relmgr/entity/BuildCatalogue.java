package com.ibsplc.neoicargo.relmgr.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			October 21, 2020 	  Binu K			First draft
 */
@Entity
@Table(name = "BLDCTG")
@NoArgsConstructor
@Getter
@Setter
@TypeDefs({
        @TypeDef(name = "jsonb",defaultForType = JsonNode.class,typeClass = JsonBinaryType.class)
})
public class BuildCatalogue {
	
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "bldnum", column = @Column(name = "BLDNUM")),
            @AttributeOverride(name = "relNum", column = @Column(name = "RELNUM")),
    })
    private BuildCatalogue.PK buildCatalogPK;
	
    @Getter
    public enum BuildStatus{
        InProgress, Complete,Unknown;
    }

    @Getter
    public enum BuildQuality{
        Alpha("No Tests Complete"),  Beta("Smoke Tests Complete"), ReleaseCandidate("E2E Tests Complete");
        private String message;

        BuildQuality(String message) {
            this.message = message;
        }
    }


    public void setBuildQuality(BuildQuality buildQuality){
        this.buildQuality = buildQuality;
        if (buildQuality==BuildQuality.ReleaseCandidate){
            this.buildStatus = BuildStatus.Complete;
        }
    }


    @NoArgsConstructor
    @RequiredArgsConstructor
    @Data
    public static class PK implements Serializable {
        @NonNull
        private long bldnum;
        @NonNull
        private String relNum;
    }

    public String getBldnum() {
    	return this.buildCatalogPK.relNum + "." + this.buildCatalogPK.bldnum;
    }

    //Optional
    @Column(name = "APPIDR")
    private String applicationIdr;

    //Optional
    @Column(name = "TNTIDR")
    private String tenantId;

    @Column(name = "BLDSTA")
    @Enumerated(EnumType.STRING)
    private BuildStatus buildStatus;

    @Column(name = "BLDQLY")
    @Enumerated(EnumType.STRING)
    private BuildQuality buildQuality;

    @Column(name = "BLDSTRTIM")
    private ZonedDateTime buildStartTime;
    @Column(name = "BLDENDTIM")
    private ZonedDateTime buildEndTime;

    @Column(name = "DEPCTG",columnDefinition = "jsonb")
    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    private JsonNode deploymentCatalogue;

}
