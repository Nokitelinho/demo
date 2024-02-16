package com.ibsplc.neoicargo.devops.utils.ecrsync.parse;

import java.util.HashMap;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			June 03, 2021 	  Binu K			First draft
 */

public class Bom {

    String applicationId;
    String buildNumber;
    HashMap<String, BomEntry> deployments = new HashMap<>();

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public HashMap<String, BomEntry> getDeployments() {
        return deployments;
    }

    public void setDeployments(HashMap<String, BomEntry> deployments) {
        this.deployments = deployments;
    }

    @Override
    public String toString() {
        return "Bom{" +
                "applicationId='" + applicationId + '\'' +
                ", buildNumber='" + buildNumber + '\'' +
                ", deployments=" + deployments +
                '}';
    }
}
