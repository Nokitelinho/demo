/**
 * 
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;

import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1747
 *
 */
public interface ViewStockRequestSession extends ScreenSession{
	
	 public String getRowValues() ;
	    
	    /**
		 * This method is used to set the priority in session
		 * @param priority
		 */
		public void setRowValues(String  row) ;
		/**
		 * @return void
		 */
		public void removeRowValues();
		
		public Collection<StockRequestForOALVO> getRequestValues() ;
	    
	    /**
		 * This method is used to set the priority in session
		 * @param priority
		 */
		public void setRequestValues(Collection<StockRequestForOALVO>  row) ;
		/**
		 * @return void
		 */
		public void removeRequestValues();

}
