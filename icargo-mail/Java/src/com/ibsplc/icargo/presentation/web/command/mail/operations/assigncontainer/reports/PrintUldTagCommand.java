/*
 * PrintULDTagCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * 	IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.assigncontainer.reports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 */
public class PrintUldTagCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "04MTK001";

	private Log log = LogFactory.getLogger("ASSIGN CONTAINER");

	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";

	private static final String PRODUCT_CODE = "mail";

	private static final String SUBPRODUCT_CODE = "operations";

	private static final String RESOURCE_BUNDLE = "assignContainerResources";

	private static final String PRINT_ACTION = "printULDTag";
	
	private static final String EMPTY_STRING=" ";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("PrintULDTagCommand", "execute");

		AssignContainerForm assignContainerForm = (AssignContainerForm) 
											invocationContext.screenModel;
		AssignContainerSession assignContainerSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);

		Collection<ContainerVO> newcontainerVOs = new ArrayList<ContainerVO>();

		String[] singleCheck = assignContainerForm.getSubCheck();
		
		if (assignContainerSession.getContainerVOs() != null) {
			Collection<ContainerVO> containerVOs = assignContainerSession
					.getContainerVOs();
			log.log(Log.FINE, "Assign Container Seesion Values::::::",
					assignContainerSession.getContainerVOs());
			if (containerVOs != null) {
				int index;
				for (String count : singleCheck) {
					index = 0;
					for (ContainerVO containerVO : containerVOs) {
						if (index == Integer.parseInt(count)) {
							newcontainerVOs.add(containerVO);
							break;
						}   
						index++;
					}
				}
			}
		}

		ReportSpec reportSpec = getReportSpec();
		
		if(newcontainerVOs!=null && newcontainerVOs.size()>0){
			String[] dateFields = null;
			StringBuilder flightDetails = null;
			StringBuilder transferPorts = null;
			StringBuilder transferRoute = null;
			String tempPorts 				= null;

			for(ContainerVO containerVO:newcontainerVOs){
				int countPrint=0;
				Collection<OnwardRoutingVO> onwardRoutings = null;
				flightDetails = new StringBuilder();
				transferPorts = null;
				transferRoute = null;
				tempPorts 				= null;
				String barCodeId=containerVO.getContainerNumber();
				List<String> barcodeList = new ArrayList<String>();

				ArrayList<String> images = new ArrayList<String>();                           
		        images.add("G001");
                
				//Loaded At - V00
				if(containerVO.getAssignedPort()!=null && containerVO.getAssignedPort().trim().length()>0){
					barcodeList.add(containerVO.getAssignedPort());
				}else{
					barcodeList.add(EMPTY_STRING);
				}
				
				//Transfer Flight - V01
				onwardRoutings = containerVO.getOnwardRoutings();
				transferPorts = new StringBuilder();
				transferRoute = new StringBuilder();

				if (onwardRoutings != null && onwardRoutings.size() > 0){
					transferRoute = new StringBuilder();
					transferPorts.append(containerVO.getPou()).append(",");
					
					for (OnwardRoutingVO routingVO : onwardRoutings){

						transferRoute.append(routingVO.getOnwardCarrierCode()).append(routingVO.getOnwardFlightNumber());
						
						if (routingVO.getOnwardFlightDate() != null){
							dateFields = containerVO.getFlightDate().toDisplayDateOnlyFormat().split("-");
							transferRoute.append("/").append(dateFields[0]).append(dateFields[1]).append(",");							
						}	

						if (countPrint < onwardRoutings.size()-1){
							transferPorts.append(routingVO.getPou()).append(",");
						}
						
						countPrint++;
					}
					
					tempPorts = transferPorts.toString ().substring (0, transferPorts.length() - 1);
				}

				if(tempPorts!=null && tempPorts.trim().length()>0){
					barcodeList.add(tempPorts);
				}else{
					barcodeList.add(EMPTY_STRING);
				}
				
				//Total weight - V02
				if(Double.toString(containerVO.getWeight().getSystemValue()) != null){
					barcodeList.add(Double.toString(containerVO.getWeight().getSystemValue()));
				}else{
				barcodeList.add(EMPTY_STRING);
				}
				
				//Transfer At - V03
				if(transferRoute!=null && transferRoute.toString().length()>0){
					barcodeList.add (transferRoute.toString());
				}else{
					barcodeList.add(EMPTY_STRING);
				}
				
				//Flight/Date - V04
				if(containerVO.getCarrierCode()!=null &&
						containerVO.getFlightNumber()!=null && containerVO.getFlightDate()!=null){
					
					flightDetails.append(containerVO.getCarrierCode()).append(containerVO.getFlightNumber());
					dateFields = containerVO.getFlightDate().toDisplayDateOnlyFormat().split("-");
					
					if (dateFields.length > 0) {
						flightDetails.append("/").append(dateFields[0]).append(dateFields[1]);
					}
					
					barcodeList.add(flightDetails.toString());
				}else{
					barcodeList.add(EMPTY_STRING);
				}				
				
				//Remarks - V05
				if(containerVO.getRemarks()!=null && containerVO.getRemarks().trim().length()>0){
					barcodeList.add(containerVO.getRemarks());
				}else{
					barcodeList.add(EMPTY_STRING);
				}				
				
				//Contents - V06
				barcodeList.add("MAIL");				
				
               //Destination - V07
				if(containerVO.getFinalDestination()!=null && containerVO.getFinalDestination().trim().length()>0){
					barcodeList.add(containerVO.getFinalDestination());
				}else{
					barcodeList.add(EMPTY_STRING);
				}

				//Barcode(ULD Number) - V08
				if(containerVO.getContainerNumber()!=null && containerVO.getContainerNumber().trim().length()>0){
					barcodeList.add(containerVO.getContainerNumber());
				}else{
					barcodeList.add(EMPTY_STRING);
				}              
               
				log.log(Log.FINE, "to print 11111--->", barcodeList);
				//reportSpec.setImageParameters(images);
				reportSpec.addBarcodeData(barCodeId);
				reportSpec.addParameter(barcodeList);
				log.log(Log.FINE, "to print 22222222--->", barcodeList);
			}
		}
		
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(PRODUCT_CODE);
		reportSpec.setSubProductCode(SUBPRODUCT_CODE);
		reportSpec.setPreview(true);
		reportSpec.setData(newcontainerVOs);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE);
		//reportSpec.setAction(PRINT_ACTION);

		generateReport();
		invocationContext.target = getTargetPage();

		log.exiting("PrintULDTagCommand", "execute");

	}	
}
