/*
 * UpdateAcceptMailCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class UpdateAcceptMailCommand extends BaseCommand {
	
	@Override
	public boolean breakOnInvocationFailure() {		
		return true;
	}
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
   private static final String SYSPAR_DEFUNIT_WEIGHT = "mail.operations.defaultcaptureunit";  
   private static final String STNPAR_DEFUNIT_WEIGHT = "station.defaults.unit.weight";
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("UpdateAcceptMailCommand","execute");
    	  
    	MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		//added by A_8353 for ICRD-274933 starts
		Map systemParameters = null;  
		SharedDefaultsDelegate sharedDelegate =new SharedDefaultsDelegate();  
		try {
			systemParameters=sharedDelegate.findSystemParameterByCodes(getSystemParameterCodes());
		} catch (BusinessDelegateException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		AreaDelegate areaDelegate = new AreaDelegate();
		Map stationParameters = null; 
	    	String stationCode = logonAttributes.getStationCode();
    	String companyCode=logonAttributes.getCompanyCode();
    	try {
			stationParameters = areaDelegate.findStationParametersByCode(companyCode, stationCode, getStationParameterCodes());
		} catch (BusinessDelegateException e1) {
			
			e1.getMessage();
		}
		//added by A_8353 for ICRD-274933 ends
		
    	ContainerDetailsVO containerDetailsVO = mailAcceptanceSession.getContainerDetailsVO();
    	
    	containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
    	containerDetailsVO.setAssignedUser(logonAttributes.getUserId().toUpperCase());
		
		//Warehouse
    	if(mailAcceptanceForm.getWarehouse() != null){
			if(!mailAcceptanceForm.getWarehouse().equals(containerDetailsVO.getWareHouse())){
				containerDetailsVO.setWareHouse(mailAcceptanceForm.getWarehouse());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
				}
			}
    	}
		
		//Location
    	if(mailAcceptanceForm.getLocation() != null){
			if(!mailAcceptanceForm.getLocation().equals(containerDetailsVO.getLocation())){
				containerDetailsVO.setLocation(mailAcceptanceForm.getLocation());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
				}
			}
    	}
    	
		//Remarks
    	if(mailAcceptanceForm.getRemarks() != null){
			if(!mailAcceptanceForm.getRemarks().equals(containerDetailsVO.getRemarks())){
				containerDetailsVO.setRemarks(mailAcceptanceForm.getRemarks());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
				} 
			}
    	} 
    	  
		//Container Type
    	if(mailAcceptanceForm.getContainerType() != null){
			if(!mailAcceptanceForm.getContainerType().equals(containerDetailsVO.getContainerType())){
				containerDetailsVO.setContainerType(mailAcceptanceForm.getContainerType());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
				}
			}
    	}
    	
		//POU
    	if(mailAcceptanceForm.getPou() != null){
			if(!mailAcceptanceForm.getPou().equals(containerDetailsVO.getPou())){
				containerDetailsVO.setPou(mailAcceptanceForm.getPou());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
				}
			}
    	}
		
		//Destination
    	if(mailAcceptanceForm.getDestn() != null){
			if(!mailAcceptanceForm.getDestn().equals(containerDetailsVO.getDestination())){
				containerDetailsVO.setDestination(mailAcceptanceForm.getDestn());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
				}
			}
    	}
		
		//PaBuilt Flag
    	if(mailAcceptanceForm.getPaBuilt() != null){
			if(!mailAcceptanceForm.getPaBuilt().equals(containerDetailsVO.getPaBuiltFlag())){
				containerDetailsVO.setPaBuiltFlag(mailAcceptanceForm.getPaBuilt());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
				}
			}
    	}
    	
    	
    	//Container Jny IDR Flag
    	if(mailAcceptanceForm.getContainerJnyId() != null){
			if(!mailAcceptanceForm.getContainerJnyId().equals(containerDetailsVO.getContainerJnyId())){
				containerDetailsVO.setContainerJnyId(mailAcceptanceForm.getContainerJnyId());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
				}
			}
    	}
    	
    	//PA CODE
    	if(mailAcceptanceForm.getPaCode() != null){
			if(!mailAcceptanceForm.getPaCode().equals(containerDetailsVO.getPaCode())){
				containerDetailsVO.setPaCode(mailAcceptanceForm.getPaCode());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
				}
			}
    	}
    	
    	
		//Transfer From Carrier
    	if(mailAcceptanceForm.getCarrier() != null){
			if(!mailAcceptanceForm.getCarrier().equals(containerDetailsVO.getTransferFromCarrier())){
				containerDetailsVO.setTransferFromCarrier(mailAcceptanceForm.getCarrier());
				if(!"I".equals(containerDetailsVO.getContainerOperationFlag())
						&& !"N".equals(containerDetailsVO.getContainerOperationFlag())){
					containerDetailsVO.setContainerOperationFlag("U");
				}
			}
    	}
    	
      	Collection<DespatchDetailsVO> despatchDetailsVOs =  containerDetailsVO.getDesptachDetailsVOs();
    	Collection<MailbagVO> mailbagVOs =  containerDetailsVO.getMailDetails();
    	/*
		 * For Despatch Tab
		 */
    	String[]conDocNo = mailAcceptanceForm.getConDocNo();
		String[]despatchDate = mailAcceptanceForm.getDespatchDate();
		String[]despatchPA = mailAcceptanceForm.getDespatchPA();
		String[]despatchOOE = mailAcceptanceForm.getDespatchOOE();
		String[]despatchDOE = mailAcceptanceForm.getDespatchDOE();
		String[]despatchCat = mailAcceptanceForm.getDespatchCat();
		String[]despatchClass = mailAcceptanceForm.getDespatchClass();
		//added by anitha for pk change
		String[]despatchSC = mailAcceptanceForm.getDespatchSC();
		String[]despatchDSN = mailAcceptanceForm.getDespatchDSN();
		String[]despatchYear = mailAcceptanceForm.getDespatchYear();
		String[]statedNoBags = mailAcceptanceForm.getStatedNoBags();
	   	String[]statedWt = mailAcceptanceForm.getStatedWt();
		String[]accNoBags = mailAcceptanceForm.getAccNoBags();
		String[]accWt = mailAcceptanceForm.getAccWt();
		String[]stdVolume = mailAcceptanceForm.getStdVolume();
		String[]accVolume = mailAcceptanceForm.getAccVolume();
		/*
		 * For Mail Tag Tab
		 */
		String[]mailOOE = mailAcceptanceForm.getMailOOE();
		String[]mailDOE = mailAcceptanceForm.getMailDOE();
		String[]mailCat = mailAcceptanceForm.getMailCat();
		String[]mailSC = mailAcceptanceForm.getMailSC();
		String[]mailYr = mailAcceptanceForm.getMailYr();
		String[]mailDSN = mailAcceptanceForm.getMailDSN();
		String[]mailRSN = mailAcceptanceForm.getMailRSN();
		String[]mailHNI = mailAcceptanceForm.getMailHNI();
		String[]mailRI = mailAcceptanceForm.getMailRI();
		Measure[]mailWt = mailAcceptanceForm.getMailWtMeasure();//modified by A-7371
		String[] stringWgt=mailAcceptanceForm.getMailWt();
		String[]mailScanDate = mailAcceptanceForm.getMailScanDate();
		String[]mailScanTime = mailAcceptanceForm.getMailScanTime();
		String[]mailCarrier = mailAcceptanceForm.getMailCarrier();
		String[]damaged = mailAcceptanceForm.getMailDamaged();
		String[]mailCompanyCode = mailAcceptanceForm.getMailCompanyCode();
		Measure[]volume = mailAcceptanceForm.getMailVolumeMeasure();//modified by A-8353
		String[]bellyCartId = mailAcceptanceForm.getMailCartId();
		String[] weightUnit=mailAcceptanceForm.getWeightUnit();
		String[] despatchOpFlag = mailAcceptanceForm.getDespatchOpFlag();
		String[] mailOpFlag = mailAcceptanceForm.getMailOpFlag();
		
		String[] mailDamaged = new String[100];
		String[] sealNo = mailAcceptanceForm.getSealNo(); 
		//Added as part of ICRD-205027 starts
		String[] mailbagId = mailAcceptanceForm.getMailbagId();
		//Added as part of ICRD-205027 ends
		
		//Added as a part of ICRD-197419
		String[] mailRemarks =mailAcceptanceForm.getMailRemarks();
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
		
		
		int size = 0;
    	if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
			   size = despatchDetailsVOs.size();
    	}
    	Collection<DespatchDetailsVO> newDespatchVOs = new ArrayList<DespatchDetailsVO>();
    	if(despatchOpFlag != null && despatchOpFlag.length > 0) {
			for(int index=0; index<despatchOpFlag.length;index++){
				if(index >= size){
					if(!"NOOP".equals(despatchOpFlag[index])){
						DespatchDetailsVO newDespatchVO = new DespatchDetailsVO();
						newDespatchVO.setCompanyCode(logonAttributes.getCompanyCode());
						newDespatchVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());
						newDespatchVO.setAcceptedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
						newDespatchVO.setOperationalFlag(despatchOpFlag[index]);	
						newDespatchVO.setContainerNumber(containerDetailsVO.getContainerNumber());
						newDespatchVO.setContainerType(containerDetailsVO.getContainerType());
						newDespatchVO.setUldNumber(containerDetailsVO.getContainerNumber());
						/*
						 * Added By RENO K ABRAHAM
						 * As a part of performance Upgrade
						 * START.
						 */
						newDespatchVO.setDisplayLabel("N");
						//END
					
						
					
						if(conDocNo != null) {
							if(conDocNo[index] != null && !("".equals(conDocNo[index]))) {
								newDespatchVO.setConsignmentNumber(conDocNo[index].toUpperCase());
							}
						}
						if(despatchDate != null) {
							if(despatchDate[index] != null && !("".equals(despatchDate[index]))) {
								LocalDate cd = 
			                        new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
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
						//added by anitha for pk change-start
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
						try{
						if(despatchYear != null) {
							if(despatchYear[index] != null && !("".equals(despatchYear[index]))) {
								newDespatchVO.setYear(Integer.parseInt(despatchYear[index]));
							}
				        }
						if(statedNoBags != null) {
							if(statedNoBags[index] != null && !("".equals(statedNoBags[index]))) {
								newDespatchVO.setStatedBags(Integer.parseInt(statedNoBags[index]));
							}
				        }
						if(statedWt != null) {
							if(statedWt[index] != null && !("".equals(statedWt[index]))) {
								//newDespatchVO.setStatedWeight(Double.parseDouble(statedWt[index]));
								newDespatchVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(statedWt[index])));//added by A-7371
							}
				        }
						if(stdVolume != null) {
							if(stdVolume[index] != null && !("".equals(stdVolume[index]))) {
								newDespatchVO.setStatedVolume(new Measure(UnitConstants.VOLUME,Double.parseDouble(stdVolume[index])));
							}
				        }
						if(accVolume != null) {
							if(accVolume[index] != null && !("".equals(accVolume[index]))) {
								newDespatchVO.setAcceptedVolume(new Measure(UnitConstants.VOLUME,Double.parseDouble(accVolume[index])));
							}
				        }
						if(accNoBags != null) {
							if(accNoBags[index] != null && !("".equals(accNoBags[index]))) {
								if(!"I".equals(newDespatchVO.getOperationalFlag())){
									if(newDespatchVO.getAcceptedBags() != (Integer.parseInt(accNoBags[index]))){
										newDespatchVO.setOperationalFlag("U");
									}
								}
								newDespatchVO.setAcceptedBags(Integer.parseInt(accNoBags[index]));
							}
				        }
						if(accWt != null) {
							if(accWt[index] != null && !("".equals(accWt[index]))) {
								if(!"I".equals(newDespatchVO.getOperationalFlag())){
									//if(newDespatchVO.getAcceptedWeight() != (Double.parseDouble(accWt[index]))){
									if(newDespatchVO.getAcceptedWeight().getRoundedSystemValue() != (Double.parseDouble(accWt[index]))){//added by A-7550
										newDespatchVO.setOperationalFlag("U");
									}
								}
								//newDespatchVO.setAcceptedWeight(Double.parseDouble(accWt[index]));
								newDespatchVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(accWt[index])));//added by A-7550
							}
				        }
						}catch (NumberFormatException ne) {
							log.log(Log.FINE,  "NumberFormatException");
						}
						newDespatchVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
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
											LocalDate cd = 
					                            new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
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
									//added by anitha for pk change-start
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
									try{
									if(despatchYear != null) {
										if(despatchYear[index] != null && !("".equals(despatchYear[index]))) {
											newDespatchVO.setYear(Integer.parseInt(despatchYear[index]));
										}
							        }
									if(statedNoBags != null) {
										if(statedNoBags[index] != null && !("".equals(statedNoBags[index]))) {
											newDespatchVO.setStatedBags(Integer.parseInt(statedNoBags[index]));
										}
							        }
									if(statedWt != null) {
										if(statedWt[index] != null && !("".equals(statedWt[index]))) {
											//newDespatchVO.setStatedWeight(Double.parseDouble(statedWt[index]));
											newDespatchVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(statedWt[index])));//added by A-7550
										}
							        }
									if(stdVolume != null) {
										if(stdVolume[index] != null && !("".equals(stdVolume[index]))) {
											newDespatchVO.setStatedVolume(new Measure(UnitConstants.VOLUME,Double.parseDouble(stdVolume[index]))); //Added by A-7550
										}
							        }
									if(accVolume != null) {
										if(accVolume[index] != null && !("".equals(accVolume[index]))) {
											newDespatchVO.setAcceptedVolume(new Measure(UnitConstants.VOLUME,Double.parseDouble(accVolume[index]))); //Added by A-7550
										}
							        }
									if(accNoBags != null) {
										if(accNoBags[index] != null && !("".equals(accNoBags[index]))) {
											if(!"I".equals(newDespatchVO.getOperationalFlag())){
												if(newDespatchVO.getAcceptedBags() != (Integer.parseInt(accNoBags[index]))){
													newDespatchVO.setOperationalFlag("U");
												}
											}
											newDespatchVO.setAcceptedBags(Integer.parseInt(accNoBags[index]));
										}
							        }
									if(accWt != null) {
										if(accWt[index] != null && !("".equals(accWt[index]))) {
											if(!"I".equals(newDespatchVO.getOperationalFlag())){
												//if(newDespatchVO.getAcceptedWeight() != (Double.parseDouble(accWt[index]))){
												if(newDespatchVO.getAcceptedWeight().getRoundedSystemValue() != (Double.parseDouble(accWt[index]))){//added by A-7550
													newDespatchVO.setOperationalFlag("U");
												}
											}
											//newDespatchVO.setAcceptedWeight(Double.parseDouble(accWt[index]));
											newDespatchVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(accWt[index])));//added by A-7550
										}
							        }
									}catch (NumberFormatException ne) {
										log.log(Log.FINE,  "NumberFormatException");
									}
									newDespatchVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
								   newDespatchVOs.add(newDespatchVO);
							   }
									
						   }
							   
						   count++;
					   }
						   
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
    	Collection<MailbagVO> deletedMailbagVOs = containerDetailsVO.getDeletedMailDetails();
    	if(deletedMailbagVOs==null ){    
    		deletedMailbagVOs=new ArrayList<MailbagVO>();     
    	}
    	
    	if(mailOpFlag != null && mailOpFlag.length > 0) {
			for(int index=0; index<mailOpFlag.length;index++){
				if(index >= size){
					if(!"NOOP".equals(mailOpFlag[index])){
						MailbagVO newMailbagVO = new MailbagVO();
						
						//newMailbagVO.setMailRemarks(mailAcceptanceForm.getMailRemarks());
						
						newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
				    	newMailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
				    	newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
				    	newMailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
				    	newMailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				    	newMailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
				    	newMailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
				    	newMailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
				    	newMailbagVO.setArrivedFlag("N");
				    	newMailbagVO.setDeliveredFlag("N");
				    	newMailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
				    	newMailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
				    	newMailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
				    	newMailbagVO.setContainerType(containerDetailsVO.getContainerType());
				    	newMailbagVO.setPou(containerDetailsVO.getPou());
						newMailbagVO.setOperationalFlag(mailOpFlag[index]);
					//	newMailbagVO.setMailRemarks(containerDetailsVO.getMailRemarks());
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
						//Added as part of ICRD-205027 starts
						if(mailbagId != null) {
							if(mailbagId[index] != null && !("".equals(mailbagId[index]))) {
								newMailbagVO.setMailbagId(mailbagId[index]);
							}
						}
						//Added as part of ICRD-205027 ends
						if(mailWt != null) {
							if(mailWt[index] != null && !("".equals(mailWt[index]))) {
								//newMailbagVO.setWeight(mailWt[index]);//added by A-7371
								newMailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,0.0,Double.parseDouble(stringWgt[index]),weightUnit[index]));
								newMailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,0.0,Double.parseDouble(stringWgt[index]),weightUnit[index]));
								
							/*	if(!"AA".equals(logonAttributes.getCompanyCode())){
									if(stringWgt[index]!=null && stringWgt[index].trim().length() > 0 &&  mailWt[index].getSystemValue() == Double.parseDouble(stringWgt[index])){
										newMailbagVO.setWeight(new Measure(UnitConstants.WEIGHT,Double.parseDouble(stringWgt[index])/10));
									}
									else{
										newMailbagVO.setWeight(mailWt[index]);
									}
									} commented by A-8353*/
								
								
							}
						}
						if(volume != null) {
							if(volume[index] != null && !("".equals(volume[index]))) {
								newMailbagVO.setVolume(volume[index]); //modified by A-8353
							}
						}
						if(mailScanDate != null && mailScanTime != null) {
							if((mailScanDate[index] != null && !("".equals(mailScanDate[index])))
								&& (mailScanTime[index] != null && !("".equals(mailScanTime[index])))) {
								LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
								String scanDT = new StringBuilder(mailScanDate[index]).append(" ")
								        .append(mailScanTime[index]).append(":00").toString();
								if(!"I".equals(newMailbagVO.getOperationalFlag())){
									String scanDate = newMailbagVO.getScannedDate().toDisplayFormat("dd-MMM-yyyy").toUpperCase();
									String scanTime = newMailbagVO.getScannedDate().toDisplayFormat("HH:mm");
									if(!(mailScanDate[index]).equalsIgnoreCase(scanDate)){
										newMailbagVO.setOperationalFlag("U");
									}
									if(!(mailScanTime[index]).equalsIgnoreCase(scanTime)){
										newMailbagVO.setOperationalFlag("U");
									}
								}
								newMailbagVO.setScannedDate(sd.setDateAndTime(scanDT,false));
							}
						}
						
						if(mailCarrier != null) {
							if(mailCarrier[index] != null && !("".equals(mailCarrier[index]))) {
								if(!"I".equals(newMailbagVO.getOperationalFlag())){
									if(!(mailCarrier[index].toUpperCase()).equals(newMailbagVO.getTransferFromCarrier())){
										newMailbagVO.setOperationalFlag("U");
									}
								}
								newMailbagVO.setTransferFromCarrier(mailCarrier[index].toUpperCase());
							}
						}
						if(newMailbagVO.getTransferFromCarrier() == null 
						 || "".equals(newMailbagVO.getTransferFromCarrier())){
							if(mailAcceptanceForm.getCarrier() != null && !("".equals(mailAcceptanceForm.getCarrier()))) {
								if(!"I".equals(newMailbagVO.getOperationalFlag())){
									if(!(mailAcceptanceForm.getCarrier().toUpperCase()).equals(newMailbagVO.getTransferFromCarrier())){
										newMailbagVO.setOperationalFlag("U");
									}
								}
							}
							newMailbagVO.setTransferFromCarrier(mailAcceptanceForm.getCarrier().toUpperCase());
						}
						if(mailDamaged != null) {
							if(mailDamaged[index] != null && !("".equals(mailDamaged[index]))) {
								if(!"I".equals(newMailbagVO.getOperationalFlag())){
									if(!mailDamaged[index].equals(newMailbagVO.getDamageFlag())){
										newMailbagVO.setOperationalFlag("U");
									}
								}
								newMailbagVO.setDamageFlag(mailDamaged[index]);
							}
						}
						if(mailCompanyCode != null) {
							if(mailCompanyCode[index] != null ) {
								
								if(!"I".equals(newMailbagVO.getOperationalFlag()) && !"D".equals(newMailbagVO.getOperationalFlag())){
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
						if(bellyCartId != null) {
							if(bellyCartId[index] != null && !("".equals(bellyCartId[index]))) {
								newMailbagVO.setBellyCartId(bellyCartId[index]);
								//newMailbagVO.setOperationalFlag("I");
							}
						}
						if(sealNo!=null) {      
							if(sealNo[index]!=null && !("".equals(sealNo[index]))) {
								newMailbagVO.setSealNumber(sealNo[index]);					
							} 	
						} 
						//Added as a part of ICRD-197419
						if(mailRemarks != null) {
							if(mailRemarks[index] != null && !("".equals(mailRemarks[index]))) {
								newMailbagVO.setMailRemarks(mailRemarks[index]);
								//newMailbagVO.setOperationalFlag("U"); 	
							}
						}	
						
						if("add".equals(mailAcceptanceForm.getSuggestValue())&&MailConstantsVO.OPERATION_FLAG_DELETE.equals(mailOpFlag[index])){
							deletedMailbagVOs.add(newMailbagVO);      
							        
						}else{
						newMailbagVOs.add(newMailbagVO);
						}
					}
				}else{
					int count = 0;
					if(mailbagVOs != null && mailbagVOs.size() > 0){
					   for(MailbagVO newMailbagVO:mailbagVOs){
						   //Added as part of bug ICRD-124832 by A-5526
						   /*String outerpk = newMailbagVO.getOoe()
							+ newMailbagVO.getDoe()
							+ newMailbagVO.getMailCategoryCode()
							+ newMailbagVO.getMailSubclass()
							+ newMailbagVO.getYear()
							+ newMailbagVO.getDespatchSerialNumber() 
							+ newMailbagVO.getReceptacleSerialNumber()
							+ newMailbagVO.getHighestNumberedReceptacle()
							+ newMailbagVO.getRegisteredOrInsuredIndicator()
							+ newMailbagVO.getWeight();  */    
						   
						   if(count == index){
							   if(!"NOOP".equals(mailOpFlag[index])){
								 //Added as part of bug ICRD-124832 by A-5526
								   /*String innerpk = mailOOE[index]
															+ mailDOE[index]
															+ mailCat[index]
															+ mailSC[index]
															+ mailYr[index]
															+ mailDSN[index] 
															+ mailRSN[index]
															+ mailHNI[index]
															+ mailRI[index]
															+ (Double.parseDouble(mailWt[index]))/10;   
								 //Added as part of bug ICRD-124832 by A-5526
								  if(outerpk.equals(innerpk)) {*/
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
									//Added as part of ICRD-205027 starts
									if(mailbagId != null) {
										if(mailbagId[index] != null && !("".equals(mailbagId[index]))) {
											newMailbagVO.setMailbagId(mailbagId[index]);
										}
									}
									//Added as part of ICRD-205027 ends
									if(mailWt != null) {
										if(mailWt[index] != null && !("".equals(mailWt[index]))) {
											//newMailbagVO.setWeight((Double.parseDouble(mailWt[index]))/10);
											
											newMailbagVO.setStrWeight(mailWt[index]);
										}
									}
									if(volume != null) {
										if(volume[index] != null && !("".equals(volume[index]))) {
											newMailbagVO.setVolume(volume[index]);   //modified by A-8353
										}
									}
									if(mailScanDate != null && mailScanTime != null) {
										if((mailScanDate[index] != null && !("".equals(mailScanDate[index])))
											&& (mailScanTime[index] != null && !("".equals(mailScanTime[index])))) {
											LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
											String scanDT = new StringBuilder(mailScanDate[index]).append(" ")
											        .append(mailScanTime[index]).append(":00").toString();
											if(!"I".equals(newMailbagVO.getOperationalFlag())){
												String scanDate = newMailbagVO.getScannedDate().toDisplayFormat("dd-MMM-yyyy").toUpperCase();
												String scanTime = newMailbagVO.getScannedDate().toDisplayFormat("HH:mm");
												if(!(mailScanDate[index]).equalsIgnoreCase(scanDate)){
													newMailbagVO.setOperationalFlag("U");
												}
												if(!(mailScanTime[index]).equalsIgnoreCase(scanTime)){
													newMailbagVO.setOperationalFlag("U");
												}
											}
											newMailbagVO.setScannedDate(sd.setDateAndTime(scanDT,false));
										}
									}
									
									if(mailCarrier != null) {
										if(mailCarrier[index] != null && !("".equals(mailCarrier[index]))) {
											if(!"I".equals(newMailbagVO.getOperationalFlag())){
												if(!(mailCarrier[index].toUpperCase()).equals(newMailbagVO.getTransferFromCarrier())){
													newMailbagVO.setOperationalFlag("U");
												}
											}
											newMailbagVO.setTransferFromCarrier(mailCarrier[index].toUpperCase());
										}
									}
									if(newMailbagVO.getTransferFromCarrier() == null 
									 || "".equals(newMailbagVO.getTransferFromCarrier())){
										if(mailAcceptanceForm.getCarrier() != null && !("".equals(mailAcceptanceForm.getCarrier()))) {
											if(!"I".equals(newMailbagVO.getOperationalFlag())){
												if(!(mailAcceptanceForm.getCarrier().toUpperCase()).equals(newMailbagVO.getTransferFromCarrier())){
													newMailbagVO.setOperationalFlag("U");
												}
											}
										}
										newMailbagVO.setTransferFromCarrier(mailAcceptanceForm.getCarrier().toUpperCase());
									}
									if(mailDamaged != null) {
										if(mailDamaged[index] != null && !("".equals(mailDamaged[index]))) {
											if(!"I".equals(newMailbagVO.getOperationalFlag())){
												if(!mailDamaged[index].equals(newMailbagVO.getDamageFlag())){
													newMailbagVO.setOperationalFlag("U");
												}
											}
											newMailbagVO.setDamageFlag(mailDamaged[index]);
										}
									}
									if(mailCompanyCode != null) {
										if(mailCompanyCode[index] != null ) {
											
											if(!"I".equals(newMailbagVO.getOperationalFlag()) && !"D".equals(newMailbagVO.getOperationalFlag())){      
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
									
									if(bellyCartId != null) {
										if(bellyCartId[index] != null && !("".equals(bellyCartId[index]))) {
											newMailbagVO.setBellyCartId(bellyCartId[index]);
											//newMailbagVO.setOperationalFlag("I");
										}
									}
									if(sealNo != null) {
										if(sealNo[index] != null && !("".equals(sealNo[index]))) {
											newMailbagVO.setSealNumber(sealNo[index]); 
											//newMailbagVO.setOperationalFlag("I");  
										}
									}
									
									//Added as a part of ICRD-197419
									if(mailRemarks != null) {
										if(mailRemarks[index] != null && !("".equals(mailRemarks[index]))) {
											newMailbagVO.setMailRemarks(mailRemarks[index]);
											//newMailbagVO.setOperationalFlag("U"); 
										}
									}
									
									// Modified as part of CRQ ICRD-118163 by A-5526 starts 
									if("add".equals(mailAcceptanceForm.getSuggestValue())&&MailConstantsVO.OPERATION_FLAG_DELETE.equals(mailOpFlag[index])){
										
								    	        
										deletedMailbagVOs.add(newMailbagVO);
								    	 
										       
									}else{
								   
								   newMailbagVOs.add(newMailbagVO);
									}// Added as part of CRQ ICRD-118163 by A-5526 ends
							  /* }//Added as part of bug ICRD-124832 by A-5526
								  else{
									  count--;      
								  }*/
							   }
						   }
						   count++;
					   
					   }
					}
				}
			}
		}
		
		containerDetailsVO.setDesptachDetailsVOs(newDespatchVOs);
		// Added as part of CRQ ICRD-118163 by A-5526 starts
		if(!"add".equals(mailAcceptanceForm.getSuggestValue()) && deletedMailbagVOs!=null && deletedMailbagVOs.size()>0){
			for(MailbagVO deletemail:containerDetailsVO.getDeletedMailDetails()){        
				
		    	 
				newMailbagVOs.add(deletemail);       
		    	 
		    	 
			}
			deletedMailbagVOs=new ArrayList<MailbagVO>();        
			}
		
		
		if(newMailbagVOs!=null && newMailbagVOs.size()>0){
			StringBuilder validateDeleteFlag=new StringBuilder();
			 int mailCount=0; 
		 for(MailbagVO newMailbagVO:newMailbagVOs){
			                
				
			 String flagValue="";        
				if(!"D".equals(newMailbagVO.getOperationalFlag())){
				if(newMailbagVO.getScannedPort()!=null && newMailbagVO.getScannedPort().trim().length()>0){
					
					if(MailConstantsVO.MAIL_STATUS_RETURNED.equals(newMailbagVO.getMailStatus())){
						flagValue=flagValue.concat(String.valueOf(mailCount)).concat("R");
						
					}
					
					else if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(newMailbagVO.getMailStatus()) ||
							MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(newMailbagVO.getMailStatus()) ||
							MailConstantsVO.FLAG_YES.equals(newMailbagVO.getArrivedFlag())||
							!logonAttributes.getAirportCode().equals(newMailbagVO.getScannedPort())){     
						flagValue=flagValue.concat(String.valueOf(mailCount)).concat("A");      
						
						//break;
					}            
					else if(MailConstantsVO.MAIL_STATUS_DELIVERED.equals(newMailbagVO.getMailStatus())){
						flagValue=flagValue.concat(String.valueOf(mailCount)).concat("D");
						
					}  
				else if("I".equals(newMailbagVO.getOperationalStatus())){      
					flagValue=flagValue.concat(String.valueOf(mailCount)).concat("A");                   
					
					
						  
				}
				
				else{
					flagValue=flagValue.concat(String.valueOf(mailCount)).concat("Y"); 
						
				}
				       
				}
				validateDeleteFlag.append(flagValue);
   		     mailCount++;
				}
				
			 if("D".equals(newMailbagVO.getOperationalFlag())){
			 deletedMailbagVOs.add(newMailbagVO);             
			 }      
		 }
		 mailAcceptanceForm.setDeleteAgreeFlag(validateDeleteFlag.toString());
		}
		if("add".equals(mailAcceptanceForm.getSuggestValue()) ){      
			if(newMailbagVOs!=null && newMailbagVOs.size()>0){
				 for(MailbagVO newMailbagVO:newMailbagVOs){
					 if("D".equals(newMailbagVO.getOperationalFlag())){
						 newMailbagVOs.remove(newMailbagVO);             
					 }      
				 }
			}
			}
		// Added as part of CRQ ICRD-118163 by A-5526 ends
		containerDetailsVO.setMailDetails(newMailbagVOs);
		// Added as part of CRQ ICRD-118163 by A-5526 starts
		containerDetailsVO.setDeletedMailDetails(deletedMailbagVOs);
		// Added as part of CRQ ICRD-118163 by A-5526 ends
		log.log(Log.FINE, "containerDetailsVO ---.in Update Command",
				containerDetailsVO);
		mailAcceptanceSession.setContainerDetailsVO(containerDetailsVO);
		if ((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT)!=null){
		mailAcceptanceForm.setDefWeightUnit((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT));//added by A_8353 for ICRD-274933 
		}
		else{
			mailAcceptanceForm.setDefWeightUnit((String)stationParameters.get(STNPAR_DEFUNIT_WEIGHT));
		}
		invocationContext.target = TARGET;
       	log.exiting("UpdateAcceptMailCommand","execute");
    	
    }
    /**
	 * added by A-8353
	 * @return systemParameterCodes
	 */
	  private Collection<String> getSystemParameterCodes(){
		  Collection systemParameterCodes = new ArrayList();
		    systemParameterCodes.add("mail.operations.defaultcaptureunit");
		    return systemParameterCodes;
	  } 
	  /**
		 * added by A-8353
		 * @return stationParameterCodes
		 */
	  private Collection<String> getStationParameterCodes()
	  {
	    Collection stationParameterCodes = new ArrayList();
	    stationParameterCodes.add(STNPAR_DEFUNIT_WEIGHT);
	    return stationParameterCodes;
    }		
       
}
