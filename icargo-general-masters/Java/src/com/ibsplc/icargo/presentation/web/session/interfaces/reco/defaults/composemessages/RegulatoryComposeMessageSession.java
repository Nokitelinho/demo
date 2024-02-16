package com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.composemessages;

import java.util.ArrayList;

import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public interface RegulatoryComposeMessageSession extends ScreenSession{

	public String getScreenID();

	public String getModuleName();

	public Integer getTotalRecords();

	public void setTotalRecords(int totalRecords);
	
	public void removeTotalRecords();
	
	public void setRegulatoryMessages(Page<RegulatoryMessageVO> regulatoryMessages);

	public Page<RegulatoryMessageVO> getRegulatoryMessages();
	
	public void removeRegulatoryMessages();
	
	public ArrayList<RegulatoryMessageVO> getRegulatoryMessageErrorList();

	public void setRegulatoryMessageErrorList(ArrayList<RegulatoryMessageVO> errorList);
	
	public void removeRegulatoryMessageErrorList();
	
	
}
