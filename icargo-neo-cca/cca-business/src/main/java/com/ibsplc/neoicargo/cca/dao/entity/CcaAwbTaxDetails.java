package com.ibsplc.neoicargo.cca.dao.entity;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ccaawbtaxdtl")
@Getter
@Setter
@NoArgsConstructor
public class CcaAwbTaxDetails extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8941401700012912585L;

    @Id
    @Column(name = "ccaawbtaxsernum")
    @SequenceGenerator(name = "ccaawbtaxdtlseq", allocationSize = 1, sequenceName = "ccaawbtaxdtl_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ccaawbtaxdtlseq")
    private Long serialNumber;

    @MapsId("ccaSerialNumber")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ccasernum", referencedColumnName = "ccasernum", nullable = false)
    private CcaMaster ccaMaster;

    @Column(name = "rectyp", insertable = false, updatable = false)
    private String recordType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(value = {
            @JoinColumn(name = "ccasernum", referencedColumnName = "ccasernum", nullable = false),
            @JoinColumn(name = "rectyp", referencedColumnName = "rectyp", nullable = false)
    })
    private CCAAwb ccaawb;

    @Column(name = "cfgtyp")
    private String configurationType;

    @Type(type = "jsonb")
    @Column(name = "taxdtl", columnDefinition = "json")
    private Object taxDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var that = (CcaAwbTaxDetails) o;
        return that.serialNumber != null && that.serialNumber.equals(serialNumber);
    }

    @Override
    public int hashCode() {
        // should be fixed since we use strategy = GenerationType.SEQUENCE
        // and there`s no unique fields
        return 31;
    }
}
