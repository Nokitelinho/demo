package com.ibsplc.neoicargo.relmgr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			November 26, 2020 	  Binu K			First draft
 */
@Entity
@Table(name = "TNTMST")
@NoArgsConstructor
@Getter
@Setter
public class TenantMaster {
    @Id
    @Column(name = "TNTIDR")
    @NotBlank
    private String tenantId;

    @Column(name = "TNTDSC")
    private String description;

    @Column(name = "ACTIVE")
    private boolean isActive = true;
}
