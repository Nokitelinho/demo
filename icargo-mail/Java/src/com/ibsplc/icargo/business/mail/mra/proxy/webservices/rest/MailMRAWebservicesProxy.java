/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.proxy.webservices.rest.MailMRAWebservicesProxy.java
 *
 *	Created by	:	A-7540
 *	Created on	:	08-May-2019
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.proxy.webservices.rest;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ResditReceiptVO;
import com.ibsplc.icargo.framework.services.jaxws.proxy.WebServiceProxy;
import com.ibsplc.icargo.framework.services.jaxws.proxy.exception.WebServiceException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * 
 * @author A-7540
 *
 */
public class MailMRAWebservicesProxy extends WebServiceProxy{
	
	private static final String SERVICE_NAME = "USPSResditReceiptService";
	private static final String OPERATION_NAME = "getResditInfofromUSPS"; 

	 public Collection<ResditReceiptVO> getResditInfofromUSPS(String mailbagID) 
			 throws SystemException,WebServiceException{
		 
	    Collection<ResditReceiptVO> resditReceiptVOs =  new ArrayList<ResditReceiptVO>();
	    resditReceiptVOs = despatchRequest(SERVICE_NAME,OPERATION_NAME,mailbagID);
		return resditReceiptVOs;
	 }
}
