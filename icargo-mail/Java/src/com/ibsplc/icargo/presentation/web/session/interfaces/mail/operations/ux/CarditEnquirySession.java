package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux;


import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import java.util.List;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailBagHistorySession.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	12-Sep-2018		:	Draft
 */
public interface CarditEnquirySession extends ScreenSession {
	
	 Collection<MailbagVO> getSelectedMailbags();
	 void setSelectedMailbags(Collection<MailbagVO> selectedMailbagVOs);
	 String getSelectedResditVersion(); 
	 void setSelectedResditVersion(String selectedResditVersion);
	 List<String> getSelectedResdits();
	 void setSelectedResdits(List<String> selectedResdits);
	 
	 
	 
}
