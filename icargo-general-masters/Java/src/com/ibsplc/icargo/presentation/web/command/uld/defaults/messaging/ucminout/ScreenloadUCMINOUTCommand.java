/*
 * ScreenloadUCMINOUTCommand.java Created on Jul 20, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucminout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMErrorLogSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMINOUTSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMINOUTForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class ScreenloadUCMINOUTCommand extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String SCREEN_ID = "uld.defaults.ucminoutmessaging";

	private static final String BLANK = "";

	private Log log = LogFactory.getLogger("ULD_MESSAGING");

	//Modified by SreekumarS - INT_ULD183_28Jan08
	//private static final String CONTENT = "uld.defaults.contentcodes"; 
	private static final String CONTENT = "uld.defaults.contentcodes";
	private static final String ULDSTATUS = "uld.defaults.ucminout.uldsentstatus";
	private static final String ULDSOURCE = "uld.defaults.ucminout.uldsourceforucm";
	private static final String SCREENID_UCMERRORLOG="uld.defaults.ucmerrorlog";
	/**
	 * execute method 
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ScreenLoadCommand", "UCM IN OUT");
		UCMINOUTForm form = (UCMINOUTForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode=logonAttributes.getCompanyCode();
		UCMINOUTSession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		session.setFlightValidationVOSession(null);
		//Added by A-7359 for ICRD-225848 starts here
		UCMErrorLogSession ucmErrorLogSession = getScreenSession(MODULE_NAME, SCREENID_UCMERRORLOG);
		if((("fromucmerrorlog").equals(ucmErrorLogSession.getPageURL()) && ucmErrorLogSession
				.getFlightFilterMessageVOSession() != null)){
			FlightFilterMessageVO uldFlightMessageFilterVO=null;
    		uldFlightMessageFilterVO=ucmErrorLogSession.getFlightFilterMessageVOSession();
    		form.setCarrierCode(uldFlightMessageFilterVO.getCarrierCode());
    		form.setFlightDate(uldFlightMessageFilterVO.getFlightDate().toDisplayDateOnlyFormat());
    		form.setFlightNo(uldFlightMessageFilterVO.getFlightNumber());
    		//Added by A-7359 fro ICRD-259943 starts here
    		form.setUcmOut(uldFlightMessageFilterVO.getMessageType());
    		form.setOrigin(uldFlightMessageFilterVO.getAirportCode());
    		//Added by A-7359 fro ICRD-259943 ends here
    		ucmErrorLogSession.removeAllAttributes();
		}
		//Added by A-7359 for ICRD-225848 ends here
		Map<String, Collection<OneTimeVO>> oneTimeCollection = fetchScreenLoadDetails(companyCode);
		Collection<OneTimeVO> contentTypes = oneTimeCollection
				.get(CONTENT);
		//Change Made By SreekumarS for defaulting "Cargo" as the default value in ComboBox - starts
		Collection<OneTimeVO> newContentTypeVOs = new ArrayList<OneTimeVO>();
		if(contentTypes != null && contentTypes.size() > 0){
			for(OneTimeVO oneTimeVO:contentTypes){
				if("A".equalsIgnoreCase(oneTimeVO.getFieldValue())){
					newContentTypeVOs.add(oneTimeVO);
				}
			}
			for(OneTimeVO oneTimeVO:contentTypes){
				if(!"A".equalsIgnoreCase(oneTimeVO.getFieldValue())){
					newContentTypeVOs.add(oneTimeVO);
				}
			}
		}
		Map<String, Collection<OneTimeVO>> oneTimeValues = getOnetimeValues(companyCode);
		session.setOneTimeValues(new HashMap<String, Collection<OneTimeVO>>(oneTimeValues));
		session.setContentType((ArrayList<OneTimeVO>) newContentTypeVOs);
		session.setNewContentType((ArrayList<OneTimeVO>) newContentTypeVOs);
		//Change Made By SreekumarS for defaulting "Cargo" as the default value in ComboBox - ends
		ULDFlightMessageReconcileVO reconcileVO = new ULDFlightMessageReconcileVO();
		reconcileVO
				.setOperationFlag(ULDFlightMessageReconcileVO.OPERATION_FLAG_INSERT);
		reconcileVO.setCompanyCode(logonAttributes.getCompanyCode());
		session.setMessageReconcileVO(reconcileVO);
		//Added by A-7359 fro ICRD-259943 starts here
		if(form.getOrigin()== null || form.getOrigin().trim().length() == 0){
		form.setOrigin(logonAttributes.getAirportCode().toUpperCase());
		}
		if(form.getUcmOut()== null || form.getUcmOut().trim().length() == 0){
		form.setUcmOut("OUT");
		}
		//Added by A-7359 fro ICRD-259943 ends here
		form.setOrgDestStatus(BLANK);

		invocationContext.target = SCREENLOAD_SUCCESS;

	}

	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(CONTENT);
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
	/**
	 * 
	 * 	Method		:	ScreenloadUCMINOUTCommand.getOnetimeValues
	 *	Added by 	:	A-7359 on 14-Sep-2017
	 * 	Used for 	:   ICRD 192413
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return 
	 *	Return type	: 	Map<String,Collection<OneTimeVO>>
	 */
	private Map<String, Collection<OneTimeVO>> getOnetimeValues(String companyCode)
	{
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			oneTimeList.add(ULDSTATUS);
			oneTimeList.add(ULDSOURCE);
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			errors = handleDelegateException(exception);
		}
		return hashMap;
	}

}
