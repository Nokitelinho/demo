/*
 * StockManagerSession.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1619
 *
 */
public interface StockManagerSession extends ScreenSession {
	
	/**
    * @return documentVO
    */
	public Collection<DocumentVO> getDocumentVO();
   /**
    * @param documentVO
    */
	public void setDocumentVO(Collection<DocumentVO> documentVO);
	/**
    * @return homeairlinecode
    */
	public String getHomeairlinecode();
   /**
    * @param homeairlinecode
    */
	public void setHomeairlinecode(String homeairlinecode);
	/**
    * 
    */
	public void removeStockAllocationVO();
	/**
    * @return airlineId
    */
	public int getAirlineId();
   /**
    * @param airlineId
    */
	public void setAirlineId(int airlineId);
	/**
    * @return collForRemoval
    */
	public Collection<RangeVO> getNewCollForRemoval();

   /**
    * @param collForRemoval
    */
	public void setNewCollForRemoval(Collection<RangeVO> collForRemoval);
	/**
    * @return stockRequestForOALVOs
    */
	public Collection<StockRequestForOALVO> getStockRequestForOALVOs();
   /**
    * @param stockRequestForOALVOs
    */
	public void setStockRequestForOALVOs(
			Collection<StockRequestForOALVO> stockRequestForOALVOs);
	/**
    * @return collForRemoval
    */
	public Collection<RangeVO> getCollForRemoval();
   /**
    * @param collForRemoval
    */
	public void setCollForRemoval(Collection<RangeVO> collForRemoval);
	/**
	 * @return documentValues
	 */
	public HashMap<String, Collection<String>> getDocumentValues();
	/**
	 * @param documentValues
	 */
	public void setDocumentValues
		(HashMap<String, Collection<String>> documentValues);
	/**
    * @return stockHolderCode
    */
	public String getStockHolderCode();
    /**
    * @param stockHolderCode
    */
	public void setStockHolderCode(String stockHolderCode);
	/**
    * @return stockAllocationVO
    */
	public StockAllocationVO getStockAllocationVO();

   /**
    * @param stockAllocationVO
    */
	public void setStockAllocationVO(StockAllocationVO stockAllocationVO);
	/**
	 * @return documentType
	 */
	public Collection<OneTimeVO> getDocumentType();
	
	/**
	 * @param documentType
	 */
	public void setDocumentType(Collection<OneTimeVO> documentType);
	
	/**
	 * @return documentSubType
	 */
	public Collection<OneTimeVO> getDocumentSubType();
	
	/**
	 * @param documentSubType
	 */
	public void setDocumentSubType(Collection<OneTimeVO> documentSubType);

    /**
     * Methos used to get Payment type onetime VOs from Session
     * @return
     */
    //Todo HashMap( OnetimeConst ,Collection<oneTimeVos>
    HashMap getOneTimeValues();
    
    /**
     * Method used to set the onetimeVOs to session
     * @param oneTimevalues
     */
    //Todo HashMap( OnetimeConst ,Collection<oneTimeVos>
    void setOneTimeValues(HashMap oneTimevalues);
    
    /**
     * 
     * @return
     */
    Collection getStockDetailsForAirline();
    
    /**
     * 
     * @param stockDetailsForAirline
     */
    void setStockDetailsForAirline(Collection stockDetailsForAirline);
    
    /**
     * @return stockAllocationVO
     */
 	public StockAllocationVO getStockAllocationVOTosave();

 	/**
     *
     */
 	public void removeStockAllocationVOTosave();

    /**
     * @param stockAllocationVO
     */
 	public void setStockAllocationVOTosave(StockAllocationVO stockAllocationVO);
 	
 	 /**
     * @return stockAllocationVO
     */
 	public String getReorderLevel();

 	/**
     *
     */
 	public void removeReorderLevel();

    /**
     * @param stockAllocationVO
     */
 	public void setReorderLevel(String reorderlevel);
 	/**
     * @return DocumentValidationVO
     */
 	public DocumentVO getDocVO();

 	/**
      * @param 
      * @return void
      * 
      */
 	public void removeDocVO();

    /**
     * @param documentvo
     */
 	public void setDocVO(
 			DocumentVO documentvo);
 
}
