package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.generatescm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.msgbroker.message.ListMessageSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.GenerateSCMSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.GenerateSCMForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SaveAsDraftCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("SaveAsDraftCommand");

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.generatescm";

	private static final String DRAFT = "D";

	private static final String SUCCESS = "SaveAsDraftSuccess";
	private static final String SAVE_DRAFT_FAILED = "SaveAsDraftFailed";
	//private static final String MISSINGULD_ERRORCODE = "ERR1";

	//private static final String FOUNDULD_ERRORCODE = "ERR2";

	private static final String FACILITY_TYPE = "uld.defaults.facilitytypes";
	private static final String MSGMODULE_NAME = "msgbroker.message";

	private static final String MSGSCREEN_ID = "msgbroker.message.listmessages";
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("SaveAsDraftCommand", "execute");
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String compCode = logonAttributes.getCompanyCode();
		ULDDefaultsDelegate uldDelegate = new ULDDefaultsDelegate();
		GenerateSCMSession generateSCMSession = (GenerateSCMSession) getScreenSession(
				MODULE, SCREENID);
		GenerateSCMForm generateSCMForm = (GenerateSCMForm) invocationContext.screenModel;
		Collection<ULDSCMReconcileVO> scmReconcileVOs = new ArrayList<ULDSCMReconcileVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ULDSCMReconcileVO scmReconcileVO = new ULDSCMReconcileVO();
		scmReconcileVO.setCompanyCode(compCode);
		scmReconcileVO.setAirportCode(generateSCMForm.getScmAirport()
				.toUpperCase());

		if (generateSCMSession != null
				&& generateSCMSession.getMessageFilterVO() != null) {
			log.log(Log.INFO, "%%%%%%%%%%%---------generateSCMSession%",
					generateSCMSession.getMessageFilterVO());
			scmReconcileVO.setAirlineIdentifier(generateSCMSession
					.getMessageFilterVO().getFlightCarrierIdentifier());
		}
		if (generateSCMSession.getMessageFilterVO().getFacility() != null
				&& generateSCMSession.getMessageFilterVO().getFacility().trim()
						.length() > 0) {
			scmReconcileVO.setFacility(generateSCMSession.getMessageFilterVO()
					.getFacility());
		}
		if (generateSCMSession.getMessageFilterVO().getLocation() != null
				&& generateSCMSession.getMessageFilterVO().getLocation().trim()
						.length() > 0) {
			scmReconcileVO.setLocation(generateSCMSession.getMessageFilterVO()
					.getLocation());
			populateScmReconcileVO(scmReconcileVO, generateSCMForm);
		}
		String sequenceno=null;
	if(generateSCMSession.getSystemStock()!=null  ){
			sequenceno=generateSCMSession.getSystemStock().get(0).getReconciliationSeqNum();
		}
		scmReconcileVO.setMessageSendFlag(DRAFT);
		Collection<ULDSCMReconcileDetailsVO> scmDetailsVOs = new ArrayList<ULDSCMReconcileDetailsVO>();
		generateSCMSession.setNewSystemStock(null);
		ArrayList<ULDVO> newULDs = new ArrayList<ULDVO>();
		String[] newuldNumbers = generateSCMForm.getNewuld();
		String[] newFacilityTypes = generateSCMForm.getNewFacilityType();
		String[] newLocation = generateSCMForm.getNewLocations();
		String[] oprFlag = generateSCMForm.getNewOperationFlag();
		String[] stockUlds = generateSCMForm.getExtrauld();
		String[] newuldNumbersOperationFlags = generateSCMForm
				.getNewOperationFlag();
		int newindex = 0;
		if (newuldNumbers != null && newuldNumbers.length > 0) {
			boolean isuldnumbernull = false;
			for (String uldNumber : newuldNumbers) {
				if (newindex < newuldNumbers.length - 1) {
					if (!"NOOP".equals(oprFlag[newindex])) {
						if (uldNumber != null && uldNumber.trim().length() > 0) {
							ULDVO uldVO = new ULDVO();
							uldVO.setCompanyCode(logonAttributes
									.getCompanyCode());
							uldVO.setScmStatusFlag("N");
							if (newuldNumbers[newindex] != null
									&& newuldNumbers[newindex].trim().length() > 0) {
								uldVO.setUldNumber(newuldNumbers[newindex]
										.toUpperCase());
							}
							uldVO.setFacilityType(newFacilityTypes[newindex]);
							uldVO.setLocation(newLocation[newindex]);
							//uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_INSERT);  //ICRD-107871							 
							if(!ULDVO.OPERATION_FLAG_DELETE.equals(oprFlag[newindex])){
							uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_INSERT);
							}else{
								uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_DELETE);
							}
							newULDs.add(uldVO);
						} else {
							ULDVO uldVO = new ULDVO();
							uldVO.setCompanyCode(logonAttributes
									.getCompanyCode());
							uldVO.setScmStatusFlag(ULDVO.SCM_FOUND_STOCK);
							if (newuldNumbers[newindex] != null
									&& newuldNumbers[newindex].trim().length() > 0) {
								uldVO.setUldNumber(newuldNumbers[newindex]
										.toUpperCase());
							}
							uldVO.setFacilityType(newFacilityTypes[newindex]);
							uldVO.setLocation(newLocation[newindex]);
							//uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_INSERT);
							if(!ULDVO.OPERATION_FLAG_DELETE.equals(oprFlag[newindex])){
							uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_INSERT);
							}else{
								uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_DELETE);
							}
							newULDs.add(uldVO);
							isuldnumbernull = true;
						}
					}else if(uldNumber!=null && uldNumber.length()>0 && sequenceno!=null){
						ULDVO uldVO = new ULDVO();
						uldVO.setCompanyCode(logonAttributes
								.getCompanyCode());
						uldVO.setScmStatusFlag("N");
						if (newuldNumbers[newindex] != null
								&& newuldNumbers[newindex].trim().length() > 0) {
							uldVO.setUldNumber(newuldNumbers[newindex]
									.toUpperCase());
						}
						uldVO.setFacilityType(newFacilityTypes[newindex]);
						uldVO.setLocation(newLocation[newindex]);
						uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_DELETE);
						newULDs.add(uldVO);
					}
				}else if(uldNumber!=null && uldNumber.length()>0 && sequenceno!=null){
					ULDVO uldVO = new ULDVO();
					uldVO.setCompanyCode(logonAttributes
							.getCompanyCode());
					uldVO.setScmStatusFlag("N");
					if (newuldNumbers[newindex] != null
							&& newuldNumbers[newindex].trim().length() > 0) {
						uldVO.setUldNumber(newuldNumbers[newindex]
								.toUpperCase());
					}
					uldVO.setFacilityType(newFacilityTypes[newindex]);
					uldVO.setLocation(newLocation[newindex]);
					uldVO.setOperationalFlag(ULDVO.OPERATION_FLAG_DELETE);
					newULDs.add(uldVO);
				}
				newindex++;
			}
			if (newuldNumbers != null && newuldNumbers.length > 0) {
				errors = checkDuplicateInFoundULDs(newuldNumbers,
						newuldNumbersOperationFlags);
			}
			if (errors != null && errors.size() > 0) {
				if (errors != null && errors.size() > 0) {
					generateSCMSession.setNewSystemStock(newULDs);
					invocationContext.addAllError(errors);
					invocationContext.target = SAVE_DRAFT_FAILED;
					return;
				}
			}

			if (isuldnumbernull) {
				ErrorVO error = new ErrorVO(
						"uld.defaults.relocate.msg.err.uldnumbercannotbenull");
				errors.add(error);
				isuldnumbernull = false;
			}
		}
		generateSCMSession.setNewSystemStock(newULDs);
		log.log(Log.INFO, "newULDs already present @@@@@", newULDs);
		HashMap<String, ULDVO> updatedULDs = generateSCMSession
				.getUpdatedULDStocks();
		if (updatedULDs == null)
			{
			updatedULDs = new HashMap<String, ULDVO>();
			}
		for (ULDVO uldVO : newULDs) {
			if (uldVO.getUldNumber() != null
					&& uldVO.getUldNumber().trim().length() > 0) {
				if (!updatedULDs.containsKey(uldVO.getUldNumber())) {
					updatedULDs.put(uldVO.getUldNumber(), uldVO);
				}
			}
		}

		generateSCMSession.setUpdatedULDStocks(updatedULDs);
		log.log(Log.INFO, "updated ulds  present @@@@@",
				generateSCMSession.getUpdatedULDStocks());
		// Ashraf starts for taking the ulds listed.
		Collection<String> uldNos = new ArrayList<String>();
		if (generateSCMSession.getUpdatedULDStocks() != null
				&& generateSCMSession.getUpdatedULDStocks().size() > 0) {

			String[] uldNumbers = generateSCMForm.getExtrauld();
			int index = 0;
			if(generateSCMSession.getSystemStock() !=null){
			for (ULDVO uldVO : generateSCMSession.getSystemStock()) {
				uldVO.setUldNumber(uldNumbers[index].toUpperCase());
				index++;
			}
			}
			
			for (ULDVO uldVO : generateSCMSession.getUpdatedULDStocks()
					.values()) {
				ULDSCMReconcileDetailsVO scmDetailsVO = new ULDSCMReconcileDetailsVO();
				if (ULDVO.SCM_FOUND_STOCK.equals(uldVO.getScmStatusFlag())
						|| "N".equals(uldVO.getScmStatusFlag())) {
					scmDetailsVO.setErrorCode(null);
					scmDetailsVO.setUldNumber(uldVO.getUldNumber());
					//scmDetailsVO.setOperationFlag("I");  //ICRD-107871
					if(!ULDVO.OPERATION_FLAG_DELETE.equals(uldVO.getOperationalFlag())){
						scmDetailsVO.setOperationFlag(ULDVO.OPERATION_FLAG_INSERT);
					}else{
						scmDetailsVO.setOperationFlag(ULDVO.OPERATION_FLAG_DELETE);
					}
					scmDetailsVO.setFacilityType(uldVO.getFacilityType());
					scmDetailsVO.setLocation(uldVO.getLocation());
					scmDetailsVOs.add(scmDetailsVO);
					scmDetailsVO.setSequenceNumber(sequenceno);
					//sequenceno=uldVO.getReconciliationSeqNum();
					if(updatedULDs!=null && updatedULDs.containsKey(uldVO.getUldNumber())){
						scmDetailsVO.setUldStatus(updatedULDs.get(uldVO.getUldNumber()).getScmStatusFlag());
					} else {
						scmDetailsVO.setUldStatus(ULDVO.SCM_SYSTEM_STOCK);
					}
				} else if (ULDVO.SCM_MISSING_STOCK.equals(uldVO
						.getScmStatusFlag())) {
					scmDetailsVO.setErrorCode(null);
					scmDetailsVO.setUldNumber(uldVO.getUldNumber());
					//scmDetailsVO.setOperationFlag("I");  //ICRD-107871
					if(!ULDVO.OPERATION_FLAG_DELETE.equals(uldVO.getOperationalFlag())){
						scmDetailsVO.setOperationFlag(ULDVO.OPERATION_FLAG_INSERT);
					}else{
						scmDetailsVO.setOperationFlag(ULDVO.OPERATION_FLAG_DELETE);
					}
					scmDetailsVO.setFacilityType(uldVO.getFacilityType());
					scmDetailsVO.setLocation(uldVO.getLocation());
					scmDetailsVO.setSequenceNumber(sequenceno);
					if(updatedULDs!=null && updatedULDs.containsKey(uldVO.getUldNumber())){
						scmDetailsVO.setUldStatus(updatedULDs.get(uldVO.getUldNumber()).getScmStatusFlag());
					} else {
						scmDetailsVO.setUldStatus(ULDVO.SCM_SYSTEM_STOCK);
					}
					//sequenceno=uldVO.getReconciliationSeqNum();
					scmDetailsVOs.add(scmDetailsVO);
				}else if (ULDVO.SCM_SYSTEM_STOCK.equals(uldVO
						.getScmStatusFlag())) {
					scmDetailsVO.setErrorCode("");
					scmDetailsVO.setUldNumber(uldVO.getUldNumber());
					scmDetailsVO.setOperationFlag("D"); //ICRD-107871					 
					scmDetailsVO.setFacilityType(uldVO.getFacilityType());
					scmDetailsVO.setLocation(uldVO.getLocation());
					scmDetailsVO.setSequenceNumber(sequenceno);
					if(updatedULDs!=null && updatedULDs.containsKey(uldVO.getUldNumber())){
						scmDetailsVO.setUldStatus(updatedULDs.get(uldVO.getUldNumber()).getScmStatusFlag());
					} else {
						scmDetailsVO.setUldStatus(ULDVO.SCM_SYSTEM_STOCK);
					}
					//sequenceno=uldVO.getReconciliationSeqNum();
					scmDetailsVOs.add(scmDetailsVO);
				}else if ("D".equals(uldVO.getOperationalFlag())) {
					scmDetailsVO.setErrorCode("");
					scmDetailsVO.setUldNumber(uldVO.getUldNumber());
					scmDetailsVO.setFacilityType(uldVO.getFacilityType());
					scmDetailsVO.setLocation(uldVO.getLocation());
					scmDetailsVO.setOperationFlag("D");
					scmDetailsVO.setSequenceNumber(sequenceno);
					if(updatedULDs!=null && updatedULDs.containsKey(uldVO.getUldNumber())){
						scmDetailsVO.setUldStatus(updatedULDs.get(uldVO.getUldNumber()).getScmStatusFlag());
					} else {
						scmDetailsVO.setUldStatus("N");
					}
					//sequenceno=uldVO.getReconciliationSeqNum();
					scmDetailsVOs.add(scmDetailsVO);
				}
				
				uldNos.add(uldVO.getUldNumber());
			}
			log.log(Log.INFO, "%%%%%%%%ULD NUMBERS%%%%%%%", uldNos);
		}
		if ("LISTULDS".equals(generateSCMForm.getPageURL())) {
			if (generateSCMSession.getSystemStock() != null
					&& generateSCMSession.getSystemStock().size() > 0) {
				for (ULDVO uldVO : generateSCMSession.getSystemStock()) {
					if (!uldNos.contains(uldVO.getUldNumber())) {
						ULDSCMReconcileDetailsVO vo = new ULDSCMReconcileDetailsVO();
						vo.setUldNumber(uldVO.getUldNumber());
						scmDetailsVOs.add(vo);
					}
				}
			}
		}

		log.log(Log.INFO, "%%%%%%%%%%%%%%%%scmDetailsVOs%%%%%%%%%%",
				scmDetailsVOs);
		ArrayList<ULDVO> extraULDs = new ArrayList<ULDVO>();
		log.log(Log.FINE, "Collection Size of extraULDs before---------->>>",
				extraULDs.size());
		String[] uldNumbers = generateSCMForm.getExtrauld();
		String[] scmStatusFlags = generateSCMForm.getScmStatusFlags();
		String[] operationFlags = generateSCMForm.getOperationFlag();
		String[] facilityTypes = generateSCMForm.getFacilityType();
		String[] location = generateSCMForm.getLocations();
		int count = 0;
		if (uldNumbers != null) {
			count = uldNumbers.length;
		}
		count = count - 1;
		log.log(Log.FINE, "ULDs length------->>>>>", count);
		int index = 0;
		if (count > 0) {
			if (uldNumbers != null) {
				for (String uldNumber : uldNumbers) {
					ULDVO uldVO = new ULDVO();
					uldVO.setCompanyCode(logonAttributes.getCompanyCode());
					uldVO.setFacilityType(facilityTypes[index]);
					uldVO.setScmStatusFlag(scmStatusFlags[index]);
					uldVO.setUldNumber(uldNumbers[index]);
					uldVO.setOperationalFlag(operationFlags[index]);
					uldVO.setLocation(location[index]);
					log.log(Log.FINE, "uldNumbers[index]--------->>",
							uldNumbers, index);
					if (!ULDVO.OPERATION_FLAG_DELETE
							.equals(operationFlags[index])
							&& !"NOOP".equals(operationFlags[index])) {
						log.log(Log.FINE, "INDEX---->>>>>", index);
						log.log(Log.FINE, "ULDNumbers ----->>", uldNumbers,
								index);
						if (!("").equals(uldNumbers[index])
								&& uldNumbers[index].trim().length() > 0) {
							extraULDs.add(uldVO);
						}
					}
					index++;
				}
			}
		}
		log.log(Log.INFO, "extra ulds already presentttt", extraULDs);
		log.log(Log.FINE, "Collection Size of extraULDs---------->>>",
				extraULDs.size());
		log.log(Log.FINE, "Value of  extraULDs---------->>>", extraULDs);
		log.log(Log.FINE,
				"----------Session not null  size of extra ULD-----------",
				extraULDs);
		Collection<ULDAirportLocationVO> uldAirportLocationVOs = null;
		extraULDs.addAll(newULDs);
		boolean isValidFacilityCode = false;
		for (ULDVO uldVO : extraULDs) {
			log.log(Log.INFO, "SCM Airport", generateSCMForm.getScmAirport());
			if (generateSCMForm.getScmAirport() != null
					&& uldVO.getFacilityType() != null
					&& uldVO.getLocation() != null
					&& uldVO.getLocation().trim().length() > 0
					&& uldVO.getFacilityType().trim().length() > 0
					&& "I".equals(uldVO.getOperationalFlag())) {
				try {

					uldAirportLocationVOs = uldDelegate.listULDAirportLocation(
							logonAttributes.getCompanyCode(), generateSCMForm
									.getScmAirport().toUpperCase(), uldVO
									.getFacilityType().toUpperCase());
				} catch (BusinessDelegateException exception) {
					exception.getMessage();
					errors = handleDelegateException(exception);
				}
				for (ULDAirportLocationVO uldAirportLocationVO : uldAirportLocationVOs) {
					if (uldAirportLocationVO.getAirportCode() != null
							&& uldAirportLocationVO.getAirportCode().equals(
									generateSCMForm.getScmAirport()
											.toUpperCase())
							&& uldAirportLocationVO.getFacilityType() != null
							&& uldAirportLocationVO.getFacilityType().equals(
									uldVO.getFacilityType())) {

						if (uldAirportLocationVO.getFacilityCode() != null
								&& uldAirportLocationVO.getFacilityCode()
										.equals(uldVO.getLocation()
												.toUpperCase())) {
							isValidFacilityCode = true;
							break;
						}
					}
				}

				if (!isValidFacilityCode) {
					ErrorVO error = new ErrorVO(
							"uld.defaults.relocate.msg.err.invalidfacilitycode");
					for (ULDVO uldVo : newULDs) {
						generateSCMSession.getUpdatedULDStocks().remove(
								uldVo.getUldNumber());
					}

					errors.add(error);
				}
			}

			Map<String, Collection<OneTimeVO>> oneTimeCollection = fetchScreenLoadDetails(logonAttributes
					.getCompanyCode());
			ArrayList<OneTimeVO> facTypes = (ArrayList<OneTimeVO>) oneTimeCollection
					.get(FACILITY_TYPE);
			for (ULDVO uldvo : extraULDs) {
				if (facTypes != null && facTypes.size() > 0) {
					for (OneTimeVO oneTimeVO : facTypes) {
						if (uldvo.getFacilityType() != null
								&& uldVO.getFacilityType().trim().length() > 0
								&& uldvo.getFacilityType().equals(
										oneTimeVO.getFieldValue())) {
							uldvo.setFacilityType(oneTimeVO.getFieldValue());
						}
					}
				}
			}

		}
		boolean uldDuplicateExist = false;
		if (newuldNumbers != null && stockUlds != null
				&& newuldNumbers.length > 0 && stockUlds.length > 0) {
			int uldIndex = 0;
			Object Object_Array[] = new Object[newuldNumbers.length];
			StringBuilder invalidUldNumbers = new StringBuilder();
			int i = 0;
			// added A-5125 for BUG-ICRD-40925
			for (String foundUldNumber : newuldNumbers) {
				if (!"NOOP".equals(newuldNumbersOperationFlags[i])) {
					for (String stkUldnumber : stockUlds) {
						if (stkUldnumber.equals(foundUldNumber)) {
							if (invalidUldNumbers.length() == 0) {
								invalidUldNumbers.append(stkUldnumber);
							} else {
								invalidUldNumbers.append(",").append(
										stkUldnumber);
							}
							uldDuplicateExist = true;
							uldIndex++;
						}
					}
				}
				i++;
			}
			for (ULDVO uldVo : newULDs) {
				generateSCMSession.getUpdatedULDStocks().remove(
						uldVo.getUldNumber());
			}
			if (uldDuplicateExist) {
				Object_Array[0] = invalidUldNumbers.toString();
				log.log(Log.FINE, invalidUldNumbers.toString());
				ErrorVO errorVO = new ErrorVO(
						"uld.defaults.messaging.generatescm.err.uldAlreadyInSystemStock",
						Object_Array);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_DRAFT_FAILED;
				return;
			}
		}
		log.log(Log.INFO, "SCM Vos-->", scmDetailsVOs);
		Collection<String> invalidUlds = null;
		invalidUlds = validateULDFormat(compCode, extraULDs);
		ErrorVO error = null;

		if (invalidUlds != null && invalidUlds.size() > 0) {
			int size = invalidUlds.size();
			for (int i = 0; i < size; i++) {
				error = new ErrorVO(
						"uld.defaults.ucminout.msg.err.invaliduldformat",
						new Object[] { ((ArrayList<String>) invalidUlds).get(i) });
				errors.add(error);
				for (ULDVO uldVo : newULDs) {
					generateSCMSession.getUpdatedULDStocks().remove(
							uldVo.getUldNumber());
				}
			}
			generateSCMSession.setNewSystemStock(newULDs);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_DRAFT_FAILED;
			return;
		}

		if (errors != null && errors.size() > 0) {
			generateSCMSession.setNewSystemStock(newULDs);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_DRAFT_FAILED;
			return;
		}

		populateScmChildVos(scmDetailsVOs, generateSCMForm);
		for (ULDSCMReconcileDetailsVO reconcileDtlVO : scmDetailsVOs) {
			log.log(Log.INFO, "before reconcileDtlVO ");
			reconcileDtlVO.setAirlineIdentifier(generateSCMSession
					.getMessageFilterVO().getFlightCarrierIdentifier());
			if ("LISTULD".equals(generateSCMForm.getPageURL())
					|| "LISTULDS".equals(generateSCMForm.getPageURL())
					|| generateSCMSession.getMessageFilterVO().getFacility() != null) {
				reconcileDtlVO.setFacilityType(generateSCMSession
						.getMessageFilterVO().getFacility());
				reconcileDtlVO.setLocation(generateSCMSession
						.getMessageFilterVO().getLocation());
			}
			log.log(Log.INFO, "after reconcileDtlVO ");
		}
		Map<String, Collection<OneTimeVO>> oneTimeCollection = fetchScreenLoadDetails(logonAttributes
				.getCompanyCode());
		ArrayList<OneTimeVO> facTypes = (ArrayList<OneTimeVO>) oneTimeCollection
				.get(FACILITY_TYPE);
		for (ULDSCMReconcileDetailsVO uldSCMReconcileDetailsVO : scmDetailsVOs) {
			if (facTypes != null && facTypes.size() > 0) {
				for (OneTimeVO oneTimeVO : facTypes) {
					if (uldSCMReconcileDetailsVO.getFacilityType() != null
							&& uldSCMReconcileDetailsVO.getFacilityType()
									.trim().length() > 0
							&& uldSCMReconcileDetailsVO.getFacilityType()
									.equals(oneTimeVO.getFieldValue())) {
						uldSCMReconcileDetailsVO.setFacilityType(oneTimeVO
								.getFieldValue());
					}
				}
			}
		}
		scmReconcileVO.setReconcileDetailsVOs(scmDetailsVOs);
		if(scmDetailsVOs!=null && scmDetailsVOs.size()>0){
			scmReconcileVO.setSequenceNumber(sequenceno);
		}
		scmReconcileVO.setRemarks(generateSCMForm.getRemarks());
		scmReconcileVOs.add(scmReconcileVO);
		Collection<ErrorVO> err = new ArrayList<ErrorVO>();
		errors = checkDuplicateULDs(scmReconcileVO.getReconcileDetailsVOs());
		if (errors != null && errors.size() > 0) {
			generateSCMSession.setNewSystemStock(newULDs);
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_DRAFT_FAILED;
			log.log(Log.INFO, "errors occued");
			return;
		}
		ULDListFilterVO uldListFilterVO = null;
		AirlineValidationVO airlineValidationVO = null;
		try {
			if("LISTULDS".equals(generateSCMForm.getPageURL())){
				log.log(Log.INFO,"LISTULDS---->");
				ListMessageSession msgsession = 
					getScreenSession( MSGMODULE_NAME,MSGSCREEN_ID);
				Collection<MessageDespatchDetailsVO> additionaldespatchDetailsVOs = msgsession.getDespatchDetails();
				uldDelegate.sendSCMMessageforUlds(scmReconcileVOs,additionaldespatchDetailsVOs);
			}else{
				//added by a-3045 for bug 24468 starts
				uldListFilterVO = new ULDListFilterVO();
				uldListFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				uldListFilterVO.setFromListULD(false);
				if(generateSCMSession.getULDListFilterVO()!= null){					
					uldListFilterVO = generateSCMSession.getULDListFilterVO();
				}else{
					uldListFilterVO.setAirlineCode(generateSCMForm.getScmAirline());
					uldListFilterVO.setCurrentStation(generateSCMForm.getScmAirport());
					
					airlineValidationVO = new AirlineDelegate().validateAlphaCode(
							logonAttributes.getCompanyCode(),generateSCMForm.getScmAirline());					
					uldListFilterVO.setAirlineidentifier(airlineValidationVO.getAirlineIdentifier());
					uldListFilterVO.setUldRangeFrom(-1);
					uldListFilterVO.setUldRangeTo(-1);
				}
				log.log(Log.INFO, "uldListFilterVO--%%%%%%%%%%%%%%%%%-->",
						uldListFilterVO);
				
				uldDelegate.saveSCMReconcialtionDetails(scmReconcileVOs);
			}
			
		} catch (BusinessDelegateException ex) {
			ex.getMessage();
			err = handleDelegateException(ex);
		}
		//modified as part of bugfix icrd-10802 by A-4810
		if(err!=null && err.size()>0){
			for(ULDVO uldVo :newULDs){
				generateSCMSession.getUpdatedULDStocks().remove(uldVo.getUldNumber());
				}
			invocationContext.addAllError(err);
			invocationContext.target = SAVE_DRAFT_FAILED;
			log.log(Log.INFO,"errors occued");
			return;
		}
		generateSCMSession.removeAllAttributes();	
		error=new ErrorVO("uld.defaults.messaging.saveas.success");
		errors.add(error);
		invocationContext.addAllError(errors);
		invocationContext.target = SUCCESS;
	}

	/**
	 * 
	 * @param companyCode
	 * @param uldVOs
	 * @return
	 */
	public Collection<String> validateULDFormat(String companyCode,
			Collection<ULDVO> uldVOs) {
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Collection<String> uldNumbers = new ArrayList<String>();
		for (ULDVO uldVO : uldVOs) {
			if ("I".equals(uldVO.getOperationalFlag())
					&& uldVO.getUldNumber() != null
					&& uldVO.getUldNumber().trim().length() > 0)
				{
				uldNumbers.add(uldVO.getUldNumber());
				}
		}
		Collection<String> invalidUlds = null;
		try {
			log.log(Log.FINE, "Invalid uld numbers", uldNumbers);
			invalidUlds = delegate.validateMultipleULDFormats(companyCode,
					uldNumbers);
		} catch (BusinessDelegateException ex) {
			log.log(Log.FINE, "\n\n\ninside handle delegatwe exception");
			ex.getMessage();
			handleDelegateException(ex);
		}
		return invalidUlds;
	}

	/**
	 * 
	 * @param detailVOs
	 * @param generateSCMForm
	 */
	private void populateScmChildVos(
			Collection<ULDSCMReconcileDetailsVO> detailVOs,
			GenerateSCMForm generateSCMForm) {
		if (detailVOs != null && detailVOs.size() > 0) {
			for (ULDSCMReconcileDetailsVO detailsVO : detailVOs) {
				detailsVO.setAirportCode(generateSCMForm.getScmAirport()
						.toUpperCase());
				detailsVO.setCompanyCode(getApplicationSession().getLogonVO()
						.getCompanyCode());
				// detailsVO.setOperationFlag(ULDSCMReconcileDetailsVO.OPERATION_FLAG_INSERT);

			}
		}
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
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);

		} catch (BusinessDelegateException exception) {
			handleDelegateException(exception);
		}
		return hashMap;
	}

	/**
	 * 
	 * @param uldVOs
	 * @return
	 */
	private Collection<ErrorVO> checkDuplicateULDs(
			Collection<ULDSCMReconcileDetailsVO> uldVOs) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		for (ULDSCMReconcileDetailsVO uldVO1 : uldVOs) {
			int noOfOccurance = 0;
			String uldNumber = uldVO1.getUldNumber();
			for (ULDSCMReconcileDetailsVO uldVO2 : uldVOs) {
				if (uldVO2.getUldNumber().equals(uldNumber)) {
					noOfOccurance++;
				}
				if (noOfOccurance > 1) {
					ErrorVO error = new ErrorVO(
							"uld.defaults.messaging.generatescm.duplicateuldsexist");
					errors.add(error);
					return errors;
				}
			}
		}
		return errors;
	}

	/**
	 * @author A-5125
	 * @param newuldNumbers
	 * @return errors
	 * 
	 *         Method to remove Duplicate ulds in Found Ulds Section
	 */
	private Collection<ErrorVO> checkDuplicateInFoundULDs(
			String[] newuldNumbers, String[] newOperaionFlags) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<String> foundUlds = new ArrayList<String>();
		Collection<String> dupilcateUldCol = new ArrayList<String>();
		StringBuffer duplicateUldNumbers = new StringBuffer();
		int i = 0;
		for (String uldNo : newuldNumbers) {
			// added A-5125 for BUG-ICRD-40925 if the ULD number is deleted in
			// found ULD section not allowing to validate.
			if (!"NOOP".equals(newOperaionFlags[i])) {
				if (foundUlds.contains(uldNo.trim())) {
					if (!dupilcateUldCol.contains(uldNo)) {
						duplicateUldNumbers.append(uldNo).append(",");
						dupilcateUldCol.add(uldNo);
					}
				} else {
					if (uldNo.length() > 0) {
						foundUlds.add(uldNo.trim());
					}
				}

			}
			i++;
		}
		if (duplicateUldNumbers.toString().length() > 0) {
			Object Object_Array[] = new Object[] { duplicateUldNumbers
					.toString().substring(0,
							duplicateUldNumbers.toString().length() - 1) };
			ErrorVO error = new ErrorVO(
					"uld.defaults.messaging.generatescm.duplicateuldsexistInFoundUlds",
					Object_Array);
			errors.add(error);
			return errors;
		}
		return errors;
	}

	/**
	 * 
	 * @param reconcileVO
	 * @param generateSCMForm
	 */
	private void populateScmReconcileVO(ULDSCMReconcileVO reconcileVO,
			GenerateSCMForm generateSCMForm) {
		reconcileVO.setOperationFlag(ULDSCMReconcileVO.OPERATION_FLAG_INSERT);
		reconcileVO.setAirportCode(generateSCMForm.getScmAirport()
				.toUpperCase());
		LocalDate fltDate = new LocalDate(reconcileVO.getAirportCode(),
				Location.ARP, true);
		String stockCheckDate = generateSCMForm.getScmStockCheckDate();
		StringBuilder dateAndTime = new StringBuilder(stockCheckDate);
		String stockCheckTime = generateSCMForm.getScmStockCheckTime();
		if (!stockCheckTime.contains(":")) {
			stockCheckTime = stockCheckTime.concat(":00");
		}
		dateAndTime.append(" ").append(stockCheckTime).append(":00");

		log.log(Log.FINE, "\n\n\n\nDATE AND TIME", dateAndTime);
		reconcileVO.setStockCheckDate(fltDate.setDateAndTime(dateAndTime
				.toString()));
	}
}
