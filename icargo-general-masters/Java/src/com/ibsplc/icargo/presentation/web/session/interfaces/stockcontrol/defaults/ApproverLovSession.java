/*
 * ApproverLovSession.java Created on Aug 30, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;

import java.util.Collection;


import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;

import com.ibsplc.icargo.framework.session.ScreenSession;



/**
 * @author kirupakaran
 *
 */
public interface ApproverLovSession extends ScreenSession {

    public Collection<OneTimeVO> getOneTimeStock();
    public void setOneTimeStock(Collection<OneTimeVO>  collOneTimeStockVO);

}
