/*
 * AccountingDtlsCommand.java created on Nov 12, 2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAAccountingVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DespatchEnqSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-3229
 * 
 */
public class AccountingDtlsCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MRA DEFAULTS DespatchEnquiry");

	private static final String CLASS_NAME = "AccountingDetailsCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.despatchenquiry";

	private static final String LISTACC_SUCCESS = "listaccounting_success";

	private static final String NO_RESULTS = "mailtracking.mra.defaults.despatchenquiry.noaccountingdetailsfound";

	private static final String LISTACC_FAILURE = "listaccouting_failure";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.dsnselectpopup";

	private static final String FUNCTION_POINT = "cra.accounting.functionpoint";

	private static final String BASE_CURRENCY = "shared.airline.basecurrency";
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO(); 
		DSNPopUpSession popUpSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		DespatchEnquiryForm despatchEnquiryForm = (DespatchEnquiryForm) invocationContext.screenModel;
		DespatchEnqSession despatchEnqSession = getScreenSession(MODULE_NAME,
				SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		
		
		// DSN popup to get pk details

		DSNPopUpVO popUpVO = popUpSession.getSelectedDespatchDetails();
		despatchEnquiryForm.setDespatchNum(popUpVO.getBlgBasis());
		despatchEnquiryForm.setDsnFilterDate(popUpVO.getDsnDate());

		log.log(Log.INFO, "popUpVO---------", popUpVO);
		//Pagination
		HashMap indexMap = null;
		HashMap finalMap = null;
			if(despatchEnqSession.getIndexMap()!= null){
			indexMap = despatchEnqSession.getIndexMap();
			}
			if(indexMap == null) {
			indexMap = new HashMap();
			indexMap.put("1", "1");
			}
		int nAbsoluteIndex = 0;
		String dispPage = despatchEnquiryForm.getDisplayPage();
		despatchEnquiryForm.setPageNum(dispPage);
		
		int displayPage = Integer.parseInt(dispPage);
		popUpVO.setPageNumber(displayPage);
		popUpVO.setTotalRecordCount(-1);
		String strAbsoluteIndex = (String)indexMap.get(dispPage);
		despatchEnquiryForm.setAbsIndex(strAbsoluteIndex);
		if(strAbsoluteIndex != null){
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		
		popUpVO.setAbsoluteIndex(nAbsoluteIndex);
		despatchEnquiryForm.setAbsIndex(String.valueOf(nAbsoluteIndex));
		despatchEnquiryForm.setDisplayPage(String.valueOf(displayPage));
		// ONE TIME DETAILS FOR FUNCTION POINT
		
    	SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
    	
    	Collection<String> functionPointValue = new ArrayList<String>();
    	functionPointValue.add(FUNCTION_POINT);
      	
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		try {
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					functionPointValue);
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e); 
		}
         //putting onetime function point into session
		if(oneTimeValues!=null){
			despatchEnqSession.setFunctionPoint(new ArrayList<OneTimeVO>(oneTimeValues.get(FUNCTION_POINT)));
		log.log(Log.INFO, "one time value----FUNCTION_POINT------",
				despatchEnqSession.getFunctionPoint());
		}
		

		if ((("N").equals(despatchEnquiryForm.getListed()))) {
			despatchEnquiryForm.setListed("N");
			invocationContext.target = LISTACC_FAILURE;
			return;
		} else {
			MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
			Page<MRAAccountingVO> mraAccountingVOs=null;
			
			// server call to get accounting details
			try {
				 mraAccountingVOs=delegate
						.findAccountingDetails(popUpVO);
				/*log.log(Log.INFO, "mraAccountingVOs size---------",
						mraAccountingVOs.size());*/
				if(mraAccountingVOs==null){
				//	despatchEnqSession.setPaymentAdviceSummary(null);
					ErrorVO error =new
					ErrorVO
					(NO_RESULTS);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}else{
					despatchEnqSession.setAccountingDetails(mraAccountingVOs);	
					finalMap = indexMap;
					if(despatchEnqSession.getAccountingDetails() != null){
				    finalMap = buildIndexMap(indexMap,
				    		despatchEnqSession.getAccountingDetails());
				    despatchEnqSession.setIndexMap(finalMap);
					   }
			} 
			}catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}
			if (mraAccountingVOs != null && mraAccountingVOs.size() > 0) {
				
				for(MRAAccountingVO mraAccountingVO:mraAccountingVOs){
					for(OneTimeVO oneTimeVO:despatchEnqSession.getFunctionPoint()){
						if(oneTimeVO.getFieldValue().equalsIgnoreCase(mraAccountingVO.getFunctionPoint())){
							mraAccountingVO.setFunctionPoint(oneTimeVO.getFieldDescription());
						}
					}
					
				}
				//Added as part of ICRD-147818 starts
				Collection<String> codes = new ArrayList<String>();
				codes.add(BASE_CURRENCY);
				Map<String, String> results = new HashMap<String, String>();
				try {
					results = new SharedDefaultsDelegate()
							.findSystemParameterByCodes(codes);
				} catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}
				String baseCurrency = results.get(BASE_CURRENCY);
				despatchEnquiryForm.setCurrency(baseCurrency);
				//Added as part of ICRD-147818 ends
				//despatchEnqSession.setAccountingDetails(mraAccountingVOs);	
				despatchEnquiryForm.setListed("Y");
				log.log(Log.FINE, "!!!inside errors==null");
				invocationContext.target = LISTACC_SUCCESS;
			} else {
				    despatchEnqSession.setAccountingDetails(null); 
					invocationContext.addAllError(errors);
					invocationContext.target = LISTACC_FAILURE;
				}

			
		}
		
		log.log(Log.INFO, "despatchEnqSession.getAccountingDetails---------",
				despatchEnqSession.getAccountingDetails());
		log.exiting(CLASS_NAME, "execute");
	}
	
    private HashMap buildIndexMap(HashMap existingMap, Page page) {
		HashMap finalMap = existingMap;
		String indexPage = String.valueOf((page.getPageNumber()+1));

		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}

		if (!isPageExits) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}
		return finalMap;
	}

}
