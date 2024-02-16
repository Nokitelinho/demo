/*
 * ReturnMailbagCommand.java Created on Jun 08, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry;

import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAttachmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DamagedMailbag;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Revision History Revision Date Author Description 0.1 Jun 07, 2018 A-2257
 * First draft
 */

public class ReturnMailbagCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("ReturnMailbagCommand");
	private static final String DAMAGE_CODE = "mailtracking.defaults.return.reasoncode";  
   private static final String CONST_RETURN_CODE = "RTN";
   private static final String CONST_ACCEPT_FLG = "ACP";
   private static final String CONST_OFFLOAD_FLG = "OFL";
   private static final String CONST_ARRIVED_FLG = "ARR";
   private static final String CONST_DELIVERED_FLG = "DLV";
   private static final String CONST_INBOUND = "I";
   private static final String CONST_OUTBOUND = "O";
   private static final String CONST_BULK = "B";
   private  static final String SUCCESS_MESSAGE="mail.operations.err.returnsuccess";
	   

	/**
	 * 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		log.entering("ReturnMailbagCommand", "execute");

		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		ScreenSession  screenSession = getScreenSession();   
		HashMap fileUploadMap = (HashMap)screenSession.getFromScreenSessionMap("fileUploadMap");
		
		MailbagEnquiryModel mailbagEnquiryModel = (MailbagEnquiryModel) actionContext.getScreenModel();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		ResponseVO responseVO = new ResponseVO();
		
		Collection<Mailbag> selectedMailbags = null;

		ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<MailbagVO> mailbagVOs = null;

		if (mailbagEnquiryModel != null && mailbagEnquiryModel.getSelectedMailbags() != null) {

			log.log(Log.FINE, "mailbagEnquiryModel.getSelectedMailbags() not null");

			selectedMailbags = mailbagEnquiryModel.getSelectedMailbags();
			log.log(Log.FINE, "selectedMailbags --------->>", selectedMailbags);

			boolean isReturnedMailbag = false;
			boolean isCapNotAcceptedMailbag = false;
			
			if (mailbagEnquiryModel.getDamagedMailbags() == null
					|| mailbagEnquiryModel.getDamagedMailbags().size() == 0) {
				errors = new ArrayList<ErrorVO>();
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.returnmail.msg.err.nodamages");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				actionContext.addError(errorVO);
				return;
			}else{
				for(DamagedMailbag damagedMailbag:mailbagEnquiryModel.getDamagedMailbags()){
				if(damagedMailbag.getDamageCode()==null){
					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.returnmail.msg.err.nodamages");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					actionContext.addError(errorVO);
					return;
				}
				}
			}

			for (Mailbag selected : selectedMailbags) {
				if (CONST_RETURN_CODE.equals(selected.getLatestStatus())) {
					isReturnedMailbag = true;
					break;
				}
				if (MAIL_STATUS_CAP_NOT_ACCEPTED.equalsIgnoreCase(selected.getLatestStatus())) {
					isCapNotAcceptedMailbag = true;
					break;
				}

				if ("Y".equalsIgnoreCase(mailbagEnquiryModel.getReturnMailFlag())) {

					Collection<MailbagHistoryVO> mailhistroryVos = null;
					String acceptedPort = null;
					if (CONST_INBOUND.equals(selected.getOperationalStatus())) {
						if (!logonAttributes.getAirportCode().equals(selected.getScannedPort())) {
							if (!(CONST_ACCEPT_FLG.equals(selected.getLatestStatus())
									|| CONST_OFFLOAD_FLG.equals(selected.getLatestStatus()))) {

								actionContext.addError(new ErrorVO(
										"mailtracking.defaults.mailbagenquiry.msg.err.invalidCondtnForInboundReturn"));
								return;
							}
						}
					} else if (CONST_OUTBOUND.equals(selected.getOperationalStatus())) {
						if (!logonAttributes.getAirportCode().equals(selected.getScannedPort())) {
							if (!(CONST_ARRIVED_FLG.equals(selected.getLatestStatus()))) {

								actionContext.addError(new ErrorVO(
										"mailtracking.defaults.mailbagenquiry.msg.err.invalidCondtnForOutboundReturn"));
								return;
							}
						}
					}
					if (CONST_DELIVERED_FLG.equals(selected.getLatestStatus())) {

						actionContext.addError(
								new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.deliveredbagscannotreturn"));
						return;
					}
					
					try {

						mailhistroryVos = new MailTrackingDefaultsDelegate()
								.findMailbagHistories(logonAttributes.getCompanyCode(), selected.getMailbagId(),0);
					} catch (BusinessDelegateException businessDelegateException) {
						errors = (ArrayList) handleDelegateException(businessDelegateException);
						log.log(Log.INFO, "ERROR--SERVER------findMailbagHistories---->>");
					}
					if (mailhistroryVos != null && mailhistroryVos.size() > 0) {
						for (MailbagHistoryVO mailbagHistoryVO : mailhistroryVos) {
							if (CONST_ACCEPT_FLG.equals(mailbagHistoryVO.getMailStatus())) {
								acceptedPort = mailbagHistoryVO.getScannedPort();
								break;
							}
						}
					}
					//Removed by A-8176 as part of ICRD-336183
					/*if (acceptedPort == null
							|| (acceptedPort != null && !(acceptedPort.equals(selected.getScannedPort())))) {
						log.log(Log.INFO, "Mail Bags acceptance port is different from the return operation's airport");
						actionContext.addError(new ErrorVO("mailtracking.defaults.manifestedcannotreturnatimport"));
						return;
					}*/

				}
			}
			if (isCapNotAcceptedMailbag) {
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.err.capturedbutnotaccepted");
				actionContext.addError(errorVO);
				return;
			}
			if (isReturnedMailbag) {
				ErrorVO errorVO = null;

				errorVO = new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.returnedMailbagsCannotReturn");

				actionContext.addError(errorVO);
				return;
			}

			Collection<DamagedMailbagVO> damagedMailbagVOs = null;
			Collection<MailAttachmentVO> mailDamageAttachmentVOs=null;
			if (mailbagEnquiryModel.getDamagedMailbags() == null
					|| mailbagEnquiryModel.getDamagedMailbags().size() == 0) {
				errors = new ArrayList<ErrorVO>();
				ErrorVO errorVO = new ErrorVO("mailtracking.defaults.returnmail.msg.err.nodamages");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				actionContext.addError(errorVO);
				return;
			}

			Collection<DamagedMailbag> damagedMailbags = mailbagEnquiryModel.getDamagedMailbags();
			log.log(Log.INFO, "damagedMailbags", damagedMailbags);

			damagedMailbagVOs = MailOperationsModelConverter.constructDamagedMailbagVOs(damagedMailbags,
					logonAttributes);
			mailDamageAttachmentVOs = MailOperationsModelConverter.constructDamagedAttachmentVOs(damagedMailbags,fileUploadMap,
					logonAttributes);
			damagedMailbagVOs = updateDamagedMailbagVOs(damagedMailbagVOs, logonAttributes,
					mailbagEnquiryModel.getOneTimeValues().get(DAMAGE_CODE), mailbagEnquiryModel.getReturnMailFlag());

			log.log(Log.FINE, "Updated damagedMailbagVOs------------> ", damagedMailbagVOs);

			mailbagVOs = MailOperationsModelConverter.constructMailbagVOs(selectedMailbags, logonAttributes);

			for (MailbagVO vo : mailbagVOs) {
				vo.setDamagedMailbags(damagedMailbagVOs);
				vo.setAttachments(mailDamageAttachmentVOs);
				vo.setScannedPort(logonAttributes.getAirportCode());
				vo.setScannedUser(logonAttributes.getUserId());
				vo.setScannedDate(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
				if ("Y".equals(mailbagEnquiryModel.getFlagSBReturn())) {
					vo.setFlagPAULDResidit("Y");
				} else {
					vo.setFlagPAULDResidit("N");
				}
				if(mailbagEnquiryModel.getNumericalScreenId()!=null) {
				   vo.setMailSource(mailbagEnquiryModel.getNumericalScreenId());
				} else {
					vo.setMailSource("Mail ENQ");
				}
				vo.setFromReturnPopUp(true);

				vo.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());

				// Added as part of bug ICRD-129281 starts
				String paCode = "";
				try {
					paCode = mailTrackingDefaultsDelegate.findPAForOfficeOfExchange(vo.getCompanyCode(), vo.getOoe());
				} catch (BusinessDelegateException e) {
					log.log(Log.INFO, "System Exception caught ");
				}
				vo.getDamagedMailbags();
				for (DamagedMailbagVO damagedvo : damagedMailbagVOs) {
					if (paCode != null && !"".equals(paCode)) {
						damagedvo.setPaCode(paCode);
					}
					if (CONST_OUTBOUND.equals(vo.getOperationalStatus())) {

						damagedvo.setOperationType("O");

					} else {
						damagedvo.setOperationType("I");
					}
				}
				if ("Y".equalsIgnoreCase(mailbagEnquiryModel.getReturnMailFlag())&&CONST_BULK.equalsIgnoreCase(vo.getContainerType())
					 &&vo.getContainerNumber()!=null&&vo.getContainerNumber().trim().length()>0) {
					ContainerAssignmentVO containerAssignmentVO = null;
					containerAssignmentVO=mailTrackingDefaultsDelegate.findLatestContainerAssignment(vo.getContainerNumber());
					if(containerAssignmentVO!=null&&containerAssignmentVO.getDestination()!=null){
						vo.setFinalDestination(containerAssignmentVO.getDestination());
						if (vo.getCarrierId()==0){	   
							vo.setCarrierId(containerAssignmentVO.getCarrierId());
						}
					}

				}
			}

			try {
				if ("Y".equalsIgnoreCase(mailbagEnquiryModel.getReturnMailFlag())) {
					log.log(Log.FINE, "GOING FOR RETURN--------> ");

					mailTrackingDefaultsDelegate.returnMailbags(mailbagVOs);
					mailbagEnquiryModel.setSuccessFlag("Y");

				} else {
					log.log(Log.FINE, "GOING FOR DAMAGE--------> ");
					mailTrackingDefaultsDelegate.saveDamageDetailsForMailbag(mailbagVOs);
				}

			} catch (BusinessDelegateException businessDelegateException) {
				errors = (ArrayList) handleDelegateException(businessDelegateException);
			}

			if (errors != null && errors.size() > 0) {
				ErrorVO curError = errors.iterator().next();
				if(curError.getErrorCode().equals("mailtracking.defaults.err.flightclosed")){
		    		 curError.setErrorCode("mailtracking.defaults.err.flightclosedforreturn");
		    	 }
				actionContext.addAllError(errors);
				return;
			}
			//As part of ICRD-335146
			else {
				ErrorVO error = null;
				if ("Y".equalsIgnoreCase(mailbagEnquiryModel.getReturnMailFlag())) {
					error = new ErrorVO("Mailbags Returned successfully");      
				} else {
					error = new ErrorVO("Damage Captured successfully");   
				}
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				actionContext.addError(error);    
		responseVO.setStatus("success");
			}
		}
		actionContext.setResponseVO(responseVO);
		log.exiting("ReturnMailbagCommand", "execute");
	}

	private Collection<DamagedMailbagVO> updateDamagedMailbagVOs(Collection<DamagedMailbagVO> damagedMailbagVOs,
			LogonAttributes logonAttributes, Collection<OneTime> onetime, String returnMailFlag) {

		log.entering("OkReturnMailCommand", "updateDamagedMailbagVOs");

		if (damagedMailbagVOs != null) {

			for (DamagedMailbagVO damagedMailbagVO : damagedMailbagVOs) {
				log.log(Log.INFO, "THE DMGMAL VO IN ITR ", damagedMailbagVO);

				damagedMailbagVO
						.setDamageDescription(handleDamageCodeDescription(onetime, damagedMailbagVO.getDamageCode()));
				damagedMailbagVO.setAirportCode(logonAttributes.getAirportCode());
				damagedMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
				LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
				damagedMailbagVO.setDamageDate(currentdate);
				damagedMailbagVO.setUserCode(logonAttributes.getUserId().toUpperCase());
				if ("Y".equalsIgnoreCase(returnMailFlag)) {
					damagedMailbagVO.setReturnedFlag("Y");
				}
				else {
					damagedMailbagVO.setReturnedFlag("N");
				}

			}
		}

		log.log(Log.FINE, "Updated DamagedMailbagVOs------------> ", damagedMailbagVOs);
		log.exiting("OkReturnMailCommand", "updateDamagedMailbagVOs");

		return damagedMailbagVOs;
	}

	private String handleDamageCodeDescription(Collection<OneTime> onetimes, String damagecode) {

		String damageDesc = "";

		for (OneTime vo : onetimes) {
			if (vo.getFieldValue().equals(damagecode)) {
				damageDesc = vo.getFieldDescription();
				break;
			}
		}

		return damageDesc;
	}

}
