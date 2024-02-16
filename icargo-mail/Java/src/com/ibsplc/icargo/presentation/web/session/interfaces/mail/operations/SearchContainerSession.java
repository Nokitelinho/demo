/*
 * SearchContainerSession.java Created on Jun 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1556
 *
 */
public interface SearchContainerSession extends ScreenSession {

	/**
	 * The setter method for searchContainerFilterVO
	 * @param searchContainerFilterVO
	 */
	public void setSearchContainerFilterVO(
    		SearchContainerFilterVO searchContainerFilterVO);
    /**
     * The getter method for SearchContainerFilterVO
     * @return SearchContainerFilterVO
     */
    public SearchContainerFilterVO getSearchContainerFilterVO();
	
	/**
	 * This method is used to set ContainerVOs to the session
	 * @param containervos - Page<ContainerVO>
	 */
	public void setListContainerVOs(Page<ContainerVO> listContainerVOs);

	/**
	 * This method returns the ContainerVOs
	 * @return CONTAINERVOS - Page<ContainerVO>
	 */
	public Page<ContainerVO> getListContainerVOs();
	
	/**
	 * The setter method for indexMap
	 * @param indexMap
	 */
	public void setIndexMap(
			HashMap<String, String> indexMap);
    /**
     * The getter method for indexMap
     * @return indexMap
     */
    public HashMap<String, String> getIndexMap();
    
    /**
	 * @return Returns the oneTimeValues.
	 */
	HashMap<String,Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */

	void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	
	public abstract Integer getTotalRecords(); //added by A-5201 for CR ICRD-21098

	public abstract void setTotalRecords(int i); //added by A-5201 for CR ICRD-21098
	
	public void setParentScreen(String parentScreen);//added by A-6371 for CR ICRD-83340
	
	public String getParentScreen();

}

