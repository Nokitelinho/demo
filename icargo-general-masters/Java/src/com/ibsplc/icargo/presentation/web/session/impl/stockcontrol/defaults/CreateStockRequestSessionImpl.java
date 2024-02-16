/**
 * 
 */
package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.CreateStockRequestSession;


/**
 * @author A-1747
 *
 */
public class CreateStockRequestSessionImpl extends AbstractScreenSession 
implements CreateStockRequestSession {
	
	private static final String KEY_SCREEN_ID = "stockcontrol.defaults.cto.createstockrequest";
	private static final String KEY_MODULE_NAME = "stockcontrol.defaults";
	private static final String KEY_ONETIM="onetimevalues";
	private static final String KEY_DOC="documentvalues";
	
	 public String getScreenID(){
	        return KEY_SCREEN_ID;
	    } 

	    /**
	     * This method returns the MODULE name for the List Product screen
	     */
	    public String getModuleName(){
	        return KEY_MODULE_NAME;
	    }  
	    
	    public Collection<OneTimeVO> getOnetimeValues() {
	    	return ((Collection<OneTimeVO>)getAttribute(KEY_ONETIM));
	    }
	    
	    /**
		 * This method is used to set the priority in session
		 * @param priority
		 */
		public void setOnetimeValues(Collection<OneTimeVO>  document) {
		    setAttribute(KEY_ONETIM, (ArrayList<OneTimeVO>)document);
		}
		/**
		 * @return void
		 */
		public void removeOnetimeValues(){
			removeAttribute(KEY_ONETIM);
		}  
		
	 public HashMap<String,Collection<String>> getDocumentValues() {
	    	return (HashMap<String,Collection<String>>)getAttribute(KEY_DOC);
	    }
	    
	    /**
		 * This method is used to set the priority in session
		 * @param priority
		 */
		public void setDocumentValues(HashMap<String,Collection<String>>  document) {
		    setAttribute(KEY_DOC, (HashMap<String,Collection<String>>)document);
		}
		/**
		 * @return void
		 */
		public void removeDocumentValues(){
			removeAttribute(KEY_DOC);
		}  
	
	
}
