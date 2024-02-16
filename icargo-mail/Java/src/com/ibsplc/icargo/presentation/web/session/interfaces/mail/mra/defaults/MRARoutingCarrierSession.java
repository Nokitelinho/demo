package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults;

/**
 * MRARoutingCarrierSession
 * 
 * @author A-4452
 * 
 */
import java.util.Collection;


import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
public interface MRARoutingCarrierSession extends ScreenSession {
    
	
	
	/**
	 * @param RoutingCarrierVO 
	 */
	public void setRoutingCarrierVOs(Collection<RoutingCarrierVO> routingCarrierVO);	
	/**
	 * @return Collection<RoutingCarrierVO>
	 */
	public Collection<RoutingCarrierVO> getRoutingCarrierVOs();
	
	
	public void removeRoutingCarrierVOs();
	
	
	 /**
	    * 
	    * @return routingCarrierFilterVO
	    */
	   
		public RoutingCarrierFilterVO getRoutingCarrierFilterVO();
		
		/**
		 * @param routingCarrierFilterVO The  routingCarrierFilterVO to set.
		 */
		public void setRoutingCarrierFilterVO(RoutingCarrierFilterVO routingCarrierFilterVO);
		
		/**
		 * @author A-4452
		 */	
		
		public void removeRoutingCarrierFilterVO();
	
	
	
	
	
	
	
	

}
