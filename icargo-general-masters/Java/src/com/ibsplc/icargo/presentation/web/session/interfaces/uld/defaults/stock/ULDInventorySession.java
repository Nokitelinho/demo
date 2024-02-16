/*
 * ULDInventorySession.java Created on May 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.InventoryULDVO;
import com.ibsplc.icargo.framework.session.ScreenSession;



/**
 * @author A-2883
 *
 */

public interface ULDInventorySession extends ScreenSession {

	
    public String getScreenID();

    public String getModuleName();   
    

    public String getStatusFlag();
	
	public void setStatusFlag(String flag);
    
	
	 ArrayList<InventoryULDVO> getListInventoryULDDetails();

	 public void setListInventoryULDDetails(ArrayList<InventoryULDVO> listInventoryULDDetails);
	 
	 ArrayList<InventoryULDVO> getDisplayInventoryDetails();

	 public void setDisplayInventoryDetails(ArrayList<InventoryULDVO> displayInventoryDetails);
	 
	 public String getInventoryPageFlag();
		
	 public void setInventoryPageFlag(String flag);
		
	    
}