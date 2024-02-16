/*
 * SearchContainerSessionImpl.java Created on Jun 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchContainerSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1876
 *
 */
public class SearchContainerSessionImpl extends AbstractScreenSession
        implements SearchContainerSession {

	private static final String SCREEN_ID = "mailtracking.defaults.searchContainer";
	private static final String MODULE_NAME = "mail.operations";

	private static final String KEY_SEARCHCONTAINERFILTERVO = "searchContainerFilterVO";
	private static final String KEY_CONTAINERVOS = "containerVOs";
	private static final String KEY_INDEXMAP = "indexMap";
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";
	private static final String TOTAL_RECORDS = "totalRecords";//added by A-5201 for CR ICRD-21098
	private static final String PARENT_SCREEN = "MAILHANDLIST";
	
	
	/**
	 * The setter method for SearchContainerFilterVO
	 * @param searchContainerFilterVO
	 */
    public void setSearchContainerFilterVO(
    		SearchContainerFilterVO searchContainerFilterVO) {
    	setAttribute(KEY_SEARCHCONTAINERFILTERVO,searchContainerFilterVO);
    }
    /**
     * The getter method for searchContainerFilterVO
     * @return searchContainerFilterVO
     */
    public SearchContainerFilterVO getSearchContainerFilterVO() {
    	return getAttribute(KEY_SEARCHCONTAINERFILTERVO);
    }
	

	/**
	 * This method is used to set listContainerVOs to the session
	 * @param listContainerVOs - Page<ContainerVO>
	 */
	public void setListContainerVOs(Page<ContainerVO> listContainerVOs){
		setAttribute(KEY_CONTAINERVOS,(Page<ContainerVO>)listContainerVOs);
	}

	/**
	 * This method returns the listContainerVOs
	 * @return listContainerVOs - Page<ContainerVO>
	 */
	public Page<ContainerVO> getListContainerVOs(){
		return (Page<ContainerVO>)getAttribute(KEY_CONTAINERVOS);
	}
	
	/**
	 * This method returns the indexMap
	 * @return indexMap - HashMap<String, String>
	 */
	public HashMap<String, String> getIndexMap(){
		return (HashMap<String, String>)getAttribute(KEY_INDEXMAP);
	}
	
	/**
	 * This method is used to set indexMap to the session
	 * @param indexMap - HashMap<String, String>
	 */
	public void setIndexMap(HashMap<String, String> indexMap){
		setAttribute(KEY_INDEXMAP,indexMap);
	}

    /**
     * @return SCREEN_ID - String
     */
	@Override
	public String getScreenID() {
		return SCREEN_ID;
	}

	/**
     * @return MODULE_NAME - String
     */
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}
	
	/**
	 * @return Returns the oneTimeValues.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
		return getAttribute(KEY_ONETIMEVALUES);
	}

	/**
	 * @param oneTimeValues
	 *            The oneTimeValues to set.
	 */
	public void setOneTimeValues(
			HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
		setAttribute(KEY_ONETIMEVALUES, oneTimeValues);
	}
	
	//added by A-5201 for CR ICRD-21098 starts
	/**	 
	  * This method is used to get total records values from session
	  * from session
	  * @return Integer
	*/
	  public Integer getTotalRecords()
	    {
	        return (Integer)getAttribute(TOTAL_RECORDS);
	    }
	  
	  /**	
		  * This method is used to set total records values in session
		  * @param int
		*/
	    public void setTotalRecords(int totalRecords)
	    {
	        setAttribute(TOTAL_RECORDS, Integer.valueOf(totalRecords));
	    }
	    
	  //added by A-5201 for CR ICRD-21098 end
	    public void setParentScreen(String parentScreen)
	    {
	    	setAttribute(PARENT_SCREEN,parentScreen);
	    }

		public String getParentScreen()
		{
			 return (String)getAttribute(PARENT_SCREEN);
		}

}
