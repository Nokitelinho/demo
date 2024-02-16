/*
 * ListCommand.java Created on Feb 28, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewbillingline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewBillingLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewBillingLineForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2398
 *
 */
public class ListCommand extends BaseCommand {

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewbillingline";

	private static final String LISTDETAILS_SUCCESS = "list_success";

	private static final String LISTDETAILS_FAILURE = "list_failure";

	private static final String CLASS_NAME = "ListCommand";

	private static final String PARENT_PAGE = "fromotherpage";
	private static final String KEY_UNTCOD ="mail.mra.gpabilling.untcod";

	/**
	 * Key value for onetime values of Billing line status
	 */
	private static final String KEY_RATE_STATUS_ONETIME =
		"mra.gpabilling.ratestatus";

	/**
	 * Key value for onetime values of Billed Sector.
	 */
	private static final String KEY_BILLED_SECTOR_ONETIME =
        "mailtracking.mra.billingSector";

	private static final String NO_ROWS_RETURNED =
		   "mailtracking.mra.defaults.viewbillinglines.novaluereturned";

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		ViewBillingLineForm form = (ViewBillingLineForm) invocationContext.screenModel;

		ViewBillingLineSession session = (ViewBillingLineSession) getScreenSession(
				MODULE, SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
//		error = validateForm(form);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		Page<BillingLineVO> billingLineDetails = null;
		BillingLineFilterVO billingLineFilterVO = null;

		if(session.getOneTimeVOs() == null){

			setOneTimeValues(session);

		}
		/*
		 * To populate filterVO with values from form
		 */
		if(PARENT_PAGE.equals(form.getFromPage())){

			billingLineFilterVO = session.getBillingLineFilterVO();
			form.setFromPage("");
			if(billingLineFilterVO.getBillingMatrixId() != null &&
					billingLineFilterVO.getBillingMatrixId().trim().length() > 0 ) {
				form.setBillingMatrixID(billingLineFilterVO.getBillingMatrixId().toUpperCase());
			}
			if(billingLineFilterVO.getPoaCode() != null &&
					billingLineFilterVO.getPoaCode().trim().length() > 0 ) {
				form.setPostalAdmin(billingLineFilterVO.getPoaCode().toUpperCase());
			}
			if(billingLineFilterVO.getValidityStartDate() != null){
				LocalDate dateFrom = new LocalDate(LocalDate.NO_STATION,Location.NONE,
						billingLineFilterVO.getValidityStartDate(),false);
				form.setValidFrom(dateFrom.toDisplayDateOnlyFormat());
			}
			if(billingLineFilterVO.getValidityEndDate() != null){
				LocalDate dateToo = new LocalDate(LocalDate.NO_STATION,Location.NONE,
						billingLineFilterVO.getValidityEndDate(),false);
				form.setValidTo(dateToo.toDisplayDateOnlyFormat());
			}
			//Added by A-4809 as part of ICRD-196742... Starts
			if(billingLineFilterVO.getAirlineCode() != null && billingLineFilterVO.getAirlineCode().trim().length() > 0){
				form.setAirline(billingLineFilterVO.getAirlineCode());
			}
			
			if(billingLineFilterVO.getOrigin()!=null && billingLineFilterVO.getOrigin().trim().length() > 0){
				form.setOrigin(billingLineFilterVO.getOrigin());
			}
			if(billingLineFilterVO.getDestination()!=null && billingLineFilterVO.getDestination().trim().length() > 0){
				form.setDestination(billingLineFilterVO.getDestination());
			}
			if(billingLineFilterVO.getOriginLevel()!=null && billingLineFilterVO.getOriginLevel().trim().length() > 0){
				form.setOriginLevel(billingLineFilterVO.getOriginLevel());
			}
			if(billingLineFilterVO.getDestinationLevel()!=null && billingLineFilterVO.getDestinationLevel().trim().length() > 0){
				form.setDestinationLevel(billingLineFilterVO.getDestinationLevel());
			}
			if(billingLineFilterVO.getUplift()!=null && billingLineFilterVO.getUplift().trim().length() > 0){
				form.setUplift(billingLineFilterVO.getUplift());
			}
			if(billingLineFilterVO.getDischarge()!=null && billingLineFilterVO.getDischarge().trim().length() > 0){
				form.setDischarge(billingLineFilterVO.getDischarge());
			}
			if(billingLineFilterVO.getUpliftLevel()!=null && billingLineFilterVO.getUpliftLevel().trim().length() > 0){
				form.setUpliftLevel(billingLineFilterVO.getUpliftLevel());
			}
			if(billingLineFilterVO.getDischargeLevel()!=null && billingLineFilterVO.getDischargeLevel().trim().length() > 0){
				form.setDischargeLevel(billingLineFilterVO.getDischargeLevel());
			}
			if(billingLineFilterVO.getBillingSector()!= null && billingLineFilterVO.getBillingSector().trim().length() > 0){
				form.setBilledSector(billingLineFilterVO.getBillingSector());
			}
			if(billingLineFilterVO.getBillingLineId()>0){
				form.setBillingLineID(String.valueOf(billingLineFilterVO.getBillingLineId()));
			}
			if(billingLineFilterVO.getBillingLineStatus()!= null && billingLineFilterVO.getBillingLineStatus().trim().length() > 0){
				form.setStatus(billingLineFilterVO.getBillingLineStatus());
			}
			if(billingLineFilterVO.getUnitCode()!= null && billingLineFilterVO.getUnitCode().trim().length() > 0){
				form.setUnitCode(billingLineFilterVO.getUnitCode());
			}
/*			if(billingLineFilterVO.getMailClass()!=null && billingLineFilterVO.getMailClass().size()>0 ){
				String mailCls =null;
				for(String mailClass : billingLineFilterVO.getMailClass()){
					mailCls = appendValues(session, "mailtracking.defaults.mailclass", mailClass);
				}
				form.setBillingClass(mailCls.);
			}*/
			//Added by A-4809 as part of ICRD-196742... Ends
		} else {
			billingLineFilterVO =
				populateFilterVO(companyCode,form,invocationContext);
		}
		/*
		 * For PageAware Multi mapper
		 */
		HashMap<String, String> indexMap = null;
		HashMap<String, String> finalMap = null;
		if (session.getIndexMap() != null) {
			indexMap = session.getIndexMap();
		}
		if (indexMap == null) {
			log.log(Log.INFO, "INDEX MAP IS NULL");
			indexMap = new HashMap<String, String>();
			indexMap.put("1", "1");
		}
		int nAbsoluteIndex = 0;
		String displayPage = "1";
		if(!PARENT_PAGE.equals(form.getFromPage())) {
			displayPage = form.getDisplayPage();
		}

		String strAbsoluteIndex = indexMap.get(displayPage);
		if (strAbsoluteIndex != null) {
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}
		billingLineFilterVO.setAbsoluteIndex(nAbsoluteIndex);
		billingLineFilterVO.setPageNumber(Integer.parseInt(displayPage));
//		for PageAware MultiMap---ends
		try{
			log.log(Log.INFO, "The filterVO  is...-->>", billingLineFilterVO);
			//Added by A-4809 as part of ICRD-196742
			session.setBillingLineFilterVO(billingLineFilterVO); 
			billingLineDetails =
				new MailTrackingMRADelegate().findBillingLineValues(billingLineFilterVO);
		}catch(BusinessDelegateException businessDelegateException){
			handleDelegateException(businessDelegateException);
		}
		//Added as part of ICRD-106032
		if(billingLineDetails != null && billingLineDetails.size() >0){
			LocalDate currentDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			for(BillingLineVO billingLineDetail:billingLineDetails){
				if(billingLineDetail.getValidityEndDate().before(currentDate)){
					billingLineDetail.setBillingLineStatus("E");
				}
			}
			
			session.setBillingLineDetails(billingLineDetails);
			log.log(Log.INFO, "Size of Page set to session-->>",
					billingLineDetails.size());
			//Added by A-5497
			session.setTotalRecordsCount(billingLineDetails.getTotalRecordCount());	
		}
		else{
			session.setBillingLineDetails(null);
			ErrorVO err = new ErrorVO(NO_ROWS_RETURNED);
			errors.add(err);
		}
		if(errors.size() == 0) {
			invocationContext.target = LISTDETAILS_SUCCESS;
		} else
		{
			invocationContext.addAllError(errors);
			invocationContext.target = LISTDETAILS_FAILURE;
		}
		finalMap = indexMap;
		if (session.getBillingLineDetails() != null) {
			finalMap = buildIndexMap(indexMap, session
					.getBillingLineDetails());
		}
		session.setIndexMap(finalMap);


		log.log(Log.INFO, "GOING TO PUT FINAL MAP IN SESSION---------*****",
				finalMap);
		log.exiting(CLASS_NAME,"execute method**** **** **** ");

	}


	/**
	 * @param companyCode
	 * @param form
	 * @return
	 */
	private BillingLineFilterVO populateFilterVO(String companyCode, ViewBillingLineForm form,
			InvocationContext ic){

		BillingLineFilterVO filterVO = new BillingLineFilterVO();

		filterVO.setCompanyCode(companyCode);
		if(form.getBillingMatrixID() != null && form.getBillingMatrixID().trim().length() > 0){
			filterVO.setBillingMatrixId(form.getBillingMatrixID().trim().toUpperCase());
		}

		if(form.getAirline() != null && form.getAirline().trim().length() > 0){
			filterVO.setAirlineCode(form.getAirline().trim().toUpperCase());
		}

		if(form.getPostalAdmin() != null && form.getPostalAdmin().trim().length() > 0){
			filterVO.setPoaCode(form.getPostalAdmin().trim().toUpperCase());
		}

		if(form.getBilledSector()!= null && form.getBilledSector().trim().length() > 0){
			filterVO.setBillingSector(form.getBilledSector().trim().toUpperCase());
		}

		if(form.getStatus()!= null && form.getStatus().trim().length() > 0){
			filterVO.setBillingLineStatus(form.getStatus().trim().toUpperCase());
		}
		//Added for ICRD-162338 starts
		if(form.getBillingLineID()!=null&&form.getBillingLineID().trim().length()>0){
			filterVO.setBillingLineId(Integer.parseInt(form.getBillingLineID()));
		}
		//Added for ICRD-162338 ends
		if(form.getValidFrom()!= null && form.getValidFrom().trim().length() > 0){

			LocalDate date = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			date.setDate(form.getValidFrom().trim());
			log.log(Log.INFO, "From date------>>", form.getValidFrom());
			filterVO.setValidityStartDate(date.toCalendar());
		}
		if(form.getValidTo()!= null && form.getValidTo().trim().length() > 0){

			LocalDate date = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			date.setDate(form.getValidTo().trim());
			log.log(Log.INFO, "To date------>>", form.getValidTo());
			filterVO.setValidityEndDate(date.toCalendar());
		}
		

		if(form.getOrigin()!=null && form.getOrigin().trim().length() > 0){
			filterVO.setOrigin(form.getOrigin());
		}
		if(form.getDestination()!=null && form.getDestination().trim().length() > 0){
			filterVO.setDestination(form.getDestination());
		}
		if(form.getOriginLevel()!=null && form.getOriginLevel().trim().length() > 0){
			filterVO.setOriginLevel(form.getOriginLevel());
		}
		if(form.getDestinationLevel()!=null && form.getDestinationLevel().trim().length() > 0){
			filterVO.setDestinationLevel(form.getDestinationLevel());
		}
		if(form.getUplift()!=null && form.getUplift().trim().length() > 0){
			filterVO.setUplift(form.getUplift());
		}
		if(form.getDischarge()!=null && form.getDischarge().trim().length() > 0){
			filterVO.setDischarge(form.getDischarge());
		}
		if(form.getUpliftLevel()!=null && form.getUpliftLevel().trim().length() > 0){
			filterVO.setUpliftLevel(form.getUpliftLevel());
		}
		if(form.getDischargeLevel()!=null && form.getDischargeLevel().trim().length() > 0){
			filterVO.setDischargeLevel(form.getDischargeLevel());
		}
		if(form.getUldType()!= null && form.getUldType().trim().length() > 0){
			log.log(Log.INFO, "Uldtypes r.....--->", extractValues(form.getUldType().trim()));
			filterVO.setUldType(extractValues(form.getUldType().trim()));
		}
		if(form.getCategory()!= null && form.getCategory().trim().length() > 0){
			log.log(Log.INFO, "Categories r.....--->", extractValues(form.getCategory().trim()));
			Collection<OneTimeVO> vos =
				 getOneTimeValues("mailtracking.defaults.mailcategory",ic);
			 filterVO.setMailCategoryCode(getOneTimeCodes(
					 extractValues(form.getCategory().trim()),vos));

			 if(filterVO.getMailCategoryCode()==null || filterVO.getMailCategoryCode().isEmpty())
				 {
				 filterVO.setMailCategoryCode( extractValues(form.getCategory().trim()));        
				 }        

		}
		if(form.getBillingClass()!= null && form.getBillingClass().trim().length() > 0){
			log.log(Log.INFO, "CLASSES r.....--->", extractValues(form.getBillingClass().trim()));
			Collection<OneTimeVO> vos =
				 getOneTimeValues("mailtracking.defaults.mailclass",ic);
			Collection<String> values=getOneTimeCodes(extractValues(form.getBillingClass().trim()),vos);
			if(values!=null &&	values.size()>0 ){
			 filterVO.setMailClass(values);
		}else {
			filterVO.setMailClass(extractValues(form.getBillingClass().trim()));
		}
		}
		if(form.getSubClass()!= null && form.getSubClass().trim().length() > 0){
			log.log(Log.INFO, "Subclasses r.....--->", extractValues(form.getSubClass().trim()));
			filterVO.setMailSubclass(extractValues(form.getSubClass().trim()));
		}
		
		if(form.getUnitCode()!= null && form.getUnitCode().trim().length() > 0){
			filterVO.setUnitCode(form.getUnitCode().trim().toUpperCase());
		}
		return filterVO;
	}

	/**
	 * @param str
	 * @return
	 */
	private ArrayList<String> extractValues(String str){
		ArrayList<String> values = new ArrayList<String>();
		String [] strArray = str.split(",");
		if(strArray.length > 0){
			for(String val : strArray){
				values.add(val);
			}
		}
		return values;
	}
	/**
	 * Method to bulid the hashmap to maintain absoluteindex
	 *
	 * @param existingMap HashMap<String, String>
	 * @param page
	 *            Page
	 * @return HashMap<String, String>
	 */
	private HashMap<String, String> buildIndexMap(
			HashMap<String, String> existingMap, Page page) {
		HashMap<String, String> finalMap = existingMap;
		String indexPage = String.valueOf((page.getPageNumber() + 1));
		boolean pageExits = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				pageExits = true;
			}
		}
		if (!pageExits) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}

		return finalMap;
	}
	private Collection<String> getOneTimeCodes(ArrayList<String> values,Collection<OneTimeVO> vos){
		ArrayList<String> returnCollection = new ArrayList<String>();
		for(String val : values){
			log.log(Log.INFO, "Val is-->", val);
			for(OneTimeVO vo : vos){
				log.log(Log.INFO, "Onetime VO is --->", vo);
			if(val.equals(vo.getFieldDescription().toUpperCase())){
				returnCollection.add(vo.getFieldValue());
				break;
			}

			}

		}
		log.log(Log.INFO, "Return coll is--->", returnCollection);
		return returnCollection;

	}

	/**
	 * @param key
	 * @param ic
	 * @return
	 */
	private Collection<OneTimeVO> getOneTimeValues(String key,InvocationContext ic){
		Map<String,Collection<OneTimeVO>> oneTimeHashMap 								= null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();

		String companyCode = logonAttributes.getCompanyCode().toUpperCase();

		/** adding attributes to map for passing to SharedDefaultsDelegate */
		oneTimeActiveStatusList.add(key);


		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException e) {
    		e.getMessage();
			ic.addAllError(handleDelegateException( e ));
		}

		log.log(Log.INFO, "  the oneTimeHashMap after server call is ",
				oneTimeHashMap);
		return oneTimeHashMap.get(key);


	}

	private Collection<ErrorVO> setOneTimeValues(ViewBillingLineSession session){
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Map oneTimeHashMap 								= null;
		Collection<String> oneTimeActiveStatusList 		= new ArrayList<String>();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();

		String companyCode = logonAttributes.getCompanyCode().toUpperCase();

		/** adding attributes to map for passing to SharedDefaultsDelegate */
		oneTimeActiveStatusList.add(KEY_RATE_STATUS_ONETIME);
		oneTimeActiveStatusList.add(KEY_BILLED_SECTOR_ONETIME);
		oneTimeActiveStatusList.add(KEY_UNTCOD);

		try {
			/** getting collections of OneTimeVOs */
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode, oneTimeActiveStatusList);
		} catch (BusinessDelegateException e) {
    		e.getMessage();
			errors=handleDelegateException( e );
		}

		log.log(Log.INFO, "  the oneTimeHashMap after server call is ",
				oneTimeHashMap);
		session.setOneTimeVOs( (HashMap<String, Collection<OneTimeVO>>)oneTimeHashMap);

	return errors;
	}

/*	private String appendValues(ViewBillingLineSession session,String onetimeCode,String value){
		StringBuilder st = null;
		Collection<OneTimeVO> oneTimes = new ArrayList<OneTimeVO>();
		if(onetimeCode!=null && onetimeCode.trim().length()>0){
		oneTimes = session.getOneTimeVOs().get(onetimeCode);
		if(oneTimes!=null && oneTimes.size()>0){
			for(OneTimeVO onetime :oneTimes){
				if(value!=null && value.trim().length()>0){
					if(value.equals(onetime.getFieldValue())){
						st = new StringBuilder(onetime.getFieldDescription()).append(",");
					}
				}
			}
		}
		}
		return st.toString();
	}*/
}
