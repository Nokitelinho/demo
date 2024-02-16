/*
 * ListRateAuditDetailsCommand.java Created on Jul 17, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.rateauditdetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListRateAuditSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.RateAuditDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.RateAuditDetailsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-2391
 *
 */
public class ListRateAuditDetailsCommand  extends BaseCommand {
	
	private Log log = LogFactory.getLogger("RATE AUDITDETAILS ListRateAuditDetailsCommand");
	private static final String MODULE = "mailtracking.mra.defaults";
	private static final String SCREENID = "mailtracking.mra.defaults.rateauditdetails";
	private static final String LIST_SUCCESS="list_success";
	private static final String LIST_FAILURE="list_failure";
	private static final String DESPATCHNUM_MANDATORY = "mailtracking.mra.defaults.despatchenquiry.dsnmandatory";
	private static final String NO_RESULTS = "mailtracking.mra.defaults.despatchenquiry.noresultsfound";
	private static final String ONETIME_DSNSTATUS = "mailtracking.mra.defaults.rateaudit.status";
	private static final String YES = "Y";
	private static final String NO = "N";
	private static final String RECIEVEABLE = "R";
	private static final String DSNPOPUP_SCREENID = "mailtracking.mra.defaults.dsnselectpopup";
	//private static final String FROM_SCREEN = "fromListRateAuditScreen";
	private static final String FROM_SCREEN="fromListRateAudit";
	
	private static final String CURRENCY_CODE = "NZD";

	private static final String SCREENID_LISTRATEAUDIT =
		"mailtracking.mra.defaults.listrateaudit";	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 
		 log.entering("ListRateAuditDetailsCommand", "execute");
		 RateAuditDetailsForm rateAuditDetailsForm=(RateAuditDetailsForm)invocationContext.screenModel;
		 //added for AirNZ174 starts
		 ListRateAuditSession listRateAuditSession = getScreenSession(
				 MODULE, SCREENID_LISTRATEAUDIT);
		 RateAuditDetailsSession session=getScreenSession(MODULE,SCREENID);
		 DSNPopUpSession dSNPopUpSession = getScreenSession(MODULE,DSNPOPUP_SCREENID);
		 RateAuditFilterVO rateAuditFilterVO = new RateAuditFilterVO();
		 session.setBillToChgFlag("N");
		 if(rateAuditDetailsForm.getFromScreen()!=null && "LISTRA".equals(rateAuditDetailsForm.getFromScreen())){
			 
			
				DSNPopUpVO dsnPopUpVO = new DSNPopUpVO();
				
				ArrayList<RateAuditVO> rateAuditVOs=new ArrayList<RateAuditVO>(listRateAuditSession.getRateAuditVOs());				
				if(rateAuditDetailsForm.getSelectedRowIndex()>=0){
				RateAuditVO rateAuditVO=rateAuditVOs.get(rateAuditDetailsForm.getSelectedRowIndex());
				if(rateAuditVO!=null){
				rateAuditDetailsForm.setIsFromListRateAuditScreen(YES);
				if(rateAuditVO.getDsn()!=null){
				rateAuditDetailsForm.setDsnNumber(rateAuditVO.getBillingBasis());
				rateAuditFilterVO.setDsn(rateAuditDetailsForm.getDsnNumber());
				}
				if(rateAuditVO.getDsnDate()!=null){
				rateAuditDetailsForm.setDsnDate(rateAuditVO.getDsnDate().toDisplayDateOnlyFormat());
				rateAuditFilterVO.setDsnDate(rateAuditVO.getDsnDate());
				}
				if(rateAuditVO.getDsnStatus()!=null){
					rateAuditDetailsForm.setDsnStatus(rateAuditVO.getDsnStatus());
					//rateAuditFilterVO.setDsnStatus(rateAuditDetailsForm.getDsnStatus());
					}
				rateAuditFilterVO.setCompanyCode(rateAuditVO.getCompanyCode());
				rateAuditFilterVO.setBillingBasis(rateAuditVO.getBillingBasis());				
				rateAuditFilterVO.setCsgDocNum(rateAuditVO.getConDocNum());
				rateAuditFilterVO.setCsgSeqNum(rateAuditVO.getConSerNum());
				rateAuditFilterVO.setGpaCode(rateAuditVO.getGpaCode());
				
				/*
				 * set the filter values in dsn popup session as it is used in cca comand to go to maintain cca screen 
				 * so that the flow from list ra to ra and to maintain cca will be fine
				 */
				 
				dsnPopUpVO.setCompanyCode(rateAuditFilterVO.getCompanyCode());
				dsnPopUpVO.setBlgBasis(rateAuditFilterVO.getBillingBasis());			
				dsnPopUpVO.setDsnDate(rateAuditFilterVO.getDsnDate().toDisplayDateOnlyFormat());
				dsnPopUpVO.setCsgdocnum(rateAuditFilterVO.getCsgDocNum());
				dsnPopUpVO.setCsgseqnum(rateAuditFilterVO.getCsgSeqNum());
				dsnPopUpVO.setGpaCode(rateAuditFilterVO.getGpaCode());
				dSNPopUpSession.setSelectedDespatchDetails(dsnPopUpVO);
				
				log.log(Log.FINE, "rateAuditFilterVO---from listrate-->>>",
						rateAuditFilterVO);
				}
				}
				session.setRateAuditFilterVO(rateAuditFilterVO);
				listRateAuditSession.setFromScreen(FROM_SCREEN);
		 }
		 // added for AirNZ174 ends
		 
		 else{
		 
				log.log(Log.FINE, "DsnDate----->>>", dSNPopUpSession.getSelectedDespatchDetails().getDsnDate());
				if(dSNPopUpSession.getSelectedDespatchDetails().getDsnDate()!=null&&!"".equals(dSNPopUpSession.getSelectedDespatchDetails().getDsnDate())){
					rateAuditFilterVO.setDsnDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(dSNPopUpSession.getSelectedDespatchDetails().getDsnDate()));
					rateAuditDetailsForm.setDsnDate(dSNPopUpSession.getSelectedDespatchDetails().getDsnDate());
				}
				rateAuditDetailsForm.setDsnNumber(dSNPopUpSession.getSelectedDespatchDetails().getBlgBasis());
				rateAuditFilterVO.setCompanyCode(dSNPopUpSession.getSelectedDespatchDetails().getCompanyCode());
				rateAuditFilterVO.setBillingBasis(dSNPopUpSession.getSelectedDespatchDetails().getBlgBasis());				
				rateAuditFilterVO.setCsgDocNum(dSNPopUpSession.getSelectedDespatchDetails().getCsgdocnum());
				rateAuditFilterVO.setCsgSeqNum(dSNPopUpSession.getSelectedDespatchDetails().getCsgseqnum());
				rateAuditFilterVO.setGpaCode(dSNPopUpSession.getSelectedDespatchDetails().getGpaCode());
				
				session.setRateAuditFilterVO(rateAuditFilterVO);
				log.log(Log.FINE, "rateAuditFilterVO----->>>",
						rateAuditFilterVO);
				
		 }
		 	MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
			RateAuditVO rateAuditVO=null;
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
			
				try {
				rateAuditVO=delegate.findListRateAuditDetails(rateAuditFilterVO);

			} catch (BusinessDelegateException e) {
				errors=handleDelegateException(e);
			}
			log.log(Log.FINE, "rateAuditVO----->>>", rateAuditVO);
			if(rateAuditVO!=null && 
					rateAuditVO.getCompanyCode() !=null && 
					rateAuditVO.getCompanyCode().trim().length()>0){				
				session.setParChangeFlag(NO);				
				rateAuditDetailsForm.setListFlag(YES);				
				rateAuditDetailsForm.setOrigin(rateAuditVO.getOrigin().substring(2, 5));
				rateAuditDetailsForm.setDestination(rateAuditVO.getDestination().substring(2, 5));
				rateAuditDetailsForm.setRoute(rateAuditVO.getRoute());
				rateAuditDetailsForm.setConsignmentDocNo(String.valueOf(rateAuditVO.getConDocNum()));
				rateAuditDetailsForm.setGpaCode(rateAuditVO.getGpaCode());
				
				if(!("".equals(rateAuditVO.getDsnStatus()))){
				if((!("").equals(rateAuditVO.getDsnStatus())) && rateAuditVO.getDsnStatus().trim().length()>0){
					rateAuditDetailsForm.setDsnStatus(fetchOneTimeDetails(logonAttributes.getCompanyCode(),rateAuditVO.getDsnStatus()));
				}
				}
				Collection<RateAuditDetailsVO>  rateAuditDetailsVOs = new ArrayList<RateAuditDetailsVO>();
				
				rateAuditDetailsVOs = rateAuditVO.getRateAuditDetails();
				
				for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){
					if(RECIEVEABLE.equals(rateAuditDetailsVO.getPayFlag())){
						
						rateAuditDetailsForm.setNoOfpcs(String.valueOf(rateAuditDetailsVO.getNoPieces()));
						rateAuditDetailsForm.setUpdWt(String.valueOf(rateAuditDetailsVO.getGrsWgt()));
						rateAuditDetailsForm.setCategory(rateAuditDetailsVO.getCategory());
						rateAuditDetailsForm.setSubClass(rateAuditDetailsVO.getSubclass());
						rateAuditDetailsForm.setULD(rateAuditDetailsVO.getUldno());
						rateAuditDetailsForm.setFlightCarCod(rateAuditDetailsVO.getCarrierCode());
						rateAuditDetailsForm.setFlightNo(rateAuditDetailsVO.getFlightno());
						rateAuditDetailsForm.setAuditWgtCharge(String.valueOf(rateAuditDetailsVO.getAudtdWgtCharge()));						
						rateAuditDetailsForm.setApplyAudit(rateAuditDetailsVO.getApplyAudt());
						rateAuditDetailsForm.setBillTo(rateAuditDetailsVO.getBillTO());
						if(YES.equals(rateAuditDetailsVO.getApplyAudt())){
							rateAuditVO.setApplyAutd(rateAuditDetailsVO.getApplyAudt());	
							rateAuditDetailsForm.setApplyAudit(rateAuditDetailsVO.getApplyAudt().toUpperCase());
						}
						rateAuditVO.setDsnDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(rateAuditDetailsForm.getDsnDate()));
						rateAuditVO.setPcs(String.valueOf(rateAuditDetailsVO.getNoPieces()));
						rateAuditVO.setCategory(rateAuditDetailsVO.getCategory());
						rateAuditVO.setRate(rateAuditDetailsVO.getRate());
						rateAuditVO.setCurrency(rateAuditDetailsVO.getCurrency());
						rateAuditVO.getPresentWtCharge().setAmount(rateAuditDetailsVO.getPrsntWgtCharge().getAmount());													
						rateAuditVO.getAuditedWtCharge().setAmount(rateAuditDetailsVO.getAudtdWgtCharge().getAmount());							
											
						log
								.log(
										Log.FINE,
										"$$$$$$$$ rateAuditVO Money Component PresentWtCharge ----->>>",
										rateAuditVO.getPresentWtCharge().getAmount());
						rateAuditVO.setUpdWt(String.valueOf(rateAuditDetailsVO.getGrsWgt()));				
						
						if(rateAuditVO!=null){
							
								Double disp=0.0;
								disp = rateAuditVO.getAuditedWtCharge().getRoundedAmount()- rateAuditVO.getPresentWtCharge().getRoundedAmount();
								log.log(Log.FINE,
										"rateAuditVO.getAuditedWtCharge()==>>",
										rateAuditVO.getAuditedWtCharge());
								log.log(Log.FINE,
										"rateAuditVO.getPresentWtCharge()==>>",
										rateAuditVO.getPresentWtCharge());
								log
										.log(
												Log.FINE,
												"rateAuditVO.getAuditedWtCharge().getAmount()==>>",
												rateAuditVO.getAuditedWtCharge().getAmount());
								log
										.log(
												Log.FINE,
												"rateAuditVO.getPresentWtCharge().getAmount()==>>",
												rateAuditVO.getPresentWtCharge().getAmount());
								if(disp==0){
									   rateAuditVO.setDiscrepancyNo(YES);							   
									}else{
										rateAuditVO.setDiscrepancyNo(NO);
										
										Money discrp = null;
										try {											
											discrp = CurrencyHelper.getMoney(CURRENCY_CODE);											
											discrp.setAmount(disp);							
											rateAuditVO.setDiscrepancyYes(discrp);
											
										} catch (CurrencyException e) {
											log.log(Log.SEVERE,"\n\n$$$$$$$$ CurrencyException Check !!!!\n\n");
											e.getErrorCode();
										}
										}
									
						}											
						}					
				}
			}else if(rateAuditVO==null || (errors!=null && errors.size()>0)){		
				rateAuditDetailsForm.setDsnNumber("");
				rateAuditDetailsForm.setDsnDate("");
				rateAuditDetailsForm.setDsnStatus("");
				rateAuditDetailsForm.setOrigin("");
				rateAuditDetailsForm.setDestination("");
				rateAuditDetailsForm.setGpaCode("");
				rateAuditDetailsForm.setConsignmentDocNo("");
				rateAuditDetailsForm.setRoute("");
				rateAuditDetailsForm.setUpdWt("");
				rateAuditDetailsForm.setCategory("");
				rateAuditDetailsForm.setSubClass("");
				rateAuditDetailsForm.setULD("");
				rateAuditDetailsForm.setFlightCarCod("");
				rateAuditDetailsForm.setFlightNo("");
				rateAuditDetailsForm.setAuditWgtCharge("");
				rateAuditDetailsForm.setDiscrepancy("");
				rateAuditDetailsForm.setApplyAudit("");
				rateAuditDetailsForm.setListFlag(NO);
				session.removeParChangeFlag();
				session.removeRateAuditVO();
				ErrorVO err=new ErrorVO(NO_RESULTS);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(err);
				invocationContext.addAllError(errors);
				invocationContext.target=LIST_FAILURE;
				
			}
		
			session.setRateAuditVO(rateAuditVO);
			/**
			 * @author A-2554
			 * to implement rounding for weight & volume
			 */
			UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
			session.setWeightRoundingVO(unitRoundingVO);
			session.setVolumeRoundingVO(unitRoundingVO);		
			setUnitComponent(logonAttributes.getStationCode(),session); 
			invocationContext.target=LIST_SUCCESS;
			rateAuditDetailsForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);				
		 log.exiting("ListRateAuditDetailsCommand", "execute");
		 
		 
		 	 
	 }
	 /**
	  * 
	  * @param companyCode
	  * @param val
	  * @return
	  */
	 private String fetchOneTimeDetails(String companyCode,String val) {
			log.entering(SCREENID,"fetchOneTimeDetails");
			Map<String, Collection<OneTimeVO>> hashMap =new HashMap<String, Collection<OneTimeVO>>();
			Collection<String> oneTimeList=new ArrayList<String>();
			oneTimeList.add(ONETIME_DSNSTATUS);
			String desc=null;
			SharedDefaultsDelegate sharedDefaultsDelegate =
				new SharedDefaultsDelegate();
			try {
				hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
						oneTimeList);
				Collection<OneTimeVO> oneTimeVOs = hashMap.get(
						ONETIME_DSNSTATUS);
				for (OneTimeVO oneTimeVO : oneTimeVOs) {
					if (oneTimeVO.getFieldValue().equalsIgnoreCase(val)) {
						desc = oneTimeVO.getFieldDescription();
					}
				}
		
			} catch (BusinessDelegateException e) {			
				handleDelegateException(e);
			}
			log.exiting(SCREENID,"fetchOneTimeDetails");
			return desc;
		}
	 
	 
	 /**
		 * @author A-2554
		 * @param stationCode
		 * @param rateAuditDetailsSession
		 */
		private void setUnitComponent(String stationCode,
				RateAuditDetailsSession rateAuditDetailsSession){
				UnitRoundingVO unitRoundingVO = null;
				try{
					log.log(Log.FINE, "\n\n<----STATION CODE IS----------->",
							stationCode);
					unitRoundingVO = UnitFormatter.getStationDefaultUnit(
							stationCode, UnitConstants.WEIGHT);			
					log
							.log(
									Log.FINE,
									"\n\n<----UNIT ROUNDING VO FOR WEIGHT IN SESSION--->",
									unitRoundingVO);
					rateAuditDetailsSession.setWeightRoundingVO(unitRoundingVO);
					unitRoundingVO = UnitFormatter.getStationDefaultUnit(
							stationCode, UnitConstants.VOLUME);
					log
							.log(
									Log.FINE,
									"\n\n<----UNIT ROUNDING VO FOR VOLUME IN SESSION--->",
									unitRoundingVO);
					rateAuditDetailsSession.setVolumeRoundingVO(unitRoundingVO);
					
				   }catch(UnitException unitException) {
						unitException.getErrorCode();
				   }
				
			}


}
