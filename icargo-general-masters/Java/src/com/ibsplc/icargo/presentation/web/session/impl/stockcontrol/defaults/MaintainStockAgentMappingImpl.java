/*
 * MaintainStockAgentMappingImpl.java Created on Oct 14, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;


import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockAgentMappingSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * @author A-2394
 *
 */
public class MaintainStockAgentMappingImpl extends AbstractScreenSession
 	implements MaintainStockAgentMappingSession{
	
	private static final String SCREEN_ID="stockcontrol.defaults.maintainstockagentmapping";
	private static final String MODULE_NAME="stockcontrol.defaults";
	private static final String KEY_COLLECTION="StockAgentVO Collection";
	private static final String KEY_MAP="StockAgentVO Map";
	
		
	/**
     * This method returns the SCREEN ID for the  Maintain Stock holder  screen
     */


    public String getScreenID(){
        return SCREEN_ID;
    }



    /**
     * This method returns the MODULE name for the Maintain Stock holder screen
     */
    public String getModuleName(){
        return MODULE_NAME;
    }

    /**
     * This method returns the StockHolderAgentMapping Collection for the Maintain Stock holder screen
     */
   
    public Page<StockAgentVO> getStockHolderAgentMapping() {
    	
    	return (Page<StockAgentVO>)getAttribute(KEY_COLLECTION);
    }

    /**
     * This method sets the StockHolderAgentMapping Collection for the Maintain Stock holder screen
     */
   
    public void setStockHolderAgentMapping(Page<StockAgentVO> list) {
    	
    	setAttribute(KEY_COLLECTION,list);
    }

    public Map<String,StockAgentVO> getStockHolderAgentMappingOriginal() {
    	
    	return (Map<String,StockAgentVO>)getAttribute(KEY_MAP); 
    }
    
    public void setStockHolderAgentMappingOriginal(Map<String,StockAgentVO> map) {
    	
    	setAttribute(KEY_MAP,(HashMap<String,StockAgentVO>)map);
    
    }
}
