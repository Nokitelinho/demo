package com.ibsplc.neoicargo.cca.dao.entity;

import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

import static com.ibsplc.neoicargo.cca.util.CcaUtil.getRoundedMoneyAmount;

@Entity
@Table(name = "ccaawbrat")
@Getter
@Setter
@NoArgsConstructor
public class CCAAwbRateDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ccaawbratsernum", updatable = false, nullable = false)
    @SequenceGenerator(name = "CCAAWBRATDTLSEQ", sequenceName = "CCAAWBRAT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CCAAWBRATDTLSEQ")
    private Long serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(value = {@JoinColumn(name = "ccasernum", referencedColumnName = "ccasernum", nullable = false),
            @JoinColumn(name = "rectyp", referencedColumnName = "rectyp", nullable = false)})
    private CCAAwb ccaawb;

    @Column(name = "rattyp")
    private String rateType;

    @Column(name = "comcod")
    private String commodityCode;

    @Column(name = "chgwgt")
    private double chargeableWeight;

    @Column(name = "dischgwgt")
    private double displayChargeableWeight;

    @Column(name = "chg")
    private double charge;

    @Type(type = "jsonb")
    @Column(name = "ratdtl", columnDefinition = "json")
    private Object rateDetails;

    @Column(name = "rat")
    private double rate;

    public CCAAwbRateDetail update(CcaRateDetailsVO rateDetailsVO) {
        setRateType(rateDetailsVO.getRateType());
        setChargeableWeight(rateDetailsVO.getChargeableWeight());
        setDisplayChargeableWeight(rateDetailsVO.getDisplayChargeableWeight());
        setCharge(getRoundedMoneyAmount(rateDetailsVO::getCharge));
        setRateDetails(rateDetailsVO.getRateDetails());
        setRate(rateDetailsVO.getRate());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CCAAwbRateDetail that = (CCAAwbRateDetail) o;
        return that.serialNumber != null && that.serialNumber.equals(serialNumber);
    }

    @Override
    public int hashCode() {
        // should be fixed since we use strategy = GenerationType.SEQUENCE
        // and there`s no unique fields
        return 31;
    }
}
