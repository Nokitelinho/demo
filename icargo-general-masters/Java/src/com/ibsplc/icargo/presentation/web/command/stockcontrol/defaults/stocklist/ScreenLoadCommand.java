/*
 * ScreenLoadCommand.java Created on Jan 17, 2006
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

import com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockListSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.StockListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1619
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	private static final String ALL = "ALL";

	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		StockListForm form = (StockListForm) invocationContext.screenModel;
		StockListSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.cto.stocklist");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		DocumentTypeDelegate delegate = new DocumentTypeDelegate();
		    
		Collection<DocumentVO> documentCol = new ArrayList<DocumentVO>();
		DocumentFilterVO filterVO = new DocumentFilterVO();
		filterVO.setCompanyCode(companyCode);
		try {
			documentCol = delegate.findDocumentDetails(filterVO);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		HashMap<String, Collection<String>> docTypes = (HashMap<String, Collection<String>>) getMapForDocument(documentCol);
		session.setStockVOs(null);
		Collection<String> key = (Collection<String>) docTypes.keySet();
		ArrayList<String> keys = new ArrayList<String>(key);
		Collection<String> documentSubtype = docTypes.get(keys.get(0));
		form.setDocumentType("");
		form.setAirline("");
		form.setDocSubType("");
		//((List)documentSubtype).get(0);
		session.setDocumentValues(docTypes);
		session.setDocumentVO(documentCol);
		invocationContext.target = "screenload_success";
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
				//Commented by A-5174 for bug 32653
				//arr.add("ALL");
				documentMap.put(docType, arr);
			}
		}
		Collection<String> all = new ArrayList<String>();
		//Commented by A-5174 for bug 32653
		//all.add("ALL");
		//documentMap.put("ALL", all);
		return documentMap;

	}

}
