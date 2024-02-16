/*
 * SubstituteDeliveryBillGenSessionImpl.java Created on Jun 30 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SubstituteDeliveryBillGenSession;

/**
 * @author A-3217
 *
 */
public class SubstituteDeliveryBillGenSessionImpl extends AbstractScreenSession implements SubstituteDeliveryBillGenSession{

	public static final String MODULE_NAME = "mail.operations";
	
	public static final String SCREEN_ID = "mailtracking.defaults.substitutedeliverybill";

	public static final String KEY_CONSIGNMENTDOCUMENTVO = "consignmentdocumentvos";
	
	public static final String KEY_RPTCONSIGNMENTDOCUMENTVO = "reportconsignmentdocumentvos";
	
	public static final String KEY_RPTID = "reportSelected";
	
	public static final String KEY_DOCUMENTVO = "DOCUMENTVO";
	/**
	 * @return Returns the ModuleName.
	 */
	public String getModuleName() {  
		return MODULE_NAME;
	}

	/**
	 * @return Returns the ScreenID.
	 */
	public String getScreenID() {
		return SCREEN_ID;
	}

	
	/**
	 * The setter method for consignmentDocumentVOs
	 * @param consignmentDocumentVOs
	 */
	public void setConsignmentDocumentVOs(
			Collection<ConsignmentDocumentVO> consignmentDocumentVOs) {
		setAttribute(KEY_CONSIGNMENTDOCUMENTVO, (ArrayList<ConsignmentDocumentVO>)consignmentDocumentVOs);  
	}
	
	/**
	 * The getter method for consignmentDocumentVOs
	 * @return consignmentDocumentVOs
	 */
	public Collection<ConsignmentDocumentVO> getConsignmentDocumentVOs() {	
		return (Collection<ConsignmentDocumentVO>)getAttribute(KEY_CONSIGNMENTDOCUMENTVO);
	}
	
	/**
	 * The setter method for consignmentDocumentVOs
	 * @param consignmentDocumentVOs
	 */
	public void setDocumentVOs(
			Collection<ConsignmentDocumentVO> consignmentDocumentVOs) {
		setAttribute(KEY_DOCUMENTVO, (ArrayList<ConsignmentDocumentVO>)consignmentDocumentVOs);  
	}
	
	/**
	 * The getter method for consignmentDocumentVOs
	 * @return consignmentDocumentVOs
	 */
	public Collection<ConsignmentDocumentVO> getDocumentVOs() {	
		return (Collection<ConsignmentDocumentVO>)getAttribute(KEY_DOCUMENTVO);
	}
	
	/**
	 * The setter method for consignmentDocumentVOs
	 * @param consignmentDocumentVOs
	 */
	public void setReportDocumentVOs(
			Collection<ConsignmentDocumentVO> consignmentDocumentVOs) {
		setAttribute(KEY_RPTCONSIGNMENTDOCUMENTVO, (ArrayList<ConsignmentDocumentVO>)consignmentDocumentVOs);  
	}
	
	/**
	 * The getter method for consignmentDocumentVOs
	 * @return consignmentDocumentVOs
	 */
	public Collection<ConsignmentDocumentVO> getReportDocumentVOs() {	
		return (Collection<ConsignmentDocumentVO>)getAttribute(KEY_RPTCONSIGNMENTDOCUMENTVO);
	}
	
	/**
	 * 
	 */
	public void setReportPrinted(int reportSelected) {
		setAttribute(KEY_RPTID, reportSelected);  
	}
	
	/**
	 * 
	 */
	public Integer getReportPrinted() {	
		return getAttribute(KEY_RPTID);
	}
	
}
