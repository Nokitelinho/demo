/*
 * MailExceptionsSession.java Created on Sep 17, 2010
 *
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2414
 *
 */
public interface MailExceptionsSession extends ScreenSession {
	
	/**
	 * @return data
	 */
	public String getData();

	/**
	 * @param data
	 */
	public void setData(String data);

	/**
	 * removes data
	 */
	public void removeData();
/**
 * 	Method		:	MailExceptionsSession.getOneTimeVOs
 *	Added by 	:	A-4809 on Jan 4, 2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	HashMap<String,Collection<OneTimeVO>>
 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();
/**
 * 	Method		:	MailExceptionsSession.setOneTimeVOs
 *	Added by 	:	A-4809 on Jan 4, 2017
 * 	Used for 	:
 *	Parameters	:	@param oneTimeVOs 
 *	Return type	: 	void
 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);
/**
 * 	Method		:	MailExceptionsSession.removeOneTimeVOs
 *	Added by 	:	A-4809 on Jan 4, 2017
 * 	Used for 	:
 *	Parameters	:	 
 *	Return type	: 	void
 */
	public void removeOneTimeVOs();

}
