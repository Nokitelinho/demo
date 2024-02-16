/* ULDNavigationVO.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo;


import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * This class is used to represent the details for 
 * creating multiple ULDs
 * 
 * @author A-2001
 *
 */
public class ULDNavigationVO extends AbstractVO{
	
    private String displayPage;
    
    private String currentPage;
    
    private String lastPageNum;
    
    private String totalrecords;
    
    private String screenloadStatus;

	/**
	 * @return Returns the currentPage.
	 */
	public String getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage The currentPage to set.
	 */
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return Returns the displayPage.
	 */
	public String getDisplayPage() {
		return displayPage;
	}

	/**
	 * @param displayPage The displayPage to set.
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	/**
	 * @return Returns the lastPageNum.
	 */
	public String getLastPageNum() {
		return lastPageNum;
	}

	/**
	 * @param lastPageNum The lastPageNum to set.
	 */
	public void setLastPageNum(String lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	/**
	 * @return Returns the totalrecords.
	 */
	public String getTotalrecords() {
		return totalrecords;
	}

	/**
	 * @param totalrecords The totalrecords to set.
	 */
	public void setTotalrecords(String totalrecords) {
		this.totalrecords = totalrecords;
	}

	/**
	 * @return Returns the screenloadStatus.
	 */
	public String getScreenloadStatus() {
		return screenloadStatus;
	}

	/**
	 * @param screenloadStatus The screenloadStatus to set.
	 */
	public void setScreenloadStatus(String screenloadStatus) {
		this.screenloadStatus = screenloadStatus;
	}
    
   
}
