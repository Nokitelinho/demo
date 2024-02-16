/*
 * UpdateSessionCommand.java Created on Mar 7, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.mailproration;


import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MailProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MailProrationForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;


/**
 * @author Ruby Abraham
 * Command class for updating session.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1			Mar 7, 2007	  Ruby Abraham				Initial draft
 */
public class UpdateSessionCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.mailproration";
	
	private static final String CLASS_NAME = "UpdateSessionCommand";
	
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		MailProrationSession  mailProrationSession = 
			(MailProrationSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		
		MailProrationForm mailProrationForm=(MailProrationForm)invocationContext.screenModel;
			
		
		ProrationFilterVO prorationFilterVO = new ProrationFilterVO();
		
		updateFilterVO(mailProrationForm,prorationFilterVO);	
		
		mailProrationSession.setProrationFilterVO(prorationFilterVO);

		log.exiting(CLASS_NAME, "execute");
	}
	
	
	/**
	 * 
	 * @param mailProrationForm
	 * @param prorationFilterVO
	 */
	private void updateFilterVO(MailProrationForm mailProrationForm,ProrationFilterVO prorationFilterVO){
		
		    log.entering("UpdateSessionCommand","updateFilterVO");
		    ApplicationSessionImpl applicationSession = getApplicationSession();
	        LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
	        String companyCode=logonAttributes.getCompanyCode().toUpperCase();
	    	log.log(Log.FINE, "CompanyCode", companyCode);
			prorationFilterVO.setCompanyCode(companyCode); 	
	    	
	    	
	    	
	    	String despatchSerNo = mailProrationForm.getDespatchSerialNo();
	    	if(despatchSerNo != null && despatchSerNo.trim().length() > 0){
	    		prorationFilterVO.setDespatchSerialNumber(despatchSerNo.toUpperCase());
		    }
			else {
				prorationFilterVO.setDespatchSerialNumber("");
			}	    	
	    	
	    	
	    	String consDocNo = mailProrationForm.getConsigneeDocNo();
	    	if(consDocNo != null && consDocNo.trim().length() > 0){
	    		
	    		prorationFilterVO.setConsigneeDocumentNumber(consDocNo.toUpperCase());
		    }
			else {
				prorationFilterVO.setConsigneeDocumentNumber("");
			}	    
	    	
	    	String flightNo = mailProrationForm.getFlightNumber();
	    	if(flightNo != null && flightNo.trim().length() > 0){
	    		prorationFilterVO.setFlightNumber(flightNo.toUpperCase());
	    	}
			else {
				prorationFilterVO.setFlightNumber("");
			}    	
	    	
	    	
	    	String flightDate = mailProrationForm.getFlightDate();
	    	if(flightDate != null && flightDate.trim().length() > 0){
	    		if(DateUtilities.isValidDate(flightDate,"dd-MMM-yyyy")) {
					LocalDate fltDate = new LocalDate(NO_STATION,NONE,false);
					fltDate.setDate(flightDate);
					prorationFilterVO.setFlightDate(fltDate);				
	    		}    		
	    	}
			else {
				prorationFilterVO.setFlightDate(null);
			}
	    	
			
			log.log(Log.FINE, "PRORATIONFILTERVO---->", prorationFilterVO);
			log.exiting("UpdateSessionCommand","updateFilterVO");    	
	    	
	}
}
