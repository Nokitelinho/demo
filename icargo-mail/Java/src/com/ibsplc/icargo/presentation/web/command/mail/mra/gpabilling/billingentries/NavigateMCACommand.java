/*
 * NavigateMCACommand.java Created on Jul 18, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.billingentries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession;

import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingEntriesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-7929
 *
 */
public class NavigateMCACommand extends BaseCommand{
	private Log log = LogFactory.getLogger(" NavigateMCACommand");
    private static final String CLASS_NAME = "NavigateMCACommand";
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String ACTION_SUCCESS = "navigate_screenload_success";

	private static final String ACTION_FAILURE = "navigate_screenload_failure";
	private static final String SCREENID = "mailtracking.mra.defaults.maintaincca";
	private static final String SCREENID_MRA001 = "mailtracking.mra.gpabilling.billingentries.listgpabillingentries";

	private static final String SIZE_EXCEED = "mailtracking.mra.billingentries.msg.error.sizeexceeded";
	private static final String MIX_OF_BILLED_BILLABLE = "mailtracking.mra.billingentries.msg.error.mixofbilledandbillabe";
	private static final String ONHOLD = "mailtracking.mra.billingentries.msg.error.onhold";
	private static final String NOT_SAME_DSN_GPACODE_ORG_DEST_CAT = "mailtracking.mra.billingentries.msg.error.manydifferntfields";
	private static final String SYS_PARAM_NO_RECORDS = "cra.defaults.recordsinMRA001forAutoMCA";
	//Added as part of ICRD-343570
	private static final String CANNOT_CREATE="mailtracking.mra.billingentries.msg.error.cannotcreate";
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-7929 on 18-Jul-2018
	 * 	Used for 	:
	 *	Parameters	:	@param invocationContext
	 *	Parameters	:	@throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		MaintainCCASession maintainCCASession=getScreenSession(MODULE_NAME,SCREENID);//mra072
		GPABillingEntriesSession gpaSession =(GPABillingEntriesSession)getScreenSession(MODULE_NAME, SCREENID_MRA001);//mra001
		maintainCCASession.removeAllAttributes();
		//LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = null;
		GPABillingEntriesForm gpaBillingEntriesForm=(GPABillingEntriesForm)invocationContext.screenModel;  //mra001
		Page<DocumentBillingDetailsVO> documentBillingDetailsVOs = gpaSession.getGpaBillingDetails();
		Collection<DocumentBillingDetailsVO> selecteddocumentBillingDetailsVO = new ArrayList<DocumentBillingDetailsVO>();
		Collection<CCAdetailsVO> ccaDetailsVOs = null;
		MaintainCCAFilterVO maintainCCAFilterVO = new MaintainCCAFilterVO();
	
		GPABillingEntriesFilterVO gpaBillingEntriesFilterVO = getSearchDetails(gpaBillingEntriesForm);
		gpaSession.setGPABillingEntriesFilterVO(gpaBillingEntriesFilterVO);
		
		 String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		 gpaBillingEntriesFilterVO.setCompanyCode(companyCode);
		 gpaBillingEntriesFilterVO.setPageNumber(Integer.parseInt(gpaBillingEntriesForm.getDisplayPage()));
		 
		 if(DocumentBillingDetailsVO.FLAG_YES.equalsIgnoreCase(gpaBillingEntriesForm.getContractRate())){
			 gpaBillingEntriesFilterVO.setContractRate(MRAConstantsVO.CONTRACT_RATE);
    	 }
    	 if(DocumentBillingDetailsVO.FLAG_YES.equalsIgnoreCase(gpaBillingEntriesForm.getUPURate())){
    		 gpaBillingEntriesFilterVO.setUPURate(MRAConstantsVO.UPU_RATE);
    	 }
    	 
       	 
		/*try{
			documentBillingDetailsVOs =new MailTrackingMRADelegate().findGPABillingEntries(gpaBillingEntriesFilterVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);

		}*/
		// Getting the selected MailBags
		//String[] selectedRows = gpaBillingEntriesForm.getCheck();
		//int size = selectedRows.length;
		
    	//int row = 0;
    	for (DocumentBillingDetailsVO documentBillingDetailsVO : documentBillingDetailsVOs) {
    		//for (int j = 0; j < size; j++) {
    			//if (row == Integer.parseInt(selectedRows[j])) {
    		if(!"VD".equals(documentBillingDetailsVO.getBillingStatus())){//Added this check as part of IASCB-20935 for A-5219
    				selecteddocumentBillingDetailsVO.add(documentBillingDetailsVO);
    		}
    			//}    			
			//}
    		//row++;
    	}
    	int records = gpaSession.getTotalRecords();
       
			//errors = validateFrom(documentBillingDetailsVOs,selectedRows,size);
    	if(selecteddocumentBillingDetailsVO!=null && selecteddocumentBillingDetailsVO.size()>0){
    	for (DocumentBillingDetailsVO documentBillingDetailsVO :selecteddocumentBillingDetailsVO) {
    	
        maintainCCAFilterVO.setCompanyCode(documentBillingDetailsVO.getCompanyCode());
    	maintainCCAFilterVO.setConsignmentDocNum(documentBillingDetailsVO.getCsgDocumentNumber());
		maintainCCAFilterVO.setConsignmentSeqNum(documentBillingDetailsVO.getCsgSequenceNumber());
		maintainCCAFilterVO.setDsnNumber(documentBillingDetailsVO.getDsn());
		maintainCCAFilterVO.setBillingBasis(documentBillingDetailsVO.getBillingBasis());
		maintainCCAFilterVO.setPOACode(documentBillingDetailsVO.getPoaCode());
		
    	}//7531
    	}
    	try{
    		ccaDetailsVOs =new MailTrackingMRADelegate().findCCAdetails(maintainCCAFilterVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);

		}//7531

    	//Added as part of ICRD-343570 starts
    	String ccastatus=null;
    	int count=0;
    	Collection<CCAdetailsVO> cCAdetailsVOs=null;
    	Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
    	try {
			cCAdetailsVOs=new MailTrackingMRADelegate().findCCA(maintainCCAFilterVO);
		} catch (BusinessDelegateException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    	
    	if(cCAdetailsVOs!=null &&cCAdetailsVOs.size()>0){
    		for(CCAdetailsVO ccaVO:cCAdetailsVOs){
    		
    		//ccastatus=ccaVO.getCcaStatus();
    		if("N".equals(ccaVO.getCcaStatus()))
        	{  
    			count=1;
       		       }
    		   }
    		
        	}
    	if(count>0){
    		ErrorVO errorVO = new ErrorVO(CANNOT_CREATE);
			 errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			 formErrors.add(errorVO);
    	}
    	
    	//Added as part of ICRD-343570 ends
    	
    	errors = validateFrom(ccaDetailsVOs,records,selecteddocumentBillingDetailsVO);
		
		if (errors != null && errors.size() > 0) {
			 log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			invocationContext.target =  ACTION_FAILURE;
			return;
		}
		//Added as part of ICRD-343570 starts
		if(formErrors!=null && formErrors.size() >0){
  			 log.log(Log.FINE,"!!!inside errors!= null");
  				invocationContext.addAllError(formErrors);
  				invocationContext.target =  ACTION_FAILURE;
  				return;
  		}
		//Added as part of ICRD-343570 ends
    	maintainCCASession.setDocumentBillingDetailsVOs(selecteddocumentBillingDetailsVO);
    	maintainCCASession.setFromScreen("listbillingentries");
    	maintainCCASession.setGPABillingEntriesFilterVO(gpaBillingEntriesFilterVO);
		invocationContext.target=ACTION_SUCCESS;
		
		
		log.exiting(CLASS_NAME, "execute");
		
	}

	
	private GPABillingEntriesFilterVO getSearchDetails(GPABillingEntriesForm form){
		GPABillingEntriesFilterVO gpaBillingEntriesFilterVO = new GPABillingEntriesFilterVO();
		if(form.getFromDate()!= null && form.getFromDate().trim().length()!=0){
			LocalDate fromDateFormat = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			gpaBillingEntriesFilterVO.setFromDate(fromDateFormat.setDate(form.getFromDate()));
		}
		if(form.getToDate()!= null && form.getToDate().trim().length()!=0){
			LocalDate toDateFormat = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			gpaBillingEntriesFilterVO.setToDate(toDateFormat.setDate(form.getToDate()));
		}
		gpaBillingEntriesFilterVO.setConDocNumber(form.getConsignmentNumber());
		gpaBillingEntriesFilterVO.setBillingStatus(form.getStatus());
		gpaBillingEntriesFilterVO.setGpaCode(form.getGpaCodeFilter());		
		gpaBillingEntriesFilterVO.setMailbagId(form.getMailbagId());
		gpaBillingEntriesFilterVO.setOriginOfficeOfExchange(form.getOriginOfficeOfExchange());
		gpaBillingEntriesFilterVO.setDestinationOfficeOfExchange(form.getDestinationOfficeOfExchange());
		gpaBillingEntriesFilterVO.setMailCategoryCode(form.getMailCategoryCode());
		gpaBillingEntriesFilterVO.setMailSubclass(form.getMailSubclass());
		gpaBillingEntriesFilterVO.setYear(form.getYear());
		gpaBillingEntriesFilterVO.setDsnNumber(form.getDsn());
		gpaBillingEntriesFilterVO.setRsn(form.getRecepatableSerialNumber());
		gpaBillingEntriesFilterVO.setHni(form.getHighestNumberIndicator());
		gpaBillingEntriesFilterVO.setRegInd(form.getRegisteredIndicator());				
		gpaBillingEntriesFilterVO.setContractRate(form.getContractRate());
		gpaBillingEntriesFilterVO.setUPURate(form.getUPURate());
		if(null != form.getDisplayPage() && "".equals(form.getDisplayPage())){
			gpaBillingEntriesFilterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
		}
		return gpaBillingEntriesFilterVO;
	}
	
	
private Collection<ErrorVO> validateFrom(Collection<CCAdetailsVO> ccaDetailsVOs, int records,Collection<DocumentBillingDetailsVO>selecteddocumentBillingDetailsVO)   {
		
		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
		MaintainCCASession maintainCCASession=getScreenSession(MODULE_NAME,SCREENID);
		CCAdetailsVO cCAdetailsVO = new CCAdetailsVO();
		double rate = 0;
		double revRate = 0;
		Money chgGrossWt=null;
		Money othChgGrsWt=null;
		Boolean isDifferentBillingStatus = false;
		Boolean isDifferentGPACode = false;
		Boolean isDifferentOrigin = false;
		Boolean isDifferentDestination = false;  
		Boolean isDifferentDSN = false;
		Boolean isDifferentCategory = false;
		double weight=0;//7531
		
		String maxMailBagCount = null;
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(SYS_PARAM_NO_RECORDS);
		Map<String, String> systemParameters = null;		
		try {
			systemParameters = sharedDefaultsDelegate
			.findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessDelegateException e) {
			log.log(Log.FINE,"Inside CurrencyException.. ");
		}
		if(systemParameters!=null &&systemParameters.size()>0 ){
			
				maxMailBagCount=systemParameters.get(SYS_PARAM_NO_RECORDS);
			
		}
		
		  Object[] obj = { maxMailBagCount };
		  
		if (records > Integer.parseInt(maxMailBagCount)) {
			 ErrorVO errorVO = new ErrorVO(SIZE_EXCEED,obj);		
			  errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			  formErrors.add(errorVO);
		}
		
		
		
		String billingStatus = "";
		String gpaCode = "";
		String origin = "";
		String destination = "";
		String dsn = "";
		String category = "";
		String currency= "";
	    long malseqnum=0;
	    int sernum=0;
	    double netAmountValue = 0.0;
		Money netAmt = null;
	    String revCurrency = "";
	    String revGpaCode ="";
		
    	for (DocumentBillingDetailsVO documentBillingDetailsVO :selecteddocumentBillingDetailsVO) {
		
    		if(billingStatus.length() == 0) {
			    billingStatus = documentBillingDetailsVO.getBillingStatus();
    		}
    		else if(!billingStatus.equals(documentBillingDetailsVO.getBillingStatus())){
				 isDifferentBillingStatus = true;	
			}
    		
			if(gpaCode.length() == 0){
				gpaCode = documentBillingDetailsVO.getGpaCode();
			}
			else if(!gpaCode.equals(documentBillingDetailsVO.getGpaCode())){
				isDifferentGPACode = true;
			}
			
			if(origin.length() == 0){
				origin = documentBillingDetailsVO.getOrigin();
			}
			else if(!origin.equals(documentBillingDetailsVO.getOrigin())){
				isDifferentOrigin = true;
				
			}
			
			if(destination.length() == 0){
				destination = documentBillingDetailsVO.getDestination();
			}
			else if(!destination.equals(documentBillingDetailsVO.getDestination())){
				isDifferentDestination = true;
				
			}
			if(dsn.length() == 0){
				dsn = documentBillingDetailsVO.getDsn();
			}
			else if(!dsn.equals(documentBillingDetailsVO.getDsn())){
				isDifferentDSN = true;
				
			}
			if(category.length() == 0){
				category = documentBillingDetailsVO.getCategory();
			}
			else if(!category.equals(documentBillingDetailsVO.getCategory())){
				isDifferentCategory = true;
				
			}	
			
    	}
    	if(ccaDetailsVOs!=null &&ccaDetailsVOs.size()>0){
		for(CCAdetailsVO ccaVO:ccaDetailsVOs){
		rate = ccaVO.getRate();
		revRate = ccaVO.getRevisedRate();
		currency = ccaVO.getContCurCode();
		revCurrency = ccaVO.getRevContCurCode();
		revGpaCode = ccaVO.getRevGpaCode();
		chgGrossWt=ccaVO.getMailChg();
		othChgGrsWt=ccaVO.getSurChg();
		//weight=ccaVO.getGrossWeight();
		billingStatus=ccaVO.getBillingStatus();
		malseqnum=ccaVO.getMailSequenceNumber();
		sernum=ccaVO.getBlgDtlSeqNum();
		gpaCode=ccaVO.getGpaCode();
		
		try {
			netAmt = CurrencyHelper.getMoney(ccaVO.getContCurCode());
			
		    netAmountValue = (ccaVO.getNetAmount().getAmount()); 
		    netAmt.setAmount(netAmountValue);
		    
		   
		} catch (CurrencyException e) {
			log.log(Log.FINE,"Inside CurrencyException.. ");
		}
		
		}
    	}
    	// Checking whether all mailbags are billabe or billed
    	if(isDifferentBillingStatus)
    	{
    		ErrorVO errorVO = new ErrorVO(MIX_OF_BILLED_BILLABLE);
			 errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			 formErrors.add(errorVO);
    	}
    	if(isDifferentGPACode || isDifferentOrigin || isDifferentDestination || isDifferentDSN || isDifferentCategory ){
    		ErrorVO errorVO = new ErrorVO(NOT_SAME_DSN_GPACODE_ORG_DEST_CAT);
			 errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			 formErrors.add(errorVO);
    	}
    	if("OH".equals(billingStatus)){  
    		 ErrorVO errorVO = new ErrorVO(ONHOLD);
			 errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			 formErrors.add(errorVO);
    	}
    	
    	
    	
    	cCAdetailsVO.setBillingStatus(billingStatus);
    	cCAdetailsVO.setGpaCode(gpaCode);
    	cCAdetailsVO.setCategory(category);
    	cCAdetailsVO.setDsnNo(dsn);
        cCAdetailsVO.setRate(revRate);
        cCAdetailsVO.setContCurCode(currency);
        cCAdetailsVO.setChgGrossWeight(chgGrossWt);
        cCAdetailsVO.setOtherChgGrossWgt(othChgGrsWt);
        cCAdetailsVO.setRevisedRate(revRate);
        cCAdetailsVO.setRevContCurCode(revCurrency);
        cCAdetailsVO.setRevGpaCode(revGpaCode);
       // cCAdetailsVO.setGrossWeight(weight);//7531
       // cCAdetailsVO.setRevGrossWeight(weight);//7531
        cCAdetailsVO.setBlgDtlSeqNum(sernum);
        cCAdetailsVO.setNetAmount(netAmt);
        
    	//cCAdetailsVO.setDocumentBillingDetailsVO(selecteddocumentBillingDetailsVO);
        
    	maintainCCASession.setCCAdetailsVO(cCAdetailsVO);
    	
    	//maintainCCAForm.setBillingStatus(billingStatus);
    	
		return formErrors;
	}

}

//   validation for other airline is pending

