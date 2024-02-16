/*
 * MaintainLoyaltySession.java Created on Apr 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListCustomerPointsVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListPointsAccumulatedFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2052
 *
 */
public interface ListCustomerPointsSession extends ScreenSession {
	    
	public ListPointsAccumulatedFilterVO getFilterDetailsOnReturn();
	
	 public void setFilterDetailsOnReturn(ListPointsAccumulatedFilterVO filterDetails);
	 
	 public void removeFilterDetailsOnReturn();
	 
	 public Page<ListCustomerPointsVO> getPage();
	 
	 public void setPage(Page<ListCustomerPointsVO> page);
	 
	 public void removePage();
	 /**
	     * Method used to get indexMap
	     * @return KEY_INDEXMAP - HashMap<String,String>
	     */
		public HashMap<String,String>  getIndexMap();
		
		/**
		 * Method used to set indexMap to session
		 * @param indexmap - HashMap<String,String>
		 */
		public void setIndexMap(HashMap<String,String>  indexmap);
		
		/**
	     * This method removes the indexMAp in session
	     */
		public void removeIndexMap();
		
	    /**
		 * @return Returns the shipmentVOs.
		 */
		public ArrayList<ShipmentVO> getShipmentVOs();
		/**
		 * @param shipmentVOs The shipmentVOs to set.
		 */
		public void setShipmentVOs(ArrayList<ShipmentVO> shipmentVOs);
		
		public ArrayList<String> getSelectedCustomerCodes();
		
		public void setSelectedCustomerCodes(ArrayList<String> custCodes);
		
		//added by A-5175 for QF CR 22065 starts
		public Integer getTotalRecords();
		
		public void setTotalRecords(int totalRecords);
		//added by A-5175 for QF CR 22065 ends
		/**
		   * Method		:	ListCustomerPointsSession.setWeightVO
		   * Added by 	:	A-5220 on 26-FEB-2014 for ICRD-46939
		   * Used for 	:	Setting weight unit vo
		   * Return type	: 	void
		   */
		 public void setWeightVO(UnitRoundingVO unitRoundingVO);
		 /**
		   * Method		:	ListCustomerPointsSession.getWeightVO
		   * Added by 	:	A-5220 on 26-FEB-2014 for ICRD-46939
		   * Used for 	:	getting weight unit vo
		   * Return type	: 	UnitRoundingVO
		   */
	    public UnitRoundingVO getWeightVO() ;
	    /**
		   * Method		:	ListCustomerPointsSession.setVolumeVO
		   * Added by 	:	A-5220 on 26-FEB-2014 for ICRD-46939
		   * Used for 	:	Setting volume unit vo
		   * Return type	: 	void
		   */
	    public void setVolumeVO(UnitRoundingVO unitRoundingVO);
	    /**
		   * Method		:	ListCustomerPointsSession.getVolumeVO
		   * Added by 	:	A-5220 on 26-FEB-2014 for ICRD-46939
		   * Used for 	:	getting volume unit vo
		   * Return type	: 	UnitRoundingVO
		   */
	    public UnitRoundingVO getVolumeVO() ;
}
