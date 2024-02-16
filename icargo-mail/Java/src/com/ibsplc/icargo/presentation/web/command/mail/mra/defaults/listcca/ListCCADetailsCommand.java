/*
 * ListCCADetailsCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listcca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ListCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListCCASession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for viewing the detailed information of
 * selected CCAs
 * 
 * @author A-3429
 */
public class ListCCADetailsCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * Class name
	 */

	private static final String CLASS_NAME = "DetailListCCACommand";

	/**
	 * 
	 * Module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * Screen ID
	 */
	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";

	/**
	 * Screen ID
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.listcca";

	/**
	 * target action
	 */
	private static final String DETAIL_SUCCESS = "detail_success";

	/**
	 * LIST_FAILURE
	 */
	private static final String LIST_FAILURE = "list_failure";

	/**
	 * For Error Tags
	 */
	private static final String ERROR_MANDATORY = "mailtracking.mra.defaults.maintaincca.anyfiltercriteria";

	/**
	 * target action
	 */
	private static final String SCREEN_STATUS = "maintaincca";
	/**
	 * For Onetime values
	 */
	private static final String CCATYPE_ONETIME = "mra.defaults.ccatype";
	private static final String CCASTATUS_ONETIME = "mra.defaults.ccastatus";
	private static final String BILLINGSTATUS_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";//added by a-7871 for ICRD-250606

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		MaintainCCASession maintainCCASession = (MaintainCCASession) getScreenSession(
				MODULE_NAME, MAINTAINCCA_SCREEN);
		ListCCASession listccaSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		ListCCAForm listCCAForm = (ListCCAForm) invocationContext.screenModel;
		 String[] selectedRows = listCCAForm.getSelectedRows();
         int count = Integer.parseInt(selectedRows[0]);
	    Page<CCAdetailsVO> ccaDetailVOs = listccaSession.getCCADetailsVOs();
	    CCAdetailsVO ccaDetailVO = ccaDetailVOs.get(count);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO displayErrorVO = null;
		ListCCAFilterVO listCCAFilterVO=null;
		Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes
				.getCompanyCode());	
		maintainCCASession.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)oneTimeValues);
		MaintainCCAFilterVO maintainCCAFilterVO = new MaintainCCAFilterVO();

		maintainCCAFilterVO.setCompanyCode(ccaDetailVO.getCompanyCode());
		maintainCCAFilterVO.setPartyCode(ccaDetailVO.getIssuingParty());
		maintainCCAFilterVO.setUsrCCANumFlg("N");
		if(ccaDetailVO.getUsrccanum()!=null){			
			maintainCCAFilterVO.setCcaReferenceNumber(ccaDetailVO
					.getUsrccanum());
		}
		else{			
			if(ccaDetailVO.getCcaRefNumber() != null) {
				maintainCCAFilterVO.setCcaReferenceNumber(ccaDetailVO
						.getCcaRefNumber());
			}
		}
		maintainCCAFilterVO.setDsnNumber(ccaDetailVO.getDsnNo());
		maintainCCAFilterVO.setCcaStatus(ccaDetailVO.getCcaStatus());
		maintainCCAFilterVO.setConsignmentDocNum(ccaDetailVO.getCsgDocumentNumber());
		listCCAFilterVO=populateListCCAFilterVO(listCCAForm,listccaSession);		
		maintainCCASession.setCCAFilterVO(maintainCCAFilterVO);
		listccaSession.setCCAFilterVO(listCCAFilterVO);
		log.log(Log.FINE, "maintainCCAFilterVO----->", maintainCCAFilterVO);
		listccaSession.setListStatus(SCREEN_STATUS);
		invocationContext.target = DETAIL_SUCCESS;

	}
	/**
	 * 
	 * @param listCCAForm
	 * @return
	 */
	private ListCCAFilterVO populateListCCAFilterVO(ListCCAForm listCCAForm ,ListCCASession listccaSession) {
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		ListCCAFilterVO listCCAFilterVO =listccaSession.getCCAFilterVO();
		LocalDate toDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);		
		toDate.setDate(listCCAForm.getToDate());
		listCCAFilterVO.setToDate(toDate);
		return listCCAFilterVO;
	}
	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(
			String companyCode) {
		log.entering(CLASS_NAME, "fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(CCATYPE_ONETIME);		
		oneTimeList.add(CCASTATUS_ONETIME);
		oneTimeList.add(BILLINGSTATUS_ONETIME);//added by a-7871 for ICRD-250606

		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		log.exiting(CLASS_NAME, "fetchOneTimeDetails");
		return hashMap;
	}

}
