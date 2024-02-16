
/*

 *

 * ListProrationExceptionsSessionImpl.java Created on Sep 2008

 *

 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.

 *

 * This software is the proprietary information of IBS
 *  Software Services (P) Ltd.

 * Use is subject to license terms.

 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionsFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListProrationExceptionsSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3108
 *
 */
public class ListProrationExceptionsSessionImpl extends AbstractScreenSession
									implements ListProrationExceptionsSession{
	/*
	 * String for SCREENID
	 */
	private static final String SCREENID = "mailtracking.mra.defaults.listmailprorationexceptions";
	/*
	 * String for MODULE_NAME
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String KEY_PRORATIONEXCEPTIONSFILTERVO="prorationExceptionsFilterVO";
	private static final String KEY_PRORATIONEXCEPTIONVOS="prorationExceptionsVOs";
	private static final String KEY_PRORATIONEXCEPTIONVOSS="prorationExceptionsVOss";
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";
	private static final String KEY_FROMSCREENFLAG="fromscreenflag";
	private static final String KEY_SELECT_ROWS="rowsselected";
	/**
	 * Parent ScreenID
	 */
	private static final String KEY_PARENTID = "parentId";
	
	//added by A-5223 for ICRD-21098 starts
	private static final String  TOTALRECORDS = "totalRecords";
	//added by A-5223 for ICRD-21098 ends
	 private static final String KEY_SELECTED_VOID_MAILS = "voidmailbagsselected";

	private static final String  SYSPAR = "systemParametres";
	
	/**
	 * @return Returns the SCREENID.
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * @return Returns the MODULE_NAME.
	 */
	public String getModuleName() {
		return MODULE_NAME;
    }
	

	/**
	 * Methods for setting NRAProrationExceptionsFilterVO
	 * @param prorationExceptionsFilterVO
	 * 
	 */
    public void setProrationExceptionFilterVO(ProrationExceptionsFilterVO prorationExceptionsFilterVO) {
    	setAttribute(KEY_PRORATIONEXCEPTIONSFILTERVO, prorationExceptionsFilterVO);
    }

    /**
	 * @return Returns the NRAProrationExceptionsFilterVO.
	 */
    public ProrationExceptionsFilterVO getProrationExceptionFilterVO() {
    	return getAttribute(KEY_PRORATIONEXCEPTIONSFILTERVO);
    }

    /**
     * Method for removing NRAProrationExceptionsFilterVO 
     */
    public void removeProrationException() {
    	removeAttribute(KEY_PRORATIONEXCEPTIONSFILTERVO);
    }

    /**
	 * @return Returns the oneTimeValues.
	 */
	public HashMap<String,Collection<OneTimeVO>> getOneTimeValues() {
		return getAttribute(KEY_ONETIMEVALUES);
	}

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */
	public void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues) {
		setAttribute(KEY_ONETIMEVALUES,oneTimeValues);
	}
	/**
	 * Methods for getting Page<NRAProrationExceptionVO>
	 * @return Page<NRAProrationExceptionVO> 
	 */
	public Page<ProrationExceptionVO> getProrationExceptionVOs(){
		return (Page<ProrationExceptionVO>) getAttribute(KEY_PRORATIONEXCEPTIONVOS);
	}
	
	/**
	 * Methods for setting Page<NRAProrationExceptionVO>
	 * @param prorationExceptionVOs
	 * 
	 */
	public void setProrationExceptionVOs(Page<ProrationExceptionVO> prorationExceptionVOs){
		setAttribute(KEY_PRORATIONEXCEPTIONVOS,(Page<ProrationExceptionVO>)prorationExceptionVOs);
	}
	
	/**
	 * Methods for getting Page<NRAProrationExceptionVO>
	 * @return Returns the Page<NRAProrationExceptionVO>.
	 */
	public Collection<ProrationExceptionVO> getProrationExceptionVOss(){
		return (Collection<ProrationExceptionVO>) getAttribute(KEY_PRORATIONEXCEPTIONVOSS);
	}
	
	/**
	 * Methods for setting Page<NRAProrationExceptionVO>
	 * @param prorationExceptionVOs
	 */
	public void setProrationExceptionVOss(Collection<ProrationExceptionVO> prorationExceptionVOs){
		setAttribute(KEY_PRORATIONEXCEPTIONVOSS,(ArrayList<ProrationExceptionVO>)prorationExceptionVOs);
	}
	
	
	/**
	 * Methods for removing Page<NRAProrationExceptionVO>
	 */
	public void removeProrationExceptionVOs(){
		removeAttribute(KEY_PRORATIONEXCEPTIONVOS);
	}
	
	/**
	 * Method for removing Collection<ProrationExceptionVOss>
	 */
	public void removeProrationExceptionVOss(){
		removeAttribute(KEY_PRORATIONEXCEPTIONVOSS);
	}

	/**
	 * Methods for getting parentId
	 * 
	 * @return parentId
	 */
	public String getParentId() {
		return getAttribute(KEY_PARENTID);
	}

	/**
	 * Methods for setting ParentId
	 * 
	 * @param parentId
	 */
	public void setParentId(String parentId) {
		setAttribute(KEY_PARENTID, parentId);
	}

	/**
	 * This method removes the ParentId in session
	 */
	public void removeParentId() {
		removeAttribute(KEY_PARENTID);
	}
	/**
	 * Methods for getting fromScreenFlag
	 * 
	 * @return fromScreenFlag
	 */
	public String getFromScreenFlag() {
		return getAttribute(KEY_FROMSCREENFLAG);
	}

	/**
	 * Methods for setting fromScreenFlag
	 * 
	 * @param fromScreenFlag
	 */
	public void setFromScreenFlag(String fromScreenFlag) {
		setAttribute(KEY_FROMSCREENFLAG, fromScreenFlag);
	}
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListProrationExceptionsSession#getTotalRecords()
 *	Added by 			: a-5223 on 17-Oct-2012
 * 	Used for 	: getting total records
 *	Parameters	:	@return
 */
	@Override
	public Integer getTotalRecords() {
		return getAttribute(TOTALRECORDS);
	}
/**
 * 
 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListProrationExceptionsSession#setTotalRecords(int)
 *	Added by 			: a-5223 on 17-Oct-2012
 * 	Used for 	: setting total records
 *	Parameters	:	@param totalRecords
 */
	@Override
	public void setTotalRecords(int totalRecords) {
		setAttribute(TOTALRECORDS,totalRecords);
		
	}
	public HashMap<String, String> getSystemParametres(){
		return getAttribute(SYSPAR);
	}
	
	public void setSystemParametres(HashMap<String, String> paramHashMap){
		setAttribute(SYSPAR,paramHashMap);
	}
	
	/**
	 * 
	 */
	public void setSelectedVoidMailbags(Collection<DocumentBillingDetailsVO> VOs){
		setAttribute(KEY_SELECTED_VOID_MAILS,(ArrayList<DocumentBillingDetailsVO>)VOs);
	}
	
	
	/**
	 * 
	 */
	public Collection<DocumentBillingDetailsVO> getSelectedVoidMailbags() {
		return (Collection<DocumentBillingDetailsVO>)getAttribute(KEY_SELECTED_VOID_MAILS);
	}
	
	/**
	 * 
	 */
	public String[] getSelectedRows(){
     	return getAttribute(KEY_SELECT_ROWS);
     }
	
	/**
	 * 
	 */
	public void setSelectedRows(String[] selectArray){
     	setAttribute(KEY_SELECT_ROWS,selectArray);
     }

}
