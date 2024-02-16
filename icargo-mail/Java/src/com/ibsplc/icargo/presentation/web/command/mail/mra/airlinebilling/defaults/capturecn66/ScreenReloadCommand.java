/*
 * ScreenReloadCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;


import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN66Form;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class ScreenReloadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("AirLineBillingInward ScreenReloadCommand");

	private static final String CLASS_NAME = "CaptureCN66ScreenReLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	private static final String ACTION_SUCCESS = "screenload_success";
	private static final String SCREENSTATUS_RELOAD="reload";
	private static final String MAILSUBCLASS_CP = "CP";

	private static final String MAILSUBCLASS_LC = "LC";
	
	private static final String MAILSUBCLASS_SAL = "SAL";
	
	private static final String MAILSUBCLASS_ULD = "UL";
	
	private static final String MAILSUBCLASS_SV = "SV";

	private static final String MAILSUBCLASS_EMS = "EMS";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
	    
	     Money[] totalAmount=null;
	     double lc =0.0;
	     double cp =0.0;
	     double sal=0.0;
	     double uld=0.0;
	     double sv=0.0;
	     double ems=0.0;
	     double wt=0.0;
	     double netWt=0.0;
	     Money moneyChg=null;
	    
		CaptureCN66Session session=(CaptureCN66Session)getScreenSession(MODULE_NAME, SCREEN_ID);
		CaptureCN66Form form=(CaptureCN66Form)invocationContext.screenModel;
		HashMap<String,Collection<AirlineCN66DetailsVO>> cn66s=null;
		if(session.getAirlineCN66DetailsVOs()!=null && session.getAirlineCN66DetailsVOs().size()>0){
			cn66s=session.getCn66DetailsMap();
			Page<AirlineCN66DetailsVO> detVos=session.getAirlineCN66DetailsVOs();
			log.log(Log.INFO, "airlineCN66DetailsVO---in reload....", cn66s);
			if(detVos!=null && detVos.size()>0){
			//ArrayList<Collection<AirlineCN66DetailsVO>> vos=new ArrayList<Collection<AirlineCN66DetailsVO>>(cn66s.values());
			//ArrayList<AirlineCN66DetailsVO> detVos=new ArrayList<AirlineCN66DetailsVO>();
			
			
			log.log(Log.INFO, "detVos---in reload..start..", detVos);
			int valsize=cn66s.values().size();
    		/*for(int i=0;i<valsize;i++){
    			detVos.addAll((ArrayList<AirlineCN66DetailsVO>)vos.get(i));
    			
    		}*/
    		
    		int siz=detVos.size();
    		log.log(Log.INFO, "size of detVos---in reload..start..", siz);
			totalAmount=new Money[siz];
			try{
				
				moneyChg=CurrencyHelper.getMoney(form.getBlgCurCode());
				moneyChg.setAmount(0.0D);
			
				
			for(int i=0;i<siz;i++){
				if(!OPERATION_FLAG_DELETE.equals(detVos.get(i).getOperationFlag())){
				log.log(Log.INFO, "detVos", detVos.size());
				if(detVos.get(i).getMailSubClass()!=null){
				if(detVos.get(i).getMailSubClass().equals(MAILSUBCLASS_CP)){
					cp=cp+detVos.get(i).getTotalWeight();
				}
				
				
				if(detVos.get(i).getMailSubClass().equals(MAILSUBCLASS_LC)){
					lc=lc+detVos.get(i).getTotalWeight();
				}
			
				if(detVos.get(i).getMailSubClass().equals(MAILSUBCLASS_SAL)){
					sal=sal+detVos.get(i).getTotalWeight();
				}
				if(detVos.get(i).getMailSubClass().equals(MAILSUBCLASS_ULD)){
					uld=uld+detVos.get(i).getTotalWeight();
				}
				if(detVos.get(i).getMailSubClass().equals(MAILSUBCLASS_SV)){
					sv=sv+detVos.get(i).getTotalWeight();
				}
				if(MAILSUBCLASS_EMS.equals(detVos.get(i).getMailSubClass())){
					ems=ems+detVos.get(i).getTotalWeight();
				}
				}
				if(detVos.get(i).getTotalWeight()!=0.0){
				
					log.log(Log.INFO, "total WGT IN RELOAD ", detVos.get(i).getTotalWeight());
					wt=wt+detVos.get(i).getTotalWeight();
	                  
				}	
				if(detVos.get(i).getAmount()!=null){
					
					log.log(Log.INFO, "total Amount IN RELOAD ", detVos.get(i).getAmount().toString());
					totalAmount[i]=CurrencyHelper.getMoney("USD");
					//totalAmount[i].setAmount(Double.parseDouble(detVos.get(i).getAmount().toString()));
			       	totalAmount[i].setAmount(detVos.get(i).getAmount().getAmount());
					log.log(Log.INFO, "MONEY SET IN FORM IN RELOAD ",
							totalAmount[i].toString());
					moneyChg.plusEquals(detVos.get(i).getAmount());
					 
				}
				if(detVos.get(i).getTotalSummaryWeight()!=0.0){
					netWt=detVos.get(i).getTotalSummaryWeight();
				}
				log.log(Log.INFO, "airlineCN66DetailsVO---in reload..end..",
						detVos);
				}
			}
		}
			catch(CurrencyException currencyException){
				log.log(Log.INFO,"CurrencyException found");
			}
			
				log.log(Log.INFO,"MONEY SET IN FORM IN RELOAD not null" );	
				
//				Added By A-3447 For cp	lc sal uld and sv wts
				
				
				
				
				//Modified weight units as part of ICRD-101112	
				
				try
				{
				form.setNetCPWeight(String.valueOf(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT, cp)));
				}catch(UnitException unitException) {
					unitException.getErrorCode();
				   }
				
				try
				{
				form.setNetLCWeight(String.valueOf(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT, lc)));
				}catch(UnitException unitException) {
					unitException.getErrorCode();
				   }
				
				try
				{
				form.setNetSalWeight(String.valueOf(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT, sal)));
				}catch(UnitException unitException) {
					unitException.getErrorCode();
				   }
				
				try
				{
				form.setNetUldWeight(String.valueOf(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT, uld)));
				}catch(UnitException unitException) {
					unitException.getErrorCode();
				   }
				
				try
				{
				form.setNetSVWeight(String.valueOf(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT, sv)));
				}catch(UnitException unitException) {
					unitException.getErrorCode();
				   }
				//Added by A-4809 for EMS changes
				try{
					form.setNetEMSWeight(String.valueOf(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT, ems)));
				}catch(UnitException unitException){
					unitException.getErrorCode();
				}
				//
				try
				{
				form.setNetSummaryWeight(String.valueOf(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT, netWt)));
				}catch(UnitException unitException) {
					unitException.getErrorCode();
				   }
				form.setNetChargeMoney(moneyChg);
				
				log.log(Log.INFO,"MONEY SET IN FORM IN RELOAD " );	
			
		}
			
		}
		
		form.setScreenStatus(SCREENSTATUS_RELOAD);
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

}
