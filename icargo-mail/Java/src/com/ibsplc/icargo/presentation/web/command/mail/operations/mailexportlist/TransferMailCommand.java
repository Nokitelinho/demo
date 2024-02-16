/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.mailexportlist.TransferMailCommand.java
 *
 *	Created by	:	A-7371
 *	Created on	:	12-Jan-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailexportlist;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailExportListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailExportListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.mailexportlist.TransferMailCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7371	:	12-Jan-2018	:	Draft
 */
public class TransferMailCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	/**
	 * TARGET
	 */
	private static final String TARGET_SUCCESS= "transfer_success";	
	private static final String MODULE_NAME = "mail.operations";	
    private static final String SCREEN_ID = "mailtracking.defaults.mailexportlist";
    private static final String SCREEN_ID_TRA = "mailtracking.defaults.transfermail";
	private static final String SCREEN_STATUS="showTransferMailScreen";	
	private static final String SCREEN_FILTER="exportlist";



	/**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		 
		log.entering("TransferMailCommand","execute");
		
		MailExportListForm mailExportListForm =  (MailExportListForm)invocationContext.screenModel;
    	MailExportListSession mailExportListSession = getScreenSession(MODULE_NAME,SCREEN_ID);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();	
		TransferMailSession transferMailSession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID_TRA);
		MailAcceptanceVO mailAcceptanceVO=mailExportListSession.getMailAcceptanceVO();
		Collection<ContainerDetailsVO> containerDetailsVOs=mailAcceptanceVO.getContainerDetails();
		Collection<MailbagVO> selMailbagVOs=new ArrayList<MailbagVO>();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();		
		String[] dsns=mailExportListForm.getSelectedContainer().split(",");
		ContainerDetailsVO containerDetailsVO= null;
		int size=dsns.length;
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();
		for(int i=0;i<size;i++){
		if((containerDetailsVOs!=null && containerDetailsVOs.size()>0)
				&&(dsns !=null && dsns.length>0)){
			String selCon=(dsns[i].split("~")[0]);
			containerDetailsVO= ((ArrayList<ContainerDetailsVO>)containerDetailsVOs).get(Integer.parseInt(selCon.substring(2, 3)));
		}
		
		DSNVO selectedvo = ((ArrayList<DSNVO>)containerDetailsVO.getDsnVOs())
		.get(Integer.parseInt(dsns[i].split("~")[1]));
	
			mailbagEnquiryFilterVO.setCarrierCode(containerDetailsVO.getCarrierCode());
			if(!("-1".equals(containerDetailsVO.getFlightNumber()))){
				mailbagEnquiryFilterVO.setFlightNumber(containerDetailsVO.getFlightNumber());
			}else{
				mailbagEnquiryFilterVO.setFlightNumber(null);
			}
			mailbagEnquiryFilterVO.setCompanyCode(mailAcceptanceVO.getCompanyCode());
			mailbagEnquiryFilterVO.setCarrierId(mailAcceptanceVO.getCarrierId());
			mailbagEnquiryFilterVO.setFlightDate(mailAcceptanceVO.getFlightDate());
			mailbagEnquiryFilterVO.setOoe(selectedvo.getOriginExchangeOffice());
			mailbagEnquiryFilterVO.setDoe(selectedvo.getDestinationExchangeOffice());
			mailbagEnquiryFilterVO.setCurrentStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			mailbagEnquiryFilterVO.setContainerNumber(containerDetailsVO.getContainerNumber());
			mailbagEnquiryFilterVO.setDespatchSerialNumber(selectedvo.getDsn());
			mailbagEnquiryFilterVO.setYear(String.valueOf(selectedvo.getYear()));
			mailbagEnquiryFilterVO.setMailCategoryCode(selectedvo.getMailCategoryCode());
			mailbagEnquiryFilterVO.setMailSubclass(selectedvo.getMailSubclass());
			mailbagEnquiryFilterVO.setScanPort(logonAttributes.getAirportCode());
			mailbagEnquiryFilterVO.setFromExportList(SCREEN_FILTER); 
			Page<MailbagVO> mailbagVOPage = null;

			try {

				mailbagVOPage = mailTrackingDefaultsDelegate.findMailbags(mailbagEnquiryFilterVO,1);
	             if(mailbagVOPage !=  null){
				for(MailbagVO mailbagVO : mailbagVOPage){
					String  maibagid = mailbagVO.getMailbagId();
					Double weight = Double.parseDouble(maibagid.substring(25,29));
					mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,weight/10));//added by A-7371
					if(mailbagVO.getScannedDate() != null) {
						LocalDate sd = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
						String scanDate = mailbagVO.getScannedDate().toDisplayDateOnlyFormat();
						String scanTime = mailbagVO.getScannedDate().toDisplayTimeOnlyFormat();
						String scanDT = new StringBuilder(scanDate).append(" ").append(scanTime).toString();
						mailbagVO.setScannedDate(sd.setDateAndTime(scanDT));
					}
					selMailbagVOs.add(mailbagVO);
				}	    	
	           }
				log.log(Log.FINE, "Mailbags :::", mailbagVOPage);
			}catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.SEVERE, "BusinessDelegateException",businessDelegateException.getMessage());
			}	
		}
	       transferMailSession.setMailbagVOs(selMailbagVOs);
	       mailExportListForm.setTransferContainerFlag(SCREEN_STATUS);
		invocationContext.target = TARGET_SUCCESS;
        log.exiting("TransferMailCommand","execute");
		
	}

  }

