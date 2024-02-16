/*
 * UpdateMailTagDetailsCommand.java Created on Jul 1 2016
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitConversionNewVO;
/**
 * @author A-5991
 */
public class UpdateMailTagDetailsCommand   extends BaseCommand{

	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	private static final String CLASS_NAME = "UpdateMailTagDetailsCommand";

	 private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";
	private static final String UPDATE_SUCCESS="update_success";
	private static final String UPDATE_FAILURE="update_failure";
	private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";
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

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MailbagVO currentMailVO = new MailbagVO();
		MailbagVO unModifiedMailVO = new MailbagVO();
		MailbagVO sessionVO = mailAcceptanceSession.getUnmodifiedMailDetail();
		MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
		try{
			BeanHelper.copyProperties(unModifiedMailVO,sessionVO);
		}catch(SystemException exception){
			log.log(Log.FINE, "exception-", exception.getMessage());
		}
		ContainerDetailsVO containerVO = new ContainerDetailsVO(); 
		containerVO = mailAcceptanceSession.getContainerDetailsVO();
		currentMailVO = updateMailDetail(containerVO,
				mailAcceptanceSession.getCurrentMailDetail(),mailAcceptanceForm);	
		if(currentMailVO.getStrWeight()==null){
			mailAcceptanceForm.setPopupCloseFlag("NO");
			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.invalidweight"));
			return;
		}
		//currentMailVO.setOperationalFlag("I");
		if(MailbagVO.FLAG_YES.equals(mailAcceptanceForm.getModify())){
			//Added as part of bug ICRD-274236 by A-5526 starts
			double unModifiedWgt=0.0;
			double currentmailWgt=0.0;
			if(unModifiedMailVO.getStrWeight()!=null){
				unModifiedWgt=unModifiedMailVO.getStrWeight().getDisplayValue();
			}
			if(currentMailVO.getStrWeight()!=null){
				currentmailWgt=currentMailVO.getStrWeight().getDisplayValue();
			}
			//Added as part of bug ICRD-274236 by A-5526 ends
			//Modified the below condition with currentmailWgt & currentmailWgt
			if(!(unModifiedMailVO.getOoe().equals(currentMailVO.getOoe()) &&
					unModifiedMailVO.getDoe().equals(currentMailVO.getDoe()) &&
					unModifiedMailVO.getMailCategoryCode().equals(currentMailVO.getMailCategoryCode()) &&
					unModifiedMailVO.getMailClass().equals(currentMailVO.getMailClass()) &&
					unModifiedMailVO.getMailSubclass().equals(currentMailVO.getMailSubclass()) &&
					unModifiedMailVO.getYear() == currentMailVO.getYear() &&
					unModifiedMailVO.getDespatchSerialNumber().equals(currentMailVO.getDespatchSerialNumber()) &&
					unModifiedMailVO.getReceptacleSerialNumber().equals(currentMailVO.getReceptacleSerialNumber()) &&
					unModifiedMailVO.getHighestNumberedReceptacle().equals(currentMailVO.getHighestNumberedReceptacle()) &&
					unModifiedMailVO.getRegisteredOrInsuredIndicator().equals(currentMailVO.getRegisteredOrInsuredIndicator()) &&
					unModifiedWgt==currentmailWgt 
					//added as a part of ICRD-197419 by a-7540
					//unModifiedMailVO.getMailRemarks().equals(currentMailVO.getMailRemarks())
					
			)){
				currentMailVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_INSERT);
				unModifiedMailVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_DELETE);
				currentMailVO.setNewMailId(currentMailVO.getMailbagId());
				unModifiedMailVO.setNewMailId(unModifiedMailVO.getMailbagId());
				unModifiedMailVO.setMailUpdated(true);
				currentMailVO.setMailUpdated(true);
				containerVO.getDeletedMailDetails().add(unModifiedMailVO);
				mailAcceptanceVO.setMailModifyflag(true);
				containerVO.setMailModifyflag(true);
				if(currentMailVO.getScannedDate() != null && currentMailVO.getScannedDate().equals(unModifiedMailVO.getScannedDate())){
					currentMailVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
				}
			}
			//added as a part of ICRD-197419 by a-7540
			else if(currentMailVO.getMailRemarks()!=null && !(currentMailVO.getMailRemarks().equals(unModifiedMailVO.getMailRemarks())		
			))
			{
				currentMailVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_UPDATE);
				currentMailVO.setMailRemarks(currentMailVO.getMailRemarks());
				unModifiedMailVO.setMailRemarks(unModifiedMailVO.getMailRemarks());
				unModifiedMailVO.setMailUpdated(true);
				currentMailVO.setMailUpdated(true);
				mailAcceptanceVO.setMailModifyflag(true);
				containerVO.setMailModifyflag(true);
			}
			if(currentMailVO.getScannedDate() != null && currentMailVO.getScannedDate().equals(unModifiedMailVO.getScannedDate())){
				currentMailVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
			}
		
			
		}
		mailAcceptanceSession.setCurrentMailDetail(currentMailVO);
		log.log(Log.FINE, "currentMailVO-", currentMailVO);
		invocationContext.target=UPDATE_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}
	
	/**
	 * updateMailDetail
	 * @param currentmailVO
	 * @param mailAcceptanceForm
	 * @return MailbagVO
	 */
	private MailbagVO updateMailDetail(ContainerDetailsVO conDtlsVO,
			MailbagVO currentmailVO,MailAcceptanceForm mailAcceptanceForm) {
		
		ApplicationSessionImpl applicationSession = getApplicationSession(); 
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String[] mailOpFlag = mailAcceptanceForm.getMailOpFlag();
		AreaDelegate areaDelegate = new AreaDelegate();//added by A-8353 for ICRD-274933 starts
	    Map stationParameters = null; 
	    String stationCode = logonAttributes.getStationCode();
    	String companyCode=logonAttributes.getCompanyCode();
    	try {
			stationParameters = areaDelegate.findStationParametersByCode(companyCode, stationCode, getStationParameterCodes());
		} catch (BusinessDelegateException e1) {
			
			e1.getMessage();
		}//added by A-8353 for ICRD-274933 ends
		//MailbagVO currentmailVO = new MailbagVO();
		/*
		 * For Mail Tag Tab
		 */
		String[]mailOOE = mailAcceptanceForm.getMailOOE(); 
		String[]mailDOE = mailAcceptanceForm.getMailDOE();
		String[]mailCat = mailAcceptanceForm.getMailCat();
		String[]mailSC = mailAcceptanceForm.getMailSC();
		String[]mailYr = mailAcceptanceForm.getMailYr();
		String[]mailDSN = mailAcceptanceForm.getMailDSN();
		String[]mailRSN = mailAcceptanceForm.getMailRSN();
		String[]mailHNI = mailAcceptanceForm.getMailHNI();
		String[]mailRI = mailAcceptanceForm.getMailRI();
		//String[]mailWt = mailAcceptanceForm.getMailWt();
		Measure[]mailWt=mailAcceptanceForm.getMailWtMeasure();
		String[]mailScanDate = mailAcceptanceForm.getMailScanDate();
		String[]mailScanTime = mailAcceptanceForm.getMailScanTime();
		String[]mailCarrier = mailAcceptanceForm.getMailCarrier();
	//	String[]damaged = mailAcceptanceForm.getMailDamaged();
		String[]volume = mailAcceptanceForm.getMailVolume();
		String[]bellyCartId = mailAcceptanceForm.getMailCartId();
		String[]sealNo =mailAcceptanceForm.getSealNo(); 
		String[] mailRemarks =mailAcceptanceForm.getMailRemarks(); 	
		String[] stringWgt=mailAcceptanceForm.getMailWt(); //Modified by A-8236 for ICRD-262947
		
		//String[] mailDamaged = new String[100];
		currentmailVO.setCompanyCode(logonAttributes.getCompanyCode());
		currentmailVO.setContainerNumber(conDtlsVO.getContainerNumber());
		currentmailVO.setScannedPort(logonAttributes.getAirportCode());
		currentmailVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
		currentmailVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		currentmailVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
		currentmailVO.setCarrierId(conDtlsVO.getCarrierId());
		currentmailVO.setFlightNumber(conDtlsVO.getFlightNumber());
		currentmailVO.setArrivedFlag("N");
		currentmailVO.setDeliveredFlag("N");
		currentmailVO.setFlightSequenceNumber(conDtlsVO.getFlightSequenceNumber());
		currentmailVO.setSegmentSerialNumber(conDtlsVO.getSegmentSerialNumber());
		currentmailVO.setUldNumber(conDtlsVO.getContainerNumber());
		currentmailVO.setContainerType(conDtlsVO.getContainerType());
		currentmailVO.setPou(conDtlsVO.getPou());
		currentmailVO.setOperationalFlag(mailOpFlag[0]);
		currentmailVO.setDisplayLabel("Y");
	/*	if(mailOOE != null) {
		  mailDamaged = new String[mailOOE.length];
		  if(damaged != null) {
			int flag = 0;
			int mailSize = mailOOE.length;
			for(int i=0;i<mailSize;i++) {
			   for(int j=0;j<damaged.length;j++){
			  	  if(i == Integer.parseInt(damaged[j])){
				    mailDamaged[i] = "Y";
				    flag = 1;
				  }
			   }
			   if(flag == 1){
				   flag = 0;
			   }else{
				   mailDamaged[i] = "N";
			   }
		    }
		  }else{
			int mailSize = mailOOE.length;
			for(int i=0;i<mailSize;i++) {
			   mailDamaged[i] = "N";
		    }
		  }
		}*/
		
		int index=0;
		
		
		 if(mailOOE != null) {
				if(mailOOE[index] != null && !("".equals(mailOOE[index]))) {
					currentmailVO.setOoe(mailOOE[index].toUpperCase());
				}
			}
			if(mailDOE != null) {
				if(mailDOE[index] != null && !("".equals(mailDOE[index]))) {
					currentmailVO.setDoe(mailDOE[index].toUpperCase());
				}
			}
			if(mailCat != null) {
				if(mailCat[index] != null && !("".equals(mailCat[index]))) {
					currentmailVO.setMailCategoryCode(mailCat[index]);
				}
			}
			if(mailSC != null) {
				if(mailSC[index] != null && !("".equals(mailSC[index]))) {
					currentmailVO.setMailSubclass(mailSC[index].toUpperCase());
					currentmailVO.setMailClass(currentmailVO.getMailSubclass().substring(0,1));
				}
			}
			if(mailYr != null) {
				if(mailYr[index] != null && !("".equals(mailYr[index]))) {
					currentmailVO.setYear(Integer.parseInt(mailYr[index]));
				}
			}
			if(mailDSN != null) {
				if(mailDSN[index] != null && !("".equals(mailDSN[index]))) {
					currentmailVO.setDespatchSerialNumber(mailDSN[index].toUpperCase());
				}
			}
			if(mailRSN != null) {
				if(mailRSN[index] != null && !("".equals(mailRSN[index]))) {
					currentmailVO.setReceptacleSerialNumber(mailRSN[index].toUpperCase());
				}
			}
			if(mailHNI != null) {
				if(mailHNI[index] != null && !("".equals(mailHNI[index]))) {
					currentmailVO.setHighestNumberedReceptacle(mailHNI[index]);
				}
			}
			if(mailRI != null) {
				if(mailRI[index] != null && !("".equals(mailRI[index]))) {
					currentmailVO.setRegisteredOrInsuredIndicator(mailRI[index]);
				}
			}
			if(mailWt != null) {
				if(mailWt[index] != null && !("".equals(mailWt[index]))) {
					//Measure strWt=new Measure(UnitConstants.WEIGHT,Double.parseDouble(mailWt[index])/10);
					//mailWt[index].setSystemValue(mailWt[index].getSystemValue()/10);
					currentmailVO.setStrWeight(mailWt[index]);//added by A-7371
					currentmailVO.setWeight(mailWt[index]);
				
				
				}
			}
			
			//Temporary Addition to be removed once mail component implemented
			if(currentmailVO.getOoe()!=null&&
					!currentmailVO.getOoe().isEmpty()&&
					currentmailVO.getDoe()!=null&&
					!currentmailVO.getDoe().isEmpty()&&
					currentmailVO.getMailCategoryCode()!=null&&
					!currentmailVO.getMailCategoryCode().isEmpty()&&
					currentmailVO.getMailSubclass()!=null&&
					!currentmailVO.getMailSubclass().isEmpty()&&
					currentmailVO.getDespatchSerialNumber()!=null&&
					!currentmailVO.getDespatchSerialNumber().isEmpty()&&
					currentmailVO.getReceptacleSerialNumber()!=null&&
					!currentmailVO.getReceptacleSerialNumber().isEmpty()&&
					currentmailVO.getHighestNumberedReceptacle()!=null&&
					!currentmailVO.getHighestNumberedReceptacle().isEmpty()&&
					currentmailVO.getRegisteredOrInsuredIndicator()!=null&&
					!currentmailVO.getRegisteredOrInsuredIndicator().isEmpty()&&
					currentmailVO.getStrWeight()!=null &&
					stringWgt[index] != null)
					//&& !currentmailVO.getStrWeight().isEmpty())//commented by A-7371
					{
				//String wgt=String.valueOf((int)currentmailVO.getStrWeight().getDisplayValue());
				//Added by a-8353 for ICRD-274933 starts
				
				double weight=mailAcceptanceForm.getMailWtMeasure()[index].getDisplayValue();  	 	  	
				//String mailWgt=String.valueOf(((int)(wgt)));
				UnitConversionNewVO unitConversionVO= null;    
				String wgtStr="";
				try {
					unitConversionVO=UnitFormatter.getUnitConversionForToUnit(UnitConstants.MAIL_WGT,mailAcceptanceForm.getMailWtMeasure()[index].getDisplayUnit(),"H",weight);
				} catch (UnitException e) {
					// TODO Auto-generated catch block
					e.getMessage();
				}
				
				double weighttoappend = unitConversionVO.getToValue();
				if((weighttoappend<1)||(weighttoappend>9999)){
					currentmailVO.setStrWeight(null);
					return currentmailVO;
				}
			String mailWgt=String.valueOf(((int)(weighttoappend)));	
			
			if(mailWgt.length() == 3){
				wgtStr = new StringBuilder("0").append(mailWgt).toString();  
			}
			else if(mailWgt.length() == 2){
				wgtStr = new StringBuilder("00").append(mailWgt).toString();
			}
			else if(mailWgt.length() == 1){
				wgtStr = new StringBuilder("000").append(mailWgt).toString();
			}
			else if(mailWgt.length() >4||mailWgt.length() ==0){
				wgtStr = new StringBuilder("0000").toString();//Handle case when weight is incorrect
			}
			else{
				wgtStr = mailWgt;
			}//Added by a-8353 for ICRD-274933 ends
			StringBuilder mailTagId = new StringBuilder()
            .append(currentmailVO.getOoe())
            .append(currentmailVO.getDoe())
			.append(currentmailVO.getMailCategoryCode())
			.append(currentmailVO.getMailSubclass())
			.append(currentmailVO.getYear())
			.append(currentmailVO.getDespatchSerialNumber())
			.append(currentmailVO.getReceptacleSerialNumber())
			.append(currentmailVO.getHighestNumberedReceptacle())
			.append(currentmailVO.getRegisteredOrInsuredIndicator())
			.append(wgtStr); //Modified by A-8236 for ICRD-262947
			 String  mailId=mailTagId.toString();
		    currentmailVO.setMailbagId(mailId);
			}
			String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL);//added by A-8353 for ICRD-274933
			//Temporary Addition to be removed once mail component implemented
			if(volume != null) {
				if(volume[index] != null && !("".equals(volume[index]))) {
					currentmailVO.setVolume(new Measure(UnitConstants.VOLUME, 0.0, Double.parseDouble(volume[index]),stationVolumeUnit)); //modified by A-8353
				}//Added as part of bug ICRD-144856 by A-5526 starts
				else if("".equals(volume[index])){
					currentmailVO.setVolume(new Measure(UnitConstants.VOLUME,0.0));    //Added by A-7550 
				}//Added as part of bug ICRD-144856 by A-5526 ends
			}
			if(mailScanDate != null && mailScanTime != null) {
				if((mailScanDate[index] != null && !("".equals(mailScanDate[index])))
					&& (mailScanTime[index] != null && !("".equals(mailScanTime[index])))) {
					LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
					String scanDT = new StringBuilder(mailScanDate[index]).append(" ")
					        .append(mailScanTime[index]).append(":00").toString();
					if(!"I".equals(currentmailVO.getOperationalFlag())){
					
						String scanDate = currentmailVO.getScannedDate().toDisplayFormat("dd-MMM-yyyy").toUpperCase();
						String scanTime = currentmailVO.getScannedDate().toDisplayFormat("HH:mm");
						if(!(mailScanDate[index]).equals(scanDate)){
							currentmailVO.setOperationalFlag("U");
						}
						if(!(mailScanTime[index]).equals(scanTime)){
							currentmailVO.setOperationalFlag("U");
						}
					}
					
					currentmailVO.setScannedDate(sd.setDateAndTime(scanDT,false));
				}
			}
			
			if(mailCarrier != null) {
				if(mailCarrier[index] != null && !("".equals(mailCarrier[index]))) {
					if(!"I".equals(currentmailVO.getOperationalFlag())){
						if(!(mailCarrier[index].toUpperCase()).equals(currentmailVO.getTransferFromCarrier())){
							currentmailVO.setOperationalFlag("U");
						}
					}
					currentmailVO.setTransferFromCarrier(mailCarrier[index].toUpperCase());
				}else{
					//Added for bug 84850 starts
					currentmailVO.setTransferFromCarrier(null);
//					Added for bug 84850 ends
				}
			}
			if(currentmailVO.getTransferFromCarrier() == null 
			 || "".equals(currentmailVO.getTransferFromCarrier())){
				if(mailAcceptanceForm.getCarrier() != null && !("".equals(mailAcceptanceForm.getCarrier()))) {
					if(!"I".equals(currentmailVO.getOperationalFlag())){
						if(!(mailAcceptanceForm.getCarrier().toUpperCase()).equals(currentmailVO.getTransferFromCarrier())){
							currentmailVO.setOperationalFlag("U");
						}
					}
				
				currentmailVO.setTransferFromCarrier(mailAcceptanceForm.getCarrier().toUpperCase());
				}
			}
		/*	if(mailDamaged != null) {
				if(mailDamaged[index] != null && !("".equals(mailDamaged[index]))) {
					if(!"I".equals(currentmailVO.getOperationalFlag())){
						if(!mailDamaged[index].equals(currentmailVO.getDamageFlag())){
							currentmailVO.setOperationalFlag("U");
						}
					}
					currentmailVO.setDamageFlag(mailDamaged[index]);
				}
			}*/
			
			if(bellyCartId != null) {
				if(bellyCartId[index] != null && !("".equals(bellyCartId[index]))) {
					if(!"I".equals(currentmailVO.getOperationalFlag())){
						
						if(!(bellyCartId[index]).equals(currentmailVO.getBellyCartId())){
							currentmailVO.setOperationalFlag("U");
						}
					}
					currentmailVO.setBellyCartId(bellyCartId[index]);
					//newMailbagVO.setOperationalFlag("I");
				}
			}
			if(sealNo != null) { 
				if(sealNo[index] != null && !("".equals(sealNo[index]))) {
					if(!"I".equals(currentmailVO.getOperationalFlag())){
						
						if(!(sealNo[index]).equals(currentmailVO.getSealNumber())){
							currentmailVO.setOperationalFlag("U");
						}
					}
					currentmailVO.setSealNumber(sealNo[index]);
					//newMailbagVO.setOperationalFlag("I"); 
				}else{
					currentmailVO.setSealNumber(null);
				}
			} 
			//added as a part of ICRD-197419
			if(mailRemarks != null) {
				if(mailRemarks[index] != null && !("".equals(mailRemarks[index]))) {
					currentmailVO.setMailRemarks(mailRemarks[index]);
					//currentmailVO.setOperationalFlag("U");
				}
			}
		    
		return currentmailVO;   
	}
	private Collection<String> getStationParameterCodes()
	  {
	    Collection stationParameterCodes = new ArrayList();
	    stationParameterCodes.add(STNPAR_DEFUNIT_VOL);
	    return stationParameterCodes;
  }

}
