/*
 * StockHolderLovSession.java Created on Sep 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;

import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;

import java.util.Collection;

/**
 * @author A-1927
 *
 */
public interface StockHolderLovSession extends ScreenSession {


	/**
	 * Method for getting stockHolderLovVOs from session
	 * @return Page<StockHolderLovVO>
	 */
	public Page<StockHolderLovVO> getStockHolderLovVOs();

	/**
	 * Method for setting stockHolderLovVOs to session
	 * @return Page<StockHolderLovVO>
	 */
	public void setStockHolderLovVOs(Page<StockHolderLovVO> stockHolderLovVO);

	/**
	 * Method for getting stockHolderLovFilterVOs from session
	 * @return StockHolderLovFilterVO
	 */

	public StockHolderLovFilterVO getStockHolderLovFilterVOs();


	/**
	 * Method for setting stockHolderLovFilterVOs to session
	 * @return StockHolderLovFilterVO
	 */
	public void setStockHolderLovFilterVOs(StockHolderLovFilterVO stockHolderLovFilterVO);

	/**
	 * Method for getting oneTimeStock from session
	 * @return Collection<OneTimeVO>
	 */

	Collection<OneTimeVO> getOneTimeStock();

	/**
	 * Method for setting oneTimeStock to session
	 * @return Collection<OneTimeVO>
	 */

	void setOneTimeStock(Collection<OneTimeVO> stockHolderTypes);

	/**
	 * Method for getting prioritizedStockHolders from session
	 * @return Collection<StockHolderPriorityVO>
	 */

    Collection<StockHolderPriorityVO> getPrioritizedStockHolders();

	/**
	 * Method for setting prioritizedStockHolders to session
	 * @return Collection<StockHolderPriorityVO>
	 */

    void setPrioritizedStockHolders(Collection<StockHolderPriorityVO> prioritizedStockHolders);

}
