package com.ibsplc.neoicargo.cca.dao.entity;

import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ccacusdtl")
@Getter
@Setter
@NoArgsConstructor
public class CCACustomerDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ccacusdtlsernum", updatable = false, nullable = false)
    @SequenceGenerator(name = "CCACUSDTLSEQ", sequenceName = "CCACUSDTL_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CCACUSDTLSEQ")
    private Long serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(value = {@JoinColumn(name = "ccasernum", referencedColumnName = "ccasernum", nullable = false),
            @JoinColumn(name = "rectyp", referencedColumnName = "rectyp", nullable = false)})
    private CCAAwb ccaawb;

    @Enumerated(EnumType.STRING)
    @Column(name = "custyp")
    private CustomerType customerType;

    @Column(name = "cusnam")
    private String customerName;

    @Column(name = "stncod")
    private String stationCode;

    @Column(name = "ctycod")
    private String cityCode;

    @Column(name = "cntcod")
    private String countryCode;

    @Column(name = "accnum")
    private String accountNumber;

    @Column(name = "iatcod")
    private String iataCode;

    @Column(name = "casidr")
    private String cassIndicator;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CCACustomerDetail that = (CCACustomerDetail) o;
        return that.serialNumber != null && that.serialNumber.equals(serialNumber);
    }

    @Override
    public int hashCode() {
        // should be fixed since we use strategy = GenerationType.SEQUENCE
        // and there`s no unique fields
        return 31;
    }

}
