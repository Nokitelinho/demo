package com.ibsplc.neoicargo.relmgr.entity;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			October 22, 2020 	  Binu K			First draft
 */


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Table(name = "RELPKG")
@NoArgsConstructor
@Getter
@Setter
public class ReleasePackage {

    public enum Status{
        Planned,
        Complete,
        Cancelled;
    }

    @Column(name = "PKGID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pkgId;

    @Column(name = "BLDNUM")
    private String bldnum;

    @NotNull
    @Column(name = "APPIDR")
    private String applicationId;

    @NotNull
    @Column(name = "TNTIDR")
    private String tenantId;

    @NotNull
    @Column(name = "ENVREF")
    private String envRef;

    @Column(name = "PLNDAT")
    private LocalDate plannedDate;

    @Column(name = "CRTDAT")
    private LocalDate createDate;

    @Column(name = "APRDAT")
    private LocalDate approvedDate;

    @Column(name = "CSTCHGRFN")
    private String customerChangeRef;

    @Column(name = "PKGRMK")
    private String remarks;

    @NotNull
    @Column(name = "PKGSTA")
    private String status;

}
