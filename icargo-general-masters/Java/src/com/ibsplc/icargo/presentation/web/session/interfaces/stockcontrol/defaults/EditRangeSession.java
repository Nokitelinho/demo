/*
 * EditRangeSession.java Created on Aug 30, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;



import com.ibsplc.icargo.framework.session.ScreenSession;
import java.util.Collection;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;


/**
 * @author A-1927
 *
 */
public interface EditRangeSession extends ScreenSession {


	/**
	 * Method for getting allocatedRangesVos from session
	 * @return Collection<RangeVO>
	 */
	 Collection<RangeVO> getAllocatedRangeVos();

	/**
	 * Method for setting allocatedRangesVos to session
	 * @return Collection<RangeVO>
	 */
	 void setAllocatedRangeVos(Collection<RangeVO> allocatedRangeVos);

	/**
	 * Method for getting availableRangesVos from session
	 * @return Collection<RangeVO>
	 */
	 Collection<RangeVO> getAvailableRangeVos();

	/**
	 * Method for setting availableRangesVos to session
	 * @return Collection<RangeVO>
	 */
	 void setAvailableRangeVos(Collection<RangeVO> allocatedRangeVos);

	 /**
	  * Method for getting stockControlFor from session
	  * @return String
	  */
	  String getStockControlFor();

	 /**
	  * Method for setting stockControlFor to session
	  * @return String
	  */
	  void setStockControlFor(String stockControlFor);
}
