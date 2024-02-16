/*
 * ProcessManagerSessionImpl.java Created on Jun 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ProcessManagerSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 *  
 * @author A-2270
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		 Jun 20, 2007			A-2270	          Created
 */
public class ProcessManagerSessionImpl extends AbstractScreenSession implements
ProcessManagerSession {
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREENID = "mailtracking.mra.defaults.processmanager";
	
	private static final String KEY_ONETIMEVALUES = "processes";
	//Added by A-7531 for ICRD-132508 
	private static final String ONETIME_MAILCATEGORY = "mailcategory";
	//Added by A-7531 for ICRD-132508 
	private static final String ONETIME_SUBCLASS = "mailsubclass";
	//Added by A-7531 for ICRD-132487
	private static final String ONETIME_PRORATIONEXCEPTION = "exceptioncode";
	
	private static final String ONETIME_FILTERMODE = "filterMode";
	
	private static final String INVOICE="invoices";
	
	
	/**
	 * 
	 */
	@Override
	public String getScreenID() {
		return SCREENID;
	}
	
	/**
	 * 
	 */
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}
	/**
	 * @author A-7531
     * This method is used to set onetime values to the session
     * @param mailCategory - Collection<OneTimeVO>
     */
	public void setMailCategory(Collection<OneTimeVO> mailCategory) {
		setAttribute(ONETIME_MAILCATEGORY,(ArrayList<OneTimeVO>)mailCategory);
	}

	/**
	 * @author A-7531
     * This method returns the onetime vos
     * @return ONETIME_MAILCATEGORY - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailCategory() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_MAILCATEGORY);
	}
	
	
	/**
	 * @author A-7531
     * This method is used to set onetime values to the session
     * @param subclass - Collection<OneTimeVO>
     */
	public void setMailSubclass(Collection<OneTimeVO> mailSubclass) {
		setAttribute(ONETIME_SUBCLASS,(ArrayList<OneTimeVO>)mailSubclass);
	}

	/**
	 * @author A-7531
     * This method returns the onetime vos
     * @return ONETIME_SUBCLASS - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailSubclass() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_SUBCLASS);
	}
	/**
	 * 
	 */
	
	public ArrayList<OneTimeVO> getProcesses() {
		return getAttribute(KEY_ONETIMEVALUES);
	}
	
	/**
	 * @param processes
	 */
	public void setProcesses(ArrayList<OneTimeVO> processes) {
		setAttribute(KEY_ONETIMEVALUES, processes);
	}
	
	/**
	 * 
	 */
	public void removeProcesses() {
		removeAttribute(KEY_ONETIMEVALUES);
	}
	/**
	 * 
	 * 	Method		:	ProcessManagerSessionImpl.setException
	 *	Added by 	:	A-7531 on 08-Nov-2017
	 * 	Used for 	:
	 *	Parameters	:	@param exception 
	 *	Return type	: 	void
	 */
	public void setException(Collection<OneTimeVO> exception) {
		setAttribute(ONETIME_PRORATIONEXCEPTION,(ArrayList<OneTimeVO>)exception);
	}
	/**
	 * 
	 * 	Method		:	ProcessManagerSessionImpl.getException
	 *	Added by 	:	A-7531 on 08-Nov-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getException() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_PRORATIONEXCEPTION);
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ProcessManagerSession#setFilterMode(java.util.Collection)
	 *	Added by 			: A-8061 on 29-Oct-2020
	 * 	Used for 	:
	 *	Parameters	:	@param filterMode
	 */
	public void setFilterMode(Collection<OneTimeVO> filterMode) {
		setAttribute(ONETIME_FILTERMODE,(ArrayList<OneTimeVO>)filterMode);
	}
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ProcessManagerSession#getFilterMode()
	 *	Added by 			: A-8061 on 29-Oct-2020
	 * 	Used for 	:
	 *	Parameters	:	@return
	 */
	public Collection<OneTimeVO> getFilterMode() {
		return (Collection<OneTimeVO>)getAttribute(ONETIME_FILTERMODE);
	}
	
	public void setInvoices(Page<CN51SummaryVO> invoices) {
		setAttribute(INVOICE,(Page<CN51SummaryVO>)invoices);
	}
	
	public Page<CN51SummaryVO> getInvoices() {
		return (Page<CN51SummaryVO>)getAttribute(INVOICE);
	}
	
	
}
	
