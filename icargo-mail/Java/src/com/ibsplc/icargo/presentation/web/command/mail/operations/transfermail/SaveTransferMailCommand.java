/*
 * SaveTransferMailCommand.java Created on July 04, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.transfermail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerInInventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.InventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInInventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.InventoryListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1876
 *
 */
public class SaveTransferMailCommand extends AbstractPrintCommand {

   private Log log = LogFactory.getLogger("MAILTRACKING");

   /**
    * TARGET
    */
   private static final String TARGET = "success";
   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.transfermail";

   private static final String SCREEN_ID_MAILARRIVAL = "mailtracking.defaults.mailarrival";
   private static final String SCREEN_ID_INV = "mailtracking.defaults.inventorylist";	
   
   private static final String MAIL_COMMODITY_SYS = "mailtracking.defaults.booking.commodity";
   private static final String TRFMFT_REPORT_ID = "RPTOPS066";

	private static final String PRODUCTCODE = "mail";
	private static final String BUNDLE = "transferMailManifestResources";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ACTION = "generateTransferManifestReportForMail";
	private static final String SCREEN_NUMERICAL_ID_MAILBAGENQUIRY = "MTK009";
	private static final String SCREEN_NUMERICAL_ID_MAILARRIVAL = "MTK007";
	private static final String SCREEN_NUMERICAL_ID_MAIL_EXPORT_LIST = "MTK009";
	   private static final String FROM_SCREEN_EXPORTLIST="MAIL_EXPORT_LIST";
	   private static final String EMBARGO_EXISTS = "embargo_exists"; //Added by A-8164 for ICRD-265711


   private CommodityValidationVO commodityValidationVO = null;
   
	 /**
	 * This method overrides the executre method of BaseComand class 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("SaveTransferMailCommand","execute");

    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	TransferMailForm transferMailForm =
    		(TransferMailForm)invocationContext.screenModel;
    	TransferMailSession transferMailSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	boolean printNeeded=false;

		String mailCommidityCode = null;
		Collection<String> commodites = new ArrayList<String>();
		Collection<String> codes = new ArrayList<String>();
    	codes.add(MAIL_COMMODITY_SYS);
    	Map<String, String> results = null;
    	try {
    		results = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
    	} catch(BusinessDelegateException businessDelegateException) {
    		handleDelegateException(businessDelegateException);
    	}
    	if(EMBARGO_EXISTS.equals(transferMailForm.getEmbargoFlag())){ //Added by A-8164 for ICRD-265711
    		invocationContext.target =TARGET; 
    		return;  
    	}
    	if(results != null && results.size() > 0) {
    		mailCommidityCode = results.get(MAIL_COMMODITY_SYS);
    	}
		if(mailCommidityCode!=null && mailCommidityCode.trim().length()>0) {
			commodites.add(mailCommidityCode);
			Map<String,CommodityValidationVO> densityMap = null;
			CommodityDelegate  commodityDelegate = new CommodityDelegate();

			try {
				densityMap = commodityDelegate.validateCommodityCodes(logonAttributes.getCompanyCode(), commodites);
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			if(densityMap !=null && densityMap.size()>0){
				commodityValidationVO = densityMap.get(mailCommidityCode);
				log.log(Log.FINE, "DENSITY-----:", commodityValidationVO.getDensityFactor());
			}
		}

    	ContainerVO containerVO=null;
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	if(transferMailForm.getScanDate()==null && ("").equals(transferMailForm.getMailScanTime())){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyScanDate"));
 	   		invocationContext.target =TARGET; 
 	   		return; 
		}
		if(transferMailForm.getMailScanTime()==null ||("").equals(transferMailForm.getMailScanTime())){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyScanTime"));
 	   		invocationContext.target =TARGET; 
 	   		return; 
		}
		
		String scanDate= new StringBuilder().append(transferMailForm.getScanDate()).append(" ").append(transferMailForm.getMailScanTime()).append(":00").toString();
	    LocalDate scanDat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	    scanDat.setDateAndTime(scanDate);
    	
    	if("FLIGHT".equals(transferMailForm.getAssignToFlight())){
			String[] primaryKeys = transferMailForm.getSelectMail();
			String mail = primaryKeys[0];
			log.log(Log.FINE, "selected mail ===>", mail);
			containerVO= ((ArrayList<ContainerVO>)(transferMailSession.getContainerVOs())).get(Integer.parseInt(mail));
			containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
			containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
			containerVO.setOperationTime(scanDat);
			   //Modified as a part of ICRD-214920	by A-7540
                if(MailConstantsVO.FLAG_YES.equals(containerVO.getArrivedStatus())){    
			invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.containerarrived",
	   				new Object[]{containerVO.getContainerNumber()}));
			invocationContext.target = TARGET;
	  		return;
		}
    	}
    	
    	
    	
    	//Added as part of bug ICRD-207140 by A-5526 starts
    	/*
    	if(MailConstantsVO.FLAG_YES.equals(containerVO.getArrivedStatus())){    
			invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.containerarrived",
	   		new Object[]{containerVO.getContainerNumber()}));
			invocationContext.target = TARGET;
	  		return;
		}*/
    
		//Added as part of bug ICRD-207140 by A-5526 ends
//
//		if("OFL".equals(containerVO.getContainerNumber().substring(0,3))){
//			invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.offloadedmails",
//	   				new Object[]{containerVO.getContainerNumber()}));
//			reassignMailForm.setCloseFlag("N");
//	  		invocationContext.target = TARGET;
//	  		return;
//		}

    	if("FLIGHT".equals(transferMailForm.getAssignToFlight())){
			FlightValidationVO flightValidationVO = transferMailSession.getFlightValidationVO();
			//A-5249 from ICRD-84046
			if((FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus()) ||
                    FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())||
                    FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus()))){
				Object[] obj = {flightValidationVO.getCarrierCode().toUpperCase(),flightValidationVO.getFlightNumber()};
				ErrorVO err = new ErrorVO("mailtracking.defaults.reassigncontainer.err.flightintbcortba",obj);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				invocationContext.addError(err);
				invocationContext.target = TARGET;							
				return;
			}
			if(flightValidationVO.getAtd() != null){
				containerVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_DEPARTED);
			}
	    }
		
		log.log(Log.FINE, "FROM SCREEN ===>", transferMailForm.getFromScreen());
		if("MAILBAG_ENQUIRY".equals(transferMailForm.getFromScreen())){

			Collection<MailbagVO> mailbagVOs =
									transferMailSession.getMailbagVOs();
			String assignTo = transferMailForm.getAssignToFlight();
			String mailbags = "";
			int errorFlag = 0;
			if (mailbagVOs != null && mailbagVOs.size() > 0) {
				for(MailbagVO mailbagVO:mailbagVOs){
					if("FLIGHT".equals(assignTo)){
						if(mailbagVO.getCarrierId() == containerVO.getCarrierId()
							&& mailbagVO.getFlightNumber().equals(containerVO.getFlightNumber())
							&& mailbagVO.getFlightSequenceNumber() == containerVO.getFlightSequenceNumber()
							&& mailbagVO.getSegmentSerialNumber() == containerVO.getSegmentSerialNumber()
							&& mailbagVO.getContainerNumber().equals(containerVO.getContainerNumber())){
							    errorFlag = 1;
								if("".equals(mailbags)){
									mailbags = mailbagVO.getMailbagId();
				       			}else{
				       				mailbags = new StringBuilder(mailbags)
				       				           .append(",")
				       				           .append(mailbagVO.getMailbagId())
				       				           .toString();
				       			}
						}
					}else{
						/**
				    	 * To validate carrier
				    	 */
				    	AirlineValidationVO airlineValidationVO = null;
				    	String carrier = transferMailForm.getCarrierCode().trim().toUpperCase();        	
				    	if (carrier != null && !"".equals(carrier)) {        		
				    		try {        			
				    			airlineValidationVO = new AirlineDelegate().validateAlphaCode(
				    					logonAttributes.getCompanyCode(),carrier);
			
				    		}catch (BusinessDelegateException businessDelegateException) {
				    			errors = handleDelegateException(businessDelegateException);
				    		}
				    		if (errors != null && errors.size() > 0) {            			
				    			Object[] obj = {carrier};
				    			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
				    			invocationContext.target = TARGET;
				    			return;
				    		}
				    	}
						containerVO =new ContainerVO();
						containerVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
						containerVO.setCompanyCode(logonAttributes.getCompanyCode());
						containerVO.setCarrierCode(carrier);
						containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());			
						containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());				  
						containerVO.setAssignedUser(logonAttributes.getUserId());
						containerVO.setOperationTime(scanDat);
						containerVO.setAssignedPort(logonAttributes.getAirportCode());
						containerVO.setMailSource(SCREEN_NUMERICAL_ID_MAILBAGENQUIRY);//Added for ICRD-156218
				    	
						if(mailbagVO.getCarrierId() == containerVO.getCarrierId()
							&& mailbagVO.getContainerNumber().equals(containerVO.getContainerNumber())
							&& mailbagVO.getPou().equals(containerVO.getFinalDestination())){
								errorFlag = 1;
								if("".equals(mailbags)){
									mailbags = mailbagVO.getMailbagId();
				       			}else{
				       				mailbags = new StringBuilder(mailbags)
				       				           .append(",")
				       				           .append(mailbagVO.getMailbagId())
				       				           .toString();
				       			}
						}
					}
				}
			}

		   if(errorFlag == 1){
	    	    invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.reassignsamecontainer",
	    	    	new Object[]{mailbags,containerVO.getContainerNumber()}));
	    	    transferMailForm.setCloseFlag("N");
	    	    transferMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	    invocationContext.target = TARGET;
	    	    return;
	       }


		   
		   for(MailbagVO mailbagVO:mailbagVOs){
			   if(mailbagVO.getLegSerialNumber()== 0){
			  FlightValidationVO flightValidationVO =	   validatFlightLegSerialNumber( mailbagVO,containerVO);
			   mailbagVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());  
			   }
			   mailbagVO.setScannedDate(scanDat);
			   mailbagVO.setScannedPort(logonAttributes.getAirportCode());
			   mailbagVO.setScannedUser(logonAttributes.getUserId());
			  // mailbagVO.setVolume(getScaledVolume(mailbagVO.getWeight())); 
			   mailbagVO.setVolume(new Measure(UnitConstants.VOLUME, getScaledVolume(mailbagVO.getWeight().getRoundedSystemValue()))); 
			   mailbagVO.setMailSource(SCREEN_NUMERICAL_ID_MAILBAGENQUIRY);//Added for ICRD-156218
		   }

		   log.log(Log.FINE, "\n\n mailbagVOs for transfer ------->", mailbagVOs);
		log.log(Log.FINE, "\n\n mailbagVOs for transfer ------->", containerVO);
		/*
		  try {
		       new MailTrackingDefaultsDelegate().transferMailbags(mailbagVOs,containerVO);
          }catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    	  }*/
          String printFlag=transferMailForm.getPrintTransferManifestFlag();
          TransferManifestVO transferManifestVO=null;
		  try {
			  if(logonAttributes.getAirportCode().equals(mailbagVOs.iterator().next().getScannedPort())
	            		 && MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagVOs.iterator().next().getLatestStatus())){
							
	                 	  transferManifestVO=new MailTrackingDefaultsDelegate().transferMailAtExport(mailbagVOs, containerVO, printFlag);
					}else{
			  transferManifestVO=new MailTrackingDefaultsDelegate().transferMail(null,mailbagVOs,containerVO,printFlag);
					}
	      }catch (BusinessDelegateException businessDelegateException) {
	    		errors = handleDelegateException(businessDelegateException);
	      }
    	  if (errors != null && errors.size() > 0) {
  				for(ErrorVO err : errors){
  					if("mailtracking.defaults.transfermail.mailbagnotavailableatairport".equalsIgnoreCase(err.getErrorCode())){
  						ErrorVO error = new ErrorVO("mailtracking.defaults.transfermail.mailbagnotavailableatairport");
  						error.setErrorDisplayType(ErrorDisplayType.ERROR);
  						invocationContext.addError(error);
  					}else{
  						invocationContext.addError(err);
  					}
  				}
    		transferMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		transferMailForm.setCloseFlag("N");
    		invocationContext.target = TARGET;
    		return;
    	  }else{
	    	  log.log(Log.FINE, "\n\n DtransferManifestVO for transfer ------->",
		    		  transferManifestVO);
    		  if(MailConstantsVO.FLAG_YES.equalsIgnoreCase(printFlag)){ 
    			  printNeeded=true;
    				ReportSpec reportSpec = getReportSpec();    
    				reportSpec.setReportId(TRFMFT_REPORT_ID);    
    				reportSpec.setProductCode(PRODUCTCODE);
    				reportSpec.setSubProductCode(SUBPRODUCTCODE);
    				reportSpec.setPreview(true);      
    				reportSpec.addFilterValue(transferManifestVO);    
    				reportSpec.setResourceBundle(BUNDLE);    				
    				reportSpec.setAction(ACTION);                      
    				   
    				generateReport();                  
    				 
    				  
    				
    		      }    
    	 
    	  }
    	  
		}
		
		else if(FROM_SCREEN_EXPORTLIST.equals(transferMailForm.getFromScreen())){//added by A-7371 for ICRD-133987


			Collection<MailbagVO> mailbagVOs =
									transferMailSession.getMailbagVOs();
			String assignTo = transferMailForm.getAssignToFlight();
			String mailbags = "";
			int errorFlag = 0;
			if (mailbagVOs != null && mailbagVOs.size() > 0) {
				for(MailbagVO mailbagVO:mailbagVOs){
					if("FLIGHT".equals(assignTo)){
						if(mailbagVO.getCarrierId() == containerVO.getCarrierId()
							&& mailbagVO.getFlightNumber().equals(containerVO.getFlightNumber())
							&& mailbagVO.getFlightSequenceNumber() == containerVO.getFlightSequenceNumber()
							&& mailbagVO.getSegmentSerialNumber() == containerVO.getSegmentSerialNumber()
							&& mailbagVO.getContainerNumber().equals(containerVO.getContainerNumber())){
							    errorFlag = 1;
								if("".equals(mailbags)){
									mailbags = mailbagVO.getMailbagId();
				       			}else{
				       				mailbags = new StringBuilder(mailbags)
				       				           .append(",")
				       				           .append(mailbagVO.getMailbagId())
				       				           .toString();
				       			}
						}
					}else{
						/**
				    	 * To validate carrier
				    	 */
				    	AirlineValidationVO airlineValidationVO = null;
				    	String carrier = transferMailForm.getCarrierCode().trim().toUpperCase();        	
				    	if (carrier != null && !"".equals(carrier)) {        		
				    		try {        			
				    			airlineValidationVO = new AirlineDelegate().validateAlphaCode(
				    					logonAttributes.getCompanyCode(),carrier);
			
				    		}catch (BusinessDelegateException businessDelegateException) {
				    			errors = handleDelegateException(businessDelegateException);
				    		}
				    		if (errors != null && errors.size() > 0) {            			
				    			Object[] obj = {carrier};
				    			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
				    			invocationContext.target = TARGET;
				    			return;
				    		}
				    	}
						containerVO =new ContainerVO();
						containerVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
						containerVO.setCompanyCode(logonAttributes.getCompanyCode());
						containerVO.setCarrierCode(carrier);
						containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());			
						containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());				  
						containerVO.setAssignedUser(logonAttributes.getUserId());
						containerVO.setOperationTime(scanDat);
						containerVO.setAssignedPort(logonAttributes.getAirportCode());
						containerVO.setMailSource(SCREEN_NUMERICAL_ID_MAIL_EXPORT_LIST);//Added for ICRD-156218
				    	
						if(mailbagVO.getCarrierId() == containerVO.getCarrierId()
							&& mailbagVO.getContainerNumber().equals(containerVO.getContainerNumber())
							&& mailbagVO.getPou().equals(containerVO.getFinalDestination())){
								errorFlag = 1;
								if("".equals(mailbags)){
									mailbags = mailbagVO.getMailbagId();
				       			}else{
				       				mailbags = new StringBuilder(mailbags)
				       				           .append(",")
				       				           .append(mailbagVO.getMailbagId())
				       				           .toString();
				       			}
						}
					}
				}
			}

		   if(errorFlag == 1){
	    	    invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.reassignsamecontainer",
	    	    	new Object[]{mailbags,containerVO.getContainerNumber()}));
	    	    transferMailForm.setCloseFlag("N");
	    	    transferMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	    invocationContext.target = TARGET;
	    	    return;
	       }


		   
		   for(MailbagVO mailbagVO:mailbagVOs){
			   if(mailbagVO.getLegSerialNumber()== 0){
			  FlightValidationVO flightValidationVO =	   validatFlightLegSerialNumber( mailbagVO,containerVO);
			   mailbagVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());  
			   }
			   mailbagVO.setScannedDate(scanDat);
			   mailbagVO.setScannedPort(logonAttributes.getAirportCode());
			   mailbagVO.setScannedUser(logonAttributes.getUserId());
			  // mailbagVO.setVolume(getScaledVolume(mailbagVO.getWeight())); 
			   mailbagVO.setVolume(new Measure(UnitConstants.VOLUME, getScaledVolume(mailbagVO.getWeight().getRoundedSystemValue()))); 
			   mailbagVO.setMailSource(SCREEN_NUMERICAL_ID_MAILBAGENQUIRY);//Added for ICRD-156218
		   }

		   log.log(Log.FINE, "\n\n mailbagVOs for transfer ------->", mailbagVOs);
		log.log(Log.FINE, "\n\n mailbagVOs for transfer ------->", containerVO);
		/*
		  try {
		       new MailTrackingDefaultsDelegate().transferMailbags(mailbagVOs,containerVO);
          }catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    	  }*/
          String printFlag=transferMailForm.getPrintTransferManifestFlag();
          TransferManifestVO transferManifestVO=null;
			try {
				if (logonAttributes.getAirportCode().equals(mailbagVOs.iterator().next().getScannedPort())
						&& MailConstantsVO.MAIL_STATUS_ACCEPTED
								.equals(mailbagVOs.iterator().next().getLatestStatus())) {

					transferManifestVO = new MailTrackingDefaultsDelegate().transferMailAtExport(mailbagVOs,
							containerVO, printFlag);
				} else {
					transferManifestVO = new MailTrackingDefaultsDelegate().transferMail(null, mailbagVOs, containerVO,
							printFlag);
				}
			}catch (BusinessDelegateException businessDelegateException) {
	    		errors = handleDelegateException(businessDelegateException);
	      }
    	  if (errors != null && errors.size() > 0) {
  				for(ErrorVO err : errors){
  					if("mailtracking.defaults.transfermail.mailbagnotavailableatairport".equalsIgnoreCase(err.getErrorCode())){
  						ErrorVO error = new ErrorVO("mailtracking.defaults.transfermail.mailbagnotavailableatairport");
  						error.setErrorDisplayType(ErrorDisplayType.ERROR);
  						invocationContext.addError(error);
  					}else{
  						invocationContext.addError(err);
  					}
  				}
    		transferMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		transferMailForm.setCloseFlag("N");
    		invocationContext.target = TARGET;
    		return;
    	  }else{
	    	  log.log(Log.FINE, "\n\n DtransferManifestVO for transfer ------->",
		    		  transferManifestVO);
    		  if(MailConstantsVO.FLAG_YES.equalsIgnoreCase(printFlag)){ 
    			  printNeeded=true;
    				ReportSpec reportSpec = getReportSpec();    
    				reportSpec.setReportId(TRFMFT_REPORT_ID);    
    				reportSpec.setProductCode(PRODUCTCODE);
    				reportSpec.setSubProductCode(SUBPRODUCTCODE);
    				reportSpec.setPreview(true);      
    				reportSpec.addFilterValue(transferManifestVO);    
    				reportSpec.setResourceBundle(BUNDLE);    				
    				reportSpec.setAction(ACTION);                      
    				   
    				generateReport();                    
    				
    		      }    
    	  }
			
		}
		
		else if("DSN_ENQUIRY".equals(transferMailForm.getFromScreen())){

			Collection<DespatchDetailsVO> despatchDetailsVOs =
									transferMailSession.getDespatchDetailsVOs();
			String assignTo = transferMailForm.getAssignToFlight();
			String mailbags = "";
			int errorFlag = 0;
			if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
				for(DespatchDetailsVO despatchDetailsVO:despatchDetailsVOs){

					String pk = new StringBuilder(despatchDetailsVO.getOriginOfficeOfExchange())
						.append(despatchDetailsVO.getDestinationOfficeOfExchange())
						.append(despatchDetailsVO.getMailClass())
						.append(despatchDetailsVO.getYear())
						.append(despatchDetailsVO.getDsn()).toString();

					if("FLIGHT".equals(assignTo)){
						if(despatchDetailsVO.getFlightSequenceNumber() != 0){
						if(despatchDetailsVO.getCarrierId() == containerVO.getCarrierId()
							&& despatchDetailsVO.getFlightNumber().equals(containerVO.getFlightNumber())
							&& despatchDetailsVO.getFlightSequenceNumber() == containerVO.getFlightSequenceNumber()
							&& despatchDetailsVO.getSegmentSerialNumber() == containerVO.getSegmentSerialNumber()
							&& despatchDetailsVO.getContainerNumber().equals(containerVO.getContainerNumber())){
							    errorFlag = 1;
								if("".equals(mailbags)){
									mailbags = pk;
				       			}else{
				       				mailbags = new StringBuilder(mailbags).append(",").append(pk).toString();
				       			}
						} else {
							/*despatchDetailsVO.setStatedBags(
									despatchDetailsVO.getAcceptedBags());
							despatchDetailsVO.setStatedWeight(
									despatchDetailsVO.getAcceptedWeight());*/
						  }
					  }
					}else{
						/**
				    	 * To validate carrier
				    	 */
				    	AirlineValidationVO airlineValidationVO = null;
				    	String carrier = transferMailForm.getCarrierCode().trim().toUpperCase();        	
				    	if (carrier != null && !"".equals(carrier)) {        		
				    		try {        			
				    			airlineValidationVO = new AirlineDelegate().validateAlphaCode(
				    					logonAttributes.getCompanyCode(),carrier);
			
				    		}catch (BusinessDelegateException businessDelegateException) {
				    			errors = handleDelegateException(businessDelegateException);
				    		}
				    		if (errors != null && errors.size() > 0) {            			
				    			Object[] obj = {carrier};
				    			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
				    			invocationContext.target = TARGET;
				    			return;
				    		}
				    	}
						containerVO =new ContainerVO();
						containerVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
						containerVO.setCarrierCode(carrier);
						containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());			
						containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());				  
						containerVO.setAssignedUser(logonAttributes.getUserId());
						containerVO.setOperationTime(scanDat);
						containerVO.setCompanyCode(logonAttributes.getCompanyCode());
						containerVO.setAssignedPort(logonAttributes.getAirportCode());
						if(despatchDetailsVO.getCarrierId() == containerVO.getCarrierId()
							&& despatchDetailsVO.getContainerNumber().equals(containerVO.getContainerNumber())
							&& despatchDetailsVO.getDestination().equals(containerVO.getFinalDestination())){
								errorFlag = 1;
								if("".equals(mailbags)){
									mailbags = pk;
				       			}else{
				       				mailbags = new StringBuilder(mailbags).append(",").append(pk).toString();
				       			}
						} else {
							despatchDetailsVO.setStatedBags(
									despatchDetailsVO.getAcceptedBags());
							despatchDetailsVO.setStatedWeight(
									despatchDetailsVO.getAcceptedWeight());
						}
					}
				}
			}

		   if(errorFlag == 1){
	    	    invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.reassignsamecontainer",
	    	    		new Object[]{mailbags,containerVO.getContainerNumber()}));
	    	    transferMailForm.setCloseFlag("N");
	    	    transferMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	    invocationContext.target = TARGET;
	    	    return;
	       }

		   log.log(Log.FINE, "\n\n getDespatchDetailsVOs for transfer ------->",
				transferMailSession.getDespatchDetailsVOs());
		log.log(Log.FINE, "\n\n DespatchDetailsVOs for transfer ------->",
				containerVO);
		/*try {
			   new MailTrackingDefaultsDelegate().transferDespatches(transferMailSession.getDespatchDetailsVOs(),containerVO);
	      }catch (BusinessDelegateException businessDelegateException) {
	    		errors = handleDelegateException(businessDelegateException);
	      }*/
	      String printFlag=transferMailForm.getPrintTransferManifestFlag();
	      TransferManifestVO transferManifestVO=null;
	      
		    try {
		    	transferManifestVO=new MailTrackingDefaultsDelegate().transferMail(despatchDetailsVOs,null,containerVO,printFlag);  
		    }catch (BusinessDelegateException businessDelegateException) {
		    		errors = handleDelegateException(businessDelegateException);
		    }
		      if (errors != null && errors.size() > 0) {
		    	  
		    	invocationContext.addAllError(errors);
		    	transferMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		    	transferMailForm.setCloseFlag("N");
		    	invocationContext.target = TARGET;
		    	return;
		      }else{
		    	  log.log(Log.FINE, "\n\n DtransferManifestVO for transfer ------->",
			    		  transferManifestVO);
	    		  if(MailConstantsVO.FLAG_YES.equalsIgnoreCase(printFlag)){ 
	    			  printNeeded=true;
	    				ReportSpec reportSpec = getReportSpec();    
	    				reportSpec.setReportId(TRFMFT_REPORT_ID);    
	    				reportSpec.setProductCode(PRODUCTCODE);
	    				reportSpec.setSubProductCode(SUBPRODUCTCODE);
	    				reportSpec.setPreview(true);
	    				reportSpec.addFilterValue(transferManifestVO);    
	    				reportSpec.setResourceBundle(BUNDLE);
	    				reportSpec.setAction(ACTION);          
	    				   
	    				generateReport();  
	    				 
	    				  
	    				
	    		      }    
		      }

		} else if("MAIL_ARRIVAL".equals(transferMailForm.getFromScreen())){
			MailArrivalSession mailArrivalSession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID_MAILARRIVAL);  
			String assignTo = transferMailForm.getAssignToFlight();
	    	
			MailArrivalVO newMailArrivalVO=mailArrivalSession.getMailArrivalVO();
	    	OperationalFlightVO operationalFlightVO = mailArrivalSession.getOperationalFlightVO();
			ArrayList<ContainerDetailsVO> containerDetails = (ArrayList<ContainerDetailsVO>)newMailArrivalVO.getContainerDetails();
			ArrayList<MailbagVO> mailbagVOs=new ArrayList<MailbagVO>();
			ArrayList<DespatchDetailsVO> despatchDetailsVOs=new ArrayList<DespatchDetailsVO>();
			String[] dsns=transferMailForm.getMailbag().split(",");
			int size=dsns.length;
			
			
	    	if("FLIGHT".equals(assignTo)){
	    		
	    		log.log(Log.FINE,"\n\n&&&&&&&&&&&&& ISIDE IF");
				for(int i=0;i<size;i++){
					ContainerDetailsVO contVO = ((ArrayList<ContainerDetailsVO>)(containerDetails)).get(Integer.parseInt(dsns[i].split("~")[0]));
					DSNVO dsnVO =  ((ArrayList<DSNVO>)(contVO.getDsnVOs())).get(Integer.parseInt(dsns[i].split("~")[1]));
					
					String innerpk = dsnVO.getOriginExchangeOffice()
					   +dsnVO.getDestinationExchangeOffice()
					   +dsnVO.getMailCategoryCode()
			           +dsnVO.getMailSubclass()
			           +dsnVO.getDsn()
			           +dsnVO.getYear();
					if (contVO.getMailDetails() != null && contVO.getMailDetails().size() > 0) {
					   for(MailbagVO mailbagvo:contVO.getMailDetails()){
							String outerpk = mailbagvo.getOoe()
							    +mailbagvo.getDoe()
					           	+mailbagvo.getMailCategoryCode()
					           	+mailbagvo.getMailSubclass()
					            +mailbagvo.getDespatchSerialNumber()
					            +mailbagvo.getYear();
							if(innerpk.equals(outerpk) && !("Y").equals(mailbagvo.getTransferFlag())
									&& !("I").equals(mailbagvo.getOperationalFlag())){
		//						mailbagvo.setContainerNumber(contVO.getContainerNumber());
		//						mailbagvo.setCompanyCode(contVO.getCompanyCode());
		//						mailbagvo.setCarrierId(contVO.getCarrierId());
		//						mailbagvo.setFlightNumber(contVO.getFlightNumber());
		//						mailbagvo.setFlightSequenceNumber(contVO.getFlightSequenceNumber());
		//						mailbagvo.setLegSerialNumber(contVO.getLegSerialNumber());
		//						mailbagvo.setSegmentSerialNumber(contVO.getSegmentSerialNumber());
		//						mailbagvo.setContainerType(contVO.getContainerType());
								mailbagvo.setScannedPort(logonAttributes.getAirportCode());
								mailbagvo.setScannedUser(logonAttributes.getUserId().toUpperCase());
								mailbagvo.setScannedDate(scanDat);
								mailbagvo.setCarrierCode(operationalFlightVO.getCarrierCode());
								mailbagvo.setFlightNumber(operationalFlightVO.getFlightNumber());
								mailbagvo.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());        
								mailbagvo.setFlightDate(operationalFlightVO.getFlightDate());
								mailbagvo.setVolume(new Measure(UnitConstants.VOLUME, getScaledVolume(mailbagvo.getWeight().getRoundedDisplayValue())));//added by A-7371
								mailbagvo.setMailSource(SCREEN_NUMERICAL_ID_MAILARRIVAL);//Added for ICRD-156218
								/*
								 * Added For  AirNZ CR: Mail Allocation
								 */
								mailbagvo.setUbrNumber(dsnVO.getUbrNumber());
								mailbagvo.setBookingLastUpdateTime(dsnVO.getBookingLastUpdateTime());
								mailbagvo.setBookingFlightDetailLastUpdTime(dsnVO.getBookingFlightDetailLastUpdTime());
								//END AirNZ CR : Mail Allocation
								
								if(mailbagvo.getInventoryContainer()==null)   
								{  
									/**
									 * Error message changed as part of bug ICRD-97075
									 * Inventory container number is null for mailbags which are not arrived.
									 * Under a dsn if one mailbag is arrived and other accepted, this error msg will be thrown
									 * For transfer all mailbags shud be arrived.Changing the error message to 
									 * All mailbags in dsn should be arrived for transfer
									 * Change done by A-4809 as discussed with Santhi K
									 */
									/*invocationContext.addError(
						 	   	   			new ErrorVO("mailtracking.defaults.msg.err.alreadytransferred",  
						 	   	   				new Object[]{mailbagvo.getMailbagId()}));*/ 
									invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.allmailbagsneedtobearrived"));
						 	   	   	invocationContext.target = TARGET;    
						 	   	   	return;
								}    
								      
								
								mailbagVOs.add(mailbagvo);
							}
						}
					}					
					if (contVO.getDesptachDetailsVOs() != null && contVO.getDesptachDetailsVOs().size() > 0) {
					    for(DespatchDetailsVO despatchDetails:contVO.getDesptachDetailsVOs()){
							String outerpk = despatchDetails.getOriginOfficeOfExchange()
					           		+despatchDetails.getDestinationOfficeOfExchange()
					           		+despatchDetails.getMailCategoryCode()
					           		+despatchDetails.getMailSubclass()
					           		+despatchDetails.getDsn()
					           		+despatchDetails.getYear();
							if(innerpk.equals(outerpk) && !("Y").equals(despatchDetails.getTransferFlag())
									&& !("I").equals(despatchDetails.getOperationalFlag())){
							//	despatchDetails.setContainerNumber(contVO.getContainerNumber());
							//	despatchDetails.setContainerNumber(contVO.getContainerNumber());
								despatchDetails.setCompanyCode(contVO.getCompanyCode());
								despatchDetails.setCarrierId(contVO.getCarrierId());
								despatchDetails.setFlightNumber(contVO.getFlightNumber());
								despatchDetails.setFlightSequenceNumber(contVO.getFlightSequenceNumber());
								despatchDetails.setLegSerialNumber(contVO.getLegSerialNumber());
								despatchDetails.setSegmentSerialNumber(contVO.getSegmentSerialNumber());
								despatchDetails.setContainerType(contVO.getContainerType());
								despatchDetails.setCarrierCode(operationalFlightVO.getCarrierCode());
								despatchDetails.setFlightDate(operationalFlightVO.getFlightDate());
								/*
								 * Added For ANZ Bug 48470 
								 */
								despatchDetails.setPrevAcceptedBags(0);
								//despatchDetails.setPrevAcceptedWeight(0.0D);
								despatchDetails.setPrevAcceptedWeight(new Measure(UnitConstants.VOLUME,0.0D));//added by A-7371
			    				//despatchDetails.setStatedVolume(getScaledVolume(despatchDetails.getStatedWeight()));
								despatchDetails.setStatedVolume(new Measure(UnitConstants.VOLUME,getScaledVolume(despatchDetails.getStatedWeight().getRoundedSystemValue())));//added by A-7371
			    				despatchDetails.setAcceptedVolume(new Measure(UnitConstants.VOLUME,getScaledVolume(despatchDetails.getAcceptedWeight().getRoundedSystemValue())));//added by A-7371
								/*
								 * Added For  AirNZ CR: Mail Allocation
								 */
								despatchDetails.setUbrNumber(dsnVO.getUbrNumber());
								despatchDetails.setBookingLastUpdateTime(dsnVO.getBookingLastUpdateTime());
								despatchDetails.setBookingFlightDetailLastUpdTime(dsnVO.getBookingFlightDetailLastUpdTime());
								//END AirNZ CR : Mail Allocation

		    				   despatchDetailsVOs.add(despatchDetails);
							}
						 }		
					  }	
				  }
				
				  log.log(Log.FINE, "\n\n despatchDetailsVOs ------->",
						despatchDetailsVOs);
				log.log(Log.FINE, "\n\n mailbagVOs ------->", mailbagVOs);
				/*  
					  try {
						   new MailTrackingDefaultsDelegate().transferDespatches(despatchDetailsVOs,containerVO);
				      }catch (BusinessDelegateException businessDelegateException) {
				    		errors = handleDelegateException(businessDelegateException);
				      }
				      try {
						   new MailTrackingDefaultsDelegate().transferMailbags(mailbagVOs,containerVO);
				      }catch (BusinessDelegateException businessDelegateException) {
				    		errors = handleDelegateException(businessDelegateException);
				      }
			      */
				  String printFlag=transferMailForm.getPrintTransferManifestFlag();
				  containerVO.setFromCarrier(transferMailForm.getFrmCarCod());
				  containerVO.setFromFltNum(transferMailForm.getFrmFltNum());
				  containerVO.setMailSource(SCREEN_NUMERICAL_ID_MAILARRIVAL);//Added for ICRD-156218
				  log.log(Log.FINE, "$$$$$$$$$4transferMailForm.getFrmFltDat()",
						transferMailForm.getFrmFltDat());
				String frmDate= new StringBuilder().append(transferMailForm.getFrmFltDat()).append(" ").append("00:00").append(":00").toString();
				  LocalDate frmDat = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				  frmDat.setDateAndTime(frmDate);
				  containerVO.setFromFltDat(frmDat);
				  TransferManifestVO transferManifestVO=null;
				  try {
					  transferManifestVO=new MailTrackingDefaultsDelegate().transferMail(despatchDetailsVOs,mailbagVOs,containerVO,printFlag);
			      }catch (BusinessDelegateException businessDelegateException) {
			    		errors = handleDelegateException(businessDelegateException);
			      }
			      if (errors != null && errors.size() > 0) {
			    	invocationContext.addAllError(errors);
			    	transferMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			    	transferMailForm.setCloseFlag("N");
			    	invocationContext.target = TARGET;
			    	return;
			      }else{
			    	  log.log(Log.FINE, "\n\n DtransferManifestVO for transfer ------->",
				    		  transferManifestVO);
		    		  if(MailConstantsVO.FLAG_YES.equalsIgnoreCase(printFlag)){ 
		    			  printNeeded=true;
		    				ReportSpec reportSpec = getReportSpec();    
		    				reportSpec.setReportId(TRFMFT_REPORT_ID);    
		    				reportSpec.setProductCode(PRODUCTCODE);
		    				reportSpec.setSubProductCode(SUBPRODUCTCODE);
		    				reportSpec.setPreview(true);
		    				reportSpec.addFilterValue(transferManifestVO);    
		    				reportSpec.setResourceBundle(BUNDLE);
		    				reportSpec.setAction(ACTION);              
		    				   
		    				generateReport();          
		    				 
		    				  
		    				
		    		      }    
			      }
				
				
	    	}else{
	    		log.log(Log.FINE,"\n\n@@@@@@@@@@@@@@@ INSIDE ELSE");
	    		/**
	    		 * To validate carrier
	    		 */
	    		AirlineValidationVO airlineValidationVO = null;
	    		String carrier = transferMailForm.getCarrierCode().trim().toUpperCase();   

	    		if (carrier != null && !"".equals(carrier)) {        		
	    			try {        			
	    				airlineValidationVO = new AirlineDelegate().validateAlphaCode(
	    						logonAttributes.getCompanyCode(),carrier);

	    			}catch (BusinessDelegateException businessDelegateException) {
	    				errors = handleDelegateException(businessDelegateException);
	    			}
	    			if (errors != null && errors.size() > 0) {            			
	    				Object[] obj = {carrier};
	    				invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
	    				invocationContext.target = TARGET;
	    				return;
	    			}
	    		}

	    		//OperationalFlightVO operationalFlightVO = mailArrivalSession.getOperationalFlightVO();
	    		operationalFlightVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
	    		Collection<MailbagVO> newMailbagVOs =  new ArrayList<MailbagVO>();
	    		Collection<MailbagVO> tempMailbagVOs =  new ArrayList<MailbagVO>();
	    		String mails = transferMailForm.getMailbag();
	    		String[] primaryKey = mails.split(",");
	    		String primaryKeyContainer = primaryKey[0].split("~")[0];
	    		MailArrivalVO mailArrivalVO =mailArrivalSession.getMailArrivalVO();

	    		ArrayList<ContainerDetailsVO> containerDetailsVOs = (ArrayList<ContainerDetailsVO>) mailArrivalVO.getContainerDetails();
	    		ContainerDetailsVO containerDetailsVO = containerDetailsVOs.get(Integer.parseInt(primaryKeyContainer));
	    		ContainerVO toContainerVO = new ContainerVO();
	    		toContainerVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
	    		toContainerVO.setCarrierCode(carrier);
	    		log.log(Log.FINE, "getOwnAirlineIdentifier===>",
						logonAttributes.getOwnAirlineIdentifier());
				toContainerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
	    		toContainerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
	    		toContainerVO.setOperationTime(scanDat);
	    		toContainerVO.setAssignedPort(logonAttributes.getAirportCode());
	    		toContainerVO.setCompanyCode(logonAttributes.getCompanyCode());

log.log(Log.FINE, "primaryKey.length ===>", primaryKey.length);
				String primaryKeyDSN;
	    		for(int i=0;i<primaryKey.length;i++){
	    			primaryKeyDSN = primaryKey[i].split("~")[1];
	    			log.log(Log.FINE, "primaryKeyD ===>", primaryKeyDSN);
					DSNVO dsnVO = ((ArrayList<DSNVO>) containerDetailsVO.getDsnVOs()).get(Integer.parseInt(primaryKeyDSN));
	    			String innerpk = dsnVO.getOriginExchangeOffice()
	    			+dsnVO.getDestinationExchangeOffice()
	    			+dsnVO.getMailCategoryCode()
	    			+dsnVO.getMailSubclass()
	    			+dsnVO.getDsn()
	    			+dsnVO.getYear();
	    			Collection<MailbagVO> totMailbagVOs = containerDetailsVO.getMailDetails();
	    			if (totMailbagVOs != null && totMailbagVOs.size() > 0) {
	    				for(MailbagVO mailbagvo:totMailbagVOs){
	    					String outerpk = mailbagvo.getOoe()
	    					+mailbagvo.getDoe()
	    					+mailbagvo.getMailCategoryCode()
	    					+mailbagvo.getMailSubclass()
	    					+mailbagvo.getDespatchSerialNumber()
	    					+mailbagvo.getYear();
	    					if(innerpk.equals(outerpk) && !("Y").equals(mailbagvo.getTransferFlag())
	    							&& !("I").equals(mailbagvo.getOperationalFlag())){
	    						mailbagvo.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
	    						mailbagvo.setCompanyCode(logonAttributes.getCompanyCode());
	    						mailbagvo.setFlightNumber(operationalFlightVO.getFlightNumber());
	    						mailbagvo.setFlightDate(operationalFlightVO.getFlightDate());
	    						mailbagvo.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
	    						mailbagvo.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
	    						mailbagvo.setAcceptanceFlag(containerDetailsVO.getAcceptedFlag());
	    						mailbagvo.setScannedPort(logonAttributes.getAirportCode());
	    						mailbagvo.setScannedUser(logonAttributes.getUserId().trim().toUpperCase());
	    						mailbagvo.setUldNumber(containerDetailsVO.getContainerNumber());
	    						//Added as part of bug ICRD-159474 by A-5526 starts
	    						mailbagvo.setMailSource(SCREEN_NUMERICAL_ID_MAILARRIVAL);    
	    						//Added as part of bug ICRD-159474 by A-5526 ends
	    						mailbagvo.setScannedDate(scanDat);
	    						/*
	    						 * Added For  AirNZ CR: Mail Allocation
	    						 */
	    						mailbagvo.setUbrNumber(dsnVO.getUbrNumber());
	    						mailbagvo.setBookingLastUpdateTime(dsnVO.getBookingLastUpdateTime());
	    						mailbagvo.setBookingFlightDetailLastUpdTime(dsnVO.getBookingFlightDetailLastUpdTime());
	    						if(mailbagvo.getInventoryContainer()==null)   
								{									/**
									 * Error message changed as part of bug ICRD-97075
									 * Inventory container number is null for mailbags which are not arrived.
									 * Under a dsn if one mailbag is arrived and other accepted, this error msg will be thrown
									 * For transfer all mailbags shud be arrived.Changing the error message to 
									 * All mailbags in dsn should be arrived for transfer
									 * Change done by A-4809 as discussed with Santhi K
									 */
									/*invocationContext.addError(
						 	   	   			new ErrorVO("mailtracking.defaults.msg.err.alreadytransferred",  
						 	   	   				new Object[]{mailbagvo.getMailbagId()}));*/ 
									invocationContext.addError(new ErrorVO("mailtracking.defaults.msg.err.allmailbagsneedtobearrived"));
						 	   	   	invocationContext.target = TARGET;    
						 	   	   	return;
								}
	    						//END AirNZ CR: Mail Allocation
	    						tempMailbagVOs.add(mailbagvo);
	    					}
	    				}
	    			}
	    			if (containerDetailsVO.getDesptachDetailsVOs() != null && containerDetailsVO.getDesptachDetailsVOs().size() > 0) {
	    				for(DespatchDetailsVO despatchDetails:containerDetailsVO.getDesptachDetailsVOs()){
	    					String outerpk = despatchDetails.getOriginOfficeOfExchange()
	    					+despatchDetails.getDestinationOfficeOfExchange()
	    					+despatchDetails.getMailCategoryCode()
	    					+despatchDetails.getMailSubclass()
	    					+despatchDetails.getDsn()
	    					+despatchDetails.getYear();
	    					if(innerpk.equals(outerpk) && !("Y").equals(despatchDetails.getTransferFlag())
	    							&& !("I").equals(despatchDetails.getOperationalFlag())){
	    						//despatchDetails.setContainerNumber(containerDetailsVO.getContainerNumber());
	    						despatchDetails.setCompanyCode(containerDetailsVO.getCompanyCode());
	    						despatchDetails.setCarrierId(containerDetailsVO.getCarrierId());
	    						despatchDetails.setFlightNumber(containerDetailsVO.getFlightNumber());
	    						despatchDetails.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
	    						despatchDetails.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
	    						despatchDetails.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
	    						despatchDetails.setContainerType(containerDetailsVO.getContainerType());
	    						despatchDetails.setCarrierCode(newMailArrivalVO.getFlightCarrierCode());
								despatchDetails.setFlightDate(operationalFlightVO.getFlightDate());
	    						/*
	    						 * Added For  AirNZ CR: Mail Allocation
	    						 */
	    						despatchDetails.setUbrNumber(dsnVO.getUbrNumber());
	    						despatchDetails.setBookingLastUpdateTime(dsnVO.getBookingLastUpdateTime());
	    						despatchDetails.setBookingFlightDetailLastUpdTime(dsnVO.getBookingFlightDetailLastUpdTime());
	    						//END AirNZ CR: Mail Allocation
	    						despatchDetailsVOs.add(despatchDetails);		
	    					}
	    				}		
	    			}	
	    		}
	    		if(tempMailbagVOs != null && tempMailbagVOs.size() > 0) {
	    			newMailbagVOs.addAll(tempMailbagVOs);
	    		}
	    		/*
		    	try {
					log.log(Log.FINE,"newMailbagVOs ------------> " + newMailbagVOs);
					new MailTrackingDefaultsDelegate().transferMailbags(newMailbagVOs, toContainerVO);
		    	}catch (BusinessDelegateException businessDelegateException) {
				}*/
	    		String printFlag=transferMailForm.getPrintTransferManifestFlag();
	    		TransferManifestVO transferManifestVO=null;        
	    		try {
	    			transferManifestVO=new MailTrackingDefaultsDelegate().transferMail(despatchDetailsVOs,newMailbagVOs,toContainerVO,printFlag);
	    		}catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    		}
	    		if (errors != null && errors.size() > 0) {
	    			invocationContext.addAllError(errors);
	    			invocationContext.target = TARGET;
	    			return;
	    		}else{
			    	  log.log(Log.FINE, "\n\n DtransferManifestVO for transfer ------->",
				    		  transferManifestVO);
		    		  if(MailConstantsVO.FLAG_YES.equalsIgnoreCase(printFlag)){ 
		    			  printNeeded=true;
		    				ReportSpec reportSpec = getReportSpec();    
		    				reportSpec.setReportId(TRFMFT_REPORT_ID);    
		    				reportSpec.setProductCode(PRODUCTCODE);
		    				reportSpec.setSubProductCode(SUBPRODUCTCODE);
		    				//reportSpec.setHttpServerBase(invocationContext.httpServerBase);  
		    				reportSpec.setPreview(true);
		    				reportSpec.addFilterValue(transferManifestVO);    
		    				reportSpec.setResourceBundle(BUNDLE);
		    				reportSpec.setAction(ACTION);          
		    				   
		    				generateReport();  
		    				 
		    				  
		    				
		    		      }    
	    		}

	    	}
	
	    } else  if("INVENTORY_LIST".equals(transferMailForm.getFromScreen())){
			InventoryListSession inventoryListSession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID_INV); 
			String assignTo = transferMailForm.getAssignToFlight();
			
			InventoryListVO inventoryListVO = inventoryListSession.getInventoryListVO();
	    	Collection<ContainerInInventoryListVO> containerInInventoryList = inventoryListVO.getContainerInInventoryList();
	   	    log.log(Log.FINE, "\n\n transferMailForm.getMailbag()-->",
					transferMailForm.getMailbag());
			Collection<MailInInventoryListVO> newMailInInventoryListVOs = new ArrayList<MailInInventoryListVO>();
	    	
	    	if(!"FLIGHT".equals(assignTo)){
	    		log.log(Log.FINE,"\n\n@@@@@@@@@@@@@@@ ISIDE ELSE");
				/**
		    	 * To validate carrier
		    	 */
		    	AirlineValidationVO airlineValidationVO = null;
		    	String carrier = transferMailForm.getCarrierCode().trim().toUpperCase();        	
		    	if (carrier != null && !"".equals(carrier)) {        		
		    		try {        			
		    			airlineValidationVO = new AirlineDelegate().validateAlphaCode(
		    					logonAttributes.getCompanyCode(),carrier);
	
		    		}catch (BusinessDelegateException businessDelegateException) {
		    			errors = handleDelegateException(businessDelegateException);
		    		}
		    		if (errors != null && errors.size() > 0) {            			
		    			Object[] obj = {carrier};
		    			invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier",obj));
		    			invocationContext.target = TARGET;
		    			return;
		    		}
		    	}
		    	
		    	ContainerVO toContainerVO = new ContainerVO();
		    	toContainerVO.setCompanyCode(logonAttributes.getCompanyCode());
		    	toContainerVO.setCarrierCode(carrier);		    	
		    	toContainerVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
		    	toContainerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());			
		    	toContainerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());				  
		    	toContainerVO.setAssignedUser(logonAttributes.getUserId());
		    	toContainerVO.setOperationTime(scanDat);
		    	toContainerVO.setAssignedPort(logonAttributes.getAirportCode());
		    	
		    	
			    if("C".equals(transferMailForm.getSelectMode())){
		   	    	String[] containers = transferMailForm.getMailbag().split(",");
		   	    	int size = containers.length;
		   		    for(int i=0;i<size;i++){
		   		 	if (containerInInventoryList != null && containerInInventoryList.size() > 0) {
	   		    		ContainerInInventoryListVO contvo = ((ArrayList<ContainerInInventoryListVO>)containerInInventoryList).get(Integer.parseInt(containers[i]));
   					   Collection<MailInInventoryListVO> mailInInventoryListVOs = contvo.getMailInInventoryList();
   					   if (mailInInventoryListVOs != null && mailInInventoryListVOs.size() > 0) {
		   					   for(MailInInventoryListVO mailvo:mailInInventoryListVOs){
		   						   mailvo.setCurrentAirport(logonAttributes.getAirportCode());					   
		   						   mailvo.setCompanyCode(logonAttributes.getCompanyCode());
		   						   mailvo.setOperationTime(scanDat);
		   						   newMailInInventoryListVOs.add(mailvo);
		   					   }
	   					   }
	   				   }
		   		   }
		   	    }else{
		   	    	String[] childStrs = transferMailForm.getMailbag().split(",");
		   	       int size=childStrs.length;
		 	       for(int i=0;i<size;i++){
		 	    	  if (containerInInventoryList != null && containerInInventoryList.size() > 0) {
			 			  ContainerInInventoryListVO contvo = ((ArrayList<ContainerInInventoryListVO>)containerInInventoryList).get(Integer.parseInt((childStrs[i].split("~"))[0]));
	 				      Collection<MailInInventoryListVO> mailInInventoryListVOs = contvo.getMailInInventoryList();
	 				     if (mailInInventoryListVOs != null && mailInInventoryListVOs.size() > 0) {
	 					       MailInInventoryListVO mailvo = ((ArrayList<MailInInventoryListVO>)mailInInventoryListVOs).get(Integer.parseInt((childStrs[i].split("~"))[1]));
	 					   		   mailvo.setCurrentAirport(logonAttributes.getAirportCode());	   
			 					   mailvo.setCompanyCode(logonAttributes.getCompanyCode());
			 					   mailvo.setOperationTime(scanDat);
			 					   newMailInInventoryListVOs.add(mailvo);
		 					   }
		 				   }				   
	 			      }			   
		          }
		    	
				if(newMailInInventoryListVOs != null && newMailInInventoryListVOs.size() > 0) {
					
			         int errorFlag = 0;
			         
					Collection<String> originColl = new ArrayList<String>();
					
			         for (MailInInventoryListVO mailInInventoryListVO : newMailInInventoryListVOs) {
			        	 if(mailInInventoryListVO.getFromFlightNumber() == null) {
			         		errorFlag = 1;
			         		invocationContext.addError(new ErrorVO("mailtracking.defaults.inventorylist.mailnotarrived", 
				 	    		new String[] { new StringBuilder().append(mailInInventoryListVO.getOriginPAdesc()).
			         				append(" ").append(mailInInventoryListVO.getDestinationCity()).toString()}));
			         		break;
			         	}
			        	 
			         	if(!originColl.contains(mailInInventoryListVO.getDestinationCity())){
			         		originColl.add(mailInInventoryListVO.getDestinationCity());
			         	}
			         }
			         
			   		  
			         if(errorFlag == 1){			 	    	
			 	    	invocationContext.target = TARGET;
			 	    	return;
			         }
//			         
//			         Map<String,CityVO> cityMap = new HashMap<String,CityVO>();
//			         
//			 	    try {
//			         	cityMap = new AreaDelegate().validateCityCodes(
//			         			logonAttributes.getCompanyCode(),originColl);
//			         }catch (BusinessDelegateException businessDelegateException) {
//			         	handleDelegateException(businessDelegateException);
//			 			log.log(Log.INFO,"ERROR--SERVER------validateCityCodes---->>");
//			 	  	}
//			         
//			         String airport = logonAttributes.getAirportCode();
//			         if(cityMap != null && cityMap.size() > 0){
//			         	for(String Key:cityMap.keySet()){
//			         		CityVO cityVO = cityMap.get(Key);
//			       			if(airport.equals(cityVO.getNearestAirport())){
//			       				errorFlag = 1;
//			       				break;
//			         		}
//			         	}
//			         }
//			   		  
//			         if(errorFlag == 1){
//			 	    	invocationContext.addError(new ErrorVO("mailtracking.defaults.inventorylist.mailbagatdestination"));
//			 	    	invocationContext.target = TARGET;
//			 	    	return;
//			         }

			         
			    	try {
						log.log(Log.FINE,
								"newMailInInventoryListVOs ------------> ",
								newMailInInventoryListVOs);
						new MailTrackingDefaultsDelegate().transferMailBagsFromInventory(
							newMailInInventoryListVOs, toContainerVO);
					}catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
					if (errors != null && errors.size() > 0) {
						invocationContext.addAllError(errors);
						invocationContext.target = TARGET;
						return;
					}
				}
			}	    	
	
	    }
		log.log(Log.FINE,"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		transferMailForm.setCloseFlag("Y");
		                     if(printNeeded){
		                    	 invocationContext.target = getTargetPage();    
		                     }else{
		invocationContext.target = TARGET;   }                     
    	log.exiting("SaveTransferMailCommand","execute");
		
    }

	/**
	 * @author A-3227
	 *  Needed to Claculate the  Volume ... This method
	 *  rounds the specified double value to a precision specified
	 * @param value
	 * @param precision
	 * @return
	 */
    private double getScaledVolume(double value) {
    	double volume = 0.0;
    	
    	if(commodityValidationVO != null && commodityValidationVO.getDensityFactor() > 0) {
    		volume=value / commodityValidationVO.getDensityFactor();
    		
    		if(volume < 0.01){
				volume = 0.01;
			   }
    		   		   		
    	}
    	return volume;
    }
 // Added by A-5945 for finding legserial num
    private FlightValidationVO validatFlightLegSerialNumber(MailbagVO mailbagVO,ContainerVO containerVO){
    	FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		flightFilterVO.setFlightCarrierId(mailbagVO.getCarrierId());
		flightFilterVO.setFlightNumber(mailbagVO.getFlightNumber());
		flightFilterVO.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
		flightFilterVO.setStation(mailbagVO.getScannedPort());
		Collection<FlightValidationVO> flightValidationVOs = null;
		FlightValidationVO flightValidationVO= null;
		try {
			log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
		flightValidationVOs =
					new MailTrackingDefaultsDelegate().validateFlight(flightFilterVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (flightValidationVOs != null && flightValidationVOs.size() > 0) {    
for(FlightValidationVO f1 : flightValidationVOs ){
	flightValidationVO =f1;
	break;
	}
		}	
	return flightValidationVO;
    }



}
