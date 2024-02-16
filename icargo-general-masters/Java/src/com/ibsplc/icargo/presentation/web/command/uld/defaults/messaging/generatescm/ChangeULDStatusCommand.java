/*
 * ChangeULDStatusCommand.java Created on AUG 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.generatescm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.GenerateSCMSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.GenerateSCMForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class ChangeULDStatusCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("Generate SCM");

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.generatescm";

	private static final String CHANGE_SUCCESS = "change_success";
	
	private static final String FACILITY_TYPE = "uld.defaults.facilitytypes";

    /**
     * @param invocationContext
     * @return 
     * @throws CommandInvocationException
    */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("AddExtraULDCommand", "execute");

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		GenerateSCMSession generateSCMSession = (GenerateSCMSession) getScreenSession(
				MODULE, SCREENID);

		GenerateSCMForm generateSCMForm = (GenerateSCMForm) invocationContext.screenModel;
		//added by nisha for bugfix on 30May
		ArrayList<ULDVO> extraULDs = new ArrayList<ULDVO>();
		String[]extrauld = generateSCMForm.getExtrauld();
		String[] facilityType=generateSCMForm.getFacilityType();
		String[] scmStatusFlag=generateSCMForm.getScmStatusFlags();
		String[] operationFlags=generateSCMForm.getOperationFlag();
		//String[] newoperationFlags=generateSCMForm.getTempOperationFlag();
		String[] locations = generateSCMForm.getLocations();		
		log.log(Log.INFO, "opr length", extrauld.length);
		log.log(Log.INFO, "opr length", operationFlags.length);
		for(int i=0;i<extrauld.length;i++){
			if(!"NOOP".equals(operationFlags[i])){
				log.log(Log.INFO, "opr uldnum", extrauld, i);
				log.log(Log.INFO, "opr oprflg", operationFlags, i);
				ULDVO uldVO = new ULDVO();
				uldVO.setFacilityType(facilityType[i]);
				uldVO.setUldNumber(extrauld[i]);
				uldVO.setScmStatusFlag(scmStatusFlag[i]);
				uldVO.setOperationalFlag(operationFlags[i]);
				uldVO.setLocation(locations[i]);
				extraULDs.add(uldVO);			
			}
		}	
		
		String[]newuld = generateSCMForm.getNewuld();
		String[] newFacilityType = generateSCMForm.getNewFacilityType();	
		String[] newlocation = generateSCMForm.getNewLocations();		
		String[] tempOprFlag = generateSCMForm.getTempOperationFlag();	
		String[] oprFlag = generateSCMForm.getNewOperationFlag();
		boolean isValidFacilityCode = false;
		Collection<ULDAirportLocationVO> uldAirportLocationVOs = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log.log(Log.INFO, "opr length", extrauld.length);
		log.log(Log.INFO, "opr length", tempOprFlag.length);
		for(int i = 0; i < tempOprFlag.length; i++){
			if(newuld[i] != null && !("").equals(newuld[i])
					&& !"NOOP".equals(oprFlag[i])
					&& ULDVO.OPERATION_FLAG_INSERT.equals(oprFlag[i])&& newFacilityType[i]!=null && newFacilityType[i].trim().length()>0 && newlocation[i]!=null && newlocation[i].trim().length()>0 ){
				try {					
					uldAirportLocationVOs = new ULDDefaultsDelegate().listULDAirportLocation(
							logonAttributes.getCompanyCode(), generateSCMForm.getScmAirport()
									.toUpperCase(), newFacilityType[i].toUpperCase());
				} catch (BusinessDelegateException exception) {
					handleDelegateException(exception);
				}
				for (ULDAirportLocationVO uldAirportLocationVO : uldAirportLocationVOs) {
					if (uldAirportLocationVO.getAirportCode() != null
							&& uldAirportLocationVO.getAirportCode().equals(
									generateSCMForm.getScmAirport()
									.toUpperCase())
							&& uldAirportLocationVO.getFacilityType() != null
							&& uldAirportLocationVO.getFacilityType().equals(
									newFacilityType[i].toUpperCase())) {

						if (uldAirportLocationVO.getFacilityCode() != null
								&& uldAirportLocationVO.getFacilityCode().equals(
										newlocation[i].toUpperCase())) {
							isValidFacilityCode = true;
							break;
						}
					}
				}
				if(isValidFacilityCode){
					//extraULDs.add(uldVO);
				}else{

					ErrorVO error = new ErrorVO(
							"uld.defaults.relocate.msg.err.invalidfacilitycode");

					errors.add(error);
				
				}
			}
		}		
		
//		Page<ULDVO> ULDpage = new Page<ULDVO>(extraULDs,0,0,0,0,0,true);		
		Page<ULDVO>  ULDpage =generateSCMSession.getSystemStock();
		if(extraULDs !=null && extraULDs.size()>0){
			extraULDs.addAll(ULDpage);
		}
		//addNewULDs(ULDpage, extrauld, facilityType, scmStatusFlag, operationFlags, locations);
		errors= addNewULDs(generateSCMForm,logonAttributes,generateSCMSession);
		generateSCMForm.setChangeStatusFlag(true);
		generateSCMSession.setSystemStock(ULDpage);

		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = CHANGE_SUCCESS;
			log.log(Log.INFO, "returning from command class",
					generateSCMSession.getSystemStock());
			return;
		}		
		
		Collection<ULDVO> uldVOs = generateSCMSession.getSystemStock();
		log.log(Log.INFO, "system stock after updation", uldVOs);
		Collection<ULDVO> uLDVOs = new ArrayList<ULDVO>();
		if(generateSCMSession.getChangedSystemStock()!=null && generateSCMSession.getChangedSystemStock().size()>0)
		{
			uLDVOs = generateSCMSession.getChangedSystemStock();
		}

		String messageSendingStatus="";  
		String[] selectedUlds = generateSCMForm.getSelectedSysStock();
		HashMap<String, ULDVO> updatedULDs=generateSCMSession.getUpdatedULDStocks();
		if(updatedULDs==null) {
			updatedULDs=new HashMap<String, ULDVO>();
		}
		if (selectedUlds != null && selectedUlds.length > 0) {
			int index = 0;
			log.log(Log.FINE, "Selected Length---------------->",
					selectedUlds.length);
			for (ULDVO uldVO : uldVOs) {
				for (int i = 0; i < selectedUlds.length; i++) {
					if(i==0){
						messageSendingStatus=uldVO.getScmMessageSendingFlag();
					}
					if (index == Integer.parseInt(selectedUlds[i])) {
						if(!"I".equals(uldVO.getOperationalFlag())){
							if ("Y".equals(generateSCMForm.getMissingFlag())) {
								uldVO.setScmStatusFlag(ULDVO.SCM_MISSING_STOCK);
								uLDVOs.add(uldVO);
								if(updatedULDs.containsKey(uldVO.getUldNumber())) {
									updatedULDs.get(uldVO.getUldNumber()).setScmStatusFlag(ULDVO.SCM_MISSING_STOCK);
								} else {
									updatedULDs.put(uldVO.getUldNumber(), uldVO);
								}
								
							} else if ("N".equals(generateSCMForm.getMissingFlag())) {
								uldVO.setScmStatusFlag(ULDVO.SCM_SYSTEM_STOCK);
								uLDVOs.remove(uldVO);
								if(updatedULDs.containsKey(uldVO.getUldNumber())) {
									updatedULDs.get(uldVO.getUldNumber()).setScmStatusFlag(ULDVO.SCM_SYSTEM_STOCK);
								} else {
									updatedULDs.put(uldVO.getUldNumber(), uldVO);
								}
							}
							//added by A-6344 for ICRD-55460 start
							else if ("S".equals(generateSCMForm.getMissingFlag())) {
								uldVO.setScmStatusFlag(ULDVO.SCM_SIGHTED_STOCK);
								uLDVOs.add(uldVO);
								if(updatedULDs.containsKey(uldVO.getUldNumber())) {
									updatedULDs.get(uldVO.getUldNumber()).setScmStatusFlag(ULDVO.SCM_SIGHTED_STOCK);
								} else {
									updatedULDs.put(uldVO.getUldNumber(), uldVO);
								}
								
							}
							//added by A-6344 for ICRD-55460 end
}
					}
				}
				index++;
			}
		}
		
		 generateSCMSession.setUpdatedULDStocks(updatedULDs);
//		Added by Sreekumar S - AirNZ CR 434
		
		//ends
	
		generateSCMForm.setScmMessageSendingFlag(messageSendingStatus);
		
		generateSCMSession.setChangedSystemStock(uLDVOs);
		invocationContext.target = CHANGE_SUCCESS;
	}
	
	public Collection<ErrorVO> addNewULDs(GenerateSCMForm generateSCMForm, LogonAttributes logonAttributes,
								GenerateSCMSession generateSCMSession){
		ArrayList<ULDVO> newULDs = new ArrayList<ULDVO>();
		//Added by A-4810 for bugs in GENERATE SCM CREEN
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String[] newuldNumbers = generateSCMForm.getNewuld();	
		String[] newFacilityTypes = generateSCMForm.getNewFacilityType();
		String[] newLocation = generateSCMForm.getNewLocations();
		String[] oprFlag = generateSCMForm.getNewOperationFlag();
		int newindex = 0;		
		if(newuldNumbers != null ){		
			boolean isuldnumbernull = false;
		 for (String uldNumber : newuldNumbers){	
			//modified as part of bugfix icrd-10453 by A-4810
			if(!"NOOP".equals(oprFlag[newindex]))
			{
				if(uldNumber!=null && uldNumber.trim().length()>0 ){
					ULDVO uldVO = new ULDVO();
					uldVO.setCompanyCode(logonAttributes.getCompanyCode());
					uldVO.setScmStatusFlag(ULDVO.SCM_FOUND_STOCK);
					uldVO.setUldNumber(newuldNumbers[newindex]);
					uldVO.setFacilityType(newFacilityTypes[newindex]);
					uldVO.setLocation(newLocation[newindex]);
					uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_INSERT);
					newULDs.add(uldVO);	
				}
				 else{
					ULDVO uldVO = new ULDVO();
					uldVO.setCompanyCode(logonAttributes.getCompanyCode());
					uldVO.setScmStatusFlag(ULDVO.SCM_FOUND_STOCK);
					uldVO.setUldNumber(newuldNumbers[newindex]);
					uldVO.setFacilityType(newFacilityTypes[newindex]);
					uldVO.setLocation(newLocation[newindex]);
					uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_INSERT);
					newULDs.add(uldVO);	
					isuldnumbernull = true;
					//ErrorVO error = new ErrorVO(
					//"uld.defaults.relocate.msg.err.uldnumbercannotbenull");
			       //  errors.add(error);
				}
			}
			newindex++;
			}
		if(isuldnumbernull)
		{
			ErrorVO error = new ErrorVO(
					"uld.defaults.relocate.msg.err.uldnumbercannotbenull");
			         errors.add(error);	
			         isuldnumbernull = false;
		}
		}
		/*
		if(newuldNumbers != null && newuldNumbers.length>0){			
		for (String uldNumber : newuldNumbers){		
			if(uldNumber!=null ){	
			if(!uldNumber.equals(""))
			{
					ULDVO uldVO = new ULDVO();
					uldVO.setCompanyCode(logonAttributes.getCompanyCode());
					uldVO.setScmStatusFlag(ULDVO.SCM_FOUND_STOCK);
					uldVO.setUldNumber(newuldNumbers[newindex]);
					uldVO.setFacilityType(newFacilityTypes[newindex]);
					uldVO.setLocation(newLocation[newindex]);
					uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_INSERT);
					if(!"NOOP".equals(oprFlag[newindex])){
						newULDs.add(uldVO);
							
					}	
				}
			}
			else{
				if(!oprFlag[newindex].equals("NOOP")){
					ErrorVO error = new ErrorVO(
					"uld.defaults.relocate.msg.err.uldnumbercannotbenull");
			         errors.add(error);
			        break;	
			}}
			newindex++;
			}
		
		}
		*/
		//
		/*
		int j=0;
		if((newFacilityTypes[j]!=null && newFacilityTypes[j].trim().length()>0 )||(newLocation[j]!=null && newLocation[j].trim().length()>0) ){
			if(!oprFlag[j].equals("NOOP")&& ("".equals(newuldNumbers[j])|| newuldNumbers[j]==null))
			{
				ULDVO uldVO = new ULDVO();
				uldVO.setCompanyCode(logonAttributes.getCompanyCode());
				uldVO.setScmStatusFlag(ULDVO.SCM_FOUND_STOCK);
				uldVO.setUldNumber(newuldNumbers[j]);
				uldVO.setFacilityType(newFacilityTypes[j]);
				uldVO.setLocation(newLocation[j]);
				uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_INSERT);
				newULDs.add(uldVO);
				ErrorVO error = new ErrorVO(
				"uld.defaults.relocate.msg.err.uldnumbercannotbenull");
		         errors.add(error);
		         j++;
			}
		}
		*/
		
		
		//
		//Added by A-4810
		/*
		else{
			if(newFacilityTypes[0]!=null||newLocation[0]!=null)
			{		
			ErrorVO error = new ErrorVO(
			"uld.defaults.relocate.msg.err.uldnumbercannotbenull");
	         errors.add(error);
			}
	        //break;	
		}
		*/
		//
		

		Map<String, Collection<OneTimeVO>> oneTimeCollection = fetchScreenLoadDetails(logonAttributes.getCompanyCode());
		ArrayList<OneTimeVO> facilityTypes = (ArrayList<OneTimeVO>) oneTimeCollection.get(FACILITY_TYPE);
		if(newULDs != null && newULDs.size() > 0){
			ArrayList<OneTimeVO> newFacTypes = (ArrayList<OneTimeVO>) oneTimeCollection.get(FACILITY_TYPE);			 
			for(ULDVO uldVO:newULDs){
				if (newFacTypes != null && newFacTypes.size() > 0) {					
					for (OneTimeVO oneTimeVO : newFacTypes) {
						if(uldVO.getFacilityType() != null && uldVO.getFacilityType().equals(oneTimeVO.getFieldValue())){
							uldVO.setFacilityType(oneTimeVO.getFieldValue());
						}
					}
				}		
			}
		}
		log.log(Log.FINE,
				"Size of uld before setting into session --------->>>", newULDs.size());
		generateSCMSession.setNewSystemStock(newULDs);	
		return errors;
	}
	
	
	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	 private Map<String, Collection<OneTimeVO>> fetchScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(FACILITY_TYPE);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);

		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			errors = handleDelegateException(exception);
		}
		return hashMap;
	}
	
	
	/**
	 * For ICRD - 3749 by a-4778
	 * @param uldvos
	 * @param extrauld
	 * @param facilityType
	 * @param scmStatusFlag
	 * @param operationFlags
	 * @param locations
	 */
	/*private void addNewULDs(Page<ULDVO> uldvos,String[]extrauld,
			String[] facilityType,String[] scmStatusFlag,String[] operationFlags,String[] locations){
		if(operationFlags != null && operationFlags.length >0){
			Page<ULDVO> newULDs = new Page<ULDVO>(new ArrayList<ULDVO>(), 0, 0, 0, 0, 0, false);
			ULDVO uldVO = null;
			for(int i=0;i<operationFlags.length;i++){
				if(ULDVO.OPERATION_FLAG_INSERT.equals(operationFlags[i])){
					uldVO = new ULDVO();
					uldVO.setFacilityType(facilityType[i]);
					uldVO.setUldNumber(extrauld[i]);
					uldVO.setScmStatusFlag(scmStatusFlag[i]);
					uldVO.setOperationalFlag(operationFlags[i]);
					uldVO.setLocation(locations[i]);
					newULDs.add(uldVO);
				}

			}
			if(newULDs.size() >0 && uldvos!=null &&  uldvos.size() >0){
				uldvos.addAll(newULDs);


			}

		}


	}*/

}





