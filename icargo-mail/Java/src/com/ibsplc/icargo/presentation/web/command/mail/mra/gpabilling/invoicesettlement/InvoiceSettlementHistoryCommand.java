/*
 * InvoiceSettlementHistoryCommand.java Created on Mar 26, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoicesettlement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementHistoryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.InvoiceSettlementSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class InvoiceSettlementHistoryCommand extends BaseCommand {
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

    private static final String SCREEN_ID ="mailtracking.mra.gpabilling.invoicesettlement";
    
    private static final String SCREENLOAD_SUCCESS ="screenload_success";
    private static final String NO_DATA="mailtracking.mra.gpabilling.msg.err.noresultsfound";
    private static final String CLASS_NAME = "ScreenLoadCommand";
    
   
    /**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
    throws CommandInvocationException{
    	Log log = LogFactory.getLogger("MRA_GPABILLING");
    	log.entering(CLASS_NAME, "execute");
    	InvoiceSettlementSession session=(InvoiceSettlementSession)getScreenSession(MODULE_NAME,SCREEN_ID);
    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	InvoiceSettlementForm form=(InvoiceSettlementForm)invocationContext.screenModel;
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	ErrorVO errorVO=null;
    	InvoiceSettlementFilterVO filterVO=new InvoiceSettlementFilterVO(); 
        //Modefied by A-8399 as part ICRD-277607  
    	Collection<InvoiceSettlementVO> invoiceSettlementVOs= session.getGPASettlementVO().iterator().next().getInvoiceSettlementVOsPage();
    	filterVO.setCompanyCode(companyCode);
    	String [] selectedRow= form.getCheck();
    	int row = 0;
    	double totalAmt=0.0;
    	for(InvoiceSettlementVO invoiceSettlementVO: invoiceSettlementVOs){
    		
    			if (row == Integer.parseInt(selectedRow[0])) {	
    				filterVO.setGpaCode(invoiceSettlementVO.getGpaCode());
    				filterVO.setGpaName(invoiceSettlementVO.getGpaName());
    				filterVO.setInvoiceNumber(invoiceSettlementVO.getInvoiceNumber()); 				
    				
    			
    		}
    		row++;
    	}
    	
    	log.log(log.INFO,"filterVO for history"+filterVO);
    	session.removeInvoiceSettlementHistoryVOs();
    	Collection<InvoiceSettlementHistoryVO> historyVOs=null;
    	try{
    		historyVOs=new MailTrackingMRADelegate().findSettlementHistory(filterVO);
    	}
    	catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);

		}
    	//for test
    	/*historyVOs=new ArrayList<InvoiceSettlementHistoryVO>();
    	InvoiceSettlementHistoryVO vo1=new InvoiceSettlementHistoryVO();
    	vo1.setSettlementDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
    	vo1.setAmountInSettlementCurrency(23455);
    	historyVOs.add(vo1);*/
    	//for test
    	if(historyVOs!=null && historyVOs.size()>0) {
			session.setInvoiceSettlementHistoryVOs((ArrayList<InvoiceSettlementHistoryVO>)historyVOs);
			for(InvoiceSettlementHistoryVO historyVo : historyVOs){
				if(historyVo.getChequeAmount()!=null){
					BigDecimal bigDecimalActualamount = new BigDecimal(historyVo.getChequeAmount().getAmount()).setScale(2,RoundingMode.HALF_UP);
					totalAmt=totalAmt+(bigDecimalActualamount.doubleValue());
					form.setTotalAmount(totalAmt);
				}
			}
		}
    	else{
    		errorVO=new ErrorVO(NO_DATA);
			errors.add(errorVO);
    	}
    	form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    	
    }
    }