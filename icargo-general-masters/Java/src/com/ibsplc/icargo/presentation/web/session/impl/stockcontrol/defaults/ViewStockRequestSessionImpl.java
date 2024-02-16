/**
 * 
 */
package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;

import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ViewStockRequestSession;

/**
 * @author A-1747
 *
 */
public class ViewStockRequestSessionImpl extends AbstractScreenSession 
implements ViewStockRequestSession{
	private static final String KEY_SCREEN_ID = "stockcontrol.defaults.cto.viewstockrequest";
	private static final String KEY_MODULE_NAME = "stockcontrol.defaults";
	private static final String KEY_VAL = "rowvalues";
	private static final String KEY_REQUEST="requestvalues";
	public String getScreenID(){
        return KEY_SCREEN_ID;
    }

    /**
     * This method returns the MODULE name for the List Product screen
     */
    public String getModuleName(){
        return KEY_MODULE_NAME;
    } 
    
    public String getRowValues() {
    	return getAttribute(KEY_VAL);
    }
    
    /**
	 * This method is used to set the priority in session
	 * @param priority
	 */
	public void setRowValues(String  row) {
	    setAttribute(KEY_VAL, row);
	}
	/**
	 * @return void
	 */
	public void removeRowValues(){
		removeAttribute(KEY_VAL);
	}  
	
	public Collection<StockRequestForOALVO> getRequestValues(){
		 return (Collection<StockRequestForOALVO>)getAttribute(KEY_REQUEST);
		
		
	}
    
    /**
	 * This method is used to set the priority in session
	 * @param priority
	 */
	public void setRequestValues(Collection<StockRequestForOALVO>  row) {
		 setAttribute(KEY_REQUEST, (ArrayList<StockRequestForOALVO>)row);
	} ;
	/**
	 * @return void
	 */
	public void removeRequestValues() {
		removeAttribute(KEY_REQUEST);
		
	}

}
