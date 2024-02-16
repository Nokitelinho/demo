/*
 * ListCN51SessionImpl.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51Session;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1556
 * @author A-2521
 */
public class ListCN51SessionImpl extends AbstractScreenSession
implements ListCN51Session {

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51s";

	private static final String KEY_CN51DETAILS = "cn51detailsvos";

	private static final String KEY_CN51FILTER = "cn51filtervo";

	private static final String KEY_CN51_CN66_TOGGLER = "cn51cn66toggler";
	private static final String KEY_PRINTALL_SELIDXS = "printallselidxs";


	/**
	 *
	 */
	public ListCN51SessionImpl() {
		super();

	}

	/**
	 *  @return cn51SummaryVOs
	 */
	public Page<CN51SummaryVO> getCN51SummaryVOs() {

		return (Page<CN51SummaryVO>) getAttribute( KEY_CN51DETAILS );
	}

	/**
	 * sets cn51SummaryVOs
	 *
	 * @param cn51SummaryVOs
	 */
	public void setCN51SummaryVOs(Page<CN51SummaryVO> cn51SummaryVOs) {

		setAttribute( KEY_CN51DETAILS, (Page<CN51SummaryVO>) cn51SummaryVOs );

	}

	/**
	 *
	 * removes CN51SummaryVOs
	 */
	public void removeCN51SummaryVOs() {

		removeAttribute( KEY_CN51DETAILS );
	}

	/**
	 *
	 */
	@Override
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 *
	 */
	@Override
	public String getModuleName() {

		return MODULE_NAME;
	}

	/**
	 * sets cn51SummaryFilterVO
	 *
	 * @param cn51SummaryFilterVO
	 */
	public void setCN51SummaryFilterVO(CN51SummaryFilterVO cn51SummaryFilterVO){

		setAttribute(KEY_CN51FILTER,cn51SummaryFilterVO);
    }

	/**
	 *  returns cn51SummaryFilterVO
	 *  @return cn51SummaryFilterVO
	 */
    public CN51SummaryFilterVO getCN51SummaryFilterVO(){

    	return getAttribute(KEY_CN51FILTER);
    }

    /**
	 *
	 * removes CN51SummaryFilterVO
	 */
    public void removeCN51SummaryFilterVO(){

    	removeAttribute(KEY_CN51FILTER);
    }


	  /**
	 * sets String
	 *
	 * @param String
	 */
	public void setCN51CN66Toggler(String toggler){

		setAttribute(KEY_CN51_CN66_TOGGLER,toggler);
  }

	/**
	 * String
	 *  @return String
	 */
  public String getCN51CN66Toggler(){

  	return getAttribute(KEY_CN51_CN66_TOGGLER);
  }

  /**
	 *
	 * removes String
	 */
  public void removeCN51CN66Toggler(){

  	removeAttribute(KEY_CN51_CN66_TOGGLER);
  }

  /**
	 * sets ArrayList<String>
	 *
	 * @param ArrayList<String>
	 */
	public void setPrintALLSelIdxs(ArrayList<String> selIdxs){

		setAttribute(KEY_PRINTALL_SELIDXS,selIdxs);
  }

	/**
	 * ArrayList<String>
	 *  @return ArrayList<String>
	 */
  public ArrayList<String> getPrintALLSelIdxs(){

  	return getAttribute(KEY_PRINTALL_SELIDXS);
  }

  /**
	 *
	 * removes ArrayList<String>
	 */
  public void removePrintALLSelIdxs(){

  	removeAttribute(KEY_PRINTALL_SELIDXS);
  }


}
