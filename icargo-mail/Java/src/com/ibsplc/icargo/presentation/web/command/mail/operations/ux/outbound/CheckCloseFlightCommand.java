package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailAcceptance;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.icargo.presentation.web.model.operations.flthandling.common.FlightValidation;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class CheckCloseFlightCommand extends AbstractCommand{
	private Log log = LogFactory.getLogger("OPERATIONS MAIL OUTBOUND NEO");
	 private static final String CONST_FLIGHT = "F";
	 private static final String MODIFY_MAILBAG="MODIFY_MAILBAG";
	 private static final String MODIFY = "MODIFY";
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
			CommandInvocationException {
		
	    	LogonAttributes logonAttributes = getLogonAttribute();
			String cmpcod = logonAttributes.getCompanyCode();
		    OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
		    MailAcceptance mailAcceptance = outboundModel.getMailAcceptance();
		    OperationalFlightVO operationalFlightVO = MailOutboundModelConverter.constructOperationalFlightVO(mailAcceptance, logonAttributes);
		    
		    String flightcarrierFlag = outboundModel.getFlightCarrierflag();
	    	List<ErrorVO> errors = new ArrayList<ErrorVO>();
	    	MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
	    	
	    	MailAcceptanceVO mailAcceptanceVO =  MailOutboundModelConverter.constructMailAcceptanceVO(mailAcceptance, logonAttributes);

	    	boolean isMailbagNotAvlInCurPort=false;
	    	  
	    	//String assignTo = flightcarrierfilter.getAssignTo();
		   // log.log(Log.FINE, "assignTo ===", assignTo);
			//String[] primaryKey = mailAcceptanceForm.getSelectMail();
	    	if(MODIFY_MAILBAG.equals(outboundModel.getActionType())){
	    		isMailbagNotAvlInCurPort=validateMailbagToModify(outboundModel,logonAttributes);
	    	}
		    
		    if(CONST_FLIGHT.equalsIgnoreCase(flightcarrierFlag)){
	    	
		    boolean isFlightClosed = false;
	    	try {
	    		
	    		isFlightClosed = mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(operationalFlightVO);
			  
				
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError(errors);
				//invocationContext.target = TARGET;
				//mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				//mailAcceptanceForm.setCloseflight("N");
				return;
			}
			log.log(Log.INFO, "isFlightClosed:------------>>", isFlightClosed);
			if (isFlightClosed) {
				isMailbagNotAvlInCurPort=true;
				if(!MODIFY_MAILBAG.equals(outboundModel.getActionType())){
				Object[] obj = {mailAcceptanceVO.getFlightCarrierCode(),
						mailAcceptanceVO.getFlightNumber(),
						mailAcceptanceVO.getFlightDate().toString().substring(0,11)};
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.flightclosed",obj);
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				actionContext.addAllError(errors);
				//mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
				//mailAcceptanceForm.setDisableSaveFlag("Y");
				//mailAcceptanceForm.setCloseflight("Y");
				//invocationContext.target = TARGET;
				return;				
			} 
			} 
			
			/**
			 * Added for CR SAA 410 STARTS
			 */
			FlightValidation flightValidationVO = outboundModel.getFlightValidation();
			if(flightValidationVO != null){
				/*if(flightValidationVO.isTBADueRouteChange()){
					Object[] obj = {mailAcceptanceVO.getFlightCarrierCode(),
							mailAcceptanceVO.getFlightNumber(),
							mailAcceptanceVO.getFlightDate().toString().substring(0,11)};
					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailacceptance.msg.err.flighttobeactioned",obj);
					errors = new ArrayList<ErrorVO>();
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					actionContext.addAllError(errors);
					//invocationContext.target = TARGET;
					return;
				}*/
			}
			
			/**
			 * Added for CR SAA 410 ENDS
			 */
			
			
		    }
		    
		    /**
		     * To check for arrived mailbags not to be accepted
		     */
		    ArrayList<ContainerDetails> contDetails = outboundModel.getContainerDetailsCollection();
		    if(contDetails!=null) {
		    	for (ContainerDetails containerDetails:contDetails) {
		    		
		    	
		   // commented on 24july19 ContainerDetails containerDetails = contDetails.get(0);
			//ContainerDetailsVO containerDetailsVO = MailOutboundModelConverter.constructContainerDetailsVO(containerDetails);
		
		//	Collection<ContainerDetailsVO> contDetailsVOs = outboundModel.getContainerDetailsCollection()
		  //  int count = 1;
		  //  if(primaryKey != null){
	      //  int primaryKeyLen = primaryKey.length;
	      //  int errorArrived = 0;
	      //  String contArrived = "";
	     //  if (contDetailsVOs != null && contDetailsVOs.size() != 0) {
	       	//for (ContainerDetailsVO contDetailsVO : contDetailsVOs) {
	       		//String primaryKeyFromVO = contDetailsVO.getCompanyCode()
	       		//		+String.valueOf(count);
	       		//if ((cnt < primaryKeyLen) && (primaryKeyFromVO.trim()).
	       				    //      equalsIgnoreCase(primaryKey[cnt].trim())) {
	       			//if("Y".equals(containerDetails.getArrivedStatus())){
	       			//	errorArrived = 1;
	       			//	if("".equals(contArrived)){
	       				//	contArrived = contDetailsVO.getContainerNumber();
		       			//}else{
		       			//	contArrived = new StringBuilder(contArrived)
	   					//                  .append(",")
	   					//                  .append(contDetailsVO.getContainerNumber())
	   					//                  .toString();	
		       			//}
	       			//}
	       		//	cnt++;
	       		//}
	       		//count++;
	       	//  }
	       //	}
	       		if("Y".equals(containerDetails.getArrivedStatus())&&!MODIFY_MAILBAG.equals(outboundModel.getActionType())&&!MODIFY.equals(outboundModel.getActionType())){
	  	    	actionContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.alreadyarrived",new Object[]{containerDetails.getContainerNumber()}));
	  	    	//mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	  	    	//mailAcceptanceForm.setCloseflight("Y");
				//invocationContext.target = TARGET;
				return;		
	       	 }
	       }
		   }
		    
		    ResponseVO responseVO = new ResponseVO();
			ArrayList<OutboundModel> result = new ArrayList<OutboundModel>();
			if(MODIFY_MAILBAG.equals(outboundModel.getActionType())){
				outboundModel.setDisableForModify(isMailbagNotAvlInCurPort);
	    	}
			result.add(outboundModel);
			responseVO.setResults(result);
			responseVO.setStatus("success");
			actionContext.setResponseVO(responseVO);
	    	log.exiting("validateAttachRoutingCommand","execute");
	}  
	private boolean validateMailbagToModify(OutboundModel outboundModel,LogonAttributes logonAttributes) {
		boolean isMailbagNotAvl=false;
		if (outboundModel.getExistingMailbags() != null && !outboundModel.getExistingMailbags().isEmpty()) {
			for (Mailbag mailbagDetail : outboundModel.getExistingMailbags()) {
				if(!logonAttributes.getAirportCode().equals(mailbagDetail.getScannedPort())){
					isMailbagNotAvl=true;
				}
			}
	  }  
		return isMailbagNotAvl;
	}
	    }
	       
	


