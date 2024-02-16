/*
 * MonitorStockSession.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/*import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;*/



/**
 * @author A-1952
 *This class implements the session interface for CreateStock screen
 * The session in this case holds the document types and status which are used as filter
 */
public interface MonitorStockSession extends ScreenSession{ 

	/** 
	 *
	 * @return
	 */
		public HashMap<String,Collection<String>> getDynamicDocType();
	/**
	 *
	 * @param dynamicDocType
	 */
		public void setDynamicDocType(HashMap<String,Collection<String>>  dynamicDocType);
	/**
	 *
	 * @return
	 */

		public Collection<StockHolderPriorityVO>   getPrioritizedStockHolders();
	/**
	 *
	 * @param stockHolderpriorityVos
	 */
		public void setPrioritizedStockHolders(Collection<StockHolderPriorityVO> stockHolderpriorityVos);
	/**
	 *
	 * @return
	 */

		public Collection<OneTimeVO> getOneTimeRequestedBy();
	/**
	 *
	 * @param oneTimeRequestedBy
	 */
		public void setOneTimeRequestedBy(Collection<OneTimeVO>  oneTimeRequestedBy);
	/**
	 *
	 * @return
	 */

		public Collection<MonitorStockVO>   getCollectionMonitorStockVO();
	/**
	 *
	 * @param monitorStockVO
	 */
		public void setCollectionMonitorStockVO(Collection<MonitorStockVO> monitorStockVO);
	/**
	 *
	 * @param dynamicDocType
	 */
		public void setDocumentType(String documentType);
		/**
		 *
		 * @return 
		 */

		public String getDocumentType();
	
		
		public void setAwbPrefix(String awbPrefix);
		public String getAwbPrefix();
		/**
	
	 *
	 * @param manual
	 */
		public void setManual(String manual);
		/**
		 *
		 * @return
		 */

		public String getManual();
		
	/**
	 *
	 * @param flag
	 */
		public void setFlag(String flag);
		/**
		 *
		 * @return
		 */

		public String getFlag();
		/**
		 *
		 * @return
		 */

		public String getApproverCode();
	/**
	 *
	 * @param approverCode
	 */
		public void setApproverCode(String approverCode);
		
		public StockFilterVO getStockFilterDetails();
		
		public void setStockFilterDetails(StockFilterVO stockFilterVO);
		
		void removeStockFilterDetails();
		
		/**
		 * 
		 * @param stockVo
		 */
		public void setMonitorStockHolderVO(MonitorStockVO stockVo);
		
		/**
		 * 
		 * @return
		 */
		public MonitorStockVO getMonitorStockHolderVO();
		
		/**
		 * 
		 * @param monitorStockDetails
		 */
		public void setMonitorStockDetails(Page<MonitorStockVO> monitorStockDetails);
		
		/**
		 * 
		 * @return
		 */
		public Page<MonitorStockVO> getMonitorStockDetails();
		
		/**
		 * The method is used to handle PageAwareMultiMapper
		 */
		public HashMap<String,String>getIndexMap();
		
		/**
		 * This method is used to set the IndexMap
		 */
				
		public void setIndexMap(HashMap<String,String>indexMap);
		
		
	
		
		
		public void setSelected(String selected);
		
		
		public String getSelected();
		
		/**
		 * @author A-2589
		 * @param findAirlineLov
		 * 
		 */
		public void setPartnerAirlines(Page<AirlineLovVO> findAirlineLov);
		
		/**
		 * @author A-2589
		 * @return Page<AirlineLovVO>
		 * 
		 */
		public Page<AirlineLovVO> getPartnerAirlines();
					   
		//Added by A-5201 for ICRD-24963 starts
		public void setCloseStatus(String closeStatus);
	
		public String getCloseStatus();	  
          
		
		
         //Added by A-5201 for ICRD-24963  end
		//Added as part of ICRD-46860
		public void setReportGenerateMode(String reportGenerateMode);
		
		public String getReportGenerateMode();
	 
}
