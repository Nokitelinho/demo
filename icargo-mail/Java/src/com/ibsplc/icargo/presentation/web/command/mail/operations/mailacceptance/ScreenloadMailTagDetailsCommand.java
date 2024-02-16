/*
 * ScreenloadMailTagDetailsCommand.java Created on Jul 1 2016
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991 
 */
public class ScreenloadMailTagDetailsCommand  extends BaseCommand{

	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	private static final String CLASS_NAME = "ScreenloadMailTagDetailsCommand";

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";
	private static final String SCREENLOAD_SUCCESS="screenload_success";
	//private static final String SCREENLOAD_FAILURE="screenload_failure";

	/**
	 * Execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		MailAcceptanceForm mailAcceptanceForm =
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	ArrayList<MailbagVO> mailbagsvos=null;
		ApplicationSessionImpl applicationSession = getApplicationSession();
//		Added for Unit Component
		UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		mailAcceptanceSession.setWeightRoundingVO(unitRoundingVO);
		mailAcceptanceSession.setVolumeRoundingVO(unitRoundingVO);
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		setUnitComponent(logonAttributes.getStationCode(),mailAcceptanceSession);
	//	LogonAttributes logonAttributes = applicationSession.getLogonVO();

		mailAcceptanceForm.setCurrentPageNum("1"); 
		int currentIndex = Integer.parseInt(mailAcceptanceForm.getCurrentPageNum())-1;
		//int displayPageNum = Integer.parseInt(mailAcceptanceForm.getDisplayPage());
		String selectedRow = mailAcceptanceForm.getSelectedRow();
		log.log(Log.INFO, "selected ", selectedRow);
		String[] primaryKey = null;
		if(selectedRow != null && selectedRow.trim().length() > 0) {
			primaryKey = selectedRow.split(",");
		}
		Page<MailbagVO> mainMailVOs = null ;
		ArrayList<MailbagVO> selectedMailVOs = new ArrayList<MailbagVO>();
		MailbagVO currentMailVO = new MailbagVO();

		ContainerDetailsVO containerVO = new ContainerDetailsVO();
		containerVO = mailAcceptanceSession.getContainerDetailsVO();
		if(containerVO != null) {
			if(containerVO.getMailDetails()!= null &&
					containerVO.getMailDetails().size() > 0) {
				//Page<RateLineVO> oldratespage=new Page<RateLineVO>(ratelinevos,1,0,ss,0,0,false);
				mainMailVOs =  new Page<MailbagVO>(
						new ArrayList<MailbagVO>(containerVO.getMailDetails()), 1, 0, containerVO.getMailDetails().size(), 0, 0,false);

			}else {
				mainMailVOs = new Page<MailbagVO>(
						new ArrayList<MailbagVO>(), 0, 0, 0, 0, 0,false);
			}
		}
		int seqIdentifier=0;
		log.log(Log.FINE, "mainMailVOs----", mainMailVOs);
		if(mainMailVOs != null && mainMailVOs.size() > 0){
			for(MailbagVO mainMailVO : mainMailVOs) {
				mainMailVO.setSeqIdentifier(seqIdentifier++);
			}
			
			if(primaryKey != null && primaryKey.length > 0) {
				for(int key=0;key<primaryKey.length;key++) {
					int position = Integer.parseInt(primaryKey[key]);
					log.log(Log.FINE, "position----", position);
					mailbagsvos=new ArrayList<MailbagVO>(mainMailVOs);
					MailbagVO selectedmailDetailVO =
						mailbagsvos.get(position-1);
					if(selectedmailDetailVO != null) {
						selectedMailVOs.add(selectedmailDetailVO);
					}
				}
				if(selectedMailVOs != null ) {
					if(selectedMailVOs.size() > 0){
						currentMailVO = ((ArrayList<MailbagVO>)selectedMailVOs).get(currentIndex);
					}
				}
			}else{
				log.log(Log.INFO,"inside ELSE PART IN SCREENLOAD");
				mailbagsvos=new ArrayList<MailbagVO>(mainMailVOs);
				MailbagVO bagVO=mailbagsvos.get(mailbagsvos.size()-1);
				if(bagVO!=null){
					log.log(Log.INFO,"inside ELSE PART IN SCREENLOAD BAG not null");
					currentMailVO.setOoe(bagVO.getOoe());
					currentMailVO.setDoe(bagVO.getDoe());
					currentMailVO.setMailCategoryCode(bagVO.getMailCategoryCode());
					currentMailVO.setMailSubclass(bagVO.getMailSubclass());
					currentMailVO.setYear(bagVO.getYear());
					currentMailVO.setDespatchSerialNumber(bagVO.getDespatchSerialNumber());
					currentMailVO.setHighestNumberedReceptacle(bagVO.getHighestNumberedReceptacle());
					currentMailVO.setRegisteredOrInsuredIndicator(bagVO.getRegisteredOrInsuredIndicator());
					currentMailVO.setCarrierCode(bagVO.getCarrierCode());
				//	currentMailVO.setScannedDate(bagVO.getScannedDate());
					
					//added as a part of ICRD-197419
					currentMailVO.setMailRemarks(bagVO.getMailRemarks()); 
					
					LocalDate sdate=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
					if(mailAcceptanceForm.getHiddenScanDate()!=null && mailAcceptanceForm.getHiddenScanDate().trim().length()>0) {
						sdate.setDate(mailAcceptanceForm.getHiddenScanDate());
					}
					currentMailVO.setScannedDate(sdate);
					currentMailVO.setSealNumber(bagVO.getSealNumber());   
				}   
				currentMailVO.setOperationalFlag("I"); 
			} 
		}else{
			log.log(Log.INFO,"inside ELSE PART adding for first time");
			LocalDate sdate=new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			if(mailAcceptanceForm.getHiddenScanDate()!=null && mailAcceptanceForm.getHiddenScanDate().trim().length()>0) {
				sdate.setDate(mailAcceptanceForm.getHiddenScanDate());
			}
			currentMailVO.setScannedDate(sdate);
			String currentDate = sdate.toDisplayDateOnlyFormat();
			int lastDigitOfYear = Integer.parseInt(currentDate.substring(10,11));
			currentMailVO.setYear(lastDigitOfYear);		
			currentMailVO.setOperationalFlag("I");
		} 

		mailAcceptanceForm.setTotalRecords(String.valueOf(selectedMailVOs.size()));
		log.log(Log.FINE, "currentCN51VO-", currentMailVO);
		mailAcceptanceSession.setCurrentMailDetail(currentMailVO);
		mailAcceptanceSession.setSelectedMailDetailsVOs(selectedMailVOs);
		/*
		 * Getting OneTime values
		 */
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		if(oneTimes!=null){
			List<String> sortedOnetimes ;
			Collection<OneTimeVO> catVOs = oneTimes.get("mailtracking.defaults.mailcategory");
			Collection<OneTimeVO> hniVOs = oneTimes.get("mailtracking.defaults.highestnumbermail");
			Collection<OneTimeVO> rsnVOs = oneTimes.get("mailtracking.defaults.registeredorinsuredcode");
			if(hniVOs!=null && !hniVOs.isEmpty()){
				sortedOnetimes= new ArrayList<String>();
				for(OneTimeVO hniVo: hniVOs){
					sortedOnetimes.add(hniVo.getFieldValue());
				}
				Collections.sort(sortedOnetimes);      
			
			
			int i=0;
			for(OneTimeVO hniVo: hniVOs){
				hniVo.setFieldValue(sortedOnetimes.get(i++));
			}
			}
			if(rsnVOs!=null && !rsnVOs.isEmpty()){
				sortedOnetimes= new ArrayList<String>();
				for(OneTimeVO riVo: rsnVOs){
					sortedOnetimes.add(riVo.getFieldValue());
				}
				Collections.sort(sortedOnetimes);    
			
			
			int i=0;
			for(OneTimeVO riVo: rsnVOs){
				riVo.setFieldValue(sortedOnetimes.get(i++));
			}
			}
			mailAcceptanceSession.setOneTimeCat(catVOs);
			mailAcceptanceSession.setOneTimeRSN(rsnVOs);
			mailAcceptanceSession.setOneTimeHNI(hniVOs);
			mailAcceptanceSession.setUnmodifiedMailDetail(currentMailVO);

		}

		//mailAcceptanceForm.setPopUpCloseFlag("N");
		invocationContext.target=SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
	/**
	 * A-3251
	 * @param stationCode
	 * @param mailAcceptanceSession
	 * @return
	 */
	private void setUnitComponent(String stationCode,
			MailAcceptanceSession mailAcceptanceSession){
		UnitRoundingVO unitRoundingVO = null;
		try{
			
			unitRoundingVO=UnitFormatter.getUnitRoundingForUnitCode(UnitConstants.MAIL_WGT, UnitConstants.WEIGHT_MAIL_UNIT);

			mailAcceptanceSession.setWeightRoundingVO(unitRoundingVO);
			unitRoundingVO = UnitFormatter.getStationDefaultUnit(
					stationCode, UnitConstants.VOLUME);
			mailAcceptanceSession.setVolumeRoundingVO(unitRoundingVO);

		   }catch(UnitException unitException) {
				unitException.getErrorCode();
		   }

	}
	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add("mailtracking.defaults.registeredorinsuredcode");
			fieldValues.add("mailtracking.defaults.mailcategory");
			fieldValues.add("mailtracking.defaults.highestnumbermail");

			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}
}
