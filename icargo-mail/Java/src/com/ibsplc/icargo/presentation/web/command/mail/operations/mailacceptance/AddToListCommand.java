/* AddToListCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchConsignmentForm;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 *
 */
public class AddToListCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILOPERATIONS");

	/**
	 * TARGET
	 */
	private static final String TARGET = "addtolist_success";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.searchconsignment";
    private static final String SCREEN_ID_AXP = "mailtracking.defaults.mailacceptance";
    private static final int  WEIGHT_DIVISION_FACTOR = 10;
    private static final double  MINIMUM_VOLUME = 0.01D;
    private static final double  NO_VOLUME = 0.00D;
	private static final String COMMA = ",";
	private static final String BLANK = "";
	private static final String ADD_TO_LIST = "ADD";
	private static final String REMOVE_FROM_LIST = "REMOVE";
	private static final String REFRESH_LIST = "REFRESH";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("AddToListCommand","execute");
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		SearchConsignmentForm carditEnquiryForm = (SearchConsignmentForm)invocationContext.screenModel;
		SearchConsignmentSession carditEnquirySession = getScreenSession(MODULE_NAME,SCREEN_ID);
		MailAcceptanceSession mailAcceptanceSession = getScreenSession(MODULE_NAME,SCREEN_ID_AXP);
		ContainerDetailsVO containerDetailsVO = mailAcceptanceSession.getContainerDetailsVO();	
		

	    double vol = 0.0D;
	    Collection<MailbagVO> mailbagVOsInConsignment=new ArrayList<MailbagVO>();
	    ArrayList<MailbagVO> newMailbagVOsInList=new ArrayList<MailbagVO>();
	    ArrayList<MailbagVO> mailbagVOsInContainer=new ArrayList<MailbagVO>();
		ArrayList<DespatchDetailsVO> despatchVOsInContainer = new ArrayList<DespatchDetailsVO>();
		
		String[] selectedrows= carditEnquiryForm.getSelect().split(COMMA);
		log.log(Log.FINE, "selectedrows--->>", carditEnquiryForm.getSelect());
		if(carditEnquirySession.getMailbagVOsCollection()!= null){
			mailbagVOsInConsignment = (Collection<MailbagVO>)carditEnquirySession.getMailbagVOsCollection();
		}
		if(carditEnquirySession.getMailBagVOsForListing ()!= null &&
				carditEnquirySession.getMailBagVOsForListing().size() >0 ) {
			newMailbagVOsInList = (ArrayList<MailbagVO>)carditEnquirySession.getMailBagVOsForListing();
		}
		if(containerDetailsVO.getMailDetails()!= null &&
				containerDetailsVO.getMailDetails().size() >0 ) {
			mailbagVOsInContainer = (ArrayList<MailbagVO>)containerDetailsVO.getMailDetails();
		}
		if(containerDetailsVO.getDesptachDetailsVOs() != null &&
				containerDetailsVO.getDesptachDetailsVOs().size() > 0) {
			despatchVOsInContainer = (ArrayList<DespatchDetailsVO>)containerDetailsVO.getDesptachDetailsVOs();
		}
		log
				.log(Log.FINE, "FromButton---->>", carditEnquiryForm.getFromButton());
		if(mailbagVOsInConsignment != null && mailbagVOsInConsignment.size()>0){
			ArrayList<MailbagVO> mailbagVOArraylist = new ArrayList<MailbagVO>(mailbagVOsInConsignment);
			ArrayList<MailbagVO> mailbagVOsToRemoveFromList = new ArrayList<MailbagVO>();
			ArrayList<MailbagVO> mailbagVOsToRemove = new ArrayList<MailbagVO>();
			ArrayList<DespatchDetailsVO> despatchVOsToRemove = new ArrayList<DespatchDetailsVO>();
			if(selectedrows != null && 
					selectedrows.length > 0 && 
					!"".equals(selectedrows[0])) {
				for(String selectedrow:selectedrows){
					if(carditEnquiryForm.getFromButton() != null && 
							carditEnquiryForm.getFromButton().trim().length() > 0) {
						MailbagVO mailbagVOInConsignment = mailbagVOArraylist.get(Integer.parseInt(selectedrow));					
						if(ADD_TO_LIST.equals(carditEnquiryForm.getFromButton())) {
							if(mailbagVOInConsignment != null && 
									!MailConstantsVO.FLAG_YES.equals(mailbagVOInConsignment.getInList()) && 
									!MailConstantsVO.FLAG_YES.equals(mailbagVOInConsignment.getAccepted())) {
								mailbagVOInConsignment.setInList(MailConstantsVO.FLAG_YES);
								if(mailbagVOInConsignment.getReceptacleSerialNumber()!=null && 
										mailbagVOInConsignment.getReceptacleSerialNumber().length()>0){
									MailbagVO newMailInList = new MailbagVO();
									try {
										BeanHelper.copyProperties(newMailInList,mailbagVOInConsignment);
									} catch (SystemException e) {
										log.log(Log.FINE, "BeanHelper.copyProperties Failed !!!");
									}
									newMailInList.setContainerNumber(containerDetailsVO.getContainerNumber());
									newMailInList.setScannedPort(logonAttributes.getAirportCode());
									newMailInList.setScannedUser(logonAttributes.getUserId().toUpperCase());
									newMailInList.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
									newMailInList.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
									newMailInList.setCarrierId(containerDetailsVO.getCarrierId());
									newMailInList.setFlightNumber(containerDetailsVO.getFlightNumber());
									newMailInList.setDamageFlag("N");
									newMailInList.setArrivedFlag("N");
									newMailInList.setDeliveredFlag("N");
									newMailInList.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
									newMailInList.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
									newMailInList.setUldNumber(containerDetailsVO.getContainerNumber());
									newMailInList.setContainerType(containerDetailsVO.getContainerType());
									newMailInList.setPou(containerDetailsVO.getPou());
									newMailInList.setScannedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
									newMailInList.setOperationalFlag("I");
									double systemWt= Double.parseDouble(weightFormatter(mailbagVOInConsignment.getWeight().getRoundedSystemValue()));
									newMailInList.setStrWeight(new Measure(UnitConstants.MAIL_WGT,systemWt));//added by A-7550
																		
									if (!BLANK.equals(carditEnquiryForm.getDensity())){	//modified by A-7371		
										if(newMailInList.getStrWeight()!=null){
										//vol =newMailInList.getStrWeight().getSystemValue()/(WEIGHT_DIVISION_FACTOR * Double.parseDouble(carditEnquiryForm.getDensity()));	
											vol =newMailInList.getStrWeight().getRoundedSystemValue()/(WEIGHT_DIVISION_FACTOR * Double.parseDouble(carditEnquiryForm.getDensity()));	
										}
										if(NO_VOLUME != vol) {
											if(MINIMUM_VOLUME > vol) {
												vol = MINIMUM_VOLUME;
											}else {
												vol = Double.parseDouble(TextFormatter.formatDouble(vol , 2));
											}
										}
									}
									
									//newMailInList.setVolume(vol);
									newMailInList.setVolume(new Measure(UnitConstants.VOLUME, vol)); //Added by A-7550
									/*
									 * Added By RENO K ABRAHAM : ANZ BUG : 37646
									 * As a part of performance Upgrade
									 * START.
									 */
									newMailInList.setDisplayLabel("Y");
									//END

									if (mailbagVOsInContainer != null && mailbagVOsInContainer.size() != 0) {
										boolean isDuplicate = false;
										for (MailbagVO alreadyMailbagVO : mailbagVOsInContainer) {
											if(alreadyMailbagVO.getMailbagId()!=null
													&& alreadyMailbagVO.getMailbagId().equals(mailbagVOInConsignment.getMailbagId())){
												isDuplicate = true;
												break;
											}
										}
										if(!isDuplicate){
											mailbagVOsInContainer.add(newMailInList);
										}
									}else{
										mailbagVOsInContainer = new ArrayList<MailbagVO>();
										mailbagVOsInContainer.add(newMailInList);
									}
								}else {
									DespatchDetailsVO despatchDetailsVO = new DespatchDetailsVO();
									despatchDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
									despatchDetailsVO.setContainerNumber(containerDetailsVO.getContainerNumber());
									despatchDetailsVO.setCarrierId(containerDetailsVO.getCarrierId());
									despatchDetailsVO.setFlightNumber(containerDetailsVO.getFlightNumber());
									despatchDetailsVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
									despatchDetailsVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
									despatchDetailsVO.setUldNumber(containerDetailsVO.getContainerNumber());
									despatchDetailsVO.setContainerType(containerDetailsVO.getContainerType());
									despatchDetailsVO.setPou(containerDetailsVO.getPou());
									despatchDetailsVO.setStatedBags(mailbagVOInConsignment.getAcceptedBags());//todo
									despatchDetailsVO.setStatedWeight(mailbagVOInConsignment.getAcceptedWeight());//todo
									despatchDetailsVO.setAcceptedBags(mailbagVOInConsignment.getAcceptedBags());//todo
									despatchDetailsVO.setAcceptedWeight(mailbagVOInConsignment.getAcceptedWeight());//todo

									//carditEnquiryForm.setDensity("");
									//added by T-1927 for the bug ICRD-25031
									if(carditEnquiryForm.getDensity() != null 
											&&  !"".equals(carditEnquiryForm.getDensity())){//added by A-7371
										//vol = despatchDetailsVO.getStatedWeight()/Double.parseDouble(carditEnquiryForm.getDensity());
										//vol = despatchDetailsVO.getStatedWeight().getSystemValue()/Double.parseDouble(carditEnquiryForm.getDensity());
										vol = despatchDetailsVO.getStatedWeight().getRoundedSystemValue()/Double.parseDouble(carditEnquiryForm.getDensity()); //Added by A-7550
									}
									if(MINIMUM_VOLUME > vol) {
										vol = MINIMUM_VOLUME;
									}else {
										vol = Double.parseDouble(TextFormatter.formatDouble(vol , 2));
									}
									//despatchDetailsVO.setStatedVolume(vol);
									despatchDetailsVO.setStatedVolume(new Measure(UnitConstants.VOLUME, vol)); //Added by A-7550
									vol = 0.0D;
									//added by T-1927 for the bug ICRD-25031
									if(carditEnquiryForm.getDensity() != null 
											&&  !"".equals(carditEnquiryForm.getDensity())){//added by A-7371
										//vol = despatchDetailsVO.getAcceptedWeight()/Double.parseDouble(carditEnquiryForm.getDensity());
										//vol = despatchDetailsVO.getAcceptedWeight().getSystemValue()/Double.parseDouble(carditEnquiryForm.getDensity());
										vol = despatchDetailsVO.getAcceptedWeight().getRoundedSystemValue()/Double.parseDouble(carditEnquiryForm.getDensity()); //Added by A-7550

									}
									if(MINIMUM_VOLUME > vol) {
										vol = MINIMUM_VOLUME;
									}else {
										vol = Double.parseDouble(TextFormatter.formatDouble(vol , 2));
									}
									//despatchDetailsVO.setAcceptedVolume(vol);
									despatchDetailsVO.setAcceptedVolume(new Measure(UnitConstants.VOLUME, vol)); //Added by A-7550
									despatchDetailsVO.setOperationalFlag("I");
									despatchDetailsVO.setOriginOfficeOfExchange(mailbagVOInConsignment.getOoe());
									despatchDetailsVO.setDestinationOfficeOfExchange(mailbagVOInConsignment.getDoe());
									despatchDetailsVO.setMailCategoryCode(mailbagVOInConsignment.getMailCategoryCode());
									despatchDetailsVO.setMailClass(mailbagVOInConsignment.getMailClass());
									despatchDetailsVO.setMailSubclass(mailbagVOInConsignment.getMailSubclass());
									despatchDetailsVO.setYear(mailbagVOInConsignment.getYear());

									/*
									 * Added By RENO K ABRAHAM
									 * As a part of performance Upgrade
									 * START.
									 */
									despatchDetailsVO.setDisplayLabel("Y");
									//END

									despatchDetailsVO.setConsignmentDate(mailbagVOInConsignment.getConsignmentDate());
									despatchDetailsVO.setConsignmentNumber(mailbagVOInConsignment.getConsignmentNumber());
									despatchDetailsVO.setPaCode(mailbagVOInConsignment.getPaCode());
									despatchDetailsVO.setDsn(mailbagVOInConsignment.getDespatchSerialNumber());
									despatchDetailsVO.setAirportCode(logonAttributes.getAirportCode());
									despatchDetailsVO.setAcceptedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false));
									despatchDetailsVO.setCarrierCode(containerDetailsVO.getCarrierCode());

									if (despatchVOsInContainer != null && despatchVOsInContainer.size() != 0) {
										boolean isDuplicate = false;
										for (DespatchDetailsVO  alreadyDespatchVO: despatchVOsInContainer) {
											String alreadypk = alreadyDespatchVO.getOriginOfficeOfExchange()
											+alreadyDespatchVO.getDestinationOfficeOfExchange()
											+alreadyDespatchVO.getMailCategoryCode()
											+alreadyDespatchVO.getMailSubclass()
											+alreadyDespatchVO.getDsn()
											+alreadyDespatchVO.getYear();
											String mainpk = mailbagVOInConsignment.getOoe()
											+mailbagVOInConsignment.getDoe()
											+mailbagVOInConsignment.getMailCategoryCode()
											+mailbagVOInConsignment.getMailSubclass()
											+mailbagVOInConsignment.getDespatchSerialNumber()
											+mailbagVOInConsignment.getYear();

											if(alreadypk!=null
													&& alreadypk.equals(mainpk)){
												isDuplicate = true;
												break;
											}
										}
										if(!isDuplicate){
											despatchVOsInContainer.add(despatchDetailsVO);
										}
									}else{
										despatchVOsInContainer = new ArrayList<DespatchDetailsVO>();
										despatchVOsInContainer.add(despatchDetailsVO);
									}								
								}
								newMailbagVOsInList.add(mailbagVOInConsignment);
							}
						}else if(REMOVE_FROM_LIST.equals(carditEnquiryForm.getFromButton())) {
							if(mailbagVOInConsignment != null && 
									MailConstantsVO.FLAG_YES.equals(mailbagVOInConsignment.getInList())) {
								mailbagVOInConsignment.setInList(MailConstantsVO.FLAG_NO);
								String outerKey = null;		
								String innerKey = null;
								
								if(mailbagVOInConsignment.getMailbagId()!=null && 
										mailbagVOInConsignment.getMailbagId().length()>0){
									outerKey = new StringBuilder()
									.append(mailbagVOInConsignment.getMailbagId())
									.toString();
									for(MailbagVO mailVOInContainer : mailbagVOsInContainer) {
										if(mailVOInContainer.getMailbagId() != null &&
												mailVOInContainer.getMailbagId().trim().length() > 0) {
											innerKey = new StringBuilder()
											.append(mailVOInContainer.getMailbagId())
											.toString();
										}
										if(outerKey.equals(innerKey)) {
											mailbagVOsToRemove.add(mailVOInContainer);
											break;
										}
									}
								}else {
									outerKey =  new StringBuilder()
									.append(mailbagVOInConsignment.getOoe())
									.append(mailbagVOInConsignment.getDoe())
									.append(mailbagVOInConsignment.getMailCategoryCode())
									.append(mailbagVOInConsignment.getMailSubclass())
									.append(mailbagVOInConsignment.getYear())
									.append(mailbagVOInConsignment.getDespatchSerialNumber())
									.toString();

									for(DespatchDetailsVO despatchVOInContainer : despatchVOsInContainer) {

										innerKey = new StringBuilder()
										.append(despatchVOInContainer.getOriginOfficeOfExchange())
										.append(despatchVOInContainer.getDestinationOfficeOfExchange())
										.append(despatchVOInContainer.getMailCategoryCode())
										.append(despatchVOInContainer.getMailSubclass())
										.append(despatchVOInContainer.getYear())
										.append(despatchVOInContainer.getDsn())
										.toString();
										if(outerKey.equals(innerKey)) {
											despatchVOsToRemove.add(despatchVOInContainer);
											break;
										}
									}
								}
								if(mailbagVOsToRemove.size() > 0) {
									mailbagVOsInContainer.removeAll(mailbagVOsToRemove);
								}
								if(despatchVOsToRemove.size() > 0) {
									despatchVOsInContainer.removeAll(despatchVOsToRemove);
								}
								if(newMailbagVOsInList != null && newMailbagVOsInList.size() > 0) {
									if(mailbagVOInConsignment.getMailbagId() != null &&
											mailbagVOInConsignment.getMailbagId().trim().length() > 0) {
										outerKey = new StringBuilder()
										.append(mailbagVOInConsignment.getMailbagId()).toString();
									}else {
										outerKey =  new StringBuilder()
										.append(mailbagVOInConsignment.getOoe())
										.append(mailbagVOInConsignment.getDoe())
										.append(mailbagVOInConsignment.getMailCategoryCode())
										.append(mailbagVOInConsignment.getMailSubclass())
										.append(mailbagVOInConsignment.getYear())
										.append(mailbagVOInConsignment.getDespatchSerialNumber())
										.toString();
									}
									for(MailbagVO mailVOInList : newMailbagVOsInList) {
										if(mailVOInList.getMailbagId() != null &&
												mailVOInList.getMailbagId().trim().length() > 0) {
											innerKey = new StringBuilder()
											.append(mailVOInList.getMailbagId()).toString();
										}else {
											innerKey =  new StringBuilder()
											.append(mailVOInList.getOoe())
											.append(mailVOInList.getDoe())
											.append(mailVOInList.getMailCategoryCode())
											.append(mailVOInList.getMailSubclass())
											.append(mailVOInList.getYear())
											.append(mailVOInList.getDespatchSerialNumber())
											.toString();
										}
										if(outerKey.equals(innerKey)) {
											mailbagVOsToRemoveFromList.add(mailVOInList);
											break;
										}
									}
									if(mailbagVOsToRemoveFromList.size() > 0) {
										newMailbagVOsInList.removeAll(mailbagVOsToRemoveFromList);
									}
								}
							}
						}
					}
				}
			}
			log
					.log(Log.FINE, "MailbagVOs >>> In List >>>",
							newMailbagVOsInList);
		}

		carditEnquirySession.setMailBagVOsForListing(newMailbagVOsInList);

		containerDetailsVO.setMailDetails(mailbagVOsInContainer);
		containerDetailsVO.setDesptachDetailsVOs(despatchVOsInContainer);
		mailAcceptanceSession.setContainerDetailsVO(containerDetailsVO);

		carditEnquiryForm.setDisableButton(MailConstantsVO.FLAG_YES);
		carditEnquiryForm.setLookupClose(REFRESH_LIST);
		invocationContext.target = TARGET;
	}
	
    /**
     * @author A-3227 RENO K ABRAHAM
     * @param weight
     * @return
     */

    private String weightFormatter(Double weight) {
    	String weightString = String.valueOf(weight);
        String weights[] = weightString.split("[.]");
        StringBuilder flatWeight = new StringBuilder(weights[0]).append(weights[1]);
        if (flatWeight.length() >= 4) {
            return flatWeight.substring(0, 4);
        }  else {
            StringBuilder zeros = new StringBuilder();
            int zerosRequired = 4 - flatWeight.length();
            for(int i = 0; i < zerosRequired; i++) {
                zeros.append("0");
            }
            return zeros.append(flatWeight).toString();
        }
    }

}
