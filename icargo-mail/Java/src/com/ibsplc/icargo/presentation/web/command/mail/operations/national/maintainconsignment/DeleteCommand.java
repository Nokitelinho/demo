/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.maintainconsignment;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.CaptureMailDocumentForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-3817
 *
 */
public class DeleteCommand extends BaseCommand {
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.national.consignment";
	private static final String TARGET = "success";
	private static final String VALIDATEACCP = "Y";
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		CaptureMailDocumentForm consignmentForm = (CaptureMailDocumentForm) invocationContext.screenModel;
		ConsignmentSession consignmentSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		ConsignmentDocumentVO consignmentDocumentVO = consignmentSession.getConsignmentDocumentVO();
		//Added by A-4810 for icrd-15420
		//check which will by pass after showing warning for deletion 
		
		consignmentForm.setDeleteFlag("");
		if(!VALIDATEACCP.equals(consignmentForm.getValidateAcceptance())){
		consignmentForm.setDeleteFlag("Y");
		validateConsignmentDetailsForClosedFlight(consignmentDocumentVO, errorVOs);
		if(errorVOs != null && errorVOs.size() >0){				
			invocationContext.addAllError(errorVOs);
			invocationContext.target = TARGET;
			return;
		}
		validateConsignmentDetailsForArrivalDetails(consignmentDocumentVO,errorVOs);
		
		if(errorVOs != null && errorVOs.size() >0){				
			invocationContext.addAllError(errorVOs);
			invocationContext.target = TARGET;
			return;
		}
		}
		consignmentDocumentVO.setOperationFlag(ConsignmentDocumentVO.OPERATION_FLAG_DELETE);
		if(consignmentDocumentVO != null ){

			if(consignmentDocumentVO.getRoutingInConsignmentVOs()!= null && consignmentDocumentVO.getRoutingInConsignmentVOs().size() >0){
				for(RoutingInConsignmentVO  routingInConsignmentVO :  consignmentDocumentVO.getRoutingInConsignmentVOs()){
					routingInConsignmentVO.setOperationFlag(consignmentDocumentVO.OPERATION_FLAG_DELETE);
				}

			}
			if(consignmentDocumentVO.getMailInConsignmentVOs() != null && consignmentDocumentVO.getMailInConsignmentVOs().size() >0){
				for(MailInConsignmentVO  mailInConsignmentVO : consignmentDocumentVO.getMailInConsignmentVOs()){
					mailInConsignmentVO.setOperationFlag(consignmentDocumentVO.OPERATION_FLAG_DELETE);

				}

			}
			if(consignmentDocumentVO.getAcceptanceInfo() != null && consignmentDocumentVO.getAcceptanceInfo().size() >0){
				for(RoutingInConsignmentVO routingInConsignmentVO : consignmentDocumentVO.getAcceptanceInfo()){
					routingInConsignmentVO.setOperationFlag(consignmentDocumentVO.OPERATION_FLAG_DELETE);
				}

			}



		}
//		try{
//			new MailTrackingDefaultsDelegate().saveNationalConsignmentDetails(consignmentDocumentVO);
//		}catch(BusinessDelegateException businessDelegateException){
//			errorVOs = handleDelegateException(businessDelegateException);
//			
//		}
		invocationContext.addAllError(errorVOs);
		invocationContext.target = TARGET;
		
	}
	/**
	 * @author A-4810
	 * @param consignmentDocumentVO
	 * @param errorVOs
	 * added to validate whether arrival details are cpatured for the consignment for icrd-15420.
	 */
	private void validateConsignmentDetailsForArrivalDetails(
			ConsignmentDocumentVO consignmentDocumentVO,
			Collection<ErrorVO> errorVOs) {
		int recievedbags = 0;
		if(consignmentDocumentVO.getAcceptanceInfo() != null && consignmentDocumentVO.getAcceptanceInfo().size() >0){
			for(RoutingInConsignmentVO routingInConsignmentVO : consignmentDocumentVO.getAcceptanceInfo()){
				recievedbags = recievedbags+routingInConsignmentVO.getRecievedNoOfPieces();
				
			 }
			}
		
		if(recievedbags>0){
			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.arrivalDetailsPresentForConsignment");
			errorVOs.add(errorVO);
		}
		
	}
	/**
	 * 
	 * @param consignmentDocumentVO
	 * @param errorVOs
	 */
	private void validateConsignmentDetailsForClosedFlight(ConsignmentDocumentVO consignmentDocumentVO,Collection<ErrorVO> errorVOs){
		if(consignmentDocumentVO.getAcceptanceInfo() != null && consignmentDocumentVO.getAcceptanceInfo().size() >0){
			for(RoutingInConsignmentVO routingInConsignmentVO : consignmentDocumentVO.getAcceptanceInfo()){
				if(routingInConsignmentVO.isFlightClosed()){
					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.national.maintainconsignment.flightClosedForOperations");
					//routingInConsignmentVO.setInvalidFlightFlag(true);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					Object[] obj = {
							routingInConsignmentVO
							.getOnwardCarrierCode(),
							routingInConsignmentVO
							.getOnwardFlightNumber(),
							routingInConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat(),
							routingInConsignmentVO.getPol(),
							routingInConsignmentVO.getPou() };
					errorVO.setErrorData(obj);
					errorVOs.add(errorVO);
					
				}
				
			}
			
		}
	}
	

}
