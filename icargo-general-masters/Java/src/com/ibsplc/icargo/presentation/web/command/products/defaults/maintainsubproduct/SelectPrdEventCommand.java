/*
 * SelectPrdEventCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainsubproduct;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.products.defaults.vo.EventLovVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.shared.milestone.vo.MilestoneDetailsVO;
import com.ibsplc.icargo.business.shared.service.vo.ServiceDetailsVO;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.milestone.MilestoneDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.service.ServiceDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-1754
 *
 */
public class SelectPrdEventCommand extends BaseCommand {
	
	//private static final String COMPANY_CODE="AV";
	
	private static final String ZERO = "0";
	
	private static final String TIME_SEPERATOR = ":";
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * Overriding the execute method of BaseCommand
	 * @param invocationContext
	 * @author A-1754
	 * @throws CommandInvocationException
	 */
		public void execute(InvocationContext invocationContext)
		throws CommandInvocationException {
			log.entering("SelectPrdEventCommand","execute");
		MaintainSubProductForm maintainSubProductForm= (MaintainSubProductForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		MaintainSubProductSessionInterface maintainSubProductSessionInterface = getScreenSession(
				"product.defaults", "products.defaults.maintainsubproducts");
		
		Collection<ProductEventVO> listAlreadySelected = maintainSubProductSessionInterface.getProductEventVOs();
		
		
			Collection<EventLovVO> eventVo = session.getSelectedMileStoneLovVos();
			session.removeSelectedMileStoneLovVos();
			session.removeNextAction();
			Collection<String> milestoneCode = new ArrayList<String>();
			Collection<String> serviceCode = new ArrayList<String>();
			if(eventVo!=null){
			for(EventLovVO vo: eventVo){
				if(vo.isService()){
					serviceCode.add(vo.getMilestoneCode());
				
				}else{								
					milestoneCode.add(vo.getMilestoneCode());
				}
			}
			  
			  Collection<MilestoneDetailsVO> milestones = 
				  findServiceMilestones(getApplicationSession().getLogonVO().getCompanyCode(),milestoneCode);
				 Collection<ServiceDetailsVO> services =
					 findServicesDetails(getApplicationSession().getLogonVO().getCompanyCode(),serviceCode);
				 Collection<ProductEventVO> selectedListFromLov  = new ArrayList<ProductEventVO>();
				 if(milestones!=null){
				 for(MilestoneDetailsVO milestonevo : milestones){
				 	ProductEventVO prodEventVO = new ProductEventVO();
				 	prodEventVO.setEventCode(milestonevo.getMilestoneCode());
				 	
				    if(!milestonevo.getIsImportMilestoneIndicator()){
				 		prodEventVO.setEventType("Export");
				 	}else if(milestonevo.getIsImportMilestoneIndicator()){
				 		prodEventVO.setEventType("Import");
				 	}else if(!milestonevo.getIsExportMilestoneIndicator()){
				 		prodEventVO.setEventType("Import");
				 	}else if(milestonevo.getIsExportMilestoneIndicator()){
				 		prodEventVO.setEventType("Export");
				 	}
				 	prodEventVO.setMinimumTime(milestonevo.getMinimumTime());
				 	prodEventVO.setMinimumTimeStr(findTimeString((int)milestonevo.getMinimumTime()));
				 	//prodEventVO.setMinimumTimeStr(settingTimeChanger(milestonevo.getMinimumTime()));
				 	prodEventVO.setMaximumTime(milestonevo.getMaximumTime());
				 	prodEventVO.setMaximumTimeStr(findTimeString((int)milestonevo.getMaximumTime()));
				 	//prodEventVO.setMaximumTimeStr(settingTimeChanger(milestonevo.getMaximumTime()));
				 	prodEventVO.setExternal(milestonevo.isExternal());		
				 	prodEventVO.setInternal(milestonevo.isInternal());	
				 	prodEventVO.setTransit(milestonevo.isTransitIndicator());	
				 	prodEventVO.setAlertTime(milestonevo.getAlertTime());
				 	prodEventVO.setChaserTime(milestonevo.getChaserTime());
				 	prodEventVO.setChaserFrequency(milestonevo.getChaserFrequency());
				 	prodEventVO.setMaxNoOfChasers(milestonevo.getMaximumNumberofChasers());
				 	prodEventVO.setOperationFlag(ProductVO.OPERATION_FLAG_INSERT);
				 	selectedListFromLov.add(prodEventVO);
				 	
				}
				 }
				if(services!=null){
				 for(ServiceDetailsVO serviceVO : services){
				 	ProductEventVO prodEventVO = new ProductEventVO();
				 	prodEventVO.setEventCode(serviceVO.getServiceCode());
				 	prodEventVO.setMaximumTime(serviceVO.getMaximumTime());
				 	prodEventVO.setMaximumTimeStr(findTimeString((int)serviceVO.getMinimumTime()));
				 	//prodEventVO.setMaximumTimeStr(settingTimeChanger(serviceVO.getMinimumTime()));
				 	prodEventVO.setMinimumTime(serviceVO.getMinimumTime());
				 	prodEventVO.setMinimumTimeStr(findTimeString((int)serviceVO.getMaximumTime()));
				 	//prodEventVO.setMinimumTimeStr(settingTimeChanger(serviceVO.getMaximumTime()));
				 	prodEventVO.setEventType(serviceVO.getServiceType());
				 	prodEventVO.setAlertTime(serviceVO.getAlertTime());
				 	prodEventVO.setChaserTime(serviceVO.getChaserTime());
				 	prodEventVO.setChaserFrequency(serviceVO.getChaserFrequency());
				 	prodEventVO.setMaxNoOfChasers(serviceVO.getMaximumNumberOfChasers());
				 	//To be reviewed set the two external,internal
				 	prodEventVO.setOperationFlag(ProductVO.OPERATION_FLAG_INSERT);
				 	selectedListFromLov.add(prodEventVO);						 	
				}	
				}
						
				if(listAlreadySelected!=null){
				Collection<ProductEventVO> newSetOfList =
										new ArrayList<ProductEventVO>(listAlreadySelected);
																	//return the collection
				for(ProductEventVO newVO : selectedListFromLov){
					boolean isPresent = false;
					for(ProductEventVO VO : listAlreadySelected ){
						if(!ProductVO.OPERATION_FLAG_DELETE.equals(VO.getOperationFlag())){
							if(VO.getEventCode().equals(newVO.getEventCode())){
							isPresent = true;
						}
						}
					}
					if(!isPresent){
						newSetOfList.add(newVO);
					}
				}	
				
				maintainSubProductSessionInterface.setProductEventVOs(newSetOfList);				

				}else{
				maintainSubProductSessionInterface.setProductEventVOs(selectedListFromLov);

				}
			
		}			
		
		resetLovAction(maintainSubProductForm);
		invocationContext.target = "screenload_success";
		log.exiting("SelectPrdEventCommand","execute");
	
		}

	    /**
	     * The function to reset the form fileds used to display the LOV
	     *@param  mintainSubProductForm
	     *@return void
	     *@author A-1754
	     *@exception none
	     */
	    private void resetLovAction(MaintainSubProductForm mintainSubProductForm){
	    	mintainSubProductForm.setLovAction("");
	    	mintainSubProductForm.setNextAction("");
	    	mintainSubProductForm.setParentSession("");
	    }
	    

	    /**
	     * The method to get the milestone details given the milestone codes
	     * @param companyCode
	     * @param milestones
	     * @author A-1754
	     * @return Collection<MilestoneDetailsVO>
	     */
	  
	    private Collection<MilestoneDetailsVO> 
	    	findServiceMilestones(String companyCode,Collection<String> milestones) {
	    	
	    	Collection<MilestoneDetailsVO> milestonesDetails = null;
			
			try{
				milestonesDetails = new MilestoneDelegate().findMilestones(companyCode,milestones);
			}catch(BusinessDelegateException businessDelegateException){
			}
			return milestonesDetails;
	    }
		/**
		 * The method to get the service details given the service codes
		 * @param companyCode
		 * @param services
		 * @author A-1754
		 * @return Collection<ServiceDetailsVO>
		 */
		private  Collection<ServiceDetailsVO>
			findServicesDetails(String companyCode,Collection<String> services){
			 Collection<ServiceDetailsVO> servicesDetails= null;
			try{
				servicesDetails = new ServiceDelegate().findServiceMilestones(companyCode,services);
			}catch(BusinessDelegateException businessDelegateException){
			}
			return servicesDetails;
		}
		
		/**
		 * @param time
		 * @return String
		 */
		/*private String settingTimeChanger(double time){
			String minMaxTime="";
			String changedTime="";
			if(time>60){
				minMaxTime=String.valueOf(time/60);
				}else{
				minMaxTime=String.valueOf(time/100);
				}
			if(minMaxTime.indexOf('.')!=-1 && (minMaxTime.length() - minMaxTime.indexOf('.')) > 2){
				minMaxTime=minMaxTime.substring(0,minMaxTime.indexOf('.')+3);
                }
			changedTime=minMaxTime.replace('.',':');
			log.log(Log.FINE,"\n\n\n**********************changedTime******************"+changedTime);
			return changedTime;
			
		}*/
		
		/**
		 * Method to return time string getting number of minutes as input
		 * @param minutes
		 * @return
		 */
		private String findTimeString(int minutes){
			StringBuilder timeString = new StringBuilder();
			int hours = 0;
			int mins = 0;
			if(minutes>60){
				hours = minutes/60;
				mins = minutes%60;
				if(hours<10){
					timeString.append(ZERO);
				}
				timeString.append(String.valueOf(hours));
				timeString.append(TIME_SEPERATOR);
				if(mins<10){
					timeString.append(ZERO);
				}
				timeString.append(String.valueOf(mins));				
			}else{
				timeString.append(ZERO).append(ZERO);
				timeString.append(TIME_SEPERATOR);
				if(minutes<10){
					timeString.append(ZERO);
				}
				timeString.append(String.valueOf(minutes));
			}
			return timeString.toString();
		}
	}
