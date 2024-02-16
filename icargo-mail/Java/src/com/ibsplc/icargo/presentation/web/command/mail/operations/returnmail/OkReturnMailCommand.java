/*
 * OkReturnMailCommand.java Created on July 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.returnmail;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.EmptyULDsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailBagEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReturnMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReturnMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1861
 *
 */
public class OkReturnMailCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILTRACKING");

	/**
	 * TARGET
	 */
	private static final String TARGET_SUCCESS = "ok_success";
	private static final String TARGET_FAILURE = "ok_failure";

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.returnmail";
	private static final String MAILBAGENQUIRY_SCREEN_ID = "mailtracking.defaults.mailBagEnquiry";
	private static final String EMPTYULD_SCREEN_ID = "mailtracking.defaults.emptyulds";

	private static final String CONST_MAILBAGENQUIRY = "MAILBAG_ENQUIRY";
	private static final String CONST_OUTBOUND = "O";
	private static final String SCREEN_NUMERICAL_ID_MAILBAGENQUIRY = "MTK009";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering("OkReturnMailCommand","execute");

		ReturnMailForm returnMailForm =
			(ReturnMailForm)invocationContext.screenModel;
		ReturnMailSession returnMailSession =
			getScreenSession(MODULE_NAME,SCREEN_ID);
		EmptyULDsSession emptyUldsSession =
			getScreenSession(MODULE_NAME,EMPTYULD_SCREEN_ID);

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;

		if (returnMailForm.getDamageCode() == null) {
			errors = new ArrayList<ErrorVO>();
			ErrorVO errorVO = new ErrorVO(
			"mailtracking.defaults.returnmail.msg.err.nodamages");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		}
		//added By Karthick V to show the errrors occured during the Validation of Form
		//Before the Control actually goes to DELEGATE
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		log.log(Log.INFO,"FORM VALIDATED SUCCESFULLY");

		Collection<DamagedMailbagVO> damagedMailbagVOs = returnMailSession.getDamagedMailbagVOs();
		log.log(Log.INFO, "THE DAMAGE MAIL BAG VOS ", damagedMailbagVOs);
		if (damagedMailbagVOs == null) {
			damagedMailbagVOs = new ArrayList<DamagedMailbagVO>();
		}
		else {
			damagedMailbagVOs = updateDamagedMailbagVOs(
					damagedMailbagVOs,
					returnMailForm,
					logonAttributes,
					returnMailSession);
		}
		log.log(Log.FINE, "Updated damagedMailbagVOs------------> ",
				damagedMailbagVOs);
		String containers = returnMailForm.getSelectedContainers();
		String[] selectedConts = containers.split(",");
		ArrayList<String> conts = new ArrayList<String>();
		for (String str : selectedConts) {
			conts.add(str);
		}
		log.log(Log.FINE, "Selected indexes------------> ", conts);
		if (CONST_MAILBAGENQUIRY.equals(returnMailForm.getFromScreen())) {
			MailBagEnquirySession mailBagEnquirySession =
				getScreenSession(MODULE_NAME,MAILBAGENQUIRY_SCREEN_ID);

			Collection<MailbagVO> mailbagVOs = mailBagEnquirySession.getMailbagVOs();
			Collection<MailbagVO> selectedMailbagVOs = new ArrayList<MailbagVO>();
			int index = 0;
			for (MailbagVO selectedvo : mailbagVOs) {
				if (conts.contains(String.valueOf(index))) {
					selectedMailbagVOs.add(selectedvo);
				}
				index++;
			}

			for (MailbagVO vo : selectedMailbagVOs) {
				vo.setDamagedMailbags(damagedMailbagVOs);
				vo.setScannedPort(logonAttributes.getAirportCode());
				vo.setScannedUser(logonAttributes.getUserId());
				vo.setScannedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
				if("Y".equals(returnMailForm.getFlagSBReturn())){
					vo.setFlagPAULDResidit("Y");
				}else{
					vo.setFlagPAULDResidit("N");
				}
				vo.setMailSource(SCREEN_NUMERICAL_ID_MAILBAGENQUIRY);//Added for ICRD-156218
				/*
				 * The below vo.setFromReturnPopUp(true); is added by Manish
				 * as part of ICRD-196881 for checking the value in ULDForSegment
				 * in the method updateMailbagInULDForSegment(MailbagVO mailbagVO)
				 */
				vo.setFromReturnPopUp(true);
				/*
				 * The below vo.setFromReturnPopUp(true); is added by Manish
				 * as part of ICRD-196881 for checking the value in ULDForSegment 
				 * in the method updateMailbagInULDForSegment(MailbagVO mailbagVO)
				 */
				vo.setFromReturnPopUp(true);
			}

			MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate
			= new MailTrackingDefaultsDelegate();
			// setting operation type for each DamagedMailbagVO
			for (MailbagVO vo : selectedMailbagVOs) {
				vo.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
				Collection<DamagedMailbagVO> damagedVOs = null;
				//Added as part of bug ICRD-129281 starts
				String paCode="";
				try {
					paCode = mailTrackingDefaultsDelegate.findPAForOfficeOfExchange(vo.getCompanyCode(),  vo.getOoe());
				} catch (BusinessDelegateException e) {     
					log.log(Log.INFO, "System Exception caught ");  
				}
				//Added as part of bug ICRD-129281 ends
				if (CONST_OUTBOUND.equals(vo.getOperationalStatus())) {
					damagedVOs = vo.getDamagedMailbags();
					for(DamagedMailbagVO damagedvo : damagedMailbagVOs) {
						//Added as part of bug ICRD-129281 starts
						if(paCode!=null && !"".equals(paCode)) {
							
							damagedvo.setPaCode(paCode);
						}
						//Added as part of bug ICRD-129281 ends
						damagedvo.setOperationType("O");
					}
				}
				else {
					damagedVOs = vo.getDamagedMailbags();
					for(DamagedMailbagVO damagedvo : damagedMailbagVOs) {
						//Added as part of bug ICRD-129281 starts
						if(paCode!=null && !"".equals(paCode)) {
							damagedvo.setPaCode(paCode);
						}
						//Added as part of bug ICRD-129281 ends
						damagedvo.setOperationType("I");
					}
				}
			}
			log.log(Log.FINE, "\n\nMailbagVOs for return------------> ",
					selectedMailbagVOs);
			
			Collection<ContainerDetailsVO> containerDetailsVOs = null;
			try {
				if (returnMailForm.isReturnMail()) {
					log.log(Log.FINE,"GOING FOR RETURN--------> ");

					containerDetailsVOs =mailTrackingDefaultsDelegate.returnMailbags(selectedMailbagVOs);
					returnMailForm.setSuccessFlag("Y");

				}
				else {
					log.log(Log.FINE,"GOING FOR DAMAGE--------> ");
					mailTrackingDefaultsDelegate.saveDamageDetailsForMailbag(selectedMailbagVOs);
				}

			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET_FAILURE;
				return;
			}
			else {
				log.log(Log.FINE, "containerDetailsVOs ------------------>>",
						containerDetailsVOs);
				returnMailSession.setDamagedMailbagVOs(null);
				if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
					emptyUldsSession.setContainerDetailsVOs(containerDetailsVOs);
					returnMailForm.setSelectedContainers("SHOWPOPUP");
				}
				else {
					returnMailForm.setSelectedContainers("CLOSE");
				}
			}
		}
		invocationContext.target = TARGET_SUCCESS;

		log.exiting("OkReturnMailCommand","execute");

	}
	/**
	 * Method to update the DamagedMailbagVOs in session
	 * @param damagedMailbagVOs
	 * @param returnMailForm
	 * @param logonAttributes
	 * @param returnMailSession
	 * @return
	 */
	private Collection<DamagedMailbagVO> updateDamagedMailbagVOs(
			Collection<DamagedMailbagVO> damagedMailbagVOs,
			ReturnMailForm returnMailForm,
			LogonAttributes logonAttributes,
			ReturnMailSession returnMailSession) {

		log.entering("OkReturnMailCommand","updateDamagedMailbagVOs");

		String[] damageCodes = returnMailForm.getDamageCode();
		String[] damageRemarks = returnMailForm.getDamageRemarks();

		if (damageCodes != null) {
			int index = 0;
			for(DamagedMailbagVO damagedMailbagVO:damagedMailbagVOs) {
				log.log(Log.INFO, "THE DMGMAL VO IN ITR ", damagedMailbagVO);
				damagedMailbagVO.setDamageCode(damageCodes[index]);
				damagedMailbagVO.setRemarks(damageRemarks[index]);
				damagedMailbagVO.setDamageDescription(
						handleDamageCodeDescription(returnMailSession,damageCodes[index]));
				damagedMailbagVO.setAirportCode(logonAttributes.getAirportCode());
				damagedMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
				LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
				damagedMailbagVO.setDamageDate(currentdate);
				damagedMailbagVO.setUserCode(logonAttributes.getUserId().toUpperCase());
				if (returnMailForm.isReturnMail()) {
					damagedMailbagVO.setReturnedFlag("Y");
				}
				else {
					damagedMailbagVO.setReturnedFlag("N");
				}
				
				
				index++;
			}
		}

		log.log(Log.FINE, "Updated DamagedMailbagVOs------------> ",
				damagedMailbagVOs);
		log.exiting("OkReturnMailCommand","updateDamagedMailbagVOs");

		return damagedMailbagVOs;
	}
	/**
	 * Method is used to get the onetime description
	 * @param returnMailSession
	 * @param damagecode
	 * @return
	 */
	private String handleDamageCodeDescription(
			ReturnMailSession returnMailSession,
			String damagecode) {

		String damageDesc = "";

		Collection<OneTimeVO> damageCodes = returnMailSession.getOneTimeDamageCodes();
		for (OneTimeVO vo : damageCodes) {
			if (vo.getFieldValue().equals(damagecode)) {
				damageDesc = vo.getFieldDescription();
				break;
			}
		}

		return damageDesc;
	}

}
