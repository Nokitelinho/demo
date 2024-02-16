/*
 * SaveReassignDSNCommand.java Created on APR 01, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassigndsn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.EmptyULDsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignDSNSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignDSNForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author RENO K ABRAHAM
 * Command class : SaveReassignDSNCommand
 *
 * Revision History
 *
 * Version      	Date      	    Author        		Description
 *
 *  0.1         APR 04, 2008  	 RENO K ABRAHAM         Coding
 */
public class SaveReassignDSNCommand  extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILTRACKING");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "save_success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.reassigndsn";	
	   private static final String EMPTYULD_SCREEN_ID = "mailtracking.defaults.emptyulds";
	   private static final String CONST_SAVED="SAVED";
	   
	   private static final String MAIL_COMMODITY_SYS = "mailtracking.defaults.booking.commodity";
	   private CommodityValidationVO commodityValidationVO = null;
	   /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("SaveReassignDSNCommand","execute");
	    	  
	    	ReassignDSNForm reassignDSNForm = (ReassignDSNForm)invocationContext.screenModel;
	    	ReassignDSNSession reassignDSNSession = getScreenSession(MODULE_NAME,SCREEN_ID);
	    	EmptyULDsSession emptyUldsSession = getScreenSession(MODULE_NAME,EMPTYULD_SCREEN_ID);

	    	ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    	FlightValidationVO flightValidationVO = null;
	    	String[] primaryKeys = reassignDSNForm.getSelectContainer();
			String container = primaryKeys[0];
			log.log(Log.FINE, "selected container ===>", container);
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
			
			//START : Updating the ReassignedPcs And ReassignedWgt 
	    	Collection<DespatchDetailsVO> despatchDetailVOs=reassignDSNSession.getDespatchDetailsVOs();
			int k=0;
			if(despatchDetailVOs!=null && despatchDetailVOs.size()>0){
				for(DespatchDetailsVO despatchDetailsVO: despatchDetailVOs){
					if((reassignDSNForm.getReAssignedPcs()[k]!=null || !("").equals(reassignDSNForm.getReAssignedPcs()[k]))
							&& reassignDSNForm.getReAssignedPcs()[k].length()>0){
						despatchDetailsVO.setAcceptedBags(Integer.parseInt(reassignDSNForm.getReAssignedPcs()[k]));
					}else{
						despatchDetailsVO.setAcceptedBags(0);
					}
					if((reassignDSNForm.getReAssignedWt()[k]!=null || !("").equals(reassignDSNForm.getReAssignedWt()[k]))
							&& reassignDSNForm.getReAssignedWt()[k].length()>0){
						//despatchDetailsVO.setAcceptedWeight(Double.parseDouble(reassignDSNForm.getReAssignedWt()[k]));
						despatchDetailsVO.setAcceptedWeight(reassignDSNForm.getReAssignedWtMeasure()[k]);//added by A-7371
					}else{
						//despatchDetailsVO.setAcceptedWeight(0);
						despatchDetailsVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,0));//added by A-7371
					}
					k++;			
				}
		    	reassignDSNSession.setDespatchDetailsVOs(despatchDetailVOs);
			}
	    	//END
			ContainerVO containerVO = ((ArrayList<ContainerVO>)(reassignDSNSession.getContainerVOs())).get(Integer.parseInt(container));
			
			if("FLIGHT".equals(reassignDSNForm.getAssignToFlight())){
				flightValidationVO = reassignDSNSession.getFlightValidationVO();
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
			
			if("Y".equals(containerVO.getArrivedStatus())){
				invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.containerarrived",
		   				new Object[]{containerVO.getContainerNumber()}));
				reassignDSNForm.setCloseFlag("N");
		  		invocationContext.target = TARGET;
		  		return;
			}

			if(containerVO.getContainerNumber()!= null &&
					containerVO.getContainerNumber().trim().length() > 3){
				if("OFL".equals(containerVO.getContainerNumber().substring(0,3))){
					invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.offloadedmails",
							new Object[]{containerVO.getContainerNumber()}));
					reassignDSNForm.setCloseFlag("N");
					invocationContext.target = TARGET;
					return;
				}
			}
			
			if(containerVO.getContainerNumber()!= null &&
					containerVO.getContainerNumber().length() > 5){
				if("TRASH".equals(containerVO.getContainerNumber().substring(0,5))){
					invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.trashmails",
			   				new Object[]{containerVO.getContainerNumber()}));
					reassignDSNForm.setCloseFlag("N");
			  		invocationContext.target = TARGET;
			  		return;
				}
			}
			
			containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
			containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
			Collection<ContainerDetailsVO> containerDetailsVOs = null;

			Collection<DespatchDetailsVO> despatchDetailsVOs = reassignDSNSession.getDespatchDetailsVOs();
			Collection<DespatchDetailsVO> selectedDespatchDetailsVOs = new ArrayList<DespatchDetailsVO>();					
			String assignTo = reassignDSNForm.getAssignToFlight();						
			String mailbags = "";
			int errorFlag = 0;
				
			
				String selectedDespatch []=reassignDSNForm.getSelectedConsignment().split("-");
				int i=0;
				int error=-1;
				for(DespatchDetailsVO despatchDetailsVO: despatchDetailsVOs){
					for(int d=0;d<selectedDespatch.length;d++){
						log.log(Log.FINE, "Selected Mailbags ",
								selectedDespatch, d);
						if(i==Integer.parseInt(selectedDespatch[d])){
							try {
								if(despatchDetailsVO.getAcceptedPcsToDisplay()!= 0 
										//&& despatchDetailsVO.getAcceptedWgtToDisplay() != 0
										&& despatchDetailsVO.getAcceptedWgtToDisplay().getRoundedSystemValue()!= 0
										&& reassignDSNForm.getReAssignedPcs()[i]!=null 
										&& !("").equals(reassignDSNForm.getReAssignedPcs()[i]) 
										&& reassignDSNForm.getReAssignedPcs()[i].length()>0
										&& reassignDSNForm.getReAssignedWt()[i]!=null 
										&& !("").equals(reassignDSNForm.getReAssignedWt()[i])
										&& reassignDSNForm.getReAssignedWt()[i].length()>0
										&& (despatchDetailsVO.getAcceptedPcsToDisplay()>=Integer.parseInt(reassignDSNForm.getReAssignedPcs()[i]))
										//&& (despatchDetailsVO.getAcceptedWgtToDisplay()>=Double.parseDouble(reassignDSNForm.getReAssignedWt()[i]))
										&&(Measure.compareTo(despatchDetailsVO.getAcceptedWgtToDisplay(), reassignDSNForm.getReAssignedWtMeasure()[i])>=0)
										&& (Integer.parseInt(reassignDSNForm.getReAssignedPcs()[i])>0)
										&& (Double.parseDouble(reassignDSNForm.getReAssignedWt()[i])>0.0)){
									despatchDetailsVO.setAcceptedBags(Integer.parseInt(reassignDSNForm.getReAssignedPcs()[i]));
									//despatchDetailsVO.setAcceptedWeight(Double.parseDouble(reassignDSNForm.getReAssignedWt()[i]));
									despatchDetailsVO.setAcceptedWeight(reassignDSNForm.getReAssignedWtMeasure()[i]);//added by A-7371
									//despatchDetailsVO.setStatedVolume(getScaledVolume(despatchDetailsVO.getStatedWeight()));
									despatchDetailsVO.setStatedVolume(new Measure(UnitConstants.VOLUME,getScaledVolume(despatchDetailsVO.getStatedWeight().getRoundedSystemValue())));
									//despatchDetailsVO.setAcceptedVolume(getScaledVolume(despatchDetailsVO.getAcceptedWeight()));
									despatchDetailsVO.setAcceptedVolume(new Measure(UnitConstants.VOLUME,getScaledVolume(despatchDetailsVO.getAcceptedWeight().getRoundedSystemValue())));//added by A-7371
								}else{
									if(((reassignDSNForm.getReAssignedPcs()[i]==null && reassignDSNForm.getReAssignedPcs()[i].length()==0))
											||((reassignDSNForm.getReAssignedWt()[i]==null  && reassignDSNForm.getReAssignedWt()[i].length()==0))
											||("".equals(reassignDSNForm.getReAssignedPcs()[i]))
											||("".equals(reassignDSNForm.getReAssignedWt()[i]))){
										error=1;	
										break;
									} else
										{
										try {
												if ((despatchDetailsVO.getAcceptedPcsToDisplay() < Integer.parseInt(reassignDSNForm.getReAssignedPcs()[i])) || Measure.compareTo(despatchDetailsVO.getAcceptedWgtToDisplay(), reassignDSNForm.getReAssignedWtMeasure()[i]) < 0) {
												error=2;
												break;
												} else if ((Integer.parseInt(reassignDSNForm.getReAssignedPcs()[i]) == 0) || (Double.parseDouble(reassignDSNForm.getReAssignedWt()[i]) == 0.0)) {
												error=3;
												break;
											}else {
												error=0;
												break;
											}
										} catch (NumberFormatException e) {
											// TODO Auto-generated catch block
											log.log(Log.SEVERE,"NumberFormatException",e.getMessage());
										} catch (UnitException e) {
											// TODO Auto-generated catch block
											log.log(Log.SEVERE,"UnitException",e.getMessage());
											}
										}
									
								}
							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								log.log(Log.SEVERE,"NumberFormatException",e.getMessage());
							} catch (UnitException e) {
								// TODO Auto-generated catch block
								log.log(Log.SEVERE,"UnitException",e.getMessage());
							}
							selectedDespatchDetailsVOs.add(despatchDetailsVO);
						}
					}
					i++;
					if(error==0){
			    	    invocationContext.addError(new ErrorVO("mailtracking.defaults.reassigndsn.reassigndetailsnotvalid"));
			    	    reassignDSNForm.setCloseFlag("N");
			    	    reassignDSNForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			    	    invocationContext.target = TARGET;
			    	    return;						
					}else if(error==1){
			    	    invocationContext.addError(new ErrorVO("mailtracking.defaults.reassigndsn.reassigndetailsnull"));
			    	    reassignDSNForm.setCloseFlag("N");
			    	    reassignDSNForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			    	    invocationContext.target = TARGET;
			    	    return;						
					}else if(error==2){
			    	    invocationContext.addError(new ErrorVO("mailtracking.defaults.reassigndsn.reassigndetailsgreaterthanoriginal"));
			    	    reassignDSNForm.setCloseFlag("N");
			    	    reassignDSNForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			    	    invocationContext.target = TARGET;
			    	    return;						
					}else if(error==3){
			    	    invocationContext.addError(new ErrorVO("mailtracking.defaults.reassigndsn.reassigndetailszero"));
			    	    reassignDSNForm.setCloseFlag("N");
			    	    reassignDSNForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			    	    invocationContext.target = TARGET;
			    	    return;						
					}
				}
				
			    log.log(Log.FINE, "Full Despatches", despatchDetailsVOs);
				log.log(Log.FINE, "Selected Despatches ",
						selectedDespatchDetailsVOs);
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
							}
						  }
						}else{
							if(despatchDetailsVO.getCarrierId() == containerVO.getCarrierId()
								&& despatchDetailsVO.getContainerNumber().equals(containerVO.getContainerNumber())
								&& despatchDetailsVO.getDestination().equals(containerVO.getFinalDestination())){
									errorFlag = 1;
									if("".equals(mailbags)){
										mailbags = pk;
					       			}else{
					       				mailbags = new StringBuilder(mailbags).append(",").append(pk).toString();
					       			}
							}
						}
						
					}
				}
				
				if(errorFlag == 1){
		    	    invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.reassignsamecontainer",
		    	    		new Object[]{mailbags,containerVO.getContainerNumber()}));
		    	    reassignDSNForm.setCloseFlag("N");
		    	    reassignDSNForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		    	    invocationContext.target = TARGET;
		    	    return;
		       }
				
			  try {
				  containerDetailsVOs = 
					  new MailTrackingDefaultsDelegate().reassignDSNs(selectedDespatchDetailsVOs,containerVO);
				  
		      }catch (BusinessDelegateException businessDelegateException) {
		    		errors = handleDelegateException(businessDelegateException);
		      }
		      if (errors != null && errors.size() > 0) {
			    	invocationContext.addAllError(errors);
			    	reassignDSNForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			    	reassignDSNForm.setCloseFlag("N");
			    	invocationContext.target = TARGET;
			    	return;
		      }
		      log.log(Log.FINE, "containerDetailsVOs ------------------>>",
					containerDetailsVOs);
			if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
			    	  emptyUldsSession.setContainerDetailsVOs(containerDetailsVOs);
			    	  reassignDSNForm.setCloseFlag("SHOWPOPUP");
			    	  invocationContext.target = TARGET;
			    	  return;
		      }
			
	    	reassignDSNForm.setCloseFlag("Y");
	    	reassignDSNForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	invocationContext.target = TARGET;
	    	log.exiting("SaveReassignDSNCommand","execute");
	    	
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
    	double volume = 0.0D;
    	if(commodityValidationVO != null && commodityValidationVO.getDensityFactor() > 0) {
    		/*try{
				volume=UnitFormatter.getRoundedValue(UnitConstants.VOLUME, UnitConstants.VOLUME_UNIT_CUBIC_CENTIMETERS, value / commodityValidationVO.getDensityFactor());
				}catch(UnitException unitException) {
					unitException.getErrorCode();
			   }*/
    		volume= value / commodityValidationVO.getDensityFactor();//added by A-7371
    		//volume = getScaledValue(value / commodityValidationVO.getDensityFactor(), 2);							
    		if(MailConstantsVO.NO_VOLUME != volume) {
    			if(MailConstantsVO.MINIMUM_VOLUME > volume) {
    				return MailConstantsVO.MINIMUM_VOLUME;
    			}
    		}
    	}
    	return volume;
    }



}
