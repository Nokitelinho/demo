package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainsubproduct;


import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm;
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
		log.entering("UpdateEventCommand","UpdateEventCommand");
		MaintainSubProductForm maintainSubProductForm= (MaintainSubProductForm)invocationContext.screenModel;
		MaintainSubProductSessionInterface session = getScreenSession(
					"product.defaults", "products.defaults.maintainsubproducts");
		handleDataUpdation(session,
				maintainSubProductForm);
		handleNewDataUpdation(session,
				maintainSubProductForm);

		log.exiting("UpdateEventCommand","UpdateEventCommand");
	}
	/**
	 * The function is to incorporate the updations made by the user in the mile stone table.
	 * The function must be called each time an addition, deletion(on Milestone Table) or save is done
	 * @param maintainSubProductForm
	 * @param session
	 * @return void
	 */

	private void handleDataUpdation(
			MaintainSubProductSessionInterface session,
			MaintainSubProductForm maintainSubProductForm){
		Collection<ProductEventVO> newMilestoneSet = null;
		if(session.getProductEventVOs()!=null){
		ArrayList<ProductEventVO> allMilestone = (ArrayList<ProductEventVO>)session.getProductEventVOs();
		newMilestoneSet = new ArrayList<ProductEventVO>();
		String[] isRowmodified = maintainSubProductForm.getIsRowModified();
//		log.log(Log.FINE,"\n\n\n\n**********************isRowmodified-------------"+isRowmodified);
		if(isRowmodified!=null){
	//	log.log(Log.FINE,"\n\n\n\n**********************isRowmodified is not null-------------"+isRowmodified);
		String hiddenExternal = maintainSubProductForm.getCheckedExternal();
		StringTokenizer tok = new StringTokenizer(hiddenExternal,"-");
		int count=0;
		String[] seperateExt = new String[allMilestone.size()];
		while(tok.hasMoreTokens()){
			seperateExt[count] = tok.nextToken();
			count++;
		}

		String hiddenInternal = maintainSubProductForm.getCheckedInternal();
		StringTokenizer token = new StringTokenizer(hiddenInternal,"-");
		count=0;
		String[] seperateInt = new String[allMilestone.size()];
		while(token.hasMoreTokens()){
			seperateInt[count] = token.nextToken();
			count++;
		}
		String hiddenTransit = maintainSubProductForm.getCheckedTransit();
		StringTokenizer to = new StringTokenizer(hiddenTransit,"-");
		count=0;
		String[] seperateTra = new String[allMilestone.size()];
		while(to.hasMoreTokens()){
			seperateTra[count] = to.nextToken();
			log
					.log(
							Log.FINE,
							"\n\n\n\n**********************seperateTra[count]-------------",
							seperateTra, count);
			count++;
		}
	//	log.log(Log.FINE,"\n\n\n\n**********************hiddenTransit-------------"+hiddenTransit);
//		log.log(Log.FINE,"\n\n\n\n**********************seperateTra-------------"+seperateTra);
		String[] minTime = maintainSubProductForm.getMinTime();
		String[] maxTime = maintainSubProductForm.getMaxTime();
		String[] alertTime =  maintainSubProductForm.getAlertTime();
		String[] chaserTime =  maintainSubProductForm.getChaserTime();
		String[] chaserFreq =  maintainSubProductForm.getChaserFrequency();
		String[] maxNoOfChasers =  maintainSubProductForm.getMaxNoOfChasers();

		for(int i=0;i<isRowmodified.length;i++){
			ProductEventVO vo = (ProductEventVO)allMilestone.get(i);
			if(("1").equals(isRowmodified[i]) && !("I").equals(vo.getOperationFlag())){
				vo.setOperationFlag(ProductVO.OPERATION_FLAG_UPDATE);
				if(!"".equals(maxTime[i])){
					vo.setMaximumTime(timeConversion(maxTime[i]));
					vo.setMaximumTimeStr(findTimeString(timeConversion(maxTime[i])));
				}else{
					vo.setMaximumTime(0);
					vo.setMaximumTimeStr("");
				}
				if(!"".equals(minTime[i])){
					vo.setMinimumTime(timeConversion(minTime[i]));
					vo.setMinimumTimeStr(findTimeString(timeConversion(minTime[i])));
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
					log
							.log(
									Log.FINE,
									"\n\n\n\n**********************seperateTra[i]-------------",
									seperateTra, i);
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
	 * @param maintainSubProductForm
	 * @param session
	 * @return
	 */

	private void handleNewDataUpdation(
			MaintainSubProductSessionInterface session,
			MaintainSubProductForm maintainSubProductForm){
		Collection<ProductEventVO> newMilestoneSet = null;
		if(session.getProductEventVOs()!=null){
		ArrayList<ProductEventVO> allMilestone = (ArrayList<ProductEventVO>)session.getProductEventVOs();
		newMilestoneSet = new ArrayList<ProductEventVO>();
		String[] isNewRowmodified = maintainSubProductForm.getIsNewRowModified();
//		log.log(Log.FINE,"\n\n\n\n*********************isNewRowmodified-------------"+isNewRowmodified);
		if(isNewRowmodified!=null){
//			log.log(Log.FINE,"\n\n\n\n*********************isNewRowmodified- is not null------------"+isNewRowmodified);
		String hiddenExternal = maintainSubProductForm.getCheckedExternal();
		StringTokenizer tok = new StringTokenizer(hiddenExternal,"-");
		int count=0;
		String[] seperateExt = new String[allMilestone.size()];
		while(tok.hasMoreTokens()){
			seperateExt[count] = tok.nextToken();
			count++;
		}
		String hiddenInternal = maintainSubProductForm.getCheckedInternal();
		StringTokenizer token = new StringTokenizer(hiddenInternal,"-");
		count=0;
		String[] seperateInt = new String[allMilestone.size()];
		while(token.hasMoreTokens()){
			seperateInt[count] = token.nextToken();
			count++;
		}
		String hiddenTransit = maintainSubProductForm.getCheckedTransit();
		StringTokenizer to = new StringTokenizer(hiddenTransit,"-");
		count=0;
		String[] seperateTra = new String[allMilestone.size()];
		while(to.hasMoreTokens()){
			seperateTra[count] = to.nextToken();
			log
					.log(
							Log.FINE,
							"\n\n\n\n**********************seperateTra[count]-------------",
							seperateTra, count);
			count++;
		}
		log.log(Log.FINE,
				"\n\n\n\n**********************hiddenTransit-------------",
				hiddenTransit);
		String[] minTime = maintainSubProductForm.getMinTime();
		String[] maxTime = maintainSubProductForm.getMaxTime();
		String[] alertTime =  maintainSubProductForm.getAlertTime();
		String[] chaserTime =  maintainSubProductForm.getChaserTime();
		String[] chaserFreq =  maintainSubProductForm.getChaserFrequency();
		String[] maxNoOfChasers =  maintainSubProductForm.getMaxNoOfChasers();


		for(int i=0;i<isNewRowmodified.length;i++){

				ProductEventVO vo = (ProductEventVO)allMilestone.get(i);
				if(ProductVO.OPERATION_FLAG_INSERT.equals(vo.getOperationFlag())){
				vo.setOperationFlag(ProductVO.OPERATION_FLAG_INSERT);

				if(!"".equals(maxTime[i])){
					vo.setMaximumTime(timeConversion(maxTime[i]));
					vo.setMaximumTimeStr(findTimeString(timeConversion(maxTime[i])));
				}else{
					vo.setMaximumTime(0);
					vo.setMaximumTimeStr("");
				}
				if(!"".equals(minTime[i])){
					vo.setMinimumTime(timeConversion(minTime[i]));
					vo.setMinimumTimeStr(findTimeString(timeConversion(minTime[i])));
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
				log
						.log(
								Log.FINE,
								"\n\n\n\n**********************seperateTra[i]-------------",
								seperateTra, i);
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
