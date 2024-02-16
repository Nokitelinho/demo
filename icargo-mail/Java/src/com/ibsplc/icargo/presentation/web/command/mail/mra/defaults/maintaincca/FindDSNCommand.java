/**
 * FindDSNCommand.java Created on May 25-2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DSNLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author a-4823
 *
 */
public class FindDSNCommand extends BaseCommand{

	/*
	 * Logger for the Mailtracking Mra
	 */
	private  Log log = LogFactory.getLogger("MAILTRACKING MRA");

	private static final String CLASS_NAME = "FindDSNCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.dsnlov";

	private static final String SCREEN_SUCCESS = "screenload_success";

	private static final String FRMDTMNDTRY = "mailtracking.mra.defaults.maintaincca.frmdatemandatory";

	private static final String TODTMNDTRY = "mailtracking.mra.defaults.maintaincca.todatemandatory";

	private static final String DATERANGEEXCEEDS = "mailtracking.mra.defaults.maintaincca.err.daterangeexceeds";
	
	private static final String DATFLDIDR = "DSNLovDateRange";//Added as part of ICRD-201846
	/*
	 * Parameter code for system parameter - level for data import to mra
	 */
	private static final String LEVEL_FOR_DATA_IMPORT_TO_MRA="mailtracking.defaults.DsnLevelImportToMRA";
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		DSNLovForm dsnLovForm = (DSNLovForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Map<String, String> systemParameterValues = null;
		//boolean filterExist = false;
		try {
			/** getting collections of OneTimeVOs */

			systemParameterValues=new SharedDefaultsDelegate().findSystemParameterByCodes(getSystemParameterTypes());
		} catch (BusinessDelegateException e) {
    		e.getMessage();
			errors=handleDelegateException( e );
		}
		for (Map.Entry<String, String> entry : systemParameterValues.entrySet()) {
			dsnLovForm.setParameterValue(entry.getValue());

    		}
		// Author A-5125(CM) For ICRD-16160
	if(("dsnScreenLoad").equals(dsnLovForm.getLovActionType()))
		{   if(dsnLovForm.getLovDescriptionTxtFieldName()!=null)
		 {
			//dsnLovForm.setLovActionType("dsnList");


			if(dsnLovForm.getLovDescriptionTxtFieldName().trim().length()==29 || dsnLovForm.getLovDescriptionTxtFieldName().trim().length()==20)
			{   dsnLovForm.setOrigin(dsnLovForm.getLovDescriptionTxtFieldName().substring(0, 6));
			    dsnLovForm.setDestination(dsnLovForm.getLovDescriptionTxtFieldName().substring(6, 12));
			    dsnLovForm.setCategory(dsnLovForm.getLovDescriptionTxtFieldName().substring(12, 13));
			    dsnLovForm.setSubclass(dsnLovForm.getLovDescriptionTxtFieldName().substring(13, 15));
			    dsnLovForm.setYear(dsnLovForm.getLovDescriptionTxtFieldName().substring(15, 16));
				dsnLovForm.setDsnNumber(dsnLovForm.getLovDescriptionTxtFieldName().substring(16, 20))	;
				//filterExist = true;
			}
			if(dsnLovForm.getLovDescriptionTxtFieldName().trim().length()==29)
			{
				dsnLovForm.setRecepatableSerialNumber(dsnLovForm.getLovDescriptionTxtFieldName().substring(20, 23))	;
				dsnLovForm.setHighestNumberIndicator(dsnLovForm.getLovDescriptionTxtFieldName().substring(23, 24))	;
				dsnLovForm.setRegisteredIndicator(dsnLovForm.getLovDescriptionTxtFieldName().substring(24, 25))	;
				//filterExist = true;
			}
			if(dsnLovForm.getLovDescriptionTxtFieldName().trim().length()==4)
			{
				dsnLovForm.setDsnNumber(dsnLovForm.getLovDescriptionTxtFieldName());
				dsnLovForm.setLovDescriptionTxtFieldName("");//Added for ICRD-202495
				//filterExist = true;
			}
			/*dsnLovForm.setLovDescriptionTxtFieldName("");
			invocationContext.target=SCREEN_SUCCESS;
			return ;*/
			//if(!filterExist){
				//Added for ICRD-201846 starts
				String dateRange="180";
				try{
					dateRange=new SharedDefaultsDelegate().findDateRange(logonAttributes.getCompanyCode(),DATFLDIDR);
				} catch (BusinessDelegateException e) {
					errors=handleDelegateException(e);
				}
				//Added for ICRD-201846 ends
				LocalDate localDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				String toDate = localDate.toDisplayDateOnlyFormat();
				localDate = localDate.addDays(-1*(Integer.parseInt(dateRange)));
				String fromDate = localDate.toDisplayDateOnlyFormat();
				dsnLovForm.setFromDate(fromDate.toUpperCase());
				dsnLovForm.setToDate(toDate.toUpperCase());
			//}
		 }
		else
		{
			invocationContext.target=SCREEN_SUCCESS;
			return ;
		}
		}

		/*if(("dsnList").equals(dsnLovForm.getLovActionType())&& !(("dsnScreenLoad").equals(dsnLovForm.getLovActionType()))&& dsnLovForm.getLovActionType()!=null)
		{*/
		//ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		//LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		Collection<ErrorVO> errorVOs=new ArrayList<ErrorVO>();
		CCAdetailsVO ccAdetailsVO = new CCAdetailsVO();
		Page<CCAdetailsVO> ccAdetailsVOs = new Page<CCAdetailsVO>(new ArrayList<CCAdetailsVO>(),0,0,0,0,0,false);
		ccAdetailsVO=populateccAdetailsVO(dsnLovForm);
		//if(!filterExist){
		errorVOs=validateForm(dsnLovForm);
		//}
		if(errorVOs!=null && errorVOs.size()>0){
			invocationContext.addAllError(errorVOs);
			invocationContext.target = SCREEN_SUCCESS;
			return;
		}
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		try {
			if(!(("Y").equals(dsnLovForm.getMultiselect()))){
				dsnLovForm.setSelectedValues("");
			}
			ccAdetailsVOs=mailTrackingMRADelegate.findDSNLov(ccAdetailsVO, Integer.parseInt(dsnLovForm.getDisplayPage()));
		} catch (BusinessDelegateException e) {

			log.log(Log.FINE,  "BusinessDelegateException");
		} catch (NumberFormatException e) {

			log.log(Log.FINE,  "NumberFormatException");
		}
		dsnLovForm.setDsnLovPage(ccAdetailsVOs);
		invocationContext.target=SCREEN_SUCCESS;

	//}
	}
	/**
	 *
	 * @param dsnLovForm
	 * @return
	 */
	private Collection<ErrorVO> validateForm(DSNLovForm dsnLovForm) {
		Collection<ErrorVO> errorVOs=new ArrayList<ErrorVO>();
		ErrorVO errorVO=null;

		//Commented by A-5214 as part of the ICRD-70883 starts
		//if((dsnLovForm.getDsnNumber()==null || dsnLovForm.getDsnNumber().trim().length()<=0) &&
			//	(dsnLovForm.getCondocno()==null||dsnLovForm.getCondocno().trim().length()<=0)){
		//Uncommented by A-6991 for BUG ICRD-196771 starts
					if(dsnLovForm.getFromDate()==null || dsnLovForm.getFromDate().trim().length()<=0){
						errorVOs.add(new ErrorVO(FRMDTMNDTRY));

					}
					if(dsnLovForm.getToDate()==null || dsnLovForm.getToDate().trim().length()<=0){
						errorVOs.add(new ErrorVO(TODTMNDTRY));

					}


		 if ((((errorVOs == null) || (errorVOs.size() == 0))) &&
			        (dsnLovForm.getFromDate() != null) && (dsnLovForm.getToDate() != null) &&
			        (!(dsnLovForm.getFromDate().equals(dsnLovForm.getToDate()))) &&
			        (!(DateUtilities.isLessThan(dsnLovForm.getFromDate(), dsnLovForm.getToDate(), "dd-MMM-yyyy")))) {
			        this.log.log(3, "FROM DATE GRTR THAN TO DATE=========>");
			        errorVO = new ErrorVO(DATERANGEEXCEEDS);
			        errorVOs.add(errorVO);
			      }
		//Uncommented by A-6991 for BUG ICRD-196771 Ends
		//Commented by A-5214 as part of the ICRD-70883 ends

		/*if(dsnLovForm.getFromDate()!=null && dsnLovForm.getFromDate().trim().length()>0 && dsnLovForm.getToDate()!=null && dsnLovForm.getToDate().trim().length()>0){
			if(DateUtilities.getDifferenceInMonths(dsnLovForm.getFromDate(),dsnLovForm.getToDate())> 6){
				errorVOs.add(new ErrorVO(DATERANGEEXCEEDS));

			}
		}*/

		return errorVOs;
	}
	//}
	/**
	 *
	 * @param dsnLovForm
	 * @return
	 */
	private CCAdetailsVO populateccAdetailsVO(DSNLovForm dsnLovForm) {
		CCAdetailsVO ccAdetailFilterVO=new CCAdetailsVO();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		ccAdetailFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		ccAdetailFilterVO.setCsgDocumentNumber(dsnLovForm.getCondocno());
		ccAdetailFilterVO.setOriginCode(dsnLovForm.getOrigin());
		ccAdetailFilterVO.setDestnCode(dsnLovForm.getDestination());
		ccAdetailFilterVO.setCategoryCode(dsnLovForm.getCategory());
		ccAdetailFilterVO.setSubClass(dsnLovForm.getSubclass());
		ccAdetailFilterVO.setYear(dsnLovForm.getYear());
		ccAdetailFilterVO.setDsnNo(dsnLovForm.getDsnNumber());
		ccAdetailFilterVO.setBillingPeriodFrom((dsnLovForm.getFromDate()));
		ccAdetailFilterVO.setBillingPeriodTo((dsnLovForm.getToDate()));
		ccAdetailFilterVO.setRsn(dsnLovForm.getRecepatableSerialNumber());
		ccAdetailFilterVO.setHni(dsnLovForm.getHighestNumberIndicator());
		ccAdetailFilterVO.setRegind(dsnLovForm.getRegisteredIndicator());

		return ccAdetailFilterVO;
	}
	private Collection<String> getSystemParameterTypes(){

    	ArrayList<String> systemparameterTypes = new ArrayList<String>();

    	systemparameterTypes.add(LEVEL_FOR_DATA_IMPORT_TO_MRA);


    	return systemparameterTypes;
	}
}
