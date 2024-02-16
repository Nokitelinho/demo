package com.ibsplc.neoicargo.relmgr.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			October 20, 2020 	  Binu K			First draft
 */
@Getter
@Setter
@ToString
public class Application {
    private String tenantId;
    private String applicationId;
    private String applicationDesc;
    private String releaseNumber;

    @NotNull
    private List<String> artifactIds;


}
