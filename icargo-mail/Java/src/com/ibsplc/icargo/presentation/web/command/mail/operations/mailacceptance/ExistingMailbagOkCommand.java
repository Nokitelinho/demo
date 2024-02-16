/*
 * ExistingMailbagOkCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.ExistingMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ExistingMailbagOkCommand extends BaseCommand{

	 private static final String TARGET = "save_success";

	 private static final String MODULE_NAME = "mail.operations";

	 private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";


	 private Log log = LogFactory.getLogger("MAILOPERATIONS");

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		log.entering("SaveMailAcceptanceCommand","execute");

    	MailAcceptanceForm mailAcceptanceForm =
    		(MailAcceptanceForm)invocationContext.screenModel;

    	MailAcceptanceSession mailAcceptanceSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

    	MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();

    	Collection<ExistingMailbagVO> existingMailbagVOs =  new ArrayList<ExistingMailbagVO>();
    	existingMailbagVOs = mailAcceptanceSession.getExistingMailbagVO();

    	Collection<ExistingMailbagVO> existingMailbags = new ArrayList<ExistingMailbagVO>();
		Collection<ContainerDetailsVO> cntnrDtlsVOs = new ArrayList<ContainerDetailsVO>();
		Collection<MailbagVO> mailDtlsVOs = new ArrayList<MailbagVO>();
		String[] reassign = mailAcceptanceForm.getReassign();
		if(reassign!=null && reassign.length>0){
			for(int i=0; i<reassign.length;i++){
				existingMailbags.add(((ArrayList<ExistingMailbagVO>)existingMailbagVOs).get(Integer.parseInt(reassign[i])));
			}
		}

		if( existingMailbags != null && existingMailbags.size() > 0){
			for(ExistingMailbagVO existingMailbagVO:existingMailbags){
				cntnrDtlsVOs = mailAcceptanceVO.getContainerDetails();
				if(cntnrDtlsVOs != null && cntnrDtlsVOs.size() > 0){
					for(ContainerDetailsVO cntrDtlsVO :cntnrDtlsVOs){
						mailDtlsVOs = cntrDtlsVO.getMailDetails();
						Collection<DSNVO> dsnVOs = cntrDtlsVO.getDsnVOs();
						if( mailDtlsVOs != null && mailDtlsVOs.size()>0){
							for(MailbagVO mailbagVO:mailDtlsVOs){
								if (OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
									if(existingMailbagVO.getMailId().equals(mailbagVO.getMailbagId())){
										mailbagVO.setCarrierCode(existingMailbagVO.getCarrierCode());
										mailbagVO.setFlightDate(existingMailbagVO.getFlightDate());
										mailbagVO.setFlightNumber(existingMailbagVO.getFlightNumber());
										mailbagVO.setFlightSequenceNumber(existingMailbagVO.getFlightSequenceNumber());
										mailbagVO.setLegSerialNumber(existingMailbagVO.getLegSerialNumber());
										mailbagVO.setSegmentSerialNumber(existingMailbagVO.getSegmentSerialNumber());
										mailbagVO.setPol(existingMailbagVO.getPol());
										mailbagVO.setPou(existingMailbagVO.getPou());
										mailbagVO.setReassignFlag("Y");
										mailbagVO.setMailbagId(existingMailbagVO.getMailId());
										mailbagVO.setCarrierId(existingMailbagVO.getCarrierId());
										mailbagVO.setScannedPort(existingMailbagVO.getCurrentAirport());
										mailbagVO.setContainerNumber(existingMailbagVO.getContainerNumber());
										mailbagVO.setContainerType(existingMailbagVO.getContainerType());
										mailbagVO.setFinalDestination(existingMailbagVO.getFinalDestination());
										mailbagVO.setFromSegmentSerialNumber(existingMailbagVO.getSegmentSerialNumber());
										mailbagVO.setUldNumber(existingMailbagVO.getContainerNumber());
										mailbagVO.setUbrNumber(existingMailbagVO.getUbrNumber());
										mailbagVO.setBookingLastUpdateTime(existingMailbagVO.getBookingLastUpdateTime());
										mailbagVO.setBookingFlightDetailLastUpdTime(existingMailbagVO.getBookingFlightDetailLastUpdTime());

										/**
										 * Anyway through reassign, count will get correct,
										 * so from client removing the count/weight from DSNVO and ContDetailsVO
										 */
										String mailpk = mailbagVO.getOoe()+mailbagVO.getDoe()
								           +(mailbagVO.getMailSubclass())+mailbagVO.getMailCategoryCode()
								           +mailbagVO.getDespatchSerialNumber()+mailbagVO.getYear();
										if(dsnVOs != null && dsnVOs.size() > 0){
											for(DSNVO dsnVO:dsnVOs){
												String dsnpk = dsnVO.getOriginExchangeOffice()
												   +dsnVO.getDestinationExchangeOffice()
												   +dsnVO.getMailSubclass()
												   +dsnVO.getMailCategoryCode()
										           +dsnVO.getDsn()+dsnVO.getYear();
												if(dsnpk.equals(mailpk)){
													dsnVO.setBags(dsnVO.getBags() - 1);
													//dsnVO.setWeight(dsnVO.getWeight() - mailbagVO.getWeight());
													try {
														dsnVO.setWeight(Measure.subtractMeasureValues(dsnVO.getWeight(), mailbagVO.getWeight()));
													} catch (UnitException e) {
														// TODO Auto-generated catch block
														log.log(Log.SEVERE,"UnitException", e.getMessage());
													}//added by A-7371
													
													log
															.log(
																	Log.FINE,
																	"2 EXISTINGOK command",
																	dsnVO.getBags());
												}
											}
										}
										cntrDtlsVO.setTotalBags(cntrDtlsVO.getTotalBags() -1);
										//cntrDtlsVO.setTotalWeight(cntrDtlsVO.getTotalWeight() - mailbagVO.getWeight());
										try {
											cntrDtlsVO.setTotalWeight(Measure.subtractMeasureValues(cntrDtlsVO.getTotalWeight(), mailbagVO.getWeight()));
										} catch (UnitException e) {
											// TODO Auto-generated catch block
											log.log(Log.SEVERE,"UnitException", e.getMessage());
										}//added by A-7371
										cntrDtlsVO.setMailDetails(mailDtlsVOs);
										log.log(Log.FINE,
												"3 EXISTINGOK command",
												cntrDtlsVO.getTotalBags());
										cntrDtlsVO.setDsnVOs(dsnVOs);
										cntrDtlsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
									}
								}
							}
						}
				    }
					mailAcceptanceVO.setContainerDetails(cntnrDtlsVOs);
			    }
			}
		}


		/**
		 * To remove VOs which are not selected for Reassign
		 */

		if(existingMailbags !=null && existingMailbags.size()>0){
			existingMailbagVOs.removeAll(existingMailbags);
		}

		if(existingMailbagVOs!= null && existingMailbagVOs.size()>0){
			for(ExistingMailbagVO existingMailVO : existingMailbagVOs){
				cntnrDtlsVOs = mailAcceptanceVO.getContainerDetails();
				if(cntnrDtlsVOs != null && cntnrDtlsVOs.size() > 0){
					for(ContainerDetailsVO cntrDtlsVO :cntnrDtlsVOs){
						mailDtlsVOs = cntrDtlsVO.getMailDetails();
						Collection<DSNVO> dsnVOs = cntrDtlsVO.getDsnVOs();
						Collection<DSNVO> dsnVOsToRmove = new ArrayList<DSNVO>();
						if( mailDtlsVOs != null && mailDtlsVOs.size()>0){
							Collection<MailbagVO> mailbagsToRemove = new ArrayList<MailbagVO>();
							for(MailbagVO mailbagVO:mailDtlsVOs){
								if (OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
									if(existingMailVO.getMailId().equals(mailbagVO.getMailbagId())){
										mailbagsToRemove.add(mailbagVO);

										String mailpk = mailbagVO.getOoe()+mailbagVO.getDoe()
								           +(mailbagVO.getMailSubclass())+mailbagVO.getMailCategoryCode()
								           +mailbagVO.getDespatchSerialNumber()+mailbagVO.getYear();
										if(dsnVOs != null && dsnVOs.size() > 0){
											for(DSNVO dsnVO:dsnVOs){
												String dsnpk = dsnVO.getOriginExchangeOffice()
												   +dsnVO.getDestinationExchangeOffice()
												   +dsnVO.getMailSubclass()
												   +dsnVO.getMailCategoryCode()
										           +dsnVO.getDsn()+dsnVO.getYear();
												if(dsnpk.equals(mailpk)){
													dsnVO.setBags(dsnVO.getBags() - 1);
													//dsnVO.setWeight(dsnVO.getWeight() - mailbagVO.getWeight()
													try {
														dsnVO.setWeight(Measure.subtractMeasureValues(dsnVO.getWeight(), mailbagVO.getWeight()));
													} catch (UnitException e) {
														// TODO Auto-generated catch block
														log.log(Log.SEVERE,"UnitException", e.getMessage());
													}//added by A-7371
													if(dsnVO.getBags()==0){
														dsnVOsToRmove.add(dsnVO);
													}
													log
															.log(
																	Log.FINE,
																	"4 EXISTINGOK command",
																	dsnVO.getBags());
												}
											}
										}
										cntrDtlsVO.setTotalBags(cntrDtlsVO.getTotalBags() -1);
										//cntrDtlsVO.setTotalWeight(cntrDtlsVO.getTotalWeight() - mailbagVO.getWeight());
										try {
											cntrDtlsVO.setTotalWeight(Measure.subtractMeasureValues(cntrDtlsVO.getTotalWeight(), mailbagVO.getWeight()));
										} catch (UnitException e) {
											// TODO Auto-generated catch block
											log.log(Log.SEVERE,"UnitException", e.getMessage());
										}
										
										log.log(Log.FINE,
												"5 in EXISTINGOK command",
												cntrDtlsVO.getTotalBags());
										dsnVOs.removeAll(dsnVOsToRmove);
										cntrDtlsVO.setDsnVOs(dsnVOs);
										cntrDtlsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
									}
								}
							}
							mailDtlsVOs.removeAll(mailbagsToRemove);
							cntrDtlsVO.setMailDetails(mailDtlsVOs);
						}
				    }
					mailAcceptanceVO.setContainerDetails(cntnrDtlsVOs);
			    }
			}
		}

	mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);
	log.log(Log.FINE, "mailAcceptanceVO in EXISTINGOK command",
			mailAcceptanceVO);
	mailAcceptanceVO.setDuplicateMailOverride("Y");
	mailAcceptanceForm.setPopupCloseFlag("Y");
	mailAcceptanceSession.setExistingMailbagVO(null);
	mailAcceptanceForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	invocationContext.target = TARGET;
	}

}
