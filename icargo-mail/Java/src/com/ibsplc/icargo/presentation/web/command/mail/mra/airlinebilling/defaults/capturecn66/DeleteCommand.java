/*
 * DeleteCommand.java Created on Jan 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;
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
public class DeleteCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("AirlineBilling DeleteCommand");

	private static final String CLASS_NAME = "DeleteCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	private static final String SCREEN_SUCCESS = "screenload_success";
	private static final String SCREENSTATUS_DELETE="delete";
	private static final String MAILSUBCLASS_CP = "CP";

	private static final String MAILSUBCLASS_LC = "LC";
	
	private static final String MAILSUBCLASS_SAL = "SAL";
	
	private static final String MAILSUBCLASS_ULD = "UL";
	
	private static final String MAILSUBCLASS_SV = "SV";

	private static final String MAILSUBCLASS_EMS ="EMS";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
		double lc =0.0;
	     double cp =0.0;
	     double sal=0.0;
	     double uld=0.0;
	     double sv=0.0;
	     double ems=0.0;
	     Money moneyChg=null;
	     double wt=0.0;
	     double netWt=0.0;
		CaptureCN66Session session=(CaptureCN66Session)getScreenSession(MODULE_NAME, SCREEN_ID);
    	CaptureCN66Form form=(CaptureCN66Form)invocationContext.screenModel;
    	//HashMap<String,Collection<AirlineCN66DetailsVO>> cn66s=null;
    	Page<AirlineCN66DetailsVO> cn66PageVos=null;
    	ArrayList<AirlineCN66DetailsVO> keyValues=null;
    	HashMap<String,Collection<AirlineCN66DetailsVO>> newcn66s=new HashMap<String,Collection<AirlineCN66DetailsVO>>();
    	//ArrayList<AirlineCN66DetailsVO> cn66sdetails=new ArrayList<AirlineCN66DetailsVO>();
    	cn66PageVos=session.getAirlineCN66DetailsVOs();
    	if(session.getAirlineCN66DetailsVOs()!=null && session.getAirlineCN66DetailsVOs().size()>0){
    		//cn66s=session.getCn66DetailsMap();
    		cn66PageVos=session.getAirlineCN66DetailsVOs();
    		log.log(Log.INFO, "cn66PageVos....", cn66PageVos);
    	}
    		/*if(cn66PageVos!=null && cn66PageVos.size()>0){
    		ArrayList<Collection<AirlineCN66DetailsVO>> vos=new ArrayList<Collection<AirlineCN66DetailsVO>>(cn66s.values());
    		int valsize=cn66PageVos.size();
    		for(int i=0;i<valsize;i++){
    			cn66sdetails.addAll((ArrayList<AirlineCN66DetailsVO>)vos.get(i));
    			
    		}
    		}
    	}*/
    	
    	String[] index=form.getCheck();
    	if(index!=null && index.length>0){
    		for(int i=index.length-1;i>-1;i--){
    			if(index[i]!=null && index[i].trim().length()>0){
    				log.log(Log.INFO, "cn66PageVos  after remove....",
							cn66PageVos);
					if(OPERATION_FLAG_INSERT.equals(cn66PageVos.get(Integer.parseInt(index[i])).getOperationFlag())){
    					log.log(Log.INFO, "index====>>>>....", Integer.parseInt(index[i]));
						cn66PageVos.remove(cn66PageVos.get(Integer.parseInt(index[i])));
    				log.log(Log.INFO, "cn66PageVos  after remove....",
							cn66PageVos);
    				} else {
						cn66PageVos.get(Integer.parseInt(index[i])).setOperationFlag(OPERATION_FLAG_DELETE);
					}
    				log.log(Log.INFO,"dshti===>>>>....");
					
    			}
    		}
    	}
    	try{
    		
			
			moneyChg=CurrencyHelper.getMoney(form.getBlgCurCode());
			moneyChg.setAmount(0.0D);
			
    	for(AirlineCN66DetailsVO vo:cn66PageVos){
    		if(!OPERATION_FLAG_DELETE.equals(vo.getOperationFlag())){
    		if(vo.getMailSubClass().equals(MAILSUBCLASS_CP)){
    			cp=cp+vo.getTotalWeight();
			}
			if(vo.getMailSubClass().equals(MAILSUBCLASS_LC)){
				lc=lc+vo.getTotalWeight();
			}
			if(vo.getMailSubClass().equals(MAILSUBCLASS_SAL)){
				sal=sal+vo.getTotalWeight();
			}
			if(vo.getMailSubClass().equals(MAILSUBCLASS_ULD)){
				uld=uld+vo.getTotalWeight();
			}
			if(vo.getMailSubClass().equals(MAILSUBCLASS_SV)){
				sv=sv+vo.getTotalWeight();
			}
			if(MAILSUBCLASS_EMS.equals(vo.getMailSubClass())){
				ems=ems+vo.getTotalWeight();
			}
			if(vo.getTotalWeight()>0.0){
				wt=wt+vo.getTotalWeight();      
			}	
			if(vo.getAmount()!=null){	
			moneyChg.plusEquals(vo.getAmount());
				 
			}
			if(vo.getTotalSummaryWeight()!=0.0){
				netWt=vo.getTotalSummaryWeight();
			}
    		}
			String key=new StringBuilder().append(vo.getCarriageFrom())
								.append("-").append(vo.getCarriageTo()).toString();
			//keyValues.add(key);
			if(!(newcn66s.containsKey(key))){
				keyValues=new ArrayList<AirlineCN66DetailsVO>();
				keyValues.add(vo);
				newcn66s.put(key,keyValues);
			}
			else{
				newcn66s.get(key).add(vo);
			}
			
		}
	}
    	catch(CurrencyException currencyException){
			log.log(Log.INFO,"CurrencyException found");
		}
    	form.setNetCPWeight(String.valueOf(cp));
    	form.setNetLCWeight(String.valueOf(lc));
    	//form.setNetSalWeight(String.valueOf(sal));
    	form.setNetUldWeight(String.valueOf(uld));
    	form.setNetSVWeight(String.valueOf(sv));
    	form.setNetEMSWeight(String.valueOf(ems));
    	form.setNetChargeMoney(moneyChg);
    	try{
    		form.setNetSummaryWeight(String.valueOf(UnitFormatter.getRoundedValue(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_DEFAULT, netWt)));
    	}catch(UnitException unitException){
    		log.log(Log.SEVERE, "Exception while converting the Net Summary Weight..", unitException.getMessage());
    	}
    	form.setScreenStatus(SCREENSTATUS_DELETE);
    	session.setCn66DetailsMap(newcn66s);
    	//System.out.println("afetr delete"+newcn66s);
    	invocationContext.target = SCREEN_SUCCESS;	// sets target
		log.exiting(CLASS_NAME,"execute");
	}
}
