package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.listinvoic;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ListInvoicForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.ListInvoicSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;

public class ScreenLoadCommand  extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Mail Mra Invoic Listing ");
	private static final String TARGET = "screenload_success";
	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREENID = "mail.mra.gpareporting.ux.listinvoic";
	private static final String STATUS_ONETIME="mailtracking.mra.gpareporting.invoicstatus";
	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ScreenloadCommand of List invoic", "execute");
		ListInvoicForm listInvoicForm = (ListInvoicForm) invocationContext.screenModel;
		invocationContext.target = TARGET;
		ListInvoicSession listinvoicsession =getScreenSession(MODULE_NAME,SCREENID);
		
		   LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		   InvoicVO invoicvo=null;
		    String companyCode = logonAttributes.getCompanyCode();
		    String stationCode = logonAttributes.getStationCode();
		    String userId = logonAttributes.getUserId();
		this.log.log(3, new Object[] { "companyCode =  ", companyCode });
		    this.log.log(3, new Object[] { "stationCode =  ", stationCode });
		    this.log.log(3, new Object[] { "userId =  ", userId });
		    listInvoicForm.setCompanycode(companyCode);
		    Map <String, Collection<OneTimeVO>>oneTimeStatus = getOneTimeValues(companyCode);
		    String defaultSize = "10";
		
			listInvoicForm.setPaCode("");
			listInvoicForm.setFromDate("");
			listInvoicForm.setToDate("");
			listInvoicForm.setTotalRecords("0");
			listInvoicForm.setFileName("");
			listInvoicForm.setInvoicRefId("");
			listInvoicForm.setDefaultPageSize(defaultSize);
			Collection<OneTimeVO> statusOneTime = (Collection<OneTimeVO>) oneTimeStatus
					.get(STATUS_ONETIME);
			if (statusOneTime != null) {
				log.log(Log.INFO, "Sizeee----", statusOneTime.size());
				for (OneTimeVO list : statusOneTime) {
					log.log(Log.INFO, "LIST----------", list.getFieldDescription());
				}
			}
			listinvoicsession.setStatus((ArrayList<OneTimeVO>)statusOneTime);
			listinvoicsession.setTotalRecords(Integer.parseInt(listInvoicForm.getTotalRecords()));
			listinvoicsession.setFilterParamValues(null);
			listinvoicsession.setListinvoicvos(null);
		log.exiting("ScreenloadCommand","execute");
	}
	private Map<String, Collection<OneTimeVO>> getOneTimeValues(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimeValues = new HashMap<String, Collection<OneTimeVO>>();
	    Collection <String> oneTimeList = new ArrayList<String>();
	    oneTimeList.add(STATUS_ONETIME); 
	
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try {

			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		return  oneTimeValues;
	}
	


}