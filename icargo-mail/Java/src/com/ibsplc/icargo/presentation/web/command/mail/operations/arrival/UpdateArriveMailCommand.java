/*
 * UpdateArriveMailCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class UpdateArriveMailCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET = "success";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";
   private static final String SYSPAR_DEFUNIT_WEIGHT= "mail.operations.defaultcaptureunit";
   private static final String STNPAR_DEFUNIT_WEIGHT = "station.defaults.unit.weight";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("UpdateArriveMailCommand","execute");

    	MailArrivalForm mailArrivalForm =
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

    	ContainerDetailsVO containerDetailsVO = mailArrivalSession.getContainerDetailsVO();
    	if(mailArrivalForm.getPol() == null || mailArrivalForm.getPol().trim().length()==0){
		if(containerDetailsVO!=null && containerDetailsVO.getPol()!=null )
			{
			mailArrivalForm.setPol(containerDetailsVO.getPol());
			}
		else{
			FlightValidationVO flightValidationVO = mailArrivalSession.getFlightValidationVO();
			if(flightValidationVO!=null && flightValidationVO.getFlightRoute()!=null && flightValidationVO.getFlightRoute().trim().length()>0){
				 String route = flightValidationVO.getFlightRoute();
				 String[] routeArr = route.split("-");
				 String pol = "";
				 for(int i=routeArr.length-1;i>=0;i--){
					 if(routeArr[i].equals(logonAttributes.getAirportCode())){
						 pol = routeArr[i-1];
						 break;
					 }
				 }
				mailArrivalForm.setPol(pol);
			}
		}
    	}
    	String[] opFlg =  mailArrivalForm.getOperationalFlag();
    	if(opFlg != null && opFlg.length > 0){
    		for(int i = 0; i<opFlg.length; i++){
        		if("NOOP".equals(mailArrivalForm.getOperationalFlag()[i])){
        		if(containerDetailsVO != null){
        			if(containerDetailsVO.getAcceptedFlag() == null){
        				containerDetailsVO.setOperationFlag(mailArrivalForm.getOperationalFlag()[i]);
        				}
        			}
            	}
        	}
    	}
    	//added by A-7540 for ICRD-274933 starts
    	Map systemParameters = null;  
        SharedDefaultsDelegate sharedDelegate =new SharedDefaultsDelegate();
        try {
        	systemParameters=sharedDelegate.findSystemParameterByCodes(getSystemParameterCodes());
        } catch (BusinessDelegateException e) {
               e.getMessage();
        }//added by A-7540 for ICRD-274933 ends
        AreaDelegate areaDelegate = new AreaDelegate();
		Map stationParameters = null; 
	    	String stationCode = logonAttributes.getStationCode();
    	String companyCode=logonAttributes.getCompanyCode();
    	try {
			stationParameters = areaDelegate.findStationParametersByCode(companyCode, stationCode, getStationParameterCodes());
		} catch (BusinessDelegateException e1) {			
			e1.getMessage();
		}
    	if ((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT)!=null){
    		mailArrivalForm.setDefWeightUnit((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT));
    		}
    		else{
    			mailArrivalForm.setDefWeightUnit((String)stationParameters.get(STNPAR_DEFUNIT_WEIGHT));
    		}     
    	containerDetailsVO.setContainerNumber(mailArrivalForm.getContainerNo().trim().toUpperCase());
    	containerDetailsVO.setPol(mailArrivalForm.getPol());
    	containerDetailsVO.setContainerType(mailArrivalForm.getContainerType());
    	log.log(Log.FINE, "mailArrivalForm.getPaBuilt() ...in command",
				mailArrivalForm.getPaBuilt());
		containerDetailsVO.setPaBuiltFlag(mailArrivalForm.getPaBuilt());
  	    /*START
  	     * Added FOR SB ULD
  	     */
    	if(mailArrivalForm.getDeliveredStatus()!=null
  	    		 && MailConstantsVO.FLAG_YES.equals(mailArrivalForm.getDeliveredStatus())){
  	    	 containerDetailsVO.setDeliveredStatus(MailConstantsVO.FLAG_YES);
  	     }else if(MailConstantsVO.FLAG_YES.equals(containerDetailsVO.getDeliveredStatus())){
  	    	 //No need to update the delivered status
  	     }
    	else{
  	    	 containerDetailsVO.setDeliveredStatus(MailConstantsVO.FLAG_NO);
  	     }
  	     if(mailArrivalForm.getArrivedStatus()!=null
  	    		 && MailConstantsVO.FLAG_YES.equals(mailArrivalForm.getArrivedStatus())){
  	  	    containerDetailsVO.setArrivedStatus(MailConstantsVO.FLAG_YES);
  	     }else if(MailConstantsVO.FLAG_YES.equals(containerDetailsVO.getArrivedStatus())){
  	    	 //No need to update the arrived status
  	     }
  	     else{
  	  	    containerDetailsVO.setArrivedStatus(MailConstantsVO.FLAG_NO);
  	     }
  	    //END
		String remarks = mailArrivalForm.getRemarks();
		if (remarks != null && remarks.trim().length() > 0) {
    	    containerDetailsVO.setRemarks(mailArrivalForm.getRemarks());
		}
		//Added for ICRD-182251 starts
		updateContainerDetailsWithFlight(mailArrivalSession,containerDetailsVO);
		//Added for ICRD-182251 ends
		log.log(Log.FINE,
				"containerDetailsVO.setContainerNumber ...in command",
				mailArrivalForm.getContainerNo());
		//		Updating Session
    	Collection<ContainerDetailsVO> containerDetailsVOs =  mailArrivalSession.getContainerDetailsVOs();
    	Collection<ContainerDetailsVO> newContainerDetailsVOs = new ArrayList<ContainerDetailsVO>();
    	int flag = 0;
    	if(containerDetailsVOs != null && containerDetailsVOs.size() > 0){
    		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
    			if(containerDtlsVO.getContainerNumber().equals(containerDetailsVO.getContainerNumber())
    					&& mailArrivalForm.getPol().equals(containerDtlsVO.getPol())){
					newContainerDetailsVOs.add(containerDetailsVO);
    				flag = 1;
    			}else{
    				log.log(Log.FINE, "NO MATCH!!!");
    				newContainerDetailsVOs.add(containerDtlsVO);
    			}
    		}
    		if(flag == 0){
    			log.log(Log.FINE, "NEW ONE!!!");
    			newContainerDetailsVOs.add(containerDetailsVO);
    		}
    	}else{
    		log.log(Log.FINE, "ONLY ONE VO!!!");
    		newContainerDetailsVOs.add(containerDetailsVO);
    	}
    	mailArrivalSession.setContainerDetailsVOs(newContainerDetailsVOs);
    	log.log(Log.FINE, "newContainerDetailsVOs ...in command",
				newContainerDetailsVOs);
		log.log(Log.FINE,
				"containerDetailsVO.getContainerNumber ...in command",
				containerDetailsVO.getContainerNumber());
		Collection<DespatchDetailsVO> despatchDetailsVOs =  containerDetailsVO.getDesptachDetailsVOs();
    	Collection<MailbagVO> mailbagVOs =  containerDetailsVO.getMailDetails();
    	/*
		 * For Despatch Tab
		 */
    	String[]conDocNo = mailArrivalForm.getConDocNo();
		String[]despatchDate = mailArrivalForm.getDespatchDate();
		String[]despatchPA = mailArrivalForm.getDespatchPA();
		String[]despatchOOE = mailArrivalForm.getDespatchOOE();
		String[]despatchDOE = mailArrivalForm.getDespatchDOE();
		String[]despatchCat = mailArrivalForm.getDespatchCat();
		String[]despatchClass = mailArrivalForm.getDespatchClass();
		String[]despatchSC = mailArrivalForm.getDespatchSC();
		String[]despatchDSN = mailArrivalForm.getDespatchDSN();
		String[]despatchYear = mailArrivalForm.getDespatchYear();
		String[]mftdBags = mailArrivalForm.getManifestedBags();
	   	String[]mftdWt = mailArrivalForm.getManifestedWt();
		String[]recvdBags = mailArrivalForm.getReceivedBags();
		String[]recvdWt = mailArrivalForm.getReceivedWt();
		String[]delvdBags = mailArrivalForm.getDeliveredBags();
		String[]delvdWt = mailArrivalForm.getDeliveredWt();
		/*
		 * For Mail Tag Tab
		 */
		String[]mailOOE = mailArrivalForm.getMailOOE();
		String[]mailDOE = mailArrivalForm.getMailDOE();
		String[]mailCat = mailArrivalForm.getMailCat();
		String[]mailSC = mailArrivalForm.getMailSC();
		String[]mailYr = mailArrivalForm.getMailYr();
		String[]mailDSN = mailArrivalForm.getMailDSN();
		String[]mailRSN = mailArrivalForm.getMailRSN();
		String[]mailHNI = mailArrivalForm.getMailHNI();
		String[]mailRI = mailArrivalForm.getMailRI();
		String[]mailWt = mailArrivalForm.getMailWt();
		String[]weightUnit = mailArrivalForm.getWeightUnit();
		String[]mailbagId = mailArrivalForm.getMailbagId();//Added for ICRD-205067
		//Added by A-5945 for ICRD-104487
		String[]mailVol = mailArrivalForm.getMailVolume();
		String[]mailScanDate = mailArrivalForm.getMailScanDate();
		String[]mailScanTime = mailArrivalForm.getMailScanTime();
		String[]damaged = mailArrivalForm.getMailDamaged();
		String[]received = mailArrivalForm.getMailReceived();
		String[]delivered = mailArrivalForm.getMailDelivered();

		String[] mailDamaged = checkBoxUpdate(mailbagVOs,damaged,mailOOE);
		String[] mailReceived = checkBoxUpdate(mailbagVOs,received,mailOOE);
		String[] mailDelivered = checkBoxUpdate(mailbagVOs,delivered,mailOOE);

		String[] despatchOpFlag = mailArrivalForm.getDespatchOpFlag();
		String[] mailOpFlag = mailArrivalForm.getMailOpFlag();

		String arrivedStatus=mailArrivalForm.getArrivedStatus();
		String deliveredStatus=mailArrivalForm.getDeliveredStatus();
		String[] mailCompanyCode = mailArrivalForm.getMailCompanyCode();
		String[] sealNo = mailArrivalForm.getSealNo();
		String[] arrivalSealNo = mailArrivalForm.getArrivalSealNo();
		Measure[] volume = mailArrivalForm.getMailVolumeMeasure();
		
		//Added as a part of ICRD-197419 
		String[] mailRemarks = mailArrivalForm.getMailRemarks();
		
		int size = 0;
    	if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
			   size = despatchDetailsVOs.size();
    	}
		Collection<DespatchDetailsVO> newDespatchVOs = new ArrayList<DespatchDetailsVO>();
		for(int index=0; index<despatchOpFlag.length;index++){
			if(index >= size){
				if(!"NOOP".equals(despatchOpFlag[index])){
		    	DespatchDetailsVO newDespatchVO = new DespatchDetailsVO();
					if("B".equals(containerDetailsVO.getContainerType())){
						newDespatchVO.setContainerNumber(containerDetailsVO.getContainerNumber());
			    	}
					newDespatchVO.setCompanyCode(logonAttributes.getCompanyCode());
					newDespatchVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());
					newDespatchVO.setAcceptedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));

					/*
					 * Added By RENO K ABRAHAM
					 * As a part of performance Upgrade
					 * START.
					 */
					newDespatchVO.setDisplayLabel("N");
					//END

					newDespatchVO.setOperationalFlag(despatchOpFlag[index]);
					if(conDocNo != null) {
						if(conDocNo[index] != null && !("".equals(conDocNo[index]))) {
							newDespatchVO.setConsignmentNumber(conDocNo[index].toUpperCase());
						}
					}
					if(despatchDate != null) {
						if(despatchDate[index] != null && !("".equals(despatchDate[index]))) {
							LocalDate cd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
							newDespatchVO.setConsignmentDate(cd.setDate(despatchDate[index]));
						}
					}
					if(despatchPA != null) {
						if(despatchPA[index] != null && !("".equals(despatchPA[index]))) {
							newDespatchVO.setPaCode(despatchPA[index].toUpperCase());
						}
					}
					if(despatchOOE != null) {
						if(despatchOOE[index] != null && !("".equals(despatchOOE[index]))) {
							newDespatchVO.setOriginOfficeOfExchange(despatchOOE[index].toUpperCase());
						}
					}
					if(despatchDOE != null) {
						if(despatchDOE[index] != null && !("".equals(despatchDOE[index]))) {
							newDespatchVO.setDestinationOfficeOfExchange(despatchDOE[index].toUpperCase());
						}
					}
					if(despatchCat != null) {
						if(despatchCat[index] != null && !("".equals(despatchCat[index]))) {
							newDespatchVO.setMailCategoryCode(despatchCat[index]);
						}
					}

//					added by anitha for pk change-start
					if(despatchClass != null) {
						if(despatchClass[index] != null && !("".equals(despatchClass[index]))){
							newDespatchVO.setMailClass(despatchClass[index].toUpperCase());
						}
					}
					if(despatchSC != null) {
						if(despatchSC[index] != null && !("".equals(despatchSC[index]))	) {
							newDespatchVO.setMailSubclass(despatchSC[index].toUpperCase());
						}
					}
					if((despatchSC == null || despatchSC[index] == null || ("".equals(despatchSC[index])))) {
						if(despatchClass != null) {
							if(despatchClass[index] != null && !("".equals(despatchClass[index]))){
								newDespatchVO.setMailSubclass(despatchClass[index].toUpperCase().concat("_"));
							}
					}
					}
					//added by anitha for pk change-end


					if(despatchDSN != null) {
						if(despatchDSN[index] != null && !("".equals(despatchDSN[index]))) {
							newDespatchVO.setDsn(despatchDSN[index].toUpperCase());
						}
					}
					if(despatchYear != null) {
						if(despatchYear[index] != null && !("".equals(despatchYear[index]))) {
							newDespatchVO.setYear(Integer.parseInt(despatchYear[index]));
						}
			        }
					if(mftdBags != null) {
						if(mftdBags[index] != null && !("".equals(mftdBags[index]))) {
							newDespatchVO.setAcceptedBags(Integer.parseInt(mftdBags[index]));
						}
			        }
					if(mftdWt != null) {
						if(mftdWt[index] != null && !("".equals(mftdWt[index]))) {
							//newDespatchVO.setAcceptedWeight(Double.parseDouble(mftdWt[index]));
							newDespatchVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mftdWt[index])));//added by A-7371
						}
			        }
					if(recvdBags != null) {
						if(recvdBags[index] != null && !("".equals(recvdBags[index]))) {
							if(newDespatchVO.getReceivedBags() == 0
									&& Integer.parseInt(recvdBags[index]) > 0){
								newDespatchVO.setReceivedDate(new LocalDate(
										logonAttributes.getAirportCode(),Location.ARP,true));
							}
							if(!"I".equals(newDespatchVO.getOperationalFlag())){
								if(newDespatchVO.getReceivedBags() != (Integer.parseInt(recvdBags[index]))){
									newDespatchVO.setOperationalFlag("U");
								}
							}
							newDespatchVO.setReceivedBags(Integer.parseInt(recvdBags[index]));
						}
			        }
					if(recvdWt != null) {
						if(recvdWt[index] != null && !("".equals(recvdWt[index]))) {
							if(!"I".equals(newDespatchVO.getOperationalFlag())){
								//if(newDespatchVO.getReceivedWeight() != (Double.parseDouble(recvdWt[index]))){
								if(newDespatchVO.getReceivedWeight().getRoundedSystemValue() != (Double.parseDouble(recvdWt[index]))){//added by A-7550
									newDespatchVO.setOperationalFlag("U");
								}
							}
							//newDespatchVO.setReceivedWeight(Double.parseDouble(recvdWt[index]));
							newDespatchVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(recvdWt[index])));//added by A-7371
						}
			        }
					if(delvdBags != null) {
						if(delvdBags[index] != null && !("".equals(delvdBags[index]))) {
							if(!"I".equals(newDespatchVO.getOperationalFlag())){
								if(newDespatchVO.getDeliveredBags() != (Integer.parseInt(delvdBags[index]))){
									newDespatchVO.setOperationalFlag("U");
								}
							}
							newDespatchVO.setDeliveredBags(Integer.parseInt(delvdBags[index]));
						}
			        }
					if(delvdWt != null) {
						if(delvdWt[index] != null && !("".equals(delvdWt[index]))) {
							if(!"I".equals(newDespatchVO.getOperationalFlag())){
								//if(newDespatchVO.getDeliveredWeight() != (Double.parseDouble(delvdWt[index]))){
									if(newDespatchVO.getDeliveredWeight().getRoundedSystemValue() != (Double.parseDouble(delvdWt[index]))){//added by A-7371
									newDespatchVO.setOperationalFlag("U");
								}
							}
							//newDespatchVO.setDeliveredWeight(Double.parseDouble(delvdWt[index]));
							newDespatchVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(delvdWt[index])));//added by A-7371
						}
			        }

					newDespatchVOs.add(newDespatchVO);
				}
			}else{
				int count = 0;
				if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
				   for(DespatchDetailsVO newDespatchVO:despatchDetailsVOs){
					   if(count == index){
						   if(!"NOOP".equals(despatchOpFlag[index])){
							   if("N".equals(despatchOpFlag[index])){
								   newDespatchVO.setOperationalFlag(null);
							   }else{
								   newDespatchVO.setOperationalFlag(despatchOpFlag[index]);
							   }

							   if(conDocNo != null) {
									if(conDocNo[index] != null && !("".equals(conDocNo[index]))) {
										newDespatchVO.setConsignmentNumber(conDocNo[index].toUpperCase());
									}
								}
								if(despatchDate != null) {
									if(despatchDate[index] != null && !("".equals(despatchDate[index]))) {
										LocalDate cd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
										newDespatchVO.setConsignmentDate(cd.setDate(despatchDate[index]));
									}
								}
								if(despatchPA != null) {
									if(despatchPA[index] != null && !("".equals(despatchPA[index]))) {
										newDespatchVO.setPaCode(despatchPA[index].toUpperCase());
									}
								}
								if(despatchOOE != null) {
									if(despatchOOE[index] != null && !("".equals(despatchOOE[index]))) {
										newDespatchVO.setOriginOfficeOfExchange(despatchOOE[index].toUpperCase());
									}
								}
								if(despatchDOE != null) {
									if(despatchDOE[index] != null && !("".equals(despatchDOE[index]))) {
										newDespatchVO.setDestinationOfficeOfExchange(despatchDOE[index].toUpperCase());
									}
								}
								if(despatchCat != null) {
									if(despatchCat[index] != null && !("".equals(despatchCat[index]))) {
										newDespatchVO.setMailCategoryCode(despatchCat[index]);
									}
								}


//								added by anitha for pk change-start
								if(despatchClass != null) {
									if(despatchClass[index] != null && !("".equals(despatchClass[index]))){
										newDespatchVO.setMailClass(despatchClass[index].toUpperCase());
									}
								}
								if(despatchSC != null) {
									if(despatchSC[index] != null && !("".equals(despatchSC[index]))	) {
										newDespatchVO.setMailSubclass(despatchSC[index].toUpperCase());
									}
								}
								if((despatchSC == null || despatchSC[index] == null || ("".equals(despatchSC[index])))) {
									if(despatchClass != null) {
										if(despatchClass[index] != null && !("".equals(despatchClass[index]))){
											newDespatchVO.setMailSubclass(despatchClass[index].toUpperCase().concat("_"));
										}
								}
								}
								//added by anitha for pk change-end


								if(despatchDSN != null) {
									if(despatchDSN[index] != null && !("".equals(despatchDSN[index]))) {
										newDespatchVO.setDsn(despatchDSN[index].toUpperCase());
									}
								}
								if(despatchYear != null) {
									if(despatchYear[index] != null && !("".equals(despatchYear[index]))) {
										newDespatchVO.setYear(Integer.parseInt(despatchYear[index]));
									}
						        }
								if(mftdBags != null) {
									if(mftdBags[index] != null && !("".equals(mftdBags[index]))) {
										newDespatchVO.setAcceptedBags(Integer.parseInt(mftdBags[index]));
									}
						        }
								if(mftdWt != null) {
									if(mftdWt[index] != null && !("".equals(mftdWt[index]))) {
										//newDespatchVO.setAcceptedWeight(Double.parseDouble(mftdWt[index]));
										newDespatchVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mftdWt[index])));//added by A-7371
									}
						        }
								if(recvdBags != null) {
									if(recvdBags[index] != null && !("".equals(recvdBags[index]))) {
										if(newDespatchVO.getReceivedBags() == 0
												&& Integer.parseInt(recvdBags[index]) > 0){
											newDespatchVO.setReceivedDate(new LocalDate(
													logonAttributes.getAirportCode(),Location.ARP,true));
										}
										if(!"I".equals(newDespatchVO.getOperationalFlag())){
											if(newDespatchVO.getReceivedBags() != (Integer.parseInt(recvdBags[index]))){
												newDespatchVO.setOperationalFlag("U");
											}
										}
										newDespatchVO.setReceivedBags(Integer.parseInt(recvdBags[index]));
									}
						        }
								if(recvdWt != null) {
									if(recvdWt[index] != null && !("".equals(recvdWt[index]))) {
										if(!"I".equals(newDespatchVO.getOperationalFlag())){
											//if(newDespatchVO.getReceivedWeight() != (Double.parseDouble(recvdWt[index]))){
											if(newDespatchVO.getReceivedWeight().getRoundedSystemValue() != (Double.parseDouble(recvdWt[index]))){//added by A-7371
												newDespatchVO.setOperationalFlag("U");
											}
										}
										//newDespatchVO.setReceivedWeight(Double.parseDouble(recvdWt[index]));
										newDespatchVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(recvdWt[index])));//added by A-7371
									}

						        }
								if(delvdBags != null) {
									if(delvdBags[index] != null && !("".equals(delvdBags[index]))) {
										if(!"I".equals(newDespatchVO.getOperationalFlag())){
											if(newDespatchVO.getDeliveredBags() != (Integer.parseInt(delvdBags[index]))){
												newDespatchVO.setOperationalFlag("U");
											}
										}
										newDespatchVO.setDeliveredBags(Integer.parseInt(delvdBags[index]));
									}else{
										newDespatchVO.setDeliveredBags(0);
									}

						        }
								if(delvdWt != null) {
									if(delvdWt[index] != null && !("".equals(delvdWt[index]))) {
										if(!"I".equals(newDespatchVO.getOperationalFlag())){
											//if(newDespatchVO.getDeliveredWeight() != (Double.parseDouble(delvdWt[index]))){
											if(newDespatchVO.getDeliveredWeight().getRoundedSystemValue() != (Double.parseDouble(delvdWt[index]))){//added by A-7371
												newDespatchVO.setOperationalFlag("U");
											}
										}
										//newDespatchVO.setDeliveredWeight(Double.parseDouble(delvdWt[index]));
										newDespatchVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(delvdWt[index])));//added by A-7371
									}else{
										//newDespatchVO.setDeliveredWeight(0.0);
										newDespatchVO.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,0.0));//added by A-7371
									}
						        }

							   newDespatchVOs.add(newDespatchVO);
						   }
					   }
					   count++;
				   }
				}
			}
		}

		//Mailbags Updation

		size = 0;
    	if(mailbagVOs != null && mailbagVOs.size() > 0){
			   size = mailbagVOs.size();
    	}
    	Collection<MailbagVO> newMailbagVOs = new ArrayList<MailbagVO>();
		for(int index=0; index<mailOpFlag.length;index++){
			if(index >= size){
				if(!"NOOP".equals(mailOpFlag[index])){
			   	MailbagVO newMailbagVO = new MailbagVO();

					newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
			    	newMailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
			    	newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
			    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
			    	newMailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			    	newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);
			    	newMailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
			    	newMailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
			    	newMailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
			    	newMailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
			    	newMailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
			    	newMailbagVO.setContainerType(containerDetailsVO.getContainerType());
			    	newMailbagVO.setAcceptanceFlag("N");
			    	newMailbagVO.setFlightDate((mailArrivalSession.getMailArrivalVO()).getArrivalDate());
			    	if(containerDetailsVO.getPou()!=null && containerDetailsVO.getPou().trim().length()>0)
			    	{
			    	newMailbagVO.setPou(containerDetailsVO.getPou());
			    	}
			    	else{
			    		newMailbagVO.setPou(logonAttributes.getAirportCode());
			    		containerDetailsVO.setPou(logonAttributes.getAirportCode());
			    	}
					newMailbagVO.setOperationalFlag(mailOpFlag[index]);
					//Added for bug ICRD-96160 by A-5526 starts
					newMailbagVO.setPaBuiltFlag(containerDetailsVO.getPaBuiltFlag());
					//Added for bug ICRD-96160 by A-5526 ends

					/*
					 * Added By RENO K ABRAHAM
					 * As a part of performance Upgrade
					 * START.
					 */
						newMailbagVO.setDisplayLabel("N");
					//END

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
					if(mailWt != null) {
						if(mailWt[index] != null && !("".equals(mailWt[index])) &&
								weightUnit[index] != null && !("".equals(weightUnit[index]))) {//Added.A-8164.ICRD-331323
						//	Measure strWt=new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailWt[index]));
							Measure strWt = new Measure(UnitConstants.MAIL_WGT,0.0,Double.parseDouble(mailWt[index]),weightUnit[index]);
                            double mailWtdbl = Double.parseDouble(mailWt[index]);
                            if("popup".equals(mailArrivalForm.getFromScreen())) {
                                mailWtdbl = mailWtdbl/10;
                            }
							//Measure strWt=new Measure(UnitConstants.MAIL_WGT, mailWtdbl);
							newMailbagVO.setStrWeight(strWt);//added by A-7371
							newMailbagVO.setWeight(strWt);
						
						}
					}
					//Added by A-5945 for ICRD-104487
					if(mailVol!= null){
						if(mailVol[index] != null && !("".equals(mailVol[index]))) {
							//newMailbagVO.setVolume(Double.parseDouble(mailVol[index]));
							newMailbagVO.setVolume(volume[index]); //Modified by A-7540					
							}
					}
					//modified for icrd-81527 by a-4810
					if(mailScanDate != null && mailScanTime != null) {
						if((mailScanDate[index] != null && mailScanTime[index] != null)){

							if(("".equals(mailScanDate[index])) || ("".equals(mailScanTime[index])) ){
								if( "U".equals(mailOpFlag[index]) || "N".equals(mailOpFlag[index])) {
								 newMailbagVO.setScannedDate(null);
								}
							}
							else{
								LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
								String scanDT = new StringBuilder(mailScanDate[index]).append(" ")
								        .append(mailScanTime[index]).append(":00").toString();
								newMailbagVO.setScannedDate(sd.setDateAndTime(scanDT,false));
							}
						}
					}

					if(mailDamaged != null) {
						if(mailDamaged[index] != null && !("".equals(mailDamaged[index]))) {
							if(!"I".equals(newMailbagVO.getOperationalFlag())){
								if(!mailDamaged[index].equals(newMailbagVO.getDamageFlag())){
									newMailbagVO.setOperationalFlag("U");
									containerDetailsVO.setOperationFlag("U");
								}
							}
							newMailbagVO.setDamageFlag(mailDamaged[index]);
						}
					}

					if(mailReceived != null) {
						if(mailReceived[index] != null && !("".equals(mailReceived[index]))) {
							if(!"I".equals(newMailbagVO.getOperationalFlag())){
								if(!mailReceived[index].equals(newMailbagVO.getArrivedFlag())){
									newMailbagVO.setOperationalFlag("U");
									//added by anitha
									containerDetailsVO.setOperationFlag("U");
								}
							}
							newMailbagVO.setArrivedFlag(mailReceived[index]);
						}
					}

					if(mailDelivered != null) {
						if(mailDelivered[index] != null && !("".equals(mailDelivered[index]))) {
							if(!"I".equals(newMailbagVO.getOperationalFlag())){

	                          if(newMailbagVO.getDeliveredFlag() == null) {
	                            if("Y".equals(mailDelivered[index])) {
	                              newMailbagVO.setOperationalFlag("U");
	                            }
	                          } else  {
	                            if(!mailDelivered[index].equals(
	                              newMailbagVO.getDeliveredFlag())){
									newMailbagVO.setOperationalFlag("U");
	                            }
	                          }
							}
							newMailbagVO.setDeliveredFlag(mailDelivered[index]);
						}
					}

					if(mailCompanyCode != null) {
						if(mailCompanyCode[index] != null ) {

							if(!"I".equals(newMailbagVO.getOperationalFlag())){
								if(newMailbagVO.getMailCompanyCode()==null){
									newMailbagVO.setMailCompanyCode("");
								}
								if(!mailCompanyCode[index].equals(newMailbagVO.getMailCompanyCode())){
							newMailbagVO.setOperationalFlag("U");

								}

								}
							newMailbagVO.setMailCompanyCode(mailCompanyCode[index]);
						}
					}

					if(sealNo != null) {
						if(sealNo[index] != null && !("".equals(sealNo[index]))) {
							newMailbagVO.setSealNumber(sealNo[index]);
						}
					}
					if(arrivalSealNo != null) {
						if(arrivalSealNo[index] != null && !("".equals(arrivalSealNo[index]))) {
							newMailbagVO.setArrivalSealNumber(arrivalSealNo[index]);
						}
					}
					
					//added as a part of ICRD-197419 by a-7540
					if(mailRemarks != null) {
						if(mailRemarks[index] != null && !("".equals(mailRemarks[index]))) {
							newMailbagVO.setMailRemarks(mailRemarks[index]);
							//newMailbagVO.setOperationalFlag("U");Commented as part of ICRD-267851
						}
					
					}
					newMailbagVOs.add(newMailbagVO);
				}	
				
			}else{
				int count = 0;
				if(mailbagVOs != null && mailbagVOs.size() > 0){
				   for(MailbagVO newMailbagVO:mailbagVOs){
					   if(count == index){
						   if(!"NOOP".equals(mailOpFlag[index])){
							   if("N".equals(mailOpFlag[index])){
								   newMailbagVO.setOperationalFlag(null);
							   }else{
								   newMailbagVO.setOperationalFlag(mailOpFlag[index]);
							   }
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
								if(mailWt != null) {
									if(mailWt[index] != null && !("".equals(mailWt[index]))&&
											weightUnit[index] != null && !("".equals(weightUnit[index]))) {//Added.A-8164.ICRD-331323
										Measure strWt = new Measure(UnitConstants.MAIL_WGT,0.0,Double.parseDouble(mailWt[index]),weightUnit[index]);
									    double mailWtdbl = Double.parseDouble(mailWt[index]);
									    if("popup".equals(mailArrivalForm.getFromScreen())) {
                                            mailWtdbl = mailWtdbl/10;
                                        }
										//Measure strWt=new Measure(UnitConstants.MAIL_WGT, mailWtdbl);
										newMailbagVO.setStrWeight(strWt);//added by A-7371
										newMailbagVO.setWeight(strWt);
										
									}
								}
								if(mailVol!= null){
									if(mailVol[index] != null && !("".equals(mailVol[index]))) {
										//newMailbagVO.setVolume(Double.parseDouble(mailVol[index]));
										newMailbagVO.setVolume(volume[index]); //Modified by A-7540
										}
								}

								if(mailScanDate != null && mailScanTime != null) {
									if((mailScanDate[index] != null && mailScanTime[index] != null)){

										if(("".equals(mailScanDate[index])) || ("".equals(mailScanTime[index])) ){
											if( "U".equals(mailOpFlag[index]) || "N".equals(mailOpFlag[index])) {
											 newMailbagVO.setScannedDate(null);
											}
										}
										else{
											LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
											String scanDT = new StringBuilder(mailScanDate[index]).append(" ")
											        .append(mailScanTime[index]).append(":00").toString();
											newMailbagVO.setScannedDate(sd.setDateAndTime(scanDT,false));
										}
									}
								}
								/*if(mailScanDate != null && mailScanTime != null) {
									if((mailScanDate[index] != null && !("".equals(mailScanDate[index])))
										&& (mailScanTime[index] != null && !("".equals(mailScanTime[index])))) {
										LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
										String scanDT = new StringBuilder(mailScanDate[index]).append(" ")
										        .append(mailScanTime[index]).append(":00").toString();
										newMailbagVO.setScannedDate(sd.setDateAndTime(scanDT,false));
									}
								}*/

								if(mailDamaged != null) {
									if(mailDamaged[index] != null && !("".equals(mailDamaged[index]))) {
										if(!"I".equals(newMailbagVO.getOperationalFlag())){
											if(!mailDamaged[index].equals(newMailbagVO.getDamageFlag())){
												newMailbagVO.setOperationalFlag("U");
												containerDetailsVO.setOperationFlag("U");
											}
										}
										newMailbagVO.setDamageFlag(mailDamaged[index]);
									}
								}

								if(mailReceived != null) {
									if(mailReceived[index] != null && !("".equals(mailReceived[index]))) {
										if(!"I".equals(newMailbagVO.getOperationalFlag())){
											if(!mailReceived[index].equals(newMailbagVO.getArrivedFlag())){
												newMailbagVO.setOperationalFlag("U");
												//added by anitha
												containerDetailsVO.setOperationFlag("U");
												if(newMailbagVO.getArrivedFlag()!=null && MailConstantsVO.FLAG_YES.equals(newMailbagVO.getArrivedFlag())){
													newMailbagVO.setUndoArrivalFlag(MailConstantsVO.FLAG_YES);
													containerDetailsVO.setOperationFlag("U");
												}
											}
										}
										newMailbagVO.setArrivedFlag(mailReceived[index]);
									}
								}

								if(mailDelivered != null) {
									if(mailDelivered[index] != null && !("".equals(mailDelivered[index]))) {
										if(!"I".equals(newMailbagVO.getOperationalFlag())){

				                          if(newMailbagVO.getDeliveredFlag() == null) {
				                            if("Y".equals(mailDelivered[index])) {
				                              newMailbagVO.setOperationalFlag("U");
				                            }
				                          } else  {
				                            if(!mailDelivered[index].equals(
				                              newMailbagVO.getDeliveredFlag())){
												newMailbagVO.setOperationalFlag("U");
				                            }
				                          }
										}
										newMailbagVO.setDeliveredFlag(mailDelivered[index]);
									}
								}

								if(mailCompanyCode != null) {
									if(mailCompanyCode[index] != null) {

										if(!"I".equals(newMailbagVO.getOperationalFlag())){
											if(newMailbagVO.getMailCompanyCode()==null){
												newMailbagVO.setMailCompanyCode("");
											}
											if(!mailCompanyCode[index].equals(newMailbagVO.getMailCompanyCode())){
											newMailbagVO.setOperationalFlag("U");

											}

											}
										newMailbagVO.setMailCompanyCode(mailCompanyCode[index]);
									}
								}
								if(sealNo != null) {
									if(sealNo[index] != null && !("".equals(sealNo[index]))) {
										newMailbagVO.setSealNumber(sealNo[index]);
										/**
										 * commented for import issue with seal number
										 */
										//newMailbagVO.setOperationalFlag("U");
									}
								}
								if(arrivalSealNo != null) {
									if(arrivalSealNo[index] != null && !("".equals(arrivalSealNo[index]))) {
										newMailbagVO.setArrivalSealNumber(arrivalSealNo[index]);
										newMailbagVO.setOperationalFlag("U");
									}
								}
								
								//added as a part of ICRD-197419 by a-7540
								
								if(mailRemarks != null) {
												if(mailRemarks[index] != null && !("".equals(mailRemarks[index]))) {
													newMailbagVO.setMailRemarks(mailRemarks[index]);
													newMailbagVO.setOperationalFlag("U");
												}
											}

							   newMailbagVOs.add(newMailbagVO);
						   }
					   }
					   count++;
				   }
				}
			}
			}

		containerDetailsVO.setDesptachDetailsVOs(newDespatchVOs);
		containerDetailsVO.setMailDetails(newMailbagVOs);
		log.log(Log.FINE, "---containerDetailsVO---in ... update...command",
				containerDetailsVO);
		mailArrivalSession.setContainerDetailsVO(containerDetailsVO);

		Collection<ContainerDetailsVO> containerDetailsVOArr = new ArrayList<ContainerDetailsVO>();
		containerDetailsVOArr.add(containerDetailsVO);
		//MailArrivalVO mailArrivalVO = new MailArrivalVO();
		//mailArrivalVO.setContainerDetails(containerDetailsVOArr);
		//mailArrivalSession.setMailArrivalVO(mailArrivalVO);
    	invocationContext.target = TARGET;
       	log.exiting("UpdateArriveMailCommand","execute");

    }


	/**
	 * This method is to update checkbox values.
	 * @param mailbagVOs
	 * @param damaged
	 * @return String[]
	 */
	private String[] checkBoxUpdate(Collection<MailbagVO> mailbagVOs,
								   String[] damaged,String[] mailOOE) {
		    String[] mailDamaged = new String[10];
		    if(mailOOE != null) {
		    	mailDamaged = new String[mailOOE.length];
		    	if(damaged != null) {
		    		int flag = 0;
		    		int mailSize = mailOOE.length;
		    		for(int i=0;i<mailSize;i++) {
		    			for(int j=0;j<damaged.length;j++){
	    					if(i == Integer.parseInt(damaged[j])){
	    						mailDamaged[i] = "Y";
	    						flag = 1;
	    					}
		    			}
		    			if(flag == 1){
		    				flag = 0;
		    			}else{
		    				mailDamaged[i] = "N";
		    			}
		    		}
		    	}else{
		    		int mailSize = mailOOE.length;
		    		for(int i=0;i<mailSize;i++) {
		    			mailDamaged[i] = "N";
		    		}
		    	}
			}
		   return mailDamaged;
	    }
	/**
	 * This method is to update container details with Flight Info
	 * Added as part of ICRD-182251
	 * Prevent Flight validate from failing in validateFlightForBulk(MailArrival)
	 * @param mailArrivalSession
	 * @param containerDetailsVO
	 */
	private void updateContainerDetailsWithFlight(MailArrivalSession mailArrivalSession,
			ContainerDetailsVO containerDetailsVO){
		MailArrivalFilterVO mailArrivalFilterVO=mailArrivalSession.getMailArrivalFilterVO();
		containerDetailsVO.setCompanyCode(mailArrivalFilterVO.getCompanyCode());
		containerDetailsVO.setFlightNumber(mailArrivalFilterVO.getFlightNumber());
		containerDetailsVO.setCarrierId(mailArrivalFilterVO.getCarrierId());
		containerDetailsVO.setFlightSequenceNumber(mailArrivalFilterVO.getFlightSequenceNumber());
		containerDetailsVO.setFlightDate(mailArrivalFilterVO.getFlightDate());
		
	    }

	/**
	 * 
	 * @return
	 */
    private Collection<String> getSystemParameterCodes(){
             Collection<String> systemParameterCodes = new ArrayList<String>();
               systemParameterCodes.add(SYSPAR_DEFUNIT_WEIGHT);
               
               return systemParameterCodes;
      }
    private Collection<String> getStationParameterCodes(){
        Collection<String> systemParameterCodes = new ArrayList<String>();
          systemParameterCodes.add(STNPAR_DEFUNIT_WEIGHT);
          
          return systemParameterCodes;
 }
}
