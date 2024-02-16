/*
 * SaveConsignmentCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignment;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Iterator;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MaintainConsignmentModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Consignment;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailInConsignment;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.MaintainFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitConversionNewVO;


/**
 * @author A-5991
 *
 */
public class SaveConsignmentCommand extends AbstractCommand {


   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */

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

	private static final String INT_REGEX = "[0-9]+";
	private static final String NO_OF_CHAR_ALLOWED_FOR_MAILTAG = "mailtracking.defaults.noofcharsallowedformailtag";
	private static final String SPECIAL_CHARACTER_REGEX="[A-Za-z0-9]+";
	 /**
	 * This method overrides the execute method of BaseComand class
	 * @param actionContext
	 * @return
	 * @throws CommandInvocationException
	 * @throws BusinessDelegateException 
	 */
    public void execute(ActionContext actionContext)
            throws CommandInvocationException, BusinessDelegateException {

    	log.entering("SaveConsignmentCommand","**execute");

    	MaintainConsignmentModel maintainConsignmentModel = (MaintainConsignmentModel) actionContext.getScreenModel();
    	ConsignmentSession consignmentSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);  
    	maintainConsignmentModel.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	List<ErrorVO> errors = null;
    	DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		MaintainFlightSession maintainFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_FLIGHT);
    	errors = validateForm(maintainConsignmentModel);
    	/*
		 * Getting OneTime values
		 */
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		if (oneTimes != null) {	
			Collection<OneTimeVO> mailServiceLevels = oneTimes.get("mail.operations.mailservicelevels");
			maintainConsignmentModel.setOneTimeMailServiceLevel(mailServiceLevels);
		}
	
		
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError(errors);
		}else{
			maintainConsignmentModel.getConsignment().setAirportCode(logonAttributes.getAirportCode());
			MailOperationsModelConverter mailOperationsModelConverter = new MailOperationsModelConverter();
			Consignment consignment =maintainConsignmentModel.getConsignment();
			Collection<MailInConsignment> mailsInConsignment = consignment.getMailsInConsignment();
			Iterator<MailInConsignment> mailInConsItrator = mailsInConsignment.iterator();
			while (mailInConsItrator.hasNext()) {
				MailInConsignment mailInConsignmentData = mailInConsItrator.next();
				if(!(isValidMailtag(mailInConsignmentData.getMailId())||(mailInConsignmentData.getMailId().length()==29&&validateMailTagFormat(mailInConsignmentData.getMailId())))){
					actionContext.addError(new ErrorVO("Invalid Mailbag:"+mailInConsignmentData.getMailId()));
					return;
				}
			}
			ConsignmentDocumentVO consignmentDocumentVO = mailOperationsModelConverter.constructConsignmentDocumentVO(maintainConsignmentModel.getConsignment(), logonAttributes,maintainConsignmentModel.getOneTimeMailServiceLevel());
			if(maintainConsignmentModel.getConsignment().isDomestic()){
				List<MailInConsignmentVO> mailInConsignmentModelList = new ArrayList<MailInConsignmentVO>(); 
				Iterator<MailInConsignmentVO> mailInConsItr = consignmentDocumentVO.getMailInConsignmentVOs().iterator();
				while (mailInConsItr.hasNext()) {
					MailInConsignmentVO mailInConsignment = mailInConsItr.next();
					try {
						String pol = consignmentDocumentVO.getRoutingInConsignmentVOs().iterator().next().getPol();
						String pou = "";
						for(RoutingInConsignmentVO consignmentRouting : consignmentDocumentVO.getRoutingInConsignmentVOs()){
							pou = consignmentRouting.getPou();
						}
						
						mailInConsignment = new MailTrackingDefaultsDelegate()
								.populatePCIDetailsforUSPS(mailInConsignment, logonAttributes.getAirportCode(), logonAttributes.getCompanyCode(), pol, pou, maintainConsignmentModel.getConsignment().getConsignmentDate().substring(maintainConsignmentModel.getConsignment().getConsignmentDate().length()-1, maintainConsignmentModel.getConsignment().getConsignmentDate().length()));
					} catch (SystemException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BusinessDelegateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Measure weight = new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailInConsignment.getMailId().substring(mailInConsignment.getMailId().length()-2)));
					mailInConsignment.setStatedWeight(weight);
					mailInConsignmentModelList.add(mailInConsignment);
				}
				consignmentDocumentVO.setMailInConsignmentVOs(mailOperationsModelConverter.convertConsignmentDocumentListToPage(mailInConsignmentModelList));
			}

    	log.log(Log.FINE, "consignmentDocumentVO ", consignmentDocumentVO);
		log.log(Log.FINE,
				"consignmentForm.getFromPopupflg while entering delegate==>>",
				maintainConsignmentModel.getFromPopupflg());
		Collection<MailInConsignment> mailBags = maintainConsignmentModel.getConsignment().getMailsInConsignment();
		Page<MailInConsignmentVO> mailVOs =  consignmentDocumentVO.getMailInConsignmentVOs();
    	Collection<MailInConsignmentVO> validateMailVOs = new ArrayList<MailInConsignmentVO>();
    	if(mailVOs != null && mailVOs.size() > 0) {

			  for(MailInConsignmentVO mailbagVO:mailVOs) {
				  mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
				  mailbagVO.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber().toUpperCase());
				  mailbagVO.setPaCode(consignmentDocumentVO.getPaCode().toUpperCase());
				  mailbagVO.setMailSource(maintainConsignmentModel.getNumericalScreenId());
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

					
					   String wt = String.valueOf(mailbagVO.getStatedWeight().getSystemValue());
						log.log(Log.FINE, "wt ...in command", wt);
						int len = wt.indexOf(".");*/
				 //Added by a-8353 for icrd-ICRD-274933 starts
					double wgt=mailbagVO.getStatedWeight().getDisplayValue();		
					UnitConversionNewVO unitConversionVO= null;    
					String wgtStr="";
					try {
						unitConversionVO=UnitFormatter.getUnitConversionForToUnit(UnitConstants.MAIL_WGT,mailbagVO.getStatedWeight().getDisplayUnit(),"H",wgt);
					} catch (UnitException e) {
						// TODO Auto-generated catch block
						e.getMessage();
					}  
					double weighttoappend = unitConversionVO.getToValue();
					//double roundedWeight=round(weighttoappend,0);
					String mailWgt=String.valueOf(((int)(weighttoappend)));	
				/*	String wgt = wt;
						if(len<4){
						if(len > 0){
							wgt = new StringBuilder(wt.substring(0,len)).append(wt.substring(len+1,len+2)).toString();
						}else{
							wgt = new StringBuilder(wt).append("0").toString();
							}
						}
						else if(len>=4){
							wgt=new StringBuilder(wt.substring(0,4)).toString();
						}
						String stdwgt = wgt;
						if(wgt.length() == 3){
							stdwgt = new StringBuilder("0").append(wgt).toString();
						}
						if(wgt.length() == 2){
							stdwgt = new StringBuilder("00").append(wgt).toString();
						}
						if(wgt.length() == 1){
							stdwgt = new StringBuilder("000").append(wgt).toString();
						}*/
					if(mailWgt.length() == 3){
						wgtStr = new StringBuilder("0").append(mailWgt).toString();  
					}
					else if(mailWgt.length() == 2){
						wgtStr = new StringBuilder("00").append(mailWgt).toString();
					}
					else if(mailWgt.length() == 1){
						wgtStr = new StringBuilder("000").append(mailWgt).toString();
					}
					else if(mailWgt.length() >4||mailWgt.length() ==0){
						wgtStr = new StringBuilder("0000").toString();//Handle case when weight is incorrect
					}
					else{
						wgtStr = mailWgt;
						}

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
						.append(wgtStr).toString();	 

						if(!maintainConsignmentModel.getConsignment().isDomestic()){
							   if(mailbagVO.getMailId()!=null){
									 mailbagVO.setMailId(mailbagVO.getMailId());
			                      }
			                      else{
						 mailbagVO.setMailId(mailId);
			                      }
						}  



			     log.log(Log.FINE, "mailId ...in command", mailId);

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

    	validateConsignment(maintainConsignmentModel,consignmentSession,actionContext,
    			consignmentDocumentVO,logonAttributes);
    	if (actionContext.getErrors() != null
					&& actionContext.getErrors().size() > 0) {
    		//errors = actionContext.getErrors();
    		//actionContext.addAllError(errors);
//				actionContext.target = TARGET;
				return;
			}

    	//if(maintainConsignmentModel.getConsignment().isDomestic()){//Commented.A-8164 for ICRD-342608 
    	validateRoutingDetails(maintainConsignmentModel, consignmentSession,
					actionContext, consignmentDocumentVO, logonAttributes,
					duplicateFlightSession, maintainFlightSession);
			if (actionContext.getErrors() != null
					&& actionContext.getErrors().size() > 0) {    
				errors = actionContext.getErrors();
				//actionContext.addAllError(errors);  
//				actionContext.target = TARGET;
				return;
			}
    	if ("Y".equals(maintainConsignmentModel.getDuplicateFlightStatus())) {
				consignmentSession
						.setConsignmentDocumentVO(consignmentDocumentVO);
				int _total_Rec_Count_TO_Remove = consignmentSession
						.getTotalRecords();
				int _total_Rec_Count = consignmentDocumentVO
						.getMailInConsignmentVOs().getTotalRecordCount()
						- _total_Rec_Count_TO_Remove;
				consignmentDocumentVO.getMailInConsignmentVOs()
						.setTotalRecordCount(_total_Rec_Count);
//				actionContext.target = TARGET;
				return;
			}
    	//}
    	validateMailDetails(maintainConsignmentModel, consignmentSession,
					actionContext, consignmentDocumentVO, logonAttributes);
			if (actionContext.getErrors() != null
					&& actionContext.getErrors().size() > 0) {
//				actionContext.target = TARGET;
				//errors = actionContext.getErrors();
				//actionContext.addAllError(errors);
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
				actionContext.addAllError(errors);
				//errors = actionContext.getErrors();
//				actionContext.target = TARGET;
				return;
			}

		log.log(Log.FINE, "consignmentForm.getFromPopupflg after delegate==>>",
				maintainConsignmentModel.getFromPopupflg());
		if("Y".equals(maintainConsignmentModel.getFromPopupflg())){
			log.log(Log.FINE, "inside Yequals(consignmentForm.getFromPopupflg");
			maintainConsignmentModel.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			maintainConsignmentModel.setFromPopupflg("N");
			maintainConsignmentModel.setAfterPopupSaveFlag("Y");
			int totalRecords = -1;
			consignmentSession.setTotalRecords(totalRecords);
				log
						.log(Log.FINE, "-----totalRecords-----Set as ",
								totalRecords);
		}else{
	  	  	ConsignmentDocumentVO conDocumentVO = new ConsignmentDocumentVO();
	  		consignmentSession.setConsignmentDocumentVO(conDocumentVO);
	  		maintainConsignmentModel.setConDocNo("");
	  		maintainConsignmentModel.setPaCode("");
			maintainConsignmentModel.setDirection("");
			maintainConsignmentModel.setDisableListSuccess("");
			maintainConsignmentModel.setDirection("O");
			maintainConsignmentModel.setDisplayPage("1");
			maintainConsignmentModel.setLastPageNum("0");
			maintainConsignmentModel.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
//		  	actionContext.addError(new ErrorVO(SAVE_SUCCESS));
		  	ErrorVO error=new ErrorVO(SAVE_SUCCESS);
			error.setErrorDisplayType(ErrorDisplayType.INFO);
			actionContext.addError(error); 
		}
		log.log(Log.FINE,
				"consignmentForm.getAfterPopupSaveFlag after delegate==>>",
				maintainConsignmentModel.getAfterPopupSaveFlag());
      }
//		actionContext.target = TARGET;
    	log.exiting("SaveConsignmentCommand","execute");
	}

    /**
	 * Method to validate form.
	 * @param consignmentForm
	 * @return Collection<ErrorVO>
	 */
	private List<ErrorVO> validateForm(MaintainConsignmentModel maintainConsignmentModel) {
		String conDocNo = maintainConsignmentModel.getConsignment().getConsignmentNumber();
		String paCode = maintainConsignmentModel.getConsignment().getPaCode();
		String direction = maintainConsignmentModel.getConsignment().getOperation();
        String conDate = maintainConsignmentModel.getConsignment().getConsignmentDate();
        String type = maintainConsignmentModel.getConsignment().getType();
        String routeOpFlag[] = maintainConsignmentModel.getRouteOpFlag();
        boolean isEmpty=true;
		List<ErrorVO> errors = new ArrayList<ErrorVO>();
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
		if(conDate == null || conDate.isEmpty()){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.msg.err.condateempty"));
		}
		if(type == null || ("".equals(type.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.msg.err.typeempty"));
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
	 * @param actionContext
	 * @param consignmentDocumentVO
	 * @param logonAttributes
	 */
	private void validateConsignment(MaintainConsignmentModel maintainConsignmentModel,
			ConsignmentSession consignmentSession,ActionContext actionContext,
			ConsignmentDocumentVO consignmentDocumentVO,LogonAttributes logonAttributes){

		Collection<ErrorVO> errors = null;

//    	validate PA code
	  	 String pacode = maintainConsignmentModel.getPaCode();
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
	  		actionContext.addError(new ErrorVO("mailtracking.defaults.invalidpacode",
	   				new Object[]{pacode.toUpperCase()}));
//	  		actionContext.target = TARGET;
	  		return;
	  	  }
		}

//		 VALIDATING Type with Mail Category Code
		int invalidCategory = 0;
		int invalidoriginAirport = 0;
		int invalidDestinationAirport = 0;
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
		    	if(!"A".equals(category) && !"B".equals(category) && !"C".equals(category) && !"D".equals(category)){
		    		  invalidCategory = 3;
		    	  }
		    	//Added as part of IASCB-46095
		    	
		    	//Validating origin Airport
		    	if(mailbagVO.getMailOrigin()!=null && mailbagVO.getMailOrigin().trim().length()>0){
		    	  invalidoriginAirport=validateOriginDestinationAirport(mailbagVO.getMailOrigin(),logonAttributes);
		    	  
		 		 }
		    	//Validating destination Airport
		    	if(mailbagVO.getMailDestination()!=null && mailbagVO.getMailDestination().trim().length()>0){
		    	 invalidDestinationAirport= validateOriginDestinationAirport(mailbagVO.getMailDestination(),logonAttributes);
		    	 
			 		 }
		      }
		    }
		}
		if(invalidCategory == 1){
			actionContext.addError(new ErrorVO("mailtracking.defaults.consignment.typeCategorymismatchsal"));
//	  		actionContext.target = TARGET;
	  		return;
		}
		if(invalidCategory == 2){
			actionContext.addError(new ErrorVO("mailtracking.defaults.consignment.typeCategorymismatchnosal"));
//	  		actionContext.target = TARGET;
	  		return;
		}
		if(invalidCategory == 3){
			actionContext.addError(new ErrorVO("mailtracking.defaults.consignment.invalidCategory"));
//	  		actionContext.target = TARGET;
	  		return;
		}
		if(invalidDestinationAirport == 1 || invalidoriginAirport == 1 ){
 			actionContext.addError(new ErrorVO("mailtracking.defaults.invalidairport"));
 	  		return;
 		 }
		


	}

   /**
    * 
    * @param origin
    * @param logonAttributes 
    * @return
    */
	private int validateOriginDestinationAirport(String airport,LogonAttributes logonAttributes) {
		 AirportValidationVO airportValidationVO = null;
		 AreaDelegate areaDelegate = new AreaDelegate();
		 Collection<ErrorVO> errors = new ArrayList();
		
		try {
			airportValidationVO = areaDelegate.validateAirportCode(
					logonAttributes.getCompanyCode(),
					airport.toUpperCase());
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
        	return 1;
		}else{
			return 0;
		}
	
	}

	/**
	 * This method is to validate consignments RoutingDetails
	 * @param consignmentForm
	 * @param consignmentSession
	 * @param actionContext
	 * @param consignmentDocumentVO
	 * @param logonAttributes
	 */
	private void validateRoutingDetails(MaintainConsignmentModel maintainConsignmentModel,
			ConsignmentSession consignmentSession,ActionContext actionContext,
			ConsignmentDocumentVO consignmentDocumentVO,LogonAttributes logonAttributes,DuplicateFlightSession duplicateFlightSession
			,MaintainFlightSession maintainFlightSession){

		List<ErrorVO> errors = null;
		List<ErrorVO> formErrors = new ArrayList<ErrorVO>();


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
			actionContext.addAllError(formErrors);
//	  		actionContext.target = TARGET;
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
			    		if(routingVO.getPol()!=null)
			    		{
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
			      }}
			    }
			}catch (BusinessDelegateException businessDelegateException) {
        		errors = handleDelegateException(businessDelegateException);
        	}

		}
		if(!"".equals(invalidPOL)){
			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidpol",
	   				new Object[]{invalidPOL.toUpperCase()}));
			actionContext.addAllError(formErrors);
//	  		actionContext.target = TARGET;
	  		return;
		}
		errors = null;
//		 VALIDATING POU
		String invalidPOU = "";
		if(routingVOs != null && routingVOs.size() > 0) {

			for(RoutingInConsignmentVO routingVO:routingVOs) {
				if(!"D".equals(routingVO.getOperationFlag())){
					if (routingVO.getPou()!=null) {
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
			}}

		}
		if(!"".equals(invalidPOU)){
			formErrors.add(new ErrorVO("mailtracking.defaults.consignment.invalidpou",
	   				new Object[]{invalidPOU.toUpperCase()}));
			actionContext.addAllError(formErrors);
//	  		actionContext.target = TARGET;
	  		return;
		}


//		Check for same POL and POU
		int sameAP = 0;
		if(routingVOs != null && routingVOs.size() > 0) {

		    for(RoutingInConsignmentVO routingVO:routingVOs) {
		    	log.log(Log.FINE, " routingVO ", routingVO);
				if(!"D".equals(routingVO.getOperationFlag())){
		    	log.log(Log.FINE, " D.equals(routingVO.getOperationFlag()");
		    	if (routingVO.getPol()!=null && routingVO.getPou()!=null)
		    	{
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
            	Collection<FlightValidationVO> flightValidationVOs = null;
            		FlightFilterVO flightFilterVO = handleFlightFilterVO(
            				routingVO,logonAttributes);
            		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());
            	log.log(Log.FINE,
						"consignmentForm.getDuplicateFlightStatus() ",
						maintainConsignmentModel.getDuplicateFlightStatus());
				log.log(Log.FINE, "routingVO.getIsDuplicateFlightChecked() ",
						routingVO.getIsDuplicateFlightChecked());
				if((maintainConsignmentModel.getDuplicateFlightStatus()==null||"".equals(maintainConsignmentModel.getDuplicateFlightStatus())) &&
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
        			maintainConsignmentModel.setDuplicateFlightStatus("");
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
		  			/*formErrors.add(new ErrorVO("mailtracking.defaults.consignment.err.invalidflight"
		  					,obj));*/
		  			actionContext.addError(new ErrorVO("mailtracking.defaults.consignment.msg.err.invalidflight"//A-8164 for ICRD-342608
		  					,obj));
                   return;
        		}else if (flightValidationVOs.size()== 1) {
        			log.log(Log.FINE,
							"consignmentForm.getDuplicateFlightStatus() ",
							maintainConsignmentModel.getDuplicateFlightStatus());
					List<FlightValidationVO> validflightvos = (List<FlightValidationVO>)flightValidationVOs;
        			FlightValidationVO flightValidationVO = validflightvos.get(0);
        			//Added by A-5249 for the ICRD-84046 starts
        			if(flightValidationVO!=null){
        			if((FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus()) ||
                            FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())||
                            FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus()))){
                      Object[] obj = {flightCarrierCode.toUpperCase(),flightValidationVO.getFlightNumber()};
                      ErrorVO err = new ErrorVO("mailtracking.defaults.reassigncontainer.err.flightintbcortba",obj);
                      err.setErrorDisplayType(ErrorDisplayType.ERROR);
                      actionContext.addError(err);
//                      actionContext.target = TARGET;
                       return;
        			}
        			if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus())){
                      Object[] obj = {flightCarrierCode.toUpperCase(),flightValidationVO.getFlightNumber()};
                      ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightcancelled",obj);
                      err.setErrorDisplayType(ErrorDisplayType.ERROR);
                      actionContext.addError(err);
//                      actionContext.target = TARGET;
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
        		}}else {
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
            			actionContext.addAllError(errors);
//            			actionContext.target = TARGET;
            			return;

            		}
            	}
		      }
		    }}
		}
		log.log(Log.FINE, " out formErrors ", formErrors);
		if(formErrors.size()>0){
	    	   actionContext.addAllError(formErrors);
	       }

		if(sameAP == 1){
//			actionContext.target = TARGET;
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
						  actionContext.addError(new ErrorVO("mailtracking.defaults.consignment.mismatchinconnectionflights"));
				  		  actionContext.target = TARGET;
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
	 * @param actionContext
	 * @param consignmentDocumentVO
	 * @param logonAttributes
	 */
	private void validateMailDetails(MaintainConsignmentModel maintainConsignmentModel,
			ConsignmentSession consignmentSession,ActionContext actionContext,
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
			actionContext.addError(new ErrorVO("mailtracking.defaults.consignment.invaliduldnumber",
	   				new Object[]{invalidULDNum.toUpperCase()}));
//	  		actionContext.target = TARGET;
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
		        			 actionContext.addError(new ErrorVO("mailtracking.defaults.sameoe",
				    	   				new Object[]{mailInConVO.getMailId()}));
		        		 }else{
		        			 String pk = new StringBuilder(mailInConVO.getOriginExchangeOffice())
		        							.append(mailInConVO.getDestinationExchangeOffice())
		        							.append(mailInConVO.getMailClass())
		        							.append(mailInConVO.getYear())
		        							.append(mailInConVO.getDsn()).toString();
		        			 actionContext.addError(new ErrorVO("mailtracking.defaults.sameoe",
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
								 actionContext.addError(new ErrorVO("mailtracking.defaults.diffmailclass",
											new Object[]{mailInConVO.getMailId()}));
							 }else{
								 String pk = new StringBuilder(mailInConVO.getOriginExchangeOffice())
												.append(mailInConVO.getDestinationExchangeOffice())
												.append(mailInConVO.getMailClass())
												.append(mailInConVO.getYear())
												.append(mailInConVO.getDsn()).toString();
								 actionContext.addError(new ErrorVO("mailtracking.defaults.diffmailclass",
											new Object[]{pk}));
							 }

			    	    }

	  			}
	  		}
	  	}
		if(sameOE == 1  || diffSC == 1){
//			actionContext.target = TARGET;
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
	  			if(count >= 2){
	  				duplicate = 1;
	  				break;
	  			}
	  			count = 0;
	  		  }
	  		}
	  	  }
	  	}

	  	if(duplicate == 1){
	  		actionContext.addError(new ErrorVO("mailtracking.defaults.consignment.msg.err.duplicatemailbag"));
//	  		actionContext.target = TARGET;
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
	  		actionContext.addError(new ErrorVO("mailtracking.defaults.consignment.duplicatedespatch"));
//	  		actionContext.target = TARGET;
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
	  					actionContext.addError(new ErrorVO("mailtracking.defaults.acceptmail.similarmaildsnkey",new Object[]{dSNKeyMB}));
//	  					actionContext.target = TARGET;
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
	  					actionContext.addError(new ErrorVO("mailtracking.defaults.acceptmail.similarmaildsnkey",new Object[]{dSNKeyMB}));
//	  					actionContext.target = TARGET;
	  					return;
	  			}
	  		}
	  	  }
	  		}
	  	}
	  	if(consignmentDocumentVO.getMailInConsignmentVOs() != null && consignmentDocumentVO.getMailInConsignmentVOs().size() > 0){ 
	  		for(MailInConsignmentVO newMailbag: consignmentDocumentVO.getMailInConsignmentVOs()) {
	  			if(!"D".equals(newMailbag.getOperationFlag())) {
	  				int mailtagLength = newMailbag.getMailId()!=null?newMailbag.getMailId().toString().trim().length():0;
	  				boolean valid=false;
	  				String systemParameterValue=null; 
	  				ArrayList<String> systemParameters = new ArrayList<String>();
	  			    systemParameters.add("mailtracking.defaults.noofcharsallowedformailtag");
	  				Map<String, String> systemParameterMap = null;
					try {
						systemParameterMap = new SharedDefaultsDelegate() 
							     	.findSystemParameterByCodes(systemParameters);
					} catch (BusinessDelegateException e) {
						log.log(Log.FINE, " ****** systemParameterMap***** ", e);
					}
	  				if (systemParameterMap != null) {
	  					systemParameterValue = systemParameterMap.get("mailtracking.defaults.noofcharsallowedformailtag");
	  				}
	  				
					if(mailtagLength ==29){
						valid = true;
	  				}
	  				if(systemParameterValue!=null && !systemParameterValue.equals("NA"))
	  				{
	  				 String[] systemParameterVal = systemParameterValue.split(","); 
	  			        for (String a : systemParameterVal) 
	  			        {
	  			        	if(Integer.valueOf(a)==mailtagLength)
	  			        	{
	  			        		valid=true;
	  			        		break;
	  			        	}
	  			        }
	  				}
	  				if(!valid){
	  					actionContext.addError(new ErrorVO("Invalid Mailbag:"+newMailbag.getMailId().toString()));
	  					return;
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
    /**
     * 
     * @param companyCode
     * @return
     */
    public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		
		try {
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add("mail.operations.mailservicelevels");
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode, fieldValues);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		return oneTimes;
    }
private boolean isValidMailtag(String mailbagId) throws BusinessDelegateException
{
	boolean valid=false;
	int mailtagLength = mailbagId!=null?mailbagId.trim().length():0;
	String systemParameterValue=null; 
	ArrayList<String> systemParameters = new ArrayList<String>();
    systemParameters.add(NO_OF_CHAR_ALLOWED_FOR_MAILTAG);
	Map<String, String> systemParameterMap = new SharedDefaultsDelegate() 
		     	.findSystemParameterByCodes(systemParameters);
	if (systemParameterMap != null) {
		systemParameterValue = systemParameterMap.get(NO_OF_CHAR_ALLOWED_FOR_MAILTAG);
	}
	if(mailtagLength==29 && mailbagId.matches(SPECIAL_CHARACTER_REGEX) ){
		return true;
	}
	if(systemParameterValue!=null && !systemParameterValue.equals("NA"))
	{
	 String[] systemParameterVal = systemParameterValue.split(","); 
        for (String a : systemParameterVal) 
        {
        	if(Integer.valueOf(a)==mailtagLength)
        	{
        		valid=true;
        		break;
        	}
        }
	}
	return valid;
}
private boolean validateMailTagFormat(String mailbagId){
boolean isValid=false;
String mailYr=mailbagId.substring(15,16);
String mailDSN=mailbagId.substring(16,20);
String mailRSN=mailbagId.substring(20,23);
String mailHNI=mailbagId.substring(23,24);
String mailRI=mailbagId.substring(24,25);
String mailWt=mailbagId.substring(25,29);
  if(   mailYr.matches(INT_REGEX) && mailYr.length()==1&&
		mailDSN.matches(INT_REGEX) && mailDSN.length()==4&&
		mailRSN.matches(INT_REGEX) && mailRSN.length()==3&&
		mailHNI.matches(INT_REGEX) && mailHNI.length()==1&&
		mailRI.matches(INT_REGEX) && mailRI.length()==1&&
		mailWt.matches(INT_REGEX) && mailWt.length()==4){
	isValid=true;
}
return isValid;
    }
}
