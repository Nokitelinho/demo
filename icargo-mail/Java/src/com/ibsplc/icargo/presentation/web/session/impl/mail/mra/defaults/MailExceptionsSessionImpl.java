/*
 * MailExceptionsSessionImpl.java Created on Sep 17, 2010
 *
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MailExceptionsSession;

/*
 * @author
 */
public class MailExceptionsSessionImpl extends AbstractScreenSession
	implements MailExceptionsSession {

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.mailexceptionreports";

	private static final String KEY_DATA = "data";
	private static final String KEY_ONETIME_VOS="onetimevalues";

	/**
     *
     */
    public MailExceptionsSessionImpl() {
        super();

    }

    /**
     * @return screenId
     */
   	@Override
	public String getScreenID() {
		return SCREENID;
	}

   	/**
   	 * @return moduleName
   	 */
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}
	
	/**
	 * @return data
	 */
	public String getData() {

		return getAttribute( KEY_DATA );
	}

	/**
	 * @param data
	 */
	public void setData(String data) {
		setAttribute(KEY_DATA, data);

	}

	/**
	 * removes data
	 */
	public void removeData() {
		removeAttribute(KEY_DATA);
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MailExceptionsSession#getOneTimeVOs()
	 *	Added by 			: A-4809 on Jan 4, 2017
	 * 	Used for 	:
	 *	Parameters	:	@return
	 */
    public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){
    return getAttribute(KEY_ONETIME_VOS);
    }
    /**
     *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MailExceptionsSession#setOneTimeVOs(java.util.HashMap)
     *	Added by 			: A-4809 on Jan 4, 2017
     * 	Used for 	:
     *	Parameters	:	@param oneTimeVOs
     */
    public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){
    setAttribute(KEY_ONETIME_VOS, oneTimeVOs);
    }
    /**
     *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MailExceptionsSession#removeOneTimeVOs()
     *	Added by 			: A-4809 on Jan 4, 2017
     * 	Used for 	:
     *	Parameters	:
     */
    public void removeOneTimeVOs() {
    removeAttribute(KEY_ONETIME_VOS);
    }

}
