package com.ibsplc.neoicargo.relmgr.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			October 20, 2020 	  Binu K			First draft
 */

@Entity
@Table(name = "APPCTG")
@NoArgsConstructor
@Getter
@Setter
public class ApplicationMaster {



    @Column(name = "APPDSC")
    private String applicationDesc;
    
    @Column(name = "RELNUM")
    private String releaseNumber;

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "tenantId", column = @Column(name = "TNTIDR")),
            @AttributeOverride(name = "applicationId", column = @Column(name = "APPIDR")),
    })
    private ApplicationMaster.PK applicationMasterPK;

    @NoArgsConstructor
    @RequiredArgsConstructor
    @Data
    public static class PK implements Serializable {
        @NonNull
        private String tenantId;
        @NonNull
        private String applicationId;
    }

}
