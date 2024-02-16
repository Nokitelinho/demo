package com.ibsplc.neoicargo.tracking.dao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TRKMILSTNMST")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "TRKMILSTNMSTSEQ", initialValue = 1, allocationSize = 1, sequenceName = "'TRKMILSTNMST_SEQ")
public class Milestone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TRKMILSTNSERNUM")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRKMILSTNMSTSEQ")
    private Long serialNumber;

    @Column(name = "CMPCOD")
    private String companyCode;

    @Column(name = "MILSTNCOD")
    private String milestoneCode;

    @Column(name = "MILSTNDES")
    private String milestoneDescription;

    @Column(name = "MILSTNTYP")
    private String milestoneType;

    @Column(name = "MILSTNSHPTYP")
    private String milestoneShipmentType;

    @Column(name = "ACTVIWFLG")
    private boolean activityViewFlag;

    @Column(name = "NTFSNDFLG")
    private boolean emailNotificationFlag;

   


}