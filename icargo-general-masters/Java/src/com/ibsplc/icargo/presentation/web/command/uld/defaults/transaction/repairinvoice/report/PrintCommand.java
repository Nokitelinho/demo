/*
 * PrintCommand.java Created on Dec 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.repairinvoice.report;



import com.ibsplc.icargo.framework.report.vo.ReportMetaData;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceDetailsVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.RepairInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

 /**
  * 
  * @author A-2122
  *
  */
public class PrintCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST035";
	private Log log = LogFactory.getLogger("Repair Invoice");
	private static final String BLANK = "";
	
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */

public void execute(InvocationContext invocationContext) 
							throws CommandInvocationException {

ApplicationSessionImpl applicationSession = getApplicationSession();
LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
RepairInvoiceForm repairInvoiceForm = 
					(RepairInvoiceForm) invocationContext.screenModel;
Collection<ErrorVO> errorVos= new ArrayList<ErrorVO>();
ULDRepairInvoiceVO uldRepairInvoiceVO = null;
ArrayList<ULDRepairInvoiceDetailsVO> uldRepairDetailsVOs = null;
ArrayList<String> repairInvoiceFilterVOList =new ArrayList<String>();
String invoiceRefNumber="";
String invoiceDate="";
String invoiceToCode="";
String invoiceToName="";
String currency="";

if(repairInvoiceForm.getInvoiceRefNo()!= null && 
					!BLANK.equals(repairInvoiceForm.getInvoiceRefNo()) ){
 invoiceRefNumber=repairInvoiceForm.getInvoiceRefNo();
 log.log(Log.FINE, "IVOICEREFNUM....-------->", invoiceRefNumber);
repairInvoiceFilterVOList.add(invoiceRefNumber);
}
if(repairInvoiceForm.getInvoicedDate()!= null && 
				!BLANK.equals(repairInvoiceForm.getInvoicedDate())){
	invoiceDate=repairInvoiceForm.getInvoicedDate();
	repairInvoiceFilterVOList.add(invoiceDate);
}
if(repairInvoiceForm.getInvoicedToCode()!= null && 
					!BLANK.equals(repairInvoiceForm.getInvoicedToCode())){
	invoiceToCode=repairInvoiceForm.getInvoicedToCode();
	repairInvoiceFilterVOList.add(invoiceToCode);
}
if(repairInvoiceForm.getName()!= null &&
								!BLANK.equals(repairInvoiceForm.getName())){
	invoiceToName=repairInvoiceForm.getName();
	repairInvoiceFilterVOList.add(invoiceToName);
}
//Added by A-7359 for ICRD-248560 starts here
	if(repairInvoiceForm.getAirlineBaseCurrency()!= null &&  !BLANK.equals(repairInvoiceForm.getAirlineBaseCurrency())){
		currency=new StringBuilder("(").append(repairInvoiceForm.getAirlineBaseCurrency()).append(")").toString();
		repairInvoiceFilterVOList.add(currency);
	}
	//Added by A-7359 for ICRD-248560 ends here
log.log(Log.FINE, "InvoiceFilterVO----------->", repairInvoiceFilterVOList);
	try {
		uldRepairInvoiceVO =
				new ULDDefaultsDelegate().findRepairInvoiceDetails(
				 logonAttributesVO.getCompanyCode(),invoiceRefNumber) ;
	}catch(BusinessDelegateException businessDelegateException){
				businessDelegateException.getMessage();
				errorVos = handleDelegateException(businessDelegateException);
	}
     log.log(Log.FINE, "\n\n--Obtained uldRepairInvoiceVO from the server->",
			uldRepairInvoiceVO);
	if(uldRepairInvoiceVO.getULDRepairInvoiceDetailsVOs() != null) {
 		uldRepairDetailsVOs = new ArrayList<ULDRepairInvoiceDetailsVO>
 					(uldRepairInvoiceVO.getULDRepairInvoiceDetailsVOs());
 	}
		Collection<ULDRepairInvoiceDetailsVO> vos =
						new ArrayList<ULDRepairInvoiceDetailsVO>();
		int size = uldRepairDetailsVOs.size();
		log.log(Log.FINE, "Page Sizeis ------->>", size);
		String waivedAmounts[] = repairInvoiceForm.getWaivedAmounts();
    	String actualAmounts[] = repairInvoiceForm.getActualAmounts();
    	
    /*	for(ULDRepairInvoiceDetailsVO uldRepairInvoiceDetailsVO : vos) {
    		getReportSpec().addExtraInfo(uldRepairInvoiceDetailsVO.
    												getDamageRefNumbers());  
    		log.log(Log.FINE,"DamageRefNumbers--------inside printCommand..
    			"+uldRepairInvoiceDetailsVO.getDamageRefNumbers());
    	}
    	*/
    		
		for(int i=0;i<size;i++){
			String invoicedamounttext = TextFormatter.formatDouble(
					(Double.parseDouble(actualAmounts[i]) -
								Double.parseDouble(waivedAmounts[i])),4);
			uldRepairDetailsVOs.get(i).
			setInvoicedAmount(Double.parseDouble(invoicedamounttext));
		/*	ArrayList<Integer> damageRefNo=
		  (ArrayList<Integer>)uldRepairDetailsVOs.get(i).getDamageRefNumbers();
			StringBuilder str = new StringBuilder();
		
			for(int j = 0;j<damageRefNo.size();j++){
			str.append(damageRefNo.get(j));
			str.append(",");
			}
		String val=(str.substring(0,str.length()-1)).toString();
		log.log(Log.FINE,"val------"+val);*/
					
			vos.add(uldRepairDetailsVOs.get(i));		
		}
     
     if(repairInvoiceFilterVOList!=null){
     	getReportSpec().setReportId(REPORT_ID);
 		ReportMetaData parameterMetaData = new ReportMetaData();
 		parameterMetaData.setFieldNames(new String[]{}); 		
    	getReportSpec().addParameterMetaData(parameterMetaData);
		getReportSpec().addParameter(repairInvoiceFilterVOList);
    	}
    	ReportMetaData reportMetaData = new ReportMetaData();

		reportMetaData.setColumnNames(new String[]{
		"ULDNUM", "RPRHED", "RPRSTN", "DMGREFNUM","RPRAMT",
		"WVDAMT","TOTAMT","RPRRMK"});
		reportMetaData.setFieldNames(new String[] {"uldNumber",
				"repairHead","repairStation", "damageRefNumbers",
				"actualAmount","waivedAmount","invoicedAmount",
				"repairRemarks"});
		getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setReportMetaData(reportMetaData);
		getReportSpec().setData(vos);
		getReportSpec().setPreview(true);
		getReportSpec().setResourceBundle("repairinvoice");
log.log(Log.FINE, "\n\n\n----------REPORT_ID----->", REPORT_ID);
log.log(Log.FINE, "\n\n\n----------reportMetaData----->", reportMetaData);
generateReport();

log.log(Log.FINE,"\n\n\n----------AFTER GENERATE REPORT----");
invocationContext.target = getTargetPage();
log.log(Log.FINE, "\n\n\n----------report----->", invocationContext.target);
}

}





