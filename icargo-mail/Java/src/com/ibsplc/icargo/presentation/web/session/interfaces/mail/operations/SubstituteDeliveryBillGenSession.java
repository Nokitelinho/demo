/*
 * SubstituteDeliveryBillGenSession.java Created on Jun 30 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-3217
 *
 */
public interface SubstituteDeliveryBillGenSession extends ScreenSession{

	public static final String MODULE_NAME = "mail.operations";
	
	public static final String SCREEN_ID = "mailtracking.defaults.substitutedeliverybill";
	
	
	/**
	 * This method is used to set ConsignmentDocumentVO to the session
	 * @param consignmentDocumentVOs
	 */
	public void setConsignmentDocumentVOs(Collection<ConsignmentDocumentVO> consignmentDocumentVOs);

	/**
	 * This method returns the ConsignmentDocumentVO
	 * @return ConsignmentDocumentVO
	 */
	public Collection<ConsignmentDocumentVO> getConsignmentDocumentVOs();
	
	
	/**
	 * This method is used to set ConsignmentDocumentVO to the session
	 * @param consignmentDocumentVOs
	 */
	public void setDocumentVOs(Collection<ConsignmentDocumentVO> consignmentDocumentVOs);

	/**
	 * This method returns the ConsignmentDocumentVO
	 * @return ConsignmentDocumentVO
	 */
	public Collection<ConsignmentDocumentVO> getDocumentVOs();
	
	
	/**
	 * This method is used to set ConsignmentDocumentVO to the session
	 * @param consignmentDocumentVOs
	 */
	public void setReportDocumentVOs(Collection<ConsignmentDocumentVO> consignmentDocumentVOs);

	/**
	 * This method returns the ConsignmentDocumentVO
	 * @return ConsignmentDocumentVO
	 */
	public Collection<ConsignmentDocumentVO> getReportDocumentVOs();
	
	/**
	 * This method is used to set ConsignmentDocumentVO to the session
	 * @param consignmentDocumentVOs
	 */
	public void setReportPrinted(int reportSelected);

	/**
	 * This method returns the ConsignmentDocumentVO
	 * @return ConsignmentDocumentVO
	 */
	public Integer getReportPrinted();
	
	
}
