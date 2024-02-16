/*
 * FlightReconciliationSessionImpl.java Created on July 06, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.MailReconciliationVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.FlightReconciliationSession;

/**
 * @author A-3217
 *
 */
public class FlightReconciliationSessionImpl extends AbstractScreenSession implements FlightReconciliationSession{

	private static final String SCREENID = "mailtracking.defaults.flightreconcilation";

	private static final String MODULENAME = "mail.operations";

	private static final String ONETIME_KEY = "onetimekey";
	private static final String VOS_KEY = "voskey";
	private static final String VO_KEY = "vokey";

	public String getModuleName() {

		return MODULENAME;
	}

	public String getScreenID() {

		return SCREENID;
	}

	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs) {
		setAttribute(ONETIME_KEY, oneTimeVOs);
	}

	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs() {
		return(HashMap<String, Collection<OneTimeVO>>) getAttribute(ONETIME_KEY);
	}
   public Collection<MailReconciliationVO> getMailReconciliationVOs(){
	   return(Collection<MailReconciliationVO>) getAttribute(VOS_KEY);
   }
   public void setMailReconciliationVOs(Collection<MailReconciliationVO> mailReconciliationVOs){
	   setAttribute(VOS_KEY, (ArrayList<MailReconciliationVO>)mailReconciliationVOs);
   }
   public void setOperationalFlightVO(OperationalFlightVO operationalFlightVO){
	   setAttribute(VO_KEY, (OperationalFlightVO)operationalFlightVO);
   }
   public OperationalFlightVO getOperationalFlightVO(){
	   return (OperationalFlightVO)getAttribute(VO_KEY);
   }
}
