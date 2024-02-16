/*
 * MaintainRoleGroupSessionImpl.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ViewRangeSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1952
 *
 */
public class ViewRangeSessionImpl extends AbstractScreenSession
        implements ViewRangeSession {

	/**
	 * KEY_SCREEN_ID
	 */
	public static final String KEY_SCREEN_ID = "stockcontrol.defaults.viewrange";

	/**
	 * KEY_MODULE_NAME
	 */
	
	public static final String KEY_MODULE_NAME = "stockcontrol.defaults";
	private static final String KEY_STOCKRANGEVO = "stockrangevo";
	private static final String KEY_MONITORSTOCKVO = "monitorstockvo";
	private static final String KEY_CODE = "code";
	private static final String KEY_PARTNER_AIRLINE="partner.airline";
	private static final String KEY_AWB_STOCK_PAGE = "listawbstockpage";

    /**
     * This method returns the SCREEN ID for the view range screen
     */
    public String getScreenID(){
        return KEY_SCREEN_ID;
    }

    /**
     * This method returns the MODULE name for the view range screen
     */

    public String getModuleName(){
        return KEY_MODULE_NAME;
    }

	 /**
     * This method returns the StockRangeVO
     */
   public StockRangeVO   getStockRangeVO(){
	   return (StockRangeVO)getAttribute(KEY_STOCKRANGEVO);

   }
   /**
    * @param stockRangeVO
    */

   public void setStockRangeVO(StockRangeVO stockRangeVO){
		  setAttribute(KEY_STOCKRANGEVO, (StockRangeVO)stockRangeVO);

   }
   /**
    * @return code
    */
   public String  getCode(){
		return (getAttribute(KEY_CODE));
   }
/**
 * @param code
 */
   public void setCode(String code){
		setAttribute(KEY_CODE,code);
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

public void setPartnerAirline(AirlineLovVO airlineLovVO) {
	setAttribute(KEY_PARTNER_AIRLINE,airlineLovVO);
	
}

public AirlineLovVO getPartnerAirline() {
	return getAttribute(KEY_PARTNER_AIRLINE);
}



public Page<String> getAWBStockPage(){
	return (Page<String>) getAttribute(KEY_AWB_STOCK_PAGE);
}

public void setAWBStockPage(Page<String>  awbStockPage){
	setAttribute(KEY_AWB_STOCK_PAGE, (Page<String>)awbStockPage);
}
public Integer getTotalRecords()
{
    return (Integer)getAttribute("totalrecords");
}

public void setTotalRecords(int totalRecords)
{
    setAttribute("totalrecords", Integer.valueOf(totalRecords));
}
}
