package com.ibsplc.neoicargo.cca.dao.entity;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.unit.EUnitOfMeasure;
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
@Table(name = "ccaawbdimdtl")
@Getter
@Setter
@NoArgsConstructor
public class CcaAwbDetailDimensions extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ccaawbdimdtlsernum", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ccaawbdimdtlseq")
    @SequenceGenerator(name = "ccaawbdimdtlseq", sequenceName = "ccaawbdimdtl_seq", allocationSize = 1)
    private Long serialNumber;

    @Column(name = "pcs")
    private Integer pieces;

    @Embedded
    @AttributeOverride(name = "weight", column = @Column(name = "diswgtunt"))
    @AttributeOverride(name = "volume", column = @Column(name = "disvolunt"))
    @AttributeOverride(name = "length", column = @Column(name = "dislenunt"))
    private EUnitOfMeasure unitOfMeasure;

    @Column(name = "wgt")
    private Double weight;

    @Column(name = "diswgt")
    private Double displayWeight;

    @Column(name = "len")
    private Double length;

    @Column(name = "dislen")
    private Double displayLength;

    @Column(name = "wid")
    private Double width;

    @Column(name = "diswid")
    private Double displayWidth;

    @Column(name = "hgt")
    private Double height;

    @Column(name = "dishgt")
    private Double displayHeight;

    @Column(name = "vol")
    private Double volume;

    @Column(name = "disvol")
    private Double displayVolume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ccaawbdtlsernum", nullable = false)
    private CCAAwbDetail ccaAwbDetail;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CcaAwbDetailDimensions that = (CcaAwbDetailDimensions) o;
        return that.serialNumber != null && that.serialNumber.equals(serialNumber);
    }

    @Override
    public int hashCode() {
        // should be fixed since we use strategy = GenerationType.SEQUENCE
        // and there`s no unique fields
        return 31;
    }

}
