/*
 * MailbagReconciliationSession.java Created on Oct 12, 2010
 *
 * Copyright 2010 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailReconciliationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailReconciliationFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * 
 * @author A-3270
 *
 */
public interface MailbagReconciliationSession extends ScreenSession {
	/**
	 * @return Returns the MailReconciliationDetailsVOs.
	 */
	public Page<MailReconciliationDetailsVO> getMailReconciliationDetailsVOs();

	/**
	 * @param forwarderAreaVOs The MailReconciliationDetailsVOs to set.
	 */

	public void setMailReconciliationDetailsVOs(Page<MailReconciliationDetailsVO> mailReconciliationDetailsVOs);
	
	/**
     * This method removes the MailReconciliationDetailsVOs in session
     */
	public void removeMailReconciliationDetailsVOs();
	
	/**
	 * @return Returns the MailReconciliationFilterVO.
	 */
	public MailReconciliationFilterVO getMailReconciliationFilterVO();

	/**
	 * @param forwarderAreaVOs The MailReconciliationVOVOs to set.
	 */

	public void setMailReconciliationFilterVO(MailReconciliationFilterVO mailReconciliationFilterVO);
	
	/**
     * This method removes the MailReconciliationFilterVO in session
     */
	public void removeMailReconciliationFilterVO();
	
	/**
	 * @return Returns the OneTimeType.
	 */
	public Collection<OneTimeVO> getOneTimeType();
	
	/**
	 * @param OneTimeType The OneTimeType to set.
	 */
	public void setOneTimeType(Collection<OneTimeVO> oneTimeType);
	
	/**
     * This method removes the OneTimeType in session
     */
	public void removeOneTimeType();
	
	/**
	 * @param delayPeiod The delayPeiod to set.
	 */
	public void setDelayPeriod(int delayPeiod);
	
	 /**
	 * Method to get the DelayPeriod
	 */
   public Integer getDelayPeriod();
   /**
	 * Method to remove the DelayPeriod
	*/
   public void removeDelayPeriod() ;

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
	
	public Integer  getTotalRecords();
	 /**
		 * @param totalRecords The totalRecords to set.
		 */
	 public void setTotalRecords(int totalRecords);


}
