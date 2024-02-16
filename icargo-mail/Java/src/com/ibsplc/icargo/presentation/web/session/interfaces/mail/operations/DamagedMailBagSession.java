/*
 * DamagedMailBagSession.java Created on July 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2047
 *
 */
public interface DamagedMailBagSession extends ScreenSession {
	 /**
     * @return Collection<DamagedMailbagVO>
     */
    Collection<DamagedMailbagVO> getDamagedMailbagVOs();
    
    /**
     * @param damagedMailbagVOs
     */
    void setDamagedMailbagVOs(Collection<DamagedMailbagVO> damagedMailbagVOs);
    
	 /**
     * @return String
     */
    String getMailBagId();
    
    /**
     * @param mailBagId
     */
    void setMailBagId(String mailBagId);
}
