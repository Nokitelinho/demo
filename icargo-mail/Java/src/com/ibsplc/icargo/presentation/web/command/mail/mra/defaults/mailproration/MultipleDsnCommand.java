/*
 * MultipleDsnCommand.java Created on May 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.mailproration;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchLovVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MailProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MailProrationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-2408
 *
 */
public class MultipleDsnCommand extends BaseCommand{
	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String FORWARD_LIST="list_screen";
	private Log log = LogFactory.getLogger(	"MRA MultipleDsnCommand");
private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.mailproration"; 
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 log.entering("DespatchLOV","execute");
		 MailProrationForm mailProrationForm=(MailProrationForm)invocationContext.screenModel;
		 MailProrationSession  mailProrationSession = 
				(MailProrationSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		 ApplicationSessionImpl applicationSession = getApplicationSession();
		 LogonAttributes logonAttributes = applicationSession.getLogonVO();
			
			Page<DespatchLovVO> despatchLovVOs=null;	
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String companyCode=logonAttributes.getCompanyCode();
		if(mailProrationForm.getDsn()!=null && mailProrationForm.getDsn().trim().length()>0){
			String dsn=mailProrationForm.getDsn(); 
		 
			MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
			
			int pageno=1;
			
			try{
				despatchLovVOs=delegate.findDespatchLov(companyCode,dsn,"","",pageno);
			}
			catch(BusinessDelegateException businessDelegateException){
				errors=handleDelegateException(businessDelegateException);
			}
		}
			  if( errors != null && errors.size() > 0 ){
		        	invocationContext.addAllError(errors);
		        	 invocationContext.target=SCREENLOAD_SUCCESS;
		        	 return;
		        }
			  if(despatchLovVOs!=null && despatchLovVOs.size()>0){
				  log.log(Log.INFO, "size of despatch", despatchLovVOs.size());
				if(despatchLovVOs.size()==1){
					  DespatchLovVO vo=  despatchLovVOs.get(0);
					  log.log(Log.INFO, "despatch", vo);
					mailProrationForm.setDespatchSerialNo(vo.getDespatch());
					  invocationContext.target=FORWARD_LIST; 
					 
			  }else{
				  mailProrationForm.setMoreValuesFlag(true);
				  invocationContext.target=SCREENLOAD_SUCCESS;
				
			  }
				  
			  }else{
				  invocationContext.target=FORWARD_LIST;
			  }
				ProrationFilterVO prorationFilterVO = new ProrationFilterVO();
			  updateFilterVO(mailProrationForm,prorationFilterVO); 
		 mailProrationSession.setProrationFilterVO(prorationFilterVO);
		 
			log.exiting("DespatchLOV","execute");
	}
	/**
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
