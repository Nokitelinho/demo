/*
 * SurChargeBillingDetailSession.java Created on Jul 15, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SurchargeBillingDetailVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;


/**
 * @author A-5255 
 * @version	0.1, Jul 15, 2015
 * 
 *
 */
/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jul 15, 2015	     A-5255		First draft
 */

public interface SurChargeBillingDetailSession extends ScreenSession{
	/**
	 * 
	 * @author A-5255
	 * @param cn51CN66FilterVO
	 */
	public void setGpaBillingFilterVO(CN51CN66FilterVO cn51CN66FilterVO);
	/**
	 * 
	 * @author A-5255
	 * @return
	 */
	public CN51CN66FilterVO getGpaBillingFilterVO();
	/**
	 * 
	 * @author A-5255
	 */
	public void removeGpaBillingFilterVO();
	/**
	 * 
	 * @author A-5255
	 * @param surchargeBillingDetailVOs
	 */
	public void setSurchargeBillingDetailVOs(ArrayList<SurchargeBillingDetailVO> surchargeBillingDetailVOs);
	/**
	 * 
	 * @author A-5255
	 * @return
	 */
	public ArrayList<SurchargeBillingDetailVO>  getSurchargeBillingDetailVOs();
	/**
	 * 
	 * @author A-5255
	 */
	public void removeSurchargeBillingDetailVOs();
	/**
	 * 
	 * @return HashMap<String, Collection<OneTimeVO>>
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

}
