package com.ibsplc.neoicargo.tracking.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRKSHPMILSTNPLN")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "TRKSHPMILSTNPLNSEQ", initialValue = 1, allocationSize = 1, sequenceName = "TRKSHPMILSTNPLN_SEQ")
public class ShipmentMilestonePlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TRKSHPMILSTNPLNSERNUM")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRKSHPMILSTNPLNSEQ")
    private Long serialNumber;

    @Column(name = "CMPCOD")
    private String companyCode;

    @Column(name = "SHPKEY")
    private String shipmentKey;

    @Column(name = "SHPTYP")
    private String shipmentType;

    @Column(name = "SHPSEQNUM")
    private Integer shipmentSequenceNumber;

    @Column(name = "ARPCOD")
    private String airportCode;

    @Column(name = "MILSTNCOD")
    private String milestoneCode;

    @Column(name = "PCS")
    private Integer pieces;

    @Column(name = "MILSTNTIM")
    private LocalDateTime milestoneTime;

    @Column(name = "MILSTNTIMUTC")
    private LocalDateTime milestoneTimeUTC;

    @Column(name = "SRC")
    private String source;

    @Column(name = "OPRTYP")
    private String operationType;

    @Column(name = "FLTNUM")
    private String flightNumber;

    @Column(name = "FLTCARCOD")
    private String flightCarrierCode;

    @Column(name = "FLTDAT")
    private LocalDate flightDate;
}