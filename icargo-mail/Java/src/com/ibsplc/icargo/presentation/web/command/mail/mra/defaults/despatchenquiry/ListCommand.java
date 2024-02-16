/*
 * ListCommand.java created on Jul 10, 2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchEnquiryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DespatchEnqSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingInvoiceEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchEnquiryForm;
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
public class ListCommand extends BaseCommand{
	private  Log log = LogFactory.getLogger("MRA DEFAULTS DespatchEnquiry");

	private static final String CLASS_NAME = "ListCommand";

	private static final String SCREEN_SUCCESS = "screenload_success";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.dsnselectpopup";
	private static final String SCREENID = "mailtracking.mra.defaults.despatchenquiry";
	private static final String DESPATCHNUM_MANDATORY = "mailtracking.mra.defaults.despatchenquiry.dsnmandatory";
	private static final String DSN_INVALID = "mailtracking.mra.defaults.despatchenquiry.invaliddsn";
	private static final String NO_RESULTS = "mailtracking.mra.defaults.despatchenquiry.noresultsfound";

	private static final String LIST_FAILURE = "list_failure";
	
	private static final String GPA_BLG_DETAILS = "gpa_blg_details";
	private static final String INTERLINE_BILLING = "interline_billing";
	private static final String FLOWN_DETAILS = "flown_details";
	private static final String OUTSTANDING_BALANCE = "outstanding_balance";
	private static final String ACCOUNTING_DETAILS="accounting_sucess";


	//
	private static final String MODULE = "mailtracking.mra.gpabilling";

	private static final String SCREENID_GPA = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";
	
	// ONE TIME
	private static final String DESPATCHENQTYPE_ONETIME = "mra.defaults.despatchenqtype";	
	private static final String BILLINGSTATUS_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
	private static final String INVOICESTATUS_ONETIME = "mra.gpabilling.invoicestatus";
	private static final String AIRLINE_BILLINGSTATUS_ONETIME = "mra.airlinebilling.billingstatus";

	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		DespatchEnquiryForm despatchEnquiryForm=(DespatchEnquiryForm)invocationContext.screenModel;
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		DSNPopUpVO popUpVO = null;
		GPABillingInvoiceEnquirySession gPABillingInvoiceEnquirySession=null;
		DespatchEnqSession despatchEnqSession=getScreenSession(MODULE_NAME,SCREENID);
		DSNPopUpSession popUpSession=getScreenSession(MODULE_NAME,SCREEN_ID);
		Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes.getCompanyCode());		
		despatchEnqSession.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)oneTimeValues);
		
		log.log(Log.FINE, "Listed-----  ", despatchEnquiryForm.getListed());
		if(despatchEnquiryForm.getListed() != null && 
			("N".equals(despatchEnquiryForm.getListed()) || 
					despatchEnquiryForm.getListed().length() == 0)) {
				despatchEnquiryForm.setDespatchEnqTyp("G");			
		}
		if(despatchEnquiryForm.getFromGPABillingInvoiceEnquiry()!=null && 
				("Y").equals(despatchEnquiryForm.getFromGPABillingInvoiceEnquiry())){
			gPABillingInvoiceEnquirySession=(GPABillingInvoiceEnquirySession)getScreenSession(
					MODULE, SCREENID_GPA);

			String counter = despatchEnquiryForm.getCounter();
			Collection<CN66DetailsVO> cN66DetailsVOs = gPABillingInvoiceEnquirySession
			.getCN66VOs();
			ArrayList<CN66DetailsVO> cN66DetailsVOArraylist = new ArrayList<CN66DetailsVO>(
					cN66DetailsVOs);
			CN66DetailsVO cN66DetailsVO;
			log.log(Log.FINE, "inside *****<<<<counter>>>>----------  ",
					counter);
			cN66DetailsVO= cN66DetailsVOArraylist.get(Integer.parseInt(counter));
			log.log(Log.FINE, "Inside list command... >>", cN66DetailsVO);
			popUpVO=new DSNPopUpVO();
			popUpVO.setCompanyCode(logonAttributes.getCompanyCode());
			popUpVO.setBlgBasis(cN66DetailsVO.getBillingBasis());
			popUpVO.setDsn(cN66DetailsVO.getDsn());
			popUpVO.setDsnDate(cN66DetailsVO.getReceivedDate().toDisplayDateOnlyFormat());
			popUpVO.setCsgdocnum(cN66DetailsVO.getConsDocNo());
			popUpVO.setCsgseqnum(Integer.parseInt(cN66DetailsVO.getConsSeqNo()));

			popUpVO.setGpaCode(cN66DetailsVO.getGpaCode());

			popUpSession.setSelectedDespatchDetails(popUpVO);
			despatchEnquiryForm.setDespatchEnqTyp("G");
			despatchEnquiryForm.setFromGPABillingInvoiceEnquiry(null);
			despatchEnquiryForm.setCloseFlag("frominvoiceEnquiry");
		}
		/*changes from lov to popup*/

		//Added by A-2107 to get session vo set frm unnaccounteddispatches
		else if("fromunnaccounteddispatches".equalsIgnoreCase(despatchEnquiryForm.getFromScreen())){
			popUpVO=despatchEnqSession.getDispatchFilterVO();
			//TODO For time being the Despatch Enquiry Type is Set to show "FLOWN_DETAILS"
			despatchEnquiryForm.setDespatchEnqTyp("F");			
			despatchEnquiryForm.setCloseFlag("fromdispatchEnquiry");
		}
		else if("fromlistproexceptions".equalsIgnoreCase(despatchEnquiryForm.getFromScreen())){

			popUpVO=despatchEnqSession.getDispatchFilterVO();
			log.log(Log.INFO, "inside listproexceptions popUpVO", popUpVO);
			despatchEnquiryForm.setCloseFlag("fromlistprorationexception");
		}else{
			popUpVO=popUpSession.getSelectedDespatchDetails();
		}
		log.log(Log.INFO, "inside list command popupvo ", popUpVO);
		if(popUpVO!=null){
			log.log(Log.INFO, "popUpVO not null... ", popUpVO);
			despatchEnquiryForm.setDespatchNum(popUpVO.getBlgBasis());
			despatchEnquiryForm.setDsnFilterDate(popUpVO.getDsnDate());
		}

		/*change ends*/
		if(("").equals(despatchEnquiryForm.getDespatchNum()) &&
				(despatchEnquiryForm.getDespatchNum().trim().length()==0)){
			ErrorVO err=new ErrorVO(DESPATCHNUM_MANDATORY);
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(err);
			despatchEnquiryForm.setListed("N");
			despatchEnqSession.removeGPABillingDtls();
			removeFormValues(despatchEnquiryForm);
			invocationContext.addAllError(errors);
			invocationContext.target=LIST_FAILURE;
			return;
		}
		
		MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
		DespatchEnquiryVO  despatchEnquiryVO=null;
		if(popUpVO != null) {
			despatchEnqSession.setDispatchFilterVO(popUpVO);
			try {
				despatchEnquiryVO=delegate.findDespatchDetails(popUpVO);
			} catch (BusinessDelegateException e) {
				errors=handleDelegateException(e);
			}
		}
		if(despatchEnquiryVO!=null){
			if(despatchEnquiryVO.getDespatchDate()!=null) {
				despatchEnquiryVO.setStrDespatchDate(despatchEnquiryVO.getDespatchDate().toDisplayDateOnlyFormat());
			}
			despatchEnqSession.setDespatchEnquiryVO(despatchEnquiryVO);
			if(despatchEnquiryForm.getDespatchEnqTyp()!=null && 
					despatchEnquiryForm.getDespatchEnqTyp().trim().length()>0) {
				if("G".equals(despatchEnquiryForm.getDespatchEnqTyp())) {
					invocationContext.target=GPA_BLG_DETAILS;
				}else if("I".equals(despatchEnquiryForm.getDespatchEnqTyp())) {
					invocationContext.target=INTERLINE_BILLING;
				}else if("F".equals(despatchEnquiryForm.getDespatchEnqTyp())) {
					invocationContext.target=FLOWN_DETAILS;
				}else if("O".equals(despatchEnquiryForm.getDespatchEnqTyp())) {
					invocationContext.target=OUTSTANDING_BALANCE;
				}else if("A".equals(despatchEnquiryForm.getDespatchEnqTyp())){	//Modified for icrd-257974
					invocationContext.target=ACCOUNTING_DETAILS;
				}
			}else {
				despatchEnquiryForm.setDespatchEnqTyp("G");
				invocationContext.target=GPA_BLG_DETAILS;
			}			
		}else{
			despatchEnquiryForm.setListed("N");
			removeFormValues(despatchEnquiryForm);
			despatchEnqSession.removeGPABillingDtls();
			ErrorVO err=new ErrorVO(NO_RESULTS);
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(err);
			invocationContext.addAllError(errors);
			invocationContext.target=LIST_FAILURE;

		}

		log.exiting(CLASS_NAME,"execute");
	}
	
	/**
	 * fetchOneTimeDetails
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(String companyCode) {
		log.entering(CLASS_NAME,"fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap =new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList=new ArrayList<String>();
		oneTimeList.add(DESPATCHENQTYPE_ONETIME);
		oneTimeList.add(BILLINGSTATUS_ONETIME);
		oneTimeList.add(INVOICESTATUS_ONETIME);
		oneTimeList.add(AIRLINE_BILLINGSTATUS_ONETIME);
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
	/**
	 * removeFormValues
	 * @param despatchEnquiryForm
	 */
	private void removeFormValues(DespatchEnquiryForm despatchEnquiryForm){
		despatchEnquiryForm.setBlgBasis("");
		despatchEnquiryForm.setCategory("");
		despatchEnquiryForm.setConDocNum("");
		despatchEnquiryForm.setCurrency("");
		despatchEnquiryForm.setDespatchDate("");
		despatchEnquiryForm.setDespatchNum("");
		despatchEnquiryForm.setDsnFilterDate("");
		despatchEnquiryForm.setDstn("");
		despatchEnquiryForm.setFinYear("");
		despatchEnquiryForm.setGpaCode("");
		despatchEnquiryForm.setGpaName("");
		despatchEnquiryForm.setLovClicked("");
		despatchEnquiryForm.setOrigin("");
		despatchEnquiryForm.setRemarks("");
		despatchEnquiryForm.setRoute("");
		despatchEnquiryForm.setStdPcs("");
		despatchEnquiryForm.setStdWt("");
		despatchEnquiryForm.setSubClass("");
	}

}
