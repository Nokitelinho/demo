/*
 * ListCommand.java Created on Jan 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.cn51cn66;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66VO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51CN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51CN66Form;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 * @author a-2270
 * 
 */
public class ListCommand extends BaseCommand {

	private static final String MODULE = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51cn66";

	private static final String LISTDETAILS_SUCCESS = "list_success";

	private static final String LISTDETAILS_FAILURE = "list_failure";

	private static final String CLASS_NAME = "ListCommand";

	// private static final String MATCH = "exact_match_earlier";

	private static final String INVOKE_SCREEN = "listCN51";

	private static final String FROM_SCREEN = "listinvoice";

	private static final String FROM_CRA = "fromCraPostingDetails";

	private static final String KEY_NO_RESULTS_FOUND = "mailtracking.mra.gpabilling.cn51cn66.noresultsfound";

	private static final String KEY_NO_INVNUM = "mailtracking.mra.gpabilling.cn51cn66.noinvnum";

	private static final String KEY_NO_GPACODE = "mailtracking.mra.gpabilling.cn51cn66.nogpacode";

	private static final String CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";

	private static final String BILLINGSTATUS_ONETIME = "mra.gpabilling.billingstatus";

	private static final String INVOICEGSTATUS_ONETIME = "mra.gpabilling.invoicestatus";
	

	private static final String FROM_MCA = "fromMCA";
	
	private static final String NEXT_STRING = "NEXT";
	/* A-5273 Added for ICRD-38227
	 * Constant for Parent screen Invoice Print
	 */ 
	private static final String PARENTSCREEN_INVOICEPRINT = "Invoice_Print";

	private Log log = LogFactory.getLogger("MRA_GPABILLING");
	// Added by A-8527 for IASCB-22915
	private static final String UNITCODDISP_ONTIME ="mail.mra.defaults.weightunit";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		ListCN51CN66Form form = (ListCN51CN66Form) invocationContext.screenModel;
		ListCN51CN66Session session = (ListCN51CN66Session) getScreenSession(
				MODULE, SCREENID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		Page<CN51DetailsVO> cN51DetailsVOs = null;
		Page<CN66DetailsVO> cN66DetailsVOs = null;
		double totalBilledAmt = 0.0;
		String description = "";

		// Collection<CN51DetailsVO> newCN51DetailsVOs = new
		// ArrayList<CN51DetailsVO>();
		// CN51DetailsVO newCN51Vo = null;
		CN51CN66VO cN51CN66Vo = new CN51CN66VO();
		CN51CN66FilterVO filterVO = null;
		CN51CN66FilterVO filterVO1 = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		session.removeCN51CN66VO();

		log.log(Log.FINE, "InvokingScreen in list...", form.getInvokingScreen());
		if (INVOKE_SCREEN.equals(form.getInvokingScreen())
				|| FROM_CRA.equals(form.getInvokingScreen())) {

			filterVO = session.getCN51CN66FilterVO();
			filterVO.setCompanyCode(companyCode.toUpperCase());
			populateFormFields(form, filterVO);
			form.setAirlineCode(getApplicationSession().getLogonVO()
					.getOwnAirlineCode());
			session.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) fetchOneTimeDetails(companyCode));
			log.log(Log.FINE,
					"INVOKE_SCREEN try : Calling findCN51CN66Details", filterVO);

		} else if (FROM_SCREEN.equals(form.getInvokingScreen())) {

			filterVO = session.getCN51CN66FilterVO();
			filterVO.setCompanyCode(companyCode.toUpperCase());
			populateFormFields(form, filterVO);
			form.setAirlineCode(getApplicationSession().getLogonVO()
					.getOwnAirlineCode());
			session.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) fetchOneTimeDetails(companyCode));
			log.log(Log.FINE,
					"Listgpabilling invoice enauiry screen try : Calling findCN51CN66Details---",
					filterVO);
		}
		/**
		 * @author A-4810 This code is added as part of icrd-13639 This is to
		 *         relist the invoice number details after getting from the
		 *         session
		 */
		else if (FROM_MCA.equals(form.getInvokingScreen())) {
			filterVO = session.getCN51CN66FilterVO();
			filterVO.setCompanyCode(companyCode.toUpperCase());
			populateFormFields(form, filterVO);
			form.setAirlineCode(getApplicationSession().getLogonVO()
					.getOwnAirlineCode());
			session.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) fetchOneTimeDetails(companyCode));
			log.log(Log.FINE,
					"List mail correction advice screen try : Calling findCN51CN66Details---",
					filterVO);
			form.setInvokingScreen("");

		}
		/*A-5273 Added for ICRD-38227
		 * when the screen navigates from Invoice print to GPA Billing
		 */
		else if (PARENTSCREEN_INVOICEPRINT.equals(form.getInvokingScreen())) {
			log.log(Log.FINE,"-->Invoice print to GPA Billing-CN51CN66 navigation<--");	
			filterVO = session.getCN51CN66FilterVO();
			filterVO.setCompanyCode(companyCode.toUpperCase());
			populateFormFields(form, filterVO);
			form.setAirlineCode(getApplicationSession().getLogonVO().getOwnAirlineCode());
			session.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) fetchOneTimeDetails(companyCode));
			log.log(Log.FINE, "Invoice Print -> CN51CN66FilterVO-->",filterVO);
		}else {

			
			      
			filterVO = new CN51CN66FilterVO();
			
			filterVO.setCompanyCode(companyCode.toUpperCase());
			//
			errors = validateForm(form, companyCode);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = LISTDETAILS_FAILURE;
				return;
			}
			//

			if (form.getGpaCode() != null
					&& form.getGpaCode().trim().length() > 0) {
				filterVO.setGpaCode(form.getGpaCode().toUpperCase());
			}
			if (form.getGpaName() != null
					&& form.getGpaName().trim().length() > 0) {
				filterVO.setGpaName(form.getGpaName().toUpperCase());

			}

			if (form.getInvoiceNumber() != null
					&& form.getInvoiceNumber().trim().length() > 0) {
				filterVO.setInvoiceNumber(form.getInvoiceNumber().toUpperCase());

			} else {

				errors.add(new ErrorVO(KEY_NO_INVNUM));
				// invocationContext.addAllError(errors);
				// invocationContext.target = LISTDETAILS_FAILURE;
				// return;
			}

		}
		
		if(session.getCN51CN66FilterVO()!=null)
		{
			filterVO1=session.getCN51CN66FilterVO();
			filterVO1.setCategory(form.getCategory());
		}
		else  
		{  
		filterVO1 = new CN51CN66FilterVO();
		}
		filterVO1.setCompanyCode(companyCode.toUpperCase());
		filterVO1.setGpaCode(form.getGpaCode());
		if (form.getGpaName() != null && form.getGpaName().trim().length() > 0) {
			filterVO1.setGpaName(form.getGpaName());
		}
		filterVO1.setInvoiceNumber(form.getInvoiceNumber());
		
		//Added by A-8464 for ICRD-280626
		String cn66tabcategory = form.getCategory();
		String cn66taborigin   = form.getOrigin();
		String cn66tabdestination = form.getDestination();
		String cn66tabdsnNum = form.getDsnNumber();
		/*Added null check as part of ICRD-305205*/
		if (cn66tabcategory != null && ("").equals(cn66tabcategory) && cn66taborigin != null
				&& ("").equals(cn66taborigin) && cn66tabdestination != null && ("").equals(cn66tabdestination)
				&& cn66tabdsnNum != null && ("").equals(cn66tabdsnNum)) 
		{
			filterVO1.setCategory("");
			filterVO1.setOrgin("");
			filterVO1.setDestination("");
			filterVO1.setDsnNumber("");
		}
		
		session.setCN51CN66FilterVO(filterVO1);

		/*
		 * calling MailTrackingMRADelegate
		 */
		log.log(Log.FINE, "displaypage",form.getDisplayPage(),form.getDisplayPageCN66());
		filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
		//Modified for ICRD-123395 starts
		if(NEXT_STRING.equals(form.getCheckButton())){
		filterVO.setPageNumberCn66(Integer.parseInt(form.getDisplayPageCN66()));
		}else{
			filterVO.setPageNumberCn66(Integer.parseInt(form.getDisplayPage()));
		}
		//Modified for ICRD-123395 ends
		try {
			log.log(Log.FINE, "Inside try : Calling findCN51CN66Details");
			cN51CN66Vo = new MailTrackingMRADelegate()
					.findCN51CN66Details(filterVO);
			log.log(Log.FINE, "CN51CN66Vo from Server:--> ", cN51CN66Vo);

		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			log.log(Log.FINE, "caught");
		}
		form.setDisplayPage("1");
		form.setDisplayPageCN66("1");
		form.setCheckButton("");//Added for ICRD-123395
        //Added by A-6991 for ICRD-211662 to fetch Invoice Status Starts
		Map<String, Collection<OneTimeVO>> hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(INVOICEGSTATUS_ONETIME);
		// Added by A-8527 for IASCB-22915
		oneTimeList.add(UNITCODDISP_ONTIME);
		try {
			hashMap = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, oneTimeList);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		
		if (hashMap != null && cN51CN66Vo != null && cN51CN66Vo.getCn51DetailsVOs() 
				!= null && !cN51CN66Vo.getCn51DetailsVOs().isEmpty()) { 
    
			String invStatus=cN51CN66Vo.getCn51DetailsVOs().get(0).getInvoiceStatus();
			form.setFileName(cN51CN66Vo.getCn51DetailsVOs().get(0).getFileName());
			form.setGpaType(cN51CN66Vo.getCn51DetailsVOs().get(0).getGpaType());
			for(OneTimeVO onetimeVO :hashMap.get(INVOICEGSTATUS_ONETIME)){
			
					if(invStatus.equalsIgnoreCase(onetimeVO.getFieldValue())){	
						String invoiceStatus=onetimeVO.getFieldDescription();
						cN51CN66Vo.setInvoiceStatus(invoiceStatus);
						form.setInvoiceStatus(invStatus);
						form.setInvStatusDesc(invoiceStatus);
					}
			}
			// Added by A-8527 for IASCB-22915 Starts
			for (CN51DetailsVO cn51VO : cN51CN66Vo.getCn51DetailsVOs()) {
				String unitcode = cn51VO.getUnitCode();
					for(OneTimeVO onetimeVO :hashMap.get(UNITCODDISP_ONTIME)){
						if (onetimeVO.getFieldValue().equalsIgnoreCase(unitcode)) {
							cn51VO.setUnitCode(onetimeVO.getFieldDescription());
						}
					}


			}
			if(cN51CN66Vo.getCn66DetailsVOs()!=null){
			for (CN66DetailsVO cn66VO : cN51CN66Vo.getCn66DetailsVOs()) {
				String unitcode = cn66VO.getUnitcode();
					for(OneTimeVO onetimeVO :hashMap.get(UNITCODDISP_ONTIME)){
						if (onetimeVO.getFieldValue().equalsIgnoreCase(unitcode)) {
							cn66VO.setUnitcode(onetimeVO.getFieldDescription());
						}
					}


			}}
			// Added by A-8527 for IASCB-22915 Ends
		}
		
		//Added by A-6991 for ICRD-211662 to fetch Invoice Status Ends
		cN51DetailsVOs = cN51CN66Vo.getCn51DetailsVOs();
		cN66DetailsVOs = cN51CN66Vo.getCn66DetailsVOs();

		
		// for a sector,mail category code and rate if one is LC then other will
		// be in CP..
		// constructing new Vo for display purposes

		// if(cN51DetailsVOs !=null && cN51DetailsVOs.size()>0){
		//
		// log.log(Log.FINE, "inside 1");
		// // outer for loop starts here..
		// for(CN51DetailsVO cn51Vo : cN51DetailsVOs){
		// log.log(Log.FINE, "inside for 1");
		//
		// String catCode = cn51Vo.getMailCategoryCode();
		// double rate = cn51Vo.getApplicableRate();
		// double amount = cn51Vo.getTotalAmount();
		// double weight = cn51Vo.getTotalWeight();
		// String origin = cn51Vo.getOrigin();
		// String destination = cn51Vo.getDestination();
		// //cn51Vo.setCheckFlag(MATCH);
		//
		// String subClass = cn51Vo.getMailSubclass().equals("CP") ? "LC" :
		// "CP";
		// log.log(Log.FINE, "mail sub class>>"+cn51Vo.getMailSubclass());//cp
		// log.log(Log.FINE, "subClass inside for>>"+subClass);
		// newCN51Vo = new CN51DetailsVO();
		//
		// //inner loop starts here
		// for(CN51DetailsVO innerCN51Vo : cN51DetailsVOs){
		//
		// log.log(Log.FINE, "inside for 2");
		// if( rate == innerCN51Vo.getApplicableRate() &&
		// innerCN51Vo.getOrigin().equals(origin) &&
		// innerCN51Vo.getMailSubclass().equals(subClass) &&
		// innerCN51Vo.getDestination().equals(destination)&&
		// innerCN51Vo.getMailCategoryCode().equals(catCode)&&
		// !MATCH.equals(innerCN51Vo.getCheckFlag())){
		// log.log(Log.FINE, "inside for match1");
		// if(innerCN51Vo.getMailSubclass().equals("CP")){
		// log.log(Log.FINE, "inside CP");
		// innerCN51Vo.setCheckFlag(MATCH);
		// newCN51Vo.setMailCategoryCode(catCode);
		// newCN51Vo.setApplicableRateCP(innerCN51Vo.getApplicableRate());
		// newCN51Vo.setTotalAmountCP(innerCN51Vo.getTotalAmount());
		// newCN51Vo.setTotalWeightCP(innerCN51Vo.getTotalWeight());
		// newCN51Vo.setSector
		// (innerCN51Vo.getOrigin().concat("/").concat(innerCN51Vo.getDestination()).
		// concat("(").concat(innerCN51Vo.getMailCategoryCode()).concat(")"));
		// break;
		//
		// }
		// else{
		// log.log(Log.FINE, "inside  LC");
		// innerCN51Vo.setCheckFlag(MATCH);
		// newCN51Vo.setMailCategoryCode(catCode);
		// newCN51Vo.setApplicableRateLC(innerCN51Vo.getApplicableRate());
		// newCN51Vo.setTotalAmountLC(innerCN51Vo.getTotalAmount());
		// newCN51Vo.setTotalWeightLC(innerCN51Vo.getTotalWeight());
		// newCN51Vo.setSector
		// (innerCN51Vo.getOrigin().concat("/").concat(innerCN51Vo.getDestination()).
		// concat("(").concat(innerCN51Vo.getMailCategoryCode()).concat(")"));
		// break;
		// }
		//
		// }
		//
		// }
		// //outer for
		//
		// if(cn51Vo.getMailSubclass().equals("CP")){
		// log.log(Log.FINE, "inside outer CP");
		// cn51Vo.setCheckFlag(MATCH);
		// newCN51Vo.setMailCategoryCode(catCode);
		// newCN51Vo.setApplicableRateCP(rate);
		// newCN51Vo.setTotalAmountCP(amount);
		// newCN51Vo.setTotalWeightCP(weight);
		// newCN51Vo.setSector
		// (origin.concat("/").concat(destination).
		// concat("(").concat(catCode).concat(")"));
		//
		// }
		// else{
		// log.log(Log.FINE, "inside outer Lc");
		// cn51Vo.setCheckFlag(MATCH);
		// newCN51Vo.setMailCategoryCode(catCode);
		// newCN51Vo.setApplicableRateLC(rate);
		// newCN51Vo.setTotalAmountLC(amount);
		// newCN51Vo.setTotalWeightLC(weight);
		// newCN51Vo.setSector
		// (origin.concat("/").concat(destination).concat("(").concat(catCode).concat(")"));
		// }
		// log.log(Log.FINE, "newCN51Vo"+newCN51Vo);
		// newCN51DetailsVOs.add(newCN51Vo);
		//
		// }
		// log.log(Log.FINE, "inside set");
		// cN51CN66Vo.setCn51DetailsVOs(newCN51DetailsVOs);
		// }
		// //

		// hash map

		// if(cN51DetailsVOs !=null && cN51DetailsVOs.size()>0){
		// Map<String, CN51DetailsVO> cn51Map = new HashMap<String,
		// CN51DetailsVO>();
		// for (CN51DetailsVO cn51Vo : cN51DetailsVOs) {
		// StringBuilder key = new StringBuilder();
		// key.append(cn51Vo.getApplicableRate());
		// key.append(cn51Vo.getOrigin());
		// key.append(cn51Vo.getDestination());
		// key.append(cn51Vo.getMailCategoryCode());
		// String voKey = key.toString();
		//
		// if (!cn51Map.containsKey(voKey)) {
		// cn51Map.put(voKey, cn51Vo);
		// if(cn51Vo.getMailSubclass().equals("CP")){
		// log.log(Log.FINE, "inside CP");
		// cn51Vo.setMailCategoryCode(cn51Vo.getMailCategoryCode());
		// cn51Vo.setApplicableRateCP(cn51Vo.getApplicableRate());
		// cn51Vo.setTotalAmountCP(cn51Vo.getTotalAmount());
		// cn51Vo.setTotalWeightCP(cn51Vo.getTotalWeight());
		// cn51Vo.setSector
		// (cn51Vo.getOrigin().concat("/").concat(cn51Vo.getDestination()).
		// concat("(").concat(cn51Vo.getMailCategoryCode()).concat(")"));
		//
		// }
		// else{
		// log.log(Log.FINE, "inside LC");
		// cn51Vo.setMailCategoryCode(cn51Vo.getMailCategoryCode());
		// cn51Vo.setApplicableRateLC(cn51Vo.getApplicableRate());
		// cn51Vo.setTotalAmountLC(cn51Vo.getTotalAmount());
		// cn51Vo.setTotalWeightLC(cn51Vo.getTotalWeight());
		// cn51Vo.setSector
		// (cn51Vo.getOrigin().concat("/").concat(cn51Vo.getDestination()).
		// concat("(").concat(cn51Vo.getMailCategoryCode()).concat(")"));
		//
		// }
		// } else {
		// CN51DetailsVO cn51detailsVO = cn51Map.get(voKey);
		// //detailsVO.setApplicableRate(cn51Vo.getApplicableRate())
		//
		// if(cn51Vo.getMailSubclass().equals("CP")){
		// log.log(Log.FINE, "inside CP");
		// cn51detailsVO.setMailCategoryCode(cn51Vo.getMailCategoryCode());
		// cn51detailsVO.setApplicableRateCP(cn51Vo.getApplicableRate());
		// cn51detailsVO.setTotalAmountCP(cn51Vo.getTotalAmount());
		// cn51detailsVO.setTotalWeightCP(cn51Vo.getTotalWeight());
		// cn51detailsVO.setSector
		// (cn51Vo.getOrigin().concat("/").concat(cn51Vo.getDestination()).
		// concat("(").concat(cn51Vo.getMailCategoryCode()).concat(")"));
		//
		// }
		// else{
		// log.log(Log.FINE, "inside LC");
		// cn51detailsVO.setMailCategoryCode(cn51Vo.getMailCategoryCode());
		// cn51detailsVO.setApplicableRateLC(cn51Vo.getApplicableRate());
		// cn51detailsVO.setTotalAmountLC(cn51Vo.getTotalAmount());
		// cn51detailsVO.setTotalWeightLC(cn51Vo.getTotalWeight());
		// cn51detailsVO.setSector
		// (cn51Vo.getOrigin().concat("/").concat(cn51Vo.getDestination()).
		// concat("(").concat(cn51Vo.getMailCategoryCode()).concat(")"));
		//
		// }
		// }
		// }
		// Collection<CN51DetailsVO> cn51collection = cn51Map.values();
		// cN51CN66Vo.setCn51DetailsVOs(cn51collection);
		// }

		// new code

		HashMap<String, Collection<OneTimeVO>> oneTimeHash = session
				.getOneTimeVOs();
		Collection<OneTimeVO> oneTimeVOs = oneTimeHash.get(CATEGORY_ONETIME);

		// HashMap<String, Collection<AccountingEntryDetailsVO>>
		// accountingEntryDetailsVO = null;
		HashMap<String, Collection<CN51DetailsVO>> cn51DetailsVO = null;
		if (cN51CN66Vo.getCn51DetailsVOs() != null
				&& cN51CN66Vo.getCn51DetailsVOs().size() > 0) {
			cn51DetailsVO = new HashMap<String, Collection<CN51DetailsVO>>();

			// accountingEntryDetailsVO= new HashMap<String,
			// Collection<AccountingEntryDetailsVO>>();
			for (CN51DetailsVO cn51VO : cN51CN66Vo.getCn51DetailsVOs()) {
				/*
				 * if(cn51VO.getTotalBilledAmount()!=null){
				 * 
				 * form.setTotalBilledAmount(cn51VO.getTotalBilledAmount().getAmount
				 * ()); }
				 */
				if (cn51VO.getTotalAmount() != null
						&& cn51VO.getTotalAmount().getAmount() != 0.0) {
					log.log(Log.INFO, "cn51VO.getTotalAmount().getAmount()",
							cn51VO.getTotalAmount().getAmount());
					BigDecimal bigDecimalamount = new BigDecimal(cn51VO
							.getTotalAmount().getAmount()).setScale(2,
							RoundingMode.HALF_UP);
					log.log(Log.INFO, "bigDecimalamount.doubleValue()",
							bigDecimalamount.doubleValue());
					totalBilledAmt = totalBilledAmt
							+ (bigDecimalamount.doubleValue());
				}

				String origin = cn51VO.getOrigin();
				String destination = cn51VO.getDestination();
				String category = cn51VO.getMailCategoryCode();
				if (oneTimeVOs != null && oneTimeVOs.size() > 0) {
					for (OneTimeVO oneTimeVo : oneTimeVOs) {
						if (oneTimeVo.getFieldValue()
								.equalsIgnoreCase(category)) {
							cn51VO.setMailCategoryCodedisp(oneTimeVo
									.getFieldDescription());
						}
					}
				}
				if (origin != null && destination != null) {
					String keyForCompare = origin.concat("-")
							.concat(destination).concat("");
					log.log(Log.INFO, "keyForCompare-->", keyForCompare);
					cn51VO.setSector(keyForCompare);
					log.log(Log.INFO, "origin-->", origin);

					boolean isAccountName = cn51DetailsVO
							.containsKey(keyForCompare);
					log.log(Log.INFO, "isAccountName present-->", isAccountName);
					if (isAccountName) {
						Collection<CN51DetailsVO> collnExists = cn51DetailsVO
								.get(keyForCompare);

						collnExists.add(cn51VO);
					} else {
						Collection<CN51DetailsVO> collnToAdd = new ArrayList<CN51DetailsVO>();
						collnToAdd.add(cn51VO);
						// cN51DetailsVOs.add(cn51VO);
						cn51DetailsVO.put(keyForCompare, collnToAdd);
					}

				}
			}

			log.log(Log.INFO, "totalBilledAmt", totalBilledAmt);
			form.setTotalBilledAmount(totalBilledAmt);
			log.log(Log.INFO, "hashMap:>>>", cn51DetailsVO);
			// session.setCN51DetailsVOs(cN51DetailsVOs);
			log.log(Log.INFO, "ACTION_SUCCESS********");
		} else {
			log.log(Log.INFO, "ACTION_FAILURE in adding to hashmap........");

		}

		// for setting the invocation context in case of success
		if (cN51DetailsVOs != null && cN51DetailsVOs.size() > 0
				|| cN66DetailsVOs != null && cN66DetailsVOs.size() > 0) {
			log.log(Log.FINE, "CN66VO frm server>>>", cN66DetailsVOs);
			session.setCN51DetailsVOs(cN51DetailsVOs);
			session.setCN66VOs(cN66DetailsVOs);
			session.setCN51CN66VO(cN51CN66Vo);
			form.setBtnStatus("");
			log.log(Log.FINE, "CN51VO frm server>>>",
					session.getCN51DetailsVOs());
			invocationContext.target = LISTDETAILS_SUCCESS;
		}
		// for setting the invouaction context in case of failure
		if ((cN51DetailsVOs == null || cN51DetailsVOs.size() == 0)
				&& (cN66DetailsVOs == null || cN66DetailsVOs.size() == 0)) {
			log.log(Log.FINE, "results from Server is ::null::");
			errors.add(new ErrorVO(KEY_NO_RESULTS_FOUND));
			invocationContext.addAllError(errors);
			session.setTotalRecords(0);
			session.setTotalRecordsUnlabelled(0);
			form.setBtnStatus("N");
			form.setSaveBtnStatus("N");
			session.setCN51DetailsVOs(cN51DetailsVOs);
			session.setCN66VOs(cN66DetailsVOs);
			session.setCN51CN66VO(cN51CN66Vo);
			invocationContext.target = LISTDETAILS_FAILURE;
		} else {
			form.setSaveBtnStatus("X");
		}
		// form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		form.setDestination("");
		form.setOrigin("");
		form.setCategory("");
		if(filterVO.getDsnNumber() != null &&  filterVO.getDsnNumber().trim().length() >0){
			form.setDsnNumber(filterVO.getDsnNumber());
		}else{
			form.setDsnNumber("");	
		}
		log.log(Log.FINE, "form.setSaveBtnStatus", form.getSaveBtnStatus());
		log.log(Log.FINE, "form.setBtnStatus()", form.getBtnStatus());
		log.exiting(CLASS_NAME, "execute");

	}

	// private String gettCodeDescription(String category){
	//
	//
	// }

	/**
	 * populates form fields from filtervo
	 * 
	 * @param form
	 * @param filterVO
	 */
	private void populateFormFields(ListCN51CN66Form form,
			CN51CN66FilterVO filterVO) {

		form.setGpaCode(filterVO.getGpaCode());
		form.setInvoiceNumber(filterVO.getInvoiceNumber());
	}

	/**
	 * Helper method to get the one time data.
	 * 
	 * @param companyCode
	 *            String
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(
			String companyCode) {

		log.entering(CLASS_NAME, "fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(CATEGORY_ONETIME);
		oneTimeList.add(BILLINGSTATUS_ONETIME);

		try {
			hashMap = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, oneTimeList);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}

		log.exiting(CLASS_NAME, "fetchOneTimeDetails");
		return hashMap;
	}

	/**
	 * Validating the Filter parameters
	 * 
	 * @param form
	 * @param companyCode
	 * @return
	 */

	private Collection<ErrorVO> validateForm(ListCN51CN66Form form,
			String companyCode) {

		log.entering(CLASS_NAME, "validateForm");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO cn51cn66ErrorVO = null;

		String gpaCode = form.getGpaCode();
		String invNumber = form.getInvoiceNumber();
		/* CC Collector Code validation */
		
		if (gpaCode == null ||("").equals(gpaCode.trim())) {
			log.log(Log.FINE, "gpaCode is null");
			cn51cn66ErrorVO = new ErrorVO(KEY_NO_GPACODE);
			if (errors == null) {
				errors = new ArrayList<ErrorVO>();
			}
			errors.add(cn51cn66ErrorVO);
		}

		if (invNumber == null || ("").equals(invNumber.trim())) {
			cn51cn66ErrorVO = new ErrorVO(KEY_NO_INVNUM);
			if (errors == null) {
				errors = new ArrayList<ErrorVO>();
			}
			errors.add(cn51cn66ErrorVO);
		}
		log.exiting(CLASS_NAME, "validateForm");
		return errors;
	}

}
