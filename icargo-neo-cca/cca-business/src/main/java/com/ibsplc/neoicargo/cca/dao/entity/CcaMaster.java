package com.ibsplc.neoicargo.cca.dao.entity;

import com.ibsplc.neoicargo.cca.mapper.CcaAwbDetailMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaCustomerDetailMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaTaxMapper;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.tenant.audit.Audit;
import com.ibsplc.neoicargo.framework.tenant.audit.Auditable;
import com.ibsplc.neoicargo.framework.tenant.audit.DisplayType;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_MASTER_GROUP;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.AUTO_CCA_SOURCE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.FLAG_N;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.FLAG_Y;

@Entity
@Table(name = "ccamst")
@Getter
@Setter
@NoArgsConstructor
@Auditable(eventName = "cca", allFields = false, isParent = true)
public class CcaMaster extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ccasernum")
    @SequenceGenerator(name = "CCAMSTSEQ", sequenceName = "CCAMST_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CCAMSTSEQ")
    private Long ccaSerialNumber;

    @Audit(name = "ccaReferenceNumber", changeGroupId = CCA_MASTER_GROUP, include = DisplayType.ALWAYS)
    @Column(name = "ccarefnum")
    private String ccaReferenceNumber;

    @Column(name = "ccaissdat")
    private LocalDate ccaIssueDate;
    @Column(name = "ccaissdattimutc")
    private LocalDateTime ccaIssueDateTimeInUTC;

    @Audit(name = "ccaType", changeGroupId = CCA_MASTER_GROUP, include = DisplayType.ALWAYS)
    @Column(name = "ccatyp")
    private String ccaType;

    @Column(name = "autccaflg")
    private String autoCCAFlag;

    @Column(name = "autcaltax")
    private String autoCalculateTax;

    @Audit(name = "ccaStatus", changeGroupId = "ccaMaster")
    @Column(name = "ccastatus")
    @Enumerated(EnumType.STRING)
    private CcaStatus ccaStatus;

    @Audit(name = "masterDocumentNumber", changeGroupId = CCA_MASTER_GROUP, include = DisplayType.ALWAYS)
    @Column(name = "mstdocnum")
    private String masterDocumentNumber;

    @Audit(name = "shipmentPrefix", changeGroupId = CCA_MASTER_GROUP, include = DisplayType.ALWAYS)
    @Column(name = "shppfx")
    private String shipmentPrefix;

    @Column(name = "awbsernum")
    private Long awbSerialNumber;

    @Column(name = "ccarmk")
    private String ccaRemarks;

    @Column(name = "ccarsn")
    private String ccaReason;

    @Audit(name = "ccaAssignee", changeGroupId = CCA_MASTER_GROUP)
    @Column(name = "ccaasgnee")
    private String ccaAssignee;

    @Column(name = "updagtflg")
    private String updateAgentFlag;

    @Audit(name = "ccaSource", changeGroupId = CCA_MASTER_GROUP)
    @Column(name = "ccasrc")
    private String ccaSource;

    @Column(name = "expblgsta")
    private String exportBillingStatus;

    @Column(name = "impblgsta")
    private String importBillingStatus;

    @Column(name = "exedat")
    private LocalDate executionDate;

    @Column(name = "fstfltdat ")
    private LocalDate firstFlightDate;

    @Column(name = "exestn", length = 4)
    private String executionStation;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ccaMaster", orphanRemoval = true)
    private Set<CCAAwb> ccaAwb;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ccaMaster", orphanRemoval = true)
    private Set<CcaWorkflow> ccaWorkflow;

    @Type(type = "jsonb")
    @Column(name = "attfilnam", columnDefinition = "json")
    private Object ccaAttachments;

    public CcaMaster update(@NotNull final CCAMasterVO ccaMasterVO,
                            @NotNull final QuantityMapper quantityMapper,
                            @NotNull final CcaAwbDetailMapper ccaAwbDetailMapper,
                            @NotNull final CcaCustomerDetailMapper ccaCustomerDetailMapper,
                            @NotNull final CcaTaxMapper ccaTaxMapper) {
        setCcaType(ccaMasterVO.getCcaType());
        setCcaStatus(ccaMasterVO.getCcaStatus());
        setCcaRemarks(ccaMasterVO.getCcaRemarks());
        setCcaReason(ccaMasterVO.getCcaReason());
        setUpdateAgentFlag(ccaMasterVO.getUpdateAgentFlag());
        setCcaSource(ccaMasterVO.getCcaSource());
        setExportBillingStatus(ccaMasterVO.getExportBillingStatus());
        setImportBillingStatus(ccaMasterVO.getImportBillingStatus());
        setCcaAttachments(ccaMasterVO.getCcaAttachments());
        setAutoCalculateTax(FLAG_Y.equals(ccaMasterVO.getAutoCalculateTax()) ? FLAG_Y : FLAG_N);
        setAutoCCAFlag(AUTO_CCA_SOURCE.equals(ccaMasterVO.getCcaSource()) ? FLAG_Y : FLAG_N);
        setExecutionDate(ccaMasterVO.getExecutionDate());
        setFirstFlightDate(ccaMasterVO.getFirstFlightDate());
        setExecutionStation(ccaMasterVO.getExecutionStation());
        ccaAwb.forEach(awbInCca -> {
            if (CCA_RECORD_TYPE_REVISED.equals(awbInCca.getCcaAwbPk().getRecordType())) {
                awbInCca.update(this.ccaStatus, ccaMasterVO.getRevisedShipmentVO(),
                        quantityMapper, ccaAwbDetailMapper, ccaCustomerDetailMapper, ccaTaxMapper);
            }
        });
        return this;
    }

    @Override
    public String getIdAsString() {
        return shipmentPrefix + "-" + masterDocumentNumber + "-" + ccaReferenceNumber;
    }

    @Override
    public String getBusinessIdAsString() {
        return shipmentPrefix + "-" + masterDocumentNumber + "-" + ccaReferenceNumber;
    }

}
