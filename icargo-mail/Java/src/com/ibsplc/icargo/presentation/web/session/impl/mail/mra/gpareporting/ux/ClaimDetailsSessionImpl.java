/**
 * GenerateandListClaimSessionImpl.java Created on March 06, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpareporting.ux;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.ClaimDetailsSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


public class ClaimDetailsSessionImpl extends AbstractScreenSession implements ClaimDetailsSession {

	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREEN_ID = "mail.mra.gpareporting.ux.claimDetails";
	private static final String LISTCLAIMDTLSVOs="listclaimdtlsvos";
	private static final String TOTAL_RECORDS="totalrecords";	
	private static final String FILTER_PARAM="filterparam";
	private static final String STATUS_LEVEL="statusLevel";
	private static final String CLAIM_TYPE="claimType";
	/**
	 * @return Screen Id
	 */
	public String getScreenID() {
		
		return SCREEN_ID;
	}

	/**
	 * @return Module Name
	 */
	public String getModuleName() {
		
		return MODULE_NAME;
	}
		
	public void setStatus(ArrayList<OneTimeVO> sourceOneTime) {
		setAttribute(STATUS_LEVEL, sourceOneTime);
	}
	public ArrayList<OneTimeVO> getStatus() {
		return (ArrayList<OneTimeVO>)getAttribute(STATUS_LEVEL);
	}	
    public void setFilterParamValues(InvoicFilterVO filterparamvalues){
    	setAttribute(FILTER_PARAM, filterparamvalues);
    }
    public void setClaimType(ArrayList<OneTimeVO> claimOneTime) {
		setAttribute(CLAIM_TYPE, claimOneTime);
	}
	public ArrayList<OneTimeVO> getClaimType() {
		return (ArrayList<OneTimeVO>)getAttribute(CLAIM_TYPE);
	}	
  
    public InvoicFilterVO getFilterParamValues(){
    	return getAttribute(FILTER_PARAM);
    }
	public void setListclaimdtlsvos(Page<ClaimDetailsVO> listclaimdtlsvos) {
		setAttribute(LISTCLAIMDTLSVOs,(Page<ClaimDetailsVO>)listclaimdtlsvos);
	}
	public Page<ClaimDetailsVO> getListclaimdtlsvos() {
		return (Page<ClaimDetailsVO>)getAttribute(LISTCLAIMDTLSVOs);
	}
	public void setTotalRecords(int totalRecords){
		setAttribute(TOTAL_RECORDS,totalRecords);
	}
	public int getTotalRecords(){
		return getAttribute(TOTAL_RECORDS);
	}
}
