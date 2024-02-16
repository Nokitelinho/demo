package com.ibsplc.icargo.services.mail.mra.webservices.standard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailInvoicMessageDetailVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.MailInvoicMessageVO;
import com.ibsplc.icargo.business.mail.mra.types.standard.MailInvoicDetailsResponseType;
import com.ibsplc.icargo.business.mail.mra.types.standard.MailInvoicMessageRequestType;
import com.ibsplc.icargo.business.mail.mra.types.standard.MailInvoicMessageType;
import com.ibsplc.icargo.business.mail.mra.types.standard.MailInvoiceMessageDetailType;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.WebServiceEndPoint;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.exception.WSBusinessException;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;

/**
 * 
 * @author A-7371
 *
 */
@javax.jws.WebService(serviceName = "MailMRAService", portName = "MailMRAService", targetNamespace = "http://www.ibsplc.com/icargo/services/MailMRAService/standard", wsdlLocation = "file:./wsdl/mail/mra/standard/MailMRAService.wsdl", endpointInterface = "com.ibsplc.icargo.services.mail.mra.webservices.standard.MailMRAService")
@Module("mail")
@SubModule("mra")
public class MailMRAServiceImpl extends WebServiceEndPoint implements MailMRAService {

	private static final String CURR_USD="USD"; 

	public MailInvoicDetailsResponseType saveMailInvoicDetails(MailInvoicMessageRequestType parameters)
			throws ServiceFault, InvalidRequestFault {
		
		LogonAttributes logonAttributes = null;  
 
		try {
			logonAttributes = (LogonAttributes) getLogonAttributesVO();
		} catch (SystemException e) {

			log.log(Log.FINE, "SystemException Caught");

		}

		Collection<MailInvoicMessageVO> mailInvoicMessage = null;
		mailInvoicMessage = populateMailInvoicMessage(parameters,logonAttributes);
		MailInvoicDetailsResponseType mailInvoicDetailsResponseType = new MailInvoicDetailsResponseType();

		try {
			despatchRequest("saveMailInvoicDetails", mailInvoicMessage);

			mailInvoicDetailsResponseType.setError("ok");
			mailInvoicDetailsResponseType.setErrorDescription("No Error");
			mailInvoicDetailsResponseType.setStatus("Save Successful");
		} catch (WSBusinessException e) {
			log.log(Log.FINE, "WSBusinessException Caught");
			mailInvoicDetailsResponseType = populateMailInvoicDetailsResponse(e, mailInvoicDetailsResponseType);

		} catch (SystemException e) {
			log.log(Log.FINE, "SystemException");
			mailInvoicDetailsResponseType.setError("SystemException");
			mailInvoicDetailsResponseType.setErrorDescription(" Error");
			mailInvoicDetailsResponseType.setStatus("Save Failed");
 
		}
		
		return mailInvoicDetailsResponseType;

	}


	private MailInvoicDetailsResponseType populateMailInvoicDetailsResponse(WSBusinessException e,
			MailInvoicDetailsResponseType mailInvoicDetailsResponseType) {

		
		ArrayList<ErrorVO> errorVos = (ArrayList<ErrorVO>) e.getErrors();
		for (ErrorVO errorVo : errorVos) {
			if (errorVo != null) { 
				
				mailInvoicDetailsResponseType.setError(errorVo.getErrorCode());
				mailInvoicDetailsResponseType.setErrorDescription(errorVo.getErrorDescription());
				mailInvoicDetailsResponseType.setStatus("Save Failed");
				
			}
		}


		return mailInvoicDetailsResponseType; 
	}

	private Collection<MailInvoicMessageVO> populateMailInvoicMessage(MailInvoicMessageRequestType parameters,LogonAttributes logonAttributes) {

		Collection<MailInvoicMessageVO> mailInvoicDetails = new ArrayList<MailInvoicMessageVO>();

		for (MailInvoicMessageType param : parameters.getMailInvoicMessageVOs()) {
			MailInvoicMessageVO mailInvoicMessageVO = new MailInvoicMessageVO(); 
			String [] date=null;
			mailInvoicMessageVO.setCompanyCode(param.getCompanyCode());
			mailInvoicMessageVO.setPoaCode(param.getPoaCode());
			if(param.getReportingPeriodFrom()!=null  && param.getReportingPeriodFrom().trim().length()>0 ){
			LocalDate reportPeriodFrom = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			date=param.getReportingPeriodFrom().split("-");
			reportPeriodFrom.setDateAndTime(date[0],formatSetter(date[0],date[1]));
			mailInvoicMessageVO.setReportingPeriodFrom(reportPeriodFrom);
			}
			
			if(param.getReportingPeriodTo()!=null && param.getReportingPeriodTo().trim().length()>0){
				LocalDate reportPeriodTo = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=param.getReportingPeriodTo().split("-");
				reportPeriodTo.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageVO.setReportingPeriodTo(reportPeriodTo);
				} 
			mailInvoicMessageVO.setInvoiceReferenceNumber(param.getInvoiceReferenceNumber());
			if(param.getNumberOfMailbags()!=null){
			mailInvoicMessageVO.setNumberOfMailbags(param.getNumberOfMailbags());
			}
			if(param.getMessageDate()!=null && param.getMessageDate().trim().length()>0){
				LocalDate messageDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=param.getMessageDate().split("-");
				messageDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageVO.setMessageDate(messageDate);
				} 
			if(param.getInvoiceDate()!=null && param.getInvoiceDate().trim().length()>0){
				LocalDate invoiceDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=param.getInvoiceDate().split("-");
				invoiceDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageVO.setInvoiceDate(invoiceDate);
				}
			mailInvoicMessageVO.setPayeeCode(param.getPayeeCode());
			mailInvoicMessageVO.setPayerCode(param.getPayerCode());
			mailInvoicMessageVO.setAssignedCarrier(param.getAssignedCarrier());
			mailInvoicMessageVO.setInvoiceAdvieType(param.getInvoiceAdvieType());
			if(param.getTotalInvoiceAmount()!=null && param.getTotalInvoiceAmount().trim().length()>0){
			 mailInvoicMessageVO.setTotalInvoiceAmount(constructAmountInMoney(CURR_USD,new BigDecimal(param.getTotalInvoiceAmount()))); 
			}
			if(param.getTotalAdjustmentAmount()!=null && param.getTotalAdjustmentAmount().trim().length()>0){
				 mailInvoicMessageVO.setTotalAdjustmentAmount(constructAmountInMoney(CURR_USD,new BigDecimal(param.getTotalAdjustmentAmount()))); 
				}
			mailInvoicMessageVO.setInvoiceStatus(param.getInvoiceStatus());
			mailInvoicMessageVO.setLastUpdatedUser(logonAttributes.getUserName());
			if(param.getLastUpdatedTime()!=null && param.getLastUpdatedTime().trim().length()>0){
				LocalDate lastUpdatedTime = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=param.getLastUpdatedTime().split("-");
				lastUpdatedTime.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageVO.setLastUpdatedTime(lastUpdatedTime);
				}else{
					mailInvoicMessageVO.setLastUpdatedTime(logonAttributes.getLoginTime());
				}
			mailInvoicMessageVO.setRemark(param.getRemark());
			mailInvoicMessageVO.setFileName(param.getFileName());
			if(param.getSplitCount()!=null && param.getSplitCount().trim().length()>0){
			mailInvoicMessageVO.setSplitCount(Integer.parseInt(param.getSplitCount()));
			}
			if(param.getTotalSplitCount()!=null && param.getTotalSplitCount().trim().length()>0){
			mailInvoicMessageVO.setTotalsplitCount(Integer.parseInt(param.getTotalSplitCount()));
			}
			mailInvoicMessageVO.setAutoProcessingFlag(param.getAutoProcessingFlag());
			mailInvoicMessageVO.setMessage(param.getMessage());
			mailInvoicMessageVO.setAssocAssignCode(param.getAssocAssignCode());
			mailInvoicMessageVO.setContractNumber(param.getContractNumber()); 
			populateMailInvoicMessageDetails(mailInvoicMessageVO, param); 
			mailInvoicDetails.add(mailInvoicMessageVO);

		}

		return mailInvoicDetails;
	}

	private void populateMailInvoicMessageDetails(MailInvoicMessageVO mailInvoicMessageVO,
			MailInvoicMessageType param) {

		Collection<MailInvoicMessageDetailVO> mailInvoicMessageDetailVOs = new ArrayList<MailInvoicMessageDetailVO>();
		for (MailInvoiceMessageDetailType paramChild : param.getMailInvoiceMessageDetailVOs()) {
			String[] date= null;
			MailInvoicMessageDetailVO mailInvoicMessageDetailVO = new MailInvoicMessageDetailVO();
			mailInvoicMessageDetailVO.setCompanyCode(paramChild.getCompanyCode());
			mailInvoicMessageDetailVO.setInvoiceNumber(paramChild.getInvoiceNumber());
			mailInvoicMessageDetailVO.setConsignmentNumber(paramChild.getConsignmentNumber());
			mailInvoicMessageDetailVO.setPoaCode(paramChild.getPoaCode());
			mailInvoicMessageDetailVO.setMailCategoryCode(paramChild.getMailCategoryCode());
			mailInvoicMessageDetailVO.setMailSubClassCode(paramChild.getMailSubClassCode());
			mailInvoicMessageDetailVO.setMailProductCode(paramChild.getMailProductCode());
			mailInvoicMessageDetailVO.setMailBagId(paramChild.getMailBagId());
			mailInvoicMessageDetailVO.setPaymentLevel(paramChild.getPaymentLevel());
			mailInvoicMessageDetailVO.setLateIndicator(paramChild.getLateIndicator());
			mailInvoicMessageDetailVO.setRateTypeIndicator(paramChild.getRateTypeIndicator());
			mailInvoicMessageDetailVO.setPayCycleIndicator(paramChild.getPayCycleIndicator());
			mailInvoicMessageDetailVO.setAdjustmentReasonCode(paramChild.getAdjustmentReasonCode());
			mailInvoicMessageDetailVO.setCarrierToPay(paramChild.getCarrierToPay());
			mailInvoicMessageDetailVO.setContainerType(paramChild.getContainerType());
			mailInvoicMessageDetailVO.setClaimNotes(paramChild.getClaimNotes());
			mailInvoicMessageDetailVO.setClaimReasonCode(paramChild.getClaimReasonCode());
			if(paramChild.getConsignmentCompletionDate()!=null && paramChild.getConsignmentCompletionDate().trim().length()>0){
				LocalDate consignmentCompletionDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=paramChild.getConsignmentCompletionDate().split("-");
				consignmentCompletionDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageDetailVO.setConsignmentCompletionDate(consignmentCompletionDate);
				}
			if(paramChild.getRequiredDeliveryDate()!=null && paramChild.getRequiredDeliveryDate().trim().length()>0){
				LocalDate requiredDeliveryDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=paramChild.getRequiredDeliveryDate().split("-");
				requiredDeliveryDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageDetailVO.setRequiredDeliveryDate(requiredDeliveryDate);
				}
			mailInvoicMessageDetailVO.setContainerNumber(paramChild.getContainerNumber());
			mailInvoicMessageDetailVO.setClaimAdjustmentCode(paramChild.getClaimAdjustmentCode());
			mailInvoicMessageDetailVO.setClaimText(paramChild.getClaimText());
			mailInvoicMessageDetailVO.setClaimStatus(paramChild.getClaimStatus());
			if(paramChild.getBaseTotalAmount()!=null && paramChild.getBaseTotalAmount().trim().length()>0){
			 mailInvoicMessageDetailVO.setBaseTotalAmount(constructAmountInMoney(CURR_USD,new BigDecimal(paramChild.getBaseTotalAmount())));
			}
			if(paramChild.getAdjustmentTotalAmount()!=null && paramChild.getAdjustmentTotalAmount().trim().length()>0){
			 mailInvoicMessageDetailVO.setAdjustmentTotalAmount(constructAmountInMoney(CURR_USD,new BigDecimal(paramChild.getAdjustmentTotalAmount())));
			}
			if(paramChild.getLineHaulCharge()!=null && paramChild.getLineHaulCharge().trim().length()>0){
			 mailInvoicMessageDetailVO.setLineHaulCharge(constructAmountInMoney(CURR_USD,new BigDecimal(paramChild.getLineHaulCharge())));
			}
			if(paramChild.getTerminalHandlingCharge()!=null && paramChild.getTerminalHandlingCharge().trim().length()>0){
			 mailInvoicMessageDetailVO.setTerminalHandlingCharge(constructAmountInMoney(CURR_USD,new BigDecimal(paramChild.getTerminalHandlingCharge())));
			}
			if(paramChild.getOtherValuationCharge()!=null && paramChild.getOtherValuationCharge().trim().length()>0){
			 mailInvoicMessageDetailVO.setOtherValuationCharge(constructAmountInMoney(CURR_USD,new BigDecimal(paramChild.getOtherValuationCharge())));
			}
			if(paramChild.getContainerChargeAmount()!=null && paramChild.getContainerChargeAmount().trim().length()>0){
			 mailInvoicMessageDetailVO.setContainerChargeAmount(constructAmountInMoney(CURR_USD,new BigDecimal(paramChild.getContainerChargeAmount())));
			}
			if(paramChild.getCharterChargeAmount()!=null && paramChild.getCharterChargeAmount().trim().length()>0){
			 mailInvoicMessageDetailVO.setCharterChargeAmount(constructAmountInMoney(CURR_USD,new BigDecimal(paramChild.getCharterChargeAmount())));
			}
			if(paramChild.getPossessionScanDate()!=null && paramChild.getPossessionScanDate().trim().length()>0){
				LocalDate possessionScanDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=paramChild.getPossessionScanDate().split("-");
				possessionScanDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageDetailVO.setPossessionScanDate(possessionScanDate);
				}
			if(paramChild.getLineHaulDollarRate()!=null){
			mailInvoicMessageDetailVO.setLineHaulDollarRate(paramChild.getLineHaulDollarRate());
			}
			if(paramChild.getTerminalHandlingDollarRate()!=null){
			mailInvoicMessageDetailVO.setTerminalHandlingDollarRate(paramChild.getTerminalHandlingDollarRate());
			}
			if(paramChild.getSpecialPerKiloDollarRate()!=null){
			mailInvoicMessageDetailVO.setSpecialPerKiloDollarRate(paramChild.getSpecialPerKiloDollarRate());
			}
			if(paramChild.getSpecialPerKiloSDRRate()!=null){
			mailInvoicMessageDetailVO.setSpecialPerKiloSDRRate(paramChild.getSpecialPerKiloSDRRate());
			}
			if(paramChild.getContainerRate()!=null){
			mailInvoicMessageDetailVO.setContainerRate(paramChild.getContainerRate());
			}
			if(paramChild.getSdrDate()!=null && paramChild.getSdrDate().trim().length()>0){
				LocalDate sdrDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=paramChild.getSdrDate().split("-");
				sdrDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageDetailVO.setSdrDate(sdrDate);
				}
			if(paramChild.getSdrRate()!=null){
			mailInvoicMessageDetailVO.setSdrRate(paramChild.getSdrRate());
			}
			if(paramChild.getTransportationWindowEndTime()!=null && paramChild.getTransportationWindowEndTime().trim().length()>0){
				LocalDate transportationWindowEndTime = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=paramChild.getTransportationWindowEndTime().split("-");
				transportationWindowEndTime.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageDetailVO.setTransportationWindowEndTime(transportationWindowEndTime);
				}
			mailInvoicMessageDetailVO.setWeightUnit(paramChild.getWeightUnit());
			if(paramChild.getGrossWeight()!=null){
			mailInvoicMessageDetailVO.setGrossWeight(paramChild.getGrossWeight());
			}
			if(paramChild.getContainerGrossWeight()!=null){
			mailInvoicMessageDetailVO.setContainerGrossWeight(paramChild.getContainerGrossWeight());
			}
			mailInvoicMessageDetailVO.setOrginAirport(paramChild.getOrginAirport());
			mailInvoicMessageDetailVO.setDestinationAirport(paramChild.getDestinationAirport());
			mailInvoicMessageDetailVO.setOfflineOriginAirport(paramChild.getOfflineOriginAirport());
			
			mailInvoicMessageDetailVO.setOriginalOriginAirport(paramChild.getOriginalOriginAirport());
			mailInvoicMessageDetailVO.setFinalDestination(paramChild.getFinalDestination());
			mailInvoicMessageDetailVO.setDeliveryScanCarrierCode(paramChild.getDeliveryScanCarrierCode());
			mailInvoicMessageDetailVO.setDeleiveryScanLocation(paramChild.getDeleiveryScanLocation());
			if(paramChild.getDeliveryDate()!=null && paramChild.getDeliveryDate().trim().length()>0){
				LocalDate deliveryDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=paramChild.getDeliveryDate().split("-");
				deliveryDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageDetailVO.setDeliveryDate(deliveryDate);
				}
			mailInvoicMessageDetailVO.setZeroPayReceptacleCode(paramChild.getZeroPayReceptacleCode());
			if(paramChild.getTerminalHandlingScanRate()!=null){
			mailInvoicMessageDetailVO.setTerminalHandlingScanRate(paramChild.getTerminalHandlingScanRate());
			}
			if(paramChild.getSortRate()!=null){
			mailInvoicMessageDetailVO.setSortRate(paramChild.getSortRate());
			}
			if(paramChild.getLiveRate()!=null){
			mailInvoicMessageDetailVO.setLiveRate(paramChild.getLiveRate());
			}
			if(paramChild.getHubrelabelingRate()!=null){
			mailInvoicMessageDetailVO.setHubrelabelingRate(paramChild.getHubrelabelingRate());
			}
			if(paramChild.getAdditionalSeparationRate()!=null){
			mailInvoicMessageDetailVO.setAdditionalSeparationRate(paramChild.getAdditionalSeparationRate());
			}
			
   
			if(paramChild.getRouteDepartureDate()!=null && paramChild.getRouteDepartureDate().trim().length()>0){
				LocalDate routeDepartureDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=paramChild.getRouteDepartureDate().split("-");
				routeDepartureDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageDetailVO.setRouteDepatureDate(routeDepartureDate);
				}
			
			if(paramChild.getAssignedDate()!=null && paramChild.getAssignedDate().trim().length()>0){
				LocalDate assignedDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=paramChild.getAssignedDate().split("-");
				assignedDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageDetailVO.setAssignedDate(assignedDate);
				}
			
			mailInvoicMessageDetailVO.setControlNumber(paramChild.getControlNumber());
			mailInvoicMessageDetailVO.setRegionCode(paramChild.getRegionCode());
			mailInvoicMessageDetailVO.setReferenceVersionNumber(paramChild.getReferenceVersionNumber());
			mailInvoicMessageDetailVO.setTruckingLocation(paramChild.getTruckingLocation());
			mailInvoicMessageDetailVO.setCarrierFinalDestination(paramChild.getCarrierFinalDestination());
			if(paramChild.getRouteArrivalDate()!=null && paramChild.getRouteArrivalDate().trim().length()>0){
				LocalDate routeArrivalDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=paramChild.getRouteArrivalDate().split("-");
				routeArrivalDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageDetailVO.setRouteArrivalDate(routeArrivalDate);	
				}
			mailInvoicMessageDetailVO.setOriginTripCarrierCode(paramChild.getOriginTripCarrierCode());
			mailInvoicMessageDetailVO.setOriginTripFlightNumber(paramChild.getOriginTripFlightNumber());
			mailInvoicMessageDetailVO.setOriginTripStagQualifier(paramChild.getOriginTripStagQualifier());
			mailInvoicMessageDetailVO.setPossessionScanStagQualifier(paramChild.getPossessionScanStagQualifier());
			mailInvoicMessageDetailVO.setPossessionScanCarrierCode(paramChild.getPossessionScanCarrierCode());
			mailInvoicMessageDetailVO.setPossessionScanExpectedSite(paramChild.getPossessionScanExpectedSite());	
			mailInvoicMessageDetailVO.setLoadScanCarrierCode(paramChild.getLoadScanCarrierCode());
			mailInvoicMessageDetailVO.setLoadScanStagQualifier(paramChild.getLoadScanStagQualifier());
			mailInvoicMessageDetailVO.setLoadScanFlightNumber(paramChild.getLoadScanFlightNumber());
			mailInvoicMessageDetailVO.setLoadScanExpectedSite(paramChild.getLoadScanExpectedSite());
			if(paramChild.getLoadScanDate()!=null && paramChild.getLoadScanDate().trim().length()>0){
				LocalDate loadScanDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=paramChild.getLoadScanDate().split("-");
				loadScanDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageDetailVO.setLoadScanDate(loadScanDate);
				}
			mailInvoicMessageDetailVO.setFirstTransferScanCarrier(paramChild.getFirstTransferScanCarrier());
			mailInvoicMessageDetailVO.setFirstTransferFlightNumber(paramChild.getFirstTransferFlightNumber());
			mailInvoicMessageDetailVO.setFirstTransferExpectedSite(paramChild.getFirstTransferExpectedSite());
			mailInvoicMessageDetailVO.setFirstTransferStagQualifier(paramChild.getFirstTransferStagQualifier());
			if(paramChild.getFirstTransferDate()!=null && paramChild.getFirstTransferDate().trim().length()>0){
				LocalDate firstTransferDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=paramChild.getFirstTransferDate().split("-");
				firstTransferDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageDetailVO.setFirstTransferDate(firstTransferDate);
				}
			mailInvoicMessageDetailVO.setSecondTransferStagQualifier(paramChild.getSecondTransferStagQualifier());
			mailInvoicMessageDetailVO.setSecondTransferExpectedSite(paramChild.getSecondTransferExpectedSite());
			mailInvoicMessageDetailVO.setSecondTransferFlightNumber(paramChild.getSecondTransferFlightNumber());
			mailInvoicMessageDetailVO.setSecondTransferScanCarrier(paramChild.getSecondTransferScanCarrier());
			if(paramChild.getSecondTransferDate()!=null && paramChild.getSecondTransferDate().trim().length()>0){
				LocalDate secondTransferDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=paramChild.getSecondTransferDate().split("-");
				secondTransferDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageDetailVO.setSecondTransferDate(secondTransferDate);
				}
			mailInvoicMessageDetailVO.setThirdTransferScanCarrier(paramChild.getThirdTransferScanCarrier());
			mailInvoicMessageDetailVO.setThirdTransferStagQualifier(paramChild.getThirdTransferStagQualifier());
			mailInvoicMessageDetailVO.setThirdTransferExpectedSite(paramChild.getThirdTransferExpectedSite());
			if(paramChild.getThirdTransferDate()!=null && paramChild.getThirdTransferDate().trim().length()>0){
				LocalDate thirdTransferDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=paramChild.getThirdTransferDate().split("-");
				thirdTransferDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageDetailVO.setThirdTransferDate(thirdTransferDate);
				}
			
			mailInvoicMessageDetailVO.setThirdTransferFlightNumber(paramChild.getThirdTransferFlightNumber());
			mailInvoicMessageDetailVO.setFourthTransferScanCarrier(paramChild.getFourthTransferScanCarrier());
			mailInvoicMessageDetailVO.setFourthTransferFlightNumber(paramChild.getFourthTransferFlightNumber());
			mailInvoicMessageDetailVO.setFourthTransferExpectedSite(paramChild.getFourthTransferExpectedSite());
			if(paramChild.getFourthTransferDate()!=null && paramChild.getFourthTransferDate().trim().length()>0){
				LocalDate fourthTransferDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=paramChild.getFourthTransferDate().split("-");
				fourthTransferDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageDetailVO.setFourthTransferDate(fourthTransferDate);
				}
			mailInvoicMessageDetailVO.setFourthTransferFlightNumber(paramChild.getFourthTransferFlightNumber());
			mailInvoicMessageDetailVO.setDeliverTransferStagQualifier(paramChild.getDeliverTransferStagQualifier());
			mailInvoicMessageDetailVO.setDeliverScanActualSite(paramChild.getDeliverScanActualSite());
			if(paramChild.getTerminalHandlingScanningCharge()!=null && paramChild.getTerminalHandlingScanningCharge().trim().length()>0){
			mailInvoicMessageDetailVO.setTerminalHandlingScanningCharge((constructAmountInMoney(CURR_USD,new BigDecimal(paramChild.getTerminalHandlingScanningCharge()))));
			}
			if(paramChild.getSortCharge()!=null && paramChild.getSortCharge().trim().length()>0){
				mailInvoicMessageDetailVO.setSortCharge((constructAmountInMoney(CURR_USD,new BigDecimal(paramChild.getSortCharge()))));
			}
			if(paramChild.getHubReLabelingCharge()!=null && paramChild.getHubReLabelingCharge().trim().length()>0 ){
				mailInvoicMessageDetailVO.setHubReLabelingCharge((constructAmountInMoney(CURR_USD,new BigDecimal(paramChild.getHubReLabelingCharge()))));
			}
			if(paramChild.getLiveCharge()!=null && paramChild.getLiveCharge().trim().length()>0){
				mailInvoicMessageDetailVO.setHubReLabelingCharge((constructAmountInMoney(CURR_USD,new BigDecimal(paramChild.getLiveCharge()))));
			}
			if(paramChild.getAdditionalSeparationCharge()!=null && paramChild.getAdditionalSeparationCharge().trim().length()>0){
				mailInvoicMessageDetailVO.setHubReLabelingCharge((constructAmountInMoney(CURR_USD,new BigDecimal(paramChild.getAdditionalSeparationCharge()))));
			}
			if(paramChild.getAdjustedTerminalHandlingCharge()!=null && paramChild.getAdjustedTerminalHandlingCharge().trim().length()>0){
				mailInvoicMessageDetailVO.setHubReLabelingCharge((constructAmountInMoney(CURR_USD,new BigDecimal(paramChild.getAdjustedTerminalHandlingCharge()))));
			}
			mailInvoicMessageDetailVO.setSortScanCarrier(paramChild.getSortScanCarrier());
			mailInvoicMessageDetailVO.setSortScanActualSite(paramChild.getSortScanActualSite());
			mailInvoicMessageDetailVO.setSortScanExpectedSite(paramChild.getSortScanExpectedSite());
			mailInvoicMessageDetailVO.setContractType(paramChild.getContractType());
			
			if(paramChild.getSortScanDate()!=null && paramChild.getSortScanDate().trim().length()>0){
				LocalDate sortScanDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=paramChild.getSortScanDate().split("-");
				sortScanDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageDetailVO.setSortScanDate(sortScanDate);				
				}
			if(paramChild.getSortScanReceiveDate()!=null && paramChild.getSortScanReceiveDate().trim().length()>0){
				LocalDate sortScanRecDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				date=paramChild.getSortScanReceiveDate().split("-");
				sortScanRecDate.setDateAndTime(date[0],formatSetter(date[0],date[1]));
				mailInvoicMessageDetailVO.setSortScanReceiveDate(sortScanRecDate);				
				}
			mailInvoicMessageDetailVOs.add(mailInvoicMessageDetailVO); 
		}
		mailInvoicMessageVO.setMailInvoiceMessageDetailVOs(mailInvoicMessageDetailVOs);

	}
	
	
	private Money constructAmountInMoney(String currency,BigDecimal amount){
			
			Money money = null;
			try{			
				money=CurrencyHelper.getMoney(currency); 
				money.setAmount(amount.doubleValue());
			}catch (CurrencyException currencyException) {
				new SystemException(currencyException.getMessage());
			}
			
			return money;
		}
	
	private String formatSetter(String date ,String dateFormatId){
		
		String pattern=null;
			if(dateFormatId.equals("203")){
				if(date.trim().length()==12){
				pattern="yyyyMMddHHmm";
				}else if(date.trim().length()==14){
					pattern="yyyyMMddHHmmss";
				}
			}
			if(dateFormatId.equals("102")){				
				pattern="yyyyMMdd";				 
			}
			if(dateFormatId.equals("204")){			
				pattern="yyyyMMddHHmmss";		 
			}
			return pattern;

		}
		

		
	
	
	
}