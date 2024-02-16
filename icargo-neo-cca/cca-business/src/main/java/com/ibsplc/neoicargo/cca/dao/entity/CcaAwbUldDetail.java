package com.ibsplc.neoicargo.cca.dao.entity;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.unit.EUnitOfQuantity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "CCAAWBULD")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "CCAAWBULDSEQ", sequenceName = "CCAAWBULD_SEQ", allocationSize = 1)
public class CcaAwbUldDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ccaawbdtlsernum", nullable = false)
    private CCAAwbDetail ccaAwbDetail;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CCAAWBULDSEQ")
    @Column(name = "ULDSERNUM", updatable = false, nullable = false)
    private Long serialNumber;

    @Column(name = "ULDTYP")
    private String type;

    @Column(name = "NUMULD")
    private long numberOfUld;

    @Column(name = "ULDCON")
    private String contour;

    @Column(name = "ULDVOL")
    private Double volume;

    @Column(name = "DISULDVOL")
    private Double displayVolume;

    @Column(name = "ULDWGT")
    private Double weight;

    @Column(name = "DISULDWGT")
    private Double displayWeight;

    @Embedded
    @AttributeOverride(name = "weight", column = @Column(name = "diswgtunt"))
    @AttributeOverride(name = "volume", column = @Column(name = "disvolunt"))
    private EUnitOfQuantity unitOfMeasure;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CcaAwbUldDetail that = (CcaAwbUldDetail) o;
        return that.serialNumber != null && that.serialNumber.equals(serialNumber);
    }

    @Override
    public int hashCode() {
        // should be fixed since we use strategy = GenerationType.SEQUENCE
        // and there`s no unique fields
        return 31;
    }

}
