/**
 * Java file       :       com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.billingentries.RerateCommand.java
 *
 * Created by      :      A-7531
 * Created on      :       29-May-2017
 *
 * Copyright 2016 Copyright 2007 IBS Software Services (camera) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 *This software is the proprietary information of Copyright 2007 IBS Software Services (camera) Ltd. All Rights Reserved.  Ltd.
 *Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.billingentries;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingEntriesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.billingentries.RerateCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7531	:	29-May-2017	:	Draft
 */
public class RerateCommand extends BaseCommand{

	
private Log log = LogFactory.getLogger("GPABillingEntries RerateCommand");
	
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.billingentries.listgpabillingentries";
	private static final String ACTION_SUCCESS = "rerate_success";
	private static final String CLASS_NAME = "RerateCommand";
	private static final String STATUS = "Re-rating of mailbags is initiated";
	private static final String SCREENID="MRA001";

	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME,"execute");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		GPABillingEntriesForm form=(GPABillingEntriesForm)invocationContext.screenModel;
		GPABillingEntriesSession session=null;
		session=(GPABillingEntriesSession) getScreenSession(MODULE_NAME,SCREEN_ID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();

		Page<DocumentBillingDetailsVO> documentBillingDetailsVOs = session.getGpaBillingDetails();
		ArrayList<DocumentBillingDetailsVO> seldocumentBillingDetailsVOs= new ArrayList<DocumentBillingDetailsVO>();

		String[] rowId=form.getCheck();
		String[] actualRowIds=rowId;



		if(rowId != null && documentBillingDetailsVOs != null){
			log.log(Log.INFO, "rowid length-->", actualRowIds.length);
			try {
				for(int i=0; i < actualRowIds.length; i++){
					String selectedId = actualRowIds[i];
					DocumentBillingDetailsVO vo = documentBillingDetailsVOs.get(Integer.parseInt(selectedId));
					if(!"VD".equals(vo.getBillingStatus())){
						vo.setScreenID(SCREENID);
						seldocumentBillingDetailsVOs.add(vo);
					}
				}
			} catch (NumberFormatException e) {
				log.log(Log.SEVERE, "execption",e.getMessage());
			} 

		}

		invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
		invoiceTransactionLogVO.setInvoiceType(MRAConstantsVO.TOBERERATED);
		invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		invoiceTransactionLogVO.setInvoiceGenerationStatus(InvoiceTransactionLogVO.OPERATION_FLAG_INSERT);
		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
		invoiceTransactionLogVO.setRemarks(STATUS);
		invoiceTransactionLogVO.setSubSystem(MRAConstantsVO.SUBSYSTEM);
		invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		invoiceTransactionLogVO.setPeriodFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
		invoiceTransactionLogVO.setPeriodTo(new LocalDate(LocalDate.NO_STATION, Location.NONE,true));
		invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
		invoiceTransactionLogVO.getSerialNumber();
		invoiceTransactionLogVO.getTransactionCode();

		try{
			invoiceTransactionLogVO = delegate.initiateTransactionLogForInvoiceGeneration( invoiceTransactionLogVO );
		}catch(BusinessDelegateException ex){
			errors = this.handleDelegateException(ex);
			log.log(Log.SEVERE, "execption",errors);
		}

	
		String txnlogInfo = new StringBuilder(invoiceTransactionLogVO.getTransactionCode()).append("-").append(invoiceTransactionLogVO.getSerialNumber()).toString();

		try {
			delegate.reRateMailbags(seldocumentBillingDetailsVOs,txnlogInfo);
			form.setReRated(true);
		} catch (BusinessDelegateException e) {

			log.log(Log.SEVERE, "execption",e.getMessage());
		}

		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}
	
	
	}
	
	
	

