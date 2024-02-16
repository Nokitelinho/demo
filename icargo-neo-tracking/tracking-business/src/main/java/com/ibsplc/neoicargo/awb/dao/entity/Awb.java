package com.ibsplc.neoicargo.awb.dao.entity;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.unit.EUnitOfQuantity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TRKAWBMST")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "TRKAWBMSTSEQ", initialValue = 1, allocationSize = 1, sequenceName = "TRKAWBMST_SEQ")
public class Awb extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TRKAWBSERNUM")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRKAWBMSTSEQ")
    private Long serialNumber;

    @Embedded
    private ShipmentKey shipmentKey;

    @Column(name = "SHPSEQNUM")
    private Long shipmentSequenceNumber;

    @Column(name = "ORGCOD")
    private String origin;

    @Column(name = "DSTCOD")
    private String destination;

    @Column(name = "STDPCS")
    private int statedPieces;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "weight", column = @Column(name = "STDWGTUNT")),
            @AttributeOverride(name = "volume", column = @Column(name = "STDVOLUNT"))
    })
    private EUnitOfQuantity eUnit;

    @Column(name = "DISSTDWGT")
    private Double statedWeight;

    @Column(name = "DISSTDVOL")
    private Double statedVolume;

    @Column(name = "PRDNAM")
    private String productName;

    @Column(name = "SHC")
    private String specialHandlingCode;

    @Column(name = "SHPDES")
    private String shipmentDescription;

    @Column(name = "AWBSTA")
    private String awbStatus;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "awb")
    private AWBContactDetails awbContactDetails;
}