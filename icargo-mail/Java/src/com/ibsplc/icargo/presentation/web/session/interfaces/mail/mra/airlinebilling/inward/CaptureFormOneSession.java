/*
 * CaptureFormOneSession.java Created on Jul 28,2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2391
 *
 */
public interface CaptureFormOneSession extends ScreenSession {

	
	/**
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	/**
	 *
	 */
	public void removeOneTimeVOs();
	
	/**
	 * @return
	 */
	public Collection<InvoiceInFormOneVO> getFormOneInvVOs();
	/**
	 * 
	 * @param exceptionInInvoiceVOs
	 */
	public void setFormOneInvVOs(ArrayList<InvoiceInFormOneVO> formOneInvVOs);
	/**
	 * remove
	 */
	public void removeFormOneInvVOs();
	
	
	 /**
	 * @return InvoiceInFormOneVO
	 */
	public  FormOneVO getFormOneVO();
	
	/**
	 * 
	 * @param InvoiceInFormOneVO
	 */
	public void setFormOneVO(FormOneVO formOneVO);
	
	/**
	 * Remove Filter Vo
	 */
	
	 public void removeFormOneVO();
	 
	 
		/**
		 *   Method to get the onetime map in the
		 *         session
		 * @return HashMap the onetime map from session
		 */
		public HashMap<String, Collection<OneTimeVO>> getOneTimeMap();

		/**
		 *  Method to set the Onetimes map to session
		 * @param oneTimeMap
		 *            The one time map to be set to session
		 */
		public void setOneTimeMap(HashMap<String, Collection<OneTimeVO>> oneTimeMap);

		/**
		 *  Method to remove One Time Map from
		 *         session
		 */
		public void removeOneTimeMap();

}

