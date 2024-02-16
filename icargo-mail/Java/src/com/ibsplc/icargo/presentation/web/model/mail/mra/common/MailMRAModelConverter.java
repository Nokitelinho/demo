/*
 * MailMRAModelConverter.java Created on Nov 22, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

/**
 * Revision History
 * Revision 	 Date      	         Author			Description
 * 0.1		     Nov 22, 2018	     A-8464		First draft
 */

package com.ibsplc.icargo.presentation.web.model.mail.mra.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GeneratePASSFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SurchargeBillingDetailVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchSettlementDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page; 
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.time.DateUtilities;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;


public class MailMRAModelConverter {
	
	private static final String PALIST_TOAPPLY_CONTRACTRAT="mailtracking.mra.PAlisttoapplycontainerrate";
	
	public static ArrayList<InvoicDetails> constructInvoicDetails(Collection<InvoicDetailsVO> invoicDetailsVOs,Map<String,Collection<OneTimeVO>> oneTimeValues) {

		ArrayList<InvoicDetails> invoicDetailsArrayList = new ArrayList<InvoicDetails>();		
		
		if(invoicDetailsVOs!=null && invoicDetailsVOs.size()>0){
			for( InvoicDetailsVO invoicDetailsVO : invoicDetailsVOs){				
				invoicDetailsArrayList.add(constructInvoicDetail(invoicDetailsVO,oneTimeValues));
			}			
		}		
		return invoicDetailsArrayList;  
	
		}
	
	public static InvoicDetails constructInvoicDetail(InvoicDetailsVO invoicDetailsVO,Map<String,Collection<OneTimeVO>> oneTimeValues){
	InvoicDetails invoicDetails = new InvoicDetails();
		
		invoicDetails.setCharge(invoicDetailsVO.getCharge().getAmount());
		invoicDetails.setClaimamount(invoicDetailsVO.getClaimamount().getAmount());
		invoicDetails.setClaimStatus(invoicDetailsVO.getClaimStatus());
		invoicDetails.setDisincentive(invoicDetailsVO.getDisincentive().getAmount());
		invoicDetails.setIncentive(invoicDetailsVO.getIncentive().getAmount());
		invoicDetails.setInvoicamount(invoicDetailsVO.getInvoicamount().getAmount());
		invoicDetails.setInvoicPayStatus(invoicDetailsVO.getInvoicPayStatus());
		invoicDetails.setMailIdr(invoicDetailsVO.getMailIdr());
		invoicDetails.setNetamount(invoicDetailsVO.getNetamount().getAmount());
		invoicDetails.setRate(invoicDetailsVO.getRate());
		invoicDetails.setRemarks(invoicDetailsVO.getRemarks());
		invoicDetails.setWeight(invoicDetailsVO.getWeight());
		invoicDetails.setTotalSettlementAmount(invoicDetailsVO.getTotalSettlementAmount().getAmount());
		
		//additional info and pk
		invoicDetails.setMailbagInvoicProcessingStatus(invoicDetailsVO.getMailbagInvoicProcessingStatus());
		invoicDetails.setCompanyCode(invoicDetailsVO.getCompanyCode());
		invoicDetails.setVersionNumber(invoicDetailsVO.getVersionNumber());
		invoicDetails.setPoaCode(invoicDetailsVO.getPoaCode());
		invoicDetails.setMailSequenceNumber(invoicDetailsVO.getMailSequenceNumber());
		invoicDetails.setCurrencyCode(invoicDetailsVO.getCurrencyCode());
		invoicDetails.setMailSubClass(invoicDetailsVO.getMailSubClass());
		
		//expanded detials info
		invoicDetails.setConsignment(invoicDetailsVO.getConsignment());
		invoicDetails.setOrigin(invoicDetailsVO.getOrigin());
		invoicDetails.setDestination(invoicDetailsVO.getDestination());
		invoicDetails.setMailClass(invoicDetailsVO.getMailClass());
		invoicDetails.setCodeshareInterlinePartner(invoicDetailsVO.getCodeshareInterlinePartner());
		invoicDetails.setScheduledDelivery(invoicDetailsVO.getScheduledDelivery());
		invoicDetails.setRegionCode(invoicDetailsVO.getRegionCode());
		invoicDetails.setCodeShareProrate(invoicDetailsVO.getCodeShareProrate().getAmount());
		invoicDetails.setCodeShareAmount(invoicDetailsVO.getCodeShareAmount().getAmount());
		
		//claim range sum
		invoicDetails.setClmfiftytohundred(invoicDetailsVO.getClmfiftytohundred());
		invoicDetails.setClmfivtoten(invoicDetailsVO.getClmfivtoten());
		invoicDetails.setClmgrtfivhundred(invoicDetailsVO.getClmgrtfivhundred());
		invoicDetails.setClmhundredtofivhundred(invoicDetailsVO.getClmhundredtofivhundred());
		invoicDetails.setClmtentotwentyfiv(invoicDetailsVO.getClmtentotwentyfiv());
		invoicDetails.setClmtwentyfivtofifty(invoicDetailsVO.getClmtwentyfivtofifty());
		
		if(invoicDetailsVO.getWeightUnit()!=null &&!invoicDetailsVO.getWeightUnit().isEmpty()){//IASCB-42403
		invoicDetails.setWeightUnit(replaceOneTimeValues(oneTimeValues,MRAConstantsVO.KEY_WGTUNITCOD_ONETIME,invoicDetailsVO.getWeightUnit()));
		}
		//invoicDetails.setWeightUnit(invoicDetailsVO.getWeightUnit());
		
		int claimLessFive = Integer.parseInt(invoicDetailsVO.getClmlessfiv());
		int awaitInvoic = Integer.parseInt(invoicDetailsVO.getCntawtinc());
		claimLessFive += awaitInvoic;
		invoicDetails.setClmlessfiv(Integer.toString(claimLessFive));
		invoicDetails.setSerialNumber(invoicDetailsVO.getSerialNumber());
		invoicDetails.setForceMajeureStatus(invoicDetailsVO.getForceMajeureStatus());
		invoicDetails.setCntawtinc(invoicDetailsVO.getCntawtinc());
		invoicDetails.setCntovrnotmra(invoicDetailsVO.getCntovrnotmra());
		invoicDetails.setClmzropay(invoicDetailsVO.getClmzropay());
		invoicDetails.setClmnoinc(invoicDetailsVO.getClmnoinc());
		invoicDetails.setClmratdif(invoicDetailsVO.getClmratdif());
		invoicDetails.setClmwgtdif(invoicDetailsVO.getClmwgtdif());
		invoicDetails.setClmmisscn(invoicDetailsVO.getClmmisscn());
		invoicDetails.setClmlatdlv(invoicDetailsVO.getClmlatdlv());
		invoicDetails.setClmsrvrsp(invoicDetailsVO.getClmsrvrsp());
		invoicDetails.setLatdlv(invoicDetailsVO.getLatdlv());
		invoicDetails.setDlvscnwrg(invoicDetailsVO.getDlvscnwrg());
		invoicDetails.setMisorgscn(invoicDetailsVO.getMisorgscn());
		invoicDetails.setMisdstscn(invoicDetailsVO.getMisdstscn());
		invoicDetails.setFulpaid(invoicDetailsVO.getFulpaid());
		invoicDetails.setOvrratdif(invoicDetailsVO.getOvrratdif());
		invoicDetails.setOvrwgtdif(invoicDetailsVO.getOvrwgtdif());
		invoicDetails.setOvrclsdif(invoicDetailsVO.getOvrclsdif());
		invoicDetails.setOvrsrvrsp(invoicDetailsVO.getOvrsrvrsp());
		invoicDetails.setOvroth(invoicDetailsVO.getOvroth());
		invoicDetails.setClmoth(invoicDetailsVO.getClmoth());
		invoicDetails.setClmnotinv(invoicDetailsVO.getClmnotinv());
		invoicDetails.setOvrpayacp(invoicDetailsVO.getOvrpayacp());
		invoicDetails.setOvrpayrej(invoicDetailsVO.getOvrpayrej());
		invoicDetails.setClmfrcmjr(invoicDetailsVO.getClmfrcmjr());
		invoicDetails.setDummyorg(invoicDetailsVO.getDummyorg());
		invoicDetails.setDummydst(invoicDetailsVO.getDummydst());
		invoicDetails.setShrpayacp(invoicDetailsVO.getShrpayacp());
		invoicDetails.setClmstagen(invoicDetailsVO.getClmstagen());
		invoicDetails.setClmstasub(invoicDetailsVO.getClmstasub());
	    invoicDetails.setAmotobeact(invoicDetailsVO.getAmotobeact());
		invoicDetails.setAmotact(invoicDetailsVO.getAmotact());
		
		return invoicDetails;
	}
	
	public static ArrayList<InvoicDetailsVO> constructInvoicDetailVOs(Collection<InvoicDetails> invoicDetailsList) {

		ArrayList<InvoicDetailsVO> invoicDetailsVOArrayList = new ArrayList<InvoicDetailsVO>();		
		
		if(invoicDetailsList!=null && invoicDetailsList.size()>0){
			for( InvoicDetails invoicDetails : invoicDetailsList){				
				invoicDetailsVOArrayList.add(constructInvoicDetailVO(invoicDetails));
			}			
		}		
		return invoicDetailsVOArrayList;  
	
		}
	
	public static InvoicDetailsVO constructInvoicDetailVO(InvoicDetails invoicDetail){
		InvoicDetailsVO invoicDetailsVO = new InvoicDetailsVO();
		
		
		try {
			
			Money netAmtInCtr = CurrencyHelper.getMoney(invoicDetail.getCurrencyCode());
			netAmtInCtr.setAmount(invoicDetail.getNetamount());
			invoicDetailsVO.setNetamount(netAmtInCtr);
			
			Money disincAmtInCtr = CurrencyHelper.getMoney(invoicDetail.getCurrencyCode());
			disincAmtInCtr.setAmount(invoicDetail.getDisincentive());
			invoicDetailsVO.setDisincentive(disincAmtInCtr);
			
			Money incAmtInCtr = CurrencyHelper.getMoney(invoicDetail.getCurrencyCode());
			incAmtInCtr.setAmount(invoicDetail.getIncentive());
			invoicDetailsVO.setIncentive(incAmtInCtr);
			
			Money chargeAmtInCtr = CurrencyHelper.getMoney(invoicDetail.getCurrencyCode());
			chargeAmtInCtr.setAmount(invoicDetail.getCharge());
			invoicDetailsVO.setCharge(chargeAmtInCtr);
			
			Money invoicAmtInCtr = CurrencyHelper.getMoney(invoicDetail.getCurrencyCode());
			invoicAmtInCtr.setAmount(invoicDetail.getInvoicamount());
			invoicDetailsVO.setInvoicamount(invoicAmtInCtr);
			
			Money claimAmtInCtr = CurrencyHelper.getMoney(invoicDetail.getCurrencyCode());
			claimAmtInCtr.setAmount(invoicDetail.getClaimamount());
			invoicDetailsVO.setClaimamount(claimAmtInCtr);
			
			Money codeshareProratAmtInCtr = CurrencyHelper.getMoney(invoicDetail.getCurrencyCode());
			codeshareProratAmtInCtr.setAmount(invoicDetail.getCodeShareProrate());
			invoicDetailsVO.setCodeShareProrate(codeshareProratAmtInCtr);
			
			Money oalAmtInCtr = CurrencyHelper.getMoney(invoicDetail.getCurrencyCode());
			oalAmtInCtr.setAmount(invoicDetail.getCodeShareAmount());
			invoicDetailsVO.setCodeShareAmount(oalAmtInCtr);
			
		} catch (CurrencyException currencyException) {
			
		}
		
		if(invoicDetail.getClaimStatus()!=null) {
		invoicDetailsVO.setClaimStatus(invoicDetail.getClaimStatus());
		}
		
		if(invoicDetail.getInvoicPayStatus()!=null) {
		invoicDetailsVO.setInvoicPayStatus(invoicDetail.getInvoicPayStatus());
		}
		if(invoicDetail.getMailbagInvoicProcessingStatus()!=null) {
		invoicDetailsVO.setMailbagInvoicProcessingStatus(invoicDetail.getMailbagInvoicProcessingStatus());
		}
		if(invoicDetail.getMailIdr()!=null) {
		invoicDetailsVO.setMailIdr(invoicDetail.getMailIdr());
		}
		
		invoicDetailsVO.setRate(invoicDetail.getRate());
		if(invoicDetail.getRemarks()!=null) {
		invoicDetailsVO.setRemarks(invoicDetail.getRemarks());
		}
		invoicDetailsVO.setWeight(invoicDetail.getWeight());
		if(invoicDetail.getScheduledDelivery()!=null) {
		invoicDetailsVO.setScheduledDelivery(invoicDetail.getScheduledDelivery());
		}
		if(invoicDetail.getCurrencyCode()!=null) {
		invoicDetailsVO.setCurrencyCode(invoicDetail.getCurrencyCode());
		}
		if(invoicDetail.getMailSubClass()!=null) {
		invoicDetailsVO.setMailSubClass(invoicDetail.getMailSubClass());
		}
		
		//pk
		
		invoicDetailsVO.setVersionNumber(invoicDetail.getVersionNumber());
		invoicDetailsVO.setMailSequenceNumber(invoicDetail.getMailSequenceNumber());
		invoicDetailsVO.setPoaCode(invoicDetail.getPoaCode());
		invoicDetailsVO.setCompanyCode(invoicDetail.getCompanyCode());
		
		invoicDetailsVO.setSerialNumber(invoicDetail.getSerialNumber());
		
		return invoicDetailsVO;
	}
	/**
 * 	Method		:	MailMRAModelConverter.constructOneTimeValues
 *	Added by 	:	A-4809 on Jan 31, 2019
 * 	Used for 	:
 *	Parameters	:	@param oneTimeValues
 *	Parameters	:	@return 
 *	Return type	: 	Map<String,Collection<OneTime>>
	 */
	public static Map<String, Collection<OneTime>> constructOneTimeValues(
			Map<String, Collection<OneTimeVO>> oneTimeValues) {
		HashMap<String, Collection<OneTime>> oneTimeValuesMap = new HashMap<String, Collection<OneTime>>();
		for (Map.Entry<String, Collection<OneTimeVO>> oneTimeValue : oneTimeValues.entrySet()) {
			ArrayList<OneTime> oneTimes = new ArrayList<OneTime>();
			for (OneTimeVO oneTimeVO : oneTimeValue.getValue()) {
				OneTime oneTime = new OneTime();
				oneTime.setFieldType(oneTimeVO.getFieldType());
				oneTime.setFieldValue(oneTimeVO.getFieldValue());
				oneTime.setFieldDescription(oneTimeVO.getFieldDescription());
				oneTimes.add(oneTime);
			}
			oneTimeValuesMap.put(oneTimeValue.getKey(), oneTimes);
		}
		return oneTimeValuesMap;
	}
/**
 * 	Method		:	MailMRAModelConverter.constructBillingDetails
 *	Added by 	:	A-4809 on Jan 31, 2019
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVOs
 *	Parameters	:	@return 
 *	Return type	: 	ArrayList<GPABillingEntryDetails>
 */
	public static ArrayList<GPABillingEntryDetails> constructBillingDetails(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs,Map<String, Collection<OneTimeVO>> oneTimeValues,Map<String, String> systemParameters){
		ArrayList<GPABillingEntryDetails> gpaBillingEntryDetails = new ArrayList<GPABillingEntryDetails>();		
		if(documentBillingDetailsVOs!=null && !documentBillingDetailsVOs.isEmpty()){
			for( DocumentBillingDetailsVO detailsVO : documentBillingDetailsVOs){				
				gpaBillingEntryDetails.add(constructBillingDetail(detailsVO,oneTimeValues,systemParameters));
			}			
		}		
		return gpaBillingEntryDetails; 
		}
/**
 * 	Method		:	MailMRAModelConverter.constructBillingDetail
 *	Added by 	:	A-4809 on Jan 31, 2019
 * 	Used for 	:
 *	Parameters	:	@param detailsVO
 *	Parameters	:	@return 
 *	Return type	: 	GPABillingEntryDetails
 */
	public static GPABillingEntryDetails constructBillingDetail(DocumentBillingDetailsVO detailsVO,Map<String, Collection<OneTimeVO>> oneTimeValues,Map<String, String> systemParameters) {
		GPABillingEntryDetails details =new GPABillingEntryDetails();
		StringBuilder weightAndUnit = new StringBuilder(); 
		
		String paListToApplyContainerRate=systemParameters.get(PALIST_TOAPPLY_CONTRACTRAT);
		
		if (paListToApplyContainerRate != null && !paListToApplyContainerRate.isEmpty()
				&& DocumentBillingDetailsVO.FLAG_YES.equals(detailsVO.getPaBuilt())
				&& ("ALL".equals(paListToApplyContainerRate)
						|| paListToApplyContainerRate.contains(detailsVO.getGpaCode()))
				&& detailsVO.getActualWeightUnit() != null && !detailsVO.getActualWeightUnit().isEmpty()
				&& detailsVO.getActualWeight() != 0 && detailsVO.getCcaType()==null) {
			detailsVO.setUnitCode(detailsVO.getActualWeightUnit());
			detailsVO.setWeight(detailsVO.getActualWeight());
		}

		details.setMailbagID(detailsVO.getBillingBasis());
		if(detailsVO.getOrigin()!=null && !detailsVO.getOrigin().isEmpty()){
		details.setRatedSector(new StringBuilder(detailsVO.getOrigin()).append("-").append(detailsVO.getDestination()).toString());
		}
		details.setGpaCode(detailsVO.getGpaCode());
		
		//Added by A-8527 for IASCB-22915
				if(detailsVO.getUnitCode()!=null &&!detailsVO.getUnitCode().isEmpty()){
				details.setWgtunitcod(replaceOneTimeValues(oneTimeValues,MRAConstantsVO.KEY_WGTUNITCOD_ONETIME,detailsVO.getUnitCode()));
				}
				String weightOfMailbag=Double.toString(detailsVO.getWeight()); 
				weightAndUnit.append(weightOfMailbag).append(details.getWgtunitcod());
		String combinedWeight= 	weightAndUnit.toString();	
		details.setWgt(combinedWeight);
		details.setWeight(detailsVO.getWeight());
		details.setCurrency(detailsVO.getCurrency());
		details.setApplicableRate(getScaledValue(detailsVO.getApplicableRate(),5));
		if(detailsVO.getNetAmount()!=null)
		details.setNetAmount(getScaledValue(detailsVO.getNetAmount().getAmount(),5));
		if(detailsVO.getServiceTax()!=null)
		details.setServiceTax(getScaledValue(detailsVO.getServiceTax().getAmount(),5));
		details.setRateType(detailsVO.getRateType());
		details.setCsgDocumentNumber(detailsVO.getCsgDocumentNumber());
		details.setHni(detailsVO.getHni());
		details.setDeclaredValue(detailsVO.getDeclaredValue());
		details.setInvoiceNumber(detailsVO.getInvoiceNumber());
		details.setOrgOfficeOfExchange(detailsVO.getOrgOfficeOfExchange());
		details.setYear(detailsVO.getYear());
		details.setRegInd(detailsVO.getRegInd());
		if(detailsVO.getSurChg()!=null)
		details.setSurChg(getScaledValue(detailsVO.getSurChg().getAmount(),5)); 
		details.setCcaRefNumber(detailsVO.getCcaRefNumber());
		if(detailsVO.getCcaRefNumber()!=null && !detailsVO.getCcaRefNumber().isEmpty()){
		details.setMcaIndicator("MCA");
		}
		details.setDestOfficeOfExchange(detailsVO.getDestOfficeOfExchange());
		details.setDsn(detailsVO.getDsn());
		if(detailsVO.getGrossAmount()!=null)
		details.setGrossAmount(getScaledValue(detailsVO.getGrossAmount().getAmount(),5));
		details.setRateIdentifier(detailsVO.getRateIdentifier());
		details.setCategory(detailsVO.getCategory());
		details.setRsn(detailsVO.getRsn());
		if(detailsVO.getValCharges()!=null)
		details.setValCharges(getScaledValue(detailsVO.getValCharges().getAmount(),5));
		details.setRateLineIdentifier(detailsVO.getRateLineIdentifier());
		details.setSubClass(detailsVO.getSubClass());
		if(DocumentBillingDetailsVO.FLAG_YES.equals(detailsVO.getIsUSPSPerformed())){
		details.setIsUSPSPerformed("Yes");
		}else if(DocumentBillingDetailsVO.FLAG_NO.equals(detailsVO.getIsUSPSPerformed())){
			details.setIsUSPSPerformed("No");	
		}else{
			details.setIsUSPSPerformed("NA");
		}
		if(detailsVO.getBillingBasis().trim().length()==29){
			details.setMailType("I");
			details.setMailWeight(detailsVO.getBillingBasis().substring(25, 29));
		}else{
			details.setMailType("D");
		}
		if(detailsVO.getBillingStatus()!=null && !detailsVO.getBillingStatus().isEmpty()){
			details.setBillingStatus(replaceOneTimeValues(oneTimeValues,MRAConstantsVO.KEY_BILLING_TYPE_ONETIME,detailsVO.getBillingStatus()).toUpperCase());
		} 
		details.setMailSequenceNumber(detailsVO.getMailSequenceNumber());
		details.setSequenceNumber(detailsVO.getSerialNumber());
		details.setInvoiceSerialNumber(detailsVO.getInvSerialNumber());
		details.setCsgSequenceNumber(detailsVO.getCsgSequenceNumber());
		details.setAirlineIdentifier(detailsVO.getAirlineIdr());
		details.setContainerID(detailsVO.getContainerID());
		details.setActualWeight(detailsVO.getActualWeight());
		if(DocumentBillingDetailsVO.FLAG_YES.equals(detailsVO.getPaBuilt()))
		{
			details.setPaBuilt("Yes");
		}
		else if (DocumentBillingDetailsVO.FLAG_NO.equals(detailsVO.getPaBuilt()))
		{
			details.setPaBuilt("No");
			
		}
		
		details.setActualWeightUnit(replaceOneTimeValues(oneTimeValues,MRAConstantsVO.KEY_WGTUNITCOD_ONETIME,detailsVO.getActualWeightUnit()));
	//	details.setPaBuilt(detailsVO.getPaBuilt());
		details.setRemarks(detailsVO.getRemarks());
		
		return details;
	} 
	/**
	 * 	Method		:	MailMRAModelConverter.replaceOneTimeValues
	 *	Added by 	:	A-4809 on Feb 6, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param oneTimeValues
	 *	Parameters	:	@param fldType
	 *	Parameters	:	@param fldValue
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public static String replaceOneTimeValues(Map<String, Collection<OneTimeVO>> oneTimeValues, String fldType , String fldValue){
		String fldDesc = null;
		Collection<OneTimeVO> oneTimeVOs = oneTimeValues.get(fldType);
		for(OneTimeVO oneTimeVO : oneTimeVOs){
			if(oneTimeVO.getFieldValue().equals(fldValue)){
				fldDesc = oneTimeVO.getFieldDescription();
				break;
			}
		}
		return fldDesc;
	}
/**
	 * 	Method		:	MailMRAModelConverter.replaceOneTimeDesc
	 *	Added by 	:	A-4809 on Feb 6, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param oneTimeValues
	 *	Parameters	:	@param fldType
	 *	Parameters	:	@param fldDesc
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */	
	public static String replaceOneTimeDesc(Map<String, Collection<OneTimeVO>> oneTimeValues, String fldType , String fldDesc){
		String fldVal = null;
		Collection<OneTimeVO> oneTimeVOs = oneTimeValues.get(fldType);
		for(OneTimeVO oneTimeVO : oneTimeVOs){
			if(oneTimeVO.getFieldDescription().equals(fldDesc)){
				fldVal = oneTimeVO.getFieldValue();
				break;
			}
		}
		return fldVal;		
	}
/**
 * 	Method		:	MailMRAModelConverter.constructConsginmentDetails
 *	Added by 	:	A-4809 on Feb 13, 2019
 * 	Used for 	:
 *	Parameters	:	@param consignmentDocVOs
 *	Parameters	:	@param oneTimeValues
 *	Parameters	:	@return 
 *	Return type	: 	ArrayList<ConsignmentDetails>
 */
	public static ArrayList<ConsignmentDetails> constructConsginmentDetails(
			Page<ConsignmentDocumentVO> consignmentDocVOs, Map<String, Collection<OneTimeVO>> oneTimeValues) {
		ArrayList<ConsignmentDetails>  consignmentDetails = new ArrayList<ConsignmentDetails>();
		if(consignmentDocVOs!=null && !consignmentDocVOs.isEmpty()){
			for(ConsignmentDocumentVO docVO : consignmentDocVOs){
				consignmentDetails.add(constructConsignmentDetail(docVO,oneTimeValues));
			}
		}
		return consignmentDetails;
	}
/**
 * 	Method		:	MailMRAModelConverter.constructConsignmentDetail
 *	Added by 	:	A-4809 on Feb 13, 2019
 * 	Used for 	:
 *	Parameters	:	@param docVO
 *	Parameters	:	@param oneTimeValues
 *	Parameters	:	@return 
 *	Return type	: 	ConsignmentDetails
 */
	private static ConsignmentDetails constructConsignmentDetail(ConsignmentDocumentVO docVO,
		Map<String, Collection<OneTimeVO>> oneTimeValues) {
		ConsignmentDetails detail= new ConsignmentDetails();
		detail.setCompanyCode(docVO.getCompanyCode());
		detail.setConsignmentNumber(docVO.getConsignmentNumber());
		detail.setCurrency(docVO.getCurrency());
		detail.setDestination(docVO.getDestination());
		detail.setDestinationOE(docVO.getDestinationOfficeOfExchange());
		detail.setDsnNumber(docVO.getDsnNumber());
		if(docVO.getMailCategory()!=null && !docVO.getMailCategory().isEmpty()){
			detail.setMailCategory(replaceOneTimeValues(oneTimeValues,MRAConstantsVO.KEY_CATEGORY_ONETIME,docVO.getMailCategory()));
		} 
		//detail.setMailCategory(docVO.getMailCategory()); 
		detail.setMailSubClass(docVO.getMailSubClass());
		detail.setOrigin(docVO.getOrgin());
		detail.setOriginOE(docVO.getOriginOfficeOfExchange());   
		detail.setPaCode(docVO.getPaCode());
		detail.setRate(getScaledValue(docVO.getRate(),5));
		detail.setIsUSPSPerformed(docVO.getIsUspsPerformed());
		detail.setMcaNumber(docVO.getMcaNumber());
	return detail;
	}
/**
 * 	Method		:	MailMRAModelConverter.constructDocumentBillingDetailsVOs
 *	Added by 	:	A-4809 on Feb 19, 2019
 * 	Used for 	:
 *	Parameters	:	@param gpaDetails
 *	Parameters	:	@param logonAttributes
 *	Parameters	:	@return 
 *	Return type	: 	ArrayList<DocumentBillingDetailsVO>
 */
	public static ArrayList<DocumentBillingDetailsVO> constructDocumentBillingDetailsVOs(Collection<GPABillingEntryDetails> gpaDetails,
			LogonAttributes logonAttributes){
		 ArrayList<DocumentBillingDetailsVO> documentBillingDetailsVOs = new ArrayList<DocumentBillingDetailsVO>();
		 DocumentBillingDetailsVO billingDetailsVO = null;
		 if(gpaDetails!=null && !gpaDetails.isEmpty()){
			 for(GPABillingEntryDetails detail:gpaDetails){
				 billingDetailsVO = constructDocumentBillingDetailsVO(detail,logonAttributes);
				documentBillingDetailsVOs.add(billingDetailsVO)	; 
			 }
		 }
		 return documentBillingDetailsVOs;
	}
/**
 * 	Method		:	MailMRAModelConverter.constructDocumentBillingDetailsVO
 *	Added by 	:	A-4809 on Feb 19, 2019
 * 	Used for 	:
 *	Parameters	:	@param detail
 *	Parameters	:	@param logonAttributes
 *	Parameters	:	@return 
 *	Return type	: 	DocumentBillingDetailsVO
 */
	private static DocumentBillingDetailsVO constructDocumentBillingDetailsVO(GPABillingEntryDetails detail,
			LogonAttributes logonAttributes) {
		DocumentBillingDetailsVO detailsVO = new DocumentBillingDetailsVO();
		detailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		detailsVO.setBillingBasis(detail.getMailbagID());
		detailsVO.setMailSequenceNumber(detail.getMailSequenceNumber());
		detailsVO.setConsignmentNumber(detail.getCsgDocumentNumber());
		detailsVO.setGpaCode(detail.getGpaCode());
		detailsVO.setCcaRefNumber(detail.getCcaRefNumber()); 
		detailsVO.setDsn(detail.getDsn());
		detailsVO.setBillingStatus(detail.getBillingStatus());
		detailsVO.setAirlineIdr(detail.getAirlineIdentifier());
		detailsVO.setCsgSequenceNumber(detail.getCsgSequenceNumber());
		detailsVO.setInvSerialNumber(detail.getInvoiceSerialNumber());
		detailsVO.setSerialNumber(detail.getSequenceNumber());
		detailsVO.setYear(detail.getYear());
		detailsVO.setOrgOfficeOfExchange(detail.getOrgOfficeOfExchange());
		detailsVO.setDestOfficeOfExchange(detail.getDestOfficeOfExchange());
		detailsVO.setSubClass(detail.getSubClass());
		detailsVO.setCategory(detail.getCategory());
		detailsVO.setRemarks(detail.getRemarks());
		if(detail.getCurrency()!=null){
		try{
		Money surChg =  CurrencyHelper.getMoney(detail.getCurrency());
		surChg.setAmount(detail.getSurChg());
		detailsVO.setSurChg(surChg);
		}catch(CurrencyException currencyException){
		}
		}  
		return detailsVO;
	}
/**
 * 	Method		:	MailMRAModelConverter.constructConsignmentDocumentVOs
 *	Added by 	:	A-4809 on Mar 11, 2019
 * 	Used for 	:
 *	Parameters	:	@param csgDetails
 *	Parameters	:	@param logonAttributes
 *	Parameters	:	@return 
 *	Return type	: 	ArrayList<ConsignmentDocumentVO>
 */
	public static ArrayList<ConsignmentDocumentVO> constructConsignmentDocumentVOs(Collection<ConsignmentDetails> csgDetails,
			LogonAttributes logonAttributes,Map<String, Collection<OneTimeVO>> oneTimeValues){
		 ArrayList<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<ConsignmentDocumentVO>();
		 ConsignmentDocumentVO consignmentDocumentVO = null;
		 if(csgDetails!=null && !csgDetails.isEmpty()){
			 for(ConsignmentDetails detail:csgDetails){
				 consignmentDocumentVO = constructConsignmentDocumentVO(detail,logonAttributes,oneTimeValues);
				 consignmentDocumentVOs.add(consignmentDocumentVO)	; 
			 }
		 }
		 return consignmentDocumentVOs;		
	}
/**
 * 	Method		:	MailMRAModelConverter.constructConsignmentDocumentVO
 *	Added by 	:	A-4809 on Mar 11, 2019
 * 	Used for 	:
 *	Parameters	:	@param detail
 *	Parameters	:	@param logonAttributes
 *	Parameters	:	@return 
 *	Return type	: 	ConsignmentDocumentVO
 */
private static ConsignmentDocumentVO constructConsignmentDocumentVO(ConsignmentDetails detail,
		LogonAttributes logonAttributes,Map<String, Collection<OneTimeVO>> oneTimeValues) {
	ConsignmentDocumentVO detailsVO = new ConsignmentDocumentVO();
	detailsVO.setCompanyCode(logonAttributes.getCompanyCode());
	detailsVO.setConsignmentNumber(detail.getConsignmentNumber());
	detailsVO.setDsnNumber(detail.getDsnNumber());
	detailsVO.setOrgin(detail.getOrigin());
	detailsVO.setDestination(detail.getDestination());
	detailsVO.setRate(detail.getRate());
	detailsVO.setPaCode(detail.getPaCode()); 
	detailsVO.setOriginOfficeOfExchange(detail.getOriginOE());
	detailsVO.setDestinationOfficeOfExchange(detail.getDestinationOE());
	if(detail.getMailCategory()!=null && !detail.getMailCategory().isEmpty()){
		detailsVO.setMailCategory(replaceOneTimeDesc(oneTimeValues,MRAConstantsVO.KEY_CATEGORY_ONETIME,detail.getMailCategory()));
	}	
	//detailsVO.setMailCategory(detail.getMailCategory());
	detailsVO.setMailSubClass(detail.getMailSubClass());
	detailsVO.setIsUspsPerformed(detail.getIsUSPSPerformed());
	detailsVO.setMcaNumber(detail.getMcaNumber());
	detailsVO.setCurrency(detail.getCurrency()); 
	return detailsVO;
}
/**
 * 	Method		:	MailMRAModelConverter.constructSurchargeDetails
 *	Added by 	:	A-4809 on Mar 4, 2019
 * 	Used for 	:
 *	Parameters	:	@param billingVOs
 *	Parameters	:	@return 
 *	Return type	: 	ArrayList<SurchargeDetails>
 */
	public static ArrayList<SurchargeDetails> constructSurchargeDetails(Collection<SurchargeBillingDetailVO> billingVOs){
		ArrayList<SurchargeDetails>  surchargeDetails = new ArrayList<SurchargeDetails>();
		if(billingVOs!=null && !billingVOs.isEmpty()){
			for(SurchargeBillingDetailVO dtlVO : billingVOs){
				surchargeDetails.add(constructSurchargeDetail(dtlVO));
			}
		}
		return surchargeDetails;
	}
/**
 * 	Method		:	MailMRAModelConverter.constructSurchargeDetail
 *	Added by 	:	A-4809 on Mar 4, 2019
 * 	Used for 	:
 *	Parameters	:	@param docVO
 *	Parameters	:	@return 
 *	Return type	: 	SurchargeDetails
 */
	private static SurchargeDetails constructSurchargeDetail(SurchargeBillingDetailVO dtlVO) {
		SurchargeDetails detail = new SurchargeDetails();
		detail.setCompanyCode(dtlVO.getCompanyCode());
		detail.setChargeHead(dtlVO.getChargeHead());
		detail.setChargeHeadDesc(dtlVO.getChargeHeadDesc());
		detail.setApplicableRate(dtlVO.getApplicableRate());
		detail.setChargeAmt(dtlVO.getChargeAmt().getAmount());
		detail.setSequenceNumber(dtlVO.getSequenceNumber());
		detail.setBillingBasis(dtlVO.getBillingBasis());
		detail.setConsigneeDocumentNumber(dtlVO.getConsigneeDocumentNumber());
		detail.setConsigneeSequenceNumber(dtlVO.getConsigneeSequenceNumber());
		detail.setGpaCode(dtlVO.getGpaCode());
		detail.setInvoiceNumber(dtlVO.getInvoiceNumber());
		//detail.setTotalAmount(dtlVO.getTotalAmount());
		return detail;
	}
/**
 * 	Method		:	MailMRAModelConverter.getScaledValue
 *	Added by 	:	A-4809 on 21-May-2020
 * 	Used for 	:
 *	Parameters	:	@param value
 *	Parameters	:	@param precision
 *	Parameters	:	@return 
 *	Return type	: 	double
 */
	private static double getScaledValue(double value, int precision) {
		java.math.BigDecimal bigDecimal = new java.math.BigDecimal(value);
		return bigDecimal.setScale(precision,
				java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
	}
/**
	 * 	Method		:	MailMRAModelConverter.constructBillingScheduleDetailsVOs
	 *	Added by 	:	A-9498 on 26-April-2021
	 * 	Used for 	:
	 *	Parameters	:	@param value
	 *	Parameters	:	@param precision
	 *	Parameters	:	@return 
	 *	Return type	: 	ArrayList
	 */
	public static ArrayList<BillingScheduleDetails> constructBillingScheduleDetailsVOs(
			Collection<BillingScheduleDetailsVO> billingScheduleDetailsVOs) {
		ArrayList<BillingScheduleDetails> billingScheduleDetailsList = new ArrayList<BillingScheduleDetails>();
		BillingScheduleDetails billingScheduleDetails = null;
		if (billingScheduleDetailsVOs != null && billingScheduleDetailsVOs.size() > 0) {
			for (BillingScheduleDetailsVO billingScheduleDetailsVO : billingScheduleDetailsVOs) {
				billingScheduleDetails = constructBillingScheduleDetailsVOs(billingScheduleDetailsVO);
				billingScheduleDetailsList.add(billingScheduleDetails);
			}
		}
		return billingScheduleDetailsList;
	}
	/**
	 * 	Method		:	MailMRAModelConverter.constructBillingScheduleDetailsVOs
	 *	Added by 	:	A-9498 on 26-April-2021
	 * 	Used for 	:
	 *	Parameters	:	@param value
	 *	Parameters	:	@param precision
	 *	Parameters	:	@return 
	 *	Return type	: 	ArrayList
	 */
	public static BillingScheduleDetails constructBillingScheduleDetailsVOs(
			BillingScheduleDetailsVO billingScheduleDetailsVO) {
		ArrayList<BillingScheduleDetails> billingScheduleMasterModels = new ArrayList<BillingScheduleDetails>();
		BillingScheduleDetails billingScheduleMasterModel = new BillingScheduleDetails();
		billingScheduleMasterModel.setCompanyCode(billingScheduleDetailsVO.getCompanyCode());
		if (billingScheduleDetailsVO.getAirLineUploadCutDate() != null) {
			billingScheduleMasterModel.setAirLineUploadCutDate(
					billingScheduleDetailsVO.getAirLineUploadCutDate().toDisplayDateOnlyFormat());
		}
		billingScheduleMasterModel.setBillingPeriod(billingScheduleDetailsVO.getBillingPeriod());
		if (billingScheduleDetailsVO.getBillingPeriodFromDate() != null) {
			billingScheduleMasterModel.setBillingPeriodFromDate(
					billingScheduleDetailsVO.getBillingPeriodFromDate().toDisplayDateOnlyFormat());
		}
		if (billingScheduleDetailsVO.getBillingPeriodToDate() != null) {
			billingScheduleMasterModel.setBillingPeriodToDate(
					billingScheduleDetailsVO.getBillingPeriodToDate().toDisplayDateOnlyFormat());
		}
		if (billingScheduleDetailsVO.getGpaInvoiceGenarateDate() != null) {
			billingScheduleMasterModel.setGpaInvoiceGenarateDate(
					billingScheduleDetailsVO.getGpaInvoiceGenarateDate().toDisplayDateOnlyFormat());
		}
		if (billingScheduleDetailsVO.getInvoiceAvailableDate() != null) {
			billingScheduleMasterModel.setInvoiceAvailableDate(
					billingScheduleDetailsVO.getInvoiceAvailableDate().toDisplayDateOnlyFormat());
		}
		if (billingScheduleDetailsVO.getLastUpdatedTime() != null) {
			billingScheduleMasterModel
					.setLastUpdatedTime(billingScheduleDetailsVO.getLastUpdatedTime().toDisplayDateOnlyFormat());
		}
		billingScheduleMasterModel.setBillingType(billingScheduleDetailsVO.getBillingType());
		if (billingScheduleDetailsVO.getMasterCutDataDate() != null) {
			billingScheduleMasterModel
					.setMasterCutDataDate(billingScheduleDetailsVO.getMasterCutDataDate().toDisplayDateOnlyFormat());
		}
		if (billingScheduleDetailsVO.getPostalOperatorUploadDate() != null) {
			billingScheduleMasterModel.setPostalOperatorUploadDate(
					billingScheduleDetailsVO.getPostalOperatorUploadDate().toDisplayDateOnlyFormat());
		}
		if (billingScheduleDetailsVO.getPassFileGenerateDate() != null) {
			billingScheduleMasterModel.setPassFileGenerateDate(
					billingScheduleDetailsVO.getPassFileGenerateDate().toDisplayDateOnlyFormat());
		}
		billingScheduleMasterModel.setLastUpdatedUser(billingScheduleDetailsVO.getLastUpdatedUser());
		billingScheduleMasterModel.setPeriodNumber(billingScheduleDetailsVO.getPeriodNumber());
		billingScheduleMasterModel.setSerialNumber(billingScheduleDetailsVO.getSerialNumber());
		billingScheduleMasterModel.setTagId(billingScheduleDetailsVO.getTagId());
		billingScheduleMasterModel.setParameterDescription(billingScheduleDetailsVO.getParameterDescription());
		billingScheduleMasterModel.setParamterCode(billingScheduleDetailsVO.getParamterCode());
		billingScheduleMasterModel.setFunctionName(billingScheduleDetailsVO.getFunctionName());
		billingScheduleMasterModel.setFunctionPoint(billingScheduleDetailsVO.getFunctionPoint());
		billingScheduleMasterModel.setExcludeFlag(billingScheduleDetailsVO.getExcludeFlag());
		billingScheduleMasterModel.setParameterValue(billingScheduleDetailsVO.getParameterValue());
		if(billingScheduleDetailsVO.getParamsList().size()>0){ 
			billingScheduleMasterModel.setParameterList(billingScheduleDetailsVO.getParamsList());
		}
		billingScheduleMasterModels.add(billingScheduleMasterModel);
		return billingScheduleMasterModel;
	}
	public static ArrayList<BillingScheduleDetailsVO> constructBillingScheduleDetailsModels(
			Collection<BillingScheduleDetails> billingScheduleDetails) {
		ArrayList<BillingScheduleDetailsVO> billingScheduleDetailsVOs = new ArrayList<BillingScheduleDetailsVO>();
		BillingScheduleDetailsVO billingScheduleDetailsVO = null;
		if (billingScheduleDetailsVOs != null && billingScheduleDetailsVOs.size() > 0) {
			for (BillingScheduleDetails billingScheduleDetail : billingScheduleDetails) {
				billingScheduleDetailsVO = constructBillingScheduleDetailsModels(billingScheduleDetail);
				billingScheduleDetailsVOs.add(billingScheduleDetailsVO);
			}
		}
		return billingScheduleDetailsVOs;
	}
	public static BillingScheduleDetailsVO constructBillingScheduleDetailsModels(
			BillingScheduleDetails billingScheduleMasterModel) {
		ArrayList<BillingScheduleDetailsVO> billingScheduleDetailsVOs = new ArrayList<BillingScheduleDetailsVO>();
		BillingScheduleDetailsVO billingScheduleDetailsVO = new BillingScheduleDetailsVO();
		ArrayList <BillingParameterVO> billingParameterVOs =new ArrayList<BillingParameterVO>();
		
		try {
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			billingScheduleDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		} catch (SystemException e) {
			e.printStackTrace();
		}
		billingScheduleDetailsVO = new BillingScheduleDetailsVO();
		billingScheduleDetailsVO
				.setAirLineUploadCutDate(convertToLocaldate(billingScheduleMasterModel.getAirLineUploadCutDate()));
		if (billingScheduleMasterModel.getBillingPeriodFromDate() != null && !(billingScheduleMasterModel.getBillingPeriodFromDate().isEmpty()) 
				&& billingScheduleMasterModel.getBillingPeriodToDate() != null && !(billingScheduleMasterModel.getBillingPeriodToDate().isEmpty())) {
			int period = DateUtilities.getDifferenceInDays(billingScheduleMasterModel.getBillingPeriodFromDate(),
					billingScheduleMasterModel.getBillingPeriodToDate(), "dd-MMM-yyyy");
			if (period <= 15) {
				billingScheduleDetailsVO.setBillingPeriod("B");
			} else {
				billingScheduleDetailsVO.setBillingPeriod("M");
			}
		}   
		if (billingScheduleMasterModel.get__opFlag() != null && billingScheduleMasterModel.get__opFlag().equals("D")) {
			billingScheduleDetailsVO.setSerialNumber(billingScheduleMasterModel.getSerialNumber());
		}
		if (billingScheduleMasterModel.getBillingPeriodFromDate() != null) {
			billingScheduleDetailsVO.setBillingPeriodFromDate(
					convertToLocaldate(billingScheduleMasterModel.getBillingPeriodFromDate()));
		}
		if (billingScheduleMasterModel.getBillingPeriodToDate() != null) {
			billingScheduleDetailsVO
					.setBillingPeriodToDate(convertToLocaldate(billingScheduleMasterModel.getBillingPeriodToDate()));
		}
		billingScheduleDetailsVO
				.setPassFileGenerateDate(convertToLocaldate(billingScheduleMasterModel.getPassFileGenerateDate()));
		billingScheduleDetailsVO.setBillingType("G");
		billingScheduleDetailsVO
				.setGpaInvoiceGenarateDate(convertToLocaldate(billingScheduleMasterModel.getGpaInvoiceGenarateDate()));
		billingScheduleDetailsVO
				.setInvoiceAvailableDate(convertToLocaldate(billingScheduleMasterModel.getInvoiceAvailableDate()));
		billingScheduleDetailsVO
				.setLastUpdatedTime(convertToLocaldate(billingScheduleMasterModel.getLastUpdatedTime()));
		billingScheduleDetailsVO.setLastUpdatedUser(billingScheduleMasterModel.getLastUpdatedUser());
		billingScheduleDetailsVO
				.setMasterCutDataDate(convertToLocaldate(billingScheduleMasterModel.getMasterCutDataDate()));
		billingScheduleDetailsVO.setOpearationFlag(billingScheduleMasterModel.get__opFlag());
		billingScheduleDetailsVO.setPeriodNumber(billingScheduleMasterModel.getPeriodNumber());
		billingScheduleDetailsVO.setPostalOperatorUploadDate(
				convertToLocaldate(billingScheduleMasterModel.getPostalOperatorUploadDate()));
		billingScheduleDetailsVO.setFunctionName(billingScheduleMasterModel.getFunctionName());
		billingScheduleDetailsVO.setFunctionPoint(billingScheduleMasterModel.getFunctionPoint());
		billingScheduleDetailsVO.setExcludeFlag(billingScheduleMasterModel.getExcludeFlag());
		billingScheduleDetailsVO.setParamterCode(billingScheduleMasterModel.getParamterCode());
		billingScheduleDetailsVO.setParameterDescription(billingScheduleMasterModel.getParameterDescription());
		billingScheduleDetailsVO.setParameterValue(billingScheduleMasterModel.getParameterValue());
		if (billingScheduleMasterModel.getParametersList() != null)
			for (BillingParameterDetails billingParameterDetails : billingScheduleMasterModel.getParametersList()) {
				BillingParameterVO billingParameterVO = new BillingParameterVO();
				billingParameterVO.setParameterDescription(billingParameterDetails.getParameterName());
				billingParameterVO.setParameterValue(billingParameterDetails.getParameterValue());
				billingParameterVO.setParamterCode(billingParameterDetails.getParameterCode());
				billingParameterVO.setExcludeFlag(billingParameterDetails.getIncludeExcludeFlag());
				billingParameterVOs.add(billingParameterVO);
			}
		billingScheduleDetailsVO.setParamsList(billingParameterVOs);
		billingScheduleDetailsVOs.add(billingScheduleDetailsVO);
		return billingScheduleDetailsVO;
	}
	public static LocalDate convertToLocaldate(String date) {
		LogonAttributes logonAttributes = null;
		try {
			logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LocalDate scanDate = null;
		if (date != null && date.trim().length() > 0) {
			scanDate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
			if (date.trim().length() == 20) { 
				scanDate.setDateAndTime(date);
			} else if(date.trim().length() == 10)  {
				scanDate.setDate(date.substring(0, 10));  
			}else{
				scanDate.setDate(date.substring(0, 11));  
			}
			return scanDate;
		}
		return scanDate;
	}
	/**
	 * 
	 * 	Method		:	MailMRAModelConverter.constructPASSFilterVO
	 *	Added by 	:	A-4809 on 12-Mar-2021
	 * 	Used for 	:
	 *	Parameters	:	@param generatepassbillingmodel
	 *	Parameters	:	@return 
	 *	Return type	: 	GeneratePASSFilterVO
	 */
	public static GeneratePASSFilterVO constructPASSFilterVO(GPAPassFilter passFilter){
		GeneratePASSFilterVO passFilterVO =new GeneratePASSFilterVO();
		passFilterVO.setPeriodNumber(passFilter.getPeriodNumber());
		passFilterVO.setBillingPeriodFrom(convertToDate(passFilter.getFromBillingDate()));
		passFilterVO.setBillingPeriodTo(convertToDate(passFilter.getToBillingDate()));
		passFilterVO.setCountry(passFilter.getCountry());
		passFilterVO.setGpaCode(passFilter.getGpaCode());
		passFilterVO.setFileName(passFilter.getFileName());
		passFilterVO.setAddNew(passFilter.isIncludeNewInvoice());
		return passFilterVO;
	}
	/**
	 * 	Method		:	MailMRAModelConverter.convertToDate
	 *	Added by 	:	A-4809 on 12-Mar-2021
	 * 	Used for 	:
	 *	Parameters	:	@param date
	 *	Parameters	:	@return 
	 *	Return type	: 	LocalDate
	 */
	private static LocalDate convertToDate(String date){
		if(date!=null && !("").equals(date)){
			return(new LocalDate(LocalDate.NO_STATION,Location.NONE,true).setDate( date ));
		}
		return null;
	}/**
 * 	Method		:	MailMRAModelConverter.constructPaymentBatchDetails
 *	Added by 	:	A-4809 on 11-Nov-2021
 * 	Used for 	:
 *	Parameters	:	@param paymentBatchDetailsVOs
 *	Parameters	:	@param oneTimeValues
 *	Parameters	:	@return 
 *	Return type	: 	ArrayList<PaymentBatchDetails>
 */
	public static ArrayList<PaymentBatchDetails> constructPaymentBatchDetails(
			Page<PaymentBatchDetailsVO> paymentBatchDetailsVOs,Map<String, Collection<OneTimeVO>> oneTimeValues){
		ArrayList<PaymentBatchDetails> paymentBatchDetails= new ArrayList<>();
		if(!Objects.isNull(paymentBatchDetailsVOs)){
			for(PaymentBatchDetailsVO paymentBatchDetailsVO : paymentBatchDetailsVOs){
				paymentBatchDetails.add(constructPaymentBatchDetails(paymentBatchDetailsVO,oneTimeValues));
			}
		}
		return paymentBatchDetails;
	}
	/**
	 * 	Method		:	MailMRAModelConverter.constructPaymentBatchDetails
	 *	Added by 	:	A-4809 on 11-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@param dtlvo
	 *	Parameters	:	@param oneTimeValues
	 *	Parameters	:	@return 
	 *	Return type	: 	PaymentBatchDetails
	 */
	private static PaymentBatchDetails constructPaymentBatchDetails(PaymentBatchDetailsVO dtlvo,
			Map<String, Collection<OneTimeVO>> oneTimeValues){
		PaymentBatchDetails  detail = new PaymentBatchDetails();
		detail.setCompanyCode(dtlvo.getCompanyCode());
		detail.setBatchID(dtlvo.getBatchID());
		detail.setPaCode(dtlvo.getPaCode());
		detail.setBatchStatus(replaceOneTimeValues(oneTimeValues,MRAConstantsVO.KEY_BATCH_STATUS_ONETIME,dtlvo.getBatchStatus()));
		//detail.setBatchSequenceNum(dtlvo.getBatchSequenceNum()); 
		detail.setCurrency(dtlvo.getBatchCurrency());
		detail.setBatchAmt(dtlvo.getBatchAmount().getAmount());
		detail.setAppliedAmt(dtlvo.getAppliedAmount().getAmount());
		detail.setUnappliedAmt(dtlvo.getUnappliedAmount().getAmount());
		detail.setDate(dtlvo.getBatchDate().toDisplayDateOnlyFormat());  
		detail.setRemark(dtlvo.getRemarks());
		detail.setProcessID(dtlvo.getProcessID());
		detail.setFileName(dtlvo.getFileName());
		return detail;
	}
	
	/**
	 * Method : MailMRAModelConverter.constructMailSettlementBatches Added by :
	 * A-3429 on 18-Nov-2021 Used for : Parameters : @param
	 * paymentBatchDetailsVOs Parameters : @return Return type :
	 * Collection<MailSettlementBatch>
	 */
	public static Collection<MailSettlementBatch> constructMailSettlementBatches(
			Collection<PaymentBatchDetailsVO> paymentBatchDetailsVOs) {
		Collection<MailSettlementBatch> mailSettlementBatchlist = new ArrayList<>();
		if (!Objects.isNull(paymentBatchDetailsVOs)) {
			for (PaymentBatchDetailsVO paymentBatchDetailsVO : paymentBatchDetailsVOs) {
				mailSettlementBatchlist.add(constructMailSettlementBatch(paymentBatchDetailsVO));
			}
		}
		return mailSettlementBatchlist;
	}

	/**
	 * Method : MailMRAModelConverter.constructMailSettlementBatch Added by :
	 * A-3429 on 18-Nov-2021 Used for : Parameters : @param dtlvo Parameters
	 * : @return Return type : PaymentBatchDetails
	 */
	private static MailSettlementBatch constructMailSettlementBatch(PaymentBatchDetailsVO dtlvo) {
		MailSettlementBatch batchModel = new MailSettlementBatch();
		batchModel.setCompanyCode(dtlvo.getCompanyCode());
		batchModel.setBatchId(dtlvo.getBatchID());
		batchModel.setGpaCode(dtlvo.getPaCode());
		batchModel.setBatchStatus(dtlvo.getBatchStatus());
		batchModel.setBatchSequenceNum(dtlvo.getBatchSequenceNum());
		batchModel.setCurrencyCode(dtlvo.getBatchCurrency());
		batchModel.setBatchamount(dtlvo.getBatchAmount().getAmount());
		batchModel.setAppliedAmount(dtlvo.getAppliedAmount().getAmount());
		batchModel.setUnAppliedAmount(dtlvo.getUnappliedAmount().getAmount());
		batchModel.setBatchDate(dtlvo.getBatchDate().toDisplayDateOnlyFormat());
		batchModel.setRecordCount(dtlvo.getRecordCount());
		batchModel.setSource("Mail");
		return batchModel;
	}

	/**
	 * Method : MailMRAModelConverter.constructMailSettlementBatchDetails Added
	 * by : A-3429 on 18-Nov-2021 Used for : Parameters : @param
	 * paymentBatchDetailsVOs Parameters : @param oneTimeValues Parameters
	 * : @return Return type : List<MailSettlementBatchDetails>
	 */
	public static List<MailSettlementBatchDetails> constructMailSettlementBatchDetails(
			Page<PaymentBatchSettlementDetailsVO> paymentBatchSettlementDetailsVOs) {
		ArrayList<MailSettlementBatchDetails> mailSettlementBatchDetails = new ArrayList<>();
		if (!Objects.isNull(paymentBatchSettlementDetailsVOs)) {
			for (PaymentBatchSettlementDetailsVO paymentBatchSettlementDetailsVO : paymentBatchSettlementDetailsVOs) {
				mailSettlementBatchDetails.add(constructMailSettlementBatchDetail(paymentBatchSettlementDetailsVO));
			}
		}
		return mailSettlementBatchDetails;
	}

	/**
	 * Method : MailMRAModelConverter.constructMailSettlementBatchDetail Added
	 * by : A-3429 on 18-Nov-2021 Used for : Parameters : @param dtlvo
	 * Parameters : @return Return type : MailSettlementBatchDetails
	 */
	private static MailSettlementBatchDetails constructMailSettlementBatchDetail(
			PaymentBatchSettlementDetailsVO dtlvo) {
		MailSettlementBatchDetails mailSettlementBatchDetail = new MailSettlementBatchDetails();
		mailSettlementBatchDetail.setCompanyCode(dtlvo.getCompanyCode());
		mailSettlementBatchDetail.setBatchId(dtlvo.getBatchID());
		mailSettlementBatchDetail.setGpaCode(dtlvo.getPaCode());
		mailSettlementBatchDetail.setBatchSequenceNum(dtlvo.getBatchSequenceNum());
		mailSettlementBatchDetail.setMailIdr(dtlvo.getMailbagId());
		mailSettlementBatchDetail.setMalseqnum(dtlvo.getMailSeqNum());
		mailSettlementBatchDetail.setAccountNumber(dtlvo.getAccountNumber());
		mailSettlementBatchDetail.setInvoiceNumber(dtlvo.getInvoiceNumber());
		mailSettlementBatchDetail.setSettlementCurrencyCode(dtlvo.getCurrencyCode());
		mailSettlementBatchDetail.setBatchamount(dtlvo.getBatchAmount().getAmount());
		mailSettlementBatchDetail.setAppliedAmount(dtlvo.getAppliedAmount().getAmount());
		mailSettlementBatchDetail.setUnappliedAmount(dtlvo.getUnappliedAmount().getAmount());
		mailSettlementBatchDetail.setReasonCode(dtlvo.getReasonCode());
		mailSettlementBatchDetail.setRemarks(dtlvo.getRemarks());
		return mailSettlementBatchDetail;
	}
	}
