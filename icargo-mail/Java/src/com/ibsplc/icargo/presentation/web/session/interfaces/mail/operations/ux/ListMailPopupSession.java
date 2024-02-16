package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.addons.trucking.vo.TruckOrderFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * 
 * @author A-7929
 *
 */
public interface ListMailPopupSession extends ScreenSession {
	
	
	public void setCarditMailbagVOsCollection(Page<MailbagVO> mailbagvos); //for cardit
	public Page<MailbagVO> getCarditMailbagVOsCollection();
	
	public void setLyingMailbagVOs(Page<MailbagVO> mailbagvos); // for lying list
	public Page<MailbagVO> getLyingMailbagVOs();
	
	
	public void setTruckOrderFilterDetails(TruckOrderFilterVO truckOrderFilterVO); 
	public TruckOrderFilterVO getTruckOrderFilterDetails();
	public void removeTruckOrderFilteVO() ;
	
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

}
