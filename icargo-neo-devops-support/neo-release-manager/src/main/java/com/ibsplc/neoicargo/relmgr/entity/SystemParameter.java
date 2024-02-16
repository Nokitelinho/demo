package com.ibsplc.neoicargo.relmgr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			October 28, 2020 	  Binu K			First draft
 */
@Entity
@Table(name = "SYSPAR")
@NoArgsConstructor
@Getter
@Setter
public class SystemParameter {

    @Id
    @Column(name = "PARCOD")
    private String parCode;

    @Column(name = "CSVVAL")
    private String valueAsCsv;

}
