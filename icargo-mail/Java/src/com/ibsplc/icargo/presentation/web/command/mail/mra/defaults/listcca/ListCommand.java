/*
 * ListCommand.java Created on AUG , 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listcca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ListCCAFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListCCASession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * 
 * @author A-3429
 * 
 */
public class ListCommand extends BaseCommand {
	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "ListCommand";

	/**
	 * Target action
	 */
	private static final String LIST_SUCCESS = "list_success";

	/**
	 * MODULE_NAME
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * SCREEN_ID
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.listcca";

	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";
	/**
	 * For Error Tags
	 */
	private static final String NO_DATA_FOUND = "mailtracking.mra.defaults.listcca.err.norecords";

	/**
	 * For Error Tags
	 */
	private static final String INVALID_AIRLINE = "mailtracking.mra.defaults.listcca.err.invalidairline";

	private static final String INVALID_GPACOD = "mailtracking.mra.defaults.listcca.err.invalidgpacode";

	private static final String CLOSE_STATUS = "listCCA";
	
	private static final String WEIGHT_UNIT_ONETIME="mail.mra.defaults.weightunit"; // added by A-9002
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering("CLASS_NAME", "execute");
		ListCCAForm form = (ListCCAForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		ListCCASession session = getScreenSession(MODULE_NAME, SCREEN_ID);
		MaintainCCASession maintainCCASession = (MaintainCCASession) getScreenSession(
				MODULE_NAME, MAINTAINCCA_SCREEN);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ListCCAFilterVO listCCAFilterVO = null;
		if ("maintaincca".equals(session.getListStatus())) {
			listCCAFilterVO = session.getCCAFilterVO();
			form.setDisplayPage("1");
			form.setLastPageNum("0");
			listCCAFilterVO.setCompanyCode(companyCode);
			listCCAFilterVO.setPageNumber(Integer.parseInt(form
					.getDisplayPage()));
			session.setListStatus(null);
		} else {

			listCCAFilterVO = new ListCCAFilterVO();
			listCCAFilterVO.setCompanyCode(companyCode);
			log.log(Log.INFO, "Company code===>", companyCode);
			listCCAFilterVO.setPageNumber(Integer.parseInt(form
					.getDisplayPage()));
			errors = loadFilterFromForm(form, listCCAFilterVO);
			log.log(Log.INFO, "listCCAFilterVO====>", listCCAFilterVO);
			session.setCCAFilterVO(listCCAFilterVO);
		}
		if (errors != null && errors.size() > 0) {
			session.setCCADetailsVOs(null);
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_SUCCESS;
			return;
		}
		MailTrackingMRADelegate mailtrackingMRADelegate = new MailTrackingMRADelegate();
		Page<CCAdetailsVO> ccaDetailVOs  = null;
		Page<CCAdetailsVO> ccaDetailVOs2 = null;
		//Added by A-5201 as part for the ICRD-21098 starts
		if(!"YES".equals(form.getCountTotalFlag()) && session.getTotalRecords().intValue() != 0){
			listCCAFilterVO.setTotalRecords(session.getTotalRecords().intValue());
	    }else
	    	{
	    	listCCAFilterVO.setTotalRecords(-1);
	    	}
		//Added by A-5201 as part for the ICRD-21098 end

		Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
		try {
			ccaDetailVOs = mailtrackingMRADelegate.listCCAs(listCCAFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "inside ccalistcommand====>", ccaDetailVOs);
		if (ccaDetailVOs != null && ccaDetailVOs.size() > 0) {
			log.log(Log.FINE, "CCADetailVos in listcommand VO To Server",
					ccaDetailVOs);
			log.log(Log.FINE, "session.getCCAFilterVO()===>>>>", session.getCCAFilterVO());
			for(CCAdetailsVO ccAdetailsVO: ccaDetailVOs){
				if("A".equals(ccAdetailsVO.getCcaStatus())){
					if(ccAdetailsVO.getRevChgGrossWeight()!=null){
						if(ccAdetailsVO.getRevChgGrossWeight().getAmount()==0 && ccAdetailsVO.getChgGrossWeight()!=null){
							ccAdetailsVO.getRevChgGrossWeight().setAmount(-ccAdetailsVO.getChgGrossWeight().getAmount());
							if(ccAdetailsVO.getRevTax()==0){
								ccAdetailsVO.setRevTax(-(ccAdetailsVO.getTax()));
							}

						}
					}
				}
			}
			
			
			Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes.getCompanyCode());

	        		for(CCAdetailsVO ccAdetailsVO: ccaDetailVOs){
	        			
	        			if ( ccAdetailsVO.getDisplayWgtUnit() != null && oneTimeValues != null && !oneTimeValues.isEmpty() && oneTimeValues.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeValues.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
	        				for (OneTimeVO oneTimeVO : oneTimeValues.get(WEIGHT_UNIT_ONETIME)) { 
	        					if ( oneTimeVO.getFieldValue().equals(ccAdetailsVO.getDisplayWgtUnit()) ) {	
	        						ccAdetailsVO.setDisplayWgtUnit (oneTimeVO.getFieldDescription());
	        					}	        					
	        				}
	        			}		
	                }
						
			//Added for bug ICRD-16263 ends
			session.setCCADetailsVOs(ccaDetailVOs);
			session.setTotalRecords(ccaDetailVOs.getTotalRecordCount()); //Added by A-5201 as part for the ICRD-21098
			/*if(ccaDetailVOs.getActualPageSize()==1){	//removed for icrd-267732			
				listCCAFilterVO=populateFilterVO(ccaDetailVOs,form,session);
			}	*/
			session.setCCAFilterVO(listCCAFilterVO);			
			
		} else {
			ErrorVO errorVO = new ErrorVO(NO_DATA_FOUND);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			session.setCCADetailsVOs(null);
			invocationContext.target = LIST_SUCCESS;
			return;
		}
		String info =  "";
		if("UPDATE".equals(maintainCCASession.getStatusinfo())){
			info = "mailtracking.mra.defaults.listcca.updateinfo";
			invocationContext.addError(new ErrorVO(info));
		}
		if(StringUtils.equals("accept_success",maintainCCASession.getStatusinfo())){
			info = "mailtracking.mra.defaults.listcca.acceptinitiated";
			invocationContext.addError(new ErrorVO(info));
		}
		maintainCCASession.setStatusinfo(null);
		session.setListStatus(null);
		session.setCloseStatus(CLOSE_STATUS);
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = LIST_SUCCESS;

	}

	private ListCCAFilterVO populateFilterVO(Page<CCAdetailsVO> ccaDetailVOs, ListCCAForm form,ListCCASession session) {
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		ListCCAFilterVO listCCAFilterVO = session.getCCAFilterVO();
		for(CCAdetailsVO ccAdetailsVO : ccaDetailVOs){
			listCCAFilterVO.setCcaRefNumber(ccAdetailsVO.getCcaRefNumber());
			listCCAFilterVO.setCcaStatus(ccAdetailsVO.getCcaStatus());
			listCCAFilterVO.setBillingStatus(ccAdetailsVO.getBillingStatus());//Added for ICRD-211662
			listCCAFilterVO.setCcaType(ccAdetailsVO.getCcaType());
			listCCAFilterVO.setDestination(ccAdetailsVO.getDestnCode());
			listCCAFilterVO.setOrigin(ccAdetailsVO.getOriginCode());
			listCCAFilterVO.setDsn(ccAdetailsVO.getDsnNo());
			//listCCAFilterVO.setDsnDate(ccAdetailsVO.getDsDate());
			listCCAFilterVO.setOriginOE(ccAdetailsVO.getOriginOE());
			listCCAFilterVO.setDestinationOE(ccAdetailsVO.getDestinationOE());
			listCCAFilterVO.setCategoryCode(ccAdetailsVO.getCategoryCode());
			listCCAFilterVO.setSubClass(ccAdetailsVO.getSubClass());
			listCCAFilterVO.setYear(ccAdetailsVO.getYear());
			listCCAFilterVO.setRsn(ccAdetailsVO.getRsn());
			listCCAFilterVO.setHni(ccAdetailsVO.getHni());
			listCCAFilterVO.setRegInd(ccAdetailsVO.getRegind());
			listCCAFilterVO.setGpaCode(ccAdetailsVO.getGpaCode());
			listCCAFilterVO.setGpaName(ccAdetailsVO.getGpaName());
			listCCAFilterVO.setFromDate(ccAdetailsVO.getIssueDat());
			form.setToDate(listCCAFilterVO.getToDate().toDisplayDateOnlyFormat().toString());			
//			LocalDate toDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
//			toDate.setDate(form.getToDate());
//			listCCAFilterVO.setToDate(toDate);
			
			
			
		}
		return listCCAFilterVO;
	}

	/**
	 * 
	 * @param form
	 * @param listCCAFilterVO
	 * @return
	 */

	private Collection<ErrorVO> loadFilterFromForm(ListCCAForm form,
			ListCCAFilterVO listCCAFilterVO) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		String companyCode = getApplicationSession().getLogonVO()
		.getCompanyCode();
		listCCAFilterVO.setCcaRefNumber(form.getCcaNum());
		listCCAFilterVO.setCcaType(form.getCcaType());
		listCCAFilterVO.setCcaStatus(form.getCcaStatus());
		listCCAFilterVO.setDsn(form.getDsn());
		listCCAFilterVO.setOriginOE(form.getOriginOfficeOfExchange());
		listCCAFilterVO.setDestinationOE(form.getDestinationOfficeOfExchange());
		listCCAFilterVO.setCategoryCode(form.getMailCategory());
		listCCAFilterVO.setSubClass(form.getSubClass());
		listCCAFilterVO.setYear(form.getYear());
		listCCAFilterVO.setRsn(form.getReceptacleSerialNumber());
		listCCAFilterVO.setHni(form.getHighestNumberIndicator());
		listCCAFilterVO.setRegInd(form.getRegisteredIndicator());
		if(form.getBillingStatus() !=null && 
				form.getBillingStatus().trim().length() >0){
			listCCAFilterVO.setBillingStatus(form.getBillingStatus());	
		}//Added by A-6991 for ICRD-211662
		
//		if (form.getDsnDate() != null && form.getDsnDate().trim().length() > 0) {
//			if (DateUtilities.isValidDate(form.getDsnDate(), "dd-MMM-yyyy")) {
//				LocalDate dsnDate = new LocalDate(getApplicationSession()
//						.getLogonVO().getAirportCode(), Location.ARP, false);
//				dsnDate.setDate(form.getDsnDate());
//				listCCAFilterVO.setDsnDate(dsnDate);
//			}
//
//		} else {
//			listCCAFilterVO.setDsnDate(null);
//		}
		listCCAFilterVO.setCcaStatus(form.getCcaStatus());
		listCCAFilterVO.setIssueParty(form.getIssueParty());
		log.log(Log.INFO, "form.getIssueParty()====>", form.getIssueParty());
		if("ARL".equals(form.getIssueParty())){
			listCCAFilterVO.setArlGpaIndicator("A");
			if (form.getAirlineCode() != null
					&& form.getAirlineCode().trim().length() > 0) {
				if (validateAirlineCode(companyCode, form.getAirlineCode()) == null) {
					log.log(Log.INFO, "inside airline2 second if====>");
					listCCAFilterVO.setAirlineCode(form.getAirlineCode()
							.toUpperCase());
				} else {
					ErrorVO error = new ErrorVO(
							INVALID_AIRLINE,
							new Object[] { form.getAirlineCode().toUpperCase() });
					errors.add(error);
				}
			} else {
				listCCAFilterVO.setAirlineCode(null);
			}
		}else if("GPA".equals(form.getIssueParty())){
			listCCAFilterVO.setArlGpaIndicator("G");
			PostalAdministrationVO postalAdministrationVo = null;
			if (form.getAirlineCode() != null
					&& form.getAirlineCode().trim().length() > 0) {
				try {
					postalAdministrationVo = mailTrackingMRADelegate
					.findPostalAdminDetails(companyCode, form.getAirlineCode());
					log.log(Log.INFO, "inside validate GPA code====>");
				} catch (BusinessDelegateException businessDelegateException) {
					log.log(Log.INFO, "inside catch exception====>");
					errors = handleDelegateException(businessDelegateException);
				}
				if (postalAdministrationVo == null) {
					log.log(Log.INFO, "inside postalAdministrationVO null====>");
					ErrorVO error = new ErrorVO(INVALID_GPACOD, new Object[] { 
							form.getAirlineCode().toUpperCase() });
					errors.add(error);
				}else{
					listCCAFilterVO.setAirlineCode(form.getAirlineCode());
				}
			}
		}else{
			listCCAFilterVO.setIssueParty(null);
			listCCAFilterVO.setAirlineCode(form.getAirlineCode());
		}
		PostalAdministrationVO postalAdministrationVO = null;
		log.log(Log.INFO, "outside validate GPA code====>", form.getGpaCode());
		if (form.getGpaCode() != null && 
				form.getGpaCode().trim().length()>0){
			try {
				postalAdministrationVO = mailTrackingMRADelegate
				.findPostalAdminDetails(companyCode, form.getGpaCode());
				log.log(Log.INFO, "inside validate GPA code====>");
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.INFO, "inside catch exception====>");
				errors = handleDelegateException(businessDelegateException);
			}
			if (postalAdministrationVO == null) {
				log.log(Log.INFO, "inside postalAdministrationVO null====>");
				ErrorVO error = new ErrorVO(INVALID_GPACOD, new Object[] { form
						.getGpaCode().toUpperCase() });
				errors.add(error);
			}else{
				listCCAFilterVO.setGpaCode(form.getGpaCode());
			}
		}
		listCCAFilterVO.setGpaName(form.getGpaName());
		if (((!("").equals(form.getFrmDate())) && form.getFrmDate() != null)) {
			if (!form.getFrmDate().equals(form.getToDate())) {
				if (!DateUtilities.isLessThan(form.getFrmDate(), form
						.getToDate(), "dd-MMM-yyyy")) {
					ErrorVO	error = new ErrorVO(
					"mailtracking.mra.defaults.fromdateearliertodate");
					error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
				}
			}
		}
		if (form.getFrmDate() != null && form.getFrmDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getFrmDate(), "dd-MMM-yyyy")) {
				LocalDate frmDate = new LocalDate(getApplicationSession()
						.getLogonVO().getAirportCode(), Location.ARP, false);
				frmDate.setDate(form.getFrmDate());
				listCCAFilterVO.setFromDate(frmDate);
			}

		} else {
			listCCAFilterVO.setFromDate(null);
		}
		if (form.getToDate() != null && form.getToDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getToDate(), "dd-MMM-yyyy")) {
				LocalDate toDate = new LocalDate(getApplicationSession()
						.getLogonVO().getAirportCode(), Location.ARP, false);
				toDate.setDate(form.getToDate());
				listCCAFilterVO.setToDate(toDate);

			}

		} else {
			listCCAFilterVO.setFromDate(null);
		}
		listCCAFilterVO.setOrigin(form.getOrigin());
		listCCAFilterVO.setDestination(form.getDestination());
		
		//Added by A-7540
		if(form.getMcacreationtype() != null && form.getMcacreationtype().trim().length() > 0){
		listCCAFilterVO.setMcacreationtype(form.getMcacreationtype());
		}
		return errors;

	}
	/**
	 * 
	 * @param form
	 * @param companyCode
	 * @param poolOwnerVO
	 * @param airlineTwo
	 * @return
	 */
	public Collection<ErrorVO> validateAirlineCode(String companyCode, String airlineCode) {
		Collection<ErrorVO> errors = null;
		// validate carriercode
		AirlineValidationVO airlineValidationVO = null;
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		if (airlineCode != null && airlineCode.trim().length() > 0) {
			log.log(Log.FINE,
					"inside validateAirlineCodeTwo--------------->>>>",
					airlineCode);
			try {
				airlineValidationVO = airlineDelegate.validateAlphaCode(
						companyCode, airlineCode.toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}

		}
		return errors;
	}
	
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(String companyCode) {
		log.entering(CLASS_NAME,"fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap =new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList=new ArrayList<String>();

		oneTimeList.add(WEIGHT_UNIT_ONETIME);
		
		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException e) {			
			handleDelegateException(e);
		}
		log.exiting(CLASS_NAME,"fetchOneTimeDetails");
		return hashMap;
	}
	

}
