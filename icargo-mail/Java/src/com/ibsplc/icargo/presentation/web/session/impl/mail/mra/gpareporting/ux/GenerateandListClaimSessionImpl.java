/**
 * GenerateandListClaimSessionImpl.java Created on March 06, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpareporting.ux;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.GenerateandListClaimSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


public class GenerateandListClaimSessionImpl extends AbstractScreenSession implements GenerateandListClaimSession {

	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREEN_ID = "mail.mra.gpareporting.ux.generateandlistclaim";
	private static final String LISTCLAIMBULKVOs="listclaimbulkvos";
	private static final String TOTAL_RECORDS="totalrecords";	
	private static final String FILTER_PARAM="filterparam";
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
		
		
    public void setFilterParamValues(InvoicFilterVO filterparamvalues){
    	setAttribute(FILTER_PARAM, filterparamvalues);
    }
    public InvoicFilterVO getFilterParamValues(){
    	return getAttribute(FILTER_PARAM);
    }
	public void setListclaimbulkvos(Page<ClaimDetailsVO> listclaimbulkvos) {
		setAttribute(LISTCLAIMBULKVOs,(Page<ClaimDetailsVO>)listclaimbulkvos);
	}
	public Page<ClaimDetailsVO> getListclaimbulkvos() {
		return (Page<ClaimDetailsVO>)getAttribute(LISTCLAIMBULKVOs);
	}
	public void setTotalRecords(int totalRecords){
		setAttribute(TOTAL_RECORDS,totalRecords);
	}
	public int getTotalRecords(){
		return getAttribute(TOTAL_RECORDS);
	}
}
