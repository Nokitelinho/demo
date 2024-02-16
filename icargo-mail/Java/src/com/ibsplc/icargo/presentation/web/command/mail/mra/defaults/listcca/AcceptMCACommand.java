/*
* AcceptMCACommand.java Created on Jul 18, 2018
*
* Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
*
* This software is the proprietary information of IBS Software Services (P) Ltd.
* Use is subject to license terms.
*/
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listcca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListCCASession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-7540
 *
 */
public class AcceptMCACommand extends BaseCommand {
	
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";

	/**
	 * Screen ID
	 */
	private static final String LISTCCA_SCREEN = "mailtracking.mra.defaults.listcca";

	private Log log = LogFactory.getLogger("MRA DEFAULTS");
	private static final String ACCEPT_SUCCESS = "accept_success";
	private static final String ACCEPT_FAILURE = "accept_failure";
	private static final String APPROVED = "A";
	private static final String BILLABLE = "BB";
	private static final String SYS_PARAM_WRKFLOWENABLED="mailtracking.mra.workflowneededforMCA";
	private static final String BASED_ON_RULES = "R";//Added for IASCB-2373
	private static final String ACCEPTMCA = "ACPMCA";//Added for IASCB-2373
	private static final String UPDATE = "UPDATE";

	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering("ACCACommand", "execute");
		ListCCASession listCCASession = (ListCCASession) getScreenSession(
				MODULE_NAME, LISTCCA_SCREEN);
		MaintainCCASession maintainCCASession = (MaintainCCASession) getScreenSession(
				MODULE_NAME, MAINTAINCCA_SCREEN);
		ListCCAForm listCCAForm = (ListCCAForm) invocationContext.screenModel;		
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();				
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Map<String, String> systemParameterValues = null;
		
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		 Collection<CCAdetailsVO> ccaDetailVOs = listCCASession.getCCADetailsVOs();
	     Collection<CCAdetailsVO> selectedCCADetailsVO = new ArrayList<CCAdetailsVO>();
	     GPABillingEntriesFilterVO gpaBillingEntriesFiletVO = new GPABillingEntriesFilterVO();	    
	     String[] selectedRows = listCCAForm.getSelectedRows();
         int size = selectedRows.length;
         boolean isLocked = false;
     	
         try {
 			systemParameterValues=new SharedDefaultsDelegate().findSystemParameterByCodes(getSystemParameterTypes());
 			
 		} catch (BusinessDelegateException e) {
     		e.getMessage();
 			errors=handleDelegateException( e );
 		}
         
	     int row = 0;
	     int count = 0;
         for (CCAdetailsVO ccaDetailsVO : ccaDetailVOs) {
                         for (int j = 0; j < size; j++) {
                             if (row == Integer.parseInt(selectedRows[j])) {
                        		     if("N".equals(ccaDetailsVO.getCcaStatus()) || "C".equals(ccaDetailsVO.getCcaStatus()) ){
                                          count=count+1;
                                          //Added as part of ICRD-340886 starts
                                          if(ccaDetailsVO.getDiffAmount()!=0.0)
                                        	  ccaDetailsVO.setNetValue(ccaDetailsVO.getDiffAmount());
                                        //Added as part of ICRD-340886 starts ends
                                          //Added as part of ICRD-342939 starts
                                          setCcaDetailNetValue(ccaDetailsVO);
                                          ccaDetailsVO.setNetAmountBase(ccaDetailsVO.getNetValue());
                                          //Added as part of ICRD-342939 ends
        	                              ccaDetailsVO.setCcaStatus(APPROVED);
                                          ccaDetailsVO.setBillingStatus(BILLABLE);
                                          ccaDetailsVO.setLastUpdateUser(logonAttributes.getUserId());
     			                          ccaDetailsVO.setOperationFlag("U");
     			                          if(systemParameterValues!=null && systemParameterValues.size()>0
     			                        		  && systemParameterValues.containsValue(BASED_ON_RULES)){//Added For IASCB-2373
     			        					ccaDetailsVO.setAcceptRejectIdentifier(ACCEPTMCA);
     			                          }
                                          selectedCCADetailsVO.add(ccaDetailsVO); 
                                         }	 
                        	        }
                               }
                         row++;
         }
          if(selectedCCADetailsVO.size()==0){
        	  maintainCCASession.setStatusinfo(UPDATE);	
 			 invocationContext.target = ACCEPT_SUCCESS;
        	  return;
          }
        try {
        	Collection<LockVO> acquiredLocks = mailTrackingMRADelegate.autoMCALock(logonAttributes.getCompanyCode());
          this.log.log(3, " Lock VOs acquiredLocks-=-=->" + acquiredLocks);
        }
        catch (Exception exception)
        {
          this.log.log(Log.SEVERE, exception);
          isLocked = true;
          errors = new ArrayList<>();
          errors.add(new ErrorVO("mailtracking.mra.defaults.listcca.objectalreadylocked"));
        }
        if ((errors != null) && (errors.size() > 0)) {
          invocationContext.target = ACCEPT_FAILURE;
          invocationContext.addAllError(errors);
          return;
        }
        if (!isLocked)
        {
			InvoiceTransactionLogVO invoiceTransactionLogVO = constructInvoiceTransactionLogVO(logonAttributes);
			try {
				invoiceTransactionLogVO = mailTrackingMRADelegate
						.initiateTransactionLogForInvoiceGeneration(invoiceTransactionLogVO);
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessage();
				handleDelegateException(businessDelegateException);
			}
			gpaBillingEntriesFiletVO.setTxnCode(invoiceTransactionLogVO.getTransactionCode());
			gpaBillingEntriesFiletVO.setTxnSerialNum(invoiceTransactionLogVO.getSerialNumber());
 					try {
 						mailTrackingMRADelegate.saveAutoMCAdetails(selectedCCADetailsVO,gpaBillingEntriesFiletVO);
 					} catch (BusinessDelegateException businessDelegateException) {
 						errors = handleDelegateException(businessDelegateException);
 					}   
 					
           
      
              maintainCCASession.setCCAdetailsVOs(selectedCCADetailsVO);
			 maintainCCASession.setStatusinfo(ACCEPT_SUCCESS);	
			 invocationContext.target = ACCEPT_SUCCESS;
	      }
	}
		
	private void setCcaDetailNetValue(CCAdetailsVO ccaDetailsVO) {
		String netvalue;
		if(ccaDetailsVO.getDiffAmount()!=0.0){
			netvalue=String.valueOf(ccaDetailsVO.getDiffAmount());
			if(netvalue.contains("-")){
				ccaDetailsVO.setNetValue(Double.valueOf(netvalue.substring(1)));
			}
			else{
				ccaDetailsVO.setNetValue(ccaDetailsVO.getDiffAmount());
			}
			}
	}
	private InvoiceTransactionLogVO constructInvoiceTransactionLogVO(LogonAttributes logonAttributes) {
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
		invoiceTransactionLogVO.setInvoiceType(ACCEPTMCA);
		invoiceTransactionLogVO.setTransactionDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		invoiceTransactionLogVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		invoiceTransactionLogVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		invoiceTransactionLogVO.setInvoiceGenerationStatus(MailConstantsVO.INITIATED_STATUS);
		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
		invoiceTransactionLogVO.setRemarks("MCA approval initiated");
		invoiceTransactionLogVO.setSubSystem(MailConstantsVO.MAIL_SUBSYSTEM);
		invoiceTransactionLogVO.setTransactionTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		invoiceTransactionLogVO.setTransactionTimeUTC(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
		return invoiceTransactionLogVO;
	      }
	
	private Collection<String> getSystemParameterTypes(){
    	log.entering("AcceptMCACommand", "getSystemParameterTypes");
    	ArrayList<String> systemparameterTypes = new ArrayList<String>();
    	
    	systemparameterTypes.add(SYS_PARAM_WRKFLOWENABLED);
    	
    	log.exiting("AcceptMCACommand", "getSystemParameterTypes");
    	return systemparameterTypes;
      }
	    
	}
	