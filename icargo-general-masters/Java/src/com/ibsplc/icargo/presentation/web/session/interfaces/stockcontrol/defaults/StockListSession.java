/*
 * StockListSession.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;

import java.util.Collection;
import java.util.HashMap;

//import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1619
 *
 */
public interface StockListSession extends ScreenSession {

    /**
     * @param 
     * @return HashMap<String,Collection<String>>
     */
	 	public HashMap<String,Collection<String>> getDocumentValues() ;
	    
	 	/**
	     * @param document
	     * @return 
	     */
		public void setDocumentValues(HashMap<String,Collection<String>>  document);
		
		
		/**
	     * @param 
	     * @return Page<StockVO>
	     */
		public Page<StockVO> getStockVOs();
		/**
	     * @param paramCode
	     * @return 
	     */
		public void setStockVOs(Page<StockVO> paramCode);
		/**
	     * @param 
	     * @return 
	     */
		public void removeStockVOs();
		/**
	     * @param 
	     * @return 
	     */
		public void removeDocumentValues();
		/**
	     * @param 
	     * @return HashMap<String,String>
	     */
		public HashMap<String,String>  getIndexMap();
		/**
	     * @param indexmap
	     * @return 
	     */
		
		public void setIndexMap(HashMap<String,String>  indexmap);
		/**
	     * @param 
	     * @return 
	     */
		public void removeIndexMap();
		/**
	     * @param 
	     * @return StockFilterVO
	     */
		public StockFilterVO  getFilterVO();

		/**
		 * Method used to set indexMap to session
		 * @param stockFilterVO
		 */
		public void setFilterVO(StockFilterVO  stockFilterVO);
		/**
	     * This method removes the indexMAp in session
	     */
		public void removeFilterVO();
		/**
	     * @param 
	     * @return Collection<DocumentVO>
	     */
		public Collection<DocumentVO>  getDocumentVO();

		/**
	     * @param stockFilterVO
	     * @return 
	     */
		public void setDocumentVO(Collection<DocumentVO>  stockFilterVO);
		/**
	     * @param 
	     * @return 
	     */
		public void removeDocumentVO();
		/**
	     * @param 
	     * @return StockFilterVO
	     */
		public StockFilterVO  getFilterFromList();

		/**
		 * Method used to set indexMap to session
		 * @param stockFilterVO
		 */
		public void setFilterFromList(StockFilterVO  stockFilterVO);
		/**
	     * @param 
	     * @return 
	     */
		public void removeFilterFromList();
		/**
	     * @param 
	     * @return String
	     */
		public String  getStringFromList();

		/**
		 * Method used to set indexMap to session
		 * @param stockFilterVO
		 */
		public void setStringFromList(String  stockFilterVO);
		/**
	     * @param 
	     * @return 
	     */
		public void removeStringFromList();
		
		void setTotalRecords(int totalRecords);//Added by A-5221 as part from the ICRD-23107
		
		Integer getTotalRecords();//Added by A-5221 as part from the ICRD-23107
		
		
   
}
