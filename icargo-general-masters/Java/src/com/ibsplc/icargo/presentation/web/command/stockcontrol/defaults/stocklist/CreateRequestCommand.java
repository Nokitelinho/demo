/*
 * CreateRequestCommand.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.stocklist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.CreateStockRequestSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockListSession;

/**
 * @author A-1619
 *
 */
public class CreateRequestCommand extends BaseCommand {
	private static final String COMNMODE_ONETIME = "stockcontrol.defaults.modeofcommunication";

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		CreateStockRequestSession session = getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.cto.createstockrequest");
		StockListSession stockListSession = getScreenSession(
				"stockcontrol.defaults", "stockcontrol.defaults.cto.stocklist");

		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		HashMap<String, Collection<OneTimeVO>> oneTimes = getScreenLoadDetails(logonAttributes
				.getCompanyCode());
		Collection<OneTimeVO> onetimes = oneTimes.get(COMNMODE_ONETIME);

		DocumentTypeDelegate delegate = new DocumentTypeDelegate();
		Collection<DocumentVO> documentCol = new ArrayList<DocumentVO>();
		DocumentFilterVO filterVO = new DocumentFilterVO();
		filterVO.setCompanyCode(logonAttributes.getCompanyCode());
		try {
			documentCol = delegate.findDocumentDetails(filterVO);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		HashMap<String, Collection<String>> docTypes = (HashMap<String, Collection<String>>) getMapForDocument(documentCol);
		session.setOnetimeValues(onetimes);
		session.setDocumentValues(docTypes);
		invocationContext.target = "screenload_success";

	}

	/**
	 * @param companyCode
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getScreenLoadDetails(
			String companyCode) {
		HashMap<String, Collection<OneTimeVO>> oneTimes = null;
		try {

			Collection<String> fieldTypes = new ArrayList<String>();
			fieldTypes.add(COMNMODE_ONETIME);
			oneTimes = (HashMap<String, Collection<OneTimeVO>>) new SharedDefaultsDelegate()
					.findOneTimeValues(companyCode, fieldTypes);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			log
					.log(Log.FINE,
							"<-----------------BusinessDelegateException------------->");
		}
		return oneTimes;
	}

	/**
	 * @param documentCol
	 * @return
	 */
	private Map<String, Collection<String>> getMapForDocument(
			Collection<DocumentVO> documentCol) {

		Map<String, Collection<String>> documentMap = new HashMap<String, Collection<String>>();
		String docType = "";
		Collection<String> arr = null;
		for (DocumentVO vo : documentCol) {
			docType = vo.getDocumentType();
			if (documentMap.get(docType) == null) {
				arr = new ArrayList<String>();
				for (DocumentVO documentVO : documentCol) {
					if (docType.equals(documentVO.getDocumentType())) {
						//Modified by A-5807 for ICRD-76004
						arr.add(documentVO.getDocumentSubType());
					}
				}
				documentMap.put(docType, arr);
			}
		}
		return documentMap;
	}

}
