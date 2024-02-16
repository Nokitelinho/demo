/*
 * MaintainPrivilegeSessionInterface.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults;

import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;

import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1366
 *
 */
public interface ListSubProductSessionInterface extends ScreenSession {
	/**
	 * Method for getting status from session
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO>  getStatus();
	/**
	 * Method for setting status to session
	 * @param status
	 */
	public void setStatus(Collection<OneTimeVO> status);
	/**
	 * Method for removing status from session
	 *@return void
	 */
	public void removeStatus();
	/**
	 * Method for getting priority from session
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO>  getPriority();
	/**
	 * Method for setting priority to session
	 * @param priority
	 */
	public void setPriority(Collection<OneTimeVO> priority);
	/**
	 * Method for removing priority from session
	 * @return void
	 */
	public void removePriority();
	/**
	 * Method for getting transportmode from session
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO>  getTransportMode();
	/**
	 * Method for setting transportmode to session
	 * @param transportMode
	 */
	public void setTransportMode(Collection<OneTimeVO> transportMode);
	/**
	 * Method for removing transportmode from session
	 *@return void
	 */
	public void removeTransportMode();
	/**
	 * Method for getting getPageSubProductVO from session
	 * @return Page<SubProductVO
	 */
	public Page<SubProductVO>   getPageSubProductVO();
	/**
	 * Method for setting getPageSubProductVO to session
	 * @param pageSubProductVO
	 */
	public void setPageSubProductVO(Page<SubProductVO> pageSubProductVO);
	/**
	 * Method for removing getPageSubProductVO from session
	 *@return void
	 */
	public void removePageSubProductVO();
	
	//Added now
	
	public ProductFilterVO getProductFilterVODetails();
    
    public void setProductFilterVODetails(ProductFilterVO productDetails);
     
    public void removeProductFilterVODetails();	
    
    public String getButtonStatusFlag();
    public void setButtonStatusFlag(String buttonStatusFlag);
    /**
     * 
     * @return
     */
    public Integer getTotalRecordsCount();
/**
 * 
 * @param paramInt
 */
    public void setTotalRecordsCount(int paramInt);
/**
 * 
 */
    public void removeTotalRecordsCount();
}
