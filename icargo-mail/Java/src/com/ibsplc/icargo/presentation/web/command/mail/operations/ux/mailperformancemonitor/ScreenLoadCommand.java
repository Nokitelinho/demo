package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformancemonitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import static com.ibsplc.icargo.framework.util.time.Location.ARP;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailPerformanceMonitorModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailMonitoringFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ScreenLoadCommand extends AbstractCommand{

	private Log log = LogFactory.getLogger("OPERATIONS CARDITENQUIRY NEO");
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
			CommandInvocationException {
		this.log.entering("ScreenLoadCommand", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
		MailPerformanceMonitorModel performanceModel = (MailPerformanceMonitorModel)actionContext.getScreenModel();
	    SharedDefaultsDelegate sharedDefaultsDelegate = 
	    	      new SharedDefaultsDelegate();
	     Map<String, Collection<OneTimeVO>> oneTimeValues = null;
	     try
	    	 {
	    	      oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
	    	        logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
	    	    }
	    	    catch (BusinessDelegateException e)
	    	    {
	    	      actionContext.addAllError(handleDelegateException(e));
	    	    }
	     this.log.log(5, new Object[] { "oneTimeValues ---> ", oneTimeValues });
	     this.log.log(5, new Object[] { "LoginAirport ---> ", logonAttributes.getAirportCode() });
	     LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,true);	
	     MailMonitoringFilter filter = new MailMonitoringFilter();
	     filter.setStation(logonAttributes.getAirportCode());
	     filter.setFromDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
	     filter.setToDate(date.toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
	     performanceModel.setMailMonitorFilter(filter);
	     performanceModel.setOneTimeValues(MailOperationsModelConverter.constructOneTimeValues(oneTimeValues));
	     ResponseVO responseVO = new ResponseVO();
	     List<MailPerformanceMonitorModel> results = new ArrayList();
	     results.add(performanceModel);
	     responseVO.setResults(results);
	     //responseVO.setStatus("_default_render_success");
	     actionContext.setResponseVO(responseVO);
	     this.log.exiting("ScreenLoadCommand", "execute");
	}
	  private Collection<String> getOneTimeParameterTypes()
	  {
	    Collection<String> parameterTypes = new ArrayList();
	    parameterTypes.add("mail.operations.mailservicelevels");
	    parameterTypes.add("mailtracking.defaults.mailstatus");
	  	return parameterTypes;
	  }



}

