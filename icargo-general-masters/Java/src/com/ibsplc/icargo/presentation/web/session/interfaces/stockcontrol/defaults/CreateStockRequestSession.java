/**
 * 
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1747
 *
 */
public interface CreateStockRequestSession extends ScreenSession{
	
	public Collection<OneTimeVO> getOnetimeValues() ;	    
	public void setOnetimeValues(Collection<OneTimeVO>  document);		
	public void removeOnetimeValues();
	
	public HashMap<String,Collection<String>> getDocumentValues() ;	    
	public void setDocumentValues(HashMap<String,Collection<String>>  document);
	public void removeDocumentValues();
}
