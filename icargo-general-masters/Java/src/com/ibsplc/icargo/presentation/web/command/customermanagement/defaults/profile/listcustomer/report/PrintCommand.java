/*
 * PrintCommand.java Created on Sep 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer.report;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerListFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */

public class PrintCommand extends AbstractPrintCommand {

    private static final String REPORT_ID = "RPTLST044";
	private Log log = LogFactory.getLogger("List Customer");
	private static final String BLANK = "";
	private static final String PRINT_FAILURE = "print_failure";
	private static final String MODULENAME = "customermanagement.defaults";
	private static final String SCREENID = "customermanagement.defaults.customerlisting";
	private static final String PRODUCTCODE = "customermanagement";
	private static final String CUSTOMER_STATUS = "customer.RegisterCustomer.status";

	private static final String SUBPRODUCTCODE = "defaults";
	private static final String ACTIONPRINT = "printListCustomerReport";

	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */

public void execute(InvocationContext invocationContext) throws CommandInvocationException {

String companyCode = getApplicationSession().getLogonVO().getCompanyCode();  
Collection<ErrorVO> errorVos= new ArrayList<ErrorVO>();

ListCustomerForm form = (ListCustomerForm) invocationContext.screenModel;
ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);

Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
HashMap<String, String> indexMap = null;
HashMap<String, String> finalMap = null;
CustomerListFilterVO filterVO = new CustomerListFilterVO();
if (session.getIndexMap() != null) {
	indexMap = session.getIndexMap();
} else {
	indexMap = new HashMap<String, String>();
	indexMap.put("1", "1");
}
int nAbsoluteIndex = 0;
String strAbsoluteIndex = (String) indexMap.get(form
		.getDisplayPageNum());
form.setAbsoluteIndex(strAbsoluteIndex);
if (strAbsoluteIndex != null) {
	nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
	filterVO.setAbsoluteIndex(nAbsoluteIndex);
}
CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
Page<CustomerVO> customerVOs = null;

filterVO.setCompanyCode(companyCode);
	if(form.getLocationType()!=null && form.getLocationValue()!=null){
		filterVO.setLocationType(form.getLocationType());
		filterVO.setLocationValue(form.getLocationValue());
	}

	if (form.getCustCode() != null && !BLANK.equals(form.getCustCode())) {
		filterVO.setCustomerCode(form.getCustCode().toUpperCase());
	}
	if (form.getLoyaltyName() != null
			&& form.getLoyaltyName().trim().length() > 0) {
		filterVO.setLoyaltyProgramme(form.getLoyaltyName()
				.toUpperCase());
	}
	if (!"ALL".equals(form.getStatus())) {
		filterVO.setActiveStatus(form.getStatus());
	}
	/* Modified by A-6800 on 12Apr2017 for ICRD-203083:starts */
	//filterVO.setPageNumber(Integer.parseInt(form.getDisplayPageNum()));
	/* To get all the records when report is generated from any page */
		filterVO.setPageNumber(1);
	/* Modified by A-6800 on 12Apr2017 for ICRD-203083:ends */
		
	if (form.getCustStation() != null
			&& form.getCustStation().trim().length() > 0) {
		filterVO.setStationCode(form.getCustStation().toUpperCase());
	}
	/* Added by A-6800 on 12Apr2017 for ICRD-203083:starts */
	filterVO.setForPrintListCustomers(true);
	if(session.getTotalRecords()!=null){
		filterVO.setTotalRecords(session.getTotalRecords());
	}else{
		filterVO.setTotalRecords(-1);
	}
	/* Added by A-6800 on 12Apr2017 for ICRD-203083:ends */
		
	CustomerListFilterVO customerListFilterVOSession = session.getListFilterVO();
	if(customerListFilterVOSession != null){
		
		if(customerListFilterVOSession.getPrivilegeLevel() != null){
			filterVO.setPrivilegeLevel(customerListFilterVOSession.getPrivilegeLevel());
			
			if(customerListFilterVOSession.getPrivilegeLevelValue() != null){
				filterVO.setPrivilegeLevelValue(customerListFilterVOSession.getPrivilegeLevelValue());		
			}
		}	
		
		if(customerListFilterVOSession.getCountryPrivilege() != null
				&& customerListFilterVOSession.getCountryCodes() != null){
			
			filterVO.setCountryPrivilege(customerListFilterVOSession.getCountryPrivilege());
			filterVO.setCountryCodes(customerListFilterVOSession.getCountryCodes());
		}
		
	}
	
	log.log(Log.FINE, "filter vo to server-------->", filterVO);
		/*

try {
	customerVOs = delegate.listCustomerDetails(filterVO);
} catch (BusinessDelegateException ex) {
//printStackTrrace()();
	handleDelegateException(ex);
}
    	if(customerVOs == null || customerVOs.size()==0){
			ErrorVO errorVO = new ErrorVO(
			"customermanagement.defaults.custlisting.msg.err.norecords");
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = "normal-report-error-jsp";
			return;
		}

    	Map<String,Collection<OneTimeVO>> oneTimeCollection = fetchScreenLoadDetails(companyCode);
    	Collection<OneTimeVO> custSta = oneTimeCollection.get(CUSTOMER_STATUS);
		log.log(Log.FINE,"\n\n\n----------Obtained cust status----->"+custSta);
		for(CustomerVO customerVO :customerVOs ){
			for(OneTimeVO oneTimeVO:custSta){
				log.log(Log.FINE,"\n\n\n----------Cust Status "+customerVO.getStatus());
				if(customerVO.getStatus() != null &&
						customerVO.getStatus().equals(oneTimeVO.getFieldValue())){
					customerVO.setStatus(oneTimeVO.getFieldDescription());
				}

			}}



		log.log(Log.FINE,"\n\n\n----------customerVOs----->"+customerVOs);  */
		getReportSpec().setReportId(REPORT_ID);
		/*ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "CUSCOD",
				"CUSNAM", "STNCOD", "CUSSTA", "PHNONE",
				"FAXNUM"});
		reportMetaData.setFieldNames(new String[] { "customerCode",
				"customerName", "stationCode", "status",
				"telephone", "fax"});
		getReportSpec().setReportMetaData(reportMetaData);
		getReportSpec().setData(customerVOs);*/

		  getReportSpec().setReportId(REPORT_ID);
		 /*code added to change print command to new framwork */
		  getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
		  getReportSpec().addFilterValue(filterVO);
		  log.log(Log.FINE, "FLTERVO ARE==========>>", filterVO);
		getReportSpec().setAction(ACTIONPRINT);


			getReportSpec().setPreview(true);
			getReportSpec().setProductCode(PRODUCTCODE);
			getReportSpec().setSubProductCode(SUBPRODUCTCODE);
			getReportSpec().setResourceBundle("listcustomerform");
			log.log(Log.FINE, "\n\n\n----------REPORT_ID----->", REPORT_ID);
		generateReport();

		log.log(Log.FINE,"\n\n\n----------AFTER GENERATE REPORT----");
		invocationContext.target = getTargetPage();
		log.log(Log.FINE, "\n\n\n----------report----->",
				invocationContext.target);
		}

		/*private Map<String,Collection<OneTimeVO>> fetchScreenLoadDetails(String companyCode){
			Map<String,Collection<OneTimeVO>> hashMap = new
			HashMap<String,Collection<OneTimeVO>>();
			Collection<String> oneTimeList = new ArrayList<String>();
			oneTimeList.add(CUSTOMER_STATUS);
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			Collection<ErrorVO> exception = new ArrayList<ErrorVO>();
			try{
				hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);

			}catch(BusinessDelegateException ex){
//printStackTrrace()();
				exception = handleDelegateException(ex);
			}
			return hashMap;
		}*/



		}







