package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

/**
 * MRARoutingCarrierSessionImpl
 * 
 * @author A-4452
 * 
 */

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RoutingCarrierVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRARoutingCarrierSession;
public class MRARoutingCarrierSessionImpl extends AbstractScreenSession implements  MRARoutingCarrierSession{


	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.dispatchroutingcarrierconfig";
	
	private static final String KEY_LISTDETAIL = "routingCarrierVO";
	private static final String KEY_LISTFILTER = "routingCarrierFilterVO";

	
	
	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return MODULE_NAME;
	}

	@Override
	public String getScreenID() {
		// TODO Auto-generated method stub
		return SCREEN_ID;
	}

	  /**
     * 
     * @return DSNRoutingFilterVO
     */
    
	public Collection<RoutingCarrierVO> getRoutingCarrierVOs() {		
		return (Collection<RoutingCarrierVO>)getAttribute(
				KEY_LISTDETAIL);
		
	}
	
	public void setRoutingCarrierVOs(Collection<RoutingCarrierVO> 
	routingCarrierVOs) {
     setAttribute(KEY_LISTDETAIL,(ArrayList<RoutingCarrierVO>)routingCarrierVOs);
	}
	
	/**
	 * @author A-4452
	 */	
	
	public void removeRoutingCarrierVOs() {
		removeAttribute(KEY_LISTDETAIL);
	}

	  /**
     * 
     * @return RoutingCarrierFilterVO
     */
    
	public RoutingCarrierFilterVO getRoutingCarrierFilterVO() {
		return getAttribute(KEY_LISTFILTER);
	}
	
	/**
	 * @param RoutingCarrierFilterVO The  RoutingCarrierFilterVO to set.
	 */
	public void setRoutingCarrierFilterVO(RoutingCarrierFilterVO routingCarrierFilterVO) {
		setAttribute(KEY_LISTFILTER,routingCarrierFilterVO);
	}
	
	/**
	 * @author A-4452
	 */	
	
	public void removeRoutingCarrierFilterVO() {
		removeAttribute(KEY_LISTFILTER);
	}
}
