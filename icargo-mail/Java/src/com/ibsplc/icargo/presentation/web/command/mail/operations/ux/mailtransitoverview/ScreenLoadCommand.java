package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailtransitoverview;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailTransitModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailTransitFilter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

public class ScreenLoadCommand extends AbstractCommand{

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException{
		
		LogonAttributes logonAttributes = (LogonAttributes)getLogonAttribute();
		MailTransitFilter mailTransitFilter = new MailTransitFilter();
		MailTransitModel mailTransitModel = (MailTransitModel)actionContext.getScreenModel();
		
		mailTransitFilter.setAirportCode(logonAttributes.getAirportCode());
		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);	
		mailTransitFilter.setFromDate(LocalDate.CALENDAR_DATE_FORMAT);
		mailTransitFilter.setToDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
		
		
		mailTransitModel.setMailTransitFilter(mailTransitFilter);
		
		ResponseVO responseVO = new ResponseVO();
		List<MailTransitModel> results = new ArrayList<>();
		results.add(mailTransitModel);
		responseVO.setResults(results);
		actionContext.setResponseVO(responseVO); 
		
		
	}
}
