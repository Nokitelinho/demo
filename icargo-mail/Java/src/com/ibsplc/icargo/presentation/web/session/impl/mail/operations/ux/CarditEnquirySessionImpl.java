package com.ibsplc.icargo.presentation.web.session.impl.mail.operations.ux;

import java.util.ArrayList;
import java.util.List;

import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.CarditEnquirySession;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.session.impl.mail.operations.ux.MailBagHistorySessionImpl.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	12-Sep-2018		:	Draft
 */
public class CarditEnquirySessionImpl extends AbstractScreenSession implements CarditEnquirySession
			 {
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.carditenquiry";
	
	private static final String SELECTED_MAILBAGS = "selectedMailbags";
	private static final String SELECTED_RESDITS = "selectedResdits";
	private static final String SELECTED_RESDIT_VERSION = "selectedResditVersion";
	@Override
	public String getModuleName() {
		
		return MODULE_NAME;
	}
	@Override
	public String getScreenID() {
	
		return SCREEN_ID;
	}

	
	@Override
	public String getSelectedResditVersion() {

		return getAttribute(SELECTED_RESDIT_VERSION);
	}
	@Override
	public void setSelectedResditVersion(String selectedResditVersion) {
		setAttribute(SELECTED_RESDIT_VERSION,selectedResditVersion);
		
	}
	
	@Override
	public Collection<MailbagVO> getSelectedMailbags() {
		return (Collection<MailbagVO>)getAttribute(SELECTED_MAILBAGS);
		
	}
	@Override
	public void setSelectedMailbags(Collection<MailbagVO> selectedMailbags)
		 {
			 setAttribute(SELECTED_MAILBAGS,(ArrayList<MailbagVO>) selectedMailbags);
	}
	@Override
	public List<String> getSelectedResdits() {
		return ( List<String>)getAttribute(SELECTED_RESDITS);
		
	}
	@Override
	public void setSelectedResdits(List<String> selectedResdits) {
		 setAttribute(SELECTED_RESDITS,(ArrayList<String>) selectedResdits);
		 
		
	}
	
	
	
	



}
