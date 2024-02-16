/** StockHolderLovSession.java Created on Sep 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;


import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovFilterVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockHolderLovSession;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;

import java.util.Collection;
import java.util.ArrayList;

/**
 * @author A-1927
 *
 */
public class StockHolderLovSessionImpl extends AbstractScreenSession
        implements StockHolderLovSession {


	private static final String STOCKHOLDER_LOV_VO="Stock HolderLov VO";
	private static final String STOCKHOLDER_LOV_FILTER_VO="Stock HolderLov Filter VO";
	private static final String PRIO_STOCKHOLDER_SHVO = "prioritizedstockholder";
	private static final String KEY_ONETIMEVO_SHVO = "oneTimeVO";



	/**
	 * This method returns the SCREEN ID for the Maintain Product screen
	 */
	 public String getScreenID(){
	      return "stockcontrol.defaults.common.stockholderlov";
	 }

	/**
	 * This method returns the MODULE name for the Maintain Product screen
	 */
	 public String getModuleName(){
	        return "stockcontrol.defaults";
	    }

	/**
	 * Methods for stock holder lov begins
	 */
	public Page<StockHolderLovVO> getStockHolderLovVOs(){
			return (Page<StockHolderLovVO>)getAttribute(STOCKHOLDER_LOV_VO);
	}
	/**
	 * Methods for getting
	 */
	public void setStockHolderLovVOs(Page<StockHolderLovVO> stockHolderLovVO){

			setAttribute(STOCKHOLDER_LOV_VO, (Page<StockHolderLovVO>)stockHolderLovVO);
	}


	/**
	 * This method is used to fetch the stockholdertypes stored in
	 * session. Stockholder types are fetched from onetime during
	 * screenload
	 * @return Collection<String>
	 */
	public Collection<OneTimeVO> getOneTimeStock(){
		   return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEVO_SHVO);
	}

	/**
	 * This method is used to set the stockholder types into the session
	 * @param stockHolderTypes
	 * Collection<String>
	 */
	public void setOneTimeStock(Collection<OneTimeVO> stockHolderTypes){
			setAttribute(KEY_ONETIMEVO_SHVO,(ArrayList<OneTimeVO>)stockHolderTypes);
	}



	/**
	 * Methods for stock holder filter lov begins
	 */
	public StockHolderLovFilterVO getStockHolderLovFilterVOs(){

			return (StockHolderLovFilterVO)getAttribute(STOCKHOLDER_LOV_FILTER_VO);
	}
	/**
	 * Methods for getting
	 * @param stockHolderLovFilterVO
	 */
	public void setStockHolderLovFilterVOs(StockHolderLovFilterVO stockHolderLovFilterVO){

			setAttribute(STOCKHOLDER_LOV_FILTER_VO, (StockHolderLovFilterVO)stockHolderLovFilterVO);
	}


	/**
	 * Thie method is used to get the PriotirizedStockHolderVO for child screen
	 * from session
	 * @return PriotirizedStockHolderVO
	 */

	public Collection<StockHolderPriorityVO> getPrioritizedStockHolders(){

			//System.out.println("*****Inside get*************"+(Collection<StockHolderPriorityVO>)getAttribute(PRIO_STOCKHOLDER_SHVO));
			return (Collection<StockHolderPriorityVO>)getAttribute(PRIO_STOCKHOLDER_SHVO);
	}


	/**
	 * This method is used to set the PriotirizedStockHolderVO in session for child
	 * @param PriotirizedStockHolderVO
	 */

	public void setPrioritizedStockHolders(Collection<StockHolderPriorityVO> prioritizedStockHolders){

			//System.out.println("*****Inside Set*************"+prioritizedStockHolders);
			setAttribute(PRIO_STOCKHOLDER_SHVO,(ArrayList<StockHolderPriorityVO>)prioritizedStockHolders);
	}

}
