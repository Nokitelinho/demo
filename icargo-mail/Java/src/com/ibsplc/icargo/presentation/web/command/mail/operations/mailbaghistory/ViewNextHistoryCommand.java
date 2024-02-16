/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbaghistory;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagHistoryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8149 created for ICRD-248207
 */
public class ViewNextHistoryCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ListMailBagHistory");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailbaghistory";
	private static final String MAILBAG_NO_DATA_FOUND = "mailtracking.defaults.mbHistory.msg.err.nodatafound";
	private static final String SUCCESS = "history_success";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.log(Log.FINE, "\n\n in the next list command----------> \n\n");

		MailBagHistoryForm mailBagHistoryForm = (MailBagHistoryForm) invocationContext.screenModel;
		MailBagHistorySession mailBagHistorySession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Collection<ErrorVO> errors = null;
		MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
		String mailBagId="";
		//Added by A-8164 for ICRD-322758 starts
		long mailSeqNumber =0;
		if(mailBagHistoryForm.getDisplayPopupPage()==null||mailBagHistoryForm.getTotalViewRecords()==null||
				mailBagHistoryForm.getDisplayPopupPage().equals("-1")||
					mailBagHistoryForm.getDisplayPopupPage().equals(mailBagHistoryForm.getTotalViewRecords())){
			invocationContext.target = SUCCESS;
			return;
		}
		if(mailBagHistoryForm.isMailbagDuplicatePresent()){
			mailSeqNumber = mailBagHistorySession.getMailBagVos()
					.get(Integer.parseInt(mailBagHistoryForm.getDisplayPopupPage())).getMailSequenceNumber();	
		}
		//Added by A-8164 for ICRD-322758 ends
		else{
			mailSeqNumber = mailBagHistorySession.getMailSequenceNumber().get(
					Integer.parseInt(mailBagHistoryForm.getDisplayPopupPage()));
		}
		Collection<MailbagHistoryVO> mailBagHistoryVOs = new ArrayList<MailbagHistoryVO>();
		try {
			mailBagHistoryVOs = delegate.findMailbagHistories(companyCode,mailBagId,
					mailSeqNumber);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}

		log.log(Log.FINE, "\n\n mailBagHistoryVOs---------->  ",
				mailBagHistoryVOs);
		if (mailBagHistoryVOs == null || mailBagHistoryVOs.size() == 0) {
			ErrorVO errorVO = new ErrorVO(MAILBAG_NO_DATA_FOUND);
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			mailBagHistoryForm.setMailbagId("");
			invocationContext.addAllError(errors);
			invocationContext.target = SUCCESS;
			return;
		}

		for (MailbagHistoryVO mailbagHistoryVO : mailBagHistoryVOs) {
			if (mailbagHistoryVO.getOriginExchangeOffice() != null) {
				String yearPrefix = new LocalDate(
						logonAttributes.getAirportCode(), ARP, false)
						.toDisplayFormat("yyyy").substring(0, 3);

				log.log(Log.FINE, "\n\n yearPrefix---------->  ", yearPrefix);
				mailBagHistoryForm.setOoe(mailbagHistoryVO
						.getOriginExchangeOffice());
				mailBagHistoryForm.setDoe(mailbagHistoryVO
						.getDestinationExchangeOffice());
				mailBagHistoryForm.setCatogory(mailbagHistoryVO
						.getMailCategoryCode());
				mailBagHistoryForm
						.setMailClass(mailbagHistoryVO.getMailClass());
				mailBagHistoryForm.setMailSubclass(mailbagHistoryVO
						.getMailSubclass());
				mailBagHistoryForm.setYear(new StringBuffer(yearPrefix).append(
						mailbagHistoryVO.getYear()).toString());
				mailBagHistoryForm.setDsn(mailbagHistoryVO.getDsn());
				mailBagHistoryForm.setRsn(mailbagHistoryVO.getRsn());
				mailBagHistoryForm.setWeightMeasure(mailbagHistoryVO
						.getWeight());
				mailBagHistoryForm
						.setMailbagId(mailbagHistoryVO.getMailbagId());
				LocalDate reqDeliveryTime = mailbagHistoryVO
						.getReqDeliveryTime();
				if (reqDeliveryTime != null) {
					mailBagHistoryForm.setReqDeliveryTime(reqDeliveryTime
							.toDisplayFormat("dd-MMM-yyyy HH:mm"));
				}
				break;
			}
		}
		if (mailBagHistoryVOs != null && !mailBagHistoryVOs.isEmpty()) {
			MailbagHistoryVO mailbagHistoryVO = mailBagHistoryVOs.iterator()
					.next();
			mailBagHistoryForm
					.setMailRemarks(mailbagHistoryVO.getMailRemarks());
		}

		mailBagHistorySession.setMailBagHistoryVOs(mailBagHistoryVOs);
		mailBagHistoryForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		mailBagHistoryForm.setBtnDisableReq("");
		invocationContext.target = SUCCESS;

		/*
		 * if (errors != null && errors.size() > 0) {
		 * invocationContext.addAllError(errors); }
		 */
	}
}
