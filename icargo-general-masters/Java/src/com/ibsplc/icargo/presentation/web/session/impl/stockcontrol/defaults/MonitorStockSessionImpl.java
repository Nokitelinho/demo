/*
 * MonitorStockSessionImpl.java Created on Aug 26, 2005
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

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1952
 *
 */
public class MonitorStockSessionImpl extends AbstractScreenSession
	implements MonitorStockSession{
	/**
	 * KEY_SCREEN_ID
	 */
	public static final String KEY_SCREEN_ID = "stockcontrol.defaults.monitorstockrequest";

	/**
	 * KEY_MODULE_NAME
	 */
	public static final String KEY_MODULE_NAME = "stockcontrol.defaults";

   private static final String DOCUMENT_TYPE = "documenttype";
   private static final String STOCKHOLDER_TYPE = "stockholdertype";
   private static final String PRIORITISED_STOCKHOLDERTYPE = "prioritisedstockholdertype";

   private static final String KEY_MONITORSTOCKVO = "monitorstockvo";
   
   private static final String KEY_STRING_DOCUMENTTYPE = "doctype";
  
   private static final String KEY_STRING_AWBPREFIX= "awbPrefix";
   private static final String KEY_MANUAL = "manual";
   
   private static final String KEY_FLAG = "flag";
   private static final String KEY_APPROVERCODE = "approvercode";
   private static final String KEY_FILTERDETAILS = "stockFilterDetails";
   
   private static final String KEY_MONITOR_STOCK_VO = "monitorStockVO";
   
   private static final String KEY_PAGE_MONITOR_STOCK ="pageMonitorStock";

  
   public static final String KEY_INDEXMAP = "indexMap";
  
   private static final String  KEY_FROM_MONITOR = "selected";
   
   private static final String KEY_PARTNER_AIRLINES="partner.airlines";
   	 //Added by A-5201 for ICRD-24963
   private static final String KEY_CLOSE = "back_to_allocate_stock";
   
   private static final String KEY_GENERATE_RPT = "reportGenerateMode";
   
   /**
	 * This method returns the SCREEN ID for the create Stock holder screen
	 * @return null
	 */
    public String getScreenID() {
        return null;
    }

    /**
	 * This method returns the
	 * MODULE AME for the create Stock holder screen
	 * @return null
	 */
    public String getModuleName() {
        return null;
    }
/**
 * @param dynamicDocType
 */

	public void setDynamicDocType(HashMap<String,Collection<String>> dynamicDocType) {
		setAttribute(DOCUMENT_TYPE,(HashMap<String,Collection<String>>)dynamicDocType);
	}
	/**
	 * @return dynamicDocType
	 */
	public HashMap<String,Collection<String>> getDynamicDocType() {
			return (HashMap<String,Collection<String>>)getAttribute(DOCUMENT_TYPE);
	}
	/**
	 * @return stockHolderpriorityVos
	 */
    public Collection<StockHolderPriorityVO>   getPrioritizedStockHolders(){
		 return (Collection<StockHolderPriorityVO>)getAttribute(STOCKHOLDER_TYPE);

	}
    /**
     * @param stockHolderpriorityVos
     */
	public void setPrioritizedStockHolders(Collection<StockHolderPriorityVO> stockHolderpriorityVos){
		  setAttribute(STOCKHOLDER_TYPE, (ArrayList<StockHolderPriorityVO>)stockHolderpriorityVos);
	}
	/**
	 * @return oneTimeRequestedBy
	 */
	public Collection<OneTimeVO> getOneTimeRequestedBy(){
		return (Collection<OneTimeVO>)getAttribute(PRIORITISED_STOCKHOLDERTYPE);

	}
	/**
	 * @param oneTimeRequestedBy
	 */
	public void setOneTimeRequestedBy(Collection<OneTimeVO>  oneTimeRequestedBy){
	    setAttribute(PRIORITISED_STOCKHOLDERTYPE, (ArrayList<OneTimeVO>)oneTimeRequestedBy);
	}
	/**
	 * @return monitorStockVO
	 */
	public Collection<MonitorStockVO>   getCollectionMonitorStockVO(){
		   return (Collection<MonitorStockVO>)getAttribute(KEY_MONITORSTOCKVO);

	}
	/**
	 * @param monitorStockVO
	 */

	public void setCollectionMonitorStockVO(Collection<MonitorStockVO> monitorStockVO){
			  setAttribute(KEY_MONITORSTOCKVO, (ArrayList<MonitorStockVO>)monitorStockVO);
	}
/**
 * @param documentType
 */
	
public void setDocumentType(String documentType){
	  setAttribute(KEY_STRING_DOCUMENTTYPE, (String)documentType);
}
	/**
	 * @return documentType
	 */
	public String getDocumentType(){
		  return (String)getAttribute(KEY_STRING_DOCUMENTTYPE);
	}

	public void setAwbPrefix(String awbPrefix){
		  setAttribute(KEY_STRING_AWBPREFIX, (String)awbPrefix);
	}
	public String getAwbPrefix(){
		return (String)getAttribute(KEY_STRING_AWBPREFIX);
	}
	/**
	 * @param manual
	 */
	public void setManual(String manual){
		setAttribute(KEY_MANUAL, (String)manual);
	}
	/**
	 * @return manual
	 */
	public String getManual(){
		 return (String)getAttribute(KEY_MANUAL);		 
		
	}
	
	/**
	 * @param flag
	 */
	public void setFlag(String flag){
		setAttribute(KEY_FLAG, (String)flag);
	}
	/**
	 * @return flag
	 */
	public String getFlag(){
		 return (String)getAttribute(KEY_FLAG);		 
		
	}
		/**
		 * @return approverCode
		 */
	public String getApproverCode(){
		 return (String)getAttribute(KEY_APPROVERCODE);		 
	}
	/**
	 * @param approverCode
	 */
	public void setApproverCode(String approverCode){
		 setAttribute(KEY_APPROVERCODE, (String)approverCode);
	}


	 public StockFilterVO getStockFilterDetails(){
	    return (StockFilterVO)getAttribute(KEY_FILTERDETAILS);
	 }
	 public void setStockFilterDetails(StockFilterVO filterDetails){
	   	setAttribute(KEY_FILTERDETAILS, (StockFilterVO)filterDetails);
	 }	
	 public void removeStockFilterDetails() {
    	removeAttribute(KEY_FILTERDETAILS);
	 }

		/**
		 * 
		 * @param stockVo
		 */
		public void setMonitorStockHolderVO(MonitorStockVO stockVo){
			setAttribute(KEY_MONITOR_STOCK_VO, stockVo);
		}
		
		/**
		 * 
		 * @return
		 */
		public MonitorStockVO getMonitorStockHolderVO(){
			return getAttribute(KEY_MONITOR_STOCK_VO);
		}
		
		/**
		 * 
		 * @param monitorStockDetails
		 */
		public void setMonitorStockDetails(Page<MonitorStockVO> monitorStockDetails){
			setAttribute(KEY_PAGE_MONITOR_STOCK, monitorStockDetails);
		}
		
		/**
		 * 
		 * @return
		 */
		public Page<MonitorStockVO> getMonitorStockDetails(){
			return getAttribute(KEY_PAGE_MONITOR_STOCK);
		}
		
		/**
		 * This method is used for PageAwareMultiMapper to get the Index Map
		 * @return  HashMap<String,String>
		 */
		public HashMap<String,String>getIndexMap(){
			 return (HashMap<String,String>)getAttribute(KEY_INDEXMAP);
		}

		/**Sets the hashmap for Absolute index of page
		 * @param indexMap
		 */
		public void setIndexMap(HashMap<String,String>indexMap){
			 setAttribute(KEY_INDEXMAP, (HashMap<String,String>)indexMap);
		}
		
	
		
		public void setSelected(String selected){
			setAttribute(KEY_FROM_MONITOR, selected);
		}
		
		/**
		 * 
		 * @return
		 */
		public String getSelected(){
			return getAttribute(KEY_FROM_MONITOR);
		}

		public void setPartnerAirlines(Page<AirlineLovVO> partnerAirlines) {
			setAttribute(KEY_PARTNER_AIRLINES,partnerAirlines);			
		}
		
		public Page<AirlineLovVO> getPartnerAirlines(){
			return getAttribute(KEY_PARTNER_AIRLINES);
		}
		   //Added by A-5201 for ICRD-24963 starts
		public void setCloseStatus(String closeStatus){
			  setAttribute(KEY_CLOSE, closeStatus);
		}
			/**
			 * @return documentType
			 */
			public String getCloseStatus(){
				  return getAttribute(KEY_CLOSE);
		}
		
			 //Added by A-5201 for ICRD-24963 end
			
			public void setReportGenerateMode(String reportGenerateMode){
				setAttribute(KEY_GENERATE_RPT, (String)reportGenerateMode);
			}
			/**
			 * @return flag
			 */
			public String getReportGenerateMode(){
				 return (String)getAttribute(KEY_GENERATE_RPT);		 
				
			}
		
}
