/*
 * ListULDAgreementCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.listuldagreement;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListULDAgreementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDAgreementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ListULDAgreementCommand  extends BaseCommand {
    
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
    private static final String MODULE_NAME = "uld.defaults";
    private static final String SCREEN_ID = "uld.defaults.listuldagreement";
    private static final String BLANK = "";
    private static final String SELECT="ALL";

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	Log log = LogFactory.getLogger("ULD_DEFAULTS");        
    	log.entering("ListCommand","ULDAgreement"); 
    	ListULDAgreementForm form = (ListULDAgreementForm)invocationContext.screenModel;
    	 ApplicationSessionImpl applicationSession = getApplicationSession();
 		LogonAttributes logonAttributes = applicationSession.getLogonVO();
 		ListULDAgreementSession session = getScreenSession(MODULE_NAME,SCREEN_ID);
 		ULDAgreementFilterVO filterVO = new ULDAgreementFilterVO();
 		form.setListStatus("");
 		if("Y".equals(form.getCloseStatus())){
 			log.log(Log.FINE,"getting filter from session------>"+filterVO);
 			 filterVO = session.getULDAgreementFilterVO();
 		}else{
 		filterVO.setCompanyCode(logonAttributes.getCompanyCode());
 		filterVO.setPageNumber(Integer.parseInt(form.getDisplayPageNum()));
 		if(form.getAgreementNumber()!= null && form.getAgreementNumber().trim().length()>0){
 			filterVO.setAgreementNumber(form.getAgreementNumber().toUpperCase());
 			log.log(Log.FINE,"agreementno-------------->"+form.getAgreementNumber());
 		}else{//Added by A-7359 for ICRD-276383
 			
 			if(form.getPartyType()!= null && !BLANK.equals(form.getPartyType())&& !SELECT.equals(form.getPartyType())){
 			filterVO.setPartyType(form.getPartyType().toUpperCase());
 		}
 		
 		if(form.getPartyCode()!= null && !BLANK .equals(form.getPartyCode()) ){
 			filterVO.setPartyCode(form.getPartyCode().toUpperCase());
 		}
 		/*else{
 			ErrorVO errorVO = new ErrorVO("uld.defaults.topartcodeymand");
 			invocationContext.addError(errorVO);
 			//Added by A-7359 for ICRD-276383 starts here
 			if(form.getFromPartyCode()!= null && !BLANK .equals(form.getFromPartyCode()) ){
 	 			filterVO.setFromPartyCode(form.getFromPartyCode().toUpperCase());
 	 		}
 			session.setULDAgreementFilterVO(filterVO);
 			//Added by A-7359 for ICRD-276383 ends here
 			invocationContext.target=SCREENLOAD_FAILURE;
 			return;
 		}*/
 		//added as part of ICRD-232684 by A-4393 starts 
 		if(form.getFromPartyType()!= null && !BLANK.equals(form.getFromPartyType())&& !SELECT.equals(form.getFromPartyType())){
 			filterVO.setFromPartyType(form.getFromPartyType().toUpperCase());
 		}
 		if(form.getFromPartyCode()!= null && !BLANK .equals(form.getFromPartyCode()) ){
 			filterVO.setFromPartyCode(form.getFromPartyCode().toUpperCase());
 		}
 		/*else{
 			ErrorVO errorVO = new ErrorVO("uld.defaults.frompartcodeymand");
 			invocationContext.addError(errorVO);
 			//Added by A-7359 for ICRD-276383 starts here
 			if(form.getPartyCode()!= null && !BLANK .equals(form.getPartyCode()) ){
 	 			filterVO.setPartyCode(form.getPartyCode().toUpperCase());
 	 		}
 			session.setULDAgreementFilterVO(filterVO);
 			//Added by A-7359 for ICRD-276383 ends here
 			invocationContext.target=SCREENLOAD_FAILURE;
 			return;
 		}*/
 		//added as part of ICRD-232684 by A-4393 ends 
 		}
 		if(form.getAgreementListDate()!= null && !BLANK.equals(form.getAgreementListDate())){
 			LocalDate agreementDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
 			filterVO.setAgreementDate(agreementDate.setDate(form.getAgreementListDate()));
 		}
 		if(form.getTransactionType() != null && !BLANK .equals(form.getTransactionType())
 				&&!SELECT.equals(form.getTransactionType())){
 			filterVO.setTxnType(form.getTransactionType());
 		}
 		if(form.getAgreementStatus() != null && !BLANK.equals(form.getAgreementStatus())
 				&&!SELECT.equals(form.getAgreementStatus())){
 			filterVO.setAgreementStatus(form.getAgreementStatus());
 		}
 		if(form.getAgreementFromDate() != null && !BLANK.equals(form.getAgreementFromDate())){
 			LocalDate fromDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
 			filterVO.setAgreementFromDate(fromDate.setDate(form.getAgreementFromDate()));
 		}
 		if(form.getAgreementToDate() != null && !BLANK.equals(form.getAgreementToDate()) ){
 			LocalDate toDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
 			filterVO.setAgreementToDate(toDate.setDate(form.getAgreementToDate()));
 		}
 		} 		
 		// Added by A-5183 for < ICRD-22824 > Starts			
			if(ListULDAgreementForm.PAGINATION_MODE_FROM_LIST.equals(form.getNavigationMode())
					||form.getNavigationMode() == null){
				
				filterVO.setTotalRecordsCount(-1);
				filterVO.setPageNumber(1); 
				
			}else if(ListULDAgreementForm.PAGINATION_MODE_FROM_NAVIGATION_LINK.equals(form.getNavigationMode()))
			{				
				filterVO.setTotalRecordsCount(session.getTotalRecordsCount());
				filterVO.setPageNumber(Integer.parseInt(form.getDisplayPageNum()));				
			}		
		// Added by A-5183 for < ICRD-22824 > Ends		
			
 		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
 		Page<ULDAgreementVO> agreementVOs = null;
 		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
 		log.log(Log.INFO,"filterVO"+filterVO);
 		session.setULDAgreementFilterVO(filterVO);
 		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
 		try{
 			
 			agreementVOs = delegate.listULDAgreements(filterVO);
 			
 		}catch(BusinessDelegateException exception){
 			exception.getMessage();
 			error = handleDelegateException(exception);
 		}
 		if(agreementVOs != null && agreementVOs.size()>0){
 		form.setListStatus("N");
 		log.log(log.FINE,"agreementVOs---------------------------->>"+agreementVOs);
 		session.setUldAgreements(agreementVOs);
 		session.setTotalRecordsCount(agreementVOs.getTotalRecordCount());
 		invocationContext.target=SCREENLOAD_SUCCESS;
 		}
 		else{
 			session.setUldAgreements(null);
 			ErrorVO errorVO = new ErrorVO("uld.defaults.norecordsfound");
 			errors.add(errorVO);
 			invocationContext.addAllError(errors);
 			invocationContext.target=SCREENLOAD_FAILURE;
 			return;
 		}
    }
}
