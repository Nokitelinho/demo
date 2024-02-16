package com.ibsplc.neoicargo.cca.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CCAAWBDetailData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String currency;

    @JsonProperty("awb_freight_payment_type")
    private String payType;

    @JsonProperty("awb_other_charge_payment_type")
    private String awbOtherChargePaymentType;

    private Double weight;

    private Double volume;

    @JsonProperty("volumetric_weight")
    private Double volumetricWeight;

    @JsonProperty("adjusted_weight")
    private Double adjustedWeight;

    @JsonProperty("chargeable_weight")
    private Double chargeableWeight;

    @JsonProperty("iata_rate")
    private Double iataRate;

    @JsonProperty("market_rate")
    private Double marketRate;

    @JsonProperty("wgt_charge")
    private Double weightCharge;

    @JsonProperty("val_charge")
    private Double valuationCharge;

    @JsonProperty("net_charge")
    private Double netCharge;

    @JsonProperty("discount_amount")
    private Double discountAmount;

    @JsonProperty("commission_amount")
    private Double commissionAmount;

    @JsonProperty("inbound_customer_code")
    private String inboundCustomerCode;

    @JsonProperty("outbound_customer_code")
    private String outboundCustomerCode;

    private String origin;

    private String destination;

    private Integer pieces;

    @JsonProperty("product_code")
    private String productCode;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("service_cargo_class")
    private String serviceCargoClass;

    @JsonProperty("agent_code")
    private String agentCode;

    @JsonProperty("inbound_customer_details")
    private CcaCustomerDetailData inboundCustomerDetails;

    @JsonProperty("outbound_customer_details")
    private CcaCustomerDetailData outboundCustomerDetails;

    @JsonProperty("cca_awb_details")
    private List<CcaDetailData> ratingDetails;

    @JsonProperty("cca_awb_charges")
    private List<CcaChargeDetailData> awbCharges;

    @JsonProperty("cca_awb_rates")
    private List<CcaRateDetailData> awbRates;

    @JsonProperty("cca_routing_details")
    private List<CcaRoutingData> ccaAwbRoutingDetails;

    @JsonProperty("cca_awb_taxes")
    private List<CcaTaxDetailsData> awbTaxes;

    @JsonProperty("display_tax_details")
    private List<CcaTaxValueData> displayTaxDetails;

    @JsonProperty("tax_amount")
    private double taxAmount;

    @JsonProperty("handling_code")
    private String handlingCode;

    @JsonProperty("shipping_date")
    private String shippingDate;

    @JsonProperty("total_non_awb_charge")
    private Double totalNonAWBCharge;

    @JsonProperty("awb_pp_tax_amount")
    private Double awbPPTaxAmount;

    @JsonProperty("awb_cc_tax_amount")
    private Double awbCCTaxAmount;

    @JsonProperty("cc_fee")
    private Double ccfee;

    @JsonProperty("ccfee_currency")
    private String ccfeeCurrency;

    @JsonProperty("higher_weight_break_rate")
    private String higherWeightBreakRate;

    @JsonProperty("dvfor_carriage")
    private double dvForCarriage;

    @JsonProperty("insurance_amount")
    private double insuranceAmount;

    @JsonProperty("agent_name")
    private String agentName;

    @JsonProperty("agent_station")
    private String agentStation;

    @JsonProperty("spot_rate_id")
    private String spotRateId;

    @JsonProperty("spot_rate_status")
    private String spotRateStatus;

    @JsonProperty("spot_category")
    private String spotCategory;

    @JsonProperty("requested_spot_value")
    private Double requestedSpotValue;

    @JsonProperty("apply_spot_rate_on_commission")
    private Boolean applySpotRateOnCommission;

    @JsonProperty("total_due_agt_ccf_chg")
    private Double totalDueAgtCCFChg;

    @JsonProperty("total_due_agt_ppd_chg")
    private Double totalDueAgtPPDChg;

    @JsonProperty("total_due_car_ccf_chg")
    private Double totalDueCarCCFChg;

    @JsonProperty("total_due_car_ppd_chg")
    private Double totalDueCarPPDChg;

    @JsonProperty("export_billing_status")
    private String exportBillingStatus;

    @JsonProperty("import_billing_status")
    private String importBillingStatus;

    @JsonProperty("shipper_code")
    private String shipperCode;

    @JsonProperty("consignee_code")
    private String consigneeCode;

    @JsonProperty("execution_date")
    private String executionDate;

    @JsonProperty("execution_station")
    private String executionStation;

    @JsonProperty("agent_iata_code")
    private String agentIataCode;

    @JsonProperty("rated_customer")
    private String ratedCustomer;

    @JsonProperty("total_other_charges")
    private double totalOtherCharges;

    @JsonProperty("first_flight_date")
    private String firstFlightDate;

    @JsonProperty("shipment_status")
    private String shipmentStatus;

    @JsonProperty("outbound_fop")
    private String outboundFop;

    @JsonProperty("inbound_fop")
    private String inboundFop;

    @JsonProperty("net_value_export")
    private Double netValueExport;

    @JsonProperty("net_value_import")
    private Double netValueImport;
}