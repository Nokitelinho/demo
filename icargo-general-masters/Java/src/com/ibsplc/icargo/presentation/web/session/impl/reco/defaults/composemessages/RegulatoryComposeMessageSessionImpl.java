package com.ibsplc.icargo.presentation.web.session.impl.reco.defaults.composemessages;

import java.util.ArrayList;

import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.composemessages.RegulatoryComposeMessageSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public class RegulatoryComposeMessageSessionImpl extends AbstractScreenSession implements 
RegulatoryComposeMessageSession{

	/** The Module Name. */
	private static final String MODULE = "reco.defaults";
	
	/** The Constant SCREENID. */
	private static final String SCREENID = "reco.defaults.maintainregulatorycompliance";
	
	/** The Constant KEY_TOTALRECORDS. */
	private static final String KEY_TOTALRECORDS = "totalrecords";
	
	/** The Constant KEY_FLIGHTNOTESLISTVO. */
	private static final String  KEY_COMPOSE_MESSAGES = "composeMessages";
	
	/** The Constant KEY_FLIGHTNOTESERRORLIST. */
	private static final String  KEY_COMPOSE_MESSAGE_ERROR_LIST = "composeMessageErrorList";
	
	
	/**
	 * method to get ModuleName.
	 *
	 * @return String
	 */
	public String getModuleName() {
		return MODULE;
	}

	/**
	 * method to get ScreenID.
	 *
	 * @return String
	 */
	public String getScreenID() {
		return SCREENID;
	}


	public void setRegulatoryMessages(Page<RegulatoryMessageVO> regulatoryMessages) {
		setAttribute(KEY_COMPOSE_MESSAGES, (Page<RegulatoryMessageVO>) regulatoryMessages);
	}

	public Page<RegulatoryMessageVO> getRegulatoryMessages() {
		return (Page<RegulatoryMessageVO>) getAttribute(KEY_COMPOSE_MESSAGES);
	}
	
	public void removeRegulatoryMessages() {
		removeAttribute(KEY_COMPOSE_MESSAGES);
	}
	
	public void setRegulatoryMessageErrorList(ArrayList<RegulatoryMessageVO> errorList) {
		setAttribute(KEY_COMPOSE_MESSAGE_ERROR_LIST, (ArrayList<RegulatoryMessageVO>) errorList);
	}

	public ArrayList<RegulatoryMessageVO> getRegulatoryMessageErrorList() {
		return (ArrayList<RegulatoryMessageVO>) getAttribute(KEY_COMPOSE_MESSAGE_ERROR_LIST);
	}
	
	public void removeRegulatoryMessageErrorList() {
		removeAttribute(KEY_COMPOSE_MESSAGE_ERROR_LIST);
	}
	
	public Integer getTotalRecords() {
		return getAttribute(KEY_TOTALRECORDS);
	}
	
	public void setTotalRecords(int totalRecords) {
		setAttribute(KEY_TOTALRECORDS, totalRecords);
	}
	
	public void removeTotalRecords() {
		removeAttribute(KEY_TOTALRECORDS);
	}


}
