/**
 *	Java file	: 	com.ibsplc.icargo.mail.operations.builder.CustomsMessageBuilder.java
 *
 *	Created by	:	A-8083
 *	Created on	:	21-OCT-2018
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.builder;

import com.ibsplc.icargo.business.customs.defaults.vo.CustomsDefaultsVO;
import com.ibsplc.icargo.business.mail.operations.proxy.CustomsMessageProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.converter.MailtrackingDefaultsVOConverter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class CustomsMessageBuilder  extends AbstractActionBuilder{
	
	private String CLASS_NAME = "CustomsMessageBuilder";
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	
	
	/**
	 * added by A-8083
	 * @param mailArrivalVO
	 */
	public void sendCustomsMessages(MailArrivalVO mailArrivalVO)throws SystemException {
		
	
						
		 log.entering(CLASS_NAME, "sendCustomsMessages");	  		
		 CustomsDefaultsVO customsDefaultsVO = null;
		 customsDefaultsVO=MailtrackingDefaultsVOConverter.populateCustomsDefaultsVO(mailArrivalVO);
		 if(customsDefaultsVO!=null){
				sendCustomsMessages(customsDefaultsVO);
		 }
						
		log.exiting(CLASS_NAME, "sendCustomsMessages"); 
	}
	/**
	 * added by A-8083
	 * @param customsDefaultsVO
	 */
	private void sendCustomsMessages(CustomsDefaultsVO customsDefaultsVO) throws SystemException {		
		log.entering(CLASS_NAME, "sendCustomsMessages");
		log.log(Log.FINE, "CustomsDefaultsVO Populated >>>>>>>>>>>>>>>>>"+customsDefaultsVO);
		try {
			new CustomsMessageProxy().sendCustomsMessages(customsDefaultsVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage());
		}
		log.exiting(CLASS_NAME, "sendCustomsMessages"); 
	}

}
