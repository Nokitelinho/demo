package com.ibsplc.icargo.presentation.web.command.mail.operations.national.maintainconsignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.CaptureMailDocumentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SaveCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("MAILTRACKING");

	/**
	 * TARGET
	 */
	private static final String TARGET = "success";

	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.national.consignment";
	private static final String VALIDATEACCP = "Y";
	private static final String TARGETFAILURE = "failure";



	@Override
	public boolean breakOnInvocationFailure() {
		
		return true;
	}



	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		ConsignmentSession consignmentSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);

		ConsignmentDocumentVO consignmentDocumentVO = null;
		List<RoutingInConsignmentVO> routingInConsignmentVOs = null;
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		consignmentDocumentVO = consignmentSession.getConsignmentDocumentVO();
		

		CaptureMailDocumentForm consignmentForm  = (CaptureMailDocumentForm)invocationContext.screenModel;
		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		ConsignmentDocumentVO newConsignmentDocumentVO = null;
		String operationFlag = consignmentDocumentVO.getOperationFlag();
		//Added as part if icrd-15420 by A-4810
		//if errors are there as part of validation, do not proceed
		 if(invocationContext.getErrors() != null && invocationContext.getErrors().size()>0){
			consignmentSession.setConsignmentDocumentVO(consignmentDocumentVO);
			invocationContext.target = TARGET;
			return;
		}
		//Added as part if icrd-15420 by A-4810
		 //if no errors are there, show warning to indicate acceptance details present
		 if( "Y".equals(consignmentForm.getDeleteFlag())) {
				if(!VALIDATEACCP.equals(consignmentForm.getValidateAcceptance())){
					consignmentForm.setValidateAcceptance("N");
					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.acceptanceinfopresent");
					errorVOs.add(errorVO);
					
					}
			      }
			
			    if(errorVOs!=null && errorVOs.size()>0 ){
				invocationContext.addAllError(errorVOs);
				invocationContext.target = TARGET;
				return;
				}
		 
	
		
	//	updateConsignmentDocument(consignmentDocumentVO);
		try{
			newConsignmentDocumentVO = (ConsignmentDocumentVO)	mailTrackingDefaultsDelegate.saveNationalConsignmentDetails(consignmentDocumentVO);
		}catch(BusinessDelegateException businessDelegateException){
			errorVOs = handleDelegateException(businessDelegateException);
		}
        
        if(errorVOs == null || errorVOs.size() ==0){
        	if("I".equals(operationFlag)){
        	ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.consignmentNumbergenerated");
        	errorVO.setErrorData(new Object[]{newConsignmentDocumentVO.getConsignmentNumber()});
        	errorVOs.add(errorVO);
        	}else if("U".equals(operationFlag)){
        		ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.consignmentNumberupdated");
            	errorVO.setErrorData(new Object[]{newConsignmentDocumentVO.getConsignmentNumber()});
            	errorVOs.add(errorVO);	
        	}else if("D".equals(operationFlag)){
        		ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.consignmentNumberdeleted");
            	errorVO.setErrorData(new Object[]{newConsignmentDocumentVO.getConsignmentNumber()});
            	errorVOs.add(errorVO);
        	}
        	consignmentForm.setConDocNo(newConsignmentDocumentVO.getConsignmentNumber());
        }

       if(errorVOs!= null && errorVOs.size()>0){
    	   invocationContext.addAllError(errorVOs);
       }




		invocationContext.target = TARGET;
		//consignmentForm.setSaveSuccessFlag("Y");
	}
	
	/**
	 * 
	 * @param consignmentDocumentVO
	 */
	private void updateConsignmentDocument(ConsignmentDocumentVO consignmentDocumentVO){
		if(consignmentDocumentVO != null){
			Collection<RoutingInConsignmentVO> routingInConsignmentVOsToRemove = new ArrayList<RoutingInConsignmentVO>();
			Collection<RoutingInConsignmentVO> acceptanceConsignmentVOsToRemove = new ArrayList<RoutingInConsignmentVO>();
			
			/**
			 * This block removes the invalid entries (the entries not needed for saving) from the routing collection
			 */
			if(consignmentDocumentVO.getRoutingInConsignmentVOs() != null && consignmentDocumentVO.getRoutingInConsignmentVOs().size() >0){
				for(RoutingInConsignmentVO routingInConsignmentVO : consignmentDocumentVO.getRoutingInConsignmentVOs()){
					if(routingInConsignmentVO.isInvalidFlightFlag() || RoutingInConsignmentVO.FLAG_NO.equals(routingInConsignmentVO.getOperationFlag())){
						routingInConsignmentVOsToRemove.add(routingInConsignmentVO);
					}
				}
				if(routingInConsignmentVOsToRemove != null && routingInConsignmentVOsToRemove.size() >0){
					//consignmentDocumentVO.getRoutingInConsignmentVOs().removeAll(routingInConsignmentVOsToRemove);
				}
			}
			
			/**
			 * 
			 * This block removes the invalid entries (the entries not needed for saving) from the acceptance collection
			 */
			
			if(consignmentDocumentVO.getAcceptanceInfo() != null && consignmentDocumentVO.getAcceptanceInfo().size() >0){
			   for(RoutingInConsignmentVO routingInConsignmentVO : consignmentDocumentVO.getAcceptanceInfo()){
				   if(routingInConsignmentVO.isInvalidFlightFlag() || routingInConsignmentVO.isOffloadFlag() || RoutingInConsignmentVO.FLAG_NO.equals(routingInConsignmentVO.getOperationFlag())){
					   acceptanceConsignmentVOsToRemove.add(routingInConsignmentVO);
				   }				   
			   }
			  if(acceptanceConsignmentVOsToRemove != null && acceptanceConsignmentVOsToRemove.size() >0){
				  consignmentDocumentVO.getAcceptanceInfo().removeAll(acceptanceConsignmentVOsToRemove);
			  } 
				
			}

		}
	}



}



