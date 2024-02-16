/*
 * GenerateandListClaimSession.java Created on March 06, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public interface GenerateandListClaimSession extends ScreenSession{
	 /**
     * @return ArrayList<PartnerCarrierVO>
     */
    
      public void setListclaimbulkvos(Page<ClaimDetailsVO> listclaimbulkvos);
      public Page<ClaimDetailsVO> getListclaimbulkvos();
      public void setFilterParamValues(InvoicFilterVO filterparamvalues);
      public InvoicFilterVO getFilterParamValues();
      public void setTotalRecords(int totalRecords) ;
    
}
