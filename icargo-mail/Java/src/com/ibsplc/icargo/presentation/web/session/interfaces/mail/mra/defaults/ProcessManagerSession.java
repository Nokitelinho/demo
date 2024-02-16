/*
 * ProcessManagerSession.java Created on Feb 15, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
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
 * 0.1     		  Jun 20, 2007				A-2270		Created
 */
public interface ProcessManagerSession extends ScreenSession {

	/**
     *@author A-7531
     * @param mailCategory - Collection<OneTimeVO>
     */ 
	public void setMailCategory(Collection<OneTimeVO> mailCategory);
	/**
     * @author A-7531
     * @return ONETIME_MAILCATEGORY - Collection<OneTimeVO>
     */
	public Collection<OneTimeVO> getMailCategory();
	
	/**
    *@author A-7531
    * @param mailCategory - Collection<OneTimeVO>
    */ 
	public void setMailSubclass(Collection<OneTimeVO> mailSubclass);
	/**
    * @author A-7531
    * @return ONETIME_MAILCATEGORY - Collection<OneTimeVO>
    */
	public Collection<OneTimeVO> getMailSubclass();
	

	/**
	 * Returns the one time values ( processes ) from the session.
	 * 
	 * 
	 */
	public ArrayList<OneTimeVO> getProcesses();
	
	/**
	 * Sets the one time values ( processes ) into the session.
	 * 
	 * 
	 */
	public void setProcesses(ArrayList<OneTimeVO> processes);
	
	/**
	 * Removes the one time value( processes ) from the session.
	 * 
	 * 
	 */
	public void removeProcesses();
	/**
	 * 
	 * 	Method		:	ProcessManagerSession.setException
	 *	Added by 	:	A-7531 on 08-Nov-2017
	 * 	Used for 	:
	 *	Parameters	:	@param exception 
	 *	Return type	: 	void
	 */
	public void setException(Collection<OneTimeVO> exception);
	/**
	 * 
	 * 	Method		:	ProcessManagerSession.getException
	 *	Added by 	:	A-7531 on 08-Nov-2017
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getException();
	
/**
 * 
 * 	Method		:	ProcessManagerSession.setFilterMode
 *	Added by 	:	A-8061 on 29-Oct-2020
 * 	Used for 	:
 *	Parameters	:	@param filterMode 
 *	Return type	: 	void
 */
	public void setFilterMode(Collection<OneTimeVO> filterMode);
/**
 * 
 * 	Method		:	ProcessManagerSession.getFilterMode
 *	Added by 	:	A-8061 on 29-Oct-2020
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Collection<OneTimeVO>
 */
	public Collection<OneTimeVO> getFilterMode();
	
	
	public void setInvoices(Page<CN51SummaryVO> invoices);
	
	public Page<CN51SummaryVO> getInvoices();
	
	
	
	
}
