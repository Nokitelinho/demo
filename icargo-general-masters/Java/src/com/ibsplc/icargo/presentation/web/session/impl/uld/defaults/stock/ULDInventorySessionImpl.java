/*
 * ULDInventorySessionImpl.java Created on May 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.stock;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.InventoryULDVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.ULDInventorySession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;



/**
 * @author A-2883
 *
 */
	public class ULDInventorySessionImpl extends AbstractScreenSession
	implements ULDInventorySession {
	
	


		private static final String MODULE = "uld.defaults";
		
		private static final String SCREENID =
		"uld.defaults.stock.inventoryuld";
		
		private static final String LISTPAGE = "listInventoryDetails";
		
		private static final String SCREENSTATUS = "screenStatus";
		
		private static final String HASHMAP_INDEXMAP = "indexMap";
		
		private static final String FLIGHTSTATUS = "flightStatus";
		
		private static final String FLIGHTDETAILS = "listFlightDetails";
		
		private static final String PAGEFLIGHTDETAILS = "pageFlightDetails";
		
		private static final String LISTINVDET = "listULDInventoryDetails";
		
		private static final String INVENTORYDET = "inventoryDetails";
		
		private static final String DISPINVENTORY = "displayInventory";
		
		private static final String PAGEINVENTORY = "pageInventory";
		
		
		/** Method to get ScreenID
		*
		* @return ScreenID
		*/
		public String getScreenID() {
			return SCREENID;
		}
		
		/**
		* @return ProductName
		*/
		public String getModuleName() {
		return MODULE;
		}

		
		public Page<InventoryULDVO> getListInventoryDetails() {
			 return getAttribute(LISTPAGE);
		}
		
	
	    public void setListInventoryDetails(Page<InventoryULDVO> inventoryULDVO) {
	    	setAttribute(LISTPAGE,inventoryULDVO);
	    }

	    public String getStatusFlag() {
			return getAttribute(SCREENSTATUS);
		}
		
		
		public void setStatusFlag(String flag) {
			setAttribute(SCREENSTATUS, flag);
			
		}

		public HashMap<String, String> getIndexMap() {
	        return getAttribute(HASHMAP_INDEXMAP);
	    }

	    public void setIndexMap(HashMap<String, String> indexMap) {
	        setAttribute(HASHMAP_INDEXMAP,indexMap);    
	    }

	    public String getFlightStatusFlag() {
			return getAttribute(FLIGHTSTATUS);
		}
		
		
		public void setFlightStatusFlag(String flag) {
			setAttribute(FLIGHTSTATUS, flag);
			
		}

		
		public ArrayList<InventoryULDVO> getListFlightDetails() {
			 return getAttribute(FLIGHTDETAILS);
		}

		
		public void setListFlightDetails(ArrayList<InventoryULDVO> listFlightDetails) {
			setAttribute(FLIGHTDETAILS, listFlightDetails);
			
		}

		
		public Page<InventoryULDVO> getPageFlightDetails() {
			return getAttribute(PAGEFLIGHTDETAILS);
		}

		
		public void setPageFlightDetails(Page<InventoryULDVO> pageFlightDetails) {
			setAttribute(PAGEFLIGHTDETAILS,pageFlightDetails);
			
		}

		
		public ArrayList<InventoryULDVO> getListInventoryULDDetails() {
			 return getAttribute(LISTINVDET);
			
		}

		public void setListInventoryULDDetails(ArrayList<InventoryULDVO> listInventoryULDDetails) {
			setAttribute(LISTINVDET,listInventoryULDDetails);
			
		}

		
		public ArrayList<InventoryULDVO> getInventoryULDDetails() {
			 return getAttribute(INVENTORYDET);
			
		}

		public void setInventoryULDDetails(ArrayList<InventoryULDVO> inventoryULDDetails) {
			setAttribute(INVENTORYDET,inventoryULDDetails);
			
		}

		
		public ArrayList<InventoryULDVO> getDisplayInventoryDetails() {
			 return getAttribute(DISPINVENTORY);
		}

		public void setDisplayInventoryDetails(ArrayList<InventoryULDVO> displayInventoryDetails) {
			setAttribute(DISPINVENTORY,displayInventoryDetails);
			
		}

		public void setInventoryPageFlag(String flag) {
			setAttribute(PAGEINVENTORY,flag);
			
		}

		
		public String getInventoryPageFlag() {
			return getAttribute(PAGEINVENTORY);
		}
	
		
	    
	}
