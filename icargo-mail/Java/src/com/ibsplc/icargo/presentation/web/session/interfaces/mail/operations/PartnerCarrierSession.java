/*
 * PartnerCarrierSession.java Created on August 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2047
 *
 */
public interface PartnerCarrierSession extends ScreenSession {
	
	 /**
     * @return ArrayList<PartnerCarrierVO>
     */
    ArrayList<PartnerCarrierVO> getPartnerCarrierVOs();
    
    /**
     * @param partnerCarrierVOs
     */
    void setPartnerCarrierVOs(ArrayList<PartnerCarrierVO> partnerCarrierVOs);
    
    /**
     * @return ArrayList<PartnerCarrierVO>
     */
    String getAirport();
    
    /**
     * @param partnerCarrierVOs
     */
    void setAirport(String Airport);
}
