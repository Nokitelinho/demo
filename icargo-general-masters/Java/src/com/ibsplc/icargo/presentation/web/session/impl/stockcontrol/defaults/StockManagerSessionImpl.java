/*
 * StockManagerSessionImpl.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockManagerSession;

/**
 * @author A-1619
 *
 */
public class StockManagerSessionImpl extends AbstractScreenSession
								implements StockManagerSession {

	/**
	 * KEY for collForRemoval in session
	 */
	public static final String KEY_AIRLINEID = "airlineId";
	/**
	 * KEY for collForRemoval in session
	 */
	public static final String KEY_STOCKREQUESTFOROALVO = "StockRequestForOALVO";
	/**
	 * KEY for collForRemoval in session
	 */
	public static final String KEY_COLLFORREMOVAL = "collForRemoval";
	/**
	 * KEY for collForRemoval in session
	 */
	public static final String KEY_NEWCOLLFORREMOVAL = "newCollForRemoval";
	/**
	 * KEY for documentType in session
	 */
	public static final String KEY_DOCUMENTTYPE = "documentType";
	/**
	 * KEY for documentValues in session
	 */
	public static final String KEY_DOCUMENTVALUES = "documentValues";

	/**
	 * KEY for documentSubType in session
	 */
	public static final String KEY_DOCUMENTSUBTYPE = "documentSubType";

	/**
	 * KEY for stockHolderCode in session
	 */
	public static final String KEY_STOCKHOLDERCODE = "stockHolderCode";

	/**
	 * KEY for cashDrawType in session
	 */
	public static final String KEY_MODULE_NAME = "stockcontrol.defaults";

	/**
	 * KEY for SCREEN_ID in session
	 */
	public static final String KEY_SCREEN_ID =
		"stockcontrol.defaults.stockmanager";
	/**
	 * KEY for stockVO in session
	 */
	public static final String KEY_STOCKALLOCATIONVO = "stockAllocationVO";
	/**
	 * KEY for stockHolderCode in session
	 */
	public static final String KEY_HOMEAIRLINECODE = "homeairlinecode";
	/**
	 * KEY for documentVO in session
	 */
	public static final String KEY_DOCUMENTVO = "documentVO";
	/**
	 * KEY for vo to save in session
	 */
	public static final String KEY_STOCKALLOCATIONVOTOSAVE="votosave";
	/**
	 * KEY for stock reorder in session
	 */
	public static final String KEY_STOCKREORDER = "reorderlevel";
	/**
	 * KEY for document validationvo in session
	 */
	public static final String KEY_DOCVO = "docvo";
	
	

	/**
    * @return Collection<DocumentVO>
    */
	public Collection<DocumentVO> getDocumentVO() {
		return (Collection<DocumentVO>)getAttribute(KEY_DOCUMENTVO);
   }
   /**
    * @param documentVO
    */
	public void setDocumentVO(Collection<DocumentVO> documentVO){
 		setAttribute(KEY_DOCUMENTVO,(ArrayList<DocumentVO>)documentVO);
	}
	/**
    * @return String
    */
	public String getHomeairlinecode() {
		return (getAttribute(KEY_HOMEAIRLINECODE));
   }

   /**
    * @param homeairlinecode
    */
	public void setHomeairlinecode(String homeairlinecode){
 		setAttribute(KEY_HOMEAIRLINECODE,homeairlinecode);
	}


	/**
    * @return int
    */
	public int getAirlineId() {
		return ((Integer)(getAttribute(KEY_AIRLINEID))).intValue();
   }
   /**
    * @param airlineId
    */
	public void setAirlineId(int airlineId){
 		setAttribute(KEY_AIRLINEID,airlineId);
	}
	/**
    * @return Collection<RangeVO>
    */
	public Collection<RangeVO> getNewCollForRemoval() {
		return (Collection<RangeVO>)getAttribute(KEY_NEWCOLLFORREMOVAL);
   }
   /**
    * @param collForRemoval
    */
	public void setNewCollForRemoval(Collection<RangeVO> collForRemoval){
 		setAttribute(KEY_NEWCOLLFORREMOVAL,(ArrayList<RangeVO>)collForRemoval);
	}
	/**
    * @return Collection<StockRequestForOALVO>
    */
	public Collection<StockRequestForOALVO> getStockRequestForOALVOs() {
		return (Collection<StockRequestForOALVO>)
						getAttribute(KEY_COLLFORREMOVAL);
   }

   /**
    * @param stockRequestForOALVOs
    */
	public void setStockRequestForOALVOs(
			Collection<StockRequestForOALVO> stockRequestForOALVOs){
 		setAttribute(KEY_COLLFORREMOVAL,(
 				ArrayList<StockRequestForOALVO>)stockRequestForOALVOs);
	}
	/**
    * @return Collection<RangeVO>
    */
	public Collection<RangeVO> getCollForRemoval() {
		return (Collection<RangeVO>)getAttribute(KEY_COLLFORREMOVAL);
   }

   /**
    * @param collForRemoval
    */
	public void setCollForRemoval(Collection<RangeVO> collForRemoval){
 		setAttribute(KEY_COLLFORREMOVAL,(ArrayList<RangeVO>)collForRemoval);
	}
	/**
	 * @return HashMap<String, Collection<String>>
	 */
	public HashMap<String, Collection<String>> getDocumentValues() {
		return (HashMap<String, Collection<String>>)getAttribute(KEY_DOCUMENTVALUES);
	}

	/**
	 * @param documentValues
	 */
	public void setDocumentValues(HashMap<String, Collection<String>> documentValues) {
		setAttribute(KEY_DOCUMENTVALUES,(HashMap<String, Collection<String>>)documentValues);
	}
	/**
    * @return String
    */
	public String getStockHolderCode() {
		return (getAttribute(KEY_STOCKHOLDERCODE));
   }

   /**
    * @param stockHolderCode
    */
	public void setStockHolderCode(String stockHolderCode){
 		setAttribute(KEY_STOCKHOLDERCODE,stockHolderCode);
	}
	/**
    * @return StockAllocationVO
    */
	public StockAllocationVO getStockAllocationVO() {
		return (StockAllocationVO)getAttribute(KEY_STOCKALLOCATIONVO);
   }

	/**
     * @param 
     * @return void
     * 
     */
	public void removeStockAllocationVO(){
 		removeAttribute(KEY_STOCKALLOCATIONVO);
	}

   /**
    * @param stockAllocationVO
    */
	public void setStockAllocationVO(StockAllocationVO stockAllocationVO){
 		setAttribute(KEY_STOCKALLOCATIONVO,(StockAllocationVO)stockAllocationVO);
	}

	/**
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getDocumentType() {
		return (Collection<OneTimeVO>)getAttribute(KEY_DOCUMENTTYPE);
	}

	/**
	 * @param documentType
	 */
	public void setDocumentType(Collection<OneTimeVO> documentType) {
		setAttribute(KEY_DOCUMENTTYPE,(ArrayList<OneTimeVO>)documentType);
	}

	/**
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO> getDocumentSubType() {
		return (Collection<OneTimeVO>)getAttribute(KEY_DOCUMENTTYPE);
	}

	/**
	 * @param documentSubType
	 */
	public void setDocumentSubType(Collection<OneTimeVO> documentSubType) {
		setAttribute(KEY_DOCUMENTSUBTYPE,(ArrayList<OneTimeVO>)documentSubType);
	}


	/**
     * @param 
     * @return HashMap
     * 
     */
    public HashMap getOneTimeValues() {
        return null;
    }

    /**
     * @param oneTimevalues
     * @return 
     * 
     */
    public void setOneTimeValues(HashMap oneTimevalues) {
    }

    /**
     * @param 
     * @return Collection
     * 
     */
    public Collection getStockDetailsForAirline() {
        return null;
    }

    
    /**
     * @param stockDetailsForAirline
     * @return 
     * 
     */
    public void setStockDetailsForAirline(Collection stockDetailsForAirline) {
    }

    

    /**
     * This method returns the MODULE name for the Stock Manager screen
     */
    /**
     * @param 
     * @return String
     * 
     */
    public String getModuleName(){
        return KEY_MODULE_NAME;
    }

	/**
     * This method returns the SCREEN ID for the Stock Manager screen
     */
    /**
     * @param 
     * @return String
     * 
     */
    public String getScreenID(){
        return KEY_SCREEN_ID;
    }
    /**
     * @return StockAllocationVO
     */
 	public StockAllocationVO getStockAllocationVOTosave() {
 		return (StockAllocationVO)getAttribute(KEY_STOCKALLOCATIONVOTOSAVE);
    }

 	/**
     * @param 
     * @return void
     * 
     */
 	public void removeStockAllocationVOTosave(){
  		removeAttribute(KEY_STOCKALLOCATIONVOTOSAVE);
 	}

    /**
     * @param stockAllocationVO
     */
 	public void setStockAllocationVOTosave(StockAllocationVO stockAllocationVO){
  		setAttribute(KEY_STOCKALLOCATIONVOTOSAVE,(StockAllocationVO)stockAllocationVO);
 	}
 	 /**
     * @return String
     */
 	public String getReorderLevel() {
 		return (String)getAttribute(KEY_STOCKREORDER);
    }

 	/**
     * @param 
     * @return void
     * 
     */
 	public void removeReorderLevel(){
  		removeAttribute(KEY_STOCKREORDER);
 	}

    /**
     * @param reorderlevel
     */
 	public void setReorderLevel(String reorderlevel){
  		setAttribute(KEY_STOCKREORDER,(String)reorderlevel);
 	}
 	
 	/**
     * @return DocumentVO
     */
 	public DocumentVO getDocVO() {
 		return (DocumentVO)getAttribute(KEY_DOCVO);
    }

 	/**
      * @param 
      * @return void
      * 
      */
 	public void removeDocVO(){
  		removeAttribute(KEY_DOCVO);
 	}

    /**
     * @param documentValidationvo
     */
 	public void setDocVO(DocumentVO documentvo){
  		setAttribute(KEY_DOCVO,(
  				DocumentVO)documentvo);
 	}

}
