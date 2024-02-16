
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.invoicenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.InvoicDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpareporting.InvoicEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.controller.mail.mra.gpareporting.invoicenquiry.UpdateMailStatusCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7929	:	10-Jan-2019:	Draft
 */

public class UpdateMailStatusCommand extends AbstractCommand {

	private Log logger = LogFactory.getLogger("Mail MRA UpdateMailStatusCommand");

	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREEN_ID = "mail.mra.gpareporting.ux.invoicenquiry";
	private static final String RAISE_SUCCESS_MESSAGE = "mail.mra.gpareporting.claimraise";
	private static final String REJECT_SUCCESS_MESSAGE = "mail.mra.gpareporting.rejectstatus";
	private static final String RAISECLAIM_FAILURE_MESSAGE = "mail.mra.gpareporting.claimraisefailure";
	private static final String REJECT_FAILURE_MESSAGE = "mail.mra.gpareporting.rejectfailure";
	private static final String ACCEPT_SUCCESS_MESSAGE = "mail.mra.gpareporting.accept";
	private static final String SHORT_PAID = "SP";
	private static final String OVER_PAID = "OP";
	private static final String SELECT_ROW = "mail.mra.gpareporting.selectrow";
	private static final String ACCEPT_FAILURE_MESSAGE = "mail.mra.gpareporting.acceptfailure";
	private static final String RAISECLAIM_SP_OTHERS_FAILURE_MESSAGE = "mail.mra.gpareporting.claimraisefailureforshortpaidothers";
	
	
	

	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {

		logger.entering("UpdateMailStatusCommand", "execute");

		InvoicEnquiryModel invoicEnquiryModel = (InvoicEnquiryModel) actionContext.getScreenModel();
		ResponseVO responseVO = new ResponseVO();
		ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();

		if (invoicEnquiryModel != null && invoicEnquiryModel.getSelectedInvoicDetails() != null) {

			Collection<InvoicDetails> selectedInvoicDetails = invoicEnquiryModel.getSelectedInvoicDetails();
			Collection<InvoicDetailsVO> invoicDetailsVOs = MailMRAModelConverter
					.constructInvoicDetailVOs(selectedInvoicDetails);

			if(selectedInvoicDetails.isEmpty()){
				ErrorVO error=new ErrorVO(SELECT_ROW);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				actionContext.addError(error);
				return;
			}
			if("Y".equals(invoicEnquiryModel.getRaiseClaimFlag())){
				for(InvoicDetails invoicDetails :selectedInvoicDetails){
					if(invoicDetails.getInvoicPayStatus() != null &&
							!invoicDetails.getInvoicPayStatus().contains(SHORT_PAID)&&
							(!"XXX".equals(invoicDetails.getOrigin()) && !"XXX".equals(invoicDetails.getDestination()))){
						ErrorVO error=new ErrorVO(RAISECLAIM_FAILURE_MESSAGE);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						actionContext.addError(error);
						return;
					}
					else if ("OTH".equals(invoicDetails.getMailbagInvoicProcessingStatus())){
						ErrorVO error=new ErrorVO(RAISECLAIM_SP_OTHERS_FAILURE_MESSAGE);     
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						actionContext.addError(error);
						return;
					}
							
					
				}
				
			}else if ("R".equals(invoicEnquiryModel.getRaiseClaimFlag())){
				for(InvoicDetails invoicDetails :selectedInvoicDetails){
					if(!OVER_PAID.equals(invoicDetails.getInvoicPayStatus())){
						ErrorVO error=new ErrorVO(REJECT_FAILURE_MESSAGE);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						actionContext.addError(error);
						return;
					}
				}
			}
			else{
				for(InvoicDetails invoicDetails :selectedInvoicDetails){
					if(((invoicDetails.getInvoicPayStatus() != null &&
								!invoicDetails.getInvoicPayStatus().equals(SHORT_PAID)) && (!OVER_PAID.equals(invoicDetails.getInvoicPayStatus())) 
							&& (!"DUMMYORG".equals(invoicDetails.getMailbagInvoicProcessingStatus())) &&  (!"DUMMYDST".equals(invoicDetails.getMailbagInvoicProcessingStatus())))|| "AMOTACT".equals(invoicDetails.getMailbagInvoicProcessingStatus())){
						ErrorVO error=new ErrorVO(ACCEPT_FAILURE_MESSAGE);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						actionContext.addError(error);
						return;
					}
							
					
				}
			}
			try {
				MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
				mailTrackingMRADelegate.updateMailStatus(invoicDetailsVOs,invoicEnquiryModel.getRaiseClaimFlag());
			} catch (BusinessDelegateException businessDelegateException) {
				errors = (ArrayList) handleDelegateException(businessDelegateException);
			}

		}

		if (errors != null && errors.size() > 0) {
			actionContext.addAllError(errors);
			return;
		}

		responseVO.setStatus("success");
		if("Y".equals(invoicEnquiryModel.getRaiseClaimFlag())){
			ErrorVO error=new ErrorVO(RAISE_SUCCESS_MESSAGE);
			error.setErrorDisplayType(ErrorDisplayType.INFO);
			actionContext.addError(error);
		
		}else if("R".equals(invoicEnquiryModel.getRaiseClaimFlag())){
			ErrorVO error=new ErrorVO(REJECT_SUCCESS_MESSAGE);
			error.setErrorDisplayType(ErrorDisplayType.INFO);
			actionContext.addError(error);
		}
		else{
			ErrorVO error=new ErrorVO(ACCEPT_SUCCESS_MESSAGE);
			error.setErrorDisplayType(ErrorDisplayType.INFO);
			actionContext.addError(error);
		}
		
		ArrayList<InvoicEnquiryModel> invoicEnquiryModels=
				new ArrayList<InvoicEnquiryModel>();  
		invoicEnquiryModels.add(invoicEnquiryModel);
		responseVO.setResults(invoicEnquiryModels);
		actionContext.setResponseVO(responseVO);
		logger.exiting("UpdateMailStatusCommand", "execute");

	}

}
