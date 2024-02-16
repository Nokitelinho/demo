/*
 * MaintainULDStockForm.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 * @author A-1347
 *
 */
public class MaintainULDStockForm extends ScreenModel {

	private static final String BUNDLE = "maintainuldstock";
	private static final String PRODUCT = "uld";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "uld.defaults.maintainuldstock";
	private String airlineIdentifier;
	private String airlineCode;
	private String stationCode;
	private String airline;
	private String station;
	private String selectedRows;
	private String rowContents;
	private String fromScreen;
	private String uldTypeCode;
	private String minimumQty;
	private String maximumQty;
	private String createStatus;
	private String validateStatus;
	private String filterStatus;
	private String linkStatus;
	private String stationMain="";
	private String airlineMain="";
	private String[] airlineIdentifiers;
	private String[] stationCodes;
	private String[] uldTypeCodes;
	private String[] minQty;
	private String[] maxQty;
	private String uldNature;
	

	//added by ayswarya
	private String statusFlag="";
	
	private String stkDisableStatus="";

	private String dmgdisplayPage = "1";
	private String dmglastPageNum =  "0";
	private String dmgtotalRecords = "0";
	private String dmgcurrentPageNum = "1";
	
	private String repdisplayPage = "1";
	private String replastPageNum =  "0";
	private String reptotalRecords = "0";
	private String repcurrentPageNum = "1";
	
	private String flag="";
	
	private String[] masterRowId;
	 private String[] rowId;
	 
	private String bundle;
	private String closeStatus;
	private String screenloadstatus;
	
	//Added by A-2412
	private String disableUldNature;
	//added by a-2883
	private String listStatus;
	private String monitorViewByNature;
	private String uldGroupCode;
	private String dwellTime;	
	
	private String remarks;
    

	
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the uldGroupCode
	 */
	public String getUldGroupCode() {
		return uldGroupCode;
	}

	/**
	 * @param uldGroupCode the uldGroupCode to set
	 */
	public void setUldGroupCode(String uldGroupCode) {
		this.uldGroupCode = uldGroupCode;
	}

	/**
	 * @return the monitorViewByNature
	 */
	public String getMonitorViewByNature() {
		return monitorViewByNature;
	}

	/**
	 * @param monitorViewByNature the monitorViewByNature to set
	 */
	public void setMonitorViewByNature(String monitorViewByNature) {
		this.monitorViewByNature = monitorViewByNature;
	}

	public String getListStatus() {
		return listStatus;
	}

	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}

	public String getDisableUldNature() {
		return disableUldNature;
	}

	public void setDisableUldNature(String disableUldNature) {
		this.disableUldNature = disableUldNature;
	}

	/**
	 * @return Returns the screenloadstatus.
	 */
	public String getScreenloadstatus() {
		return screenloadstatus;
	}

	/**
	 * @param screenloadstatus The screenloadstatus to set.
	 */
	public void setScreenloadstatus(String screenloadstatus) {
		this.screenloadstatus = screenloadstatus;
	}

	/**
     * @return
     */
    public String getFilterStatus() {
		return filterStatus;
	}

	/**
	 * @param filterStatus
	 */
	public void setFilterStatus(String filterStatus) {
		this.filterStatus = filterStatus;
	}

	/**
     * @return String
     */
    public String getLinkStatus() {
		return linkStatus;
	}

	/**
	 * @param linkStatus
	 */
	public void setLinkStatus(String linkStatus) {
		this.linkStatus = linkStatus;
	}

	/**
	 * @return String
	 */
	public String getRowContents() {
		return rowContents;
	}

	/**
	 * @param rowContents
	 */
	public void setRowContents(String rowContents) {
		this.rowContents = rowContents;
	}

	/**
     * @return String
     */
    public String getValidateStatus() {
		return validateStatus;
	}

	/**
	 * @param validateStatus
	 */
	public void setValidateStatus(String validateStatus) {
		this.validateStatus = validateStatus;
	}

	/**
     * Method to return the product the screen is associated with
     *
     * @return String
     */
    public String getProduct() {
        return PRODUCT;
    }

    /**
     * Method to return the sub product the screen is associated with
     *
     * @return String
     */
    public String getSubProduct() {
        return SUBPRODUCT;
    }

    /**
     * Method to return the id the screen is associated with
     *
     * @return String
     */
    public String getScreenId() {
        return SCREENID;
    }
/**
 * 
 */
    public String getBundle() {
	    return BUNDLE;
    }
/**
 * 
 * @param airlineIdentifier
 */
    public void setAirlineIdentifier(String airlineIdentifier) {
    	this.airlineIdentifier = airlineIdentifier;
    }
/**
 * 
 * @return
 */
    public String getAirlineIdentifier() {
    	return airlineIdentifier;
    }
/**
 * 
 * @param stationCode
 */
    public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
/**
 * 
 * @return
 */
	public String getStationCode() {
		return stationCode;
    }
/**
 * 
 * @param station
 */
	public void setStation(String station) {
		this.station = station;
	}
/**
 * 
 * @param airline
 */
    public void setAirline(String airline) {
    	this.airline = airline;
    }
/**
 * 
 * @param selectedRows
 */
    public void setSelectedRows(String selectedRows) {
	    this.selectedRows = selectedRows;
    }

/**
 * 
 * @param fromScreen
 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
/**
 * 
 * @return
 */
	public String getFromScreen() {
		return fromScreen;
    }
/**
 * 
 * @param createStatus
 */
    public void setCreateStatus(String createStatus) {
		this.createStatus = createStatus;
	}
/**
 * 
 * @return
 */
	public String getCreateStatus() {
		return createStatus;
	}
/**
 * 
 * @param airlineCode
 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
/**
 * 
 * @return
 */
	public String getAirlineCode() {
		return airlineCode;
	}
/**
 * 
 * @return
 */

    public String getSelectedRows() {
	    return selectedRows;
    }
/**
 * 
 * @return
 */
    public String getAirline() {
    	return airline;
    }
    /**
     * 
     * @return
     */
	public String getStation() {
		return station;
    }
/**
 * 
 * @param uldTypeCode
 */
	public void setUldTypeCode(String uldTypeCode) {
		this.uldTypeCode = uldTypeCode;
	}
/**
 * 
 * @return
 */
	public String getUldTypeCode() {
		return uldTypeCode;
    }
/**
 * 
 * @param minimumQty
 */
	public void setMinimumQty(String minimumQty) {
		this.minimumQty = minimumQty;
	}
/**
 * 
 * @return
 */
	public String getMinimumQty() {
		return minimumQty;
    }
/**
 * 
 * @param maximumQty
 */
	    public void setMaximumQty(String maximumQty) {
			this.maximumQty = maximumQty;
		}
/**
 * 
 * @return
 */
		public String getMaximumQty() {
			return maximumQty;
    }
/**
 * 
 * @param airlineIdentifiers
 */
    public void setAirlineIdentifiers(String[] airlineIdentifiers) {
		this.airlineIdentifiers = airlineIdentifiers;
	}
/**
 * 
 * @return
 */
	public String[] getAirlineIdentifiers() {
		return airlineIdentifiers;
    }
/**
 * 
 * @param stationCodes
 */
    public void setStationCodes(String[] stationCodes) {
		this.stationCodes = stationCodes;
	}
/**
 * 
 * @return
 */
	public String[] getStationCodes() {
		return stationCodes;
    }
/**
 * 
 * @param uldTypeCodes
 */
	public void setUldTypeCodes(String[] uldTypeCodes) {
		this.uldTypeCodes = uldTypeCodes;
	}
/**
 * 
 * @return
 */
	public String[] getUldTypeCodes() {
		return uldTypeCodes;
    }
/**
 * 
 * @param minQty
 */
    public void setMinQty(String[] minQty) {
		this.minQty = minQty;
	}
/**
 * 
 * @return
 */
	public String[] getMinQty() {
		return minQty;
    }
/**
 * 
 * @param maxQty
 */
     public void setMaxQty(String[] maxQty) {
		this.maxQty = maxQty;
	}
/**
 * 
 * @return
 */
	public String[] getMaxQty() {
		return maxQty;
    }

	/**
	 * @return Returns the statusFlag.
	 */
	public String getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag The statusFlag to set.
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	/**
	 * @return Returns the dmgcurrentPageNum.
	 */
	public String getDmgcurrentPageNum() {
		return dmgcurrentPageNum;
	}

	/**
	 * @param dmgcurrentPageNum The dmgcurrentPageNum to set.
	 */
	public void setDmgcurrentPageNum(String dmgcurrentPageNum) {
		this.dmgcurrentPageNum = dmgcurrentPageNum;
	}

	/**
	 * @return Returns the dmgdisplayPage.
	 */
	public String getDmgdisplayPage() {
		return dmgdisplayPage;
	}

	/**
	 * @param dmgdisplayPage The dmgdisplayPage to set.
	 */
	public void setDmgdisplayPage(String dmgdisplayPage) {
		this.dmgdisplayPage = dmgdisplayPage;
	}

	/**
	 * @return Returns the dmglastPageNum.
	 */
	public String getDmglastPageNum() {
		return dmglastPageNum;
	}

	/**
	 * @param dmglastPageNum The dmglastPageNum to set.
	 */
	public void setDmglastPageNum(String dmglastPageNum) {
		this.dmglastPageNum = dmglastPageNum;
	}

	/**
	 * @return Returns the dmgtotalRecords.
	 */
	public String getDmgtotalRecords() {
		return dmgtotalRecords;
	}

	/**
	 * @param dmgtotalRecords The dmgtotalRecords to set.
	 */
	public void setDmgtotalRecords(String dmgtotalRecords) {
		this.dmgtotalRecords = dmgtotalRecords;
	}

	/**
	 * @return Returns the repcurrentPageNum.
	 */
	public String getRepcurrentPageNum() {
		return repcurrentPageNum;
	}

	/**
	 * @param repcurrentPageNum The repcurrentPageNum to set.
	 */
	public void setRepcurrentPageNum(String repcurrentPageNum) {
		this.repcurrentPageNum = repcurrentPageNum;
	}

	/**
	 * @return Returns the repdisplayPage.
	 */
	public String getRepdisplayPage() {
		return repdisplayPage;
	}

	/**
	 * @param repdisplayPage The repdisplayPage to set.
	 */
	public void setRepdisplayPage(String repdisplayPage) {
		this.repdisplayPage = repdisplayPage;
	}

	/**
	 * @return Returns the replastPageNum.
	 */
	public String getReplastPageNum() {
		return replastPageNum;
	}

	/**
	 * @param replastPageNum The replastPageNum to set.
	 */
	public void setReplastPageNum(String replastPageNum) {
		this.replastPageNum = replastPageNum;
	}

	/**
	 * @return Returns the reptotalRecords.
	 */
	public String getReptotalRecords() {
		return reptotalRecords;
	}

	/**
	 * @param reptotalRecords The reptotalRecords to set.
	 */
	public void setReptotalRecords(String reptotalRecords) {
		this.reptotalRecords = reptotalRecords;
	}

	/**
	 * @return Returns the flag.
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag The flag to set.
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return Returns the masterRowId.
	 */
	public String[] getMasterRowId() {
		return masterRowId;
	}

	/**
	 * @param masterRowId The masterRowId to set.
	 */
	public void setMasterRowId(String[] masterRowId) {
		this.masterRowId = masterRowId;
	}

	/**
	 * @return Returns the rowId.
	 */
	public String[] getRowId() {
		return rowId;
	}

	/**
	 * @param rowId The rowId to set.
	 */
	public void setRowId(String[] rowId) {
		this.rowId = rowId;
	}

	/**
	 * @return Returns the closeStatus.
	 */
	public String getCloseStatus() {
		return closeStatus;
	}

	/**
	 * @param closeStatus The closeStatus to set.
	 */
	public void setCloseStatus(String closeStatus) {
		this.closeStatus = closeStatus;
	}

	/**
	 * @return Returns the stkDisableStatus.
	 */
	public String getStkDisableStatus() {
		return stkDisableStatus;
	}

	/**
	 * @param stkDisableStatus The stkDisableStatus to set.
	 */
	public void setStkDisableStatus(String stkDisableStatus) {
		this.stkDisableStatus = stkDisableStatus;
	}

	/**
	 * @return Returns the airlineMain.
	 */
	public String getAirlineMain() {
		return airlineMain;
	}

	/**
	 * @param airlineMain The airlineMain to set.
	 */
	public void setAirlineMain(String airlineMain) {
		this.airlineMain = airlineMain;
	}

	/**
	 * @return Returns the stationMain.
	 */
	public String getStationMain() {
		return stationMain;
	}

	/**
	 * @param stationMain The stationMain to set.
	 */
	public void setStationMain(String stationMain) {
		this.stationMain = stationMain;
	}

	/**
	 * @return Returns the uldNature.
	 */
	public String getUldNature() {
		return uldNature;
	}

	/**
	 * @param uldNature The uldNature to set.
	 */
	public void setUldNature(String uldNature) {
		this.uldNature = uldNature;
	}

	/**
	 * @return the dwellTime
	 */
	public String getDwellTime() {
		return dwellTime;
	}

	/**
	 * @param dwellTime the dwellTime to set
	 */
	public void setDwellTime(String dwellTime) {
		this.dwellTime = dwellTime;
	}

 }
