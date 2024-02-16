/*
 * ListCustomerPointsSessionImpl.java Created on Apr 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.customermanagement.defaults.loyalty;


import java.util.ArrayList;
import java.util.HashMap;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListCustomerPointsVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListPointsAccumulatedFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ListCustomerPointsSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;



/**
 * @author A-2052
 *
 */

public class ListCustomerPointsSessionImpl extends AbstractScreenSession
		implements ListCustomerPointsSession {
/**
 * 
 */
    private static final String MODULENAME = "customermanagement.defaults";
    /**
     * 
     */
    private static final String SCREENID = "customermanagement.defaults.listcustomerpoints";
  /**
   * 
   */
	public static final String KEY_FILTERDETAILS = "filterDetails";
	/**
	 * @param String
	 */
	public static final String KEY_PAGE = "page";
	private static final String KEY_INDEXMAP="index";
	private static final String KEY_SHIPMENTVOS = "shipmentVOs";
	private static final String KEY_CODES = "customerCodes";
	private static final String KEY_TOTALRECORDS = "totalrecords";
	//Added by A-5220 for ICRD-46939
	private static final String KEY_WGT_UNT = "customermanagement.defaults.listcustomerpoints.weightunit";
	private static final String KEY_VOL_UNT = "customermanagement.defaults.listcustomerpoints.volumeunit";
	/**
	 * 
	 */
	public String getScreenID() {
		return SCREENID;
	}
	/**
	 * 
	 */
	public String getModuleName() {
		return MODULENAME;
	}
	/**
	 * 
	 */
	 public ListPointsAccumulatedFilterVO getFilterDetailsOnReturn(){
	    return (ListPointsAccumulatedFilterVO)getAttribute(KEY_FILTERDETAILS);
	 }
/**
 * @param filterDetails
 */
	 public void setFilterDetailsOnReturn(ListPointsAccumulatedFilterVO filterDetails){
	   	setAttribute(KEY_FILTERDETAILS, (ListPointsAccumulatedFilterVO)filterDetails);
	 }	
	 /**
	  * 
	  */
	 public void removeFilterDetailsOnReturn() {
    	removeAttribute(KEY_FILTERDETAILS);
	 }
/**
 * @return Page<ListCustomerPointsVO>
 */
	 public Page<ListCustomerPointsVO> getPage(){
	    return (Page<ListCustomerPointsVO>)getAttribute(KEY_PAGE);
	 }
	 /**
	  * @param page
	  */
	 public void setPage(Page<ListCustomerPointsVO> page){
	   	setAttribute(KEY_PAGE, (Page<ListCustomerPointsVO>)page);
	 }
	 /**
	  * 
	  */
	 public void removePage() {
    	removeAttribute(KEY_PAGE);
	 }
	 /**
	     * Method used to get indexMap
	     * @return KEY_INDEXMAP - HashMap<String,String>
	     */
		public HashMap<String,String>  getIndexMap(){
		    return (HashMap<String,String>)getAttribute(KEY_INDEXMAP);
		}

		/**
		 * Method used to set indexMap to session
		 * @param indexmap - HashMap<String,String>
		 */
		public void setIndexMap(HashMap<String,String>  indexmap) {
		    setAttribute(KEY_INDEXMAP, (HashMap<String,String>)indexmap);
		}
		/**
	     * This method removes the indexMAp in session
	     */
		public void removeIndexMap(){
			removeAttribute(KEY_INDEXMAP);
		}
		/**
		 * @return Returns the shipmentVOs.
		 */
		public ArrayList<ShipmentVO> getShipmentVOs() {
			return getAttribute(KEY_SHIPMENTVOS);
		}

		/**
		 * @param shipmentVOs
		 */
		public void setShipmentVOs(ArrayList<ShipmentVO> shipmentVOs) {
			setAttribute(KEY_SHIPMENTVOS,shipmentVOs);
		}
		/**
		 * 
		 */
		public ArrayList<String> getSelectedCustomerCodes(){
			return getAttribute(KEY_CODES);
		}
		/**
		 * @param custCodes
		 */
		public void setSelectedCustomerCodes(ArrayList<String> custCodes){
			setAttribute(KEY_CODES,custCodes);
		}
		/**
		 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ListCustomerPointsSession#getTotalRecords()
		 *	Added by 			: A-5175 on 24-Oct-2012
		 * 	Used for 	:   icrd-22065
		 *	Parameters	:	@return 
		 */
		
		public Integer getTotalRecords() {
			return getAttribute(KEY_TOTALRECORDS);
		}
		/**
		 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ListCustomerPointsSession#setTotalRecords(int)
		 *	Added by 			: A-5175 on 24-Oct-2012
		 * 	Used for 	:   icrd-22065
		 *	Parameters	:	@param totalRecords 
		 */
		
		public void setTotalRecords(int totalRecords) {
			setAttribute(KEY_TOTALRECORDS, totalRecords);
		}
		/**
		   * Method		:	ListCustomerPointsSessionImpl.setWeightVO
		   * Added by 	:	A-5220 on 26-FEB-2014 for ICRD-46939
		   * Used for 	:	Setting weight unit vo
		   * Return type	: 	void
		   */
		 public void setWeightVO(UnitRoundingVO unitRoundingVO){
			 setAttribute(KEY_WGT_UNT, unitRoundingVO);
		 }
		 /**
		   * Method		:	ListCustomerPointsSessionImpl.getWeightVO
		   * Added by 	:	A-5220 on 26-FEB-2014 for ICRD-46939
		   * Used for 	:	getting weight unit vo
		   * Return type	: 	UnitRoundingVO
		   */
		  public UnitRoundingVO getWeightVO() {
			  return (UnitRoundingVO)getAttribute(KEY_WGT_UNT);
		  }
		  /**
			   * Method		:	ListCustomerPointsSessionImpl.setVolumeVO
			   * Added by 	:	A-5220 on 26-FEB-2014 for ICRD-46939
			   * Used for 	:	Setting volume unit vo
			   * Return type	: 	void
			   */
		  public void setVolumeVO(UnitRoundingVO unitRoundingVO){
			  setAttribute(KEY_VOL_UNT, unitRoundingVO);
		  }
		  /**
			   * Method		:	ListCustomerPointsSessionImpl.getVolumeVO
			   * Added by 	:	A-5220 on 26-FEB-2014 for ICRD-46939
			   * Used for 	:	getting volume unit vo
			   * Return type	: 	UnitRoundingVO
			   */
		  public UnitRoundingVO getVolumeVO() {
			  return (UnitRoundingVO)getAttribute(KEY_VOL_UNT);
		  }
}
