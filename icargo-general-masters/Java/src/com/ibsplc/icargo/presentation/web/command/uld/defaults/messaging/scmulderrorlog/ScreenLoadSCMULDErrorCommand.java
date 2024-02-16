/* ScreenLoadSCMULDErrorCommand.java Created on Aug 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmulderrorlog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMULDErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMULDErrorLogForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1496
 * 
 */
public class ScreenLoadSCMULDErrorCommand extends BaseCommand {
	
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID = "uld.defaults.scmulderrorlog";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	
	private static final String ERROR_DESC = "uld.defaults.scmulderror"; 

	private Log log = LogFactory.getLogger("ScreenLoadSCMULDErrorCommand");

	private static final String BLANK = "";
	
	private static final String GHA = "GHA";


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
		//Commented by Manaf for INT ULD510
		//String compCode = logonAttributes.getCompanyCode();

		SCMULDErrorLogForm scmUldReconcileForm = (SCMULDErrorLogForm) invocationContext.screenModel;
		SCMULDErrorLogSession scmUldSession = (SCMULDErrorLogSession) getScreenSession(
				MODULE, SCREENID);
		scmUldSession.setSCMULDFilterVO(null);
		scmUldSession.setSCMReconcileDetailVOs(null);
		scmUldSession.setPageUrl(null);
		SCMMessageFilterVO filterVO = new SCMMessageFilterVO();    	
    	filterVO.setAirportCode(logonAttributes.getAirportCode());
    	/*   changed by A-5142 for ICRD-16062 */
    	if(logonAttributes.isGHAUser()){
    		scmUldReconcileForm.setScmULDDisable(GHA);  
    	}else{
    		scmUldReconcileForm.setScmULDDisable(BLANK);  
    	}
    	
    	scmUldSession.setSCMULDFilterVO(filterVO);
 		//    	Added by Sreekumar as a part of defaulting airline code in page (ANACR - 1471)
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
    	//removed by nisha on 29Apr08
		
		/*   changed by A-5142  */
			if(logonAttributes.isAirlineUser()){
				scmUldReconcileForm.setAirline(logonAttributes.getOwnAirlineCode()); 
		}else{
			scmUldReconcileForm.setAirline(BLANK);
			}
		
		// Added by Sreekumar as a part of defaulting airline code in page (ANACR - 1471) ends
		//Added By Sreekumar S as a part of AirNZ CR 521
		Collection<OneTimeVO> errorDescriptions = getOneTimeVOs(logonAttributes.getCompanyCode());
		scmUldSession.setErrorDescriptions(errorDescriptions);
		//Added By Sreekumar S as a part of AirNZ CR 521 ends
		invocationContext.addAllError(error);
		invocationContext.target = SCREENLOAD_SUCCESS;

	}
	
	public Collection<OneTimeVO> getOneTimeVOs(String companyCode){
		ArrayList<String> parameterList = new ArrayList<String>();
		Map<String,Collection<OneTimeVO>> hashMap = new HashMap<String,Collection<OneTimeVO>>();
		parameterList.add(ERROR_DESC);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode, parameterList);
		} catch (BusinessDelegateException e) {
			// To be reviewed Auto-generated catch block
			e.getMessage();
		}
		return hashMap.get(ERROR_DESC);
	}

	
}
