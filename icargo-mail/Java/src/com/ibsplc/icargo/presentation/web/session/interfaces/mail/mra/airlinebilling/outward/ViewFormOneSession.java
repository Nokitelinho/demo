/*
 * ViewFormOneSession.java Created on july 15, 2006
 * 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. 
 * 
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO;

import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-3456
 * 
 */
public interface ViewFormOneSession extends ScreenSession {

	/**
	 * 
	 * @return InterlineFilterVO
	 */
	public InterlineFilterVO getInterlineFilterVO();

	/**
	 * 
	 * @param interlineFilterVo
	 */
	public void setInterlineFilterVO(InterlineFilterVO interlineFilterVo);

	/**
	 * 
	 * @param invoiceFormOneDetailsVOs
	 */
	public void setInvoiceFormOneDetailsVOs(
			Collection<InvoiceInFormOneVO> invoiceFormOneDetailsVOs);

	/**
	 * 
	 * @return Collection(InvoiceInFormOneVO)
	 */
	public Collection<InvoiceInFormOneVO> getInvoiceFormOneDetailsVOs();

	/**
	 * 
	 * 
	 */
	public void removeInvoiceFormOneDetailsVOs();

	/**
	 * 
	 * @param FormOneVO
	 */
	public void setFormOneVO(FormOneVO formOneVO);

	/**
	 * 
	 * @return FormOneVO
	 */
	public FormOneVO getFormOneVO();

	/**
	 * 
	 * 
	 */
	public void removeFormOneVO();

	/**
	 * 
	 * @return String
	 */
	public String getCloseStatus();

	/**
	 * 
	 * @param closeStatus
	 */
	public void setCloseStatus(String closeStatus);

	/**
	 * 
	 * 
	 */
	public void removeCloseStatus();

	/**
	 * 
	 * @return
	 */
	public String getClrperiod();

	/**
	 * 
	 * @param closeStatus
	 */
	public void setClrperiod(String clrperiod);

	/**
	 * 
	 * 
	 */
	public void removeAirlineCode();
	/**
	 * 
	 * @return
	 */
	public String getAirlineCode();

	/**
	 * 
	 * @param closeStatus
	 */
	public void setAirlineCode(String airlineCode);

	/**
	 * 
	 * 
	 */
	public void removeClrperiod();
	
	
	
}
