package com.ibsplc.neoicargo.relmgr.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.ibsplc.neoicargo.relmgr.entity.BuildCatalogue;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			October 21, 2020 	  Binu K			First draft
 */
@Getter
@Setter
public class Build {


    private String buildNum;


    private String applicationIdr;


    private String tenantId;

    private BuildCatalogue.BuildStatus buildStatus;

    private BuildCatalogue.BuildQuality buildQuality;
    private String buildQualityDesc;
    private int releasePackagesWithBuild;


    private ZonedDateTime buildStartTime;

    private ZonedDateTime buildEndTime;

    private JsonNode deploymentBom;
    private boolean isAClone;
}
