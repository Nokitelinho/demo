/*
 * SaveConsignmentCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.consignment;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitConversionNewVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.MaintainFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ConsignmentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class SaveConsignmentCommand extends BaseCommand {

	@Override
	public boolean breakOnInvocationFailure() {

		return true;
	}

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET = "success";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.consignment";
   private static final String SAVE_SUCCESS =
		"mailtracking.defaults.mailsubclassmaster.msg.info.savesuccess";
   private static final String HYPHEN ="-";
   /**
    *  Status of flag
    */
   private static final String OUTBOUND = "O";

   /**
    * Screen id
    */
   private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";

   /**
    * Screen id
    */
   private static final String SCREEN_ID_FLIGHT = "flight.operation.maintainflight";

   /**
    * Flight Module name
    */
   private static final String MODULE_NAME_FLIGHT =  "flight.operation";
   /**
    * Target string
    */
   private static final String DUPLICATE_SUCCESS = "duplicate_success";

	 /**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("SaveConsignmentCommand","**execute");

    	ConsignmentForm consignmentForm =
    		(ConsignmentForm)invocationContext.screenModel;
    	ConsignmentSession consignmentSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
    	DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		MaintainFlightSession maintainFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_FLIGHT);
    	errors = validateForm(consignmentForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
		}else{

    	ConsignmentDocumentVO consignmentDocumentVO = consignmentSession.getConsignmentDocumentVO();

    	log.log(Log.FINE, "consignmentDocumentVO ", consignmentDocumentVO);
		log.log(Log.FINE,
				"consignmentForm.getFromPopupflg while entering delegate==>>",
				consignmentForm.getFromPopupflg());
		Page<MailInConsignmentVO> mailVOs =  consignmentDocumentVO.getMailInConsignmentVOs();
    	Collection<MailInConsignmentVO> validateMailVOs = new ArrayList<MailInConsignmentVO>();
    	if(mailVOs != null && mailVOs.size() > 0) {

			  for(MailInConsignmentVO mailbagVO:mailVOs) {
				  mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
				  mailbagVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber().toUpperCase());
				  mailbagVO.setPaCode(consignmentDocumentVO.getPaCode().toUpperCase());
				if(mailbagVO.getReceptacleSerialNumber() != null &&
						!"".equals(mailbagVO.getReceptacleSerialNumber())){

				   /* String wt = String.valueOf(mailbagVO.getStrWeight());
					log.log(Log.FINE, "wt ...in command", wt);
					String stdwgt = wt;
					int d=(Integer.parseInt(wt));
					wt=String.valueOf(d);

					if(wt.length() == 3){
						wt = new StringBuilder("0").append(wt).toString();
					}
					if(wt.length() == 2){
						wt = new StringBuilder("00").append(wt).toString();
					}
					if(wt.length() == 1){
						wt = new StringBuilder("000").append(wt).toString();
					}
					log.log(Log.FINE, "stdwgt ...in command", stdwgt);


				// Making Mail Id
				String mailId = new StringBuilder()
			            .append(mailbagVO.getOriginExchangeOffice())
			            .append(mailbagVO.getDestinationExchangeOffice())
						.append(mailbagVO.getMailCategoryCode())
						.append(mailbagVO.getMailSubclass())
						.append(mailbagVO.getYear())
						.append(mailbagVO.getDsn())
						.append(mailbagVO.getReceptacleSerialNumber())
						.append(mailbagVO.getHighestNumberedReceptacle())
						.append(mailbagVO.getRegisteredOrInsuredIndicator())
						.append(wt).toString();
			     mailbagVO.setMailId(mailId);
                  //Added by a-8353 for icrd-ICRD-274933 starts
					*/  UnitConversionNewVO unitConversionVO= null;
					try {
						unitConversionVO=UnitFormatter.getUnitConversionForToUnit(UnitConstants.MAIL_WGT,mailbagVO.getStatedWeight().getDisplayUnit(),"H",mailbagVO.getStatedWeight().getDisplayValue());
					} catch (UnitException e) {
						// TODO Auto-generated catch block
						e.getMessage();
					}  
					   String wgt = String.valueOf(((int)(unitConversionVO.getToValue())));//Added by a-8353 for icrd-ICRD-274933 ends
						/*log.log(Log.FINE, "wt ...in command", wt);
						int len = wt.indexOf(".");
						String wgt = wt;
						if(len<4){
						if(len > 0){
							wgt = new StringBuilder(wt.substring(0,len)).append(wt.substring(len+1,len+2)).toString();
						}else{
							wgt = new StringBuilder(wt).append("0").toString();
							}
						}
						else if(len>=4){
							wgt=new StringBuilder(wt.substring(0,4)).toString();
						}*/
						String stdwgt = wgt;
						if(wgt.length() == 3){
							stdwgt = new StringBuilder("0").append(wgt).toString();
						}
						if(wgt.length() == 2){
							stdwgt = new StringBuilder("00").append(wgt).toString();
						}
						if(wgt.length() == 1){
							stdwgt = new StringBuilder("000").append(wgt).toString();
						}

						if(mailbagVO.getMailId().length()!=12){
						String mailId = new StringBuilder()
			            .append(mailbagVO.getOriginExchangeOffice())
			            .append(mailbagVO.getDestinationExchangeOffice())
						.append(mailbagVO.getMailCategoryCode())
						.append(mailbagVO.getMailSubclass())
						.append(mailbagVO.getYear())
						.append(mailbagVO.getDsn())
						.append(mailbagVO.getReceptacleSerialNumber())
						.append(mailbagVO.getHighestNumberedReceptacle())
						.append(mailbagVO.getRegisteredOrInsuredIndicator())
						.append(stdwgt).toString();
                      if(mailbagVO.getMailId()!=null){
						 mailbagVO.setMailId(mailbagVO.getMailId());
                      }
                      else{
                    	  mailbagVO.setMailId(mailId);
                      }
                      
			     log.log(Log.FINE, "mailId ...in command", mailId);

						}

			

				validateMailVOs.add(mailbagVO);
				}
				else
				{ //Clearing mailbagid if we are changing a duplicate mailbag after error shown to a despatch
					mailbagVO.setMailId("");
					mailbagVO.setReceptacleSerialNumber("");
					mailbagVO.setHighestNumberedReceptacle("");
					mailbagVO.setRegisteredOrInsuredIndicator("");
				}


			  }

			}

    	consignmentDocumentVO.setMailInConsignmentVOs(mailVOs);
    	consignmentSession.setConsignmentDocumentVO(consignmentDocumentVO);

    	validateConsignment(consignmentForm,consignmentSession,invocationContext,
    			consignmentDocumentVO,logonAttributes);
    	if (invocationContext.getErrors() != null
					&& invocationContext.getErrors().size() > 0) {
				int _total_Rec_Count_TO_Remove = consignmentSession
						.getTotalRecords();
				int _total_Rec_Count = consignmentDocumentVO
						.getMailInConsignmentVOs().getTotalRecordCount()
						- _total_Rec_Count_TO_Remove;
				consignmentDocumentVO.getMailInConsignmentVOs()
						.setTotalRecordCount(_total_Rec_Count);
				invocationContext.target = TARGET;
				return;
			}

    	validateRoutingDetails(consignmentForm, consignmentSession,
					invocationContext, consignmentDocumentVO, logonAttributes,
					duplicateFlightSession, maintainFlightSession);
			if (invocationContext.getErrors() != null
					&& invocationContext.getErrors().size() > 0) {
				int _total_Rec_Count_TO_Remove = consignmentSession
						.getTotalRecords();
				int _total_Rec_Count = consignmentDocumentVO
						.getMailInConsignmentVOs().getTotalRecordCount()
						- _total_Rec_Count_TO_Remove;
				consignmentDocumentVO.getMailInConsignmentVOs()
						.setTotalRecordCount(_total_Rec_Count);
				invocationContext.target = TARGET;
				return;
			}
    	if ("Y".equals(consignmentForm.getDuplicateFlightStatus())) {
				consignmentSession
						.setConsignmentDocumentVO(consignmentDocumentVO);
				int _total_Rec_Count_TO_Remove = consignmentSession
						.getTotalRecords();
				int _total_Rec_Count = consignmentDocumentVO
						.getMailInConsignmentVOs().getTotalRecordCount()
						- _total_Rec_Count_TO_Remove;
				consignmentDocumentVO.getMailInConsignmentVOs()
						.setTotalRecordCount(_total_Rec_Count);
				invocationContext.target = TARGET;
				return;
			}

    	validateMailDetails(consignmentForm, consignmentSession,
					invocationContext, consignmentDocumentVO, logonAttributes);
			if (invocationContext.getErrors() != null
					&& invocationContext.getErrors().size() > 0) {
				invocationContext.target = TARGET;
				int _total_Rec_Count_TO_Remove = consignmentSession
						.getTotalRecords();
				int _total_Rec_Count = consignmentDocumentVO
						.getMailInConsignmentVOs().getTotalRecordCount()
						- _total_Rec_Count_TO_Remove;
				consignmentDocumentVO.getMailInConsignmentVOs()
						.setTotalRecordCount(_total_Rec_Count);
				return;
			}

    	consignmentDocumentVO.setCompanyCode(logonAttributes
					.getCompanyCode());
			consignmentDocumentVO.setAirportCode(logonAttributes
					.getAirportCode());
			consignmentDocumentVO.setLastUpdateUser(logonAttributes.getUserId()
					.toUpperCase());
			log.log(Log.FINE, "Going To Save ...in command",
					consignmentDocumentVO);
//			 for(MailInConsignmentVO mailbagVO:mailVOs) {
//
//					 mailbagVO.setStatedWeight(mailbagVO.getStatedWeight()/10);
//
//			 }
			try {
				new MailTrackingDefaultsDelegate()
						.saveConsignmentDocument(consignmentDocumentVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				consignmentForm.setFromPopupflg("N");
				consignmentForm.setAfterPopupSaveFlag("Y");
				int _total_Rec_Count_TO_Remove = consignmentSession
						.getTotalRecords();
				int _total_Rec_Count = consignmentDocumentVO
						.getMailInConsignmentVOs().getTotalRecordCount()
						- _total_Rec_Count_TO_Remove;
				consignmentDocumentVO.getMailInConsignmentVOs()
						.setTotalRecordCount(_total_Rec_Count);
				invocationContext.target = TARGET;
				return;
			}

		log.log(Log.FINE, "consignmentForm.getFromPopupflg after delegate==>>",
				consignmentForm.getFromPopupflg());
		if("Y".equals(consignmentForm.getFromPopupflg())){
			log.log(Log.FINE, "inside Yequals(consignmentForm.getFromPopupflg");
			consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			consignmentForm.setFromPopupflg("N");
			consignmentForm.setAfterPopupSaveFlag("Y");
			int totalRecords = -1;
			consignmentSession.setTotalRecords(totalRecords);
				log
						.log(Log.FINE, "-----totalRecords-----Set as ",
								totalRecords);
		}else{
	  	  	ConsignmentDocumentVO conDocumentVO = new ConsignmentDocumentVO();
	  		consignmentSession.setConsignmentDocumentVO(conDocumentVO);
	  		consignmentForm.setConDocNo("");
			consignmentForm.setPaCode("");
			consignmentForm.setDirection("");
			consignmentForm.setDisableListSuccess("");
			consignmentForm.setDirection("O");
			consignmentForm.setDisplayPage("1");
			consignmentForm.setLastPageNum("0");
			consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		  	invocationContext.addError(new ErrorVO(SAVE_SUCCESS));
		}
		log.log(Log.FINE,
				"consignmentForm.getAfterPopupSaveFlag after delegate==>>",
				consignmentForm.getAfterPopupSaveFlag());
      }
		invocationContext.target = TARGET;
    	log.exiting("SaveConsignmentCommand","execute");
	}

    /**
	 * Method to validate form.
	 * @param consignmentForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(ConsignmentForm consignmentForm) {
		String conDocNo = consignmentForm.getConDocNo();
		String paCode = consignmentForm.getPaCode();
		String direction = consignmentForm.getDirection();
        String conDate = consignmentForm.getConDate();
        String type = consignmentForm.getType();
        String routeOpFlag[] = consignmentForm.getRouteOpFlag();
        boolean isEmpty=true;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(conDocNo == null || ("".equals(conDocNo.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.condocno.empty"));
		}
		if(conDocNo!=null && conDocNo.trim().length()>0){
			if(conDocNo.startsWith("ACM")){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.wrongcsgdocformat"));
			}
		}
		if(paCode == null || ("".equals(paCode.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.pacode.empty"));
		}
		if(direction == null || ("".equals(direction.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.operationtype.empty"));
		}
		if(conDate == null || ("".equals(conDate.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.condate.empty"));
		}
		if(type == null || ("".equals(type.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.type.empty"));
		}
		if(routeOpFlag!=null && routeOpFlag.length>1){
			for(int count=0;count<routeOpFlag.length;count++){
				if(!"NOOP".equals(routeOpFlag[count]) && !"D".equals(routeOpFlag[count])){
					isEmpty=false;
					break;
				}
			}
		}
		//Modified as part of CRQ ICRD-100406 by A-5526 starts
		/*if(isEmpty){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.type.routeempty"));
		}
		*/
		//Modified as part of CRQ ICRD-100406 by A-5526 ends
		return errors;
	}



	/**
	 * This method is to validate consignments
	 * @param consignmentForm
	 * @param consignmentSession
	 * @param invocationContext
	 * @param consignmentDocumentVO
	 * @param logonAttributes
	 */
	private void validateConsignment(ConsignmentForm consignmentForm,
			ConsignmentSession consignmentSession,InvocationContext invocationContext,
			ConsignmentDocumentVO consignmentDocumentVO,LogonAttributes logonAttributes){

		Collection<ErrorVO> errors = null;

//    	validate PA code
	  	 String pacode = consignmentForm.getPaCode();
		if (pacode != null && pacode.trim().length() > 0) {
			log.log(Log.FINE, "Going To validate PA code ...in command");
	  	PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		  try {
			  postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
					  logonAttributes.getCompanyCode(),pacode.toUpperCase());
       }catch (BusinessDelegateException businessDelegateException) {
 			errors = handleDelegateException(businessDelegateException);
	  	  }
	  	  if (postalAdministrationVO == null) {
	  		invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidpacode",
	   				new Object[]{pacode.toUpperCase()}));
	  		invocationContext.target = TARGET;
	  		return;
	  	  }
		}

//		 VALIDATING Type with Mail Category Code
		int invalidCategory = 0;
		Collection<MailInConsignmentVO> mailVOs =  consignmentDocumentVO.getMailInConsignmentVOs();
		String type = consignmentDocumentVO.getType();
		if(mailVOs != null && mailVOs.size() > 0) {
		    for(MailInConsignmentVO mailbagVO:mailVOs) {
		    	if(!"D".equals(mailbagVO.getOperationFlag())){
		    	String category = mailbagVO.getMailCategoryCode();
		    	if("CN41".equals(type)){
		    		if(!"B".equals(category)){
		    			invalidCategory = 1;
		    		}
		    	}
		    	if("CN38".equals(type)){
		    		if("B".equals(category)){
		    			invalidCategory = 2;
		    		}
		    	}
		      }
		    }
		}
		if(invalidCategory == 1){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.consignment.typeCategorymismatchsal"));
	  		invocationContext.target = TARGET;
	  		return;
		}
		if(invalidCategory == 2){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.consignment.typeCategorymismatchnosal"));
	  		invocationContext.target = TARGET;
	  		return;
		}


	}


	/**
	 * This method is to validate consignments RoutingDetails
	 * @param consignmentForm
	 * @param consignmentSession
	 * @param invocationContext
	 * @param consignmentDocumentVO
	 * @param logonAttributes
	 */
	private void validateRoutingDetails(ConsignmentForm consignmentForm,
			ConsignmentSession consignmentSession,InvocationContext invocationContext,
			ConsignmentDocumentVO consignmentDocumentVO,LogonAttributes logonAttributes,DuplicateFlightSession duplicateFlightSession
			,MaintainFlightSession maintainFlightSession){

		Collection<ErrorVO> errors = null;
		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();


//		 VALIDATING CarrierCode
		String invalidCarrierCode = "";
		Collection<RoutingInConsignmentVO> routingVOs =  consignmentDocumentVO.getRoutingInConsignmentVOs();
		if(routingVOs != null && routingVOs.size() > 0) {
			try {
				for(RoutingInConsignmentVO routingVO:routingVOs) {
			    	if(!"D".equals(routingVO.getOperationFlag())){
			    	routingVO.setCompanyCode(logonAttributes.getCompanyCode());
			    	routingVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber().toUpperCase());
			    	routingVO.setPaCode(consignmentDocumentVO.getPaCode().toUpperCase());
//Added as part of CRQ ICRD-100406 by A-5526 starts
			    	String carrierCode ="";
			    	if(routingVO.getOnwardCarrierCode()!=null)
			    {
			    carrierCode = routingVO.getOnwardCarrierCode().toUpperCase();      
			    }      
//Added as part of CRQ ICRD-100406 by A-5526 ends
			    	AirlineValidationVO airlineValidationVO = null;

		        	airlineValidationVO = new AirlineDelegate().validateAlphaCode(
		        			logonAttributes.getCompanyCode(),carrierCode);

		        	if (airlineValidationVO == null) {
		        		if("".equals(invalidCarrierCode)){
		        			invalidCarrierCode = carrierCode;
			    		}else{
			    			invalidCarrierCode = new StringBuilder(invalidCarrierCode).append(",").append(carrierCode).toString();
			    		}
			    	}else{
			    		routingVO.setOnwardCarrierId(airlineValidationVO.getAirlineIdentifier());
			    	}
			      }
			    	String flightNumber = (routingVO.getOnwardFlightNumber());
			    	routingVO.setOnwardFlightNumber(flightNumber);
			    }
			}catch (BusinessDelegateException businessDelegateException) {
        		errors = handleDelegateException(businessDelegateException);
        	}
		}

		if(!"".equals(invalidCarrierCode)){
			formErrors.add(new ErrorVO("mailtracking.defaults.invalidcarrier",
	   				new Object[]{invalidCarrierCode.toUpperCase()}));
			invocationContext.addAllError(formErrors);
	  		invocationContext.target = TARGET;
	  		return;
		}

		consignmentDocumentVO.setRoutingInConsignmentVOs(routingVOs);
		consignmentSession.setConsignmentDocumentVO(consignmentDocumentVO);
		errors = null;
//		 VALIDATING POL
		String invalidPOL = "";
		if(routingVOs != null && routingVOs.size() > 0) {
			try {
				for(RoutingInConsignmentVO routingVO:routingVOs) {
			    	if(!"D".equals(routingVO.getOperationFlag())){
			    	String pol = routingVO.getPol().toUpperCase();
			    	AirportValidationVO airportValidationVO = null;

			        airportValidationVO = new AreaDelegate().validateAirportCode(
			        		logonAttributes.getCompanyCode(),pol);

		        	if (airportValidationVO == null) {
		        		if("".equals(invalidPOL)){
		        			invalidPOL = pol;
			    		}else{
			    			invalidPOL = new StringBuilder(invalidPOL).append(",").append(pol).toString();
			    		}
			    	}
			      }
			    }
			}catch (BusinessDelegateException businessDelegateException) {
        		errors = handleDelegateException(businessDelegateException);
        	}

		}
		if(!"".equals(invalidPOL)){
			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidpol",
	   				new Object[]{invalidPOL.toUpperCase()}));
			invocationContext.addAllError(formErrors);
	  		invocationContext.target = TARGET;
	  		return;
		}
		errors = null;
//		 VALIDATING POU
		String invalidPOU = "";
		if(routingVOs != null && routingVOs.size() > 0) {

			for(RoutingInConsignmentVO routingVO:routingVOs) {
				if(!"D".equals(routingVO.getOperationFlag())){
					String pou = routingVO.getPou().toUpperCase();
					AirportValidationVO airportValidationVO = null;
					try {
						airportValidationVO = new AreaDelegate().validateAirportCode(
								logonAttributes.getCompanyCode(),pou);
					}catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
					if (airportValidationVO == null) {
						if("".equals(invalidPOU)){
							invalidPOU = pou;
						}else{
							invalidPOU = new StringBuilder(invalidPOU).append(",").append(pou).toString();
						}
					}
				}
			}

		}
		if(!"".equals(invalidPOU)){
			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidpou",
	   				new Object[]{invalidPOU.toUpperCase()}));
			invocationContext.addAllError(formErrors);
	  		invocationContext.target = TARGET;
	  		return;
		}


//		Check for same POL and POU
		int sameAP = 0;
		if(routingVOs != null && routingVOs.size() > 0) {

		    for(RoutingInConsignmentVO routingVO:routingVOs) {
		    	log.log(Log.FINE, " routingVO ", routingVO);
				if(!"D".equals(routingVO.getOperationFlag())){
		    	log.log(Log.FINE, " D.equals(routingVO.getOperationFlag()");
		    	String pol = routingVO.getPol().toUpperCase();
		    	String pou = routingVO.getPou().toUpperCase();
		    	String flightRoute = null;
//Added if check as part of CRQ ICRD-100406 by A-5526 
	        	if (!pol.isEmpty() && !pou.isEmpty() && pol.equals(pou)) {     
	        		sameAP = 1;
	        		formErrors.add(new ErrorVO("mailtracking.defaults.consignment.sameairport",
	    	   				new Object[]{new StringBuilder(routingVO.getOnwardCarrierCode())
	        				.append("").append(routingVO.getOnwardFlightNumber()).toString()}));
		    	}
//		   		 VALIDATE FLIGHT CARRIER CODE
	    		AirlineDelegate airlineDelegate = new AirlineDelegate();
	    		AirlineValidationVO airlineValidationVO = null;
	    		String flightCarrierCode = routingVO.getOnwardCarrierCode();
	    		errors = null;
	        	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {
	        		try {
	        			airlineValidationVO = airlineDelegate.validateAlphaCode(
	        					logonAttributes.getCompanyCode(),
	        					flightCarrierCode.trim().toUpperCase());

	        		}catch (BusinessDelegateException businessDelegateException) {
	        			errors = handleDelegateException(businessDelegateException);
	        		}
	        		if (errors != null && errors.size() > 0) {

	        			errors = new ArrayList<ErrorVO>();
	        			Object[] obj = {flightCarrierCode.toUpperCase()};
	    				  			sameAP = 1;
	    				  			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidCarrier"
	        					,obj));
	        		}
	        	}

	        	//Checking Flight number  validation

            	if(flightCarrierCode.equals(logonAttributes.getOwnAirlineCode())){
            	Collection<FlightValidationVO> flightValidationVOs = new ArrayList<FlightValidationVO>();
            		FlightFilterVO flightFilterVO = handleFlightFilterVO(
            				routingVO,logonAttributes);
            		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
            	log.log(Log.FINE,
						"consignmentForm.getDuplicateFlightStatus() ",
						consignmentForm.getDuplicateFlightStatus());
				log.log(Log.FINE, "routingVO.getIsDuplicateFlightChecked() ",
						routingVO.getIsDuplicateFlightChecked());
				if("".equals(consignmentForm.getDuplicateFlightStatus()) &&
            			("".equals(routingVO.getIsDuplicateFlightChecked()) || routingVO.getIsDuplicateFlightChecked() == null )){
            		try {
            			flightValidationVOs =
            				new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);
                    	log.log(Log.FINE, "flightValidationVOs ",
								flightValidationVOs);
            		}catch (BusinessDelegateException businessDelegateException) {
            			errors = handleDelegateException(businessDelegateException);
            		}
        		}else{
        			flightValidationVOs.add(duplicateFlightSession.getFlightValidationVO());
        			consignmentForm.setDuplicateFlightStatus("");
        		}
            	if (errors != null && errors.size() > 0) {
            			errors = new ArrayList<ErrorVO>();
	        			Object[] obj = {flightCarrierCode.toUpperCase(),
	        					routingVO.getOnwardFlightNumber(),
	        					routingVO.getOnwardFlightDate().toDisplayDateOnlyFormat()};
	    				  			sameAP = 1;
	    				  			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.err.invalidflight"
	        					,obj));
            	}
        		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
        			Object[] obj = {flightCarrierCode.toUpperCase(),
        					routingVO.getOnwardFlightNumber(),
        					routingVO.getOnwardFlightDate().toDisplayDateOnlyFormat()};
		  			sameAP = 1;
		  			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.err.invalidflight"
		  					,obj));
        		}else if (flightValidationVOs.size()== 1) {
        			log.log(Log.FINE,
							"consignmentForm.getDuplicateFlightStatus() ",
							consignmentForm.getDuplicateFlightStatus());
					List<FlightValidationVO> validflightvos = (List<FlightValidationVO>)flightValidationVOs;
        			FlightValidationVO flightValidationVO = validflightvos.get(0);
        			//Added by A-5249 for the ICRD-84046 starts
        			if((FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus()) ||
                            FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())||
                            FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus()))){
                      Object[] obj = {flightCarrierCode.toUpperCase(),flightValidationVO.getFlightNumber()};
                      ErrorVO err = new ErrorVO("mailtracking.defaults.reassigncontainer.err.flightintbcortba",obj);
                      err.setErrorDisplayType(ErrorDisplayType.ERROR);
                      invocationContext.addError(err);
                      invocationContext.target = TARGET;
                       return;
        			}
        			if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus())){
                      Object[] obj = {flightCarrierCode.toUpperCase(),flightValidationVO.getFlightNumber()};
                      ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightcancelled",obj);
                      err.setErrorDisplayType(ErrorDisplayType.ERROR);
                      invocationContext.addError(err);
                      invocationContext.target = TARGET;
                       return;
        			}
        			//Added by A-5249 for the ICRD-84046 ends
        			//Validating whether POU present in the Flight Route
        			flightRoute = flightValidationVO.getFlightRoute();
        			String route = flightValidationVO.getFlightRoute();
        			if(route != null && route.length() >0) {
        				StringTokenizer stationTokens = new StringTokenizer(route,"-");
        				boolean isPouInRoute = false;
        				while(stationTokens.hasMoreTokens()) {
        					if(pou.equals(stationTokens.nextToken()) && formErrors.size() == 0 ) {
        						isPouInRoute = true;
        						break;
        					}
        				}
        				if(!isPouInRoute) {
        					Object[] obj = {pou,
        							flightCarrierCode.toUpperCase(),
        							routingVO.getOnwardFlightNumber(),
        							route};
        		  			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.err.invalidflightRoute",obj));
        				}
        			}
        			routingVO.setOnwardCarrierSeqNum(flightValidationVO.getFlightSequenceNumber());
        		}else {
        			duplicateFlightSession.setFlightValidationVOs((ArrayList<FlightValidationVO>)(flightValidationVOs));
        			FlightValidationVO flightValidationVO = selectFlightValidationVO(flightValidationVOs, pol, pou);
        			if(flightValidationVO  ==null){
        				flightRoute = "";
        				String route = "";
        				Object[] obj = {pou,
    							flightCarrierCode.toUpperCase(),
    							routingVO.getOnwardFlightNumber(),
    							pol+"-"+pou};
    		  			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.err.invalidflightRoute",obj));
        			}else{
        				flightRoute = flightValidationVO.getFlightRoute();
            			String route = flightValidationVO.getFlightRoute();
        				routingVO.setOnwardCarrierSeqNum(flightValidationVO.getFlightSequenceNumber());
        			}
//        			duplicateFlightSession.setParentScreenId(SCREEN_ID);
//        			duplicateFlightSession.setFlightFilterVO(flightFilterVO);
//        			maintainFlightSession.setCompanyCode(logonAttributes.getCompanyCode());
//        			consignmentForm.setDuplicateFlightStatus(FLAG_YES);
//        			routingVO.setIsDuplicateFlightChecked(FLAG_YES);
//        			break;

        		}

            	}else{
            		routingVO.setOnwardCarrierSeqNum(MailConstantsVO.DESTN_FLT);
            	}


//	        	 VALIDATE POL & POU
	        	Collection<ErrorVO> polErrors = new ArrayList<ErrorVO>();
	        	Collection<ErrorVO> pouErrors = new ArrayList<ErrorVO>();
            	String org = routingVO.getPol();
            	String dest = routingVO.getPou();
            	AreaDelegate areaDelegate = new AreaDelegate();
            	AirportValidationVO airportValidationVO = null;
            	errors = null;
            	if (org != null && !"".equals(org)) {
            		try {
            			airportValidationVO = areaDelegate.validateAirportCode(
            					logonAttributes.getCompanyCode(),
            					org.toUpperCase());

            		}catch (BusinessDelegateException businessDelegateException) {
                		errors = handleDelegateException(businessDelegateException);
            		}
            		if (errors != null && errors.size() > 0) {
            			polErrors.addAll(errors);
            			}
            	if (polErrors != null && polErrors.size() > 0) {
            		Object[] obj = {org.toUpperCase()};
		  			sameAP = 1;
		  			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidPOL"
        					,obj));
        		}}
            	errors = null;
            	if (dest != null && !"".equals(dest)) {
            		try {
            			airportValidationVO = areaDelegate.validateAirportCode(
            					logonAttributes.getCompanyCode(),
            					dest.toUpperCase());

            		}catch (BusinessDelegateException businessDelegateException) {
                		errors = handleDelegateException(businessDelegateException);
            		}
            		if (errors != null && errors.size() > 0) {
            			pouErrors.addAll(errors);
            			}
            	if (pouErrors != null && pouErrors.size() > 0) {
            		Object[] obj = {dest.toUpperCase()};
		  			sameAP = 1;
		  			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidPOU"
        					,obj));
        		}
            	}

            	if((flightRoute!=null && flightRoute.trim().length()>0) && (pol != null && pol.trim().length()>0) && (pou != null && pou.trim().length() >0)){
            		boolean isValidRoute = validateFlightRoute(flightRoute, pol, pou);
            		if(!isValidRoute){


            			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.consignment.err.invalidflight");
            			Object [] obj = {flightCarrierCode.toUpperCase(),
	        					routingVO.getOnwardFlightNumber(),
	        					routingVO.getOnwardFlightDate().toDisplayDateOnlyFormat()};
            			errorVO.setErrorData(obj);
            			if(errors == null){
            				errors = new ArrayList<ErrorVO>();
            			}
            			errors.add(errorVO);
            			invocationContext.addAllError(errors);
            			invocationContext.target = TARGET;
            			return;

            		}
            	}
		      }
		    }
		}
		log.log(Log.FINE, " out formErrors ", formErrors);
		if(formErrors.size()>0){
	    	   invocationContext.addAllError(formErrors);
	       }

		if(sameAP == 1){
			invocationContext.target = TARGET;
	  		return;
		}

		/**
		 * Added to check whether connection flights are continous
		 */
		//Commented by A-5266 for ICRD-36146 Starts
		/*if(routingVOs != null && routingVOs.size() > 1) {
			  List<RoutingInConsignmentVO>	routingVOList = (List<RoutingInConsignmentVO>)routingVOs;
			  int routingVOSize = routingVOList.size();
			  log.log(Log.FINE, " routingVOList.size() = "+routingVOSize);
			  for(int i=0;i<routingVOSize;i++) {
				  int nextvo = (i+1);

				  if(nextvo < routingVOSize){
					  log.log(Log.FINE, "nextvo = "+nextvo);
					  RoutingInConsignmentVO routingInConsignmentVO1 = (RoutingInConsignmentVO)routingVOList.get(i);
					  RoutingInConsignmentVO routingInConsignmentVO2 = (RoutingInConsignmentVO)routingVOList.get(i+1);
					  if(routingInConsignmentVO1.getOperationFlag()!=null && "D".equals(routingInConsignmentVO1.getOperationFlag())){
						  continue;
					  }
					  if(!routingInConsignmentVO1.getPou().equals(routingInConsignmentVO2.getPol())){
						  invocationContext.addError(new ErrorVO("mailtracking.defaults.consignment.mismatchinconnectionflights"));
				  		  invocationContext.target = TARGET;
				  		  return;
					  }
				  }
			  }
		}*/
		//Commented by A-5266 for ICRD-36146 ends

	}

	/**
	 * This method is to validate consignments MailDetails
	 * @param consignmentForm
	 * @param consignmentSession
	 * @param invocationContext
	 * @param consignmentDocumentVO
	 * @param logonAttributes
	 */
	private void validateMailDetails(ConsignmentForm consignmentForm,
			ConsignmentSession consignmentSession,InvocationContext invocationContext,
			ConsignmentDocumentVO consignmentDocumentVO,LogonAttributes logonAttributes){

		Collection<ErrorVO> errors = null;

		//VALIDATING ULD NO---- COMMENTED BY INDU FOR BUG 49459
		Map<String, String> results = null;
		Collection<String> codes = new ArrayList<String>();
      	codes.add(MailConstantsVO.ULD_INTEGRATION_ENABLED);
		boolean isUldIntEnable=false;
      	try {
      		results = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
      	} catch(BusinessDelegateException businessDelegateException) {
      		handleDelegateException(businessDelegateException);
      	}
      	if(results != null && results.size() > 0) {
      		if(results.containsKey(MailConstantsVO.ULD_INTEGRATION_ENABLED)){
      			if("Y".equals(results.get(MailConstantsVO.ULD_INTEGRATION_ENABLED)))
      				{
      				isUldIntEnable=true;
      				}
      		}
      	}
      	if(isUldIntEnable){
			String invalidULDNum = "";
		String uldnumber ="";
		ULDValidationVO uldValidationVO = null;
		Collection<MailInConsignmentVO> mailVOs =  consignmentDocumentVO.getMailInConsignmentVOs();
		if(mailVOs != null && mailVOs.size() > 0) {

				for(MailInConsignmentVO mailbagVO:mailVOs) {
			  try {
				     uldValidationVO = null;
			    	if(!"D".equals(mailbagVO.getOperationFlag())){
			    	 uldnumber = mailbagVO.getUldNumber();
			    	if(!"".equals(uldnumber) && uldnumber != null){
			    		ULDDelegate uldDelegate = new ULDDelegate();
			    		uldValidationVO = uldDelegate.validateULD(
		    							logonAttributes.getCompanyCode(),
		    							uldnumber.toUpperCase());
			    		   	}
			    	}
			    }
		       catch (BusinessDelegateException businessDelegateException) {
		    	log.log(Log.FINE, "!@@##ULD### uldValidationVO() ",
						uldValidationVO);
				if (uldValidationVO == null) {
	    			if("".equals(invalidULDNum)){
	    				invalidULDNum = uldnumber.toUpperCase();
	    			}else{
	    				invalidULDNum = new StringBuilder(invalidULDNum).append(",").append(uldnumber.toUpperCase()).toString();
	    			}
	    		}
    			errors = handleDelegateException(businessDelegateException);
    		}
			}//for

		}
		if(!"".equals(invalidULDNum)){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.consignment.invaliduldnumber",
	   				new Object[]{invalidULDNum.toUpperCase()}));
	  		invocationContext.target = TARGET;
	  		return;
		}
      	}
//		Check for same OOE and DOE

		int sameOE = 0;
		int diffSC = 0;
		Collection<MailInConsignmentVO> mailInConVOs = consignmentDocumentVO.getMailInConsignmentVOs();
		if(mailInConVOs != null && mailInConVOs.size() > 0){
	  		for(MailInConsignmentVO mailInConVO:mailInConVOs){
	  			if(!"D".equals(mailInConVO.getOperationFlag())){
	  				String ooe = mailInConVO.getOriginExchangeOffice();
			    	String doe = mailInConVO.getDestinationExchangeOffice();
			    	if(ooe.trim().length() == 6){
			    	  if(doe.trim().length() == 6){
			    	   if (ooe.equals(doe)) {
		        		 sameOE = 1;
		        		 if(mailInConVO.getReceptacleSerialNumber() != null &&
		 						!"".equals(mailInConVO.getReceptacleSerialNumber())){
		        			 invocationContext.addError(new ErrorVO("mailtracking.defaults.sameoe",
				    	   				new Object[]{mailInConVO.getMailId()}));
		        		 }else{
		        			 String pk = new StringBuilder(mailInConVO.getOriginExchangeOffice())
		        							.append(mailInConVO.getDestinationExchangeOffice())
		        							.append(mailInConVO.getMailClass())
		        							.append(mailInConVO.getYear())
		        							.append(mailInConVO.getDsn()).toString();
		        			 invocationContext.addError(new ErrorVO("mailtracking.defaults.sameoe",
				    	   				new Object[]{pk}));
		        		 }

			    	    }
			    	  }
			    	}

			    		/**
						 * Check for same class and subclass...- first Letter
						 */

						String mailClass = mailInConVO.getMailClass();
						String subClass = mailInConVO.getMailSubclass();
					    if(subClass == null || "".equals(subClass)){

							mailInConVO.setMailSubclass(new StringBuilder(mailClass)
												.append("_").toString());
					    }

					    subClass = mailInConVO.getMailSubclass();


						if (!mailClass.equals(subClass.substring(0,1))) {
							 diffSC = 1;
							 if(mailInConVO.getReceptacleSerialNumber() != null &&
									!"".equals(mailInConVO.getReceptacleSerialNumber())){
								 invocationContext.addError(new ErrorVO("mailtracking.defaults.diffmailclass",
											new Object[]{mailInConVO.getMailId()}));
							 }else{
								 String pk = new StringBuilder(mailInConVO.getOriginExchangeOffice())
												.append(mailInConVO.getDestinationExchangeOffice())
												.append(mailInConVO.getMailClass())
												.append(mailInConVO.getYear())
												.append(mailInConVO.getDsn()).toString();
								 invocationContext.addError(new ErrorVO("mailtracking.defaults.diffmailclass",
											new Object[]{pk}));
							 }

			    	    }

	  			}
	  		}
	  	}
		if(sameOE == 1  || diffSC == 1){
			invocationContext.target = TARGET;
	  		return;
		}

//Duplicate check for Mail bags
	  	Collection<MailInConsignmentVO> firstMailbagVOs = consignmentDocumentVO.getMailInConsignmentVOs();
	  	Collection<MailInConsignmentVO> secMailbagVOs = consignmentDocumentVO.getMailInConsignmentVOs();
	  	Collection<MailInConsignmentVO> mailbagVOs = new ArrayList<MailInConsignmentVO>();
	  	Collection<MailInConsignmentVO> despatchVOs = new ArrayList<MailInConsignmentVO>();

	  	int duplicate = 0;
	  	if(firstMailbagVOs != null && firstMailbagVOs.size() > 0){
	  		int count = 0;
	  		for(MailInConsignmentVO fstMailbagVO:firstMailbagVOs){
	  			if(fstMailbagVO.getReceptacleSerialNumber() != null &&
						!"".equals(fstMailbagVO.getReceptacleSerialNumber())){
	  			if(!"D".equals(fstMailbagVO.getOperationFlag())){
	  				log.log(Log.FINE, "!@@###fstMailbagVO.getOperationFlag() ",
							fstMailbagVO.getOperationFlag());
					mailbagVOs.add(fstMailbagVO);//Added for DSN PK Check --->> no mailbags and despatches must have same DSNPK
	  			for(MailInConsignmentVO secMailbagVO:secMailbagVOs){
	  				if(secMailbagVO.getReceptacleSerialNumber() != null &&
							!"".equals(secMailbagVO.getReceptacleSerialNumber())){
	  					if(!"D".equals(secMailbagVO.getOperationFlag())){
			  				if(fstMailbagVO.getMailId().equals(secMailbagVO.getMailId())){
			  					count++;
			  				}
	  					}
	  				}
	  			}
	  			if(count == 2){
	  				duplicate = 1;
	  				break;
	  			}
	  			count = 0;
	  		  }
	  		}
	  	  }
	  	}

	  	if(duplicate == 1){
	  		invocationContext.addError(new ErrorVO("mailtracking.defaults.consignment.duplicatemailbag"));
	  		invocationContext.target = TARGET;
	  		return;
	  	}

//	  Duplicate check for Despatches
	  	duplicate = 0;
	  	if(firstMailbagVOs != null && firstMailbagVOs.size() > 0){
	  		int count = 0;
	  		for(MailInConsignmentVO fstMailbagVO:firstMailbagVOs){
	  			if(!"D".equals(fstMailbagVO.getOperationFlag())){
	  			if(fstMailbagVO.getReceptacleSerialNumber() == null ||
						"".equals(fstMailbagVO.getReceptacleSerialNumber())){
	  			  despatchVOs.add(fstMailbagVO);//Added for DSN PK Check --->> no mailbags and despatches must have same DSNPK
	  			String firstPK = new StringBuilder()
	            .append(fstMailbagVO.getOriginExchangeOffice())
	            .append(fstMailbagVO.getDestinationExchangeOffice())
				.append(fstMailbagVO.getMailCategoryCode())
				.append(fstMailbagVO.getMailClass())
				.append(fstMailbagVO.getYear())
				.append(fstMailbagVO.getDsn())
//				.append(fstMailbagVO.getStatedBags())
//				.append(fstMailbagVO.getStatedWeight())
//				.append(fstMailbagVO.getUldNumber())
				.toString();
	  			for(MailInConsignmentVO secMailbagVO:secMailbagVOs){
	  				if(!"D".equals(secMailbagVO.getOperationFlag())){
	  				if(secMailbagVO.getReceptacleSerialNumber() == null ||
							"".equals(secMailbagVO.getReceptacleSerialNumber())){
	  				String secondPK = new StringBuilder()
		            .append(secMailbagVO.getOriginExchangeOffice())
		            .append(secMailbagVO.getDestinationExchangeOffice())
					.append(secMailbagVO.getMailCategoryCode())
					.append(secMailbagVO.getMailClass())
					.append(secMailbagVO.getYear())
					.append(secMailbagVO.getDsn())
//					.append(secMailbagVO.getStatedBags())
//					.append(secMailbagVO.getStatedWeight())
//					.append(secMailbagVO.getUldNumber())
					.toString();
		  				if(firstPK.equals(secondPK)){
		  					count++;
		  				}
	  				}
	  			}
	  			if(count == 2){
	  				duplicate = 1;
	  				break;
	  			}
	  		  }
	  			count = 0;
	  		}
	      }
	  	 }
	  	}

	  	if(duplicate == 1){
	  		invocationContext.addError(new ErrorVO("mailtracking.defaults.consignment.duplicatedespatch"));
	  		invocationContext.target = TARGET;
	  		return;
	  	}

	  	///////////////Added for DSN PK Check --->> no mailbags and despatches must have same DSNPK
	  	String dSNKeyMB = null;
	  	String dSNKeyDsp = null;
	  	//checking Newly inserted Mailbags with Despatches
	  	for(MailInConsignmentVO mailbagvo : mailbagVOs)
	  	{ 	if(mailbagvo.getOperationFlag()!= null){
	  			if((!("").equals(mailbagvo.getOperationFlag()))&& ("I").equals(mailbagvo.getOperationFlag())){
	  			//create DSN PK for mailbag
		  		dSNKeyMB = mailbagvo.getOriginExchangeOffice()+HYPHEN
		  		+mailbagvo.getDestinationExchangeOffice()+HYPHEN
		  		+mailbagvo.getMailCategoryCode()+HYPHEN
		  		+mailbagvo.getMailSubclass()+HYPHEN
		  		+mailbagvo.getDsn()+HYPHEN
		  		+mailbagvo.getYear();
	  			for(MailInConsignmentVO despatchvo : despatchVOs )
	  			{
	  				//create DSN PK for despatch
	  				dSNKeyDsp = despatchvo.getOriginExchangeOffice()+HYPHEN
	  				+despatchvo.getDestinationExchangeOffice()+HYPHEN
	  				+despatchvo.getMailCategoryCode()+HYPHEN
	  				+despatchvo.getMailSubclass()+HYPHEN
	  				+despatchvo.getDsn()+HYPHEN
	  				+despatchvo.getYear();
	  				if(dSNKeyMB.equals(dSNKeyDsp)){
	  					invocationContext.addError(new ErrorVO("mailtracking.defaults.acceptmail.similarmaildsnkey",new Object[]{dSNKeyMB}));
	  					invocationContext.target = TARGET;
	  					return;
	  			}
	  		}
	  	  }
	  	}
	  	}

	  	//checking Newly inserted Despatches with mailbags
	  	for(MailInConsignmentVO despatchvo : despatchVOs)
	  	{
	  		if(despatchvo.getOperationFlag()!= null){
	  		if((!("").equals(despatchvo.getOperationFlag()))&& ("I").equals(despatchvo.getOperationFlag())){

	  			//create DSN PK for despatch
		  		dSNKeyDsp = despatchvo.getOriginExchangeOffice()+HYPHEN
		  		+despatchvo.getDestinationExchangeOffice()+HYPHEN
		  		+despatchvo.getMailCategoryCode()+HYPHEN
		  		+despatchvo.getMailSubclass()+HYPHEN
		  		+despatchvo.getDsn()+HYPHEN
		  		+despatchvo.getYear();
	  			for(MailInConsignmentVO mailbagvo : mailbagVOs )
	  			{
	  				//create DSN PK for mailbag
	  				dSNKeyMB = mailbagvo.getOriginExchangeOffice()+HYPHEN
	  				+mailbagvo.getDestinationExchangeOffice()+HYPHEN
	  				+mailbagvo.getMailCategoryCode()+HYPHEN
	  				+mailbagvo.getMailSubclass()+HYPHEN
	  				+mailbagvo.getDsn()+HYPHEN
	  				+mailbagvo.getYear();
	  				if(dSNKeyDsp.equals(dSNKeyMB)){
	  					invocationContext.addError(new ErrorVO("mailtracking.defaults.acceptmail.similarmaildsnkey",new Object[]{dSNKeyMB}));
	  					invocationContext.target = TARGET;
	  					return;
	  			}
	  		}
	  	  }
	  		}
	  	}


	}

	/**
	 * This method is to format flightNumber
	 * Not using accoding to the CRQ-AirNZ989-12
	 * @param flightNumber
	 * @return String
	 */
 /*	private String formatFlightNumber(String flightNumber){
		int numLength = flightNumber.length();
		String newFlightNumber = "" ;
	    if(numLength == 1) {
	    	newFlightNumber = new  StringBuilder("000").append(flightNumber).toString();
	    }
	    else if(numLength == 2) {
	    	newFlightNumber = new  StringBuilder("00").append(flightNumber).toString();
	    }
	    else if(numLength == 3) {
	    	newFlightNumber = new  StringBuilder("0").append(flightNumber).toString();
	    }
	    else {
	    	newFlightNumber = flightNumber ;
	    }

		return newFlightNumber;
	}*/


	  /**
     * Method to create the filter vo for flight validation
     * @param mailManifestForm
     * @param logonAttributes
     * @return FlightFilterVO
     */
    private FlightFilterVO handleFlightFilterVO(
    		RoutingInConsignmentVO routingVO,
			LogonAttributes logonAttributes){

		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setStation(routingVO.getPol());
		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setActiveAlone(false);
		flightFilterVO.setFlightDate(routingVO.getOnwardFlightDate());
		flightFilterVO.setCarrierCode(routingVO.getOnwardCarrierCode());
		flightFilterVO.setFlightNumber(routingVO.getOnwardFlightNumber());
		log.log(Log.FINE, " ****** flightFilterVO***** ", flightFilterVO);
		return flightFilterVO;
	}

    /**
     *
     * @param flightRoute
     * @return
     */
    private boolean validateFlightRoute(String flightRoute,String pol,String pou) {
    	 String [] routeArray = flightRoute.split("-");
 	    Collection<String> segmentS = new ArrayList<String>();
 	   String route = pol.concat("-").concat(pou);
 	    for(int i=0;i<routeArray.length-1;i++){

 	    		for(int j=i+1;j<routeArray.length;j++){
 	    			String segment = routeArray[i].concat("-").concat(routeArray[j]);
 	    			segmentS.add(segment);
 	    		}
 	    }
 	   return (segmentS.contains(route));

    }
    /**
     *
     * @param flightValidationVOs
     * @param pol
     * @param pou
     * @return
     */
    private FlightValidationVO selectFlightValidationVO(Collection<FlightValidationVO> flightValidationVOs,String pol,String pou){
    	for(FlightValidationVO flightValidationVO :flightValidationVOs ){
    		boolean selectFlag = false;
    		String flightRoute  = flightValidationVO.getFlightRoute();
    		selectFlag = validateFlightRoute(flightRoute,pol,pou);
    		if(selectFlag){
    			return flightValidationVO;
    		}
    	}
    	return null;
    }
}
