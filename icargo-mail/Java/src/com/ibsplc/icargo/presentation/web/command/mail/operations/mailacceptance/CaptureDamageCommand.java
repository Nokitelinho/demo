/*
 * CaptureDamageCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class CaptureDamageCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "capturedamage_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("CaptureDamageCommand","execute");
    	  
    	MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		mailAcceptanceSession.setDamagedMailbagVOs(null);
    	
    	ContainerDetailsVO containerDetailsVO = mailAcceptanceSession.getContainerDetailsVO();
    	
    	containerDetailsVO.setWareHouse(mailAcceptanceForm.getWarehouse());
    	String location = mailAcceptanceForm.getLocation();
		if (location != null && location.trim().length() > 0) {
    	    containerDetailsVO.setLocation(mailAcceptanceForm.getLocation().toUpperCase());
		}
		String remarks = mailAcceptanceForm.getRemarks();
		if (remarks != null && remarks.trim().length() > 0) {
    	    containerDetailsVO.setRemarks(mailAcceptanceForm.getRemarks());
		}
    	
    	containerDetailsVO = updateContainerDetails(containerDetailsVO,mailAcceptanceForm,logonAttributes);
    	mailAcceptanceSession.setContainerDetailsVO(containerDetailsVO);
    	
    	mailAcceptanceForm.setPopupCloseFlag("showDamagePopup");
    	
    	invocationContext.target = TARGET;
    	
       	log.exiting("CaptureDamageCommand","execute");
    	
    }
    /**
	 * Mehtod to update Container Details from Form
	 * @param containerDetailsVO
	 * @param mailAcceptanceForm
	 * @param logonAttributes
	 * @return ContainerDetailsVO
	 */
    public ContainerDetailsVO  updateContainerDetails(ContainerDetailsVO containerDetailsVO,
			MailAcceptanceForm mailAcceptanceForm,LogonAttributes logonAttributes){
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
		//String[]mailWt = mailAcceptanceForm.getMailWt();
		Measure[]mailWt=mailAcceptanceForm.getMailWtMeasure();//modified by A-7371
		String[]mailScanDate = mailAcceptanceForm.getMailScanDate();
		String[]damaged = mailAcceptanceForm.getMailDamaged();
		String[]mailbagId = mailAcceptanceForm.getMailbagId();//Added for ICRD-205027
    	
		String[] mailDamaged = new String[mailbagVOs.size()];
		if(damaged != null) {
			int flag = 0;
			int mailSize = mailbagVOs.size();
			for(int i=0;i<mailSize;i++) {
			   for(int j=0;j<damaged.length;j++){
				   if(!"".equals(damaged[j])){
				  	  if(i == Integer.parseInt(damaged[j])){
						   mailDamaged[i] = "Y";
						   flag = 1;
					  }
				   }
			   }
			   if(flag == 1){
				   flag = 0;
			   }else{
				   mailDamaged[i] = "N";
			   }
		    }
		}
		
		Collection<DespatchDetailsVO> newDespatchVOs = new ArrayList<DespatchDetailsVO>();
		Collection<MailbagVO> newMailbagVOs = new ArrayList<MailbagVO>();
		int count = 0;
		try{
			if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
		      for(DespatchDetailsVO despatchDetailsVO:despatchDetailsVOs) {
		    	  DespatchDetailsVO newDespatchVO = new DespatchDetailsVO();
				BeanHelper.copyProperties(newDespatchVO,despatchDetailsVO);
				if(!"D".equals(despatchDetailsVO.getOperationalFlag())){
				if(conDocNo != null) {
					if(conDocNo[count] != null && !("".equals(conDocNo[count]))) {
						newDespatchVO.setConsignmentNumber(conDocNo[count].toUpperCase());
					}
				}
				if(despatchDate != null) {
					if(despatchDate[count] != null && !("".equals(despatchDate[count]))) {
						LocalDate cd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
						newDespatchVO.setConsignmentDate(cd.setDate(despatchDate[count]));
					}
				}
				if(despatchPA != null) {
					if(despatchPA[count] != null && !("".equals(despatchPA[count]))) {
						newDespatchVO.setPaCode(despatchPA[count].toUpperCase());
					}
				}
				if(despatchOOE != null) {
					if(despatchOOE[count] != null && !("".equals(despatchOOE[count]))) {
						newDespatchVO.setOriginOfficeOfExchange(despatchOOE[count].toUpperCase());
					}
				}
				if(despatchDOE != null) {
					if(despatchDOE[count] != null && !("".equals(despatchDOE[count]))) {
						newDespatchVO.setDestinationOfficeOfExchange(despatchDOE[count].toUpperCase());
					}
				}
				if(despatchCat != null) {
					if(despatchCat[count] != null && !("".equals(despatchCat[count]))) {
						newDespatchVO.setMailCategoryCode(despatchCat[count]);
					}
				}
				//added be anitha for pk change-start
				if(despatchClass != null) {					
						if(despatchClass[count] != null && !("".equals(despatchClass[count]))){
							newDespatchVO.setMailClass(despatchClass[count].toUpperCase());
						}					
				}
				if(despatchSC != null) {
					if(despatchSC[count] != null && !("".equals(despatchSC[count]))	) {
						newDespatchVO.setMailSubclass(despatchSC[count].toUpperCase());
					}
				}
				if((despatchSC == null || despatchSC[count] == null || ("".equals(despatchSC[count])))) {
					if(despatchClass != null) {						
						if(despatchClass[count] != null && !("".equals(despatchClass[count]))){
							newDespatchVO.setMailSubclass(despatchClass[count].toUpperCase().concat("_"));
						}			
				}
				}
				//added be anitha for pk change-end
				if(despatchDSN != null) {
					if(despatchDSN[count] != null && !("".equals(despatchDSN[count]))) {
						newDespatchVO.setDsn(despatchDSN[count].toUpperCase());
					}
				}
				if(despatchYear != null) {
					if(despatchYear[count] != null && !("".equals(despatchYear[count]))) {
						newDespatchVO.setYear(Integer.parseInt(despatchYear[count]));
					}
		        }
				if(statedNoBags != null) {
					if(statedNoBags[count] != null && !("".equals(statedNoBags[count]))) {
						newDespatchVO.setStatedBags(Integer.parseInt(statedNoBags[count]));
					}
		        }
				if(statedWt != null) {
					if(statedWt[count] != null && !("".equals(statedWt[count]))) {
						//newDespatchVO.setStatedWeight(Double.parseDouble(statedWt[count]));
						newDespatchVO.setStatedWeight(new Measure(UnitConstants.WEIGHT,Double.parseDouble(statedWt[count])));//added by A-7371
					}
		        }
				if(accNoBags != null) {
					if(accNoBags[count] != null && !("".equals(accNoBags[count]))) {
						if(!"I".equals(newDespatchVO.getOperationalFlag())){
							if(newDespatchVO.getAcceptedBags() != (Integer.parseInt(accNoBags[count]))){
								newDespatchVO.setOperationalFlag("U");
							}
						}
						newDespatchVO.setAcceptedBags(Integer.parseInt(accNoBags[count]));
					}
		        }
				if(accWt != null) {
					if(accWt[count] != null && !("".equals(accWt[count]))) {
						if(!"I".equals(newDespatchVO.getOperationalFlag())){
							//if(newDespatchVO.getAcceptedWeight() != (Double.parseDouble(accWt[count]))){
							if(newDespatchVO.getAcceptedWeight().getSystemValue() != (Double.parseDouble(accWt[count]))){//added by A-7371
								newDespatchVO.setOperationalFlag("U");
							}
						}
						//newDespatchVO.setAcceptedWeight(Double.parseDouble(accWt[count]));
						newDespatchVO.setAcceptedWeight(new Measure(UnitConstants.WEIGHT,Double.parseDouble(accWt[count])));//added by A-7371
					}
		        }
				count++;
				}
				newDespatchVOs.add(newDespatchVO);
		     }
			}
			count = 0;
			if(mailbagVOs != null && mailbagVOs.size() > 0) {
			  for(MailbagVO mailbagVO:mailbagVOs) {
			   	MailbagVO newMailbagVO = new MailbagVO();
				BeanHelper.copyProperties(newMailbagVO,mailbagVO);
				if(!"D".equals(mailbagVO.getOperationalFlag())){
				if(mailOOE != null) {
					if(mailOOE[count] != null && !("".equals(mailOOE[count]))) {
						newMailbagVO.setOoe(mailOOE[count].toUpperCase());
					}
				}
				if(mailDOE != null) {
					if(mailDOE[count] != null && !("".equals(mailDOE[count]))) {
						newMailbagVO.setDoe(mailDOE[count].toUpperCase());
					}
				}
				if(mailCat != null) {
					if(mailCat[count] != null && !("".equals(mailCat[count]))) {
						newMailbagVO.setMailCategoryCode(mailCat[count]);
					}
				}
				if(mailSC != null) {
					if(mailSC[count] != null && !("".equals(mailSC[count]))) {
						newMailbagVO.setMailSubclass(mailSC[count].toUpperCase());
						newMailbagVO.setMailClass(newMailbagVO.getMailSubclass().substring(0,1));
					}
				}
				if(mailYr != null) {
					if(mailYr[count] != null && !("".equals(mailYr[count]))) {
						newMailbagVO.setYear(Integer.parseInt(mailYr[count]));
					}
				}
				if(mailDSN != null) {
					if(mailDSN[count] != null && !("".equals(mailDSN[count]))) {
						newMailbagVO.setDespatchSerialNumber(mailDSN[count].toUpperCase());
					}
				}
				if(mailRSN != null) {
					if(mailRSN[count] != null && !("".equals(mailRSN[count]))) {
						newMailbagVO.setReceptacleSerialNumber(mailRSN[count].toUpperCase());
					}
				}
				if(mailHNI != null) {
					if(mailHNI[count] != null && !("".equals(mailHNI[count]))) {
						newMailbagVO.setHighestNumberedReceptacle(mailHNI[count]);
					}
				}
				if(mailRI != null) {
					if(mailRI[count] != null && !("".equals(mailRI[count]))) {
						newMailbagVO.setRegisteredOrInsuredIndicator(mailRI[count]);
					}
				}
				if(mailWt != null) {
					if(mailWt[count] != null && !("".equals(mailWt[count]))) {
						//Measure strWt= new Measure(UnitConstants.WEIGHT,(Double.parseDouble(mailWt[count]))/10);
						//mailWt[count].setSystemValue(mailWt[count].getDisplayValue()/10);
						newMailbagVO.setStrWeight(mailWt[count]);//added by A-7371
						newMailbagVO.setWeight(mailWt[count]);
						
					}
				}
				if(mailScanDate != null) {
					if(mailScanDate[count] != null && !("".equals(mailScanDate[count]))) {
						LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
						newMailbagVO.setScannedDate(sd.setDate(mailScanDate[count]));
					}
				}
				if(mailDamaged != null) {
					if(mailDamaged[count] != null && !("".equals(mailDamaged[count]))) {
						if(!"I".equals(newMailbagVO.getOperationalFlag())){
							if(!mailDamaged[count].equals(newMailbagVO.getDamageFlag())){
								newMailbagVO.setOperationalFlag("U");
							}
						}
						newMailbagVO.setDamageFlag(mailDamaged[count]);
					}
				}
				//Added as part of ICRD-205027 starts
				if(mailbagId != null) {
					if(mailbagId[count] != null && !("".equals(mailbagId[count]))) {
						newMailbagVO.setMailbagId(mailbagId[count]);
					}
				}
				//Added as part of ICRD-205027 ends
				count++;
				}
				newMailbagVOs.add(newMailbagVO);
			  }
			}
			
			}catch (SystemException e) {
				e.getMessage();
			}
    	
			containerDetailsVO.setDesptachDetailsVOs(newDespatchVOs);
			containerDetailsVO.setMailDetails(newMailbagVOs);
    	
    	
    	return containerDetailsVO;
    }
                  
}
