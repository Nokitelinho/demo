/*
 * InvoiceSettlementSession.java Created on Mar 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementHistoryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


	/**
	 * @author A-2408
	 *
	 */
	public interface InvoiceSettlementSession extends ScreenSession {
	/**
	 * 	
	 * @param status
	 * @return
	 */
	public String getFromSave();
	/**
	 * 
	 * @param status
	 */
	public void setFromSave(String status);
	/**
	 * @return
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();

	/**
	 * @param oneTimeVOs
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	/**
	 *
	 */
	public void removeOneTimeVOs();
	
	/**
     * @return
     */
    public ArrayList<InvoiceSettlementVO> getInvoiceSettlementVOs();
    /**
     * @param invsettlementvos
     */
    public void setInvoiceSettlementVOs(ArrayList<InvoiceSettlementVO> invsettlementvos);
    /**
     * 
     */
    public void removeInvoiceSettlementVOs();
    /**
     * @return
     */
    public ArrayList<InvoiceSettlementHistoryVO> getInvoiceSettlementHistoryVOs();
    /**
     * @param invsettlementvos
     */
    public void setInvoiceSettlementHistoryVOs(ArrayList<InvoiceSettlementHistoryVO> invsettlementvos);
    /**
     * 
     */
    public void removeInvoiceSettlementHistoryVOs();
    /**
     * @return
     */
    public Page<GPASettlementVO> getGPASettlementVO();
    /**
     * @param gpaSettlementVOs
     */
    public void setGPASettlementVO(Page<GPASettlementVO> gpaSettlementVO);
    
    /**
     * @return
     */
    public Page<GPASettlementVO> getGPASettlementVOs();
    /**
     * @param gpaSettlementVOs
     */
    public void setGPASettlementVOs(Page<GPASettlementVO> gpaSettlementVOs);
    
    /**
     * @return
     */
    public Page<GPASettlementVO> getSelectedGPASettlementVOs();
    /**
     * @param gpaSettlementVOs
     */
    public GPASettlementVO getSelectedGPASettlementVO();
    public void setSelectedGPASettlementVO(GPASettlementVO gpaSettlementVO);
    public void setSelectedGPASettlementVOs(Page<GPASettlementVO> gpaSettlementVOs);
    public void setInvoiceSettlementFilterVO(InvoiceSettlementFilterVO invoiceSettlementFilterVO);
    public InvoiceSettlementFilterVO getInvoiceSettlementFilterVO();
    /**
     * 
     * @return
     */
    public Page<InvoiceSettlementVO> getInvoiceSettlementDetailVOs();
    /**
     * 
     * @param invoiceSettlementDetailVOs
     */
    public void setInvoiceSettlementDetailVOs(Page<InvoiceSettlementVO> invoiceSettlementDetailVOs) ;
}
