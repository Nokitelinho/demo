/* TransferDispatchSession.java Created on Feb 2, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
/**
 * @author A-4810
 *
 */
public interface TransferDispatchSession extends ScreenSession {

	/**
	 * The setter method for MailArrivalVO
	 * @param mailArrivalVO
	 */
	public void setMailArrivalVO(MailArrivalVO mailArrivalVO);
    /**
     * The getter method for MailArrivalVO
     * @return MailArrivalVO
     */
    public MailArrivalVO getMailArrivalVO();
    
    /**
     * This method is used to set ContainerDetailsVOs to the session
     * @param containerDetailsVOs - Collection<ContainerDetailsVO>
     */
	public void setContainerDetailsVOs(Collection<ContainerDetailsVO> containerDetailsVOs);

	/**
     * This method returns the ContainerDetails vos
     * @return containerDetailsVOs - Collection<ContainerDetailsVO>
     */
	public Collection<ContainerDetailsVO> getContainerDetailsVOs();
	/**
	 * The setter method for containerDetailsVO
	 * @param containerDetailsVO
	 */
	public void setContainerDetailsVO(ContainerDetailsVO containerDetailsVO);
    /**
     * The getter method for containerDetailsVO
     * @return containerDetailsVO
     */
    public ContainerDetailsVO getContainerDetailsVO();
    
    /**
     * The getter method for DespatchDetailsVO
     * @return despatchDetailsVO
     */
    
    
   public Collection<DespatchDetailsVO> getDespatchDetailsVOs();
   /**
	 * The setter method for DespatchDetailsVO
	 * @param despatchDetailsVO
	 */
   public void setDespatchDetailsVOs(Collection<DespatchDetailsVO> despatchDetailsVOs);
    
   /**
	 * The setter method for containerDetailsVO
	 * @param containerDetailsVO
	 */
	public void setDespatchDetailsVO(DespatchDetailsVO despatchDetailsVO);
   /**
    * The getter method for containerDetailsVO
    * @return containerDetailsVO
    */
   public DespatchDetailsVO getDespatchDetailsVO();
   
	
}


