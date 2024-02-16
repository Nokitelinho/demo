package com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.ux.mailawbbooking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.xaddons.bs.mail.operations.MailAwbBookingPopupModel;
import com.ibsplc.icargo.presentation.web.model.xaddons.bs.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.xaddons.bs.mail.operations.ux.mailbooking.MailBookingPopupScreenloadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	30-Sep-2019		:	Draft
 */
public class MailBookingPopupScreenloadCommand extends AbstractCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	private static final String MODULE_NAME = "bsmail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailawbbooking";
	private static final String KEY_SHIPMENT_ONETIME = "operations.shipment.shipmentstatus";
	
	public void execute(ActionContext actionContext) {
		
		log.entering("MailBookingPopupScreenloadCommand","execute");
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute();
		MailAwbBookingPopupModel mailAwbBookingPopupModel=
				(MailAwbBookingPopupModel) actionContext.getScreenModel(); 
		Map<String, Collection<OneTimeVO>>oneTimeValues = null;
		String companyCode = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ResponseVO responseVO = new ResponseVO();
		List<MailAwbBookingPopupModel> results = new ArrayList<MailAwbBookingPopupModel>();

		companyCode = logonAttributes.getCompanyCode();

		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(KEY_SHIPMENT_ONETIME);
		
		try {
			oneTimeValues = new SharedDefaultsDelegate()
									.findOneTimeValues(companyCode, oneTimeList);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		if(oneTimeValues != null){
			mailAwbBookingPopupModel.setOneTimeValues(MailOperationsModelConverter.constructOneTimeValues(oneTimeValues));
		}
		results.add(mailAwbBookingPopupModel);
		responseVO.setResults(results);
		actionContext.setResponseVO(responseVO);

        log.exiting("MailBookingPopupScreenloadCommand","execute");
		
	}

}
