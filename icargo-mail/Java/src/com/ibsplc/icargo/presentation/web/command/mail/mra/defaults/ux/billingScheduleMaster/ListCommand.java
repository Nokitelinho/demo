package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.ux.billingScheduleMaster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.BillingScheduleDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.BillingScheduleFilterModel;
import com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.BillingScheduleMasterModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-9498
 *
 */
public class ListCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("MRA MAIL");

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {

		this.log.entering("ListCommand", "execute");
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		BillingScheduleMasterModel billingScheduleMasterModel = (BillingScheduleMasterModel) actionContext
				.getScreenModel();
		BillingScheduleFilterVO billingScheduleFilterVO = new BillingScheduleFilterVO();
		List<ErrorVO> warningErrors = new ArrayList<ErrorVO>();
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		billingScheduleFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		billingScheduleFilterVO.setBillingType(billingScheduleMasterModel.getBillingScheduleFilter().getBillingType());
		billingScheduleFilterVO
				.setBillingPeriod(billingScheduleMasterModel.getBillingScheduleFilter().getBillingPeriod());
		billingScheduleFilterVO.setYear(billingScheduleMasterModel.getBillingScheduleFilter().getYear());
		int totalRecords=Integer.parseInt(billingScheduleMasterModel.getBillingScheduleFilter().getPageSize());  
		billingScheduleFilterVO.setTotalRecordCount(totalRecords);
		Collection<BillingScheduleDetailsVO> billingScheduleDetailsVOs = null;
		String displayPage = billingScheduleMasterModel.getBillingScheduleFilter().getDisplayPage();
		int pageNumber = Integer.parseInt(displayPage);
		billingScheduleDetailsVOs = mailTrackingMRADelegate.findBillingType(billingScheduleFilterVO, pageNumber);
		if (billingScheduleDetailsVOs == null) {
			ErrorVO warningError = new ErrorVO("Mail Billing Details does not exist. Do you want to create a new one?");
			warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
			warningErrors.add(warningError);
			actionContext.addAllError(warningErrors);
			return;
			
		} else {
			
			ArrayList<BillingScheduleDetails> billingScheduleMaster = MailMRAModelConverter
					.constructBillingScheduleDetailsVOs(billingScheduleDetailsVOs);
			ResponseVO responseVO = new ResponseVO();
			ArrayList<BillingScheduleMasterModel> results = new ArrayList<BillingScheduleMasterModel>();
			PageResult<BillingScheduleDetails> pageList = new PageResult<BillingScheduleDetails>(
					mailTrackingMRADelegate.findBillingType(billingScheduleFilterVO, pageNumber),
					billingScheduleMaster);
			billingScheduleMasterModel.setBillingScheduleMasterPage(pageList);
			results.add(billingScheduleMasterModel);
			responseVO.setResults(results);
			actionContext.setResponseVO(responseVO);
		}

		log.exiting("ListCommand", "execute");
	}
}
