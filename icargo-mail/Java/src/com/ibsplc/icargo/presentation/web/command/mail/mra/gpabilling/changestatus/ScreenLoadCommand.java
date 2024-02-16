/*
 * ScreenLoadCommand.java Created on June 4, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.changestatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingInvoiceEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingInvoiceEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ChangeStatus ScreenloadCommand");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";
	private static final String KEY_STATUS_NOT_CHANGE="mailtracking.mra.gpabilling.statuscantbechange";
	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "screen_success";
	private static final String ACTION_FAILURE = "screen_failure";
	private static final String KEY_BILLING_STATUS_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";

	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME, "execute");

    	GPABillingInvoiceEnquiryForm  form=( GPABillingInvoiceEnquiryForm )invocationContext.screenModel;

 		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
 		GPABillingInvoiceEnquirySession session = (GPABillingInvoiceEnquirySession) getScreenSession(
 				MODULE_NAME, SCREEN_ID);

 		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String counter = form.getCounter();
		Collection<CN66DetailsVO> cN66DetailsVOs = session
				.getCN66VOs();
		ArrayList<CN66DetailsVO> cN66DetailsVOArraylist = new ArrayList<CN66DetailsVO>(
				cN66DetailsVOs);
		CN66DetailsVO cN66DetailsVO;
		log.log(Log.FINE, "inside *****<<<<counter>>>>----------  ", counter);
		cN66DetailsVO= cN66DetailsVOArraylist.get(Integer.parseInt(counter));
		log.log(Log.FINE, "Inside screenload command... >>", cN66DetailsVO);
		form.setDsn(cN66DetailsVO.getDsn());
		form.setDsDate(cN66DetailsVO.getReceivedDate().toDisplayDateOnlyFormat());
		form.setRemarks(cN66DetailsVO.getRemarks());
		form.setStatus(cN66DetailsVO.getBillingStatus());
		form.setStatus(cN66DetailsVO.getBillingStatus());

 		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
 		Collection<String> parameterTypes = new ArrayList<String>();
 		parameterTypes.add("mra.gpabilling.gpabillingstatus");


 		ApplicationSessionImpl applicationSession = getApplicationSession();
 		LogonAttributes logonAttributes = applicationSession.getLogonVO();
 		String companyCode = logonAttributes.getCompanyCode();
 		Map<String, Collection<OneTimeVO>> oneTimeValuesNew = new HashMap <String, Collection<OneTimeVO>>();
 		Collection<OneTimeVO> oneTimeVos=new ArrayList<OneTimeVO>();


 		try {
 			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
 					companyCode, parameterTypes);
 			log.log(Log.FINE, " One Time Values---->>", oneTimeValues);

 		} catch (BusinessDelegateException e) {
 			handleDelegateException(e);

 		}
 		/*
 		 * Added By A-3434 For removing unwanted onetime values.
 		}*/

 		Collection<OneTimeVO> billingStatus = oneTimeValues.get(KEY_BILLING_STATUS_ONETIME);
 		HashMap<Integer, OneTimeVO > hashmap=new HashMap() ;

 		log.log(Log.FINE, " billingStatus---->>", billingStatus);
		int i=0;

 		for(OneTimeVO oneTimeVO:billingStatus){

 			hashmap.put(i, oneTimeVO);


 			if (!(("PB").equals(oneTimeVO.getFieldValue())||("PO").equals(oneTimeVO.getFieldValue())
 					||("WD").equals(oneTimeVO.getFieldValue()))){

 				hashmap.keySet().remove(i);

 			}
 			i++;
 		}

 		log.log(Log.FINE, " hashmap after remove---->>", hashmap);
		for(OneTimeVO  onetimeVO:hashmap.values()){
 			oneTimeVos.add(onetimeVO);
 		}
 		oneTimeValuesNew.put(KEY_BILLING_STATUS_ONETIME,oneTimeVos);


 		session.setOneTimeMap((HashMap<String, Collection<OneTimeVO>>) oneTimeValuesNew);

 		CN51SummaryVO cN51SummaryVO=session.getCN51SummaryVO();
 		log.log(Log.FINE, "InvoiceStatus-----  ", cN51SummaryVO.getInvoiceStatus());
		if("Finalised".equals(cN51SummaryVO.getInvoiceStatus())){
 			errors.add(new ErrorVO(KEY_STATUS_NOT_CHANGE));
    		invocationContext.addAllError(errors);

			invocationContext.target = ACTION_FAILURE;

 		}
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    }

}
