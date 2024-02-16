/*
 * FlightReconciliationSession.java Created on July 06, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.MailReconciliationVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-3217
 *
 */
public interface FlightReconciliationSession extends ScreenSession{
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs);

	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs();
	public Collection<MailReconciliationVO> getMailReconciliationVOs();
	public void setMailReconciliationVOs(Collection<MailReconciliationVO> mailReconciliationVOs);
	public void setOperationalFlightVO(OperationalFlightVO operationalFlightVO);
	public OperationalFlightVO getOperationalFlightVO();
}
