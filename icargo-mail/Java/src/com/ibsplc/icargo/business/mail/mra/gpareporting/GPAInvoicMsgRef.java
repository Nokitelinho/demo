/**
 *
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.MRAGPAReportingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5219
 *
 */
@Entity
@Table(name = "MALMRAGPAINCMSGREF")
public class GPAInvoicMsgRef {

	private  Log log = LogFactory.getLogger("MRA GPAREPORTING GPAInvoicMsgRef");

	private GPAInvoicMsgRefPK gpaInvoicMsgRefPK;

	private String controlNumber;
	private String regionCode;
	private String referenceVersionNumber;
	private String truckingLocation;
	private Calendar routeArrivalDate;
	private String originTripFlightNumber;
	private String originTripCarrierCode;
	private String possessionScanCarrierCode;
	private String possessionScanExpectedSite;
	private String loadScanCarrierCode;
	private String loadScanFlightNumber;
	private String loadScanExpectedSite;
	private Calendar loadScanDate;
	private String firstTransferScanCarrier;
	private String firstTransferFlightNumber;
	private String firstTransferExpectedSite;
	private Calendar firstTransferDate;
	private String secondTransferScanCarrier;
	private String secondTransferFlightNumber;
	private String secondTransferExpectedSite;
	private Calendar secondTransferDate;
	private String thirdTransferScanCarrier;
	private String thirdTransferFlightNumber;
	private String thirdTransferExpectedSite;
	private Calendar thirdTransferDate;
	private String fourthTransferScanCarrier;
	private String fourthTransferFlightNumber;
	private String fourthTransferExpectedSite;
	private Calendar fourthTransferDate;
	private String deliverScanActualSite;
	private String carrierFinalDestination;
	private String rateTypeIndicator;
	private String greatCircleMiles;
	private String consignmentNumber;
	private String mailCategoryCode;
	private  String mailSubClassCode;
	private String mailProductCode;
	private String payRateCode;
	private  double lineHaulDollarRate;
	private double lineHaulSDRRate;
	private double terminalHandlingDollarRate;
	private double terminalHandlingSDRRate;
	private double specialPerKiloDollarRate;
	private double specialPerKiloSDRRate;
	private double containerRate;
	private String containerType;
	private String weightUnit;
	private String containerWeightUnit;
	private double containerGrossWeight;
	private Calendar consignmentCompletionDate;
	private String contractType;
	private double grossWeight;
	private String carrierToPay;
	private String orginAirport;
	private String destinationAirport;
	private String offlineOriginAirport;
	private String deliveryScanCarrierCode;
	private String originalOriginAirport;
	private String finalDestination;
	private Calendar routeDepatureDate;
	private Calendar possessionScanDate;
	private Calendar deliveryDate;
	private String deliveryScanExpectedSite;
	private String messageText;
	private String claimRefNumber;
	private Calendar lastUpdateTime;
	private String lastUpdateUser;

	/**
	 * @return the gpaInvoicMsgRefPK
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")),
			@AttributeOverride(name = "mailSequenceNumber", column = @Column(name = "MALSEQNUM")) })
	public GPAInvoicMsgRefPK getGpaInvoicMsgRefPK() {
		return gpaInvoicMsgRefPK;
	}
	/**
	 * @param gpaInvoicMsgRefPK the gpaInvoicMsgRefPK to set
	 */
	public void setGpaInvoicMsgRefPK(GPAInvoicMsgRefPK gpaInvoicMsgRefPK) {
		this.gpaInvoicMsgRefPK = gpaInvoicMsgRefPK;
	}
	/**
	 * @return the controlNumber
	 */
	@Column(name="CTLNUM")
	public String getControlNumber() {
		return controlNumber;
	}
	/**
	 * @param controlNumber the controlNumber to set
	 */
	public void setControlNumber(String controlNumber) {
		this.controlNumber = controlNumber;
	}
	/**
	 * @return the regionCode
	 */
	@Column(name="REGCOD")
	public String getRegionCode() {
		return regionCode;
	}
	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * @return the referenceVersionNumber
	 */
	@Column(name="REFVERNUM")
	public String getReferenceVersionNumber() {
		return referenceVersionNumber;
	}
	/**
	 * @param referenceVersionNumber the referenceVersionNumber to set
	 */
	public void setReferenceVersionNumber(String referenceVersionNumber) {
		this.referenceVersionNumber = referenceVersionNumber;
	}
	/**
	 * @return the truckingLocation
	 */
	@Column(name="TRKLOC")
	public String getTruckingLocation() {
		return truckingLocation;
	}
	/**
	 * @param truckingLocation the truckingLocation to set
	 */
	public void setTruckingLocation(String truckingLocation) {
		this.truckingLocation = truckingLocation;
	}
	/**
	 * @return the routeArrivalDate
	 */
	@Column(name="ROUARRDAT")
	public Calendar getRouteArrivalDate() {
		return routeArrivalDate;
	}
	/**
	 * @param routeArrivalDate the routeArrivalDate to set
	 */
	public void setRouteArrivalDate(Calendar routeArrivalDate) {
		this.routeArrivalDate = routeArrivalDate;
	}
	/**
	 * @return the originTripStagQualifier
	 *//*
	@Column(name="ORGTRPSTG")
	public String getOriginTripStagQualifier() {
		return originTripStagQualifier;
	}
	*//**
	 * @param originTripStagQualifier the originTripStagQualifier to set
	 *//*
	public void setOriginTripStagQualifier(String originTripStagQualifier) {
		this.originTripStagQualifier = originTripStagQualifier;
	}*/
	/**
	 * @return the originTripFlightNumber
	 */
	@Column(name="ORGFLTNUM")
	public String getOriginTripFlightNumber() {
		return originTripFlightNumber;
	}
	/**
	 * @param originTripFlightNumber the originTripFlightNumber to set
	 */
	public void setOriginTripFlightNumber(String originTripFlightNumber) {
		this.originTripFlightNumber = originTripFlightNumber;
	}
	/**
	 * @return the originTripCarrierCode
	 */
	@Column(name="ORGCARCOD")
	public String getOriginTripCarrierCode() {
		return originTripCarrierCode;
	}
	/**
	 * @param originTripCarrierCode the originTripCarrierCode to set
	 */
	public void setOriginTripCarrierCode(String originTripCarrierCode) {
		this.originTripCarrierCode = originTripCarrierCode;
	}
	/**
	 * @return the possessionScanStagQualifier
	 */
	/*@Column(name="POSSCNTRPSTG")
	public String getPossessionScanStagQualifier() {
		return possessionScanStagQualifier;
	}
	*//**
	 * @param possessionScanStagQualifier the possessionScanStagQualifier to set
	 *//*
	public void setPossessionScanStagQualifier(String possessionScanStagQualifier) {
		this.possessionScanStagQualifier = possessionScanStagQualifier;
	}*/
	/**
	 * @return the possessionScanCarrierCode
	 */
	@Column(name="POSSCNCAR")
	public String getPossessionScanCarrierCode() {
		return possessionScanCarrierCode;
	}
	/**
	 * @param possessionScanCarrierCode the possessionScanCarrierCode to set
	 */
	public void setPossessionScanCarrierCode(String possessionScanCarrierCode) {
		this.possessionScanCarrierCode = possessionScanCarrierCode;
	}
	/**
	 * @return the possessionScanExpectedSite
	 */
	@Column(name="POSSCNEXPSTE")
	public String getPossessionScanExpectedSite() {
		return possessionScanExpectedSite;
	}
	/**
	 * @param possessionScanExpectedSite the possessionScanExpectedSite to set
	 */
	public void setPossessionScanExpectedSite(String possessionScanExpectedSite) {
		this.possessionScanExpectedSite = possessionScanExpectedSite;
	}
	/**
	 * @return the loadScanCarrierCode
	 */
	@Column(name="LODSCANCAR")
	public String getLoadScanCarrierCode() {
		return loadScanCarrierCode;
	}
	/**
	 * @param loadScanCarrierCode the loadScanCarrierCode to set
	 */
	public void setLoadScanCarrierCode(String loadScanCarrierCode) {
		this.loadScanCarrierCode = loadScanCarrierCode;
	}
	/**
	 * @return the loadScanStagQualifier
	 */
	/*@Column(name="LODSCNTRPSTG")
	public String getLoadScanStagQualifier() {
		return loadScanStagQualifier;
	}
	*//**
	 * @param loadScanStagQualifier the loadScanStagQualifier to set
	 *//*
	public void setLoadScanStagQualifier(String loadScanStagQualifier) {
		this.loadScanStagQualifier = loadScanStagQualifier;
	}*/
	/**
	 * @return the loadScanFlightNumber
	 */
	@Column(name="LODSCNFLTNUM")
	public String getLoadScanFlightNumber() {
		return loadScanFlightNumber;
	}
	/**
	 * @param loadScanFlightNumber the loadScanFlightNumber to set
	 */
	public void setLoadScanFlightNumber(String loadScanFlightNumber) {
		this.loadScanFlightNumber = loadScanFlightNumber;
	}
	/**
	 * @return the loadScanExpectedSite
	 */
	@Column(name="LODSCNEXPSTE")
	public String getLoadScanExpectedSite() {
		return loadScanExpectedSite;
	}
	/**
	 * @param loadScanExpectedSite the loadScanExpectedSite to set
	 */
	public void setLoadScanExpectedSite(String loadScanExpectedSite) {
		this.loadScanExpectedSite = loadScanExpectedSite;
	}
	/**
	 * @return the loadScanDate
	 */
	@Column(name="LODSCNDAT")
	public Calendar getLoadScanDate() {
		return loadScanDate;
	}
	/**
	 * @param loadScanDate the loadScanDate to set
	 */
	public void setLoadScanDate(Calendar loadScanDate) {
		this.loadScanDate = loadScanDate;
	}
	/**
	 * @return the firstTransferScanCarrier
	 */
	@Column(name="TRFONECAR")
	public String getFirstTransferScanCarrier() {
		return firstTransferScanCarrier;
	}
	/**
	 * @param firstTransferScanCarrier the firstTransferScanCarrier to set
	 */
	public void setFirstTransferScanCarrier(String firstTransferScanCarrier) {
		this.firstTransferScanCarrier = firstTransferScanCarrier;
	}
	/**
	 * @return the firstTransferStagQualifier
	 */
	/*@Column(name="TRFONETRPSTG")
	public String getFirstTransferStagQualifier() {
		return firstTransferStagQualifier;
	}
	*//**
	 * @param firstTransferStagQualifier the firstTransferStagQualifier to set
	 *//*
	public void setFirstTransferStagQualifier(String firstTransferStagQualifier) {
		this.firstTransferStagQualifier = firstTransferStagQualifier;
	}*/
	/**
	 * @return the firstTransferFlightNumber
	 */
	@Column(name="TRFONEFLTNUM")
	public String getFirstTransferFlightNumber() {
		return firstTransferFlightNumber;
	}
	/**
	 * @param firstTransferFlightNumber the firstTransferFlightNumber to set
	 */
	public void setFirstTransferFlightNumber(String firstTransferFlightNumber) {
		this.firstTransferFlightNumber = firstTransferFlightNumber;
	}
	/**
	 * @return the firstTransferExpectedSite
	 */
	@Column(name="TRFONEEXPSTE")
	public String getFirstTransferExpectedSite() {
		return firstTransferExpectedSite;
	}
	/**
	 * @param firstTransferExpectedSite the firstTransferExpectedSite to set
	 */
	public void setFirstTransferExpectedSite(String firstTransferExpectedSite) {
		this.firstTransferExpectedSite = firstTransferExpectedSite;
	}
	/**
	 * @return the firstTransferDate
	 */
	@Column(name="TRFONEDAT")
	public Calendar getFirstTransferDate() {
		return firstTransferDate;
	}
	/**
	 * @param firstTransferDate the firstTransferDate to set
	 */
	public void setFirstTransferDate(Calendar firstTransferDate) {
		this.firstTransferDate = firstTransferDate;
	}
	/**
	 * @return the secondTransferScanCarrier
	 */
	@Column(name="TRFTWOCAR")
	public String getSecondTransferScanCarrier() {
		return secondTransferScanCarrier;
	}
	/**
	 * @param secondTransferScanCarrier the secondTransferScanCarrier to set
	 */
	public void setSecondTransferScanCarrier(String secondTransferScanCarrier) {
		this.secondTransferScanCarrier = secondTransferScanCarrier;
	}
	/**
	 * @return the secondTransferStagQualifier
	 */
	/*@Column(name="TRFTWOTRPSTG")
	public String getSecondTransferStagQualifier() {
		return secondTransferStagQualifier;
	}
	*//**
	 * @param secondTransferStagQualifier the secondTransferStagQualifier to set
	 *//*
	public void setSecondTransferStagQualifier(String secondTransferStagQualifier) {
		this.secondTransferStagQualifier = secondTransferStagQualifier;
	}*/
	/**
	 * @return the secondTransferFlightNumber
	 */
	@Column(name="TRFTWOFLTNUM")
	public String getSecondTransferFlightNumber() {
		return secondTransferFlightNumber;
	}
	/**
	 * @param secondTransferFlightNumber the secondTransferFlightNumber to set
	 */
	public void setSecondTransferFlightNumber(String secondTransferFlightNumber) {
		this.secondTransferFlightNumber = secondTransferFlightNumber;
	}
	/**
	 * @return the secondTransferExpectedSite
	 */
	@Column(name="TRFTWOEXPSTE")
	public String getSecondTransferExpectedSite() {
		return secondTransferExpectedSite;
	}
	/**
	 * @param secondTransferExpectedSite the secondTransferExpectedSite to set
	 */
	public void setSecondTransferExpectedSite(String secondTransferExpectedSite) {
		this.secondTransferExpectedSite = secondTransferExpectedSite;
	}
	/**
	 * @return the secondTransferDate
	 */
	@Column(name="TRFTWODAT")
	public Calendar getSecondTransferDate() {
		return secondTransferDate;
	}
	/**
	 * @param secondTransferDate the secondTransferDate to set
	 */
	public void setSecondTransferDate(Calendar secondTransferDate) {
		this.secondTransferDate = secondTransferDate;
	}
	/**
	 * @return the thirdTransferScanCarrier
	 */
	@Column(name="TRFTHRCAR")
	public String getThirdTransferScanCarrier() {
		return thirdTransferScanCarrier;
	}
	/**
	 * @param thirdTransferScanCarrier the thirdTransferScanCarrier to set
	 */
	public void setThirdTransferScanCarrier(String thirdTransferScanCarrier) {
		this.thirdTransferScanCarrier = thirdTransferScanCarrier;
	}
	/**
	 * @return the thirdTransferStagQualifier
	 */
	/*@Column(name="TRFTHRTRPSTG")
	public String getThirdTransferStagQualifier() {
		return thirdTransferStagQualifier;
	}
	*//**
	 * @param thirdTransferStagQualifier the thirdTransferStagQualifier to set
	 *//*
	public void setThirdTransferStagQualifier(String thirdTransferStagQualifier) {
		this.thirdTransferStagQualifier = thirdTransferStagQualifier;
	}*/
	/**
	 * @return the thirdTransferFlightNumber
	 */
	@Column(name="TRFTHRFLTNUM")
	public String getThirdTransferFlightNumber() {
		return thirdTransferFlightNumber;
	}
	/**
	 * @param thirdTransferFlightNumber the thirdTransferFlightNumber to set
	 */
	public void setThirdTransferFlightNumber(String thirdTransferFlightNumber) {
		this.thirdTransferFlightNumber = thirdTransferFlightNumber;
	}
	/**
	 * @return the thirdTransferExpectedSite
	 */
	@Column(name="TRFTHREXPSTE")
	public String getThirdTransferExpectedSite() {
		return thirdTransferExpectedSite;
	}
	/**
	 * @param thirdTransferExpectedSite the thirdTransferExpectedSite to set
	 */
	public void setThirdTransferExpectedSite(String thirdTransferExpectedSite) {
		this.thirdTransferExpectedSite = thirdTransferExpectedSite;
	}
	/**
	 * @return the thirdTransferDate
	 */
	@Column(name="TRFTHRDAT")
	public Calendar getThirdTransferDate() {
		return thirdTransferDate;
	}
	/**
	 * @param thirdTransferDate the thirdTransferDate to set
	 */
	public void setThirdTransferDate(Calendar thirdTransferDate) {
		this.thirdTransferDate = thirdTransferDate;
	}
	/**
	 * @return the fourthTransferScanCarrier
	 */
	@Column(name="TRFFORCAR")
	public String getFourthTransferScanCarrier() {
		return fourthTransferScanCarrier;
	}
	/**
	 * @param fourthTransferScanCarrier the fourthTransferScanCarrier to set
	 */
	public void setFourthTransferScanCarrier(String fourthTransferScanCarrier) {
		this.fourthTransferScanCarrier = fourthTransferScanCarrier;
	}
	/**
	 * @return the fourthTransferStagQualifier
	 */
	/*@Column(name="TRFFORTRPSTG")
	public String getFourthTransferStagQualifier() {
		return fourthTransferStagQualifier;
	}
	*//**
	 * @param fourthTransferStagQualifier the fourthTransferStagQualifier to set
	 *//*
	public void setFourthTransferStagQualifier(String fourthTransferStagQualifier) {
		this.fourthTransferStagQualifier = fourthTransferStagQualifier;
	}*/
	/**
	 * @return the fourthTransferFlightNumber
	 */
	@Column(name="TRFFORFLTNUM")
	public String getFourthTransferFlightNumber() {
		return fourthTransferFlightNumber;
	}
	/**
	 * @param fourthTransferFlightNumber the fourthTransferFlightNumber to set
	 */
	public void setFourthTransferFlightNumber(String fourthTransferFlightNumber) {
		this.fourthTransferFlightNumber = fourthTransferFlightNumber;
	}
	/**
	 * @return the fourthTransferExpectedSite
	 */
	@Column(name="TRFFOREXPSTE")
	public String getFourthTransferExpectedSite() {
		return fourthTransferExpectedSite;
	}
	/**
	 * @param fourthTransferExpectedSite the fourthTransferExpectedSite to set
	 */
	public void setFourthTransferExpectedSite(String fourthTransferExpectedSite) {
		this.fourthTransferExpectedSite = fourthTransferExpectedSite;
	}
	/**
	 * @return the fourthTransferDate
	 */
	@Column(name="TRFFORDAT")
	public Calendar getFourthTransferDate() {
		return fourthTransferDate;
	}
	/**
	 * @param fourthTransferDate the fourthTransferDate to set
	 */
	public void setFourthTransferDate(Calendar fourthTransferDate) {
		this.fourthTransferDate = fourthTransferDate;
	}
	/**
	 * @return the deliverTransferStagQualifier
	 */
	/*@Column(name="DLVSCNTRPSTG")
	public String getDeliverTransferStagQualifier() {
		return deliverTransferStagQualifier;
	}
	*//**
	 * @param deliverTransferStagQualifier the deliverTransferStagQualifier to set
	 *//*
	public void setDeliverTransferStagQualifier(String deliverTransferStagQualifier) {
		this.deliverTransferStagQualifier = deliverTransferStagQualifier;
	}*/
	/**
	 * @return the deliverScanActualSite
	 */
	@Column(name="DLVSCNACTSTE")
	public String getDeliverScanActualSite() {
		return deliverScanActualSite;
	}
	/**
	 * @param deliverScanActualSite the deliverScanActualSite to set
	 */
	public void setDeliverScanActualSite(String deliverScanActualSite) {
		this.deliverScanActualSite = deliverScanActualSite;
	}
	/**
	 * @return the carrierFinalDestination
	 */
	@Column(name="CARFNLDST")
	public String getCarrierFinalDestination() {
		return carrierFinalDestination;
	}
	/**
	 * @param carrierFinalDestination the carrierFinalDestination to set
	 */
	public void setCarrierFinalDestination(String carrierFinalDestination) {
		this.carrierFinalDestination = carrierFinalDestination;
	}
	/**
	 * @return the rateTypeIndicator
	 */
	@Column(name="RATTYPIND")
	public String getRateTypeIndicator() {
		return rateTypeIndicator;
	}
	/**
	 * @param rateTypeIndicator the rateTypeIndicator to set
	 */
	public void setRateTypeIndicator(String rateTypeIndicator) {
		this.rateTypeIndicator = rateTypeIndicator;
	}
	/**
	 * @return the greatCircleMiles
	 */
	@Column(name="GCMORGDST")
	public String getGreatCircleMiles() {
		return greatCircleMiles;
	}
	/**
	 * @param greatCircleMiles the greatCircleMiles to set
	 */
	public void setGreatCircleMiles(String greatCircleMiles) {
		this.greatCircleMiles = greatCircleMiles;
	}
	/**
	 * @return the consignmentNumber
	 */
	@Column(name="CSGDOCNUM")
	public String getConsignmentNumber() {
		return consignmentNumber;
	}
	/**
	 * @param consignmentNumber the consignmentNumber to set
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}
	/**
	 * @return the mailCategoryCode
	 */
	@Column(name="MALCTGCOD")
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}
	/**
	 * @param mailCategoryCode the mailCategoryCode to set
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}
	/**
	 * @return the mailSubClassCode
	 */
	@Column(name="MALSUBCLSCOD")
	public String getMailSubClassCode() {
		return mailSubClassCode;
	}
	/**
	 * @param mailSubClassCode the mailSubClassCode to set
	 */
	public void setMailSubClassCode(String mailSubClassCode) {
		this.mailSubClassCode = mailSubClassCode;
	}
	/**
	 * @return the mailProductCode
	 */
	@Column(name="MALPRDTYP")
	public String getMailProductCode() {
		return mailProductCode;
	}
	/**
	 * @param mailProductCode the mailProductCode to set
	 */
	public void setMailProductCode(String mailProductCode) {
		this.mailProductCode = mailProductCode;
	}
	/**
	 * @return the payRateCode
	 */
	@Column(name="PAYRATCOD")
	public String getPayRateCode() {
		return payRateCode;
	}
	/**
	 * @param payRateCode the payRateCode to set
	 */
	public void setPayRateCode(String payRateCode) {
		this.payRateCode = payRateCode;
	}
	/**
	 * @return the lineHaulDollarRate
	 */
	@Column(name="LINHALUSDRAT")
	public double getLineHaulDollarRate() {
		return lineHaulDollarRate;
	}
	/**
	 * @param lineHaulDollarRate the lineHaulDollarRate to set
	 */
	public void setLineHaulDollarRate(double lineHaulDollarRate) {
		this.lineHaulDollarRate = lineHaulDollarRate;
	}
	/**
	 * @return the lineHaulSDRRate
	 */
	@Column(name="LINHALSDRRAT")
	public double getLineHaulSDRRate() {
		return lineHaulSDRRate;
	}
	/**
	 * @param lineHaulSDRRate the lineHaulSDRRate to set
	 */
	public void setLineHaulSDRRate(double lineHaulSDRRate) {
		this.lineHaulSDRRate = lineHaulSDRRate;
	}
	/**
	 * @return the terminalHandlingDollarRate
	 */
	@Column(name="TERHDLUSDRAT")
	public double getTerminalHandlingDollarRate() {
		return terminalHandlingDollarRate;
	}
	/**
	 * @param terminalHandlingDollarRate the terminalHandlingDollarRate to set
	 */
	public void setTerminalHandlingDollarRate(double terminalHandlingDollarRate) {
		this.terminalHandlingDollarRate = terminalHandlingDollarRate;
	}
	/**
	 * @return the terminalHandlingSDRRate
	 */
	@Column(name="TERHDLSDRRAT")
	public double getTerminalHandlingSDRRate() {
		return terminalHandlingSDRRate;
	}
	/**
	 * @param terminalHandlingSDRRate the terminalHandlingSDRRate to set
	 */
	public void setTerminalHandlingSDRRate(double terminalHandlingSDRRate) {
		this.terminalHandlingSDRRate = terminalHandlingSDRRate;
	}
	/**
	 * @return the specialPerKiloDollarRate
	 */
	@Column(name="SPLPERKGMUSDRAT")
	public double getSpecialPerKiloDollarRate() {
		return specialPerKiloDollarRate;
	}
	/**
	 * @param specialPerKiloDollarRate the specialPerKiloDollarRate to set
	 */
	public void setSpecialPerKiloDollarRate(double specialPerKiloDollarRate) {
		this.specialPerKiloDollarRate = specialPerKiloDollarRate;
	}
	/**
	 * @return the specialPerKiloSDRRate
	 */
	@Column(name="SPLPERKGMSDRRAT")
	public double getSpecialPerKiloSDRRate() {
		return specialPerKiloSDRRate;
	}
	/**
	 * @param specialPerKiloSDRRate the specialPerKiloSDRRate to set
	 */
	public void setSpecialPerKiloSDRRate(double specialPerKiloSDRRate) {
		this.specialPerKiloSDRRate = specialPerKiloSDRRate;
	}
	/**
	 * @return the containerRate
	 */
	@Column(name="CNTRAT")
	public double getContainerRate() {
		return containerRate;
	}
	/**
	 * @param containerRate the containerRate to set
	 */
	public void setContainerRate(double containerRate) {
		this.containerRate = containerRate;
	}
	/**
	 * @return the containerType
	 */
	@Column(name="CONTYP")
	public String getContainerType() {
		return containerType;
	}
	/**
	 * @param containerType the containerType to set
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	/**
	 * @return the weightUnit
	 */
	@Column(name="MALWGTUNT")
	public String getWeightUnit() {
		return weightUnit;
	}
	/**
	 * @param weightUnit the weightUnit to set
	 */
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	/**
	 * @return the containerWeightUnit
	 */
	@Column(name="CONWGTUNT")
	public String getContainerWeightUnit() {
		return containerWeightUnit;
	}
	/**
	 * @param containerWeightUnit the containerWeightUnit to set
	 */
	public void setContainerWeightUnit(String containerWeightUnit) {
		this.containerWeightUnit = containerWeightUnit;
	}
	/**
	 * @return the containerGrossWeight
	 */
	@Column(name="CONGRSWGT")
	public double getContainerGrossWeight() {
		return containerGrossWeight;
	}
	/**
	 * @param containerGrossWeight the containerGrossWeight to set
	 */
	public void setContainerGrossWeight(double containerGrossWeight) {
		this.containerGrossWeight = containerGrossWeight;
	}
	/**
	 * @return the consignmentCompletionDate
	 */
	@Column(name="CSGCMPDAT")
	public Calendar getConsignmentCompletionDate() {
		return consignmentCompletionDate;
	}
	/**
	 * @param consignmentCompletionDate the consignmentCompletionDate to set
	 */
	public void setConsignmentCompletionDate(Calendar consignmentCompletionDate) {
		this.consignmentCompletionDate = consignmentCompletionDate;
	}
	/**
	 * @return the contractType
	 */
	@Column(name="CTRTYP")
	public String getContractType() {
		return contractType;
	}
	/**
	 * @param contractType the contractType to set
	 */
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	/**
	 * @return the grossWeight
	 */
	@Column(name="MALGRSWGT")
	public double getGrossWeight() {
		return grossWeight;
	}
	/**
	 * @param grossWeight the grossWeight to set
	 */
	public void setGrossWeight(double grossWeight) {
		this.grossWeight = grossWeight;
	}
	/**
	 * @return the carrierToPay
	 */
	@Column(name="CARTOOPAY")
	public String getCarrierToPay() {
		return carrierToPay;
	}
	/**
	 * @param carrierToPay the carrierToPay to set
	 */
	public void setCarrierToPay(String carrierToPay) {
		this.carrierToPay = carrierToPay;
	}
	/**
	 * @return the orginAirport
	 */
	@Column(name="ORGARPCOD")
	public String getOrginAirport() {
		return orginAirport;
	}
	/**
	 * @param orginAirport the orginAirport to set
	 */
	public void setOrginAirport(String orginAirport) {
		this.orginAirport = orginAirport;
	}
	/**
	 * @return the destinationAirport
	 */
	@Column(name="DSTARPCOD")
	public String getDestinationAirport() {
		return destinationAirport;
	}
	/**
	 * @param destinationAirport the destinationAirport to set
	 */
	public void setDestinationAirport(String destinationAirport) {
		this.destinationAirport = destinationAirport;
	}
	/**
	 * @return the offlineOriginAirport
	 */
	@Column(name="OFLORGARP")
	public String getOfflineOriginAirport() {
		return offlineOriginAirport;
	}
	/**
	 * @param offlineOriginAirport the offlineOriginAirport to set
	 */
	public void setOfflineOriginAirport(String offlineOriginAirport) {
		this.offlineOriginAirport = offlineOriginAirport;
	}
	/**
	 * @return the deliveryScanCarrierCode
	 */
	@Column(name="DLVSCNCAR")
	public String getDeliveryScanCarrierCode() {
		return deliveryScanCarrierCode;
	}
	/**
	 * @param deliveryScanCarrierCode the deliveryScanCarrierCode to set
	 */
	public void setDeliveryScanCarrierCode(String deliveryScanCarrierCode) {
		this.deliveryScanCarrierCode = deliveryScanCarrierCode;
	}
	/**
	 * @return the originalOriginAirport
	 */
	@Column(name="ORIORGARP")
	public String getOriginalOriginAirport() {
		return originalOriginAirport;
	}
	/**
	 * @param originalOriginAirport the originalOriginAirport to set
	 */
	public void setOriginalOriginAirport(String originalOriginAirport) {
		this.originalOriginAirport = originalOriginAirport;
	}
	/**
	 * @return the finalDestination
	 */
	@Column(name="FNLDST")
	public String getFinalDestination() {
		return finalDestination;
	}
	/**
	 * @param finalDestination the finalDestination to set
	 */
	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}
	/**
	 * @return the routeDepatureDate
	 */
	@Column(name="ROUDEPDAT")
	public Calendar getRouteDepatureDate() {
		return routeDepatureDate;
	}
	/**
	 * @param routeDepatureDate the routeDepatureDate to set
	 */
	public void setRouteDepatureDate(Calendar routeDepatureDate) {
		this.routeDepatureDate = routeDepatureDate;
	}
	/**
	 * @return the possessionScanDate
	 */
	@Column(name="POSSCNDAT")
	public Calendar getPossessionScanDate() {
		return possessionScanDate;
	}
	/**
	 * @param possessionScanDate the possessionScanDate to set
	 */
	public void setPossessionScanDate(Calendar possessionScanDate) {
		this.possessionScanDate = possessionScanDate;
	}
	/**
	 * @return the deliveryDate
	 */
	@Column(name="DLVSCNDAT")
	public Calendar getDeliveryDate() {
		return deliveryDate;
	}
	/**
	 * @param deliveryDate the deliveryDate to set
	 */
	public void setDeliveryDate(Calendar deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	/**
	 * @return the baseTotalAmount
	 */
	/*@Column(name="BASTOTAMT")
	public double getBaseTotalAmount() {
		return baseTotalAmount;
	}
	*//**
	 * @param baseTotalAmount the baseTotalAmount to set
	 *//*
	public void setBaseTotalAmount(double baseTotalAmount) {
		this.baseTotalAmount = baseTotalAmount;
	}*/
	/**
	 * @return the deliveryScanExpectedSite
	 */
	@Column(name="DLVSCNEXPSTE")
	public String getDeliveryScanExpectedSite() {
		return deliveryScanExpectedSite;
	}
	/**
	 * @param deliveryScanExpectedSite the deliveryScanExpectedSite to set
	 */
	public void setDeliveryScanExpectedSite(String deliveryScanExpectedSite) {
		this.deliveryScanExpectedSite = deliveryScanExpectedSite;
	}
	/**
	 * @return the messageText
	 */
	@Column(name="MSGTXT")
	public String getMessageText() {
		return messageText;
	}
	/**
	 * @param messageText the messageText to set
	 */
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	/**
	 * @return the claimRefNumber
	 */
	@Column(name="CLMREFNUM")
	public String getClaimRefNumber() {
		return claimRefNumber;
	}
	/**
	 * @param claimRefNumber the claimRefNumber to set
	 */
	public void setClaimRefNumber(String claimRefNumber) {
		this.claimRefNumber = claimRefNumber;
	}

	@Version
	 @Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 *
	 */
	public GPAInvoicMsgRef(){

	}


	/**
	 *
	 * @param gpaInvoicMsgRef
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static GPAInvoicMsgRef find(GPAInvoicMsgRefPK gpaInvoicMsgRef)
		      throws FinderException, SystemException {
		      return PersistenceController.getEntityManager().find(GPAInvoicMsgRef.class, gpaInvoicMsgRef);
		  }


	/**
	 *
	 * @return
	 * @throws SystemException
	 */
	private static MRAGPAReportingDAO constructDAO() throws SystemException {
		try {
			return MRAGPAReportingDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO(
							"mail.mra.gpareporting"));
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}


	/**
	 * @author A-7371
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public Collection<ClaimDetailsVO> findMailBagsForMessageGeneration(String companyCode) throws SystemException {

		Collection<ClaimDetailsVO> claimDetailsVO=null;
		claimDetailsVO=constructDAO().findMailBagsForMessageGeneration(companyCode);
		return claimDetailsVO;
	}

}
