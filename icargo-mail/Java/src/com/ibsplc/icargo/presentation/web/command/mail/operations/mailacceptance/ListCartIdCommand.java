/*
 * ListCartIdCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;

/**
 * @author A-5991
 *
 */
public class ListCartIdCommand extends BaseCommand {
	
	
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	/**
	 * TARGET
	 */
	private static final String TARGET = "success";
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";
	private static final int  WEIGHT_DIVISION_FACTOR = 10;
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("ListAcceptMailCommand","execute");
		
		MailAcceptanceForm mailAcceptanceForm = 
			(MailAcceptanceForm)invocationContext.screenModel;
		MailAcceptanceSession mailAcceptanceSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<MailbagVO> newMailbagVOs = null;
		ContainerDetailsVO newcontrDtlVO = mailAcceptanceSession.getContainerDetailsVO();
		newcontrDtlVO.setBellyCartId(mailAcceptanceForm.getBellyCarditId());
		Collection<MailbagVO> mailbagsInSession = newcontrDtlVO.getMailDetails();
		Collection<MailbagVO> finalMailBagVOs = new ArrayList<MailbagVO>();
		ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
		consignmentFilterVO.setBellyCartId(mailAcceptanceForm.getBellyCarditId());
		try {					
			newMailbagVOs =new MailTrackingDefaultsDelegate().findCartIds(consignmentFilterVO);
		} catch (BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		if(mailbagsInSession !=null && !mailbagsInSession.isEmpty()){
			checkExistingMailBag(mailbagsInSession,newMailbagVOs,newcontrDtlVO,logonAttributes,mailAcceptanceForm.getDensity());
			finalMailBagVOs.addAll(mailbagsInSession);
		}else if (newMailbagVOs !=null && !newMailbagVOs.isEmpty()){
			setMailBagVODetails(newMailbagVOs,newcontrDtlVO,logonAttributes,mailAcceptanceForm.getDensity());
			finalMailBagVOs.addAll(newMailbagVOs);
			newcontrDtlVO.setMailDetails(finalMailBagVOs);
			mailAcceptanceSession.setContainerDetailsVO(newcontrDtlVO);	
			invocationContext.target = TARGET;
		}else{
			mailAcceptanceSession.setContainerDetailsVO(newcontrDtlVO);	
			errors.add(new ErrorVO("mailtracking.defaults.mailacceptance.lookup.noresults"));
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET;
			return;
		}
		mailAcceptanceForm.setScreenStatusFlag("detail");
		log.exiting("ListAcceptMailCommand","execute");	
			
	}	
	
	
	private void checkExistingMailBag(Collection<MailbagVO> mailbagsInSession,Collection<MailbagVO> mailbagVOsFromCardit,
			ContainerDetailsVO newcontrDtlVO,LogonAttributes logonAttributes,String density){
		int sessionmailbagsize = mailbagsInSession.size();
		for(int i =0;i<sessionmailbagsize;i++) {
			MailbagVO mailbagOuterVO = (MailbagVO)((ArrayList<MailbagVO>)mailbagsInSession).get(i);
			int carditmailbagsize = mailbagVOsFromCardit.size();
			for(int j =0;j<carditmailbagsize;j++){
				MailbagVO mailbagInnerVO = (MailbagVO)((ArrayList<MailbagVO>)mailbagVOsFromCardit).get(j);
				log.log(Log.FINE, " mailbag is der...", mailbagOuterVO.getMailbagId());
				log.log(Log.FINE, " mailbag is der...", mailbagInnerVO.getMailbagId());
				if(mailbagOuterVO.getMailbagId().equals(mailbagInnerVO.getMailbagId())){
					mailbagVOsFromCardit.remove(mailbagInnerVO);
					j--;
					carditmailbagsize = mailbagVOsFromCardit.size();
				}
			}
		}
		if(mailbagVOsFromCardit !=null && ! (mailbagVOsFromCardit.isEmpty())){
			setMailBagVODetails(mailbagVOsFromCardit,newcontrDtlVO,logonAttributes,density);
			mailbagsInSession.addAll(mailbagVOsFromCardit);
		}
		
	}
	
	private void setMailBagVODetails(Collection<MailbagVO> mailbagVOsFromCardit,ContainerDetailsVO newcontrDtlVO,
			LogonAttributes logonAttributes,String density){
		if(mailbagVOsFromCardit != null && mailbagVOsFromCardit.size() >0){
			for(MailbagVO mailbagVO : mailbagVOsFromCardit){
				double vol = 0.0;
				mailbagVO.setBellyCartId(newcontrDtlVO.getBellyCartId());
				mailbagVO.setContainerNumber(newcontrDtlVO.getContainerNumber());
      			mailbagVO.setScannedPort(logonAttributes.getAirportCode());
      			mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
      			mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
      			mailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
      			mailbagVO.setCarrierId(newcontrDtlVO.getCarrierId());
      			mailbagVO.setFlightNumber(newcontrDtlVO.getFlightNumber());
      			mailbagVO.setDamageFlag("N");
      			mailbagVO.setArrivedFlag("N");
      			mailbagVO.setDeliveredFlag("N");
      			mailbagVO.setFlightSequenceNumber(newcontrDtlVO.getFlightSequenceNumber());
      			mailbagVO.setSegmentSerialNumber(newcontrDtlVO.getSegmentSerialNumber());
      			mailbagVO.setUldNumber(newcontrDtlVO.getContainerNumber());
      			mailbagVO.setContainerType(newcontrDtlVO.getContainerType());
      			mailbagVO.setPou(newcontrDtlVO.getPou());
      			mailbagVO.setOperationalFlag("I");
      			mailbagVO.setDisplayLabel("Y");
      			mailbagVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
      			if(mailbagVO.getStrWeight()!=null){//modified by A-7371
      			vol =mailbagVO.getStrWeight().getRoundedSystemValue()/(WEIGHT_DIVISION_FACTOR * Double.parseDouble(density)); //Added by A-7550
      			}
				vol = Double.parseDouble(TextFormatter.formatDouble(vol , 2));
      			//mailbagVO.setVolume(vol);
				mailbagVO.setVolume(new Measure(UnitConstants.VOLUME, vol));    //Added by A-7550
			}
		}
	}
	
	
	
	
}
