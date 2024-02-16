/*
 * GenerateInvoiceSession.java Created on Feb 18, 2008
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author a-1863
 *
 */
public interface GenerateInvoiceSession extends ScreenSession{
	
	 public void setGenerateInvoiceVOs(Collection<GenerateInvoiceVO> generateInvoiceVOs);
	 
	 public void setToDate(Collection<String> fromdates);
	 
	 public ArrayList<GenerateInvoiceVO> getGenerateInvoiceVOs();
	 
	 public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	 public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	 public void removeOneTimeVOs();

}
