package com.ibsplc.icargo.presentation.web.session.impl.mail.operations.ux;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.addons.trucking.vo.TruckOrderFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.ListMailPopupSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * 
 * @author A-7929
 *
 */
public class ListMailPopupSessionImpl extends AbstractScreenSession implements ListMailPopupSession {
	
	private static final String SCREEN_ID = "mail.operations.ux.listmailbagpopup";
	private static final String MODULE_NAME = "mail.operations";
	
	private static final String MAILBAGVO_COLL="mailbagcollection";	
	private static final String MAILBAGVOS = "mailbagvos"; 
	private static final String KEY_FILTERVO = "truckingOrderfilterVO";
	private static final String KEY_ONETIME_VO = "oneTimeVOs";
	
	
	
	
	public String getScreenID() {
		return SCREEN_ID;
	}
	public String getModuleName() {
		return MODULE_NAME;
	}
	
	
	public void setCarditMailbagVOsCollection(Page<MailbagVO> mailbagvos) {
		setAttribute(MAILBAGVO_COLL,(Page<MailbagVO>)mailbagvos);
	}
	public Page<MailbagVO> getCarditMailbagVOsCollection() {
		return (Page<MailbagVO>)getAttribute(MAILBAGVO_COLL);
	}
 
	
	public void setTruckOrderFilterDetails(TruckOrderFilterVO truckOrderFilterVO) {
		setAttribute(KEY_FILTERVO, truckOrderFilterVO);
	}
	public TruckOrderFilterVO getTruckOrderFilterDetails() {
	 	return getAttribute(KEY_FILTERVO);
	}
	public void removeTruckOrderFilteVO() {
		removeAttribute(KEY_FILTERVO);
		
	}
	
	public void setLyingMailbagVOs(Page<MailbagVO> mailbagvos) {
		setAttribute(MAILBAGVOS,(Page<MailbagVO>)mailbagvos);
	}
	public Page<MailbagVO> getLyingMailbagVOs() {
		return (Page<MailbagVO>)getAttribute(MAILBAGVOS);
	}
	
	
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {
		return (HashMap<String, Collection<OneTimeVO>>)getAttribute(KEY_ONETIME_VO);
	}
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {
		setAttribute(KEY_ONETIME_VO,(HashMap<String, Collection<OneTimeVO>>)oneTimeVOs);
	}

}
