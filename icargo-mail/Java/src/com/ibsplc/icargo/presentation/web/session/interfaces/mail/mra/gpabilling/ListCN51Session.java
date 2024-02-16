/*
 * ListCN51Session.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling;

import java.util.ArrayList;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1556
 * @author A-2521
 */
public interface ListCN51Session extends ScreenSession {

	/**
	 *
	 * @return
	 */
	public Page<CN51SummaryVO> getCN51SummaryVOs();

	/**
	 *
	 * @param cn51SummaryVOs
	 */
	public void setCN51SummaryVOs(Page<CN51SummaryVO> cn51SummaryVOs) ;

	/**
	 * removes  cn51SummaryVOs
	 *
	 */
	public void removeCN51SummaryVOs();

	/**
	 *
	 * @param cn51SummaryFilterVO
	 */
	public void setCN51SummaryFilterVO(CN51SummaryFilterVO cn51SummaryFilterVO);

	/**
	 *
	 * @return cn51SummaryFilterVO
	 */
	public CN51SummaryFilterVO getCN51SummaryFilterVO();

	/**
	 * removes  CN51SummaryFilterVO
	 *
	 */
	public void removeCN51SummaryFilterVO();

		/**
		 *
		 * @param toggler
		 */
		public void setCN51CN66Toggler(String toggler);

		/**
		 *
		 * @return String
		 */
		public String getCN51CN66Toggler();

		/**
		 * removes  String
		 *
		 */
		  public void removeCN51CN66Toggler();

		  /**
			 * sets ArrayList<String>
			 *
			 * @param ArrayList<String>
			 */
			public void setPrintALLSelIdxs(ArrayList<String> selIdxs);

			/**
			 * ArrayList<String>
			 *  @return ArrayList<String>
			 */
		    public ArrayList<String> getPrintALLSelIdxs();
		    /**
			 *
			 * removes ArrayList<String>
			 */
	    public void removePrintALLSelIdxs();
}
