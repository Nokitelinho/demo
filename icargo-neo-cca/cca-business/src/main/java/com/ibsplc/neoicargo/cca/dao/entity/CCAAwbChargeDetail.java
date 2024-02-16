package com.ibsplc.neoicargo.cca.dao.entity;

import com.ibsplc.neoicargo.framework.tenant.audit.Audit;
import com.ibsplc.neoicargo.framework.tenant.audit.Auditable;
import com.ibsplc.neoicargo.framework.tenant.audit.DisplayType;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AWB_CHARGE_DETAIL_GROUP;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.FLAG_Y;

@Entity
@Table(name = "ccaawbchgdtl")
@Getter
@Setter
@NoArgsConstructor
@Auditable(eventName = "cca", allFields = false)
public class CCAAwbChargeDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ccaawbchgsernum")
    @SequenceGenerator(name = "CCAAWBCHGSEQ", sequenceName = "CCAAWBCHGDTL_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CCAAWBCHGSEQ")
    private Long serialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(value = {@JoinColumn(name = "ccasernum", referencedColumnName = "ccasernum", nullable = false),
            @JoinColumn(name = "rectyp", referencedColumnName = "rectyp", nullable = false)})
    private CCAAwb ccaawb;

    @Audit(name = "charge", changeGroupId = CCA_AWB_CHARGE_DETAIL_GROUP)
    @Column(name = "chg")
    private double charge;

    @Column(name = "paytyp", length = 50)
    private String paymentType;

    @Audit(name = "dueCarrier", changeGroupId = CCA_AWB_CHARGE_DETAIL_GROUP, include = DisplayType.ALWAYS)
    @Column(name = "duecarflg")
    private String dueCarrier;

    @Audit(name = "dueAgent", changeGroupId = CCA_AWB_CHARGE_DETAIL_GROUP, include = DisplayType.ALWAYS)
    @Column(name = "dueagtflg")
    private String dueAgent;

    @Audit(name = "chargeHead", changeGroupId = CCA_AWB_CHARGE_DETAIL_GROUP, include = DisplayType.ALWAYS)
    @Column(name = "chghed")
    private String chargeHead;

    @Audit(name = "chargeHeadCode", changeGroupId = CCA_AWB_CHARGE_DETAIL_GROUP, include = DisplayType.ALWAYS)
    @Column(name = "chdcod")
    private String chargeHeadCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CCAAwbChargeDetail that = (CCAAwbChargeDetail) o;
        return that.serialNumber != null && that.serialNumber.equals(serialNumber);
    }

    @Override
    public int hashCode() {
        // should be fixed since we use strategy = GenerationType.SEQUENCE
        // and there`s no unique fields
        return 31;
    }

    @Override
    public BaseEntity getParentEntity() {
        return this.ccaawb;
    }

    @Override
    public String getIdAsString() {
        String type = FLAG_Y.equalsIgnoreCase(getDueAgent()) ? "OCDA" : "OCDC";
        return getParentEntity().getIdAsString() + "-" + type + "-" + getChargeHeadCode();
    }

}