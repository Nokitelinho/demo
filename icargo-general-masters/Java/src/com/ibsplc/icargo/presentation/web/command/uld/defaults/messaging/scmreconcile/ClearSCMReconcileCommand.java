/* ClearSCMReconcileCommand.java Created on Aug 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmreconcile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.ibase.util.time.TimeConvertor;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMReconcileSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMReconcileForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * 
 * @author A-1496
 * 
 */
public class ClearSCMReconcileCommand extends BaseCommand {
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID = "uld.defaults.scmreconcile";

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String CLEAR_FAILURE = "list_failure";

	private static final String BLANK = "";

	private static final String MESSAGE_STATUS = "uld.defaults.uldmessagesendflag";
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		SCMReconcileForm scmReconcileForm = (SCMReconcileForm) invocationContext.screenModel;
		SCMReconcileSession scmReconcileSession = (SCMReconcileSession) getScreenSession(
				MODULE, SCREENID);
		// Added for getting one time values Author A-5125(Chandra Mouli S) for ICRD-24635 
		Map<String, Collection<OneTimeVO>> oneTimeCollection = fetchScreenLoadDetails(logonAttributes
				.getCompanyCode());
		Collection<OneTimeVO> statusValues = oneTimeCollection.get(MESSAGE_STATUS);
		scmReconcileForm.setAbsoluteIndex(BLANK);
		scmReconcileForm.setAirport(BLANK);
		scmReconcileForm.setCurrentPageNum("1");
		scmReconcileForm.setDisplayPage("1");
		scmReconcileForm.setLastPageNumber("0");
		//added for ICRD-24635 author A-5125
		//	scmReconcileForm.setStockChkdate(BLANK);
		scmReconcileForm.setTotalRecords(BLANK);
		//added for ICRD-24635 author A-5125
		//	scmReconcileForm.setScmStockCheckTime(BLANK);
		scmReconcileForm.setSeqNo("");
		//Added for Bug ID:106788 by Vivek.Perla on 22Feb2011
		scmReconcileForm.setAirline(BLANK);
		scmReconcileSession.setIndexMap(null);
		scmReconcileSession.setPageUrl(BLANK);
		scmReconcileSession.setSCMReconcileVOs(null);
		scmReconcileSession.setMessageFilterVO(null);
		
		scmReconcileForm.setAirport(logonAttributes.getAirportCode());
		scmReconcileForm.setScmDisable("GHA");   
		scmReconcileForm.setListStatus("");
		scmReconcileForm.setListflag("");
		//Commented below line by A-5173 for ICRD-25668
		//scmReconcileSession.removeAllAttributes();
		LocalDate stockDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true);
		String stockDateString = TimeConvertor.toStringFormat(stockDate.toCalendar(),
		"dd-MMM-yyyy");
		//added author A-5125 for ICRD-24635 
		String stockCheckTim=stockDate.toDisplayFormat("HH:mm");
		scmReconcileForm.setScmStockCheckTime(stockCheckTim);
		scmReconcileForm.setStockChkdate(stockDateString);
		scmReconcileSession.setMessageStatus((ArrayList<OneTimeVO>) statusValues);
		scmReconcileForm.setScmStockCheckTime(stockCheckTim);
		//ends A-5125
	   	// Added by Sreekumar S as a part of defaulting airline code in page (ANACR - 1471)
      	Collection<ErrorVO> error = new ArrayList<ErrorVO>();
    	//commented by nisha on 29Apr08
      	//String airlineCode = "";
    /*	try {
			airlineCode = new ULDDefaultsDelegate()
					.findDefaultAirlineCode(logonAttributes.getCompanyCode(),logonAttributes.getUserId());
		} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
			error = handleDelegateException(businessDelegateException);
		}
		if(airlineCode != null && airlineCode.trim().length() > 0){
			scmReconcileForm.setAirline(airlineCode);
		}
		else{*/
			if(logonAttributes.isAirlineUser()){
	    		//Commented for Bug ID:106788 by Vivek.Perla on 22Feb2011
				//scmReconcileForm.setAirline(logonAttributes.getOwnAirlineCode());
	    	}
		//}
		//Added by Sreekumar S as a part of defaulting airline code in page (ANACR - 1471) ends
		invocationContext.addAllError(error);
		invocationContext.target=CLEAR_SUCCESS;
		

	}
	//added author A-5125 for ICRD-24635 
	private Map<String, Collection<OneTimeVO>> fetchScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(MESSAGE_STATUS);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			errors = handleDelegateException(exception);
		}
		return hashMap;
	}
}
