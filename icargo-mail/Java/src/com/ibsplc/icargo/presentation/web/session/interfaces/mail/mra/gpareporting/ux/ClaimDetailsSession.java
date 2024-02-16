/**
 * GenerateandListClaimSession.java Created on March 06, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public interface ClaimDetailsSession extends ScreenSession{
	 /**
     * @return ArrayList<PartnerCarrierVO>
     */
    
      public void setListclaimdtlsvos(Page<ClaimDetailsVO> listclaimdtlsvos);
      public Page<ClaimDetailsVO> getListclaimdtlsvos();
      public void setFilterParamValues(InvoicFilterVO filterparamvalues);
      public InvoicFilterVO getFilterParamValues();
      public void setTotalRecords(int totalRecords) ;
      public void setStatus(ArrayList<OneTimeVO> sourceOneTime);
      public ArrayList<OneTimeVO> getStatus(); 
      public void setClaimType(ArrayList<OneTimeVO> claimOneTime);
      public ArrayList<OneTimeVO> getClaimType(); 
}
