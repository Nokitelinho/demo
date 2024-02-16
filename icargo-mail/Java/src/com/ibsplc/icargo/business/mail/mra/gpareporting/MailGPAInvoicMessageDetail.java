package com.ibsplc.icargo.business.mail.mra.gpareporting;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailInvoicMessageDetailVO;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@Entity
@Table(name = "MALMRAGPAINCMSGDTL")
public class MailGPAInvoicMessageDetail  {
	
	private  Log log = LogFactory.getLogger("MRA GPAREPORTING MailInvoicMessageDetail");

	private MailGPAInvoicMessageDetailPK mailInvoicMessageDetailPK;
	
	private String invoiceNumber;
	
	private String consignmentNumber;
	
	private String poaCode;
	
	private String mailCategoryCode;
	
	private  String mailSubClassCode;
	
	private String mailProductCode;
	
	private String mailBagId;
	
	private String paymentLevel;
	
	private String lateIndicator;
	
	private String rateTypeIndicator;
	
	private String payCycleIndicator;
	
	private String adjustmentReasonCode;
	
	private Calendar requiredDeliveryDate;
	
	private String containerNumber;
	
	private String claimAdjustmentCode;
	
	private String claimText;
	
	private String claimStatus;
	
	private String claimReasonCode;
	
	private double baseTotalAmount;
	
	private double adjustmentTotalAmount;
	
	private double lineHaulCharge;
	
	private double terminalHandlingCharge;
	
	private double otherValuationCharge;
	
	private double containerChargeAmount;
	
	private double charterChargeAmount;
	
	private Calendar possessionScanDate;
	
	private  double lineHaulDollarRate;
	
	private double lineHaulSDRRate;
	
	private double terminalHandlingDollarRate;
	
	private double terminalHandlingSDRRate;
	
	private double specialPerKiloDollarRate;
	
	private double specialPerKiloSDRRate;
	
	private double rangeDependentRate;
	
	private double activeIngredientRate;
	
	private double containerRate;
	
	private Calendar sdrDate;
	
	private double sdrRate;
	
	private Calendar consignmentCompletionDate;
	
	private Calendar transportationWindowEndTime;
	
	private String weightUnit;
	
	private double grossWeight;
	
	private String containerWeightUnit;
	
	private double containerGrossWeight;
	
	private String orginAirport;
	
	private String destinationAirport;
	
	private String offlineOriginAirport;
	
	private String originalOriginAirport;
	
	private String finalDestination;
	
	private String deliveryScanCarrierCode;
	
	private String deleiveryScanLocation;
	
	private Calendar deliveryDate;
	
	private String zeroPayReceptacleCode;
	
	private double terminalHandlingScanRate;
	
	private double sortRate;
	
	private double liveRate;
	
	private double  hubrelabelingRate;
	
	private  double additionalSeparationRate;
	
	private String adjustmentIndicator;
	
	private String misSentIndicator;
	
	private String scanPayIndicator;
	
	private String greatCircleMiles;
	
	private String greatCircleMilesUnit;
	
	private String payRateCode;
	
	private String contractType;
	
	private String carrierToPay;
	
	private String containerType;
	
	private Calendar assignedDate;

	private Calendar routeDepatureDate;
	private String carrierFinalDestination;
	private String controlNumber;
	private String regionCode;
	private String referenceVersionNumber;
	private String truckingLocation;
	private Calendar routeArrivalDate;
	private String originTripStagQualifier;
	private String originTripFlightNumber;
	private String originTripCarrierCode;
	
	private String possessionScanStagQualifier;
	private String possessionScanCarrierCode;
	private String possessionScanExpectedSite;
	
	private String loadScanCarrierCode;
	private String loadScanStagQualifier;
	private String loadScanFlightNumber;
	private String loadScanExpectedSite;
	private Calendar loadScanDate;
	
	private String firstTransferScanCarrier;
	private String firstTransferStagQualifier;
	private String firstTransferFlightNumber;
	private String firstTransferExpectedSite;
	private Calendar firstTransferDate;
	
	private String secondTransferScanCarrier;
	private String secondTransferStagQualifier;
	private String secondTransferFlightNumber;
	private String secondTransferExpectedSite;
	private Calendar secondTransferDate;
	
	private String thirdTransferScanCarrier;
	private String thirdTransferStagQualifier;
	private String thirdTransferFlightNumber;
	private String thirdTransferExpectedSite;
	private Calendar thirdTransferDate;
	
	private String fourthTransferScanCarrier;
	private String fourthTransferStagQualifier;
	private String fourthTransferFlightNumber;
	private String fourthTransferExpectedSite;
	private Calendar fourthTransferDate;
	
	private String deliverTransferStagQualifier;
	private String deliverScanActualSite;
	
	private double terminalHandlingScanningCharge;
	private double sortCharge;
	private double hubReLabelingCharge;
	private double liveCharge;
	private double additionalSeparationCharge;
	private double adjustedTerminalHandlingCharge;
	private String sortScanCarrier;
	private String sortScanExpectedSite;
	private String sortScanActualSite;
	private Calendar sortScanDate;
	private Calendar sortScanReceiveDate;
	private String claimNotes;


	@Column(name="INVNUM")
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	@Column(name="CSGDOCNUM")
	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	@Column(name="POACOD")
	public String getPoaCode() {
		return poaCode;
	}

	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	@Column(name="MALCTGCOD")
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}
	@Column(name="MALSUBCLSCOD")
	public String getMailSubClassCode() {
		return mailSubClassCode;
	}

	public void setMailSubClassCode(String mailSubClassCode) {
		this.mailSubClassCode = mailSubClassCode;
	}
	@Column(name="MALPRDTYP")
	public String getMailProductCode() {
		return mailProductCode;
	}

	public void setMailProductCode(String mailProductCode) {
		this.mailProductCode = mailProductCode;
	}
	@Column(name="MALIDR")
	public String getMailBagId() {
		return mailBagId;
	}

	public void setMailBagId(String mailBagId) {
		this.mailBagId = mailBagId;
	}
	@Column(name="PAYLVL")
	public String getPaymentLevel() {
		return paymentLevel;
	}

	public void setPaymentLevel(String paymentLevel) {
		this.paymentLevel = paymentLevel;
	}
	@Column(name="LATIND")
	public String getLateIndicator() {
		return lateIndicator;
	}

	public void setLateIndicator(String lateIndicator) {
		this.lateIndicator = lateIndicator;
	}
	@Column(name="RATTYPIND")
	public String getRateTypeIndicator() {
		return rateTypeIndicator;
	}

	public void setRateTypeIndicator(String rateTypeIndicator) {
		this.rateTypeIndicator = rateTypeIndicator;
	}
	@Column(name="PAYCYLIND")
	public String getPayCycleIndicator() {
		return payCycleIndicator;
	}

	public void setPayCycleIndicator(String payCycleIndicator) {
		this.payCycleIndicator = payCycleIndicator;
	}
	@Column(name="ADJRSNCOD")
	public String getAdjustmentReasonCode() {
		return adjustmentReasonCode;
	}

	public void setAdjustmentReasonCode(String adjustmentReasonCode) {
		this.adjustmentReasonCode = adjustmentReasonCode;
	}
	@Column(name="REQDLVDAT")
	public Calendar getRequiredDeliveryDate() {
		return requiredDeliveryDate;
	}

	public void setRequiredDeliveryDate(Calendar requiredDeliveryDate) {
		this.requiredDeliveryDate = requiredDeliveryDate;
	}
	@Column(name="CONNUM")
	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	@Column(name="CLMADJCOD")
	public String getClaimAdjustmentCode() {
		return claimAdjustmentCode;
	}

	public void setClaimAdjustmentCode(String claimAdjustmentCode) {
		this.claimAdjustmentCode = claimAdjustmentCode;
	}
	@Column(name="CLMTXT")
	public String getClaimText() {
		return claimText;
	}

	public void setClaimText(String claimText) {
		this.claimText = claimText;
	}
	@Column(name="CLMSTA")
	public String getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}
	@Column(name="CLMRSNCOD")
	public String getClaimReasonCode() {
		return claimReasonCode;
	}

	public void setClaimReasonCode(String claimReasonCode) {
		this.claimReasonCode = claimReasonCode;
	}
	@Column(name="BASTOTAMT")
	public double getBaseTotalAmount() {
		return baseTotalAmount;
	}

	public void setBaseTotalAmount(double baseTotalAmount) {
		this.baseTotalAmount = baseTotalAmount;
	}
	@Column(name="ADJTOTPMT")
	public double getAdjustmentTotalAmount() {
		return adjustmentTotalAmount;
	}

	public void setAdjustmentTotalAmount(double adjustmentTotalAmount) {
		this.adjustmentTotalAmount = adjustmentTotalAmount;
	}
	@Column(name="LINHALCHG")
	public double getLineHaulCharge() {
		return lineHaulCharge;
	}

	public void setLineHaulCharge(double lineHaulCharge) {
		this.lineHaulCharge = lineHaulCharge;
	}
	@Column(name="TERHNDCHG")
	public double getTerminalHandlingCharge() {
		return terminalHandlingCharge;
	}

	public void setTerminalHandlingCharge(double terminalHandlingCharge) {
		this.terminalHandlingCharge = terminalHandlingCharge;
	}
	@Column(name="OTHVALCHG")
	public double getOtherValuationCharge() {
		return otherValuationCharge;
	}

	public void setOtherValuationCharge(double otherValuationCharge) {
		this.otherValuationCharge = otherValuationCharge;
	}
	@Column(name="CONCHGAMT")
	public double getContainerChargeAmount() {
		return containerChargeAmount;
	}

	public void setContainerChargeAmount(double containerChargeAmount) {
		this.containerChargeAmount = containerChargeAmount;
	}
	@Column(name="CHTCHGAMT")
	public double getCharterChargeAmount() {
		return charterChargeAmount;
	}

	public void setCharterChargeAmount(double charterChargeAmount) {
		this.charterChargeAmount = charterChargeAmount;
	}
	@Column(name="POSSCNDAT")
	public Calendar getPossessionScanDate() {
		return possessionScanDate;
	}

	public void setPossessionScanDate(Calendar possessionScanDate) {
		this.possessionScanDate = possessionScanDate;
	}
	@Column(name="LINHALUSDRAT")
	public double getLineHaulDollarRate() {
		return lineHaulDollarRate;
	}

	public void setLineHaulDollarRate(double lineHaulDollarRate) {
		this.lineHaulDollarRate = lineHaulDollarRate;
	}
	@Column(name="LINHALSDRRAT")
	public double getLineHaulSDRRate() {
		return lineHaulSDRRate;
	}

	public void setLineHaulSDRRate(double lineHaulSDRRate) {
		this.lineHaulSDRRate = lineHaulSDRRate;
	}
	@Column(name="TERHDLUSDRAT")
	public double getTerminalHandlingDollarRate() {
		return terminalHandlingDollarRate;
	}

	public void setTerminalHandlingDollarRate(double terminalHandlingDollarRate) {
		this.terminalHandlingDollarRate = terminalHandlingDollarRate;
	}
	@Column(name="TERHDLSDRRAT")
	public double getTerminalHandlingSDRRate() {
		return terminalHandlingSDRRate;
	}

	public void setTerminalHandlingSDRRate(double terminalHandlingSDRRate) {
		this.terminalHandlingSDRRate = terminalHandlingSDRRate;
	}
	@Column(name="SPLPERKGMUSDRAT")
	public double getSpecialPerKiloDollarRate() {
		return specialPerKiloDollarRate;
	}

	public void setSpecialPerKiloDollarRate(double specialPerKiloDollarRate) {
		this.specialPerKiloDollarRate = specialPerKiloDollarRate;
	}
	@Column(name="SPLPERKGMSDRRAT")
	public double getSpecialPerKiloSDRRate() {
		return specialPerKiloSDRRate;
	}

	public void setSpecialPerKiloSDRRate(double specialPerKiloSDRRate) {
		this.specialPerKiloSDRRate = specialPerKiloSDRRate;
	}
	@Column(name="RNGDEPRAT")
	public double getRangeDependentRate() {
		return rangeDependentRate;
	}
	
	public void setRangeDependentRate(double rangeDependentRate) {
		this.rangeDependentRate = rangeDependentRate;
	}
	@Column(name="ACTINGRAT")
	public double getActiveIngredientRate() {
		return activeIngredientRate;
	}

	public void setActiveIngredientRate(double activeIngredientRate) {
		this.activeIngredientRate = activeIngredientRate;
	}
	@Column(name="CNTRAT")
	public double getContainerRate() {
		return containerRate;
	}

	public void setContainerRate(double containerRate) {
		this.containerRate = containerRate;
	}
	@Column(name="SDRDAT")
	public Calendar getSdrDate() {
		return sdrDate;
	}

	public void setSdrDate(Calendar sdrDate) {
		this.sdrDate = sdrDate;
	}
	@Column(name="SDRRAT")
	public double getSdrRate() {
		return sdrRate;
	}

	public void setSdrRate(double sdrRate) {
		this.sdrRate = sdrRate;
	}
	@Column(name="CSGCMPDAT")
	public Calendar getConsignmentCompletionDate() {
		return consignmentCompletionDate;
	}

	public void setConsignmentCompletionDate(Calendar consignmentCompletionDate) {
		this.consignmentCompletionDate = consignmentCompletionDate;
	}
	@Column(name="TRNSRVWNDDAT")
	public Calendar getTransportationWindowEndTime() {
		return transportationWindowEndTime;
	}

	public void setTransportationWindowEndTime(Calendar transportationWindowEndTime) {
		this.transportationWindowEndTime = transportationWindowEndTime;
	}
	@Column(name="MALWGTUNT")
	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	@Column(name="MALGRSWGT")
	public double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(double grossWeight) {
		this.grossWeight = grossWeight;
	}
	@Column(name="CONWGTUNT")
	public String getContainerWeightUnit() {
		return containerWeightUnit;
	}
	public void setContainerWeightUnit(String containerWeightUnit) {
		this.containerWeightUnit = containerWeightUnit;
	}
	@Column(name="CONGRSWGT")
	public double getContainerGrossWeight() {
		return containerGrossWeight;
	}

	public void setContainerGrossWeight(double containerGrossWeight) {
		this.containerGrossWeight = containerGrossWeight;
	}
	@Column(name="ORGARPCOD")
	public String getOrginAirport() {
		return orginAirport;
	}

	public void setOrginAirport(String orginAirport) {
		this.orginAirport = orginAirport;
	}
	@Column(name="DSTARPCOD")
	public String getDestinationAirport() {
		return destinationAirport;
	}

	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}
	@Column(name="OFLORGARP")
	public String getOfflineOriginAirport() {
		return offlineOriginAirport;
	}

	public void setOfflineOriginAirport(String offlineOriginAirport) {
		this.offlineOriginAirport = offlineOriginAirport;
	}
	@Column(name="ORIORGARP")
	public String getOriginalOriginAirport() {
		return originalOriginAirport;
	}

	public void setOriginalOriginAirport(String originalOriginAirport) {
		this.originalOriginAirport = originalOriginAirport;
	}
	@Column(name="FNLDST")
	public String getFinalDestination() {
		return finalDestination;
	}

	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}
	@Column(name="DLVSCNCAR")
	public String getDeliveryScanCarrierCode() {
		return deliveryScanCarrierCode;
	}

	public void setDeliveryScanCarrierCode(String deliveryScanCarrierCode) {
		this.deliveryScanCarrierCode = deliveryScanCarrierCode;
	}
	@Column(name="DLVSCNLOC")
	public String getDeleiveryScanLocation() {
		return deleiveryScanLocation;
	}

	public void setDeleiveryScanLocation(String deleiveryScanLocation) {
		this.deleiveryScanLocation = deleiveryScanLocation;
	}
	@Column(name="DLVSCNDAT")
	public Calendar getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Calendar deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	@Column(name="NILPAYRCPCOD")
	public String getZeroPayReceptacleCode() {
		return zeroPayReceptacleCode;
	}

	public void setZeroPayReceptacleCode(String zeroPayReceptacleCode) {
		this.zeroPayReceptacleCode = zeroPayReceptacleCode;
	}
	@Column(name="TERHNDSCNRAT")
	public double getTerminalHandlingScanRate() {
		return terminalHandlingScanRate;
	}

	public void setTerminalHandlingScanRate(double terminalHandlingScanRate) {
		this.terminalHandlingScanRate = terminalHandlingScanRate;
	}
	@Column(name="SORRAT")
	public double getSortRate() {
		return sortRate;
	}

	public void setSortRate(double sortRate) {
		this.sortRate = sortRate;
	}
	@Column(name="LIVRAT")
	public double getLiveRate() {
		return liveRate;
	}
	
	public void setLiveRate(double liveRate) {
		this.liveRate = liveRate;
	}
	@Column(name="HUBRELRAT")
	public double getHubrelabelingRate() {
		return hubrelabelingRate;
	}

	public void setHubrelabelingRate(double hubrelabelingRate) {
		this.hubrelabelingRate = hubrelabelingRate;
	}
	@Column(name="ADLSEPRAT")
	public double getAdditionalSeparationRate() {
		return additionalSeparationRate;
	}

	public void setAdditionalSeparationRate(double additionalSeparationRate) {
		this.additionalSeparationRate = additionalSeparationRate;
	}
	@Column(name="ADJIND")
	public String getAdjustmentIndicator() {
		return adjustmentIndicator;
	}

	public void setAdjustmentIndicator(String adjustmentIndicator) {
		this.adjustmentIndicator = adjustmentIndicator;
	}
	@Column(name="MISSNTIND")
	public String getMisSentIndicator() {
		return misSentIndicator;
	}

	public void setMisSentIndicator(String misSentIndicator) {
		this.misSentIndicator = misSentIndicator;
	}
	@Column(name="SCNPAYIND")
	public String getScanPayIndicator() {
		return scanPayIndicator;
	}

	public void setScanPayIndicator(String scanPayIndicator) {
		this.scanPayIndicator = scanPayIndicator;
	}
	@Column(name="GCMORGDST")
	public String getGreatCircleMiles() {
		return greatCircleMiles;
	}

	public void setGreatCircleMiles(String greatCircleMiles) {
		this.greatCircleMiles = greatCircleMiles;
	}
	@Column(name="GCMUNT")
	public String getGreatCircleMilesUnit() {
		return greatCircleMilesUnit;
	}

	public void setGreatCircleMilesUnit(String greatCircleMilesUnit) {
		this.greatCircleMilesUnit = greatCircleMilesUnit;
	}
	@Column(name="PAYRATCOD")
	public String getPayRateCode() {
		return payRateCode;
	}

	public void setPayRateCode(String payRateCode) {
		this.payRateCode = payRateCode;
	}
	@Column(name="CTRTYP")
	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	@Column(name="CARTOOPAY")
	public String getCarrierToPay() {
		return carrierToPay;
	}

	public void setCarrierToPay(String carrierToPay) {
		this.carrierToPay = carrierToPay;
	}
	@Column(name="CONTYP")
	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	@Column(name="ASGDAT")
	public Calendar getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Calendar assignedDate) {
		this.assignedDate = assignedDate;
	}

	@Column(name="ROUDEPDAT")
	public Calendar getRouteDepatureDate() {
		return routeDepatureDate;
	}

	public void setRouteDepatureDate(Calendar routeDepatureDate) {
		this.routeDepatureDate = routeDepatureDate;
	}
	@Column(name="CTLNUM")
	public String getControlNumber() {
		return controlNumber;
	}
	
	public void setControlNumber(String controlNumber) {
		this.controlNumber = controlNumber;
	}
	@Column(name="REGCOD")
	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	@Column(name="REFVERNUM")
	public String getReferenceVersionNumber() {
		return referenceVersionNumber;
	}

	public void setReferenceVersionNumber(String referenceVersionNumber) {
		this.referenceVersionNumber = referenceVersionNumber;
	}
	@Column(name="TRKLOC")
	public String getTruckingLocation() {
		return truckingLocation;
	}

	public void setTruckingLocation(String truckingLocation) {
		this.truckingLocation = truckingLocation;
	}
	@Column(name="ROUARRDAT")
	public Calendar getRouteArrivalDate() {
		return routeArrivalDate;
	}

	public void setRouteArrivalDate(Calendar routeArrivalDate) {
		this.routeArrivalDate = routeArrivalDate;
	}
	@Column(name="ORGTRPSTG")
	public String getOriginTripStagQualifier() {
		return originTripStagQualifier;
	}

	public void setOriginTripStagQualifier(String originTripStagQualifier) {
		this.originTripStagQualifier = originTripStagQualifier;
	}
	@Column(name="ORGFLTNUM")
	public String getOriginTripFlightNumber() {
		return originTripFlightNumber;
	}

	public void setOriginTripFlightNumber(String originTripFlightNumber) {
		this.originTripFlightNumber = originTripFlightNumber;
	}
	@Column(name="ORGCARCOD")
	public String getOriginTripCarrierCode() {
		return originTripCarrierCode;
	}

	public void setOriginTripCarrierCode(String originTripCarrierCode) {
		this.originTripCarrierCode = originTripCarrierCode;
	}
	@Column(name="POSSCNTRPSTG")
	public String getPossessionScanStagQualifier() {
		return possessionScanStagQualifier;
	}
	public void setPossessionScanStagQualifier(String possessionScanStagQualifier) {
		this.possessionScanStagQualifier = possessionScanStagQualifier;
	}
	@Column(name="POSSCNCAR")
	public String getPossessionScanCarrierCode() {
		return possessionScanCarrierCode;
	}

	public void setPossessionScanCarrierCode(String possessionScanCarrierCode) {
		this.possessionScanCarrierCode = possessionScanCarrierCode;
	}
	@Column(name="POSSCNEXPSTE")
	public String getPossessionScanExpectedSite() {
		return possessionScanExpectedSite;
	}

	public void setPossessionScanExpectedSite(String possessionScanExpectedSite) {
		this.possessionScanExpectedSite = possessionScanExpectedSite;
	}
	@Column(name="LODSCANCAR")
	public String getLoadScanCarrierCode() {
		return loadScanCarrierCode;
	}

	public void setLoadScanCarrierCode(String loadScanCarrierCode) {
		this.loadScanCarrierCode = loadScanCarrierCode;
	}
	@Column(name="LODSCNTRPSTG")
	public String getLoadScanStagQualifier() {
		return loadScanStagQualifier;
	}

	public void setLoadScanStagQualifier(String loadScanStagQualifier) {
		this.loadScanStagQualifier = loadScanStagQualifier;
	}
	@Column(name="LODSCNFLTNUM")
	public String getLoadScanFlightNumber() {
		return loadScanFlightNumber;
	}

	public void setLoadScanFlightNumber(String loadScanFlightNumber) {
		this.loadScanFlightNumber = loadScanFlightNumber;
	}
	@Column(name="LODSCNEXPSTE")
	public String getLoadScanExpectedSite() {
		return loadScanExpectedSite;
	}

	public void setLoadScanExpectedSite(String loadScanExpectedSite) {
		this.loadScanExpectedSite = loadScanExpectedSite;
	}
	@Column(name="LODSCNDAT")
	public Calendar getLoadScanDate() {
		return loadScanDate;
	}

	public void setLoadScanDate(Calendar loadScanDate) {
		this.loadScanDate = loadScanDate;
	}
	@Column(name="TRFONECAR")
	public String getFirstTransferScanCarrier() {
		return firstTransferScanCarrier;
	}

	public void setFirstTransferScanCarrier(String firstTransferScanCarrier) {
		this.firstTransferScanCarrier = firstTransferScanCarrier;
	}
	@Column(name="TRFONETRPSTG")
	public String getFirstTransferStagQualifier() {
		return firstTransferStagQualifier;
	}

	public void setFirstTransferStagQualifier(String firstTransferStagQualifier) {
		this.firstTransferStagQualifier = firstTransferStagQualifier;
	}
	@Column(name="TRFONEFLTNUM")
	public String getFirstTransferFlightNumber() {
		return firstTransferFlightNumber;
	}

	public void setFirstTransferFlightNumber(String firstTransferFlightNumber) {
		this.firstTransferFlightNumber = firstTransferFlightNumber;
	}
	@Column(name="TRFONEEXPSTE")
	public String getFirstTransferExpectedSite() {
		return firstTransferExpectedSite;
	}

	public void setFirstTransferExpectedSite(String firstTransferExpectedSite) {
		this.firstTransferExpectedSite = firstTransferExpectedSite;
	}
	@Column(name="TRFONEDAT")
	public Calendar getFirstTransferDate() {
		return firstTransferDate;
	}

	public void setFirstTransferDate(Calendar firstTransferDate) {
		this.firstTransferDate = firstTransferDate;
	}
	@Column(name="TRFTWOCAR")
	public String getSecondTransferScanCarrier() {
		return secondTransferScanCarrier;
	}

	public void setSecondTransferScanCarrier(String secondTransferScanCarrier) {
		this.secondTransferScanCarrier = secondTransferScanCarrier;
	}
	@Column(name="TRFTWOTRPSTG")
	public String getSecondTransferStagQualifier() {
		return secondTransferStagQualifier;
	}

	public void setSecondTransferStagQualifier(String secondTransferStagQualifier) {
		this.secondTransferStagQualifier = secondTransferStagQualifier;
	}
	@Column(name="TRFTWOFLTNUM")
	public String getSecondTransferFlightNumber() {
		return secondTransferFlightNumber;
	}

	public void setSecondTransferFlightNumber(String secondTransferFlightNumber) {
		this.secondTransferFlightNumber = secondTransferFlightNumber;
	}
	@Column(name="TRFTWOEXPSTE")
	public String getSecondTransferExpectedSite() {
		return secondTransferExpectedSite;
	}

	public void setSecondTransferExpectedSite(String secondTransferExpectedSite) {
		this.secondTransferExpectedSite = secondTransferExpectedSite;
	}
	@Column(name="TRFTWODAT")
	public Calendar getSecondTransferDate() {
		return secondTransferDate;
	}

	public void setSecondTransferDate(Calendar secondTransferDate) {
		this.secondTransferDate = secondTransferDate;
	}
	@Column(name="TRFTHRCAR")
	public String getThirdTransferScanCarrier() {
		return thirdTransferScanCarrier;
	}

	public void setThirdTransferScanCarrier(String thirdTransferScanCarrier) {
		this.thirdTransferScanCarrier = thirdTransferScanCarrier;
	}
	@Column(name="TRFTHRTRPSTG")
	public String getThirdTransferStagQualifier() {
		return thirdTransferStagQualifier;
	}

	public void setThirdTransferStagQualifier(String thirdTransferStagQualifier) {
		this.thirdTransferStagQualifier = thirdTransferStagQualifier;
	}
	@Column(name="TRFTHRFLTNUM")
	public String getThirdTransferFlightNumber() {
		return thirdTransferFlightNumber;
	}

	public void setThirdTransferFlightNumber(String thirdTransferFlightNumber) {
		this.thirdTransferFlightNumber = thirdTransferFlightNumber;
	}
	@Column(name="TRFTHREXPSTE")
	public String getThirdTransferExpectedSite() {
		return thirdTransferExpectedSite;
	}

	public void setThirdTransferExpectedSite(String thirdTransferExpectedSite) {
		this.thirdTransferExpectedSite = thirdTransferExpectedSite;
	}
	@Column(name="TRFTHRDAT")
	public Calendar getThirdTransferDate() {
		return thirdTransferDate;
	}

	public void setThirdTransferDate(Calendar thirdTransferDate) {
		this.thirdTransferDate = thirdTransferDate;
	}
	@Column(name="TRFFORCAR")
	public String getFourthTransferScanCarrier() {
		return fourthTransferScanCarrier;
	}

	public void setFourthTransferScanCarrier(String fourthTransferScanCarrier) {
		this.fourthTransferScanCarrier = fourthTransferScanCarrier;
	}
	@Column(name="TRFFORTRPSTG")
	public String getFourthTransferStagQualifier() {
		return fourthTransferStagQualifier;
	}

	public void setFourthTransferStagQualifier(String fourthTransferStagQualifier) {
		this.fourthTransferStagQualifier = fourthTransferStagQualifier;
	}
	@Column(name="TRFFORFLTNUM")
	public String getFourthTransferFlightNumber() {
		return fourthTransferFlightNumber;
	}

	public void setFourthTransferFlightNumber(String fourthTransferFlightNumber) {
		this.fourthTransferFlightNumber = fourthTransferFlightNumber;
	}
	@Column(name="TRFFOREXPSTE")
	public String getFourthTransferExpectedSite() {
		return fourthTransferExpectedSite;
	}

	public void setFourthTransferExpectedSite(String fourthTransferExpectedSite) {
		this.fourthTransferExpectedSite = fourthTransferExpectedSite;
	}
	@Column(name="TRFFORDAT")
	public Calendar getFourthTransferDate() {
		return fourthTransferDate;
	}

	public void setFourthTransferDate(Calendar fourthTransferDate) {
		this.fourthTransferDate = fourthTransferDate;
	}
	@Column(name="DLVSCNTRPSTG")
	public String getDeliverTransferStagQualifier() {
		return deliverTransferStagQualifier;
	}

	public void setDeliverTransferStagQualifier(String deliverTransferStagQualifier) {
		this.deliverTransferStagQualifier = deliverTransferStagQualifier;
	}
	@Column(name="DLVSCNACTSTE")
	public String getDeliverScanActualSite() {
		return deliverScanActualSite;
	}

	public void setDeliverScanActualSite(String deliverScanActualSite) {
		this.deliverScanActualSite = deliverScanActualSite;
	}
	@Column(name="CARFNLDST")
	public String getCarrierFinalDestination() {
		return carrierFinalDestination;
	}

	public void setCarrierFinalDestination(String carrierFinalDestination) {
		this.carrierFinalDestination = carrierFinalDestination;
	}
	@Column(name="TERHNDSCNCHG")
	public double getTerminalHandlingScanningCharge() {
		return terminalHandlingScanningCharge;
	}

	public void setTerminalHandlingScanningCharge(double terminalHandlingScanningCharge) {
		this.terminalHandlingScanningCharge = terminalHandlingScanningCharge;
	}
	@Column(name="SORCHG")
	public double getSortCharge() {
		return sortCharge;
	}

	public void setSortCharge(double sortCharge) {
		this.sortCharge = sortCharge;
	}
	@Column(name="HUBRLBCHG")
	public double getHubReLabelingCharge() {
		return hubReLabelingCharge;
	}

	public void setHubReLabelingCharge(double hubReLabelingCharge) {
		this.hubReLabelingCharge = hubReLabelingCharge;
	}
	@Column(name="LIVCHG")
	public double getLiveCharge() {
		return liveCharge;
	}

	public void setLiveCharge(double liveCharge) {
		this.liveCharge = liveCharge;
	}
	@Column(name="ADSCHG")
	public double getAdditionalSeparationCharge() {
		return additionalSeparationCharge;
	}

	public void setAdditionalSeparationCharge(double additionalSeparationCharge) {
		this.additionalSeparationCharge = additionalSeparationCharge;
	}
	@Column(name="ADJTERHNDCHG")
	public double getAdjustedTerminalHandlingCharge() {
		return adjustedTerminalHandlingCharge;
	}

	public void setAdjustedTerminalHandlingCharge(double adjustedTerminalHandlingCharge) {
		this.adjustedTerminalHandlingCharge = adjustedTerminalHandlingCharge;
	}
	@Column(name="SORSCNCAR")
	public String getSortScanCarrier() {
		return sortScanCarrier;
	}

	public void setSortScanCarrier(String sortScanCarrier) {
		this.sortScanCarrier = sortScanCarrier;
	}
	@Column(name="SORSCNEXPSTE")
	public String getSortScanExpectedSite() {
		return sortScanExpectedSite;
	}

	public void setSortScanExpectedSite(String sortScanExpectedSite) {
		this.sortScanExpectedSite = sortScanExpectedSite;
	}
	@Column(name="SORSCNACTSTE")
	public String getSortScanActualSite() {
		return sortScanActualSite;
	}

	public void setSortScanActualSite(String sortScanActualSite) {
		this.sortScanActualSite = sortScanActualSite;
	}
	@Column(name="SORSCNACTDAT")
	public Calendar getSortScanDate() {
		return sortScanDate;
	}

	public void setSortScanDate(Calendar sortScanDate) {
		this.sortScanDate = sortScanDate;
	}
	@Column(name="SORSCNRCVDAT")
	public Calendar getSortScanReceiveDate() {
		return sortScanReceiveDate;
	}

	public void setSortScanReceiveDate(Calendar sortScanReceiveDate) {
		this.sortScanReceiveDate = sortScanReceiveDate;
	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM"))})
	public MailGPAInvoicMessageDetailPK getMailInvoicMessageDetailPK() {
		return mailInvoicMessageDetailPK;
	}

	public void setMailInvoicMessageDetailPK(MailGPAInvoicMessageDetailPK mailInvoicMessageDetailPK) {
		this.mailInvoicMessageDetailPK = mailInvoicMessageDetailPK;
	}
	
	public MailGPAInvoicMessageDetail(MailInvoicMessageMasterPK invoicMessageMasterPK,MailInvoicMessageDetailVO mailInvoicMessageDetailVO) throws SystemException{
		
		populatePK(invoicMessageMasterPK,mailInvoicMessageDetailVO);
		populateAttribute(mailInvoicMessageDetailVO);
		try {
            PersistenceController.getEntityManager().persist(this);
        } catch(CreateException createException) {
            throw new SystemException(createException.getMessage(),
                    createException);
        }
		
		
	}

	private void populateAttribute(MailInvoicMessageDetailVO mailInvoicMessageDetailVO) {
	
		this.setInvoiceNumber(mailInvoicMessageDetailVO.getInvoiceNumber());
		this.setConsignmentNumber(mailInvoicMessageDetailVO.getConsignmentNumber());
		this.setPoaCode(mailInvoicMessageDetailVO.getPoaCode());
		this.setMailCategoryCode(mailInvoicMessageDetailVO.getMailCategoryCode());
		this.setMailSubClassCode(mailInvoicMessageDetailVO.getMailSubClassCode());
		this.setMailProductCode(mailInvoicMessageDetailVO.getMailProductCode());
		if(mailInvoicMessageDetailVO.getMailBagId() != null && mailInvoicMessageDetailVO.getMailBagId().trim().length() == 29)
			this.setMailBagId(mailInvoicMessageDetailVO.getMailBagId());
		else{
			String wgt = String.valueOf((int)mailInvoicMessageDetailVO.getContainerGrossWeight());
			wgt = wgt.length() == 1 ? new StringBuilder("0").append(wgt).toString() : wgt;
			this.setMailBagId(mailInvoicMessageDetailVO.getMailBagId().concat(wgt));
		}
		this.setPaymentLevel(mailInvoicMessageDetailVO.getPaymentLevel());
		this.setLateIndicator(mailInvoicMessageDetailVO.getLateIndicator());
		this.setRateTypeIndicator(mailInvoicMessageDetailVO.getRateTypeIndicator());
		this.setPayCycleIndicator(mailInvoicMessageDetailVO.getPayCycleIndicator());
		this.setAdjustmentReasonCode(mailInvoicMessageDetailVO.getAdjustmentReasonCode());
		this.setRequiredDeliveryDate(mailInvoicMessageDetailVO.getRequiredDeliveryDate());
		this.setContainerNumber(mailInvoicMessageDetailVO.getContainerNumber());
		this.setClaimAdjustmentCode(mailInvoicMessageDetailVO.getClaimAdjustmentCode());
		this.setClaimText(mailInvoicMessageDetailVO.getClaimText());
		this.setClaimStatus(mailInvoicMessageDetailVO.getClaimStatus());
		this.setClaimReasonCode(mailInvoicMessageDetailVO.getClaimReasonCode());
		if(mailInvoicMessageDetailVO.getBaseTotalAmount()!=null){
		this.setBaseTotalAmount(mailInvoicMessageDetailVO.getBaseTotalAmount().getAmount());
		}
		if(mailInvoicMessageDetailVO.getAdjustmentTotalAmount()!=null){
		this.setAdjustmentTotalAmount(mailInvoicMessageDetailVO.getAdjustmentTotalAmount().getAmount());
		}
		if(mailInvoicMessageDetailVO.getLineHaulCharge()!=null){
		this.setLineHaulCharge(mailInvoicMessageDetailVO.getLineHaulCharge().getAmount());
		}
		if(mailInvoicMessageDetailVO.getTerminalHandlingCharge()!=null){
		this.setTerminalHandlingCharge(mailInvoicMessageDetailVO.getTerminalHandlingCharge().getAmount());
		}
		if(mailInvoicMessageDetailVO.getOtherValuationCharge()!=null){
		this.setOtherValuationCharge(mailInvoicMessageDetailVO.getOtherValuationCharge().getAmount());
		}
		if(mailInvoicMessageDetailVO.getContainerChargeAmount()!=null){
		this.setContainerChargeAmount(mailInvoicMessageDetailVO.getContainerChargeAmount().getAmount());
		}
		if(mailInvoicMessageDetailVO.getCharterChargeAmount()!=null){
		this.setCharterChargeAmount(mailInvoicMessageDetailVO.getCharterChargeAmount().getAmount());
		}
		this.setPossessionScanDate(mailInvoicMessageDetailVO.getPossessionScanDate());
		this.setLineHaulDollarRate(mailInvoicMessageDetailVO.getLineHaulDollarRate());
		this.setLineHaulSDRRate(mailInvoicMessageDetailVO.getLineHaulDollarRate());
		this.setTerminalHandlingDollarRate(mailInvoicMessageDetailVO.getTerminalHandlingDollarRate());
		this.setTerminalHandlingSDRRate(mailInvoicMessageDetailVO.getTerminalHandlingSDRRate());
		this.setSpecialPerKiloDollarRate(mailInvoicMessageDetailVO.getSpecialPerKiloDollarRate());
		this.setSpecialPerKiloSDRRate(mailInvoicMessageDetailVO.getSpecialPerKiloSDRRate());
		this.setRangeDependentRate(mailInvoicMessageDetailVO.getRangeDependentRate());
		this.setActiveIngredientRate(mailInvoicMessageDetailVO.getActiveIngredientRate());
		this.setContainerRate(mailInvoicMessageDetailVO.getContainerRate());
		this.setSdrDate(mailInvoicMessageDetailVO.getSdrDate());
		this.setSdrRate(mailInvoicMessageDetailVO.getSdrRate());
		this.setConsignmentCompletionDate(mailInvoicMessageDetailVO.getConsignmentCompletionDate());
		this.setTransportationWindowEndTime(mailInvoicMessageDetailVO.getTransportationWindowEndTime());
		this.setWeightUnit(mailInvoicMessageDetailVO.getWeightUnit());
		this.setGrossWeight(mailInvoicMessageDetailVO.getGrossWeight());
		this.setContainerWeightUnit(mailInvoicMessageDetailVO.getContainerWeightUnit());
		this.setContainerGrossWeight(mailInvoicMessageDetailVO.getContainerGrossWeight());
		this.setOrginAirport(mailInvoicMessageDetailVO.getOrginAirport());
		this.setDestinationAirport(mailInvoicMessageDetailVO.getDestinationAirport());
		this.setOfflineOriginAirport(mailInvoicMessageDetailVO.getOfflineOriginAirport());
		this.setOriginalOriginAirport(mailInvoicMessageDetailVO.getOriginalOriginAirport());
		this.setFinalDestination(mailInvoicMessageDetailVO.getFinalDestination());
		this.setDeliveryScanCarrierCode(mailInvoicMessageDetailVO.getDeliveryScanCarrierCode());
		this.setDeleiveryScanLocation(mailInvoicMessageDetailVO.getDeleiveryScanLocation());
		this.setDeliveryDate(mailInvoicMessageDetailVO.getDeliveryDate());
		this.setZeroPayReceptacleCode(mailInvoicMessageDetailVO.getZeroPayReceptacleCode());
		this.setTerminalHandlingScanRate(mailInvoicMessageDetailVO.getTerminalHandlingScanRate());
		this.setSortRate(mailInvoicMessageDetailVO.getSortRate());
		this.setLiveRate(mailInvoicMessageDetailVO.getLiveRate());
		this.setHubrelabelingRate(mailInvoicMessageDetailVO.getHubrelabelingRate());
		this.setAdditionalSeparationRate(mailInvoicMessageDetailVO.getAdditionalSeparationRate());
		this.setAdjustmentIndicator(mailInvoicMessageDetailVO.getAdjustmentIndicator());
		this.setMisSentIndicator(mailInvoicMessageDetailVO.getMisSentIndicator());
		this.setScanPayIndicator(mailInvoicMessageDetailVO.getScanPayIndicator());
		this.setGreatCircleMiles(mailInvoicMessageDetailVO.getGreatCircleMiles());
		this.setGreatCircleMilesUnit(mailInvoicMessageDetailVO.getGreatCircleMilesUnit());
		this.setPayRateCode(mailInvoicMessageDetailVO.getPayRateCode());
		this.setContractType(mailInvoicMessageDetailVO.getContractType());
		this.setCarrierToPay(mailInvoicMessageDetailVO.getCarrierToPay());
		this.setContainerType(mailInvoicMessageDetailVO.getContainerType()); 
		this.setRouteDepatureDate(mailInvoicMessageDetailVO.getRouteDepatureDate());
		this.setAssignedDate(mailInvoicMessageDetailVO.getAssignedDate()); 
		this.setControlNumber(mailInvoicMessageDetailVO.getControlNumber());
		this.setRegionCode(mailInvoicMessageDetailVO.getRegionCode());
		this.setCarrierFinalDestination(mailInvoicMessageDetailVO.getCarrierFinalDestination());
		this.setReferenceVersionNumber(mailInvoicMessageDetailVO.getReferenceVersionNumber());
		this.setTruckingLocation(mailInvoicMessageDetailVO.getTruckingLocation());
		this.setCarrierFinalDestination(mailInvoicMessageDetailVO.getCarrierFinalDestination());
		this.setRouteArrivalDate(mailInvoicMessageDetailVO.getRouteArrivalDate());	
		this.setOriginTripCarrierCode(mailInvoicMessageDetailVO.getOriginTripCarrierCode());
		this.setOriginTripFlightNumber(mailInvoicMessageDetailVO.getOriginTripFlightNumber());
		this.setOriginTripStagQualifier(mailInvoicMessageDetailVO.getOriginTripStagQualifier());
		this.setPossessionScanStagQualifier(mailInvoicMessageDetailVO.getPossessionScanStagQualifier());
		this.setPossessionScanCarrierCode(mailInvoicMessageDetailVO.getPossessionScanCarrierCode());
		this.setPossessionScanExpectedSite(mailInvoicMessageDetailVO.getPossessionScanExpectedSite());	
		this.setLoadScanCarrierCode(mailInvoicMessageDetailVO.getLoadScanCarrierCode());
		this.setLoadScanStagQualifier(mailInvoicMessageDetailVO.getLoadScanStagQualifier());
		this.setLoadScanFlightNumber(mailInvoicMessageDetailVO.getLoadScanFlightNumber());
		this.setLoadScanExpectedSite(mailInvoicMessageDetailVO.getLoadScanExpectedSite());
		this.setLoadScanDate(mailInvoicMessageDetailVO.getLoadScanDate());
		this.setFirstTransferScanCarrier(mailInvoicMessageDetailVO.getFirstTransferScanCarrier());
		this.setFirstTransferFlightNumber(mailInvoicMessageDetailVO.getFirstTransferFlightNumber());
		this.setFirstTransferExpectedSite(mailInvoicMessageDetailVO.getFirstTransferExpectedSite());
		this.setFirstTransferStagQualifier(mailInvoicMessageDetailVO.getFirstTransferStagQualifier());
		this.setFirstTransferDate(mailInvoicMessageDetailVO.getFirstTransferDate());
		this.setSecondTransferStagQualifier(mailInvoicMessageDetailVO.getSecondTransferStagQualifier());
		this.setSecondTransferExpectedSite(mailInvoicMessageDetailVO.getSecondTransferExpectedSite());
		this.setSecondTransferFlightNumber(mailInvoicMessageDetailVO.getSecondTransferFlightNumber());
		this.setSecondTransferScanCarrier(mailInvoicMessageDetailVO.getSecondTransferScanCarrier());
		this.setSecondTransferDate(mailInvoicMessageDetailVO.getSecondTransferDate());
		this.setThirdTransferScanCarrier(mailInvoicMessageDetailVO.getThirdTransferScanCarrier());
		this.setThirdTransferStagQualifier(mailInvoicMessageDetailVO.getThirdTransferStagQualifier());
		this.setThirdTransferExpectedSite(mailInvoicMessageDetailVO.getThirdTransferExpectedSite());
		this.setThirdTransferDate(mailInvoicMessageDetailVO.getThirdTransferDate());
		this.setThirdTransferFlightNumber(mailInvoicMessageDetailVO.getThirdTransferFlightNumber());
		this.setFourthTransferScanCarrier(mailInvoicMessageDetailVO.getFourthTransferScanCarrier());
		this.setFourthTransferFlightNumber(mailInvoicMessageDetailVO.getFourthTransferFlightNumber());
		this.setFourthTransferExpectedSite(mailInvoicMessageDetailVO.getFourthTransferExpectedSite());
		this.setFourthTransferDate(mailInvoicMessageDetailVO.getFourthTransferDate());
		this.setFourthTransferFlightNumber(mailInvoicMessageDetailVO.getFourthTransferFlightNumber());
		this.setDeliverTransferStagQualifier(mailInvoicMessageDetailVO.getDeliverTransferStagQualifier());
		this.setDeliverScanActualSite(mailInvoicMessageDetailVO.getDeliverScanActualSite());
		if(mailInvoicMessageDetailVO.getTerminalHandlingScanningCharge()!=null){
			this.setTerminalHandlingScanningCharge(mailInvoicMessageDetailVO.getTerminalHandlingScanningCharge().getAmount());
		}
		if(mailInvoicMessageDetailVO.getSortCharge()!=null){
			this.setSortCharge(mailInvoicMessageDetailVO.getSortCharge().getAmount());
		}
		if(mailInvoicMessageDetailVO.getHubReLabelingCharge()!=null){
			this.setHubReLabelingCharge(mailInvoicMessageDetailVO.getHubReLabelingCharge().getAmount());
		}
		if(mailInvoicMessageDetailVO.getLiveCharge()!=null){
			this.setLiveCharge(mailInvoicMessageDetailVO.getLiveCharge().getAmount());
		}
		if(mailInvoicMessageDetailVO.getAdditionalSeparationCharge()!=null){
			this.setAdditionalSeparationCharge(mailInvoicMessageDetailVO.getAdditionalSeparationCharge().getAmount());
		}
		if(mailInvoicMessageDetailVO.getAdjustedTerminalHandlingCharge()!=null){
			this.setAdjustedTerminalHandlingCharge(mailInvoicMessageDetailVO.getAdjustedTerminalHandlingCharge().getAmount());
		}
		this.setSortScanCarrier(mailInvoicMessageDetailVO.getSortScanCarrier());
		this.setSortScanExpectedSite(mailInvoicMessageDetailVO.getSortScanExpectedSite());
		this.setSortScanActualSite(mailInvoicMessageDetailVO.getSortScanActualSite());
		this.setSortScanDate(mailInvoicMessageDetailVO.getSortScanDate());
		this.setSortScanReceiveDate(mailInvoicMessageDetailVO.getSortScanReceiveDate()); 
		this.setClaimNotes(mailInvoicMessageDetailVO.getClaimNotes());
	}

	
	private void populatePK(MailInvoicMessageMasterPK invoicMessageMasterPK,MailInvoicMessageDetailVO mailInvoicMessageDetailVO) {
		mailInvoicMessageDetailPK =new MailGPAInvoicMessageDetailPK();
		mailInvoicMessageDetailPK.setCompanyCode(invoicMessageMasterPK.getCompanyCode());
		mailInvoicMessageDetailPK.setSerialNumber(invoicMessageMasterPK.getSerialNumber());
		this.setMailInvoicMessageDetailPK(mailInvoicMessageDetailPK);
	}
	@Column(name="CLMNOT")
	public String getClaimNotes() {
		return claimNotes;
	}
	public void setClaimNotes(String claimNotes) {
		this.claimNotes = claimNotes;
	}
	
	

	
	
}
