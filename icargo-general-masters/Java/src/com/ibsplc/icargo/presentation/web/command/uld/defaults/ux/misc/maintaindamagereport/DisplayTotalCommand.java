package com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


import com.ibsplc.icargo.business.shared.currency.vo.CurrencyConvertorVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc.MaintainDamageReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ux.misc.MaintainDamageReportForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.ux.misc.maintaindamagereport.DisplayTotalCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7627	:	25-Oct-2017	:	Draft
 */
public class DisplayTotalCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Maintain Damage Report");
	private static final String MODULE = "uld.defaults";
	private static final String MAINTAIN_DAMAGE_REPORT_REPAIRNOTFOUND_ERROR = "uld.defaults.maintainDmgRep.msg.err.repairdetailsnotpresent";
	private static final String SYSPAR_ULDINVCURRENCY = "uld.defaults.uldinvoicingcurrency";
	private static final String SYSPAR_EXCHANGERATE="uld.defaults.exchangeratepriorityorder";
	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID = "uld.defaults.ux.maintaindamagereport";
	private static final String DISPLAY_SUCCESS = "display_success";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		MaintainDamageReportForm maintainDamageReportForm = (MaintainDamageReportForm) invocationContext.screenModel;
		MaintainDamageReportSession maintainDamageReportSession = getScreenSession(
				MODULE, SCREENID);

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		populateUldDamageRepairDetailsVO(maintainDamageReportForm,maintainDamageReportSession,logonAttributes);
		
		// for populating details from main screen into the session-START
		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO = maintainDamageReportSession
				.getULDDamageVO() != null ? maintainDamageReportSession
				.getULDDamageVO() : new ULDDamageRepairDetailsVO();
		if (uldDamageRepairDetailsVO != null) {
			uldDamageRepairDetailsVO.setDamageStatus(maintainDamageReportForm
					.getDamageStatus());
			uldDamageRepairDetailsVO.setOverallStatus(maintainDamageReportForm
					.getOverallStatus());
			uldDamageRepairDetailsVO.setRepairStatus(maintainDamageReportForm
					.getRepairStatus());
			uldDamageRepairDetailsVO.setSupervisor(maintainDamageReportForm
					.getSupervisor());
			uldDamageRepairDetailsVO
					.setInvestigationReport(maintainDamageReportForm
							.getInvRep());

		}
		maintainDamageReportForm.setScreenReloadStatus("reload");
		// for populating details from main screen into the session-END
		
		Collection<String> systemparameterCodes = new ArrayList<String>();
		HashMap<String, String> systemParameterMap = new HashMap<String, String>();
		systemparameterCodes.add(SYSPAR_ULDINVCURRENCY);
		systemparameterCodes.add(SYSPAR_EXCHANGERATE);
		try {
			systemParameterMap = (HashMap<String, String>)new SharedDefaultsDelegate().findSystemParameterByCodes(systemparameterCodes);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.SEVERE, "System Parameter for Damage Status not found ");
		}

		ArrayList<ULDRepairVO> uLDRepairVOs = maintainDamageReportSession
				.getULDDamageVO().getUldRepairVOs() != null ? new ArrayList<ULDRepairVO>(
				maintainDamageReportSession.getULDDamageVO().getUldRepairVOs())
				: new ArrayList<ULDRepairVO>();
		if (uLDRepairVOs == null || uLDRepairVOs.size() == 0) {
			maintainDamageReportForm.setStatusFlag("TOTAL_FAILURE");
			ErrorVO error = null;
			error = new ErrorVO(MAINTAIN_DAMAGE_REPORT_REPAIRNOTFOUND_ERROR);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
		} else {
			double totalAmt = 0.0;
			try {
				totalAmt = findUldTotalCost(uLDRepairVOs,systemParameterMap);
			} catch (BusinessDelegateException e) {
				invocationContext.addAllError(handleDelegateException(e));
			} catch (SystemException e){
				invocationContext.addError(new ErrorVO(e.getMessage()));
			}
			maintainDamageReportForm.setStatusFlag("TOTAL_SUCESS");
			maintainDamageReportForm.setTotAmt(String.valueOf(totalAmt));
		}
		invocationContext.target = DISPLAY_SUCCESS;
	}

	/**
	 * A-7390
	 * @param form
	 * @param session
	 * @param logonAttributes
	 */
	private void populateUldDamageRepairDetailsVO(MaintainDamageReportForm form,MaintainDamageReportSession session,LogonAttributes logonAttributes){
		
		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO = session.getULDDamageVO();
		String[] section = form.getSection();
		String[] damageDesc = form.getDescription();
		String[] severity = form.getSeverity();
		String[] reportedAirport = form.getRepStn();
		String[] reportedDate = form.getReportedDate();
		String[] facilityType = form.getFacilityType();
		String[] location = form.getLocation();
		String[] partyType = form.getPartyType();
		String[] party = form.getParty();
		String[] remarks = form.getRemarks();
		String[] opFlag = form.getTempOperationFlag();
		String[] closed = form.getClosed();
		
		ULDDamageVO uldDamageVO = null;
		ULDRepairVO uldRepairVO = null;
		ArrayList<ULDDamageVO> uLDDamageVOsFromSession = (ArrayList)uldDamageRepairDetailsVO.getUldDamageVOs();
		ArrayList<ULDRepairVO> uldRepairVOsFromSession = (ArrayList)uldDamageRepairDetailsVO.getUldRepairVOs();
		
		ArrayList<ULDDamageVO> uLDDamageVOsToSession = new ArrayList<ULDDamageVO>();
		ArrayList<ULDRepairVO> uldRepairVOsToSession = new ArrayList<ULDRepairVO>();
		
		if(uLDDamageVOsFromSession != null && uLDDamageVOsFromSession.size() > 0){
			for(ULDDamageVO vo : uLDDamageVOsFromSession){
				if(!ULDDamageVO.OPERATION_FLAG_INSERT.equals(vo.getOperationFlag())){
					uLDDamageVOsToSession.add(vo);
				}
			}
		}
		if(uldRepairVOsFromSession != null && uldRepairVOsFromSession.size() > 0){
			for(ULDRepairVO vo : uldRepairVOsFromSession){
				if(!ULDDamageVO.OPERATION_FLAG_INSERT.equals(vo.getOperationFlag())){
					uldRepairVOsToSession.add(vo);
				}
			}
		}
		uldRepairVOsFromSession = uldRepairVOsToSession;
		uLDDamageVOsFromSession = uLDDamageVOsToSession;
		if (opFlag != null && opFlag.length > 0){
			for (int i = 0; i < opFlag.length-1; i++) {
				uldDamageVO=null;
				if(ULDDamageVO.OPERATION_FLAG_INSERT.equals(opFlag[i])) {
					
					uldDamageVO = new ULDDamageVO();
					uldDamageVO.setOperationFlag(ULDDamageVO.OPERATION_FLAG_INSERT);
					uLDDamageVOsFromSession.add(uldDamageVO);
				}else if(ULDDamageVO.OPERATION_FLAG_UPDATE.equals(opFlag[i]) || "N".equals(opFlag[i])){
					uldDamageVO = uLDDamageVOsFromSession.get(i);
					uldDamageVO.setOperationFlag(ULDDamageVO.OPERATION_FLAG_UPDATE);
				}else if(ULDDamageVO.OPERATION_FLAG_DELETE.equals(opFlag[i])){
					uldDamageVO = uLDDamageVOsFromSession.get(i);
					uldDamageVO.setOperationFlag(ULDDamageVO.OPERATION_FLAG_DELETE);
				}
				if (uldDamageVO!=null && (ULDDamageVO.OPERATION_FLAG_INSERT.equals(uldDamageVO.getOperationFlag())
						|| ULDDamageVO.OPERATION_FLAG_UPDATE.equals(uldDamageVO.getOperationFlag()))
						|| "N".equals(opFlag[i])) {
					
					uldDamageVO.setSection(section[i]);
					uldDamageVO.setDamageDescription(damageDesc[i]);
					uldDamageVO.setSeverity(severity[i]);
					uldDamageVO.setReportedStation(reportedAirport[i]);
					if(reportedDate[i]!=null && reportedDate[i].trim().length() > 0) { 	
						LocalDate repDateLocal  = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);		
						String repDateStr = reportedDate[i];
						String repdate = null;
						StringBuilder time = new StringBuilder();
						time.append(" ").append(repDateLocal.get(LocalDate.HOUR_OF_DAY)).append(":").append(repDateLocal.get(LocalDate.MINUTE)).append(":").append(repDateLocal.get(LocalDate.SECOND));
						if(repDateStr != null){
							repdate =repDateStr.concat(time.toString());
							repDateLocal.setDateAndTime(repdate);  		
						}
						uldDamageVO.setReportedDate(repDateLocal);
					}
					uldDamageVO.setFacilityType(facilityType[i]);
					uldDamageVO.setLocation(location[i]);
					uldDamageVO.setPartyType(partyType[i]);
					uldDamageVO.setParty(party[i]);
					uldDamageVO.setRemarks(remarks[i]);
					uldDamageVO.setLastUpdateUser(logonAttributes.getUserId());
				}
			}
		}
		//populating Repailr Details
		String[] repairHead = form.getRepHead();
		String[] repairStn = form.getRepairStn();
		String[] repairDate = form.getRepairDate();
		String[] dmgRepairRefNo = form.getDmgRepairRefNo();
		String[] amount = form.getAmount();
		String[] currency = form.getCurrency();
		String[] repRemark = form.getRepRemarks();
		String[] tempRepairOpFlag = form.getTempRepairOpFlag();
		Double repairAmount = 0.0;
		
		if (tempRepairOpFlag != null && tempRepairOpFlag.length > 0){
			for (int i = 0; i < tempRepairOpFlag.length-1; i++) {
				uldRepairVO=null;
				if(ULDRepairVO.OPERATION_FLAG_INSERT.equals(tempRepairOpFlag[i])) {
					//uldRepairVO = new ULDRepairVO();
					//uldRepairVO.setOperationFlag(ULDRepairVO.OPERATION_FLAG_INSERT);

					//Replicating ICRD-297633 to DisplayTotalCommand to handle multiple damages against single repair.

					if(dmgRepairRefNo[i].contains(",")){
						String[] dmgRepairRefNumber=dmgRepairRefNo[i].split(",");
						if(form.getAmount()[i]!=null && form.getAmount()[i].trim().length() > 0){
							int len = dmgRepairRefNumber.length;
							repairAmount =Double.parseDouble(form.getAmount()[i])/len;
						}
						for (String temp: dmgRepairRefNumber){
							if(temp != null && temp.trim().length()>0) {
					uldRepairVO = new ULDRepairVO();
								uldRepairVO.setDamageReferenceNumber(Integer.parseInt(temp));
								uldRepairVO.setAmount(repairAmount);
					uldRepairVO.setOperationFlag(ULDRepairVO.OPERATION_FLAG_INSERT);
								populateRepairDetailsVO(form, i, logonAttributes, uldRepairVO);
					uldRepairVOsFromSession.add(uldRepairVO);
							}
						}
					}
					else{
						uldRepairVO = new ULDRepairVO();
						if(dmgRepairRefNo[i]!=null && dmgRepairRefNo[i].trim().length() > 0){
							uldRepairVO.setDamageReferenceNumber(Integer.parseInt(dmgRepairRefNo[i]));
						}
						if(amount[i]!=null && amount[i].trim().length() > 0){
							uldRepairVO.setAmount(Double.parseDouble(amount[i]));
							uldRepairVO.setDisplayAmount(Double.parseDouble(TextFormatter.formatDouble(uldRepairVO.getAmount(),3)));
						}
						uldRepairVO.setOperationFlag(ULDRepairVO.OPERATION_FLAG_INSERT);
						populateRepairDetailsVO(form,i,logonAttributes,uldRepairVO);
						uldRepairVOsFromSession.add(uldRepairVO);
					}

					//uldRepairVOsFromSession.add(uldRepairVO);
				}else if(ULDRepairVO.OPERATION_FLAG_UPDATE.equals(tempRepairOpFlag[i]) || "N".equals(tempRepairOpFlag[i])){
					uldRepairVO = uldRepairVOsFromSession.get(i);
					populateRepairDetailsVO(form,i,logonAttributes,uldRepairVO);
					uldRepairVO.setOperationFlag(ULDRepairVO.OPERATION_FLAG_UPDATE);
				}else if(ULDRepairVO.OPERATION_FLAG_DELETE.equals(tempRepairOpFlag[i])){
					uldRepairVO = uldRepairVOsFromSession.get(i);
					if(dmgRepairRefNo[i]!=null && dmgRepairRefNo[i].trim().length() > 0){
						uldRepairVO.setDamageReferenceNumber(Integer.parseInt(dmgRepairRefNo[i]));
					}
					uldRepairVO.setOperationFlag(ULDRepairVO.OPERATION_FLAG_DELETE);
				}
				if (uldRepairVO!=null /*&& (ULDRepairVO.OPERATION_FLAG_INSERT.equals(uldRepairVO.getOperationFlag()*/
						&&(ULDRepairVO.OPERATION_FLAG_UPDATE.equals(uldRepairVO.getOperationFlag())
						|| "N".equals(tempRepairOpFlag[i]))) {
					
					/*uldRepairVO.setRepairHead(repairHead[i]);
					uldRepairVO.setRepairStation(repairStn[i]);
					if(repairDate[i]!=null && repairDate[i].trim().length() > 0) { 	
						LocalDate repairDateLocal  = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);		
						String repDateStr = repairDate[i];
						String repdate = null;
						StringBuilder time = new StringBuilder();
						time.append(" ").append(repairDateLocal.get(LocalDate.HOUR_OF_DAY)).append(":").append(repairDateLocal.get(LocalDate.MINUTE)).append(":").append(repairDateLocal.get(LocalDate.SECOND));
						if(repDateStr != null){
							repdate =repDateStr.concat(time.toString());
							repairDateLocal.setDateAndTime(repdate);  		
						}
						uldRepairVO.setRepairDate(repairDateLocal);
					}*/
					if(dmgRepairRefNo[i]!=null && dmgRepairRefNo[i].trim().length() > 0){
						uldRepairVO.setDamageReferenceNumber(Integer.parseInt(dmgRepairRefNo[i]));
					}
					if(amount[i]!=null && amount[i].trim().length() > 0){
						uldRepairVO.setAmount(Double.parseDouble(amount[i]));
						//Added by A-7390 for ICRD-234401
						//uldRepairVO.setDisplayAmount(Double.parseDouble(TextFormatter.formatDouble(uldRepairVO.getAmount(),3)));
					}
					/*uldRepairVO.setCurrency(currency[i]);
					uldRepairVO.setRemarks(repRemark[i]);
					uldRepairVO.setLastUpdateUser(logonAttributes.getUserId());*/
				}
			}
		}
		if(closed!=null) {
			for(int i=0;i<closed.length;i++)
		if("Y".equals(closed[i]))
		{
//			uLDDamageVOsFromSession.get(Integer.parseInt(closed[i])).setClosed(true);
			uLDDamageVOsFromSession.get(i).setClosed(true);
		}
		else{
//			uLDDamageVOsFromSession.get(Integer.parseInt(closed[i])).setClosed(false);
			uLDDamageVOsFromSession.get(i).setClosed(false);
			
		}
		}
		uldDamageRepairDetailsVO.setUldDamageVOs(uLDDamageVOsFromSession);
		uldDamageRepairDetailsVO.setUldRepairVOs(uldRepairVOsFromSession);
		session.setULDDamageVO(uldDamageRepairDetailsVO);
	}

	//Replicating 297633
	private void populateRepairDetailsVO(MaintainDamageReportForm form,int j,LogonAttributes logonAttributes,ULDRepairVO uldRepairVO) {
		uldRepairVO.setRepairHead(form.getRepHead()[j]);
		uldRepairVO.setRepairStation(form.getRepairStn()[j]);
		if(form.getRepairDate()[j]!=null && form.getRepairDate()[j].trim().length() > 0) {
			LocalDate repairDateLocal  = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			String repDateStr = form.getRepairDate()[j];
			String repdate = null;
			StringBuilder time = new StringBuilder();
			time.append(" ").append(repairDateLocal.get(LocalDate.HOUR_OF_DAY)).append(":").append(repairDateLocal.get(LocalDate.MINUTE)).append(":").append(repairDateLocal.get(LocalDate.SECOND));
			if(repDateStr != null){
				repdate =repDateStr.concat(time.toString());
				repairDateLocal.setDateAndTime(repdate);
			}
			uldRepairVO.setRepairDate(repairDateLocal);
		}
		uldRepairVO.setDisplayAmount(Double.parseDouble(TextFormatter.formatDouble(uldRepairVO.getAmount(),3)));
		uldRepairVO.setCurrency(form.getCurrency()[j]);
		uldRepairVO.setRemarks(form.getRepRemarks()[j]);
		uldRepairVO.setLastUpdateUser(logonAttributes.getUserId());
	}

	/**
	 * @author A-7390
	 * @param uLDRepairVOs
	 * @return
	 */
	private double findUldTotalCost(ArrayList<ULDRepairVO> uLDRepairVOs,HashMap<String,String> systemParameterMap) throws BusinessDelegateException, SystemException {
		HashMap<String,Double> conversionRateMap = new HashMap<String, Double>();
		double totalAmt=0;
		double conversionFac=0;
		Money totalAmount;
		String baseCurrency = systemParameterMap.get(SYSPAR_ULDINVCURRENCY);
		String basis = systemParameterMap.get(SYSPAR_EXCHANGERATE);
		if(basis==null || baseCurrency==null || uLDRepairVOs == null) {
			return 0;
		}
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		CurrencyDelegate currencyDelegate = new CurrencyDelegate();
		CurrencyConvertorVO currencyConvertorVO = new CurrencyConvertorVO();
		try {
			totalAmount = CurrencyHelper.getMoney(baseCurrency);
		} catch (CurrencyException e) {
			throw new SystemException(e.getMessage());
		}
		if(totalAmount==null) {
			return 0;
		}
		for (ULDRepairVO uldRepairVO : uLDRepairVOs) {
			if(ULDRepairVO.OPERATION_FLAG_DELETE.equalsIgnoreCase(uldRepairVO.getOperationFlag())) {
				continue;
			}
			if(!conversionRateMap.containsKey(uldRepairVO.getCurrency())) {

				currencyConvertorVO.setCompanyCode(companyCode);
				currencyConvertorVO.setAirlineIdentifier(uldRepairVO.getAirlineIdentifier());
				currencyConvertorVO.setToCurrencyCode(baseCurrency);
				currencyConvertorVO.setRatePickUpDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
				currencyConvertorVO.setFromCurrencyCode(uldRepairVO.getCurrency());

				for (int i = 0; i < basis.split(",").length; i++) {
					currencyConvertorVO.setRatingBasisType(basis.split(",")[i]);
					conversionFac = currencyDelegate.findExchangeRate(currencyConvertorVO);
					if (conversionFac > 0) {
						break;
					}
				}
			} else {
				conversionFac = conversionRateMap.get(uldRepairVO.getCurrency());
			}
			conversionRateMap.put(uldRepairVO.getCurrency(),conversionFac);
			totalAmt += conversionFac * uldRepairVO.getDisplayAmount();
		}
		totalAmount.setAmount(totalAmt);
		return totalAmount.getRoundedAmount();
	}

}
