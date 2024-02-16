/**
 * 
 */
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
 * @author A-5219
 *
 */
public class ReProcessCommand  extends AbstractCommand{

	 private Log log = LogFactory.getLogger("Mail MRA gpareporting invoic enquiry");
		
	 public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
			
			log.entering("SaveClaimDetailsCommand","execute");
			
			InvoicEnquiryModel invoicEnquiryModel = (InvoicEnquiryModel) actionContext.getScreenModel();
			ResponseVO responseVO = new ResponseVO();
			ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();	
			String status = "";
			
			if (invoicEnquiryModel != null && invoicEnquiryModel.getSelectedInvoicDetails()!= null) {
				
				log.log(Log.FINE, "invoicEnquiryModel.getSelectedInvoicDetails() not null");
				Collection<InvoicDetails> selectedInvoicDetails = invoicEnquiryModel.getSelectedInvoicDetails();
				Collection<InvoicDetailsVO> invoicDetailsVOs = MailMRAModelConverter.constructInvoicDetailVOs(selectedInvoicDetails);
		        
				try {
					MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
					status = mailTrackingMRADelegate.reprocessInvoicMails(invoicDetailsVOs);
				} catch (BusinessDelegateException businessDelegateException) {
					errors = (ArrayList) handleDelegateException(businessDelegateException);
				}

			}
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError(errors);
				return;
			}

			responseVO.setStatus("success");
			
			ErrorVO error = new ErrorVO("mail.mra.gpareporting.err.savesuccess");
			error.setErrorDisplayType(ErrorDisplayType.INFO);
			actionContext.addError(error);
			
			actionContext.setResponseVO(responseVO);
			log.exiting("SaveClaimDetailsCommand", "execute");
			
		}
	 
	 
}
