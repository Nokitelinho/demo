package com.ibsplc.neoicargo.tracking.dao.entity;

import com.ibsplc.neoicargo.framework.util.unit.EUnitOfQuantity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRKSHPMILSTNEVT")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "TRKSHPMILSTNEVTSEQ", initialValue = 1, allocationSize = 1, sequenceName = "TRKSHPMILSTNEVT_SEQ")
public class ShipmentMilestoneEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TRKSHPMILSTNEVTSERNUM")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRKSHPMILSTNEVTSEQ")
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

    @Type(type = "jsonb")
    @Column(name = "MILSTNDTL",columnDefinition="json")
    private Object transactionDetails;

    @Column(name = "LSTUPDTIM")
    private LocalDateTime lastUpdateTime;

    @Column(name = "LSTUPDUSR")
    private String lastUpdateUser;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "weight", column = @Column(name = "WGTUNT")),
            @AttributeOverride(name = "volume", column = @Column(name = "VOLUNT"))
    })
    private EUnitOfQuantity eUnit;

    @Column(name = "WGT")
    private Double weight;

}