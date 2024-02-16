/*
 * PrintCommand.java Created on Oct 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.printmailtag;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PrintMailTagForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1876
 *
 */
public class PrintCommand extends AbstractPrintCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
    private static final String REPORT_ID = "RPRMTK085";
    private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ACTION = "generateMailTag";
	private static final String NORMAL_REPORT_ERROR = "normal-report-error-jsp";
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("PrintCommand","execute");
    	  
    	PrintMailTagForm printMailTagForm = 
    		(PrintMailTagForm)invocationContext.screenModel;
    	    	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		/*
		 * For Mail Tag 
		 */
		String[] mailOOE = printMailTagForm.getOriginOE();
		String[] mailDOE = printMailTagForm.getDestnOE();
		String[] mailCat = printMailTagForm.getCategory();
		String[] mailSC = printMailTagForm.getSubClass();
		String[] mailYr = printMailTagForm.getYear();
		String[] mailDSN = printMailTagForm.getDsn();
		String[] mailRSN = printMailTagForm.getRsn();
		String[] mailHNI = printMailTagForm.getHni();
		String[] mailRI = printMailTagForm.getRi();
		String[] mailWt = printMailTagForm.getWeight();
		Measure[] mailWgt=printMailTagForm.getMailWtMeasure();//added by A-7371
		String[] mailbagId = printMailTagForm.getMailbagId();
		
		String[] mailOpFlag = printMailTagForm.getOpFlag();
		
		ArrayList<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		for(int index=0; index<mailOpFlag.length;index++){
			log.log(Log.FINE, "mailOpFlag :", mailOpFlag, index);
			if(!"NOOP".equals(mailOpFlag[index])){
				MailbagVO newMailbagVO = new MailbagVO();
				newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
		    	newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
		    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
		    	newMailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		    	newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
		    	newMailbagVO.setArrivedFlag("N");
		    	newMailbagVO.setDeliveredFlag("N");
				newMailbagVO.setOperationalFlag(mailOpFlag[index]);
				
				if(mailOOE != null) {
					if(mailOOE[index] != null && !("".equals(mailOOE[index]))) {
						newMailbagVO.setOoe(mailOOE[index].toUpperCase());
					}
				}
				if(mailDOE != null) {
					if(mailDOE[index] != null && !("".equals(mailDOE[index]))) {
						newMailbagVO.setDoe(mailDOE[index].toUpperCase());
					}
				}
				if(mailCat != null) {
					if(mailCat[index] != null && !("".equals(mailCat[index]))) {
						newMailbagVO.setMailCategoryCode(mailCat[index]);
					}
				}
				if(mailSC != null) {
					if(mailSC[index] != null && !("".equals(mailSC[index]))) {
						newMailbagVO.setMailSubclass(mailSC[index].toUpperCase());
						newMailbagVO.setMailClass(newMailbagVO.getMailSubclass().substring(0,1));
					}
				}
				if(mailYr != null) {
					if(mailYr[index] != null && !("".equals(mailYr[index]))) {
						newMailbagVO.setYear(Integer.parseInt(mailYr[index]));
					}
				}
				if(mailDSN != null) {
					if(mailDSN[index] != null && !("".equals(mailDSN[index]))) {
						newMailbagVO.setDespatchSerialNumber(mailDSN[index].toUpperCase());
					}
				}
				if(mailRSN != null) {
					if(mailRSN[index] != null && !("".equals(mailRSN[index]))) {
						newMailbagVO.setReceptacleSerialNumber(mailRSN[index].toUpperCase());
					}
				}
				if(mailHNI != null) {
					if(mailHNI[index] != null && !("".equals(mailHNI[index]))) {
						newMailbagVO.setHighestNumberedReceptacle(mailHNI[index]);
					}
				}
				if(mailRI != null) {
					if(mailRI[index] != null && !("".equals(mailRI[index]))) {
						newMailbagVO.setRegisteredOrInsuredIndicator(mailRI[index]);
					}
				}
				//Added for ICRD-205027 starts
				if(mailbagId != null) {
					if(mailbagId[index] != null && !("".equals(mailbagId[index]))) {
						newMailbagVO.setMailbagId(mailbagId[index]);
					}
				}
				//Added for ICRD-205027 ends
				if(mailWt != null &&mailWt[index] != null && !("".equals(mailWt[index]))) {
						if (newMailbagVO.getMailbagId()!=null &&newMailbagVO.getMailbagId().length()==29){
							newMailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,0.0,(Double.parseDouble(newMailbagVO.getMailbagId().substring(25,29))/10),UnitConstants.WEIGHT_UNIT_KILOGRAM));
							newMailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,0.0,(Double.parseDouble(newMailbagVO.getMailbagId().substring(25,29))/10),UnitConstants.WEIGHT_UNIT_KILOGRAM)); 	
						}
						else{
						newMailbagVO.setWeight(mailWgt[index]);
						newMailbagVO.setStrWeight(mailWgt[index]);//added by A-7371
					}
				}
				mailbagVOs.add(newMailbagVO);
			}
		}
    	
		log.log(Log.FINE, "mailbagVOs to print :", mailbagVOs);
		
		if(mailbagVOs != null && mailbagVOs.size() > 0){                                  
			ReportSpec reportSpec = getReportSpec();				
			reportSpec.setReportId(REPORT_ID);
			reportSpec.setProductCode(PRODUCTCODE);
			reportSpec.setSubProductCode(SUBPRODUCTCODE);
			reportSpec.addFilterValue(mailbagVOs);
			reportSpec.setResourceBundle("printMailTagResources");
			reportSpec.setAction(ACTION);
				
			generateReport();
			invocationContext.target = getTargetPage();
			return;                                                                                                        
		}                                                
		                    
		invocationContext.target = NORMAL_REPORT_ERROR;                   
    }                                                                
}
