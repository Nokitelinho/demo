/*
 * UpdateEventCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;


import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-1754
 *
 */
public class UpdateEventCommand extends BaseCommand {
	
	private static final String ZERO = "0";
	
	private static final String TIME_SEPERATOR = ":";
	
	/**
     * Log
     */
    private Log log = LogFactory.getLogger("PRODUCTS.DEFAULTS");
/**
 * The execute method in BaseCommand
 * @author A-1754
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		MaintainProductForm maintainProductForm= (MaintainProductForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
					"product.defaults", "products.defaults.maintainproduct");
		handleDataUpdation(session,
				maintainProductForm);
		handleNewDataUpdation(session,
				maintainProductForm);
		
		
	}
	/**
	 * The function is to incorporate the updations made by the user in the mile stone table.
	 * The function must be called each time an addition, deletion(on Milestone Table) or save is done 
	 * @param maintainProductForm
	 * @param session
	 * @return
	 */
	
	private void handleDataUpdation(
			MaintainProductSessionInterface session,
			MaintainProductForm maintainProductForm){
		Collection<ProductEventVO> newMilestoneSet = null;
		if(session.getProductEventVOs()!=null){
		ArrayList<ProductEventVO> allMilestone = (ArrayList<ProductEventVO>)session.getProductEventVOs();
		newMilestoneSet = new ArrayList<ProductEventVO>();		
		String[] isRowmodified = maintainProductForm.getIsRowModified();
		if(isRowmodified!=null){
		String hiddenExternal = maintainProductForm.getCheckedExternal();
		StringTokenizer tok = new StringTokenizer(hiddenExternal,"-");
		int count=0;
		String[] seperateExt = new String[allMilestone.size()]; 
		while(tok.hasMoreTokens()){									
			seperateExt[count] = tok.nextToken();
			count++;
		}
		
		String hiddenInternal = maintainProductForm.getCheckedInternal();
		StringTokenizer token = new StringTokenizer(hiddenInternal,"-");
		count=0;
		String[] seperateInt = new String[allMilestone.size()]; 
		while(token.hasMoreTokens()){									
			seperateInt[count] = token.nextToken();
			count++;
		}	
		String hiddenTransit = maintainProductForm.getCheckedTransit();
		log.log(Log.FINE, "**********************hiddenTransit-------------",
				hiddenTransit);
		StringTokenizer to = new StringTokenizer(hiddenTransit,"-");
		count=0;
		String[] seperateTra = new String[allMilestone.size()]; 
		while(to.hasMoreTokens()){									
			seperateTra[count] = to.nextToken();
			count++;
		}
		String[] minTime = maintainProductForm.getMinTime();
		String[] maxTime = maintainProductForm.getMaxTime();
		String[] alertTime =  maintainProductForm.getAlertTime();
		String[] chaserTime =  maintainProductForm.getChaserTime();
		String[] chaserFreq =  maintainProductForm.getChaserFrequency();
		String[] maxNoOfChasers =  maintainProductForm.getMaxNoOfChasers();
		
		for(int i=0;i<isRowmodified.length;i++){
			ProductEventVO vo = (ProductEventVO)allMilestone.get(i);
			if(("1").equals(isRowmodified[i]) && !("I").equals(vo.getOperationFlag())){
				vo.setOperationFlag(ProductVO.OPERATION_FLAG_UPDATE);
				if(!"".equals(maxTime[i])){
					vo.setMaximumTime(timeConversion(maxTime[i]));
					vo.setMaximumTimeStr(findTimeString(timeConversion(maxTime[i])));
					/*vo.setMaximumTime(Double.parseDouble(timeConversion(maxTime[i])));
					vo.setMaximumTimeStr(settingTimeChanger(Double.parseDouble(timeConversion(maxTime[i]))));*/
					
				}else{
					vo.setMaximumTime(0);
					vo.setMaximumTimeStr("");
				}
				if(!"".equals(minTime[i])){
					vo.setMinimumTime(timeConversion(minTime[i]));
					vo.setMinimumTimeStr(findTimeString(timeConversion(minTime[i])));
					/*vo.setMinimumTime(Double.parseDouble(timeConversion(minTime[i])));
					vo.setMinimumTimeStr(settingTimeChanger(Double.parseDouble(timeConversion(minTime[i]))));*/
				}else{
					vo.setMinimumTime(0);
					vo.setMinimumTimeStr("");
				}
				if(!"".equals(alertTime[i])){
					vo.setAlertTime(Double.parseDouble(alertTime[i]));
				}else{
					vo.setAlertTime(0);
				}
				if(!"".equals(chaserTime[i])){
					vo.setChaserTime(Double.parseDouble(chaserTime[i]));
				}else{
					vo.setChaserTime(0);
				}
				if(!"".equals(chaserFreq[i])){
					vo.setChaserFrequency(Double.parseDouble(chaserFreq[i]));
				}else{
					vo.setChaserFrequency(0);
				}
				if(!"".equals(maxNoOfChasers[i])){
					vo.setMaxNoOfChasers(Integer.parseInt(maxNoOfChasers[i]));
				}else{
					vo.setMaxNoOfChasers(0);
				}
				
				if ("true".equals(seperateExt[i])){
					vo.setExternal(true);
				}else{
					vo.setExternal(false);
				}
		
		
				if("true".equals(seperateInt[i])){
					vo.setInternal(true);
				}else{
					vo.setInternal(false);
				}
				if("true".equals(seperateTra[i])){
					vo.setTransit(true);
				}else{
					vo.setTransit(false);
				}
			
		
			}
			newMilestoneSet.add(vo);						
			}
			session.setProductEventVOs(newMilestoneSet);
		}
		}
		
		
			
		
	}
	

	/**
	 * The function is to incorporate the updations made by the user in the newly Added milesstones
	 * The function must be called each time an addition, deletion(on Milestone Table) or save is done 
	 * @param maintainProductForm
	 * @param session 
	 * @return none
	 */
	
	private void handleNewDataUpdation(
			MaintainProductSessionInterface session,
			MaintainProductForm maintainProductForm){
		Collection<ProductEventVO> newMilestoneSet = null;		
		if(session.getProductEventVOs()!=null){
		ArrayList<ProductEventVO> allMilestone = (ArrayList<ProductEventVO>)session.getProductEventVOs();
		newMilestoneSet = new ArrayList<ProductEventVO>();		
		String[] isNewRowmodified = maintainProductForm.getIsNewRowModified();
		
		if(isNewRowmodified!=null){
		String hiddenExternal = maintainProductForm.getCheckedExternal();
		StringTokenizer tok = new StringTokenizer(hiddenExternal,"-");
		int count=0;
		String[] seperateExt = new String[allMilestone.size()]; 
		while(tok.hasMoreTokens()){									
			seperateExt[count] = tok.nextToken();
			count++;
		}

		String hiddenInternal = maintainProductForm.getCheckedInternal();
		StringTokenizer token = new StringTokenizer(hiddenInternal,"-");
		count=0;
		String[] seperateInt = new String[allMilestone.size()]; 
		while(token.hasMoreTokens()){									
			seperateInt[count] = token.nextToken();
			count++;
		}
		String hiddenTransit = maintainProductForm.getCheckedTransit();
		log.log(Log.FINE, "**********************hiddenTransit-------------",
				hiddenTransit);
		StringTokenizer to = new StringTokenizer(hiddenTransit,"-");
		count=0;
		String[] seperateTra = new String[allMilestone.size()]; 
		while(to.hasMoreTokens()){									
			seperateTra[count] = to.nextToken();
			count++;
		}
		
		String[] minTime = maintainProductForm.getMinTime();
		String[] maxTime = maintainProductForm.getMaxTime();
		String[] alertTime =  maintainProductForm.getAlertTime();
		String[] chaserTime =  maintainProductForm.getChaserTime();
		String[] chaserFreq =  maintainProductForm.getChaserFrequency();
		String[] maxNoOfChasers =  maintainProductForm.getMaxNoOfChasers();
							
		
		for(int i=0;i<isNewRowmodified.length;i++){
			
				ProductEventVO vo = (ProductEventVO)allMilestone.get(i);
				if(ProductVO.OPERATION_FLAG_INSERT.equals(vo.getOperationFlag())){
				vo.setOperationFlag(ProductVO.OPERATION_FLAG_INSERT);
				
				if(!"".equals(maxTime[i])){
					vo.setMaximumTime(timeConversion(maxTime[i]));
					vo.setMaximumTimeStr(findTimeString(timeConversion(maxTime[i])));
					/*vo.setMaximumTime(Double.parseDouble(timeConversion(maxTime[i])));
					vo.setMaximumTimeStr(settingTimeChanger(Double.parseDouble(timeConversion(maxTime[i]))));*/
				}else{
					vo.setMaximumTime(0);
					vo.setMaximumTimeStr("");
				}
				if(!"".equals(minTime[i])){
					vo.setMinimumTime(timeConversion(minTime[i]));
					vo.setMinimumTimeStr(findTimeString(timeConversion(minTime[i])));					
					/*vo.setMinimumTime(Double.parseDouble(timeConversion(minTime[i])));
					vo.setMinimumTimeStr(settingTimeChanger(Double.parseDouble(timeConversion(minTime[i]))));*/
				}else{
					vo.setMinimumTime(0);
					vo.setMinimumTimeStr("");
				}
				if(!"".equals(alertTime[i])){
					vo.setAlertTime(Double.parseDouble(alertTime[i]));
				}else{
					vo.setAlertTime(0);
				}
				if(!"".equals(chaserTime[i])){
					vo.setChaserTime(Double.parseDouble(chaserTime[i]));
				}else{
					vo.setChaserTime(0);
				}
				
				if(!"".equals(chaserFreq[i])){
					vo.setChaserFrequency(Double.parseDouble(chaserFreq[i]));
				}else{
					vo.setChaserFrequency(0);
				}
				if(!"".equals(maxNoOfChasers[i])){
					vo.setMaxNoOfChasers(Integer.parseInt(maxNoOfChasers[i]));
				}else{
					vo.setMaxNoOfChasers(0);
				}
				if ("true".equals(seperateExt[i])){
					vo.setExternal(true);
				}else{
					vo.setExternal(false);
				}
				if("true".equals(seperateInt[i])){
					vo.setInternal(true);
				}else{
					vo.setInternal(false);
				}
				if("true".equals(seperateTra[i])){
					vo.setTransit(true);
				}else{
					vo.setTransit(false);
				}
						
			}		
				newMilestoneSet.add(vo);	
			}
			session.setProductEventVOs(newMilestoneSet);
		}
		}
		
	}
	/**
	 * 
	 * @param time
	 * @return String
	 */
	private int timeConversion(String time){
		StringTokenizer newTime = new StringTokenizer(time, ":");
		log.log(Log.FINE, "*********************newTime------------", newTime);
		String integerPart="0";
		String decimalPart="0";
		int totalInt=0;
		int totalDeci=0;
		int totalMinutes;
		String timeStr = "";
		int count = 0;
		if(newTime!=null){
			while (newTime.hasMoreTokens()) {
				timeStr = newTime.nextToken();				
				/*decimalPart = newTime
						.nextToken();*/
				if(count == 0){
					integerPart = timeStr;
				}
				if(count == 1){
					decimalPart = timeStr;
				}
				count++;
			}
			
		}
		
		log.log(Log.FINE, "**********************integerPart-------------",
				integerPart);
		totalInt=(Integer.parseInt(integerPart))*60;
		log.log(Log.FINE, "**********************totalInt-------------",
				totalInt);
		log.log(Log.FINE, "**********************decimalPart-------------",
				decimalPart);
		totalDeci=Integer.parseInt(decimalPart);
		log.log(Log.FINE, "**********************totalDeci-------------",
				totalDeci);
		totalMinutes=totalInt+totalDeci;
		log.log(Log.FINE, "**********************totalMinMinutes-------------",
				totalMinutes);
		return totalMinutes;
	}
	/**
	 * @param times
	 * @return String
	 */
	/*private String settingTimeChanger(double times){
		String minMaxTime="";
		String changedTime="";
		if(times>60){
			minMaxTime=String.valueOf(times/60);
			}else{
			minMaxTime=String.valueOf(times/100);
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
		/*
		 * given > = 60 by A-5220 for ICRD-46203
		 * because if the minutes are equal to 60, time should be 01:00
		 */
		if(minutes >= 60){
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
