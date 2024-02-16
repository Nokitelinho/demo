package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.ux.billingScheduleMaster;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailRuleConfigVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.BillingScheduleDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.BillingScheduleMasterModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-9498
 *
 */
public class SaveCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("OPERATIONS MAIL");
	private static final String SUCCESS_MESSAGE = "Saved Successfully";
	private static final String SUCCESS = "success";

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {

		this.log.entering("SaveCommand", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
		BillingScheduleMasterModel billingScheduleMasterModel = (BillingScheduleMasterModel) actionContext
				.getScreenModel(); // Model
		List<BillingScheduleDetails> mailBillingList = billingScheduleMasterModel.getBillingScheduleDetailList();
		List<ErrorVO> errorVOs = null;
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		if (!mailBillingList.isEmpty()) {
			for (BillingScheduleDetails billingScheduleModel : mailBillingList) {
				BillingScheduleDetailsVO billingScheduleDetailsVO=MailMRAModelConverter.constructBillingScheduleDetailsModels(billingScheduleModel);
				billingScheduleDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
				try {
					mailTrackingMRADelegate.saveBillingSchedulemaster(billingScheduleDetailsVO);
				} catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
					errorVOs = handleDelegateException(businessDelegateException);
					actionContext.addAllError(errorVOs);
					return;
				}
			}
		}

		ResponseVO responseVO = new ResponseVO();
		ArrayList<BillingScheduleMasterModel> results = new ArrayList<>();
		results.add(billingScheduleMasterModel);
		responseVO.setResults(results);
		responseVO.setStatus(SUCCESS);
		ErrorVO error = new ErrorVO(SUCCESS_MESSAGE);
		error.setErrorDisplayType(ErrorDisplayType.INFO);
		actionContext.addError(error);
		actionContext.setResponseVO(responseVO);
		log.exiting("SaveCommand", "execute");

	}
}