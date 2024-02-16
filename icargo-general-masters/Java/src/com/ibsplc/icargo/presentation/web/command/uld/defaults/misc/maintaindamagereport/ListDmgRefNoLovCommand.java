/**
 * ListDmgRefNoLovCommand.java Created on Feb 06,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.
											misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageReferenceNumberLovVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.DamageRefNoLovSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.DamageRefNoLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is used to list the damage reports. 
 * @author A-1862
 */
public class ListDmgRefNoLovCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("ListDmgRefNoLovCommand");
	
	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"uld.defaults.damagerefnolov";
    
	private static final String LIST_SUCCESS = "list_success";
   

    /**
     * execute method
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
		String  compCode = logonAttributes.getCompanyCode();
		
		DamageRefNoLovForm form = 
			(DamageRefNoLovForm)invocationContext.screenModel;
	//	form.setScreenStatusFlag(
		//		ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		
		DamageRefNoLovSession session = getScreenSession(MODULE, SCREENID);
		log
				.log(Log.INFO, "form.getPageURL()-------------->", form.getPageURL());
		if(("listlov").equals(form.getPageURL()))
		{
		Page<ULDDamageReferenceNumberLovVO> pg = null;
		HashMap indexMap = null;
		HashMap finalMap = null;
		if (session.getIndexMap()!=null){
			indexMap = session.getIndexMap();
		}
		if (indexMap == null) {
			log.log(Log.FINE,"INDEX MAP IS NULL");
			indexMap = new HashMap();
			indexMap.put("1", "1");
		}
		int nAbsoluteIndex = 0;
		String toDisplayPage = form.getDisplayPage();
		int displayPage = Integer.parseInt(toDisplayPage);
		String strAbsoluteIndex = (String)indexMap.get(toDisplayPage);
		if(strAbsoluteIndex != null){
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		
		String uldNo = form.getUldNo().toUpperCase();
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		log.log(log.FINE,"before setting to delegate--------------->");
		log.log(Log.INFO, "uldNo-------------->", uldNo);
		log.log(Log.INFO, "displayPage---------->", displayPage);
		log.log(Log.INFO, "compCode---------->", compCode);
		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
		 pg = delegate.findULDDamageReferenceNumberLov(compCode,uldNo,displayPage);
		 for(ULDDamageReferenceNumberLovVO vo:pg){
				log.log(log.FINE,"getting from delegate--------------->");	
				log.log(Log.INFO, "vo-------------->", vo);
				
			}
		} catch (BusinessDelegateException e) {
			e.getMessage();
			exception = handleDelegateException(e);
		}
		
		if(pg !=null && pg.size()>0){	
			log.log(log.FINE,"if pg is not null------------------->");
			session.setULDDamageReferenceNumberLovVOs(pg);
			log.log(Log.INFO, "session.getULDDamageReferenceNumberLovVOs()",
					session.getULDDamageReferenceNumberLovVOs());
			invocationContext.target = LIST_SUCCESS;	
			}else {
				log.log(log.FINE,"if pg is null------------------->");
				session.removeULDDamageReferenceNumberLovVOs();
				invocationContext.target = LIST_SUCCESS;	
			}
		}else {
			
			form.setPageURL(session.getParentScreenId());
			session.removeParentScreenId();
		}
		
		invocationContext.target = LIST_SUCCESS;	
	}
    /**
     * @param existingMap
     * @param page
     * @return HashMap
     */
    private HashMap buildIndexMap(HashMap existingMap, Page page) {
    	HashMap finalMap = existingMap;
    	String currentPage = String.valueOf(page.getPageNumber());
    	String currentAbsoluteIndex = String.valueOf(page.getAbsoluteIndex());
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
