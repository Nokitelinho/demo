package com.ibsplc.neoicargo.cca.vo;

import com.ibsplc.neoicargo.businessrules.client.annotation.FactObject;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.util.currency.Money;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Volume;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import com.ibsplc.neoicargo.pricing.annotations.PricingFact;
import com.ibsplc.neoicargo.pricing.annotations.PricingParameters;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CcaAwbVO extends AbstractVO {

    private static final long serialVersionUID = 1L;

    private String recordType;

    private String currency;

    @PricingFact(code = PricingParameters.PAYMENT_TYPE)

    @FactObject("paymentType")
    private String payType;

    private String awbOtherChargePaymentType;

    private Quantity<Weight> volumetricWeight;

    private Quantity<Weight> adjustedWeight;

    private Quantity<Weight> chargeableWeight;

    private int pieces;

    private Quantity<Weight> weight;

    private Quantity<Volume> volume;

    @FactObject("awbOriginAirport")
    private String origin;

    @FactObject("inboundCustomerCode")
    private String inboundCustomerCode;

    @FactObject("outboundCustomerCode")
    private String outboundCustomerCode;

    @FactObject("awbDestinationAirport")
    private String destination;

    private Set<CcaCustomerDetailVO> ccaCustomerDetails;

    private String productCode;

    @FactObject("productCodes")
    @PricingFact(code = PricingParameters.PRODUCT_CODE, group = PricingParameters.PRODUCT_GROUP)
    private String productName;

    @PricingFact(code = PricingParameters.AGENT_CODE, group = PricingParameters.AGENT_GROUP)
    @FactObject("agentCode")
    private String agentCode;

    @FactObject("serviceCargoClass")
    private String serviceCargoClass;

    @FactObject("weightCharge")
    private double weightCharge;

    @FactObject("valuationCharge")
    private double valuationCharge;

    @FactObject("netCharge")
    private double netCharge;

    @FactObject("totalDueAgtCCFChg")
    private double totalDueAgtCCFChg;

    @FactObject("totalDueAgtPPDChg")
    private double totalDueAgtPPDChg;

    @FactObject("totalDueCarCCFChg")
    private double totalDueCarCCFChg;

    @FactObject("totalDueCarPPDChg")
    private double totalDueCarPPDChg;

    @FactObject("ccfee")
    private double ccfee;

    private String ccfeeCurrency;

    @FactObject("discountAmount")
    private double discountAmount;

    @FactObject("commissionAmount")
    private double commissionAmount;

    private double taxAmount;

    private String handlingCode;

    private Collection<CcaRatingDetailVO> ratingDetails;

    @FactObject("awbCharges")
    private Collection<CcaChargeDetailsVO> awbCharges;

    @FactObject("ratingDetails")
    private Collection<CcaRateDetailsVO> awbRates;

    private Set<CcaAwbRoutingDetailsVO> ccaAwbRoutingDetails;

    private Collection<CcaTaxDetailsVO> awbTaxes;

    private double iataRate;

    private double marketRate;

    private double totalNonAWBCharge;

    private double awbPPTaxAmount;

    private double awbCCTaxAmount;

    private String shipmentPrefix;

    private String masterDocumentNumber;

    @PricingFact(code = PricingParameters.AGENT_IATA_CODE)
    private String agentIataCode;

    private String agentName;

    private Collection<CcaRoutingVO> routingDetails;

    private LocalDate shippingDate;

    @FactObject("spotRateId")
    private String spotRateId;

    @FactObject("spotRateStatus")
    private String spotRateStatus;

    private String spotCategory;

    private double requestedSpotValue;

    @FactObject("totalOtherCharges")
    private double totalOtherCharges;

    private Units unitOfMeasure;

    @PricingFact(code = PricingParameters.SHIPPER_CODE, group = PricingParameters.SHIPPER_GROUP)
    private String shipperCode;

    @PricingFact(code = PricingParameters.CONSIGNEE_CODE)
    private String consigneeCode;

    private boolean isAwbCaptured;

    private String ubrNumber;

    private String shipmentStatus;

    private double chgWeight;

    private double grsWeight;

    private double volWeight;

    private double adjWeight;

    private LocalDate executionDate;

    private String executionStation;

    private LocalDateTime captureDate;

    private String dataCaptureFlag;

    private String inboundCustomerName;

    private String outboundCustomerName;

    private String inboundAccountNumber;

    private String outboundAccountNumber;

    private String saveShipmentDetails;

    private String qualityAuditStatus;

    private Object notificationDetails;

    private LocalDateTime lastUpdateTime;

    private String lastUpdateUser;

    private boolean isQualityAudited;

    private String specialCategoryCode;

    private String billingStatus;

    private boolean applySpotRateOnCommission;

    private String autoSetSccCode;

    @PricingFact(code = PricingParameters.PROMO_CODE)
    private String promotionCode;

    private double commissionPercentage;

    private String source;

    private CcaDetailVO awbQualityAuditDetail;

    private CcaPaymentAdviceDetailsVO paymentAdviceDetailsVO;

    private String exportBillingType;

    private String importBillingType;

    private double interlineRevenue;

    private String awbRemarks;

    private String displayWeightUnit;

    private String displayVolumeUnit;

    private String autoCorrectFlag;

    @FactObject("outboundfop")
    private String outboundFop;

    private Integer totalPieces;

    @FactObject("inboundfop")
    private String inboundFop;

    private String qualityAuditVerified;

    @PricingFact(code = PricingParameters.CUSTOMER_CODE, group = PricingParameters.CUSTOMER_GROUP)
    private String ratedCustomer;

    private String higherWeightBreakRate;

    private String sciCode;

    private String exportBillingStatus;

    private String importBillingStatus;

    @PricingFact(code = PricingParameters.AIRLINE_CODE)
    private String ownerCode;

    private int documentOwnerId;

    private double dvForCustoms;

    private double dvForCarriage;

    private double insuranceAmount;

    private double insurancePercentage;

    private String agentStation;

    private String ratingParameter;

    private boolean cyclicRoute;

    private String qualityAuditMode;

    private LocalDateTime lastUpdatedTime;

    private String lastUpdatedUser;

    @FactObject("awbValue")
    private double awbValue;

    private CcaProrationVO awbProrationDetail;

    private String exportPayAdviceNumber;

    private String importPayAdviceNumber;

    @PricingFact(code = PricingParameters.STATION, group = PricingParameters.STATION_GROUP)
    private String stationCode;

    @PricingFact(code = PricingParameters.DAY_OF_WEEK, group = PricingParameters.DAY_OF_WEEK_GROUP)
    int dayOfWeek;

    private String companyCode;

    private String nonAwbChargeModifiedFlag;

    private String routeParameterValue;

    private double totalDryIceWeight;

    private Object displayTaxDetails;

    private String triggerPoint;

    private Money netValueExport;

    private Money netValueImport;
}