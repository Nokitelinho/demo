package com.ibsplc.neoicargo.cca.dao.entity;

import com.ibsplc.neoicargo.cca.mapper.CcaAwbDetailMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaCustomerDetailMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaTaxMapper;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
import com.ibsplc.neoicargo.framework.tenant.audit.Audit;
import com.ibsplc.neoicargo.framework.tenant.audit.Auditable;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.framework.util.unit.EUnitOfQuantity;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.cca.constants.CcaAuditConstants.CCA_AWB_GROUP;
import static com.ibsplc.neoicargo.cca.util.CcaUtil.isNullOrEmpty;
import static com.ibsplc.neoicargo.cca.util.QuantityUtil.getDisplayValue;
import static com.ibsplc.neoicargo.cca.util.QuantityUtil.getValue;

@Entity
@Table(name = "ccaawb")
@Getter
@Setter
@NoArgsConstructor
@Auditable(eventName = "cca", allFields = false)
public class CCAAwb extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CCAAwbPk ccaAwbPk;

    @MapsId("ccaSerialNumber")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ccasernum", referencedColumnName = "ccasernum", nullable = false)
    private CcaMaster ccaMaster;

    @Column(name = "curcod")
    private String currency;

    @Audit(name = "payType", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "paytyp")
    private String payType;

    @Audit(name = "pieces", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "pcs")
    private int pieces;

    @Embedded
    @AttributeOverride(name = "weight", column = @Column(name = "diswgtunt"))
    @AttributeOverride(name = "volume", column = @Column(name = "disvolunt"))
    private EUnitOfQuantity unitOfQuantity;

    @Column(name = "volwgt")
    private Double volumetricWeight;

    @Audit(name = "displayVolumetricWeight", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "disvolwgt")
    private Double displayVolumetricWeight;

    @Column(name = "adjwgt")
    private Double adjustedWeight;

    @Audit(name = "displayAdjustedWeight", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "disadjwgt")
    private Double displayAdjustedWeight;

    @Column(name = "chgwgt")
    private Double chargeableWeight;

    @Audit(name = "displayChargeableWeight", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "dischgwgt")
    private Double displayChargeableWeight;

    @Column(name = "wgt")
    private Double weight;

    @Audit(name = "displayWeight", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "diswgt")
    private Double displayWeight;

    @Column(name = "vol")
    private Double volume;

    @Audit(name = "displayVolume", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "disvol")
    private Double displayVolume;

    @Audit(name = "origin", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "orgcod")
    private String origin;

    @Audit(name = "destination", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "dstcod")
    private String destination;

    @Audit(name = "inboundCustomerCode", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "inbcuscod")
    private String inboundCustomerCode;

    @Audit(name = "outboundCustomerCode", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "outcuscod")
    private String outboundCustomerCode;

    @Column(name = "agtcod")
    private String agentCode;

    @Audit(name = "productCode", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "prdcod")
    private String productCode;

    @Audit(name = "productName", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "prdnam")
    private String productName;

    @Audit(name = "serviceCargoClass", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "srvcrgcls")
    private String serviceCargoClass;

    @Column(name = "iatrat")
    private double iataRate;

    @Audit(name = "weightCharge", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "awbwgtchg")
    private double weightCharge;

    @Column(name = "awbvalchg")
    private double valuationCharge;

    @Column(name = "mktrat")
    private double marketRate;

    @Audit(name = "netCharge", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "awbnetchg")
    private double netCharge;

    @Audit(name = "totalDueAgtCCFChg", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "awbdueagtccfchg")
    private double totalDueAgtCCFChg;

    @Audit(name = "totalDueAgtPPDChg", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "awbdueagtppdchg")
    private double totalDueAgtPPDChg;

    @Audit(name = "totalDueCarCCFChg", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "awbduecarccfchg")
    private double totalDueCarCCFChg;

    @Audit(name = "totalDueCarPPDChg", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "awbduecarppdchg")
    private double totalDueCarPPDChg;

    @Column(name = "awbnonawbchg")
    private double totalNonAWBCharge;

    @Column(name = "chgcolfee")
    private double ccfee;

    @Column(name = "chgcolfeecur")
    private String ccfeeCurrency;

    @Audit(name = "commissionAmount", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "awbcmsamt")
    private double commissionAmount;

    @Audit(name = "discountAmount", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "awbdisamt")
    private double discountAmount;

    @Audit(name = "awbTaxPP", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "awbtaxppd")
    private double awbTaxPP;

    @Audit(name = "awbTaxCC", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "awbtaxccf")
    private double awbTaxCC;

    @Column(name = "hdlcod")
    private String handlingCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ccaawb", orphanRemoval = true)
    private Set<CCAAwbDetail> awbDetails;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ccaawb", orphanRemoval = true)
    private Set<CCAAwbRateDetail> awbRates;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ccaawb", orphanRemoval = true)
    private Set<CCAAwbChargeDetail> awbCharges;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ccaawb", orphanRemoval = true)
    private Set<CcaAwbRoutingDetails> ccaAwbRoutingDetails;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ccaawb", orphanRemoval = true)
    private Set<CCACustomerDetail> ccaCustomerDetails;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "ccaawb", orphanRemoval = true)
    private Set<CcaAwbTaxDetails> awbTaxes;

    @Audit(name = "totalTaxAmount", changeGroupId = CCA_AWB_GROUP)
    @Column(name = "tottaxamt")
    private double totalTaxAmount;

    @Type(type = "jsonb")
    @Column(name = "distaxdtl", columnDefinition = "json")
    private Object displayTaxDetails;

    @Column(name = "sptratidr")
    private String spotRateId;

    @Column(name = "sptratsta")
    private String spotRateStatus;

    @Column(name = "shpdat")
    private LocalDate shippingDate;

    @Column(name = "expduearl")
    private Double netValueExport;

    @Column(name = "impduearl")
    private Double netValueImport;

    @Column(name = "outfop")
    private String outboundFop;

    @Column(name = "inbfop")
    private String inboundFop;

    public CCAAwb update(@NotNull final CcaStatus ccaStatus,
                         @NotNull final CcaAwbVO ccaAwbVO,
                         @NotNull final QuantityMapper quantityMapper,
                         @NotNull final CcaAwbDetailMapper ccaAwbDetailMapper,
                         @NotNull final CcaCustomerDetailMapper ccaCustomerDetailMapper,
                         @NotNull final CcaTaxMapper ccaTaxMapper) {
        setCurrency(ccaAwbVO.getCurrency());
        setPayType(ccaAwbVO.getPayType());

        final var eUnitOfQuantity = new EUnitOfQuantity();
        if (ccaAwbVO.getVolume() != null) {
            eUnitOfQuantity.setVolume(
                    quantityMapper.unitConvetion(ccaAwbVO.getVolume().getDisplayUnit())
            );
        }
        if (ccaAwbVO.getVolumetricWeight() != null) {
            eUnitOfQuantity.setWeight(
                    quantityMapper.unitConvetion(ccaAwbVO.getVolumetricWeight().getDisplayUnit())
            );
        }
        setUnitOfQuantity(eUnitOfQuantity);

        setVolumetricWeight(getValue(ccaAwbVO.getVolumetricWeight()));
        setDisplayVolumetricWeight(getDisplayValue(ccaAwbVO.getVolumetricWeight()));
        setChargeableWeight(getValue(ccaAwbVO.getChargeableWeight()));
        setDisplayChargeableWeight(getDisplayValue(ccaAwbVO.getChargeableWeight()));
        setAdjustedWeight(getValue(ccaAwbVO.getAdjustedWeight()));
        setDisplayAdjustedWeight(getDisplayValue(ccaAwbVO.getAdjustedWeight()));
        setWeight(getValue(ccaAwbVO.getWeight()));
        setDisplayWeight(getDisplayValue(ccaAwbVO.getWeight()));
        setVolume(getValue(ccaAwbVO.getVolume()));
        setDisplayVolume(getDisplayValue(ccaAwbVO.getVolume()));
        setTotalNonAWBCharge(ccaAwbVO.getAwbQualityAuditDetail() != null
                ? ccaAwbVO.getAwbQualityAuditDetail().getTotalNonAWBCharge()
                : 0.0);
        setPieces(ccaAwbVO.getPieces());
        setDestination(ccaAwbVO.getDestination());
        setOrigin(ccaAwbVO.getOrigin());
        setInboundCustomerCode(ccaAwbVO.getInboundCustomerCode());
        setOutboundCustomerCode(ccaAwbVO.getOutboundCustomerCode());
        setProductCode(ccaAwbVO.getProductCode());
        setServiceCargoClass(ccaAwbVO.getServiceCargoClass());
        setWeightCharge(ccaAwbVO.getWeightCharge());
        setNetCharge(ccaAwbVO.getNetCharge());
        setValuationCharge(ccaAwbVO.getValuationCharge());
        setCommissionAmount(ccaAwbVO.getCommissionAmount());
        setTotalDueAgtCCFChg(ccaAwbVO.getTotalDueAgtCCFChg());
        setTotalDueAgtPPDChg(ccaAwbVO.getTotalDueAgtPPDChg());
        setTotalDueCarCCFChg(ccaAwbVO.getTotalDueCarCCFChg());
        setTotalDueCarPPDChg(ccaAwbVO.getTotalDueCarPPDChg());
        setTotalTaxAmount(ccaAwbVO.getTaxAmount());
        setDiscountAmount(ccaAwbVO.getDiscountAmount());
        setCcfee(ccaAwbVO.getCcfee());
        setCcfeeCurrency(ccaAwbVO.getCcfeeCurrency());
        setIataRate(ccaAwbVO.getIataRate());
        setMarketRate(ccaAwbVO.getMarketRate());
        setProductName(ccaAwbVO.getProductName());
        setAgentCode(ccaAwbVO.getAgentCode());
        setSpotRateId(ccaAwbVO.getSpotRateId());
        setSpotRateStatus(ccaAwbVO.getSpotRateStatus());
        setShippingDate(ccaAwbVO.getShippingDate());
        setHandlingCode(ccaAwbVO.getHandlingCode());
        setInboundFop(ccaAwbVO.getInboundFop());
        setOutboundFop(ccaAwbVO.getOutboundFop());
        updateAwbDetails(ccaAwbVO, quantityMapper, ccaAwbDetailMapper);
        updateAwbRateDetails(ccaAwbVO, ccaAwbDetailMapper);
        if (!CcaStatus.A.equals(ccaStatus)) {
            updateAwbCharges(ccaAwbVO, ccaAwbDetailMapper);
        }
        updateCcaAwbRoutingDetails(ccaAwbVO, ccaAwbDetailMapper);
        updateCustomerDetails(ccaAwbVO, ccaCustomerDetailMapper);
        updateTaxDetails(ccaAwbVO, ccaTaxMapper);
        setNetValueExport(ccaAwbVO.getNetValueExport().getAmount().doubleValue());
        setNetValueImport(ccaAwbVO.getNetValueImport().getAmount().doubleValue());

        return this;
    }

    private void updateTaxDetails(CcaAwbVO ccaAwbVO, CcaTaxMapper ccaTaxMapper) {
        if (this.awbTaxes != null) {
            this.awbTaxes.clear();
        } else {
            this.awbTaxes = new HashSet<>();
        }
        if (!isNullOrEmpty(ccaAwbVO.getAwbTaxes())) {
            var awbTaxesDetails = ccaAwbVO.getAwbTaxes()
                    .stream()
                    .map(ccaTaxDetailsVO -> {
                        var awbTaxDetails = ccaTaxMapper.fromCcaAwbVOToCcaAwbTaxDetails(ccaTaxDetailsVO);
                        awbTaxDetails.setCcaawb(this);
                        awbTaxDetails.setCcaMaster(this.getCcaMaster());
                        awbTaxDetails.setRecordType(this.getCcaAwbPk().getRecordType());
                        return awbTaxDetails;
                    })
                    .collect(Collectors.toSet());
            this.awbTaxes.addAll(awbTaxesDetails);
        }
        this.displayTaxDetails = ccaAwbVO.getDisplayTaxDetails();
    }

    private void updateCustomerDetails(CcaAwbVO shipmentDetailVO, CcaCustomerDetailMapper ccaCustomerDetailMapper) {
        if (ccaCustomerDetails != null) {
            ccaCustomerDetails.clear();
        } else {
            setCcaCustomerDetails(new HashSet<>());
        }

        final var customerDetailVOS = shipmentDetailVO.getCcaCustomerDetails();
        if (customerDetailVOS != null) {
            customerDetailVOS.forEach(ccaCustomerDetail -> {
                final var customerDetail = ccaCustomerDetailMapper.constructCcaCustomerDetail(ccaCustomerDetail);
                customerDetail.setCcaawb(this);
                ccaCustomerDetails.add(customerDetail);
            });
        }
    }

    public void updateAwbDetails(CcaAwbVO shipmentDetailVO,
                                 QuantityMapper quantityMapper,
                                 CcaAwbDetailMapper ccaAwbDetailMapper) {
        /* multiple entry with same commodity code needs to be checked */
        final var ratingDetails = shipmentDetailVO.getRatingDetails();
        if (ratingDetails == null) {
            if (awbDetails != null) {
                awbDetails.clear();
            }
        } else {
            awbDetails.forEach(awbDetail ->
                ratingDetails.stream()
                        .filter(ratingDetail ->
                                awbDetail.getSerialNumber() != null
                                        && ratingDetail.getSerialNumber() != null
                                        && ratingDetail.getSerialNumber().equals(awbDetail.getSerialNumber())
                        )
                        .findFirst()
                        .or(() ->
                                ratingDetails.stream()
                                        .filter(ratingDetail -> awbDetail.getCommodityCode() != null
                                                && ratingDetail.getCommodityCode() != null
                                                && ratingDetail.getCommodityCode().equals(awbDetail.getCommodityCode()))
                                        .findFirst()
                        )
                        .ifPresentOrElse(
                                ratingDetail -> awbDetail.update(ratingDetail, quantityMapper, ccaAwbDetailMapper),
                                () -> awbDetails.remove(awbDetail)
                        )
            );
        }
    }

    public void updateAwbRateDetails(CcaAwbVO shipmentDetailVO, CcaAwbDetailMapper ccaAwbDetailMapper) {
        final var awbDetailRates = shipmentDetailVO.getAwbRates();
        if (awbDetailRates == null) {
            if (awbRates != null) {
                awbRates.clear();
            }
        } else {
            if (awbRates == null) {
                setAwbRates(new HashSet<>());
                awbDetailRates.forEach(ratingDetailVO -> {
                    final var ccaAwbRateDetail = ccaAwbDetailMapper.constructAwbRate(ratingDetailVO);
                    ccaAwbRateDetail.setCcaawb(this);
                    awbRates.add(ccaAwbRateDetail);
                });
            } else {
                final var rateDetailsMap = new HashMap<String, CcaRateDetailsVO>();
                awbDetailRates.forEach(
                        awbRatDetailVO -> rateDetailsMap.put(getRateDetailKey(awbRatDetailVO), awbRatDetailVO));
                awbRates.stream()
                        .filter(x -> !rateDetailsMap.containsKey(x.getRateType().concat(x.getCommodityCode())))
                        .forEach(awbRates::remove);
                awbRates.forEach(ratDetail -> {
                    ratDetail.update(rateDetailsMap.get(ratDetail.getRateType().concat(ratDetail.getCommodityCode())));
                    rateDetailsMap.remove(ratDetail.getRateType().concat(ratDetail.getCommodityCode()));
                });
                rateDetailsMap.values().forEach(ratingDetailVO -> {
                    final var ccaAwbRateDetail = ccaAwbDetailMapper.constructAwbRate(ratingDetailVO);
                    ccaAwbRateDetail.setCcaawb(this);
                    awbRates.add(ccaAwbRateDetail);
                });
            }
        }
    }

    @NotNull
    private String getRateDetailKey(CcaRateDetailsVO awbRatDetailVO) {
        return awbRatDetailVO.getRateType().concat(awbRatDetailVO.getCommodityCode());
    }

    public void updateAwbCharges(CcaAwbVO shipmentDetailVO, CcaAwbDetailMapper ccaAwbDetailMapper) {
        if (awbCharges != null) {
            awbCharges.clear();
        } else {
            setAwbCharges(new HashSet<>());
        }

        final var detailVOAwbChargesVOs = shipmentDetailVO.getAwbCharges();
        if (detailVOAwbChargesVOs != null) {
            detailVOAwbChargesVOs.forEach(awbCharge -> {
                final var awbChargeDetail = ccaAwbDetailMapper.constructAwbChargeDetail(awbCharge);
                awbChargeDetail.setCcaawb(this);
                awbCharges.add(awbChargeDetail);
            });
        }
    }

    public void updateCcaAwbRoutingDetails(CcaAwbVO shipmentDetailVO, CcaAwbDetailMapper ccaAwbDetailMapper) {
        if (ccaAwbRoutingDetails != null) {
            ccaAwbRoutingDetails.clear();
        } else {
            setCcaAwbRoutingDetails(new HashSet<>());
        }

        final var routingDetails = shipmentDetailVO.getCcaAwbRoutingDetails();
        if (routingDetails != null) {
            routingDetails.forEach(detail -> {
                final var entity = ccaAwbDetailMapper.constructCcaAwbRoutingDetails(detail);
                entity.setCcaawb(this);
                ccaAwbRoutingDetails.add(entity);
            });
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CCAAwb ccaAwb = (CCAAwb) o;
        return Objects.equals(ccaAwbPk, ccaAwb.ccaAwbPk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ccaAwbPk);
    }

    @Override
    public BaseEntity getParentEntity() {
        return this.ccaMaster;
    }

    @Override
    public String getIdAsString() {
        return getParentEntity().getIdAsString() + "-" + ccaAwbPk.getRecordType();
    }

}
